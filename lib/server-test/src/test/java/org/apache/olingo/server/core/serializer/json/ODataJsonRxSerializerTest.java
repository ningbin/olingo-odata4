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
package org.apache.olingo.server.core.serializer.json;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Operation;
import org.apache.olingo.commons.api.edm.EdmEntityContainer;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edmx.EdmxReference;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataContent;
import org.apache.olingo.server.api.ODataContentWriteErrorCallback;
import org.apache.olingo.server.api.ODataContentWriteErrorContext;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.uri.queryoption.CountOption;
import org.apache.olingo.server.rx.api.EntityObservable;
import org.apache.olingo.server.rx.serializer.json.ODataJsonRxSerializer;
import org.apache.olingo.server.tecsvc.MetadataETagSupport;
import org.apache.olingo.server.tecsvc.data.DataProvider;
import org.apache.olingo.server.tecsvc.provider.EdmTechProvider;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.Observable;


public class ODataJsonRxSerializerTest {
  private static final OData odata = OData.newInstance();
  private static final ServiceMetadata metadata = odata.createServiceMetadata(
      new EdmTechProvider(), Collections.<EdmxReference> emptyList(), new MetadataETagSupport("W/\"metadataETag\""));
  private static final EdmEntityContainer entityContainer = metadata.getEdm().getEntityContainer();
  private final DataProvider data = new DataProvider(odata, metadata.getEdm());
  private final ODataSerializer serializer = new ODataJsonRxSerializer(ContentType.JSON);
  private final ODataSerializer serializerNoMetadata = new ODataJsonRxSerializer(ContentType.JSON_NO_METADATA);
  private final ODataSerializer serializerFullMetadata = new ODataJsonRxSerializer(ContentType.JSON_FULL_METADATA);

  @Test
  public void entityCollectionStreamed() throws Exception {
    final EdmEntitySet edmEntitySet = entityContainer.getEntitySet("ESAllPrim");
    EntityObservable streamCollection = new EntityObservable(){
      EntityCollection entityCollection = data.readAll(edmEntitySet);
      Observable<Entity> observable = Observable.fromIterable(entityCollection);
      @Override
      public Observable<Entity> getObservable() {
       return observable;
      };
      private URI next = entityCollection.getNext();
      private Integer count = entityCollection.getCount();
      @Override
      public List<Operation> getOperations() {
        return entityCollection.getOperations();
      } 
      
      public URI getNext() {
        return next;
      }
      
      public Integer getCount() {
        return count;
      }
    };
    CountOption countOption = Mockito.mock(CountOption.class);
    Mockito.when(countOption.getValue()).thenReturn(true);

    ODataContent result = serializer.entityCollectionStreamed(
        metadata, edmEntitySet.getEntityType(), streamCollection,
        EntityCollectionSerializerOptions.with()
            .contextURL(ContextURL.with().entitySet(edmEntitySet).build())
            .build()).getODataContent();
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    result.write(bout);
    final String resultString = new String(bout.toByteArray(), "UTF-8");

    Assert.assertThat(resultString, CoreMatchers.startsWith("{"
        + "\"@odata.context\":\"$metadata#ESAllPrim\","
        + "\"@odata.metadataEtag\":\"W/\\\"metadataETag\\\"\","
        + "\"value\":[{\"PropertyInt16\":32767,\"PropertyString\""));
    Assert.assertThat(resultString, CoreMatchers.endsWith(
        "\"PropertyTimeOfDay\":\"00:01:01\"}]}"));

    int count = 0;
    int index = -1;
    while ((index = resultString.indexOf("PropertyInt16\":", ++index)) > 0) {
      count++;
    }
    Assert.assertEquals(3, count);
  }
  
  @Test
  public void entityCollectionStreamedNoMetadata() throws Exception {
    final EdmEntitySet edmEntitySet = entityContainer.getEntitySet("ESAllPrim");
    EntityObservable streamCollection = new EntityObservable(){
      EntityCollection entityCollection = data.readAll(edmEntitySet);
      Observable<Entity> observable = Observable.fromIterable(entityCollection);
      @Override
      public Observable<Entity> getObservable() {
       return observable;
      };
      private URI next = entityCollection.getNext();
      private Integer count = entityCollection.getCount();
      @Override
      public List<Operation> getOperations() {
        return entityCollection.getOperations();
      } 
      
      public URI getNext() {
        return next;
      }
      
      public Integer getCount() {
        return count;
      }
    };
    CountOption countOption = Mockito.mock(CountOption.class);
    Mockito.when(countOption.getValue()).thenReturn(true);

    ODataContent result = serializerNoMetadata.entityCollectionStreamed(
        metadata, edmEntitySet.getEntityType(), streamCollection,
        EntityCollectionSerializerOptions.with()
            .contextURL(ContextURL.with().entitySet(edmEntitySet).build())
            .build()).getODataContent();
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    result.write(bout);
    final String resultString = new String(bout.toByteArray(), "UTF-8");

    Assert.assertThat(resultString, CoreMatchers.startsWith("{"
        + "\"value\":[{\"PropertyInt16\":32767,\"PropertyString\""));
    Assert.assertThat(resultString, CoreMatchers.endsWith(
        "\"PropertyTimeOfDay\":\"00:01:01\"}]}"));

    int count = 0;
    int index = -1;
    while ((index = resultString.indexOf("PropertyInt16\":", ++index)) > 0) {
      count++;
    }
    Assert.assertEquals(3, count);
  }
  
