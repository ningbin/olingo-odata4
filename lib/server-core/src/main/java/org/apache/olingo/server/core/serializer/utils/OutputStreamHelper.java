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
package org.apache.olingo.server.core.serializer.utils;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.olingo.server.api.serializer.SerializerException;

public class OutputStreamHelper {

  public static final String IO_EXCEPTION_TEXT = "An I/O exception occurred.";

  public static void closeCircleStreamBufferOutput(final OutputStream outputStream,
      final SerializerException cachedException)
      throws SerializerException {
    if (outputStream != null) {
      try {
        outputStream.close();
      } catch (IOException e) {
        if (cachedException != null) {
          throw cachedException;
        } else {
          throw new SerializerException(IO_EXCEPTION_TEXT, e,
              SerializerException.MessageKeys.IO_EXCEPTION);
        }
      }
    }
  }

}
