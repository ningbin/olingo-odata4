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

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.api.data.ResWrap;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.Constants;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.server.rx.api.EntityObservable;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.reactivex.Observable;

public class JSONTest {
  
  private static final ODataClient client = ODataClientFactory.getClient();
  
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  
  private String getSuffix(final ContentType contentType) {
    return contentType.isCompatible(ContentType.APPLICATION_ATOM_SVC)
        || contentType.isCompatible(ContentType.APPLICATION_ATOM_XML)
        || contentType.isCompatible(ContentType.APPLICATION_XML) ? "xml": "json";
  }

  @SuppressWarnings("rawtypes")
  @Test
  public void entitySetWithStreaming() throws Exception {
    final StringWriter writer = new StringWriter();
    final EntityCollection entityCollection = client.getDeserializer(ContentType.JSON).toEntitySet(
        getClass().getResourceAsStream("Customers" + "." + getSuffix(ContentType.JSON))).getPayload();
    EntityObservable streamCollection = new EntityObservable() {
      Observable observable = Observable.fromIterable(entityCollection);
      @Override
      public Observable getObservable() {
        return observable;
      }
    };
    
    client.getRxSerializer(ContentType.JSON).write(writer, streamCollection);
    assertSimilar("Customers" + "." + getSuffix(ContentType.JSON), writer.toString(), false);
  }
  
  @SuppressWarnings("rawtypes")
  @Test
  public void entitySetWithStreamingWithNoneMetadata() throws Exception {
    final StringWriter writer = new StringWriter();
    final EntityCollection entityCollection = client.getDeserializer(ContentType.JSON_NO_METADATA).toEntitySet(
        getClass().getResourceAsStream("Customers" + ".json")).getPayload();
    EntityObservable streamCollection = new EntityObservable() {
      Observable observable = Observable.fromIterable(entityCollection);
      @Override
      public Observable getObservable() {
        return observable;
      }
    };
    
    new JsonRxSerializer(false, ContentType.JSON_NO_METADATA).write(writer, streamCollection);
    assertSimilarWithFullMetadata("Customers" + ".json", writer.toString(), false);
  }
  
  @SuppressWarnings("rawtypes")
  @Test
  public void entitySetWithStreamingWithFullMetadata() throws Exception {
    final StringWriter writer = new StringWriter();
    final EntityCollection entityCollection = client.getDeserializer(ContentType.JSON_FULL_METADATA).toEntitySet(
        getClass().getResourceAsStream("CustomersWithFullMetadata" + ".json")).getPayload();
    EntityObservable streamCollection = new EntityObservable() {
      Observable observable = Observable.fromIterable(entityCollection);
      @Override
      public Observable getObservable() {
        return observable;
      }
    };
    streamCollection.getAnnotations().addAll(entityCollection.getAnnotations());
    streamCollection.setNext(entityCollection.getNext());
    streamCollection.setBaseURI(entityCollection.getBaseURI());
    streamCollection.setId(entityCollection.getId());
    
    new JsonRxSerializer(false, ContentType.JSON_FULL_METADATA).write(writer, streamCollection);
    assertSimilarWithFullMetadata("CustomersWithFullMetadata" + ".json", writer.toString(), false);
  }
  
  @SuppressWarnings({ "rawtypes"})
  @Test
  public void entitySetWithStreaming1() throws Exception {
    final StringWriter writer = new StringWriter();
    final ResWrap<EntityCollection> container = client.getDeserializer(ContentType.JSON).toEntitySet(
        getClass().getResourceAsStream("Customers" + "." + getSuffix(ContentType.JSON)));
    final EntityObservable collection = new EntityObservable() {
      Observable observable1 = Observable.fromIterable(container.getPayload());
          @Override
          public Observable getObservable() {
            return observable1;
          }
        };
    collection.getAnnotations().addAll(container.getPayload().getAnnotations());
    collection.setNext(container.getPayload().getNext());
    collection.setBaseURI(container.getPayload().getBaseURI());
    collection.setId(container.getPayload().getId());
    ResWrap<EntityObservable> streamCollection = 
        new ResWrap<EntityObservable>(container.getContextURL(), 
            container.getMetadataETag(), collection);
    
    client.getRxSerializer(ContentType.JSON).write(writer, streamCollection);
    assertSimilar("Customers" + "." + getSuffix(ContentType.JSON), writer.toString(), false);
  }
  
