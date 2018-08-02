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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.olingo.commons.api.edm.provider.annotation.CsdlLabeledElement;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPropertyPath;
import org.junit.Test;

public class CsdlPropertyPathTest {
  
  @Test
  public void compareCsdlPropertyPath() {
    CsdlPropertyPath path1 = new CsdlPropertyPath().setValue("value");
    CsdlPropertyPath path2 = new CsdlPropertyPath().setValue("value");
    assertTrue(path1.equals(path2));
  }
  
  @Test
  public void negCompareCsdlPropertyPathWithDiffValues() {
    CsdlPropertyPath path1 = new CsdlPropertyPath().setValue("value1");
    CsdlPropertyPath path2 = new CsdlPropertyPath().setValue("value2");
    assertFalse(path1.equals(path2));
  }
  
  @Test
  public void negCompareWithNullCsdlPropertyPath() {
    CsdlPropertyPath path1 = new CsdlPropertyPath().setValue("value1");
    CsdlPropertyPath path2 = null;
    assertFalse(path1.equals(path2));
  }
  
  @Test
  public void negCompareWithrongInstance() {
    CsdlPropertyPath path1 = new CsdlPropertyPath().setValue("value1");
    CsdlLabeledElement labelledEle = new CsdlLabeledElement().setName("value1");
    assertFalse(path1.equals(labelledEle));
  }
  
  @Test
  public void compareWithFirstValueNull() {
    CsdlPropertyPath path1 = new CsdlPropertyPath().setValue(null);
    CsdlPropertyPath path2 = new CsdlPropertyPath().setValue("value2");
    assertFalse(path1.equals(path2));
  }
  
  @Test
  public void compareWithBothValueNull() {
    CsdlPropertyPath path1 = new CsdlPropertyPath().setValue(null);
    CsdlPropertyPath path2 = new CsdlPropertyPath().setValue(null);
    assertTrue(path1.equals(path2));
  }
  
  @Test
  public void compareWithSecondValueNull() {
    CsdlPropertyPath path1 = new CsdlPropertyPath().setValue("value");
    CsdlPropertyPath path2 = new CsdlPropertyPath().setValue(null);
    assertFalse(path1.equals(path2));
  }
}
