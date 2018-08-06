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
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlApply;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression.ConstantExpressionType;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlExpression;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlLogicalOrComparisonExpression;
import org.apache.olingo.commons.api.edm.provider.annotation.
CsdlLogicalOrComparisonExpression.LogicalOrComparisonExpressionType;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPropertyPath;
import org.junit.Test;

public class CsdlApplyTest {

  @Test
  public void compareCsdlApply() {
    CsdlApply csdlApply1 = new CsdlApply();
    csdlApply1.setFunction("Function");

    List<CsdlExpression> parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlApply1.setParameters(parameters);

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlApply1.setAnnotations(csdlAnnotations);
    
    CsdlApply csdlApply2 = new CsdlApply();
    csdlApply2.setFunction("Function");
    csdlApply2.setParameters(parameters);
    csdlApply2.setAnnotations(csdlAnnotations);
    
    assertTrue(csdlApply1.equals(csdlApply2));
    assertNotNull(csdlApply1.toString());
  }
  
  @Test
  public void compareCsdlApplyWithEmptyParamsAndAnnot() {
    CsdlApply csdlApply1 = new CsdlApply();
    csdlApply1.setFunction("Function");

    CsdlApply csdlApply2 = new CsdlApply();
    csdlApply2.setFunction("Function");
    
    assertTrue(csdlApply1.equals(csdlApply2));
    assertNotNull(csdlApply1.toString());
  }
  