  @SuppressWarnings("rawtypes")
  @Test
  public void entitySetWithStreamingWithFullMetadataInServerMode() throws Exception {
    final StringWriter writer = new StringWriter();
    final ResWrap<EntityCollection> container = client.getDeserializer(ContentType.JSON_FULL_METADATA).
        toEntitySet(getClass().getResourceAsStream("Customers_InServerMode_Observable" + ".json"));
    EntityObservable collection = new EntityObservable() {
      Observable observable = Observable.fromIterable(container.getPayload());
      @Override
      public Observable getObservable() {
        return observable;
      }
    };
    collection.getAnnotations().addAll(container.getPayload().getAnnotations());
    collection.setNext(container.getPayload().getNext());
    collection.setBaseURI(container.getPayload().getBaseURI());
    collection.setId(container.getPayload().getId());
    
    ResWrap<EntityObservable> streamCollection = 
        new ResWrap<EntityObservable>(container.getContextURL(), 
            container.getMetadataETag(), collection);
    
    new JsonRxSerializer(true, ContentType.JSON_FULL_METADATA).write(writer, streamCollection);
    assertSimilarWithFullMetadata("Customers_InServerMode_Observable" + ".json", writer.toString(), true);
  }
  
  @SuppressWarnings("rawtypes")
  @Test
  public void entitySetWithStreamingWithNoneMetadata1() throws Exception {
    final StringWriter writer = new StringWriter();
    final ResWrap<EntityCollection> container = client.getDeserializer(ContentType.JSON_NO_METADATA).toEntitySet(
        getClass().getResourceAsStream("Customers" + ".json"));
    EntityObservable collection = new EntityObservable() {
      Observable observable = Observable.fromIterable(container.getPayload());
      @Override
      public Observable getObservable() {
        return observable;
      }
    };
    
    collection.getAnnotations().addAll(container.getPayload().getAnnotations());
    collection.setNext(container.getPayload().getNext());
    collection.setBaseURI(container.getPayload().getBaseURI());
    collection.setId(container.getPayload().getId());
    
    ResWrap<EntityObservable> streamCollection = 
        new ResWrap<EntityObservable>(container.getContextURL(), 
            container.getMetadataETag(), collection);
    
    new JsonRxSerializer(false, ContentType.JSON_NO_METADATA).write(writer, streamCollection);
    assertSimilarWithFullMetadata("Customers" + ".json", writer.toString(), false);
  }
  
  @SuppressWarnings("rawtypes")
  @Test
  public void entitySetWithStreamingWithFullMetadata1() throws Exception {
    final StringWriter writer = new StringWriter();
    final ResWrap<EntityCollection> container = client.getDeserializer(ContentType.JSON_FULL_METADATA).toEntitySet(
        getClass().getResourceAsStream("CustomersWithFullMetadata" + ".json"));
    EntityObservable collection = new EntityObservable() {
      Observable observable = Observable.fromIterable(container.getPayload());
      @Override
      public Observable getObservable() {
        return observable;
      }
    };
    
    collection.getAnnotations().addAll(container.getPayload().getAnnotations());
    collection.setNext(container.getPayload().getNext());
    collection.setBaseURI(container.getPayload().getBaseURI());
    collection.setId(container.getPayload().getId());
    
    ResWrap<EntityObservable> streamCollection = 
        new ResWrap<EntityObservable>(container.getContextURL(), 
            container.getMetadataETag(), collection);
    
    new JsonRxSerializer(false, ContentType.JSON_FULL_METADATA).write(writer, streamCollection);
    assertSimilarWithFullMetadata("CustomersWithFullMetadata" + ".json", writer.toString(), false);
  }
  
  protected void assertSimilar(final String filename, final String actual, 
      boolean isServerMode) throws Exception {
    final JsonNode expected = OBJECT_MAPPER.readTree(IOUtils.toString(getClass().getResourceAsStream(filename)).
        replace(Constants.JSON_NAVIGATION_LINK, Constants.JSON_BIND_LINK_SUFFIX));
    cleanup((ObjectNode) expected, isServerMode);
    final ObjectNode actualNode = (ObjectNode) OBJECT_MAPPER.readTree(new ByteArrayInputStream(actual.getBytes()));
    cleanup(actualNode, isServerMode);
    assertEquals(expected, actualNode);
  }
  
