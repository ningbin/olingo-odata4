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
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlLogicalOrComparisonExpression;
import org.apache.olingo.commons.api.edm.
provider.annotation.CsdlLogicalOrComparisonExpression.LogicalOrComparisonExpressionType;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPropertyPath;
import org.junit.Test;

public class CsdlLogicalOrComparisonExpressionTest {

  @Test
  public void compareLogicalOrComparisonExpression() {
    CsdlLogicalOrComparisonExpression exp1 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp1.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp1.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    exp1.setAnnotations(csdlAnnotations);
    
    CsdlLogicalOrComparisonExpression exp2 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp2.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp2.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    exp2.setAnnotations(csdlAnnotations);
    
    assertTrue(exp1.equals(exp2));
    assertNotNull(exp1.toString());
  }
  
  @Test
  public void compareLogicalOrComparisonExpressionWithNoAnnot() {
    CsdlLogicalOrComparisonExpression exp1 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp1.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp1.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    
    CsdlLogicalOrComparisonExpression exp2 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp2.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp2.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    
    assertTrue(exp1.equals(exp2));
    assertNotNull(exp1.toString());
  }
  
  @Test
  public void negCompareLogicalOrComparisonExpressionWithNullLeft() {
    CsdlLogicalOrComparisonExpression exp1 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp1.setLeft(null);
    exp1.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    exp1.setAnnotations(csdlAnnotations);
    
    CsdlLogicalOrComparisonExpression exp2 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp2.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp2.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    exp2.setAnnotations(csdlAnnotations);
    
    assertFalse(exp1.equals(exp2));
    assertNotNull(exp1.toString());
  }
  
  @Test
  public void negCompareLogicalOrComparisonExpressionWithNullOtherLeft() {
    CsdlLogicalOrComparisonExpression exp1 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp1.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp1.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    exp1.setAnnotations(csdlAnnotations);
    
    CsdlLogicalOrComparisonExpression exp2 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp2.setLeft(null);
    exp2.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    exp2.setAnnotations(csdlAnnotations);
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void compareLogicalOrComparisonExpressionWithAllNullValues() {
    CsdlLogicalOrComparisonExpression exp1 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp1.setLeft(null);
    exp1.setRight(null);
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    exp1.setAnnotations(csdlAnnotations);
    
    CsdlLogicalOrComparisonExpression exp2 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp2.setLeft(null);
    exp2.setRight(null);
    exp2.setAnnotations(csdlAnnotations);
    
    assertTrue(exp1.equals(exp2));
    assertNotNull(exp1.toString());
  }
  
  @Test
  public void negCompareLogicalOrComparisonExpressionWithNullRight() {
    CsdlLogicalOrComparisonExpression exp1 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp1.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp1.setRight(null);
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    exp1.setAnnotations(csdlAnnotations);
    
    CsdlLogicalOrComparisonExpression exp2 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp2.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp2.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    exp2.setAnnotations(csdlAnnotations);
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareLogicalOrComparisonExpressionWithNullOtherRight() {
    CsdlLogicalOrComparisonExpression exp1 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp1.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp1.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    exp1.setAnnotations(csdlAnnotations);
    
    CsdlLogicalOrComparisonExpression exp2 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp2.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp2.setRight(null);
    exp2.setAnnotations(csdlAnnotations);
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareLogicalOrComparisonExpressionWithDiffAnnot() {
    CsdlLogicalOrComparisonExpression exp1 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp1.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp1.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    exp1.setAnnotations(csdlAnnotations);
    
    CsdlLogicalOrComparisonExpression exp2 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp2.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp2.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    exp2.setAnnotations(csdlAnnotations);
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareLogicalOrComparisonExpressionWithDiffAnnotSize() {
    CsdlLogicalOrComparisonExpression exp1 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp1.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp1.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    exp1.setAnnotations(csdlAnnotations);
    
    CsdlLogicalOrComparisonExpression exp2 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp2.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp2.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term2"));
    exp2.setAnnotations(csdlAnnotations);
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareLogicalOrComparisonExpressionWithNullObj() {
    CsdlLogicalOrComparisonExpression exp1 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp1.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp1.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    exp1.setAnnotations(csdlAnnotations);
    
    CsdlLogicalOrComparisonExpression exp2 = null;
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareLogicalOrComparisonExpressionWithDiffInstance() {
    CsdlLogicalOrComparisonExpression exp1 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp1.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp1.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    exp1.setAnnotations(csdlAnnotations);
    
    CsdlPropertyPath exp2 = new CsdlPropertyPath();
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareLogicalOrComparisonExpressionWithTypeNull() {
    CsdlLogicalOrComparisonExpression exp1 = new 
        CsdlLogicalOrComparisonExpression(null);
    exp1.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp1.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    exp1.setAnnotations(csdlAnnotations);
    
    CsdlLogicalOrComparisonExpression exp2 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp2.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp2.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    exp2.setAnnotations(csdlAnnotations);
    
    assertFalse(exp1.equals(exp2));
    assertNotNull(exp1.toString());
  }
  
  @Test
  public void negCompareLogicalOrComparisonExpressionWithOtherTypeNull() {
    CsdlLogicalOrComparisonExpression exp1 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp1.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp1.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    exp1.setAnnotations(csdlAnnotations);
    
    CsdlLogicalOrComparisonExpression exp2 = new 
        CsdlLogicalOrComparisonExpression(null);
    exp2.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp2.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    exp2.setAnnotations(csdlAnnotations);
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareLogicalOrComparisonExpressionWithBothTypeNull() {
    CsdlLogicalOrComparisonExpression exp1 = new 
        CsdlLogicalOrComparisonExpression(null);
    exp1.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp1.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    exp1.setAnnotations(csdlAnnotations);
    
    CsdlLogicalOrComparisonExpression exp2 = new 
        CsdlLogicalOrComparisonExpression(null);
    exp2.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp2.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    exp2.setAnnotations(csdlAnnotations);
    
    assertTrue(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareLogicalOrComparisonExpressionWithDiffType() {
    CsdlLogicalOrComparisonExpression exp1 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.Or);
    exp1.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp1.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    exp1.setAnnotations(csdlAnnotations);
    
    CsdlLogicalOrComparisonExpression exp2 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And);
    exp2.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp2.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    exp2.setAnnotations(csdlAnnotations);
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void compareLogicalOrComparisonExpression1() {
    CsdlLogicalOrComparisonExpression exp1 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.Not);
    exp1.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp1.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.Not));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    exp1.setAnnotations(csdlAnnotations);
    
    CsdlLogicalOrComparisonExpression exp2 = new 
        CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.Not);
    exp2.setLeft(new CsdlConstantExpression(ConstantExpressionType.String));
    exp2.setRight(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.Not));
    exp2.setAnnotations(csdlAnnotations);
    
    assertTrue(exp1.equals(exp2));
  }
  
  @Test
  public void fetchExpressionType() {
    assertEquals(LogicalOrComparisonExpressionType.Not, LogicalOrComparisonExpressionType.fromString("Not"));
  }
}
