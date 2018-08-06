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

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.commons.api.edm.provider.CsdlAnnotation;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression.ConstantExpressionType;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlLabeledElement;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPath;
import org.junit.Test;

public class CsdlLabelledElementTest {

  @Test
  public void compareCsdlLabelledEle() {
    CsdlLabeledElement csdlLabeledElement1 = new CsdlLabeledElement();
    csdlLabeledElement1.setName("name");
    csdlLabeledElement1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlLabeledElement1.setAnnotations(csdlAnnotations);
    
    CsdlLabeledElement csdlLabeledElement2 = new CsdlLabeledElement();
    csdlLabeledElement2.setName("name");
    csdlLabeledElement2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlLabeledElement2.setAnnotations(csdlAnnotations);
    
    assertTrue(csdlLabeledElement1.equals(csdlLabeledElement2));
    assertNotNull(csdlLabeledElement1.toString());
  }
  
  @Test
  public void compareCsdlLabelledEleWithEmptyAnnot() {
    CsdlLabeledElement csdlLabeledElement1 = new CsdlLabeledElement();
    csdlLabeledElement1.setName("name");
    csdlLabeledElement1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    
    CsdlLabeledElement csdlLabeledElement2 = new CsdlLabeledElement();
    csdlLabeledElement2.setName("name");
    csdlLabeledElement2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    
    assertTrue(csdlLabeledElement1.equals(csdlLabeledElement2));
    assertNotNull(csdlLabeledElement1.toString());
  }
  
  @Test
  public void negCompareCsdlLabelledEleWithNullObj() {
    CsdlLabeledElement csdlLabeledElement1 = new CsdlLabeledElement();
    csdlLabeledElement1.setName("name");
    csdlLabeledElement1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlLabeledElement1.setAnnotations(csdlAnnotations);
    
    CsdlLabeledElement csdlLabeledElement2 = null;
    
    assertFalse(csdlLabeledElement1.equals(csdlLabeledElement2));
  }
  
  @Test
  public void negCompareCsdlLabelledEleWithDiffInstane() {
    CsdlLabeledElement csdlLabeledElement1 = new CsdlLabeledElement();
    csdlLabeledElement1.setName("name");
    csdlLabeledElement1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlLabeledElement1.setAnnotations(csdlAnnotations);
    
    CsdlPath path = new CsdlPath();
    assertFalse(csdlLabeledElement1.equals(path));
  }
  
