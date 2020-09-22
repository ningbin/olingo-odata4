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
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.constants.EdmTypeKind;

/**
 * Abstract singleton implementation of the EDM term-type interface.
 */
public abstract class SingletonTermType implements EdmTermType {

  protected String name;
  
  @Override
  public FullQualifiedName getFullQualifiedName() {
    return new FullQualifiedName(getNamespace(), getName());
  }

  @Override
  public boolean isCompatible(final EdmTermType termType) {
    return equals(termType);
  }

  @Override
  public boolean equals(final Object obj) {
    return this == obj || obj != null && getClass() == obj.getClass();
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public String getNamespace() {
    return EDM_NAMESPACE;
  }

  @Override
  public String getName() {
    if(name == null) {
      name = getClass().getSimpleName().substring(3);
    }
    return name;
  }

  @Override
  public EdmTypeKind getKind() {
    return EdmTypeKind.TERM;
  }
}
