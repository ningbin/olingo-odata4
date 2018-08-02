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

import org.apache.olingo.commons.api.edm.geo.SRID;
import org.apache.olingo.commons.api.edm.provider.CsdlAnnotation;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression.ConstantExpressionType;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlIsOf;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPath;
import org.junit.Test;

public class CsdlIsOfTest {

  @Test
  public void compareCsdlIsOf() {
    CsdlIsOf csdlIsOf1 = new CsdlIsOf();
    csdlIsOf1.setMaxLength(1);
    csdlIsOf1.setPrecision(2);
    csdlIsOf1.setScale(3);
    csdlIsOf1.setType("Edm.String");
    csdlIsOf1.setSrid(SRID.valueOf("1234"));
    csdlIsOf1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIsOf1.setAnnotations(csdlAnnotations);
    
    CsdlIsOf csdlIsOf2 = new CsdlIsOf();
    csdlIsOf2.setMaxLength(1);
    csdlIsOf2.setPrecision(2);
    csdlIsOf2.setScale(3);
    csdlIsOf2.setType("Edm.String");
    csdlIsOf2.setSrid(SRID.valueOf("1234"));
    csdlIsOf2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIsOf2.setAnnotations(csdlAnnotations);
    
    assertTrue(csdlIsOf1.equals(csdlIsOf2));
    assertNotNull(csdlIsOf1.toString());
  }
  
  @Test
  public void compareCsdlIsOfWithEmptyProperties() {
    CsdlIsOf csdlIsOf1 = new CsdlIsOf();
    csdlIsOf1.setType("Edm.String");
    csdlIsOf1.setSrid(SRID.valueOf("1234"));
    csdlIsOf1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    
    CsdlIsOf csdlIsOf2 = new CsdlIsOf();
    csdlIsOf2.setType("Edm.String");
    csdlIsOf2.setSrid(SRID.valueOf("1234"));
    csdlIsOf2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    
    assertTrue(csdlIsOf1.equals(csdlIsOf2));
    assertNotNull(csdlIsOf1.toString());
  }
  
