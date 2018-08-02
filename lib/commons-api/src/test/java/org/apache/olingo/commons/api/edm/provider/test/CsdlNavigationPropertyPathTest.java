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
package org.apache.olingo.commons.api.edm.provider.test;

import static org.junit.Assert.*;

import org.apache.olingo.commons.api.edm.provider.annotation.CsdlNavigationPropertyPath;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPropertyPath;
import org.junit.Test;

public class CsdlNavigationPropertyPathTest {

  @Test
  public void compareCsdlNavigationPropertyPath() {
    CsdlNavigationPropertyPath exp1 = new CsdlNavigationPropertyPath();
    exp1.setValue("value1");
    
    CsdlNavigationPropertyPath exp2 = new CsdlNavigationPropertyPath();
    exp2.setValue("value1");
    
    assertTrue(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareCsdlNavigationPropertyPathWithNullValue() {
    CsdlNavigationPropertyPath exp1 = new CsdlNavigationPropertyPath();
    exp1.setValue(null);
    
    CsdlNavigationPropertyPath exp2 = new CsdlNavigationPropertyPath();
    exp2.setValue("value1");
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareCsdlNavigationPropertyPathWithOtherNullValue() {
    CsdlNavigationPropertyPath exp1 = new CsdlNavigationPropertyPath();
    exp1.setValue("value1");
    
    CsdlNavigationPropertyPath exp2 = new CsdlNavigationPropertyPath();
    exp2.setValue(null);
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareCsdlNavigationPropertyPathWithNullObj() {
    CsdlNavigationPropertyPath exp1 = new CsdlNavigationPropertyPath();
    exp1.setValue("value1");
    
    CsdlNavigationPropertyPath exp2 = null;
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareCsdlNavigationPropertyPathWithOtherInstance() {
    CsdlNavigationPropertyPath exp1 = new CsdlNavigationPropertyPath();
    exp1.setValue("value1");
    
    CsdlPropertyPath exp2 = new CsdlPropertyPath();
    
    assertFalse(exp1.equals(exp2));
  }
}
