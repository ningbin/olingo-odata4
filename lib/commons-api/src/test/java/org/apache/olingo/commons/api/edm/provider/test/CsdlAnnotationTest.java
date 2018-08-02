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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.commons.api.edm.provider.CsdlAnnotation;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlAnnotationPath;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlApply;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlCast;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlCollection;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression.ConstantExpressionType;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlIf;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlIsOf;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlLabeledElement;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlLabeledElementReference;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlLogicalOrComparisonExpression;
import org.apache.olingo.commons.api.edm.provider.
annotation.CsdlLogicalOrComparisonExpression.LogicalOrComparisonExpressionType;
import 
org.apache.olingo.commons.api.edm.provider.annotation.CsdlNavigationPropertyPath;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlNull;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPath;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPropertyPath;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlRecord;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlUrlRef;
import org.junit.Test;

public class CsdlAnnotationTest {

  @Test
  public void compareAnnotationWithAnnotPathExp() {
    CsdlAnnotation annot1 = new CsdlAnnotation();
    annot1.setQualifier("Qualifier1");
    annot1.setTerm("ns.term");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    annot1.setAnnotations(csdlAnnotations);
    annot1.setExpression(new CsdlAnnotationPath());
    
    CsdlAnnotation annot2 = new CsdlAnnotation();
    annot2.setQualifier("Qualifier1");
    annot2.setTerm("ns.term");
    annot2.setAnnotations(csdlAnnotations);
    annot2.setExpression(new CsdlAnnotationPath());
    
    assertTrue(annot1.equals(annot2));
    assertTrue(annot1.getExpression().isDynamic());
    assertTrue(annot2.getExpression().isDynamic());
    assertNotNull(annot1.getExpression().asDynamic());
    assertNotNull(annot2.getExpression().asDynamic());
    assertTrue(annot1.getExpression().asDynamic().isAnnotationPath());
    assertTrue(annot2.getExpression().asDynamic().isAnnotationPath());
    assertNotNull(annot1.getExpression().asDynamic().asAnnotationPath());
    assertNotNull(annot2.getExpression().asDynamic().asAnnotationPath());
    assertTrue(annot1.getExpression().asDynamic()
        .equals(annot2.getExpression().asDynamic()));
  }
  
  @Test
  public void compareAnnotationWithConstExp() {
    CsdlAnnotation annot1 = new CsdlAnnotation();
    annot1.setQualifier("Qualifier1");
    annot1.setTerm("ns.term");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    annot1.setAnnotations(csdlAnnotations);
    annot1.setExpression(new CsdlConstantExpression(ConstantExpressionType.Date));
    CsdlAnnotation annot2 = new CsdlAnnotation();
    annot2.setQualifier("Qualifier1");
    annot2.setTerm("ns.term");
    annot2.setAnnotations(csdlAnnotations);
    annot2.setExpression(new CsdlConstantExpression(ConstantExpressionType.Date));
    
    assertTrue(annot1.equals(annot2));
    assertTrue(annot1.getExpression().isConstant());
    assertTrue(annot2.getExpression().isConstant());
    assertNotNull(annot1.getExpression().asConstant());
    assertNotNull(annot2.getExpression().asConstant());
    assertNotNull(annot1.getExpression().asConstant().getType());
    assertNotNull(annot2.getExpression().asConstant().getType());
    assertTrue(annot1.getExpression()
        .equals(annot2.getExpression()));
  }
  
  @Test
  public void compareAnnotationWithCsdlPathExp() {
    CsdlAnnotation annot1 = new CsdlAnnotation();
    annot1.setQualifier("Qualifier1");
    annot1.setTerm("ns.term");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    annot1.setAnnotations(csdlAnnotations);
    annot1.setExpression(new CsdlPath());
    
    CsdlAnnotation annot2 = new CsdlAnnotation();
    annot2.setQualifier("Qualifier1");
    annot2.setTerm("ns.term");
    annot2.setAnnotations(csdlAnnotations);
    annot2.setExpression(new CsdlPath().setValue("Value1"));
    
    assertFalse(annot1.equals(annot2));
    assertTrue(annot1.getExpression().isDynamic());
    assertTrue(annot2.getExpression().isDynamic());
    assertNotNull(annot1.getExpression().asDynamic());
    assertNotNull(annot2.getExpression().asDynamic());
    assertTrue(annot1.getExpression().asDynamic().isPath());
    assertTrue(annot2.getExpression().asDynamic().isPath());
    assertNotNull(annot1.getExpression().asDynamic().asPath());
    assertNotNull(annot2.getExpression().asDynamic().asPath());
    assertFalse(annot1.getExpression().asDynamic()
        .equals(annot2.getExpression().asDynamic()));
  }
  
