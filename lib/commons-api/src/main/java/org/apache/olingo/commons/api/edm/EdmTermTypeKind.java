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
package org.apache.olingo.commons.api.edm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum EdmTermTypeKind {

	AnnotationPath,
	PropertyPath,
	NavigationPropertyPath,
	AnyPropertyPath,
	ModelElementPath;
	
	private static Map<String, EdmTermTypeKind> VALUES_BY_NAME;

	  static {
	    Map<String, EdmTermTypeKind> valuesByName = new HashMap<>();
	    for (EdmTermTypeKind value : values()) {
	      valuesByName.put(value.name(), value);
	    }
	    VALUES_BY_NAME = Collections.unmodifiableMap(valuesByName);
	  }
	  
	  /**
	   * Get a type kind by name.
	   *
	   * @param name The name.
	   * @return The type kind or <tt>null</tt> if it does not exist.
	   */
	  public static EdmTermTypeKind getByName(String name) {
	    return VALUES_BY_NAME.get(name);
	  }
	  
	  /**
	   * Returns the {@link FullQualifiedName} for this type kind.
	   *
	   * @return {@link FullQualifiedName}
	   */
	  public FullQualifiedName getFullQualifiedName() {
	    return new FullQualifiedName(EdmTermType.EDM_NAMESPACE, toString());
	  }
	  
	  /**
	   * Gets the {@link EdmPrimitiveTypeKind} from a full-qualified type name.
	   * @param fqn full-qualified type name
	   * @return {@link EdmTermTypeKind} object
	   */
	  public static EdmTermTypeKind valueOfFQN(final FullQualifiedName fqn) {
	    if (EdmTermType.EDM_NAMESPACE.equals(fqn.getNamespace())) {
	      return valueOf(fqn.getName());
	    } else {
	      throw new IllegalArgumentException(fqn + " does not look like an EDM primitive type.");
	    }
	  }
	  
	  /**
	   * Gets the {@link EdmTermTypeKind} from a full type expression (like <code>Edm.Int32</code>).
	   * @param fqn String containing a full-qualified type name
	   * @return {@link EdmTermTypeKind} object
	   */
	  public static EdmTermTypeKind valueOfFQN(final String fqn) {
	    if (!fqn.startsWith(EdmTermType.EDM_NAMESPACE + ".")) {
	      throw new IllegalArgumentException(fqn + " does not look like an Edm primitive type");
	    }

	    return valueOf(fqn.substring(4));
	  }
}
