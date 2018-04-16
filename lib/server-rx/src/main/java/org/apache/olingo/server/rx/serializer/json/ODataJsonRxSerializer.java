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
package org.apache.olingo.server.rx.serializer.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.olingo.commons.api.Constants;
import org.apache.olingo.commons.api.data.AbstractEntityCollection;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.uri.queryoption.ExpandOption;
import org.apache.olingo.server.api.uri.queryoption.SelectOption;
import org.apache.olingo.server.core.serializer.json.ODataJsonSerializer;
import org.apache.olingo.server.rx.api.EntityObservable;

import com.fasterxml.jackson.core.JsonGenerator;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ODataJsonRxSerializer extends ODataJsonSerializer {

  public ODataJsonRxSerializer(final ContentType contentType) {
    super(contentType);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void writeEntitySet(final ServiceMetadata metadata, final EdmEntityType entityType,
      final AbstractEntityCollection entitySet, final ExpandOption expand, final Integer toDepth, 
      final SelectOption select, final boolean onlyReference, final Set<String> ancestors, 
      final String name, final JsonGenerator json) throws IOException, SerializerException {
    final List<SerializerException> ex = new ArrayList<SerializerException>();
    json.writeStartArray();
    ((EntityObservable) entitySet).getObservable().subscribe(new Observer<Entity>() {

      @Override
      public void onError(Throwable e) {
        ex.add(new SerializerException(e.getMessage(), e,
            SerializerException.MessageKeys.IO_EXCEPTION));
      }

      @Override
      public void onNext(Entity entity) {
        try {
          if (onlyReference) {
            json.writeStartObject();
            json.writeStringField(Constants.JSON_ID, getEntityId((Entity) entity,
                entityType, name));
            json.writeEndObject();
          } else {
            writeEntity(metadata, entityType, (Entity) entity, null, expand, toDepth,
                select, false, ancestors, name, json);
          }
        } catch (SerializerException e) {
          ex.add(e);
        } catch (IOException e) {
          ex.add(new SerializerException(e.getMessage(), e,
              SerializerException.MessageKeys.IO_EXCEPTION));
        }
      }

      @Override
      public void onSubscribe(Disposable d) {
        //Provides the Observer with the means of cancelling the connection with the Observable.
       
      }

      @Override
      public void onComplete() {
        //Notifies the Observer that the Observable has finished sending push-based notifications
        //No task to be performed on Complete
      }
    });

    json.writeEndArray();
    if (!ex.isEmpty()) {
      throw ex.get(0);
    }
  }
}