  @Test
  public void compareAnnotationWithDiffDynExp() {
    CsdlAnnotation annot1 = new CsdlAnnotation();
    annot1.setQualifier("Qualifier1");
    annot1.setTerm("ns.term");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    annot1.setAnnotations(csdlAnnotations);
    annot1.setExpression(new CsdlCast().setValue(new CsdlConstantExpression(ConstantExpressionType.Date)));
    
    CsdlAnnotation annot2 = new CsdlAnnotation();
    annot2.setQualifier("Qualifier1");
    annot2.setTerm("ns.term");
    annot2.setAnnotations(csdlAnnotations);
    annot2.setExpression(new CsdlUrlRef().setValue(new CsdlConstantExpression(ConstantExpressionType.Binary)));
    
    assertFalse(annot1.equals(annot2));
    assertTrue(annot1.getExpression().isDynamic());
    assertTrue(annot2.getExpression().isDynamic());
    assertNotNull(annot1.getExpression().asDynamic());
    assertNotNull(annot2.getExpression().asDynamic());
    assertTrue(annot1.getExpression().asDynamic().isCast());
    assertTrue(annot2.getExpression().asDynamic().isUrlRef());
    assertNotNull(annot1.getExpression().asDynamic().asCast());
    assertNotNull(annot2.getExpression().asDynamic().asUrlRef());
    assertFalse(annot1.getExpression().asDynamic()
        .equals(annot2.getExpression().asDynamic()));
  }
  
  @Test
  public void compareAnnotationWithNullObj() {
    CsdlAnnotation annot1 = new CsdlAnnotation();
    annot1.setQualifier("Qualifier1");
    annot1.setTerm("ns.term");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    annot1.setAnnotations(csdlAnnotations);
    annot1.setExpression(new CsdlIf());
    
    CsdlAnnotation annot2 = null;
    
    assertFalse(annot1.equals(annot2));
    assertTrue(annot1.getExpression().isDynamic());
    assertNotNull(annot1.getExpression().asDynamic());
    assertTrue(annot1.getExpression().asDynamic().isIf());
    assertNotNull(annot1.getExpression().asDynamic().asIf());
  }
  
  @Test
  public void compareAnnotationWithOtherInstance() {
    CsdlAnnotation annot1 = new CsdlAnnotation();
    annot1.setQualifier("Qualifier1");
    annot1.setTerm("ns.term");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    annot1.setAnnotations(csdlAnnotations);
    annot1.setExpression(new CsdlIsOf());
    
    CsdlPath annot2 = new CsdlPath();
    
    assertFalse(annot1.equals(annot2));
    assertTrue(annot1.getExpression().isDynamic());
    assertNotNull(annot1.getExpression().asDynamic());
    assertTrue(annot1.getExpression().asDynamic().isIsOf());
    assertNotNull(annot1.getExpression().asDynamic().asIsOf());
  }
  
  @Test
  public void negCompareAnnotationWithNullQualifier() {
    CsdlAnnotation annot1 = new CsdlAnnotation();
    annot1.setQualifier(null);
    annot1.setTerm("ns.term");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    annot1.setAnnotations(csdlAnnotations);
    annot1.setExpression(new CsdlLabeledElement());
    
    CsdlAnnotation annot2 = new CsdlAnnotation();
    annot2.setQualifier("Qualifier1");
    annot2.setTerm("ns.term");
    annot2.setAnnotations(csdlAnnotations);
    annot2.setExpression(new CsdlLabeledElement());
    
    assertFalse(annot1.equals(annot2));
    assertTrue(annot1.getExpression().isDynamic());
    assertTrue(annot2.getExpression().isDynamic());
    assertNotNull(annot1.getExpression().asDynamic());
    assertNotNull(annot2.getExpression().asDynamic());
    assertTrue(annot1.getExpression().asDynamic().isLabeledElement());
    assertTrue(annot2.getExpression().asDynamic().isLabeledElement());
    assertNotNull(annot1.getExpression().asDynamic().asLabeledElement());
    assertNotNull(annot2.getExpression().asDynamic().asLabeledElement());
    assertTrue(annot1.getExpression().asDynamic()
        .equals(annot2.getExpression().asDynamic()));
  }
  
