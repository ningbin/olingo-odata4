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

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.rx.api.MediaEntityObservable;
import org.apache.olingo.server.rx.serializer.json.ODataRxMediaSerializer;
import org.junit.Test;

import io.reactivex.Observable;

public class MediaSerializerTest {

	@SuppressWarnings("deprecation")
	@Test
	public void mediaStreaming() throws SerializerException, IOException {
		byte[] mediaData = "This is the media entity".getBytes();
		MediaEntityObservable observable = new MediaEntityObservable() {
			Observable<byte[]> observable = Observable.fromArray(mediaData);
			@Override
			public Observable<byte[]> getObservable() {
				return observable;
			}
		};
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		new ODataRxMediaSerializer().binaryIntoStreamed(observable, outputStream);
		assertNotNull(outputStream);
		assertEquals(mediaData.length, outputStream.toByteArray().length);
		assertEquals("This is the media entity", IOUtils.toString(outputStream.toByteArray()));
	}
}
