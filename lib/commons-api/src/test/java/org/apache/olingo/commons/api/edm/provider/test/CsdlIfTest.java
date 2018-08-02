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
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlIf;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlLogicalOrComparisonExpression;
import org.apache.olingo.commons.api.edm.provider.
annotation.CsdlLogicalOrComparisonExpression.LogicalOrComparisonExpressionType;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPath;
import org.junit.Test;

public class CsdlIfTest {
  @Test
  public void compareCsdlIf() {
    CsdlIf csdlIf1 = new CsdlIf();
    csdlIf1.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf1.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf1.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIf1.setAnnotations(csdlAnnotations);
    
    CsdlIf csdlIf2 = new CsdlIf();
    csdlIf2.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf2.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf2.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlIf2.setAnnotations(csdlAnnotations);
    
    assertTrue(csdlIf1.equals(csdlIf2));
    assertNotNull(csdlIf1.toString());
  }
  
  @Test
  public void compareCsdlIfWithNoAnnotations() {
    CsdlIf csdlIf1 = new CsdlIf();
    csdlIf1.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf1.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf1.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    
    CsdlIf csdlIf2 = new CsdlIf();
    csdlIf2.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf2.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf2.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    
    assertTrue(csdlIf1.equals(csdlIf2));
    assertNotNull(csdlIf1.toString());
  }
  
  @Test
  public void compareCsdlIfWithNullValues() {
    CsdlIf csdlIf1 = new CsdlIf();
    csdlIf1.setGuard(null);
    csdlIf1.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIf1.setAnnotations(csdlAnnotations);
    
    CsdlIf csdlIf2 = new CsdlIf();
    csdlIf2.setGuard(null);
    csdlIf2.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlIf2.setAnnotations(csdlAnnotations);
    
    assertTrue(csdlIf1.equals(csdlIf2));
    assertNotNull(csdlIf1.toString());
  }
  
  @Test
  public void negCompareCsdlIfWithNullGuard() {
    CsdlIf csdlIf1 = new CsdlIf();
    csdlIf1.setGuard(null);
    csdlIf1.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf1.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIf1.setAnnotations(csdlAnnotations);
    
    CsdlIf csdlIf2 = new CsdlIf();
    csdlIf2.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf2.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf2.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlIf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIf1.equals(csdlIf2));
  }
  
  @Test
  public void negCompareCsdlIfWithNullThen() {
    CsdlIf csdlIf1 = new CsdlIf();
    csdlIf1.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf1.setThen(null);
    csdlIf1.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIf1.setAnnotations(csdlAnnotations);
    
    CsdlIf csdlIf2 = new CsdlIf();
    csdlIf2.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf2.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf2.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlIf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIf1.equals(csdlIf2));
    assertNotNull(csdlIf1.toString());
  }
  
  @Test
  public void negCompareCsdlIfWithNullElse() {
    CsdlIf csdlIf1 = new CsdlIf();
    csdlIf1.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf1.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf1.setElse(null);
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIf1.setAnnotations(csdlAnnotations);
    
    CsdlIf csdlIf2 = new CsdlIf();
    csdlIf2.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf2.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf2.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlIf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIf1.equals(csdlIf2));
    assertNotNull(csdlIf1.toString());
  }
  
  @Test
  public void negCompareCsdlIfWithNullOtherGuard() {
    CsdlIf csdlIf1 = new CsdlIf();
    csdlIf1.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf1.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf1.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIf1.setAnnotations(csdlAnnotations);
    
    CsdlIf csdlIf2 = new CsdlIf();
    csdlIf2.setGuard(null);
    csdlIf2.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf2.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlIf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIf1.equals(csdlIf2));
  }
  
  @Test
  public void negCompareCsdlIfWithNullOtherThen() {
    CsdlIf csdlIf1 = new CsdlIf();
    csdlIf1.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf1.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf1.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIf1.setAnnotations(csdlAnnotations);
    
    CsdlIf csdlIf2 = new CsdlIf();
    csdlIf2.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf2.setThen(null);
    csdlIf2.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlIf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIf1.equals(csdlIf2));
  }
  
  @Test
  public void negCompareCsdlIfWithNullOtherElse() {
    CsdlIf csdlIf1 = new CsdlIf();
    csdlIf1.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf1.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf1.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIf1.setAnnotations(csdlAnnotations);
    
    CsdlIf csdlIf2 = new CsdlIf();
    csdlIf2.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf2.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf2.setElse(null);
    csdlIf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIf1.equals(csdlIf2));
  }
  
  @Test
  public void negCompareCsdlIfWithDiffAnnotSize() {
    CsdlIf csdlIf1 = new CsdlIf();
    csdlIf1.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf1.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf1.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIf1.setAnnotations(csdlAnnotations);
    
    CsdlIf csdlIf2 = new CsdlIf();
    csdlIf2.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf2.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf2.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlIf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIf1.equals(csdlIf2));
  }
  
  @Test
  public void negCompareCsdlIfWithDiffAnnot() {
    CsdlIf csdlIf1 = new CsdlIf();
    csdlIf1.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf1.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf1.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIf1.setAnnotations(csdlAnnotations);
    
    CsdlIf csdlIf2 = new CsdlIf();
    csdlIf2.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf2.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf2.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlIf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIf1.equals(csdlIf2));
  }
  
  @Test
  public void negCompareCsdlIfWithDiffValues() {
    CsdlIf csdlIf1 = new CsdlIf();
    csdlIf1.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf1.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf1.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.Or));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIf1.setAnnotations(csdlAnnotations);
    
    CsdlIf csdlIf2 = new CsdlIf();
    csdlIf2.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf2.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf2.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlIf2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlIf1.equals(csdlIf2));
  }
  
  @Test
  public void negCompareCsdlIfWithNullObj() {
    CsdlIf csdlIf1 = new CsdlIf();
    csdlIf1.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf1.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf1.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.Or));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIf1.setAnnotations(csdlAnnotations);
    
    CsdlIf csdlIf2 = null;
    
    assertFalse(csdlIf1.equals(csdlIf2));
  }
  
  @Test
  public void negCompareCsdlIfWithDiffInstance() {
    CsdlIf csdlIf1 = new CsdlIf();
    csdlIf1.setGuard(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlIf1.setThen(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlIf1.setElse(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.Or));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlIf1.setAnnotations(csdlAnnotations);
    
    CsdlPath path = new CsdlPath();
    
    assertFalse(csdlIf1.equals(path));
  }
}