  @Test
  public void negCompareAnnotationWithOtherNullQualifier() {
    CsdlAnnotation annot1 = new CsdlAnnotation();
    annot1.setQualifier("Qualifier1");
    annot1.setTerm("ns.term");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    annot1.setAnnotations(csdlAnnotations);
    annot1.setExpression(new CsdlLabeledElementReference());
    
    CsdlAnnotation annot2 = new CsdlAnnotation();
    annot2.setQualifier(null);
    annot2.setTerm("ns.term");
    annot2.setAnnotations(csdlAnnotations);
    annot2.setExpression(new CsdlLabeledElementReference());
    
    assertFalse(annot1.equals(annot2));
    assertTrue(annot1.getExpression().isDynamic());
    assertTrue(annot2.getExpression().isDynamic());
    assertNotNull(annot1.getExpression().asDynamic());
    assertNotNull(annot2.getExpression().asDynamic());
    assertTrue(annot1.getExpression().asDynamic().isLabeledElementReference());
    assertTrue(annot2.getExpression().asDynamic().isLabeledElementReference());
    assertNotNull(annot1.getExpression().asDynamic().asLabeledElementReference());
    assertNotNull(annot2.getExpression().asDynamic().asLabeledElementReference());
    assertTrue(annot1.getExpression().asDynamic()
        .equals(annot2.getExpression().asDynamic()));
  }
  
  @Test
  public void negCompareAnnotationWithBothNullQualifier() {
    CsdlAnnotation annot1 = new CsdlAnnotation();
    annot1.setQualifier(null);
    annot1.setTerm("ns.term");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    annot1.setAnnotations(csdlAnnotations);
    annot1.setExpression(new CsdlApply());
    
    CsdlAnnotation annot2 = new CsdlAnnotation();
    annot2.setQualifier(null);
    annot2.setTerm("ns.term");
    annot2.setAnnotations(csdlAnnotations);
    annot2.setExpression(new CsdlApply());
    
    assertTrue(annot1.equals(annot2));
    assertTrue(annot1.getExpression().isDynamic());
    assertTrue(annot2.getExpression().isDynamic());
    assertNotNull(annot1.getExpression().asDynamic());
    assertNotNull(annot2.getExpression().asDynamic());
    assertTrue(annot1.getExpression().asDynamic().isApply());
    assertTrue(annot2.getExpression().asDynamic().isApply());
    assertNotNull(annot1.getExpression().asDynamic().asApply());
    assertNotNull(annot2.getExpression().asDynamic().asApply());
    assertTrue(annot1.getExpression().asDynamic()
        .equals(annot2.getExpression().asDynamic()));
  }
  
  @Test
  public void negCompareAnnotationWithTermNull() {
    CsdlAnnotation annot1 = new CsdlAnnotation();
    annot1.setQualifier("Qualifier1");
    annot1.setTerm(null);
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    annot1.setAnnotations(csdlAnnotations);
    annot1.setExpression(new CsdlNavigationPropertyPath());
    
    CsdlAnnotation annot2 = new CsdlAnnotation();
    annot2.setQualifier("Qualifier1");
    annot2.setTerm("ns.term");
    annot2.setAnnotations(csdlAnnotations);
    annot2.setExpression(new CsdlNavigationPropertyPath());
    
    assertFalse(annot1.equals(annot2));
    assertTrue(annot1.getExpression().isDynamic());
    assertTrue(annot2.getExpression().isDynamic());
    assertNotNull(annot1.getExpression().asDynamic());
    assertNotNull(annot2.getExpression().asDynamic());
    assertTrue(annot1.getExpression().asDynamic().isNavigationPropertyPath());
    assertTrue(annot2.getExpression().asDynamic().isNavigationPropertyPath());
    assertNotNull(annot1.getExpression().asDynamic().asNavigationPropertyPath());
    assertNotNull(annot2.getExpression().asDynamic().asNavigationPropertyPath());
    assertTrue(annot1.getExpression().asDynamic()
        .equals(annot2.getExpression().asDynamic()));
  }
  