  @Test
  public void negCompareCsdlIsOfWithDiffAnnot() {
    CsdlIsOf csdlIsOf1 = new CsdlIsOf();
    csdlIsOf1.setMaxLength(1);
    csdlIsOf1.setPrecision(2);
    csdlIsOf1.setScale(3);
    csdlIsOf1.setType("Edm.String");
    csdlIsOf1.setSrid(SRID.valueOf("1234"));
    csdlIsOf1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIsOf1.setAnnotations(csdlAnnotations);
    
    CsdlIsOf csdlIsOf2 = new CsdlIsOf();
    csdlIsOf2.setMaxLength(1);
    csdlIsOf2.setPrecision(2);
    csdlIsOf2.setScale(3);
    csdlIsOf2.setType("Edm.String");
    csdlIsOf2.setSrid(SRID.valueOf("1234"));
    csdlIsOf2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlIsOf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIsOf1.equals(csdlIsOf2));
  }
  
  @Test
  public void negCompareCsdlIsOfWithDiffAnnotSize() {
    CsdlIsOf csdlIsOf1 = new CsdlIsOf();
    csdlIsOf1.setMaxLength(1);
    csdlIsOf1.setPrecision(2);
    csdlIsOf1.setScale(3);
    csdlIsOf1.setType("Edm.String");
    csdlIsOf1.setSrid(SRID.valueOf("1234"));
    csdlIsOf1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIsOf1.setAnnotations(csdlAnnotations);
    
    CsdlIsOf csdlIsOf2 = new CsdlIsOf();
    csdlIsOf2.setMaxLength(1);
    csdlIsOf2.setPrecision(2);
    csdlIsOf2.setScale(3);
    csdlIsOf2.setType("Edm.String");
    csdlIsOf2.setSrid(SRID.valueOf("1234"));
    csdlIsOf2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlIsOf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIsOf1.equals(csdlIsOf2));
  }
  
  @Test
  public void negCompareCsdlIsOfWithTypeNull() {
    CsdlIsOf csdlIsOf1 = new CsdlIsOf();
    csdlIsOf1.setMaxLength(1);
    csdlIsOf1.setPrecision(2);
    csdlIsOf1.setScale(3);
    csdlIsOf1.setType(null);
    csdlIsOf1.setSrid(SRID.valueOf("1234"));
    csdlIsOf1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIsOf1.setAnnotations(csdlAnnotations);
    
    CsdlIsOf csdlIsOf2 = new CsdlIsOf();
    csdlIsOf2.setMaxLength(1);
    csdlIsOf2.setPrecision(2);
    csdlIsOf2.setScale(3);
    csdlIsOf2.setType("Edm.String");
    csdlIsOf2.setSrid(SRID.valueOf("1234"));
    csdlIsOf2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIsOf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIsOf1.equals(csdlIsOf2));
    assertNotNull(csdlIsOf1.toString());
  }
  
  @Test
  public void negCompareCsdlIsOfWithOtherTypeNull() {
    CsdlIsOf csdlIsOf1 = new CsdlIsOf();
    csdlIsOf1.setMaxLength(1);
    csdlIsOf1.setPrecision(2);
    csdlIsOf1.setScale(3);
    csdlIsOf1.setType("Edm.String");
    csdlIsOf1.setSrid(SRID.valueOf("1234"));
    csdlIsOf1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIsOf1.setAnnotations(csdlAnnotations);
    
    CsdlIsOf csdlIsOf2 = new CsdlIsOf();
    csdlIsOf2.setMaxLength(1);
    csdlIsOf2.setPrecision(2);
    csdlIsOf2.setScale(3);
    csdlIsOf2.setType(null);
    csdlIsOf2.setSrid(SRID.valueOf("1234"));
    csdlIsOf2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIsOf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIsOf1.equals(csdlIsOf2));
  }
  
  @Test
  public void negCompareCsdlIsOfWithValueNull() {
    CsdlIsOf csdlIsOf1 = new CsdlIsOf();
    csdlIsOf1.setMaxLength(1);
    csdlIsOf1.setPrecision(2);
    csdlIsOf1.setScale(3);
    csdlIsOf1.setType("Edm.String");
    csdlIsOf1.setSrid(SRID.valueOf("1234"));
    csdlIsOf1.setValue(null);
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIsOf1.setAnnotations(csdlAnnotations);
    
    CsdlIsOf csdlIsOf2 = new CsdlIsOf();
    csdlIsOf2.setMaxLength(1);
    csdlIsOf2.setPrecision(2);
    csdlIsOf2.setScale(3);
    csdlIsOf2.setType("Edm.String");
    csdlIsOf2.setSrid(SRID.valueOf("1234"));
    csdlIsOf2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIsOf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIsOf1.equals(csdlIsOf2));
    assertNotNull(csdlIsOf1.toString());
  }
  
  @Test
  public void negCompareCsdlIsOfWithOtherValueNull() {
    CsdlIsOf csdlIsOf1 = new CsdlIsOf();
    csdlIsOf1.setMaxLength(1);
    csdlIsOf1.setPrecision(2);
    csdlIsOf1.setScale(3);
    csdlIsOf1.setType("Edm.String");
    csdlIsOf1.setSrid(SRID.valueOf("1234"));
    csdlIsOf1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIsOf1.setAnnotations(csdlAnnotations);
    
    CsdlIsOf csdlIsOf2 = new CsdlIsOf();
    csdlIsOf2.setMaxLength(1);
    csdlIsOf2.setPrecision(2);
    csdlIsOf2.setScale(3);
    csdlIsOf2.setType("Edm.String");
    csdlIsOf2.setSrid(SRID.valueOf("1234"));
    csdlIsOf2.setValue(null);
    csdlIsOf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIsOf1.equals(csdlIsOf2));
  }
  
  @Test
  public void negCompareCsdlIsOfWithSridNull() {
    CsdlIsOf csdlIsOf1 = new CsdlIsOf();
    csdlIsOf1.setMaxLength(1);
    csdlIsOf1.setPrecision(2);
    csdlIsOf1.setScale(3);
    csdlIsOf1.setType("Edm.String");
    csdlIsOf1.setSrid(null);
    csdlIsOf1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIsOf1.setAnnotations(csdlAnnotations);
    
    CsdlIsOf csdlIsOf2 = new CsdlIsOf();
    csdlIsOf2.setMaxLength(1);
    csdlIsOf2.setPrecision(2);
    csdlIsOf2.setScale(3);
    csdlIsOf2.setType("Edm.String");
    csdlIsOf2.setSrid(SRID.valueOf("1234"));
    csdlIsOf2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIsOf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIsOf1.equals(csdlIsOf2));
    assertNotNull(csdlIsOf1.toString());
  }
  
  @Test
  public void negCompareCsdlIsOfWithDiffPrec() {
    CsdlIsOf csdlIsOf1 = new CsdlIsOf();
    csdlIsOf1.setMaxLength(1);
    csdlIsOf1.setPrecision(2);
    csdlIsOf1.setScale(3);
    csdlIsOf1.setType("Edm.String");
    csdlIsOf1.setSrid(SRID.valueOf("1234"));
    csdlIsOf1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIsOf1.setAnnotations(csdlAnnotations);
    
    CsdlIsOf csdlIsOf2 = new CsdlIsOf();
    csdlIsOf2.setMaxLength(1);
    csdlIsOf2.setPrecision(3);
    csdlIsOf2.setScale(3);
    csdlIsOf2.setType("Edm.String");
    csdlIsOf2.setSrid(SRID.valueOf("1234"));
    csdlIsOf2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIsOf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIsOf1.equals(csdlIsOf2));
  }
  
  @Test
  public void negCompareCsdlIsOfWithDiffScale() {
    CsdlIsOf csdlIsOf1 = new CsdlIsOf();
    csdlIsOf1.setMaxLength(1);
    csdlIsOf1.setPrecision(2);
    csdlIsOf1.setScale(3);
    csdlIsOf1.setType("Edm.String");
    csdlIsOf1.setSrid(SRID.valueOf("1234"));
    csdlIsOf1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIsOf1.setAnnotations(csdlAnnotations);
    
    CsdlIsOf csdlIsOf2 = new CsdlIsOf();
    csdlIsOf2.setMaxLength(1);
    csdlIsOf2.setPrecision(2);
    csdlIsOf2.setScale(4);
    csdlIsOf2.setType("Edm.String");
    csdlIsOf2.setSrid(SRID.valueOf("1234"));
    csdlIsOf2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIsOf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIsOf1.equals(csdlIsOf2));
  }
  
  @Test
  public void negCompareCsdlIsOfWithDiffMaxLen() {
    CsdlIsOf csdlIsOf1 = new CsdlIsOf();
    csdlIsOf1.setMaxLength(10);
    csdlIsOf1.setPrecision(2);
    csdlIsOf1.setScale(3);
    csdlIsOf1.setType("Edm.String");
    csdlIsOf1.setSrid(SRID.valueOf("1234"));
    csdlIsOf1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIsOf1.setAnnotations(csdlAnnotations);
    
    CsdlIsOf csdlIsOf2 = new CsdlIsOf();
    csdlIsOf2.setMaxLength(1);
    csdlIsOf2.setPrecision(2);
    csdlIsOf2.setScale(3);
    csdlIsOf2.setType("Edm.String");
    csdlIsOf2.setSrid(SRID.valueOf("1234"));
    csdlIsOf2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIsOf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIsOf1.equals(csdlIsOf2));
  }
  
  @Test
  public void negCompareCsdlIsOfWithOtherSridNull() {
    CsdlIsOf csdlIsOf1 = new CsdlIsOf();
    csdlIsOf1.setMaxLength(1);
    csdlIsOf1.setPrecision(2);
    csdlIsOf1.setScale(3);
    csdlIsOf1.setType("Edm.String");
    csdlIsOf1.setSrid(SRID.valueOf("1234"));
    csdlIsOf1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIsOf1.setAnnotations(csdlAnnotations);
    
    CsdlIsOf csdlIsOf2 = new CsdlIsOf();
    csdlIsOf2.setMaxLength(1);
    csdlIsOf2.setPrecision(2);
    csdlIsOf2.setScale(3);
    csdlIsOf2.setType("Edm.String");
    csdlIsOf2.setSrid(null);
    csdlIsOf2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIsOf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIsOf1.equals(csdlIsOf2));
  }
  
  @Test
  public void compareCsdlIsOfWithAllValuesNull() {
    CsdlIsOf csdlIsOf1 = new CsdlIsOf();
    csdlIsOf1.setMaxLength(1);
    csdlIsOf1.setPrecision(2);
    csdlIsOf1.setScale(3);
    csdlIsOf1.setType(null);
    csdlIsOf1.setSrid(null);
    csdlIsOf1.setValue(null);
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIsOf1.setAnnotations(csdlAnnotations);
    
    CsdlIsOf csdlIsOf2 = new CsdlIsOf();
    csdlIsOf2.setMaxLength(1);
    csdlIsOf2.setPrecision(2);
    csdlIsOf2.setScale(3);
    csdlIsOf2.setType(null);
    csdlIsOf2.setSrid(null);
    csdlIsOf2.setValue(null);
    csdlIsOf2.setAnnotations(csdlAnnotations);
    
    assertTrue(csdlIsOf1.equals(csdlIsOf2));
  }
  
  @Test
  public void negCompareCsdlIsOfWithNullObj() {
    CsdlIsOf csdlIsOf1 = new CsdlIsOf();
    csdlIsOf1.setMaxLength(1);
    csdlIsOf1.setPrecision(2);
    csdlIsOf1.setScale(3);
    csdlIsOf1.setType("Edm.String");
    csdlIsOf1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIsOf1.setAnnotations(csdlAnnotations);
    
    CsdlIsOf csdlIsOf2 = null;
    
    assertFalse(csdlIsOf1.equals(csdlIsOf2));
  }
  
  @Test
  public void negCompareCsdlIsOfWithDiffInstance() {
    CsdlIsOf csdlIsOf1 = new CsdlIsOf();
    csdlIsOf1.setMaxLength(1);
    csdlIsOf1.setPrecision(2);
    csdlIsOf1.setScale(3);
    csdlIsOf1.setType("Edm.String");
    csdlIsOf1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIsOf1.setAnnotations(csdlAnnotations);
    
    CsdlPath path = new CsdlPath();
    
    assertFalse(csdlIsOf1.equals(path));
  }
}