  @Test
  public void negCompareCsdlLabelledEleWithDiffAnnot() {
    CsdlLabeledElement csdlLabeledElement1 = new CsdlLabeledElement();
    csdlLabeledElement1.setName("name");
    csdlLabeledElement1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlLabeledElement1.setAnnotations(csdlAnnotations);
    
    CsdlLabeledElement csdlLabeledElement2 = new CsdlLabeledElement();
    csdlLabeledElement2.setName("name");
    csdlLabeledElement2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlLabeledElement2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlLabeledElement1.equals(csdlLabeledElement2));
  }
  
  @Test
  public void negCompareCsdlLabelledEleWithDiffAnnotSize() {
    CsdlLabeledElement csdlLabeledElement1 = new CsdlLabeledElement();
    csdlLabeledElement1.setName("name");
    csdlLabeledElement1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlLabeledElement1.setAnnotations(csdlAnnotations);
    
    CsdlLabeledElement csdlLabeledElement2 = new CsdlLabeledElement();
    csdlLabeledElement2.setName("name");
    csdlLabeledElement2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlLabeledElement2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlLabeledElement1.equals(csdlLabeledElement2));
  }
  
  @Test
  public void negCompareCsdlLabelledEleWithNullAnnot() {
    CsdlLabeledElement csdlLabeledElement1 = new CsdlLabeledElement();
    csdlLabeledElement1.setName("name");
    csdlLabeledElement1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlLabeledElement1.setAnnotations(null);
    
    CsdlLabeledElement csdlLabeledElement2 = new CsdlLabeledElement();
    csdlLabeledElement2.setName("name");
    csdlLabeledElement2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlLabeledElement2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlLabeledElement1.equals(csdlLabeledElement2));
    assertNotNull(csdlLabeledElement1.toString());
  }
  
  @Test
  public void negCompareCsdlLabelledEleWithOtherAnnotNull() {
    CsdlLabeledElement csdlLabeledElement1 = new CsdlLabeledElement();
    csdlLabeledElement1.setName("name");
    csdlLabeledElement1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlLabeledElement1.setAnnotations(csdlAnnotations);
    
    CsdlLabeledElement csdlLabeledElement2 = new CsdlLabeledElement();
    csdlLabeledElement2.setName("name");
    csdlLabeledElement2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlLabeledElement2.setAnnotations(null);
    
    assertFalse(csdlLabeledElement1.equals(csdlLabeledElement2));
    assertNotNull(csdlLabeledElement2.toString());
  }
  
  @Test
  public void negCompareCsdlLabelledEleWithAllNulls() {
    CsdlLabeledElement csdlLabeledElement1 = new CsdlLabeledElement();
    csdlLabeledElement1.setName(null);
    csdlLabeledElement1.setValue(null);
    csdlLabeledElement1.setAnnotations(null);
    
    CsdlLabeledElement csdlLabeledElement2 = new CsdlLabeledElement();
    csdlLabeledElement2.setName(null);
    csdlLabeledElement2.setValue(null);
    csdlLabeledElement2.setAnnotations(null);
    
    assertTrue(csdlLabeledElement1.equals(csdlLabeledElement2));
  }
  
  @Test
  public void negCompareCsdlLabelledEleWithNameNull() {
    CsdlLabeledElement csdlLabeledElement1 = new CsdlLabeledElement();
    csdlLabeledElement1.setName(null);
    csdlLabeledElement1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlLabeledElement1.setAnnotations(csdlAnnotations);
    
    CsdlLabeledElement csdlLabeledElement2 = new CsdlLabeledElement();
    csdlLabeledElement2.setName("name");
    csdlLabeledElement2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlLabeledElement2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlLabeledElement1.equals(csdlLabeledElement2));
    assertNotNull(csdlLabeledElement1.toString());
  }
  
  @Test
  public void negCompareCsdlLabelledEleWithOtherNameNull() {
    CsdlLabeledElement csdlLabeledElement1 = new CsdlLabeledElement();
    csdlLabeledElement1.setName("name");
    csdlLabeledElement1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlLabeledElement1.setAnnotations(csdlAnnotations);
    
    CsdlLabeledElement csdlLabeledElement2 = new CsdlLabeledElement();
    csdlLabeledElement2.setName(null);
    csdlLabeledElement2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlLabeledElement2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlLabeledElement1.equals(csdlLabeledElement2));
  }
  
  @Test
  public void negCompareCsdlLabelledEleWithValueNull() {
    CsdlLabeledElement csdlLabeledElement1 = new CsdlLabeledElement();
    csdlLabeledElement1.setName(null);
    csdlLabeledElement1.setValue(null);
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlLabeledElement1.setAnnotations(csdlAnnotations);
    
    CsdlLabeledElement csdlLabeledElement2 = new CsdlLabeledElement();
    csdlLabeledElement2.setName(null);
    csdlLabeledElement2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlLabeledElement2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlLabeledElement1.equals(csdlLabeledElement2));
    assertNotNull(csdlLabeledElement1.toString());
  }
  
  @Test
  public void negCompareCsdlLabelledEleWithOtherValueNull() {
    CsdlLabeledElement csdlLabeledElement1 = new CsdlLabeledElement();
    csdlLabeledElement1.setName(null);
    csdlLabeledElement1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlLabeledElement1.setAnnotations(csdlAnnotations);
    
    CsdlLabeledElement csdlLabeledElement2 = new CsdlLabeledElement();
    csdlLabeledElement2.setName(null);
    csdlLabeledElement2.setValue(null);
    csdlLabeledElement2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlLabeledElement1.equals(csdlLabeledElement2));
  }
}
