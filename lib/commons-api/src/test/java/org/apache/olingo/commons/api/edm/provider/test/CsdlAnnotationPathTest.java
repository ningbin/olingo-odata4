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

import org.apache.olingo.commons.api.edm.provider.annotation.CsdlAnnotationPath;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlLabeledElement;
import org.junit.Test;

public class CsdlAnnotationPathTest {

  @Test
  public void compareCsdlAnnotationPath() {
    CsdlAnnotationPath path1 = new CsdlAnnotationPath().setValue("value");
    CsdlAnnotationPath path2 = new CsdlAnnotationPath().setValue("value");
    assertTrue(path1.equals(path2));
  }
  
  @Test
  public void negCompareCsdlAnnotationPathWithDiffValues() {
    CsdlAnnotationPath path1 = new CsdlAnnotationPath().setValue("value1");
    CsdlAnnotationPath path2 = new CsdlAnnotationPath().setValue("value2");
    assertFalse(path1.equals(path2));
  }
  
  @Test
  public void negCompareWithNullCsdlAnnotationPath() {
    CsdlAnnotationPath path1 = new CsdlAnnotationPath().setValue("value1");
    CsdlAnnotationPath path2 = null;
    assertFalse(path1.equals(path2));
  }
  
  @Test
  public void negCompareWithrongInstance() {
    CsdlAnnotationPath path1 = new CsdlAnnotationPath().setValue("value1");
    CsdlLabeledElement labelledEle = new CsdlLabeledElement().setName("value1");
    assertFalse(path1.equals(labelledEle));
  }
  
  @Test
  public void compareWithFirstValueNull() {
    CsdlAnnotationPath path1 = new CsdlAnnotationPath().setValue(null);
    CsdlAnnotationPath path2 = new CsdlAnnotationPath().setValue("value2");
    assertFalse(path1.equals(path2));
  }
  
  @Test
  public void compareWithBothValueNull() {
    CsdlAnnotationPath path1 = new CsdlAnnotationPath().setValue(null);
    CsdlAnnotationPath path2 = new CsdlAnnotationPath().setValue(null);
    assertTrue(path1.equals(path2));
  }
  
  @Test
  public void compareWithSecondValueNull() {
    CsdlAnnotationPath path1 = new CsdlAnnotationPath().setValue("value");
    CsdlAnnotationPath path2 = new CsdlAnnotationPath().setValue(null);
    assertFalse(path1.equals(path2));
    assertNotNull(path1.toString());
    assertNotNull(path2.toString());
  }
}
