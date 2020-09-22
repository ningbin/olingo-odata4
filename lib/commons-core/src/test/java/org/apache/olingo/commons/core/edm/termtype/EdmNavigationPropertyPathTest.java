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

import static org.junit.Assert.*;

import org.apache.olingo.commons.api.edm.EdmTermType;
import org.apache.olingo.commons.api.edm.EdmTermTypeKind;
import org.apache.olingo.commons.api.edm.constants.EdmTypeKind;
import org.junit.Test;

public class EdmNavigationPropertyPathTest {
	private final EdmTermType instance = EdmTermTypeFactory.getInstance(EdmTermTypeKind.NavigationPropertyPath);

	  @Test
	  public void testEdmNavigationPropertyPath() throws Exception {
		  assertNotNull(EdmNavigationPropertyPath.getInstance());
		  assertEquals("Edm.NavigationPropertyPath", instance.getFullQualifiedName()
				  .getFullQualifiedNameAsString());
		  assertEquals(EdmTypeKind.TERM.name(), instance.getKind().name());
		  assertEquals("NavigationPropertyPath", instance.getName());
		  assertEquals("Edm", instance.getNamespace());
		  assertTrue(instance.isCompatible(EdmNavigationPropertyPath.getInstance()));
		  assertFalse(instance.isCompatible(EdmPropertyPath.getInstance()));
	  }
}
