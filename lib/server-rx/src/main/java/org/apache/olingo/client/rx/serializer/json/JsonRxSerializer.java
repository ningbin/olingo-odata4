/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.client.rx.serializer.json;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.client.api.data.ResWrap;
import org.apache.olingo.client.api.serialization.ODataSerializerException;
import org.apache.olingo.client.core.serialization.JsonEntitySerializer;
import org.apache.olingo.commons.api.Constants;
import org.apache.olingo.commons.api.data.Annotation;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeException;
import org.apache.olingo.commons.api.ex.ODataNotSupportedException;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.server.rx.api.EntityObservable;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class JsonRxSerializer extends JsonEntitySerializer {

  public JsonRxSerializer(boolean serverMode, ContentType contentType) {
    super(serverMode, contentType);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> void write(final Writer writer, final T entityCollection) throws ODataSerializerException {
    final List<ODataSerializerException> ex = new ArrayList<ODataSerializerException>();
    final List<JsonGenerator> json = new ArrayList<JsonGenerator>();
    try {
      json.add(new JsonFactory().createGenerator(writer));
    } catch (IOException e) {
      ex.add(new ODataSerializerException(e));
    }
    if (entityCollection instanceof EntityObservable) {
      try {
        json.get(0).writeStartObject();
        doSerializeAdditionalContent(
            new ResWrap<EntityObservable>(null, null, (EntityObservable) entityCollection), 
            json.get(0), (EntityObservable) entityCollection);
        json.get(0).writeArrayFieldStart(Constants.VALUE);
        } catch (EdmPrimitiveTypeException e) {
          ex.add(new ODataSerializerException(e));
        } catch (IOException e) {
          ex.add(new ODataSerializerException(e));
        }
      
      ((EntityObservable) entityCollection).getObservable().subscribe(new Observer<Entity>() {

        @Override
        public void onSubscribe(Disposable d) {
          System.out.println();
        }

        @Override
        public void onNext(Entity entity) {
          try {
            try {
              doSerialize(entity, json.get(0));
            } catch (IOException e) {
              ex.add(new ODataSerializerException(e));
            }
          } catch (final EdmPrimitiveTypeException e) {
            ex.add(new ODataSerializerException(e));
          }
        }

        @Override
        public void onError(Throwable e) {
          ex.add(new ODataSerializerException(e.getMessage(), e));
        }

        @Override
        public void onComplete() {
          
        }
        
      });
      try {
        json.get(0).writeEndArray();
        json.get(0).writeEndObject();
        json.get(0).flush();
      } catch (IOException e1) {
        ex.add(new ODataSerializerException(e1));
      }
      
      if (!ex.isEmpty()) {
        throw ex.get(0);
      }
    } else {
      throw new ODataNotSupportedException("Streaming is supported only for Entity Collection");
    }
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public <T> void write(final Writer writer, final ResWrap<T> container) throws ODataSerializerException {
    final List<ODataSerializerException> ex = new ArrayList<ODataSerializerException>();
    final List<JsonGenerator> json = new ArrayList<JsonGenerator>();
    T entityCollection = container.getPayload();
    try {
      json.add(new JsonFactory().createGenerator(writer));
    } catch (IOException e) {
      ex.add(new ODataSerializerException(e));
    }
    if (entityCollection instanceof EntityObservable) {
      try {
        json.get(0).writeStartObject();
        doSerializeAdditionalContent((ResWrap<EntityObservable>) container, 
            json.get(0), (EntityObservable) entityCollection);
        json.get(0).writeArrayFieldStart(Constants.VALUE);
        } catch (EdmPrimitiveTypeException e) {
          ex.add(new ODataSerializerException(e));
        } catch (IOException e) {
          ex.add(new ODataSerializerException(e));
        }
      
      ((EntityObservable) entityCollection).getObservable().subscribe(new Observer<Entity>() {

        @Override
        public void onSubscribe(Disposable d) {
          
        }

        @Override
        public void onNext(Entity entity) {
          try {
            try {
              doSerialize(entity, json.get(0));
            } catch (IOException e) {
              ex.add(new ODataSerializerException(e));
            }
          } catch (final EdmPrimitiveTypeException e) {
            ex.add(new ODataSerializerException(e));
          }
        }

        @Override
        public void onError(Throwable e) {
          ex.add(new ODataSerializerException(e.getMessage(), e));
        }

        @Override
        public void onComplete() {
          
        }
        
      });
      try {
        json.get(0).writeEndArray();
        json.get(0).writeEndObject();
        json.get(0).flush();
      } catch (IOException e1) {
        ex.add(new ODataSerializerException(e1));
      }
      
      if (!ex.isEmpty()) {
        throw ex.get(0);
      }
    } else {
      throw new ODataNotSupportedException("Streaming is supported only for Entity Collection");
    }
  }
  
  /**
   * @param container
   * @param jgen
   * @param entitySet
   * @throws IOException
   * @throws EdmPrimitiveTypeException
   */
  private void doSerializeAdditionalContent(final ResWrap<EntityObservable> container, final JsonGenerator jgen,
      final EntityObservable entitySet) throws IOException, EdmPrimitiveTypeException {
    if (serverMode && !isODataMetadataNone) {
      if (container.getContextURL() != null) {
        jgen.writeStringField(Constants.JSON_CONTEXT, container.getContextURL().toASCIIString());
      }

      if (container.getMetadataETag() != null) {
        jgen.writeStringField(Constants.JSON_METADATA_ETAG, container.getMetadataETag());
      }
    }

    if (entitySet.getId() != null && isODataMetadataFull) {
      jgen.writeStringField(Constants.JSON_ID, entitySet.getId().toASCIIString());
    }
    final Integer count = entitySet.getCount() == null ? 
        Integer.parseInt(entitySet.getObservable().count().blockingGet().toString()) : 
          entitySet.getCount();
    if (isIEEE754Compatible) {
      jgen.writeStringField(Constants.JSON_COUNT, Integer.toString(count));
    } else {
      jgen.writeNumberField(Constants.JSON_COUNT, count);
    }
    if (serverMode) {
      if (entitySet.getNext() != null) {
        jgen.writeStringField(Constants.JSON_NEXT_LINK,
            entitySet.getNext().toASCIIString());
      }
      /*if (entitySet.getDeltaLink() != null && !isODataMetadataNone) {
        jgen.writeStringField(Constants.JSON_DELTA_LINK,
            entitySet.getDeltaLink().toASCIIString());
      }*/
    }

    for (Annotation annotation : entitySet.getAnnotations()) {
      valuable(jgen, annotation, "@" + annotation.getTerm());
    }
  }
}
