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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.commons.api.data.EntityMediaObject;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.core.serializer.FixedFormatSerializerImpl;
import org.apache.olingo.server.rx.api.MediaEntityObservable;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ODataRxMediaSerializer extends FixedFormatSerializerImpl {

	  @SuppressWarnings("unchecked")
	  @Override
	protected void binary(final EntityMediaObject entityMedia, 
			OutputStream outputStream) throws SerializerException {
		final List<SerializerException> ex = new ArrayList<>();
		((MediaEntityObservable) entityMedia).getObservable().subscribe(new Observer<byte[]>() {
			
	        @Override
	        public void onError(Throwable e) {
	          ex.add(new SerializerException(e.getMessage(), e,
	              SerializerException.MessageKeys.IO_EXCEPTION));
	        }

	        @Override
	        public void onNext(byte[] bytes) {
	        	try {
					outputStream.write(bytes);
				} catch (IOException e) {
					ex.add(new SerializerException(e.getMessage(), e,
			                SerializerException.MessageKeys.IO_EXCEPTION));
				}
	        }

	        @Override
	        public void onSubscribe(Disposable d) {
	          // Provides the Observer with the means of cancelling the connection with the Observable.

	        }

	        @Override
	        public void onComplete() {
	          // Notifies the Observer that the Observable has finished sending push-based notifications
	          // No task to be performed on Complete
	        }
	      });
		if (!ex.isEmpty()) {
	        throw ex.get(0);
	      }
	  }
}
