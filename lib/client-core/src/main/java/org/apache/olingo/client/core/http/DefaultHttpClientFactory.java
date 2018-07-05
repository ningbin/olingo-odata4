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

import java.io.IOException;
import java.net.URI;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.olingo.commons.api.http.HttpMethod;

/**
 * Default implementation returning HttpClients with default parameters.
 */
public class DefaultHttpClientFactory extends AbstractHttpClientFactory {

  @SuppressWarnings("deprecation")
  @Override
  public CloseableHttpClient create(final HttpMethod method, final URI uri) {
    try {
      Class.forName("org.apache.http.impl.client.HttpClientBuilder");
      final HttpClientBuilder clientBuilder = HttpClientBuilder.create();
      clientBuilder.setUserAgent(USER_AGENT);
      return clientBuilder.build();
    } catch(ClassNotFoundException e) {
      final DefaultHttpClient client = new DefaultHttpClient();
      client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT);
      return client;
    }
  }

  @SuppressWarnings("deprecation")
  @Override
  public void close(HttpClient httpClient) {
    if (httpClient instanceof CloseableHttpClient) {
      try {
        ((CloseableHttpClient) httpClient).close();
      } catch (IOException e) {
        httpClient = null;
      }
    } else {
      httpClient.getConnectionManager().shutdown();
    }
  }
}
