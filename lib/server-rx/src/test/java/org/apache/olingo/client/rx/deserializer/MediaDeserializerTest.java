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
package org.apache.olingo.client.rx.deserializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.commons.api.data.EntityMediaObject;
import org.apache.olingo.server.api.deserializer.DeserializerException;
import org.apache.olingo.server.rx.api.MediaEntityObservable;
import org.apache.olingo.server.rx.deserializer.json.ODataJsonRxDeserializer;
import org.junit.Test;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MediaDeserializerTest {

	@SuppressWarnings("unchecked")
	@Test
	public void binaryIntoStream() throws DeserializerException, IOException {
		InputStream in = new ByteArrayInputStream("This is a test".getBytes());
		EntityMediaObject entityMedia = new ODataJsonRxDeserializer().binaryIntoStream(in);
		final List<DeserializerException> ex = new ArrayList<>();
		List<byte[]> byteList = new ArrayList<>();
		
		((MediaEntityObservable)entityMedia).getObservable().subscribe(new Observer<byte[]>() {
			byte[] data = new byte[0];
			
	        @Override
	        public void onError(Throwable e) {
	          ex.add(new DeserializerException(e.getMessage(), e,
	        		  DeserializerException.MessageKeys.IO_EXCEPTION));
	        }

	        @Override
	        public void onNext(byte[] bytes) {
	        	ByteBuffer bb = ByteBuffer.allocate(data.length + bytes.length);
	        	bb.put(data);
	        	bb.put(bytes);
	        	data = bb.array();
	        }

	        @Override
	        public void onSubscribe(Disposable d) {
	          // Provides the Observer 
	          // with the means of cancelling the connection with the Observable.

	        }

	        @Override
	        public void onComplete() {
	        	byteList.add(data);
	        }
	      });
		if (!ex.isEmpty()) {
			assertNotNull(ex.get(0));
	      }
		assertEquals("This is a test".getBytes().length, byteList.get(0).length);
		assertEquals("This is a test", new String(byteList.get(0), "UTF-8"));
	}
}
