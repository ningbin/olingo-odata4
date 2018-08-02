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

import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPath;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPropertyPath;
import org.junit.Test;

public class CsdlPathTest {

  @Test
  public void compareCsdlPath() {
    CsdlPath csdlPath1 = new CsdlPath();
    csdlPath1.setValue("value1");
    
    CsdlPath csdlPath2 = new CsdlPath();
    csdlPath2.setValue("value1");
    
    assertTrue(csdlPath1.equals(csdlPath2));
  }
  
  @Test
  public void negCompareCsdlPathWithNullValue() {
    CsdlPath csdlPath1 = new CsdlPath();
    csdlPath1.setValue(null);
    
    CsdlPath csdlPath2 = new CsdlPath();
    csdlPath2.setValue("value1");
    
    assertFalse(csdlPath1.equals(csdlPath2));
  }
  
  @Test
  public void negCompareCsdlPathWithOtherNullValue() {
    CsdlPath csdlPath1 = new CsdlPath();
    csdlPath1.setValue("value");
    
    CsdlPath csdlPath2 = new CsdlPath();
    csdlPath2.setValue(null);
    
    assertFalse(csdlPath1.equals(csdlPath2));
  }
  
  @Test
  public void negCompareCsdlPathWithBothNullValue() {
    CsdlPath csdlPath1 = new CsdlPath();
    csdlPath1.setValue(null);
    
    CsdlPath csdlPath2 = new CsdlPath();
    csdlPath2.setValue(null);
    
    assertTrue(csdlPath1.equals(csdlPath2));
  }
  
  @Test
  public void negCompareCsdlPathWithOtherInstance() {
    CsdlPath csdlPath1 = new CsdlPath();
    csdlPath1.setValue("value");
    
    CsdlPropertyPath csdlPath2 = new CsdlPropertyPath();
    
    assertFalse(csdlPath1.equals(csdlPath2));
  }
  
  @Test
  public void negCompareCsdlPathWithNullObj() {
    CsdlPath csdlPath1 = new CsdlPath();
    csdlPath1.setValue("value");
    
    CsdlPath csdlPath2 = null;
    
    assertFalse(csdlPath1.equals(csdlPath2));
  }
}
