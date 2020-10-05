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
package org.apache.olingo.server.api.uri.queryoption;

/**
 * Represents a single transformation from the system query option $apply.
 */
public interface ApplyItem {

  /** The kind of the transformation. */
  public enum Kind {
    AGGREGATE,
    BOTTOM_TOP,
    COMPUTE,
    CONCAT,
    CUSTOM_FUNCTION,
    EXPAND,
    FILTER,
    GROUP_BY,
    IDENTITY,
    SEARCH,
    ORDERBY,
    TOP,
    SKIP
  }

  /**
   * Gets the kind of the transformation.
   * @return transformation kind
   */
  Kind getKind();
}