  private void cleanup(final ObjectNode node, boolean isServerMode) {
    if (!isServerMode) {
      if (node.has(Constants.JSON_CONTEXT)) {
        node.remove(Constants.JSON_CONTEXT);
      }
      if (node.has(Constants.JSON_ETAG)) {
        node.remove(Constants.JSON_ETAG);
      }
      if (node.has(Constants.JSON_COUNT)) {
        node.remove(Constants.JSON_COUNT);
      }
    }
    if (node.has(Constants.JSON_TYPE)) {
      node.remove(Constants.JSON_TYPE);
    }
    if (node.has(Constants.JSON_EDIT_LINK)) {
      node.remove(Constants.JSON_EDIT_LINK);
    }
    if (node.has(Constants.JSON_READ_LINK)) {
      node.remove(Constants.JSON_READ_LINK);
    }
    if (node.has(Constants.JSON_MEDIA_EDIT_LINK)) {
      node.remove(Constants.JSON_MEDIA_EDIT_LINK);
    }
    if (node.has(Constants.JSON_MEDIA_READ_LINK)) {
      node.remove(Constants.JSON_MEDIA_READ_LINK);
    }
    if (node.has(Constants.JSON_MEDIA_CONTENT_TYPE)) {
      node.remove(Constants.JSON_MEDIA_CONTENT_TYPE);
    }
    if (node.has(Constants.JSON_ID)) {
      node.remove(Constants.JSON_ID);
    }
    final List<String> toRemove = new ArrayList<String>();
    for (final Iterator<Map.Entry<String, JsonNode>> itor = node.fields(); itor.hasNext();) {
      final Map.Entry<String, JsonNode> field = itor.next();

      final String key = field.getKey();
      if (key.charAt(0) == '#'
          || key.endsWith(Constants.JSON_TYPE)
          || key.endsWith(Constants.JSON_MEDIA_EDIT_LINK)
          || key.endsWith(Constants.JSON_MEDIA_CONTENT_TYPE)
          || key.endsWith(Constants.JSON_ASSOCIATION_LINK)
          || key.endsWith(Constants.JSON_MEDIA_ETAG)
          || key.endsWith(Constants.JSON_BIND_LINK_SUFFIX)) {

        toRemove.add(key);
      } else if (field.getValue().isObject()) {
        cleanup((ObjectNode) field.getValue(), false);
      } else if (field.getValue().isArray()) {
        for (final Iterator<JsonNode> arrayItems = field.getValue().elements(); arrayItems.hasNext();) {
          final JsonNode arrayItem = arrayItems.next();
          if (arrayItem.isObject()) {
            cleanup((ObjectNode) arrayItem, false);
          }
        }
      }
    }
    node.remove(toRemove);
  }
  
  protected void assertSimilarWithFullMetadata(final String filename, final String actual, 
      boolean isServerMode) throws Exception {
    String value = IOUtils.toString(getClass().getResourceAsStream(filename));
    final JsonNode expected = isServerMode ? OBJECT_MAPPER.readTree(value.
        replace(Constants.JSON_MEDIA_EDIT_LINK, Constants.JSON_MEDIA_READ_LINK)) :
    OBJECT_MAPPER.readTree(value.
        replace(Constants.JSON_NAVIGATION_LINK, Constants.JSON_BIND_LINK_SUFFIX));
    cleanupWithFullMetadata((ObjectNode) expected, isServerMode);
    final ObjectNode actualNode = (ObjectNode) OBJECT_MAPPER.readTree(new ByteArrayInputStream(actual.getBytes()));
    cleanupWithFullMetadata(actualNode, isServerMode);
    assertEquals(expected, actualNode);
  }
  
  private void cleanupWithFullMetadata(final ObjectNode node, boolean isServerMode) {
    if (!isServerMode) {
      if (node.has(Constants.JSON_CONTEXT)) {
        node.remove(Constants.JSON_CONTEXT);
      }
      if (node.has(Constants.JSON_ETAG)) {
        node.remove(Constants.JSON_ETAG);
      }
      if (node.has(Constants.JSON_COUNT)) {
        node.remove(Constants.JSON_COUNT);
      }
      if (node.has(Constants.JSON_EDIT_LINK)) {
        node.remove(Constants.JSON_EDIT_LINK);
      }
      if (node.has(Constants.JSON_MEDIA_READ_LINK)) {
        node.remove(Constants.JSON_MEDIA_READ_LINK);
      }
    }
    
    if (node.has(Constants.JSON_READ_LINK)) {
      node.remove(Constants.JSON_READ_LINK);
    }
    
    if (node.has(Constants.JSON_MEDIA_CONTENT_TYPE)) {
      node.remove(Constants.JSON_MEDIA_CONTENT_TYPE);
    }
    if (node.has(Constants.JSON_MEDIA_ETAG)) {
      node.remove(Constants.JSON_MEDIA_ETAG);
    }
    final List<String> toRemove = new ArrayList<String>();
    for (final Iterator<Map.Entry<String, JsonNode>> itor = node.fields(); itor.hasNext();) {
      final Map.Entry<String, JsonNode> field = itor.next();

      final String key = field.getKey();
      if (key.charAt(0) == '#'
          || (!isServerMode && key.endsWith(Constants.JSON_TYPE))
          || (!isServerMode && key.endsWith(Constants.JSON_MEDIA_EDIT_LINK))
          || key.endsWith(Constants.JSON_MEDIA_CONTENT_TYPE)
          || (!isServerMode && key.endsWith(Constants.JSON_ASSOCIATION_LINK))
          || key.endsWith(Constants.JSON_MEDIA_ETAG)) {

        toRemove.add(key);
      } else if (field.getValue().isObject()) {
        cleanup((ObjectNode) field.getValue(), false);
      } else if (field.getValue().isArray()) {
        for (final Iterator<JsonNode> arrayItems = field.getValue().elements(); arrayItems.hasNext();) {
          final JsonNode arrayItem = arrayItems.next();
          if (arrayItem.isObject()) {
            cleanup((ObjectNode) arrayItem, false);
          }
        }
      }
    }
    node.remove(toRemove);
  }
}
