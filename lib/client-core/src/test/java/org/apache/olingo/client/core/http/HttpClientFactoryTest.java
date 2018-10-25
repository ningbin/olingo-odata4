/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.client.core.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.olingo.commons.api.http.HttpMethod;
import org.junit.Test;

public class HttpClientFactoryTest {

  @Test
  public void testBasicAuthHttpClient() {
    BasicAuthHttpClientFactory client = new BasicAuthHttpClientFactory
        ("username", "password");
    assertNotNull(client);
    assertNotNull(client.create(HttpMethod.GET, 
        URI.create("http://host/service/$metadata")));
  }
  
  @Test
  public void testDefaultHttpClient() {
    DefaultHttpClientFactory client = new DefaultHttpClientFactory();
    assertNotNull(client);
    assertNotNull(client.create(HttpMethod.GET, 
        URI.create("http://host/service/$metadata")));
   client.close(new TestClosableClient());
  }
  
  @Test
  public void testHttpMerge() {
    HttpMerge client = new HttpMerge();
    assertNotNull(client);
    client = new HttpMerge(URI.create("test"));
    assertNotNull(client);
    client = new HttpMerge("test");
    assertNotNull(client);
    assertEquals("MERGE", client.getMethod());
  }
  
  @Test
  public void testHttpPatch() {
    HttpPatch client = new HttpPatch();
    assertNotNull(client);
    client = new HttpPatch(URI.create("test"));
    assertNotNull(client);
    client = new HttpPatch("test");
    assertNotNull(client);
    assertEquals("PATCH", client.getMethod());
  }
  
  @Test
  public void testNTLMAuthClient() {
    NTLMAuthHttpClientFactory client = new NTLMAuthHttpClientFactory
        ("username", "password", "workstation", "domain");
    assertNotNull(client);
    assertNotNull(client.create(HttpMethod.GET, 
        URI.create("http://host/service/$metadata")));
  }
  
  @Test
  public void testOAuth2Exception() {
    OAuth2Exception ex = new OAuth2Exception("exception");
    assertNotNull(ex);
    assertEquals("exception",ex.getMessage());
    Throwable cause = new RuntimeException();
    ex = new OAuth2Exception(cause);
    assertNotNull(ex);
    assertEquals(cause,ex.getCause());
  }
  
  @Test
  public void testProxyWrappingHttpClient() throws URISyntaxException, MalformedURLException {
    URL url = new URL("http://host/service/$metadata");
    URI uri = new URI(url.getProtocol(), url.getHost(), 
        url.getPath(), url.getQuery(), null);
    ProxyWrappingHttpClientFactory client = new ProxyWrappingHttpClientFactory
        (uri);
    assertNotNull(client);
    DefaultHttpClientFactory defaultClient = new DefaultHttpClientFactory();
    client = new ProxyWrappingHttpClientFactory
        (uri, defaultClient);
    assertNotNull(client);
    client = new ProxyWrappingHttpClientFactory
        (uri, "user", "password", defaultClient);
    assertNotNull(client);
    assertNotNull(client.getWrappedHttpClientFactory());
    client = new ProxyWrappingHttpClientFactory
        (uri, "username", "password");
    assertNotNull(client);
    
    assertNotNull(client.create(HttpMethod.GET, 
        uri));
    client.close(new TestClosableClient());
  }
   class TestClosableClient extends CloseableHttpClient{

    @Override
    public HttpParams getParams() {
      return null;
    }

    @Override
    public ClientConnectionManager getConnectionManager() {
      return null;
    }

    @Override
    public void close() throws IOException {
      
    }

    @Override
    protected CloseableHttpResponse doExecute(HttpHost target, HttpRequest request, HttpContext context)
        throws IOException, ClientProtocolException {
      return null;
    }
     
   }
}