  @Test
  public void negCompareAnnotationWithOtherNullTerm() {
    CsdlAnnotation annot1 = new CsdlAnnotation();
    annot1.setQualifier("Qualifier1");
    annot1.setTerm("ns.term");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    annot1.setAnnotations(csdlAnnotations);
    annot1.setExpression(new CsdlNull());
    
    CsdlAnnotation annot2 = new CsdlAnnotation();
    annot2.setQualifier("Qualifier1");
    annot2.setTerm(null);
    annot2.setAnnotations(csdlAnnotations);
    annot2.setExpression(new CsdlNull());
    
    assertFalse(annot1.equals(annot2));
    assertTrue(annot1.getExpression().isDynamic());
    assertTrue(annot2.getExpression().isDynamic());
    assertNotNull(annot1.getExpression().asDynamic());
    assertNotNull(annot2.getExpression().asDynamic());
    assertTrue(annot1.getExpression().asDynamic().isNull());
    assertTrue(annot2.getExpression().asDynamic().isNull());
    assertNotNull(annot1.getExpression().asDynamic().asNull());
    assertNotNull(annot2.getExpression().asDynamic().asNull());
    assertTrue(annot1.getExpression().asDynamic()
        .equals(annot2.getExpression().asDynamic()));
  }
  
  @Test
  public void negCompareAnnotationWithBothNullTerm() {
    CsdlAnnotation annot1 = new CsdlAnnotation();
    annot1.setQualifier("Qualifier1");
    annot1.setTerm(null);
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    annot1.setAnnotations(csdlAnnotations);
    annot1.setExpression(new CsdlPropertyPath());
    
    CsdlAnnotation annot2 = new CsdlAnnotation();
    annot2.setQualifier("Qualifier1");
    annot2.setTerm(null);
    annot2.setAnnotations(csdlAnnotations);
    annot2.setExpression(new CsdlPropertyPath());
    
    assertTrue(annot1.equals(annot2));
    assertTrue(annot1.getExpression().isDynamic());
    assertTrue(annot2.getExpression().isDynamic());
    assertNotNull(annot1.getExpression().asDynamic());
    assertNotNull(annot2.getExpression().asDynamic());
    assertTrue(annot1.getExpression().asDynamic().isPropertyPath());
    assertTrue(annot2.getExpression().asDynamic().isPropertyPath());
    assertNotNull(annot1.getExpression().asDynamic().asPropertyPath());
    assertNotNull(annot2.getExpression().asDynamic().asPropertyPath());
    assertTrue(annot1.getExpression().asDynamic()
        .equals(annot2.getExpression().asDynamic()));
  }
  