  @Test
  public void entityCollectionStreamedFullMetadata() throws Exception {
    final EdmEntitySet edmEntitySet = entityContainer.getEntitySet("ESAllPrim");
    EntityObservable streamCollection = new EntityObservable(){
      EntityCollection entityCollection = data.readAll(edmEntitySet);
      Observable<Entity> observable = Observable.fromIterable(entityCollection);
      @Override
      public Observable<Entity> getObservable() {
       return observable;
      };
      private URI next = entityCollection.getNext();
      private Integer count = entityCollection.getCount();
      @Override
      public List<Operation> getOperations() {
        return entityCollection.getOperations();
      } 
      
      public URI getNext() {
        return next;
      }
      
      public Integer getCount() {
        return count;
      }
    };
    CountOption countOption = Mockito.mock(CountOption.class);
    Mockito.when(countOption.getValue()).thenReturn(true);

    ODataContent result = serializerFullMetadata.entityCollectionStreamed(
        metadata, edmEntitySet.getEntityType(), streamCollection,
        EntityCollectionSerializerOptions.with()
            .contextURL(ContextURL.with().entitySet(edmEntitySet).build())
            .build()).getODataContent();
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    result.write(bout);
    final String resultString = new String(bout.toByteArray(), "UTF-8");

    Assert.assertThat(resultString, CoreMatchers.startsWith("{"
        + "\"@odata.context\":\"$metadata#ESAllPrim\","
        + "\"@odata.metadataEtag\":\"W/\\\"metadataETag\\\"\","
        + "\"value\":[{\"@odata.type\":\"#olingo.odata.test1.ETAllPrim\""));
    Assert.assertThat(resultString, CoreMatchers.endsWith(
        "\"target\":\"ESAllPrim(0)/olingo.odata.test1.BAETAllPrimRT\"}}]}"));

    int count = 0;
    int index = -1;
    while ((index = resultString.indexOf("PropertyInt16\":", ++index)) > 0) {
      count++;
    }
    Assert.assertEquals(3, count);
  }
  

  @Test
  public void entityCollectionStreamedWithError() throws Exception {
    final EdmEntitySet edmEntitySet = entityContainer.getEntitySet("ESAllPrim");
    Entity e =  new Entity();
    e.setId(URI.create("id"));
    final EntityCollection ec = new EntityCollection();
    ec.getEntities().addAll(Collections.singletonList(e));
    EntityObservable streamCollection = new EntityObservable(){
      EntityCollection entityCollection = data.readAll(edmEntitySet);
      Observable<Entity> observable = Observable.fromIterable(ec);
      @Override
      public Observable<Entity> getObservable() {
       return observable;
      };
     @Override
      public List<Operation> getOperations() {
        return entityCollection.getOperations();
      } 
    };
    
    CountOption countOption = Mockito.mock(CountOption.class);
    Mockito.when(countOption.getValue()).thenReturn(true);

    ODataContentWriteErrorCallback errorCallback = new ODataContentWriteErrorCallback() {
      @Override
      public void handleError(ODataContentWriteErrorContext context, WritableByteChannel channel) {
        try {
          Exception ex = context.getException();
          Assert.assertEquals(ex, context.getODataLibraryException());
          String msgKey = context.getODataLibraryException().getMessageKey().getKey();
          String toChannel = "ERROR: " + msgKey;
          channel.write(ByteBuffer.wrap(toChannel.getBytes("UTF-8")));
        } catch (IOException e) {
          throw new RuntimeException("Error in error.");
        }
      }
    };

    ODataContent result = serializer.entityCollectionStreamed(
        metadata, edmEntitySet.getEntityType(), streamCollection,
        EntityCollectionSerializerOptions.with()
            .writeContentErrorCallback(errorCallback)
            .contextURL(ContextURL.with().entitySet(edmEntitySet).build())
            .build()).getODataContent();
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    result.write(bout);
    final String resultString = new String(bout.toByteArray(), "UTF-8");
    Assert.assertEquals("ERROR: IO_EXCEPTION", resultString);
  }
  
  @Test
  public void entityCollectionStreamedWihPaging() throws Exception {
    final EdmEntitySet edmEntitySet = entityContainer.getEntitySet("ESAllPrim");
    EntityObservable streamCollection = new EntityObservable(){
      EntityCollection entityCollection = data.readAll(edmEntitySet);
      Observable<Entity> observable = Observable.fromIterable(entityCollection);
      @Override
      public Observable<Entity> getObservable() {
       return observable;
      };
      @Override
      public List<Operation> getOperations() {
        return entityCollection.getOperations();
      } 
      
      public URI getNext() {
        try {
          return new URI("ESAllPrim?%24skiptoken=1%2A10");
        } catch (URISyntaxException e) {
          return entityCollection.getNext();
        }
      }
      
      public Integer getCount() {
        return 3;
      }
    };
    CountOption countOption = Mockito.mock(CountOption.class);
    Mockito.when(countOption.getValue()).thenReturn(true);

    ODataContent result = serializer.entityCollectionStreamed(
        metadata, edmEntitySet.getEntityType(), streamCollection,
        EntityCollectionSerializerOptions.with()
            .contextURL(ContextURL.with().entitySet(edmEntitySet).build())
            .build()).getODataContent();
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    result.write(bout);
    final String resultString = new String(bout.toByteArray(), "UTF-8");

    Assert.assertThat(resultString, CoreMatchers.startsWith("{"
        + "\"@odata.context\":\"$metadata#ESAllPrim\","
        + "\"@odata.metadataEtag\":\"W/\\\"metadataETag\\\"\","
        + "\"value\":[{\"PropertyInt16\":32767,\"PropertyString\""));
    Assert.assertThat(resultString, CoreMatchers.endsWith(
        "\"@odata.nextLink\":\"ESAllPrim?%24skiptoken=1%2A10\"}"));

    int count = 0;
    int index = -1;
    while ((index = resultString.indexOf("PropertyInt16\":", ++index)) > 0) {
      count++;
    }
    Assert.assertEquals(3, count);
  }
  
}
