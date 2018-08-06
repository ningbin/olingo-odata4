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
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPropertyPath;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPropertyValue;
import org.junit.Test;

public class CsdlPropertyValueTest {

  @Test
  public void compareCsdlPropertyValue() {
    CsdlPropertyValue csdlPropertyValue1 = new CsdlPropertyValue();
    csdlPropertyValue1.setProperty("Prop1");
    csdlPropertyValue1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlPropertyValue1.setAnnotations(csdlAnnotations);
    
    CsdlPropertyValue csdlPropertyValue2 = new CsdlPropertyValue();
    csdlPropertyValue2.setProperty("Prop1");
    csdlPropertyValue2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlPropertyValue2.setAnnotations(csdlAnnotations);
    
    assertTrue(csdlPropertyValue1.equals(csdlPropertyValue2));
    assertNotNull(csdlPropertyValue1.toString());
  }
  
  @Test
  public void compareCsdlPropertyValueWithNoAnnot() {
    CsdlPropertyValue csdlPropertyValue1 = new CsdlPropertyValue();
    csdlPropertyValue1.setProperty("Prop1");
    csdlPropertyValue1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    
    CsdlPropertyValue csdlPropertyValue2 = new CsdlPropertyValue();
    csdlPropertyValue2.setProperty("Prop1");
    csdlPropertyValue2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    
    assertTrue(csdlPropertyValue1.equals(csdlPropertyValue2));
    assertNotNull(csdlPropertyValue1.toString());
  }
  
  @Test
  public void compareCsdlPropertyNullValues() {
    CsdlPropertyValue csdlPropertyValue1 = new CsdlPropertyValue();
    csdlPropertyValue1.setProperty(null);
    csdlPropertyValue1.setValue(null);
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlPropertyValue1.setAnnotations(csdlAnnotations);
    
    CsdlPropertyValue csdlPropertyValue2 = new CsdlPropertyValue();
    csdlPropertyValue2.setProperty(null);
    csdlPropertyValue2.setValue(null);
    csdlPropertyValue2.setAnnotations(csdlAnnotations);
    
    assertTrue(csdlPropertyValue1.equals(csdlPropertyValue2));
    assertNotNull(csdlPropertyValue1.toString());
  }
  
