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
package org.apache.olingo.server.rx.deserializer.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.olingo.commons.api.data.EntityMediaObject;
import org.apache.olingo.server.api.deserializer.DeserializerException;
import org.apache.olingo.server.core.deserializer.FixedFormatDeserializerImpl;
import org.apache.olingo.server.rx.api.MediaEntityObservable;

import io.reactivex.Observable;

public class ODataRxMediaDeserializer extends FixedFormatDeserializerImpl {
	
	private static final int DEFAULT_BUFFER_SIZE = 128;
	
	  @Override
	  public EntityMediaObject binaryIntoStream(InputStream content) throws DeserializerException {
		  byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		  MediaEntityObservable mediaObservable = new MediaEntityObservable() {
		    	Observable<byte[]> observable = Observable.create(subscriber -> {
		  		  try {
		  			int count = 0;
		  			  do {
			              count = content.read(buffer);
			              if (count == -1) {
			            	  subscriber.onComplete();
			              } else {
			            	  byte[] tempBuffer = Arrays.copyOf(buffer, count);
			            	  subscriber.onNext(tempBuffer);
			              }
		  			  } while(count > 0);
		          } catch (IOException e) {
		        	  subscriber.onError(e);
		          }
		  	  });
				
				@Override
				public Observable<byte[]> getObservable() {
					return observable;
				}
			};
			return mediaObservable;
	  }
}