  @Test
  public void negCompareAnnotationWithDiffAnnot() {
    CsdlAnnotation annot1 = new CsdlAnnotation();
    annot1.setQualifier("Qualifier1");
    annot1.setTerm("ns.term");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    annot1.setAnnotations(csdlAnnotations);
    annot1.setExpression(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    
    CsdlAnnotation annot2 = new CsdlAnnotation();
    annot2.setQualifier("Qualifier1");
    annot2.setTerm("ns.term");
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    annot2.setAnnotations(csdlAnnotations);
    annot2.setExpression(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    
    assertFalse(annot1.equals(annot2));
    assertTrue(annot1.getExpression().isDynamic());
    assertTrue(annot2.getExpression().isDynamic());
    assertNotNull(annot1.getExpression().asDynamic());
    assertNotNull(annot2.getExpression().asDynamic());
    assertTrue(annot1.getExpression().asDynamic().isLogicalOrComparison());
    assertTrue(annot2.getExpression().asDynamic().isLogicalOrComparison());
    assertNotNull(annot1.getExpression().asDynamic().asLogicalOrComparison());
    assertNotNull(annot2.getExpression().asDynamic().asLogicalOrComparison());
    assertTrue(annot1.getExpression().asDynamic()
        .equals(annot2.getExpression().asDynamic()));
  }
  
  @Test
  public void negCompareAnnotationWithDiffAnnotSize() {
    CsdlAnnotation annot1 = new CsdlAnnotation();
    annot1.setQualifier("Qualifier1");
    annot1.setTerm("ns.term");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    annot1.setAnnotations(csdlAnnotations);
    annot1.setExpression(new CsdlCollection());
    
    CsdlAnnotation annot2 = new CsdlAnnotation();
    annot2.setQualifier("Qualifier1");
    annot2.setTerm("ns.term");
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term2"));
    annot2.setAnnotations(csdlAnnotations);
    annot2.setExpression(new CsdlCollection());
    
    assertFalse(annot1.equals(annot2));
    assertTrue(annot1.getExpression().isDynamic());
    assertTrue(annot2.getExpression().isDynamic());
    assertNotNull(annot1.getExpression().asDynamic());
    assertNotNull(annot2.getExpression().asDynamic());
    assertTrue(annot1.getExpression().asDynamic().isCollection());
    assertTrue(annot2.getExpression().asDynamic().isCollection());
    assertNotNull(annot1.getExpression().asDynamic().asCollection());
    assertNotNull(annot2.getExpression().asDynamic().asCollection());
    assertTrue(annot1.getExpression().asDynamic()
        .equals(annot2.getExpression().asDynamic()));
  }
  
  @Test
  public void negCompareAnnotationWithNullExp() {
    CsdlAnnotation annot1 = new CsdlAnnotation();
    annot1.setQualifier("Qualifier1");
    annot1.setTerm("ns.term");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    annot1.setAnnotations(csdlAnnotations);
    annot1.setExpression(null);
    
    CsdlAnnotation annot2 = new CsdlAnnotation();
    annot2.setQualifier("Qualifier1");
    annot2.setTerm("ns.term");
    annot2.setAnnotations(csdlAnnotations);
    annot2.setExpression(new CsdlRecord());
    
    assertFalse(annot1.equals(annot2));
    assertNull(annot1.getExpression());
    assertTrue(annot2.getExpression().isDynamic());
    assertNotNull(annot2.getExpression().asDynamic());
    assertNotNull(annot2.getExpression().asDynamic().asRecord());
  }
  
  @Test
  public void negCompareAnnotationWithOtherNullExp() {
    CsdlAnnotation annot1 = new CsdlAnnotation();
    annot1.setQualifier("Qualifier1");
    annot1.setTerm("ns.term");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    annot1.setAnnotations(csdlAnnotations);
    annot1.setExpression(new CsdlAnnotationPath());
    
    CsdlAnnotation annot2 = new CsdlAnnotation();
    annot2.setQualifier("Qualifier1");
    annot2.setTerm("ns.term");
    annot2.setAnnotations(csdlAnnotations);
    annot2.setExpression(null);
    
    assertFalse(annot1.equals(annot2));
    assertTrue(annot1.getExpression().isDynamic());
    assertNull(annot2.getExpression());
    assertNotNull(annot1.getExpression().asDynamic());
    assertTrue(annot1.getExpression().asDynamic().isAnnotationPath());
  }
  
  @Test
  public void negCompareAnnotationWithBothNullExp() {
    CsdlAnnotation annot1 = new CsdlAnnotation();
    annot1.setQualifier("Qualifier1");
    annot1.setTerm("ns.term");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    annot1.setAnnotations(csdlAnnotations);
    annot1.setExpression(null);
    
    CsdlAnnotation annot2 = new CsdlAnnotation();
    annot2.setQualifier("Qualifier1");
    annot2.setTerm("ns.term");
    annot2.setAnnotations(csdlAnnotations);
    annot2.setExpression(null);
    
    assertTrue(annot1.equals(annot2));
    assertNull(annot1.getExpression());
    assertNull(annot2.getExpression());
  }
}
