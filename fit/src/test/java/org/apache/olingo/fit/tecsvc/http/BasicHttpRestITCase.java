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
package org.apache.olingo.fit.tecsvc.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpMethod;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.fit.AbstractBaseTestITCase;
import org.apache.olingo.fit.tecsvc.TecSvcConst;
import org.junit.Test;

public class BasicHttpRestITCase extends AbstractBaseTestITCase {

	private static final String SERVICE_URI = TecSvcConst.BASE_URI + "/";

	@Test
	public void testRestFlowEntityWithPrimitivePropoerty() throws Exception {
		URL url = new URL(SERVICE_URI + "ESAllPrim/32767");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.OK.getStatusCode(), connection.getResponseCode());

		final String content = IOUtils.toString(connection.getInputStream());
		assertTrue(content.contains("\"PropertyInt16\":32767"));
		assertTrue(content.contains("\"PropertyString\":\"First Resource - positive values\""));
	}

	@Test
	public void testRestFlowEntityWithPrimitivePropoertyName() throws Exception {
		URL url = new URL(SERVICE_URI + "ESAllPrim/PropertyInt16=32767");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.OK.getStatusCode(), connection.getResponseCode());

		final String content = IOUtils.toString(connection.getInputStream());
		assertTrue(content.contains("\"PropertyInt16\":32767"));
		assertTrue(content.contains("\"PropertyString\":\"First Resource - positive values\""));
	}
	
	@Test
	public void testRestFlowEntityWithPrimitivePropoertyAndNavigation() throws Exception {
		URL url = new URL(SERVICE_URI + "ESAllPrim/32767/NavPropertyETTwoPrimMany");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.OK.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getInputStream());
		assertTrue(content.contains("\"PropertyInt16\":-365"));
		assertTrue(content.contains("\"PropertyString\":\"Test String2\""));
	}
	
	@Test
	public void testRestFlowEntityWithPrimitivePropoertyNameAndNavigation() throws Exception {
		URL url = new URL(SERVICE_URI + "ESAllPrim/PropertyInt16=32767/NavPropertyETTwoPrimMany");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.OK.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getInputStream());
		assertTrue(content.contains("\"PropertyInt16\":-365"));
		assertTrue(content.contains("\"PropertyString\":\"Test String2\""));
	}
	
	@Test
	public void testRestFlowEntityWithPrimitivePropoertyAndNavigationWithPrimitiveProperty() throws Exception {
		URL url = new URL(SERVICE_URI + "ESAllPrim/32767/NavPropertyETTwoPrimMany/-365");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.OK.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getInputStream());
		assertTrue(content.contains("\"PropertyInt16\":-365"));
		assertTrue(content.contains("\"PropertyString\":\"Test String2\""));
	}
	
	@Test
	public void testRestFlowEntityWithPrimitivePropoertyNameAndNavigationWithPrimitiveProperty() 
			throws Exception {
		URL url = new URL(SERVICE_URI + "ESAllPrim/PropertyInt16=32767/NavPropertyETTwoPrimMany"
				+ "/PropertyInt16=-365");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.OK.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getInputStream());
		assertTrue(content.contains("\"PropertyInt16\":-365"));
		assertTrue(content.contains("\"PropertyString\":\"Test String2\""));
	}
	
	@Test
	public void testRestFlowEntityWithCompoundPropoerties() throws Exception {
		URL url = new URL(SERVICE_URI + "ESTwoKeyNavCont/PropertyInt16=365,PropertyString=test");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.NOT_FOUND.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getErrorStream());
		assertTrue(content.contains("\"message\":\"Nothing found.\""));
	}
	
	@Test
	public void testRestFlowEntityWithCompoundPropoertiesAndNavigation() throws Exception {
		URL url = new URL(SERVICE_URI + "ESTwoKeyNavCont/PropertyInt16=365,PropertyString=test/"
				+ "NavPropertyETTwoKeyNavContMany");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.NOT_FOUND.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getErrorStream());
		assertTrue(content.contains("\"message\":\"Nothing found.\""));
	}
	
	@Test
	public void testRestFlowEntityWithCompoundPropoertiesAndNavigationWithCompoundProperties() 
			throws Exception {
		URL url = new URL(SERVICE_URI + "ESTwoKeyNavCont/PropertyInt16=365,PropertyString=test"
				+ "/NavPropertyETTwoKeyNavContMany/PropertyInt16=365,PropertyString=test");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.NOT_FOUND.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getErrorStream());
		assertTrue(content.contains("\"message\":\"Nothing found.\""));
	}
	
	@Test
	public void testRestFlowEntityWithCompoundPropoertiesOfAllDataTypes() throws Exception {
		URL url = new URL(SERVICE_URI + "ESAllKey/PropertyString=First,PropertyBoolean=true,"
				+ "PropertyByte=255,PropertySByte=127,PropertyInt16=32767,PropertyInt32=2147483647,"
				+ "PropertyInt64=9223372036854775807,PropertyDecimal=34,PropertyDate=2012-12-03,"
				+ "PropertyDateTimeOffset=2012-12-03T07:16:23Z,PropertyDuration=duration'PT6S',"
				+ "PropertyGuid=01234567-89ab-cdef-0123-456789abcdef,PropertyTimeOfDay=02:48:21");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.OK.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getInputStream());
		assertTrue(content.contains("\"PropertyString\":\"First\""));
		assertTrue(content.contains("\"PropertyBoolean\":true"));
		assertTrue(content.contains("\"PropertyByte\":255"));
		assertTrue(content.contains("\"PropertySByte\":127"));
		assertTrue(content.contains("\"PropertyInt16\":32767"));
		assertTrue(content.contains("\"PropertyInt32\":2147483647"));
		assertTrue(content.contains("\"PropertyInt64\":9223372036854775807"));
		assertTrue(content.contains("\"PropertyDecimal\":34"));
		assertTrue(content.contains("\"PropertyDate\":\"2012-12-03\""));
		assertTrue(content.contains("\"PropertyDateTimeOffset\":\"2012-12-03T07:16:23Z\""));
		assertTrue(content.contains("\"PropertyDuration\":\"PT6S\""));
		assertTrue(content.contains("\"PropertyGuid\":\"01234567-89ab-cdef-0123-456789abcdef\""));
		assertTrue(content.contains("\"PropertyTimeOfDay\":\"02:48:21\""));
	}
	
	@Test
	public void testRestFlowEntityWithInvalidProperty() throws Exception {
		URL url = new URL(SERVICE_URI + "ESTwoKeyNavCont/PropertyInt16=365,PropertyString"
				+ "=test/NavPropertyETTwoKeyNavContMany/365");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.BAD_REQUEST.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getErrorStream());
		assertTrue(content.contains("\"message\":\"The key property '' is invalid.\""));
	}
	
	@Test
	public void testRestFlowEntityWithInvalidNumeberofProperties() throws Exception {
		URL url = new URL(SERVICE_URI + "ESTwoKeyNavCont/PropertyInt16=365,PropertyString"
				+ "=test/NavPropertyETTwoKeyNavContMany/PropertyInt16=365");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.BAD_REQUEST.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getErrorStream());
		assertTrue(content.contains("\"message\":\"There are 1 key properties instead of the expected 2.\""));
	}
	
	@Test
	public void testRestFlowWithSpecialCharectersinStringPropoerty() throws Exception {
		URL url = new URL(SERVICE_URI + "ESAllKey/PropertyString=%$First,PropertyBoolean=true,"
				+ "PropertyByte=255,PropertySByte=127,PropertyInt16=32767,PropertyInt32=2147483647,"
				+ "PropertyInt64=9223372036854775807,PropertyDecimal=34,PropertyDate=2012-12-03,"
				+ "PropertyDateTimeOffset=2012-12-03T07:16:23Z,PropertyDuration=duration'PT6S',"
				+ "PropertyTimeOfDay=02:48:21,PropertyGuid=01234567-89ab-cdef-0123-456789abcdef");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.BAD_REQUEST.getStatusCode(), connection.getResponseCode());
	}
	
	@Test
	public void testRestFlowWithOutprotocolTypeAttribute() throws Exception {
		URL url = new URL(SERVICE_URI + "ESAllPrim/32767");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		//connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.BAD_REQUEST.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getErrorStream());
		assertTrue(content.contains("\"message\":\"The URI is malformed.\""));
	}
	
	@Test
	public void testRestFlowEntityWithPrimitivePropoertyAndAcceptHeaderMetadataNone() throws Exception {
		URL url = new URL(SERVICE_URI + "ESAllPrim/32767");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=none");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.OK.getStatusCode(), connection.getResponseCode());

		final String content = IOUtils.toString(connection.getInputStream());
		assertTrue(content.contains("\"PropertyInt16\":32767"));
		assertTrue(content.contains("\"PropertyString\":\"First Resource - positive values\""));
	}
	
	@Test
	public void testRestFlowEntityWithPrimitivePropoertyAndAcceptHeaderMetadataMonimal() throws Exception {
		URL url = new URL(SERVICE_URI + "ESAllPrim/32767");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=minimal");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.OK.getStatusCode(), connection.getResponseCode());

		final String content = IOUtils.toString(connection.getInputStream());
		assertTrue(content.contains("\"PropertyInt16\":32767"));
		assertTrue(content.contains("\"PropertyString\":\"First Resource - positive values\""));
	}
	
	@Test
	public void testRestFlowWithOdataFlowCombination1() throws Exception {
		URL url = new URL(SERVICE_URI + "ESAllPrim(32767)/NavPropertyETTwoPrimMany/-365");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.connect();

		assertEquals(HttpStatusCode.BAD_REQUEST.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getErrorStream());
		assertTrue(content.contains("\"message\":\"The URI is malformed.\""));
	}
	
	@Test
	public void testRestFlowWithOdataFlowCombination2() throws Exception {
		URL url = new URL(SERVICE_URI + "ESAllPrim/32767/NavPropertyETTwoPrimMany(-365)");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.connect();

		assertEquals(HttpStatusCode.BAD_REQUEST.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getErrorStream());
		assertTrue(content.contains("\"message\":\"The URI is malformed.\""));
	}
	
	@Test
	public void testRestFlowWithDifferentPropertyOrder() throws Exception {
		URL url = new URL(SERVICE_URI + "ESAllKey/PropertyString=First,PropertyBoolean=true,"
				+ "PropertySByte=127,PropertyByte=255,PropertyInt16=32767,PropertyInt32=2147483647,"
				+ "PropertyInt64=9223372036854775807,PropertyDecimal=34,PropertyDate=2012-12-03,"
				+ "PropertyDateTimeOffset=2012-12-03T07:16:23Z,PropertyDuration=duration'PT6S',"
				+ "PropertyTimeOfDay=02:48:21,PropertyGuid=01234567-89ab-cdef-0123-456789abcdef");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.OK.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getInputStream());
		assertTrue(content.contains("\"PropertyString\":\"First\""));
		assertTrue(content.contains("\"PropertyBoolean\":true"));
	}
	
	@Test
	public void testRestFlowWithKeyWithAliasNames() throws Exception {
		URL url = new URL(SERVICE_URI + "ESFourKeyAlias/PropertyInt16=1,KeyAlias1=11,"
				+ "KeyAlias2=Num11,KeyAlias3=Num111");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.OK.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getInputStream());
		assertTrue(content.contains("\"PropertyInt16\":11"));
		assertTrue(content.contains("\"PropertyString\":\"Num11\""));
		
	}
	
	@Test
	public void testRestFlowEOFFollowedyEntitySet() throws Exception {
		URL url = new URL(SERVICE_URI + "ESFourKeyAlias/");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.BAD_REQUEST.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getErrorStream());
		assertTrue(content.contains("\"message\":\"The URI is malformed.\""));
		
	}
	
	@Test
	public void testRestFlowAndOdataFlowEntitySet() throws Exception {
		URL url = new URL(SERVICE_URI + "ESTwoKeyNavCont(365)");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.BAD_REQUEST.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getErrorStream());
		assertTrue(content.contains("\"message\":\"The URI is malformed.\""));
		
	}
	
	@Test
	public void testRestFlowAndOdataFlowNavigationSet() throws Exception {
		URL url = new URL(SERVICE_URI + "ESAllPrim/PropertyInt16=32767/"
				+ "NavPropertyETTwoPrimMany(PropertyInt16=-365)");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty(HttpHeader.ACCEPT, "application/json;odata.metadata=full");
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.BAD_REQUEST.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getErrorStream());
		assertTrue(content.contains("\"message\":\"The URI is malformed.\""));
		
	}
	
	@Test
	public void testRestFlowEntitySetwithMoreProperties() throws Exception {
		URL url = new URL(SERVICE_URI + "ESAllPrim/PropertyInt16=32767,PropertyString='abc'");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.BAD_REQUEST.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getErrorStream());
		assertTrue(content.contains("\"message\":\"There are 2 key properties instead of the expected 1.\""));
		
	}
	
	@Test
	public void testRestFlowEntitySetwithLessProperties() throws Exception {
		URL url = new URL(SERVICE_URI + "ESTwoKeyNavCont/PropertyInt16=365");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.BAD_REQUEST.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getErrorStream());
		assertTrue(content.contains("\"message\":\"There are 1 key properties instead of the expected 2.\""));
		
	}
	
	@Test
	public void testRestFlowEntitySetWithNavWithMoreProperties() throws Exception {
		URL url = new URL(SERVICE_URI + "ESAllPrim/PropertyInt16=32767/NavPropertyETTwoPrimMany"
				+ "/PropertyInt16=-365,PropertyString='abc'");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.BAD_REQUEST.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getErrorStream());
		assertTrue(content.contains("\"message\":\"There are 2 key properties instead of the expected 1.\""));
		
	}
	
	@Test
	public void testRestFlowEntitySetWithNavWithProperties() throws Exception {
		URL url = new URL(SERVICE_URI + "ESTwoKeyNavCont/PropertyInt16=365,PropertyString=test"
				+ "/NavPropertyETTwoKeyNavContMany/PropertyInt16=365");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethod.GET.name());
		connection.setRequestProperty("protocolType", "Rest");
		connection.connect();

		assertEquals(HttpStatusCode.BAD_REQUEST.getStatusCode(), connection.getResponseCode());
		final String content = IOUtils.toString(connection.getErrorStream());
		assertTrue(content.contains("\"message\":\"There are 1 key properties instead of the expected 2.\""));
		
	}
	
	@Override
	protected ODataClient getClient() {
		return null;
	}

}