  @Test
  public void negCompareCsdlPropertyValueWithNullProp() {
    CsdlPropertyValue csdlPropertyValue1 = new CsdlPropertyValue();
    csdlPropertyValue1.setProperty(null);
    csdlPropertyValue1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlPropertyValue1.setAnnotations(csdlAnnotations);
    
    CsdlPropertyValue csdlPropertyValue2 = new CsdlPropertyValue();
    csdlPropertyValue2.setProperty("Prop1");
    csdlPropertyValue2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlPropertyValue2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlPropertyValue1.equals(csdlPropertyValue2));
    assertNotNull(csdlPropertyValue1.toString());
  }
  
  @Test
  public void negCompareCsdlPropertyValueWithOtherNullProp() {
    CsdlPropertyValue csdlPropertyValue1 = new CsdlPropertyValue();
    csdlPropertyValue1.setProperty("Prop1");
    csdlPropertyValue1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlPropertyValue1.setAnnotations(csdlAnnotations);
    
    CsdlPropertyValue csdlPropertyValue2 = new CsdlPropertyValue();
    csdlPropertyValue2.setProperty(null);
    csdlPropertyValue2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlPropertyValue2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlPropertyValue1.equals(csdlPropertyValue2));
  }
  
  @Test
  public void negCompareCsdlPropertyValueWithNullValue() {
    CsdlPropertyValue csdlPropertyValue1 = new CsdlPropertyValue();
    csdlPropertyValue1.setProperty("Prop1");
    csdlPropertyValue1.setValue(null);
    csdlPropertyValue1.setAnnotations(null);
    
    CsdlPropertyValue csdlPropertyValue2 = new CsdlPropertyValue();
    csdlPropertyValue2.setProperty("Prop1");
    csdlPropertyValue2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlPropertyValue2.setAnnotations(null);
    
    assertFalse(csdlPropertyValue1.equals(csdlPropertyValue2));
  }
  
  @Test
  public void negCompareCsdlPropertyValueWithAllNull() {
    CsdlPropertyValue csdlPropertyValue1 = new CsdlPropertyValue();
    csdlPropertyValue1.setProperty(null);
    csdlPropertyValue1.setValue(null);
    csdlPropertyValue1.setAnnotations(null);
    
    CsdlPropertyValue csdlPropertyValue2 = new CsdlPropertyValue();
    csdlPropertyValue2.setProperty(null);
    csdlPropertyValue2.setValue(null);
    csdlPropertyValue2.setAnnotations(null);
    
    assertTrue(csdlPropertyValue1.equals(csdlPropertyValue2));
  }
  
  @Test
  public void negCompareCsdlPropertyValueWithOtherNullValue() {
    CsdlPropertyValue csdlPropertyValue1 = new CsdlPropertyValue();
    csdlPropertyValue1.setProperty("Prop1");
    csdlPropertyValue1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlPropertyValue1.setAnnotations(csdlAnnotations);
    
    CsdlPropertyValue csdlPropertyValue2 = new CsdlPropertyValue();
    csdlPropertyValue2.setProperty("Prop1");
    csdlPropertyValue2.setValue(null);
    csdlPropertyValue2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlPropertyValue1.equals(csdlPropertyValue2));
  }
  
  @Test
  public void negCompareCsdlPropertyValueWithDiffAnnot() {
    CsdlPropertyValue csdlPropertyValue1 = new CsdlPropertyValue();
    csdlPropertyValue1.setProperty("Prop1");
    csdlPropertyValue1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlPropertyValue1.setAnnotations(csdlAnnotations);
    
    CsdlPropertyValue csdlPropertyValue2 = new CsdlPropertyValue();
    csdlPropertyValue2.setProperty("Prop1");
    csdlPropertyValue2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlPropertyValue2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlPropertyValue1.equals(csdlPropertyValue2));
  }
  
  @Test
  public void negCompareCsdlPropertyValueWithAnnotNull() {
    CsdlPropertyValue csdlPropertyValue1 = new CsdlPropertyValue();
    csdlPropertyValue1.setProperty("Prop1");
    csdlPropertyValue1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlPropertyValue1.setAnnotations(null);
    
    CsdlPropertyValue csdlPropertyValue2 = new CsdlPropertyValue();
    csdlPropertyValue2.setProperty("Prop1");
    csdlPropertyValue2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlPropertyValue2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlPropertyValue1.equals(csdlPropertyValue2));
    assertNotNull(csdlPropertyValue1.toString());
  }
  
  @Test
  public void negCompareCsdlPropertyValueWithOtherAnnotNull() {
    CsdlPropertyValue csdlPropertyValue1 = new CsdlPropertyValue();
    csdlPropertyValue1.setProperty("Prop1");
    csdlPropertyValue1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlPropertyValue1.setAnnotations(csdlAnnotations);
    
    CsdlPropertyValue csdlPropertyValue2 = new CsdlPropertyValue();
    csdlPropertyValue2.setProperty("Prop1");
    csdlPropertyValue2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlPropertyValue2.setAnnotations(null);
    
    assertFalse(csdlPropertyValue1.equals(csdlPropertyValue2));
    assertNotNull(csdlPropertyValue2.toString());
  }
  
  @Test
  public void negCompareCsdlPropertyValueWithDiffAnnotSize() {
    CsdlPropertyValue csdlPropertyValue1 = new CsdlPropertyValue();
    csdlPropertyValue1.setProperty("Prop1");
    csdlPropertyValue1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlPropertyValue1.setAnnotations(csdlAnnotations);
    
    CsdlPropertyValue csdlPropertyValue2 = new CsdlPropertyValue();
    csdlPropertyValue2.setProperty("Prop1");
    csdlPropertyValue2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term2"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlPropertyValue2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlPropertyValue1.equals(csdlPropertyValue2));
  }
  
  @Test
  public void negCompareCsdlPropertyValueWithOtherInstance() {
    CsdlPropertyValue csdlPropertyValue1 = new CsdlPropertyValue();
    csdlPropertyValue1.setProperty("Prop1");
    csdlPropertyValue1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlPropertyValue1.setAnnotations(csdlAnnotations);
    
    CsdlPropertyPath csdlPropertyValue2 = new CsdlPropertyPath();
    
    assertFalse(csdlPropertyValue1.equals(csdlPropertyValue2));
  }
  
  @Test
  public void negCompareCsdlPropertyValueWithNullObj() {
    CsdlPropertyValue csdlPropertyValue1 = new CsdlPropertyValue();
    csdlPropertyValue1.setProperty("Prop1");
    csdlPropertyValue1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlPropertyValue1.setAnnotations(csdlAnnotations);
    
    CsdlPropertyValue csdlPropertyValue2 = null;
    
    assertFalse(csdlPropertyValue1.equals(csdlPropertyValue2));
  }
}
