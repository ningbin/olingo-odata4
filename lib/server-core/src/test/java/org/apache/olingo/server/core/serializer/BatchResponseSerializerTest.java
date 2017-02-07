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
package org.apache.olingo.server.core.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityIterator;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataResponse;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.deserializer.batch.ODataResponsePart;
import org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions;
import org.apache.olingo.server.api.serializer.SerializerStreamResult;
import org.apache.olingo.server.core.deserializer.batch.BatchLineReader;
import org.junit.Test;

public class BatchResponseSerializerTest {
  private static final String CRLF = "\r\n";
  private static final String BOUNDARY = "batch_" + UUID.randomUUID().toString();

  private static final Charset CS_ISO_8859_1 = Charset.forName("iso-8859-1");

  @Test
  public void testBatchResponse() throws Exception {
    final List<ODataResponsePart> parts = new ArrayList<ODataResponsePart>();
    ODataResponse response = new ODataResponse();
    response.setStatusCode(HttpStatusCode.OK.getStatusCode());
    response.setHeader(HttpHeader.CONTENT_TYPE, ContentType.APPLICATION_JSON.toContentTypeString());
    response.setContent(IOUtils.toInputStream("Walter Winter" + CRLF));

    List<ODataResponse> responses = new ArrayList<ODataResponse>(1);
    responses.add(response);
    parts.add(new ODataResponsePart(responses, false));

    ODataResponse changeSetResponse = new ODataResponse();
    changeSetResponse.setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    changeSetResponse.setHeader(HttpHeader.CONTENT_ID, "1");
    responses = new ArrayList<ODataResponse>(1);
    responses.add(changeSetResponse);
    parts.add(new ODataResponsePart(responses, true));

    BatchResponseSerializer serializer = new BatchResponseSerializer();
    final InputStream content = serializer.serialize(parts, BOUNDARY);
    assertNotNull(content);
    final BatchLineReader reader =
        new BatchLineReader(content);
    final List<String> body = reader.toList();
    reader.close();

    int line = 0;
    assertEquals(24, body.size());
    assertTrue(body.get(line++).contains("--batch_"));
    assertEquals("Content-Type: application/http" + CRLF, body.get(line++));
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("HTTP/1.1 200 OK" + CRLF, body.get(line++));
    assertEquals("Content-Type: application/json" + CRLF, body.get(line++));
    assertEquals("Content-Length: 15" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("Walter Winter" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--batch_"));
    assertTrue(body.get(line++).contains("Content-Type: multipart/mixed; boundary=changeset_"));
    assertEquals(CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--changeset_"));
    assertEquals("Content-Type: application/http" + CRLF, body.get(line++));
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, body.get(line++));
    assertEquals("Content-ID: 1" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("HTTP/1.1 204 No Content" + CRLF, body.get(line++));
    assertEquals("Content-Length: 0" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--changeset_"));
    assertTrue(body.get(line++).contains("--batch_"));
  }

  @Test
  public void testBatchResponseUmlautsUtf8() throws Exception {
    final List<ODataResponsePart> parts = new ArrayList<ODataResponsePart>();
    ODataResponse response = new ODataResponse();
    response.setStatusCode(HttpStatusCode.OK.getStatusCode());
    response.setHeader(HttpHeader.CONTENT_TYPE,
        ContentType.APPLICATION_JSON.toContentTypeString() + "; charset=UTF-8");
    response.setContent(IOUtils.toInputStream("Wälter Winter" + CRLF));

    List<ODataResponse> responses = new ArrayList<ODataResponse>(1);
    responses.add(response);
    parts.add(new ODataResponsePart(responses, false));

    ODataResponse changeSetResponse = new ODataResponse();
    changeSetResponse.setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    changeSetResponse.setHeader(HttpHeader.CONTENT_ID, "1");
    responses = new ArrayList<ODataResponse>(1);
    responses.add(changeSetResponse);
    parts.add(new ODataResponsePart(responses, true));

    BatchResponseSerializer serializer = new BatchResponseSerializer();
    final InputStream content = serializer.serialize(parts, BOUNDARY);
    assertNotNull(content);
    final BatchLineReader reader =
        new BatchLineReader(content);
    final List<String> body = reader.toList();
    reader.close();

    int line = 0;
    assertEquals(24, body.size());
    assertTrue(body.get(line++).contains("--batch_"));
    assertEquals("Content-Type: application/http" + CRLF, body.get(line++));
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("HTTP/1.1 200 OK" + CRLF, body.get(line++));
    assertEquals("Content-Type: application/json; charset=UTF-8" + CRLF, body.get(line++));
    assertEquals("Content-Length: 16" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("Wälter Winter" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--batch_"));
    assertTrue(body.get(line++).contains("Content-Type: multipart/mixed; boundary=changeset_"));
    assertEquals(CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--changeset_"));
    assertEquals("Content-Type: application/http" + CRLF, body.get(line++));
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, body.get(line++));
    assertEquals("Content-ID: 1" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("HTTP/1.1 204 No Content" + CRLF, body.get(line++));
    assertEquals("Content-Length: 0" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--changeset_"));
    assertTrue(body.get(line++).contains("--batch_"));
  }

  @Test
  public void testBatchResponseUmlautsUtf8BodyIsoHeader() throws Exception {
    final List<ODataResponsePart> parts = new ArrayList<ODataResponsePart>();
    ODataResponse response = new ODataResponse();
    response.setStatusCode(HttpStatusCode.OK.getStatusCode());
    response.setHeader(HttpHeader.CONTENT_TYPE,
        ContentType.APPLICATION_JSON.toContentTypeString() + "; charset=UTF-8");
    response.setContent(IOUtils.toInputStream("Wälter Winter" + CRLF));

    List<ODataResponse> responses = new ArrayList<ODataResponse>(1);
    responses.add(response);
    parts.add(new ODataResponsePart(responses, false));

    ODataResponse changeSetResponse = new ODataResponse();
    changeSetResponse.setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    changeSetResponse.setHeader(HttpHeader.CONTENT_ID, "1");

    byte[] umlauts = "äüö".getBytes(CS_ISO_8859_1);
    changeSetResponse.setHeader("Custom-Header", new String(umlauts, CS_ISO_8859_1));
    responses = new ArrayList<ODataResponse>(1);
    responses.add(changeSetResponse);
    parts.add(new ODataResponsePart(responses, true));

    BatchResponseSerializer serializer = new BatchResponseSerializer();
    final InputStream content = serializer.serialize(parts, BOUNDARY);
    assertNotNull(content);
    final BatchLineReader reader =
        new BatchLineReader(content);
    final List<String> body = reader.toList();
    reader.close();

    int line = 0;
    assertEquals(25, body.size());
    assertTrue(body.get(line++).contains("--batch_"));
    assertEquals("Content-Type: application/http" + CRLF, body.get(line++));
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("HTTP/1.1 200 OK" + CRLF, body.get(line++));
    assertEquals("Content-Type: application/json; charset=UTF-8" + CRLF, body.get(line++));
    assertEquals("Content-Length: 16" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("Wälter Winter" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--batch_"));
    assertTrue(body.get(line++).contains("Content-Type: multipart/mixed; boundary=changeset_"));
    assertEquals(CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--changeset_"));
    assertEquals("Content-Type: application/http" + CRLF, body.get(line++));
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, body.get(line++));
    assertEquals("Content-ID: 1" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("HTTP/1.1 204 No Content" + CRLF, body.get(line++));
    assertEquals("Custom-Header: äüö" + CRLF, body.get(line++));
    assertEquals("Content-Length: 0" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--changeset_"));
    assertTrue(body.get(line++).contains("--batch_"));
  }

  @Test
  public void testBatchResponseUmlautsUtf8BodyAndHeader() throws Exception {
    final List<ODataResponsePart> parts = new ArrayList<ODataResponsePart>();
    ODataResponse response = new ODataResponse();
    response.setStatusCode(HttpStatusCode.OK.getStatusCode());
    response.setHeader(HttpHeader.CONTENT_TYPE,
        ContentType.APPLICATION_JSON.toContentTypeString() + "; charset=UTF-8");
    response.setContent(IOUtils.toInputStream("Wälter Winter" + CRLF));

    List<ODataResponse> responses = new ArrayList<ODataResponse>(1);
    responses.add(response);
    parts.add(new ODataResponsePart(responses, false));

    ODataResponse changeSetResponse = new ODataResponse();
    changeSetResponse.setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    changeSetResponse.setHeader(HttpHeader.CONTENT_ID, "1");

    //    byte[] umlauts = "äüö".getBytes(CS_UTF_8);
    //    changeSetResponse.setHeader("Custom-Header", new String(umlauts, CS_UTF_8));
    changeSetResponse.setHeader("Custom-Header", "äüö");
    responses = new ArrayList<ODataResponse>(1);
    responses.add(changeSetResponse);
    parts.add(new ODataResponsePart(responses, true));

    BatchResponseSerializer serializer = new BatchResponseSerializer();
    final InputStream content = serializer.serialize(parts, BOUNDARY);
    assertNotNull(content);
    final BatchLineReader reader =
        new BatchLineReader(content);
    final List<String> body = reader.toList();
    reader.close();

    assertEquals(25, body.size());
    // TODO: check: with latest change in BatchResponseSerializer is not possible
    // to set header values with UTF-8 (only iso-8859-1)
    //    assertEquals("Custom-Header: Ã¤Ã¼Ã¶" + CRLF, body.get(19));
    assertEquals("Custom-Header: äüö" + CRLF, body.get(19));
  }

  @Test
  public void testBatchResponseUmlautsIso() throws Exception {
    final List<ODataResponsePart> parts = new ArrayList<ODataResponsePart>();
    ODataResponse response = new ODataResponse();
    response.setStatusCode(HttpStatusCode.OK.getStatusCode());
    response.setHeader(HttpHeader.CONTENT_TYPE,
        ContentType.APPLICATION_JSON.toContentTypeString() + "; charset=iso-8859-1");
    byte[] payload = ("Wälter Winter" + CRLF).getBytes("iso-8859-1");
    response.setContent(new ByteArrayInputStream(payload));

    List<ODataResponse> responses = new ArrayList<ODataResponse>(1);
    responses.add(response);
    parts.add(new ODataResponsePart(responses, false));

    ODataResponse changeSetResponse = new ODataResponse();
    changeSetResponse.setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    changeSetResponse.setHeader(HttpHeader.CONTENT_ID, "1");
    responses = new ArrayList<ODataResponse>(1);
    responses.add(changeSetResponse);
    parts.add(new ODataResponsePart(responses, true));

    BatchResponseSerializer serializer = new BatchResponseSerializer();
    final InputStream content = serializer.serialize(parts, BOUNDARY);
    assertNotNull(content);
    final BatchLineReader reader =
        new BatchLineReader(content);
    final List<String> body = reader.toList();
    reader.close();

    int line = 0;
    assertEquals(24, body.size());
    assertTrue(body.get(line++).contains("--batch_"));
    assertEquals("Content-Type: application/http" + CRLF, body.get(line++));
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("HTTP/1.1 200 OK" + CRLF, body.get(line++));
    assertEquals("Content-Type: application/json; charset=iso-8859-1" + CRLF, body.get(line++));
    assertEquals("Content-Length: 15" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("Wälter Winter" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--batch_"));
    assertTrue(body.get(line++).contains("Content-Type: multipart/mixed; boundary=changeset_"));
    assertEquals(CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--changeset_"));
    assertEquals("Content-Type: application/http" + CRLF, body.get(line++));
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, body.get(line++));
    assertEquals("Content-ID: 1" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("HTTP/1.1 204 No Content" + CRLF, body.get(line++));
    assertEquals("Content-Length: 0" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--changeset_"));
    assertTrue(body.get(line++).contains("--batch_"));
  }

  @Test
  public void testBatchResponseWithEndingCRLF() throws Exception {
    final List<ODataResponsePart> parts = new ArrayList<ODataResponsePart>();
    ODataResponse response = new ODataResponse();
    response.setStatusCode(HttpStatusCode.OK.getStatusCode());
    response.setHeader(HttpHeader.CONTENT_TYPE, "application/json");
    response.setContent(IOUtils.toInputStream("Walter Winter"));

    List<ODataResponse> responses = new ArrayList<ODataResponse>(1);
    responses.add(response);
    parts.add(new ODataResponsePart(responses, false));

    ODataResponse changeSetResponse = new ODataResponse();
    changeSetResponse.setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    changeSetResponse.setHeader(HttpHeader.CONTENT_ID, "1");
    responses = new ArrayList<ODataResponse>(1);
    responses.add(changeSetResponse);
    parts.add(new ODataResponsePart(responses, true));

    BatchResponseSerializer serializer = new BatchResponseSerializer();
    final InputStream content = serializer.serialize(parts, BOUNDARY);
    assertNotNull(content);
    final BatchLineReader reader =
        new BatchLineReader(content);
    final List<String> body = reader.toList();
    reader.close();

    int line = 0;
    assertEquals(23, body.size());
    assertTrue(body.get(line++).contains("--batch_"));
    assertEquals("Content-Type: application/http" + CRLF, body.get(line++));
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("HTTP/1.1 200 OK" + CRLF, body.get(line++));
    assertEquals("Content-Type: application/json" + CRLF, body.get(line++));
    assertEquals("Content-Length: 13" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("Walter Winter" + CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--batch_"));
    assertTrue(body.get(line++).contains("Content-Type: multipart/mixed; boundary=changeset_"));
    assertEquals(CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--changeset_"));
    assertEquals("Content-Type: application/http" + CRLF, body.get(line++));
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, body.get(line++));
    assertEquals("Content-ID: 1" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("HTTP/1.1 204 No Content" + CRLF, body.get(line++));
    assertEquals("Content-Length: 0" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--changeset_"));
    assertTrue(body.get(line++).contains("--batch_"));
  }

  @Test
  public void testResponse() throws Exception {
    List<ODataResponsePart> parts = new ArrayList<ODataResponsePart>();
    ODataResponse response = new ODataResponse();
    response.setStatusCode(HttpStatusCode.OK.getStatusCode());
    response.setHeader(HttpHeader.CONTENT_TYPE, "application/json");
    response.setContent(IOUtils.toInputStream("Walter Winter"));

    List<ODataResponse> responses = new ArrayList<ODataResponse>(1);
    responses.add(response);
    parts.add(new ODataResponsePart(responses, false));

    final BatchResponseSerializer serializer = new BatchResponseSerializer();
    final InputStream content = serializer.serialize(parts, BOUNDARY);

    assertNotNull(content);
    final BatchLineReader reader =
        new BatchLineReader(content);
    final List<String> body = reader.toList();
    reader.close();

    int line = 0;
    assertEquals(10, body.size());
    assertTrue(body.get(line++).contains("--batch_"));
    assertEquals("Content-Type: application/http" + CRLF, body.get(line++));
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("HTTP/1.1 200 OK" + CRLF, body.get(line++));
    assertEquals("Content-Type: application/json" + CRLF, body.get(line++));
    assertEquals("Content-Length: 13" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("Walter Winter" + CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--batch_"));
  }

  @Test
  public void testBigResponse() throws Exception {
    List<ODataResponsePart> parts = new ArrayList<ODataResponsePart>();
    ODataResponse response = new ODataResponse();
    response.setStatusCode(HttpStatusCode.OK.getStatusCode());
    response.setHeader(HttpHeader.CONTENT_TYPE, "application/json");
    String bigData = generateData(10000);
    response.setContent(IOUtils.toInputStream(bigData));

    List<ODataResponse> responses = new ArrayList<ODataResponse>(1);
    responses.add(response);
    parts.add(new ODataResponsePart(responses, false));

    final BatchResponseSerializer serializer = new BatchResponseSerializer();
    final InputStream content = serializer.serialize(parts, BOUNDARY);

    assertNotNull(content);
    final BatchLineReader reader =
        new BatchLineReader(content);
    final List<String> body = reader.toList();
    reader.close();

    int line = 0;
    assertEquals(10, body.size());
    assertTrue(body.get(line++).contains("--batch_"));
    assertEquals("Content-Type: application/http" + CRLF, body.get(line++));
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("HTTP/1.1 200 OK" + CRLF, body.get(line++));
    assertEquals("Content-Type: application/json" + CRLF, body.get(line++));
    assertEquals("Content-Length: 10000" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals(bigData + CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--batch_"));
  }

  @Test
  public void testChangeSetResponse() throws Exception {
    List<ODataResponsePart> parts = new ArrayList<ODataResponsePart>();
    ODataResponse response = new ODataResponse();
    response.setHeader(HttpHeader.CONTENT_ID, "1");
    response.setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());

    List<ODataResponse> responses = new ArrayList<ODataResponse>(1);
    responses.add(response);
    parts.add(new ODataResponsePart(responses, true));

    BatchResponseSerializer serializer = new BatchResponseSerializer();
    final InputStream content = serializer.serialize(parts, BOUNDARY);

    assertNotNull(content);

    final BatchLineReader reader =
        new BatchLineReader(content);
    final List<String> body = reader.toList();
    reader.close();

    int line = 0;
    assertEquals(14, body.size());
    assertTrue(body.get(line++).contains("--batch_"));
    assertTrue(body.get(line++).contains("Content-Type: multipart/mixed; boundary=changeset_"));
    assertEquals(CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--changeset_"));
    assertEquals("Content-Type: application/http" + CRLF, body.get(line++));
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, body.get(line++));
    assertEquals("Content-ID: 1" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("HTTP/1.1 204 No Content" + CRLF, body.get(line++));
    assertEquals("Content-Length: 0" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertTrue(body.get(line++).contains("--changeset_"));
    assertTrue(body.get(line++).contains("--batch_"));
  }

  /**
   * Generates a string with given length containing random upper case characters ([A-Z]).
   * @param len length of the generated string
   * @return random upper case characters ([A-Z])
   */
  public static String generateData(final int len) {
    Random random = new Random();
    StringBuilder b = new StringBuilder(len);
    for (int j = 0; j < len; j++) {
      final char c = (char) ('A' + random.nextInt('Z' - 'A' + 1));
      b.append(c);
    }
    return b.toString();
  }
  
  @Test
  public void testODataContentResponse() throws Exception {
    List<ODataResponsePart> parts = new ArrayList<ODataResponsePart>();
    ServiceMetadata serviceMetadata = mock(ServiceMetadata.class);
    final EdmEntityType edmEntityType = mock(EdmEntityType.class);
    EntityIterator entityCollection = new EntityIterator() {
      
      @Override
      public Entity next() {
        return null;
      }
      
      @Override
      public boolean hasNext() {
        return false;
      }
    };  

    SerializerStreamResult serializerResult = OData.newInstance().
        createSerializer(ContentType.APPLICATION_JSON).entityCollectionStreamed(
        serviceMetadata,
        edmEntityType,
        entityCollection,
        EntityCollectionSerializerOptions.with().contextURL
        (ContextURL.with().oDataPath("http://host/svc").build()).build());
    ODataResponse response = new ODataResponse();
    response.setODataContent(serializerResult.getODataContent());
    response.setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    parts.add(new ODataResponsePart(response, false));

    BatchResponseSerializer serializer = new BatchResponseSerializer();
    final InputStream content = serializer.serialize(parts, BOUNDARY);

    assertNotNull(content);

    final BatchLineReader reader = new BatchLineReader(content);
    final List<String> body = reader.toList();
    reader.close();

    int line = 0;
    assertEquals(9, body.size());
    assertEquals("--" + BOUNDARY + CRLF, body.get(line++));
    assertEquals("Content-Type: application/http" + CRLF, body.get(line++));
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("HTTP/1.1 204 No Content" + CRLF, body.get(line++));
    assertEquals("Content-Length: 47" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("{\"@odata.context\":\"../../$metadata\",\"value\":[]}" + CRLF, body.get(line++));
    assertEquals("--" + BOUNDARY + "--" + CRLF, body.get(line++));
  }
  
  @Test
  public void changeSetODataContentResponse() throws Exception {
    List<ODataResponsePart> parts = new ArrayList<ODataResponsePart>();
    ServiceMetadata serviceMetadata = mock(ServiceMetadata.class);
    final EdmEntityType edmEntityType = mock(EdmEntityType.class);
    EntityIterator entityCollection = new EntityIterator() {
      
      @Override
      public Entity next() {
        return null;
      }
      
      @Override
      public boolean hasNext() {
        return false;
      }
    };  

    SerializerStreamResult serializerResult = OData.newInstance().
        createSerializer(ContentType.APPLICATION_JSON).entityCollectionStreamed(
        serviceMetadata,
        edmEntityType,
        entityCollection,
        EntityCollectionSerializerOptions.with().contextURL
        (ContextURL.with().oDataPath("http://host/svc").build()).build());
    ODataResponse response = new ODataResponse();
    response.setODataContent(serializerResult.getODataContent());
    response.setStatusCode(HttpStatusCode.NO_CONTENT.getStatusCode());
    response.setHeader(HttpHeader.CONTENT_ID, "1");
    parts.add(new ODataResponsePart(response, true));

    BatchResponseSerializer serializer = new BatchResponseSerializer();
    final InputStream content = serializer.serialize(parts, BOUNDARY);

    assertNotNull(content);

    final BatchLineReader reader = new BatchLineReader(content);
    final List<String> body = reader.toList();
    reader.close();

    int line = 0;
    assertEquals(14, body.size());
    assertEquals("--" + BOUNDARY + CRLF, body.get(line++));
    assertTrue(body.get(line++).startsWith("Content-Type: multipart/mixed; boundary=changeset_"));
    assertEquals(CRLF, body.get(line++));
    assertTrue(body.get(line++).startsWith("--changeset_"));
    assertEquals("Content-Type: application/http" + CRLF, body.get(line++));
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, body.get(line++));
    assertEquals("Content-ID: 1" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("HTTP/1.1 204 No Content" + CRLF, body.get(line++));
    assertEquals("Content-Length: 47" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("{\"@odata.context\":\"../../$metadata\",\"value\":[]}" + CRLF, body.get(line++));
    assertTrue(body.get(line++).startsWith("--changeset_"));
    assertEquals("--" + BOUNDARY + "--" + CRLF, body.get(line++));
  }
  
  @Test
  public void testODataContentWithODataResponse() throws Exception {
    List<ODataResponsePart> parts = new ArrayList<ODataResponsePart>();
    
    ODataResponse response = new ODataResponse();
    response.setStatusCode(HttpStatusCode.OK.getStatusCode());
    response.setHeader(HttpHeader.CONTENT_TYPE, ContentType.TEXT_PLAIN.toContentTypeString());
    String bigData = generateData(10000);
    response.setContent(IOUtils.toInputStream(bigData));
    parts.add(new ODataResponsePart(response, false));
    
    ServiceMetadata serviceMetadata = mock(ServiceMetadata.class);
    final EdmEntityType edmEntityType = mock(EdmEntityType.class);
    EntityIterator entityCollection = new EntityIterator() {
      
      @Override
      public Entity next() {
        return null;
      }
      
      @Override
      public boolean hasNext() {
        return false;
      }
    };  

    SerializerStreamResult serializerResult = OData.newInstance().
        createSerializer(ContentType.APPLICATION_JSON).entityCollectionStreamed(
        serviceMetadata,
        edmEntityType,
        entityCollection,
        EntityCollectionSerializerOptions.with().contextURL
        (ContextURL.with().oDataPath("http://host/svc").build()).build());
    ODataResponse response1 = new ODataResponse();
    response1.setODataContent(serializerResult.getODataContent());
    response1.setStatusCode(HttpStatusCode.OK.getStatusCode());
    parts.add(new ODataResponsePart(response1, false));

    BatchResponseSerializer serializer = new BatchResponseSerializer();
    final InputStream content = serializer.serialize(parts, BOUNDARY);

    assertNotNull(content);

    final BatchLineReader reader = new BatchLineReader(content);
    final List<String> body = reader.toList();
    reader.close();

    int line = 0;
    assertEquals(18, body.size());
    assertEquals("--" + BOUNDARY + CRLF, body.get(line++));
    assertEquals("Content-Type: application/http" + CRLF, body.get(line++));
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("HTTP/1.1 200 OK" + CRLF, body.get(line++));
    assertEquals("Content-Type: text/plain" + CRLF, body.get(line++));
    assertEquals("Content-Length: 10000" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals(bigData + CRLF, body.get(line++));
    assertEquals("--" + BOUNDARY + CRLF, body.get(line++));
    assertEquals("Content-Type: application/http" + CRLF, body.get(line++));
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("HTTP/1.1 200 OK" + CRLF, body.get(line++));
    assertEquals("Content-Length: 47" + CRLF, body.get(line++));
    assertEquals(CRLF, body.get(line++));
    assertEquals("{\"@odata.context\":\"../../$metadata\",\"value\":[]}" + CRLF, body.get(line++));
    assertEquals("--" + BOUNDARY + "--" + CRLF, body.get(line++));
  }
}