  @Test
  public void negCompareCsdlApplyWithMoreParams() {
    CsdlApply csdlApply1 = new CsdlApply();
    csdlApply1.setFunction("Function");

    List<CsdlExpression> parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlApply1.setParameters(parameters);

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlApply1.setAnnotations(csdlAnnotations);
    
    CsdlApply csdlApply2 = new CsdlApply();
    csdlApply2.setFunction("Function");
    parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.Or));
    csdlApply2.setParameters(parameters);
    csdlApply2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlApply1.equals(csdlApply2));
  }
  
  @Test
  public void negCompareCsdlApplyWithMoreAnnotations() {
    CsdlApply csdlApply1 = new CsdlApply();
    csdlApply1.setFunction("Function");

    List<CsdlExpression> parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlApply1.setParameters(parameters);

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlApply1.setAnnotations(csdlAnnotations);
    
    CsdlApply csdlApply2 = new CsdlApply();
    csdlApply2.setFunction("Function");
    csdlApply2.setParameters(parameters);
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlApply2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlApply1.equals(csdlApply2));
  }
  
  @Test
  public void negCompareCsdlApplyWithDiffInstance() {
    CsdlApply csdlApply1 = new CsdlApply();
    csdlApply1.setFunction("Function");

    List<CsdlExpression> parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlApply1.setParameters(parameters);

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlApply1.setAnnotations(csdlAnnotations);
    
    CsdlPropertyPath path = new CsdlPropertyPath().setValue("value1");
    
    assertFalse(csdlApply1.equals(path));
  }
  
  @Test
  public void negCompareCsdlApplyWithNullObj() {
    CsdlApply csdlApply1 = new CsdlApply();
    csdlApply1.setFunction("Function");

    List<CsdlExpression> parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlApply1.setParameters(parameters);

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlApply1.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlApply1.equals(null));
  }
  
  @Test
  public void negCompareCsdlApplyWithNullFunction() {
    CsdlApply csdlApply1 = new CsdlApply();
    csdlApply1.setFunction(null);

    List<CsdlExpression> parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlApply1.setParameters(parameters);

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlApply1.setAnnotations(csdlAnnotations);
    
    CsdlApply csdlApply2 = new CsdlApply();
    csdlApply2.setFunction("Function");
    csdlApply2.setParameters(parameters);
    csdlApply2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlApply1.equals(csdlApply2));
    assertNotNull(csdlApply1.toString());
  }
  
  @Test
  public void negCompareCsdlApplyWithSecondNullFunction() {
    CsdlApply csdlApply1 = new CsdlApply();
    csdlApply1.setFunction("Function");

    List<CsdlExpression> parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlApply1.setParameters(parameters);

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlApply1.setAnnotations(csdlAnnotations);
    
    CsdlApply csdlApply2 = new CsdlApply();
    csdlApply2.setFunction(null);
    csdlApply2.setParameters(parameters);
    csdlApply2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlApply1.equals(csdlApply2));
  }
  
  @Test
  public void negCompareCsdlApplyWithBothNullFunction() {
    CsdlApply csdlApply1 = new CsdlApply();
    csdlApply1.setFunction(null);

    List<CsdlExpression> parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlApply1.setParameters(parameters);

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlApply1.setAnnotations(csdlAnnotations);
    
    CsdlApply csdlApply2 = new CsdlApply();
    csdlApply2.setFunction(null);
    csdlApply2.setParameters(parameters);
    csdlApply2.setAnnotations(csdlAnnotations);
    
    assertTrue(csdlApply1.equals(csdlApply2));
  }
  
  @Test
  public void negCompareCsdlApplyWithDiffAnnot() {
    CsdlApply csdlApply1 = new CsdlApply();
    csdlApply1.setFunction("Function");

    List<CsdlExpression> parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlApply1.setParameters(parameters);

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlApply1.setAnnotations(csdlAnnotations);
    
    CsdlApply csdlApply2 = new CsdlApply();
    csdlApply2.setFunction("Function");
    csdlApply2.setParameters(parameters);
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlApply2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlApply1.equals(csdlApply2));
  }
  
  @Test
  public void negCompareCsdlApplyWithDiffParams() {
    CsdlApply csdlApply1 = new CsdlApply();
    csdlApply1.setFunction("Function");

    List<CsdlExpression> parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlApply1.setParameters(parameters);

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlApply1.setAnnotations(csdlAnnotations);
    
    CsdlApply csdlApply2 = new CsdlApply();
    csdlApply2.setFunction("Function");
    parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.Or));
    csdlApply2.setParameters(parameters);
    csdlApply2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlApply1.equals(csdlApply2));
  }
  
  @Test
  public void negCompareCsdlApplyWithOtherNullParams() {
    CsdlApply csdlApply1 = new CsdlApply();
    csdlApply1.setFunction("Function");

    List<CsdlExpression> parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlApply1.setParameters(null);

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlApply1.setAnnotations(csdlAnnotations);
    
    CsdlApply csdlApply2 = new CsdlApply();
    csdlApply2.setFunction("Function");
    parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.Or));
    csdlApply2.setParameters(parameters);
    csdlApply2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlApply1.equals(csdlApply2));
  }
  
  @Test
  public void negCompareCsdlApplyWithOtherNullAnnot() {
    CsdlApply csdlApply1 = new CsdlApply();
    csdlApply1.setFunction("Function");

    List<CsdlExpression> parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlApply1.setParameters(parameters);

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlApply1.setAnnotations(null);
    
    CsdlApply csdlApply2 = new CsdlApply();
    csdlApply2.setFunction("Function");
    parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.Or));
    csdlApply2.setParameters(parameters);
    csdlApply2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlApply1.equals(csdlApply2));
  }
  
  @Test
  public void negCompareCsdlApplyWithNullParams() {
    CsdlApply csdlApply1 = new CsdlApply();
    csdlApply1.setFunction("Function");

    List<CsdlExpression> parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlApply1.setParameters(parameters);

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlApply1.setAnnotations(csdlAnnotations);
    
    CsdlApply csdlApply2 = new CsdlApply();
    csdlApply2.setFunction("Function");
    csdlApply2.setParameters(null);
    csdlApply2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlApply1.equals(csdlApply2));
    assertNotNull(csdlApply2.toString());
  }
  
  @Test
  public void compareCsdlApplyWithBothNullParams() {
    CsdlApply csdlApply1 = new CsdlApply();
    csdlApply1.setFunction("Function");

    csdlApply1.setParameters(null);

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlApply1.setAnnotations(csdlAnnotations);
    
    CsdlApply csdlApply2 = new CsdlApply();
    csdlApply2.setFunction("Function");
    csdlApply2.setParameters(null);
    csdlApply2.setAnnotations(csdlAnnotations);
    
    assertTrue(csdlApply1.equals(csdlApply2));
  }
  
  @Test
  public void negCompareCsdlApplyWithNullAnnot() {
    CsdlApply csdlApply1 = new CsdlApply();
    csdlApply1.setFunction("Function");

    List<CsdlExpression> parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlApply1.setParameters(parameters);

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlApply1.setAnnotations(csdlAnnotations);
    
    CsdlApply csdlApply2 = new CsdlApply();
    csdlApply2.setFunction("Function");
    csdlApply2.setParameters(parameters);
    csdlApply2.setAnnotations(null);
    
    assertFalse(csdlApply1.equals(csdlApply2));
    assertNotNull(csdlApply2.toString());
  }
  
  @Test
  public void compareCsdlApplyWithBothNullAnnot() {
    CsdlApply csdlApply1 = new CsdlApply();
    csdlApply1.setFunction("Function");

    List<CsdlExpression> parameters = new ArrayList<CsdlExpression>();
    parameters.add(new CsdlConstantExpression(ConstantExpressionType.String));
    parameters.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    csdlApply1.setParameters(parameters);

    csdlApply1.setAnnotations(null);
    
    CsdlApply csdlApply2 = new CsdlApply();
    csdlApply2.setFunction("Function");
    csdlApply2.setParameters(parameters);
    csdlApply2.setAnnotations(null);
    
    assertTrue(csdlApply1.equals(csdlApply2));
  }
}
