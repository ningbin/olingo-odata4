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
package org.apache.olingo.commons.core.edm.termtype;

import org.apache.olingo.commons.api.edm.EdmTermType;
import org.apache.olingo.commons.api.edm.EdmTermTypeKind;

public final class EdmTermTypeFactory {

  /**
   * Returns an instance for the provided {@link EdmTermTypeKind} in the form of {@link EdmTermType}.
   *
   * @param kind EdmTermTypeKind
   * @return {@link EdmTermType} instance
   */
  public static EdmTermType getInstance(final EdmTermTypeKind kind) {
    switch (kind) {
    case PropertyPath:
      return EdmPropertyPath.getInstance();
    case NavigationPropertyPath:
      return EdmNavigationPropertyPath.getInstance();
    case AnnotationPath:
      return EdmAnnotationPath.getInstance();
    case ModelElementPath:
    	return EdmModelElementPath.getInstance();
    case AnyPropertyPath:
    	return EdmAnyPropertyPath.getInstance();
    default:
      throw new IllegalArgumentException("Wrong type: " + kind);
    }
  }

  private EdmTermTypeFactory() {
    // empty constructor for static utility class
  }

}
