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
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPath;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlUrlRef;
import org.junit.Test;

public class CsdlUrlRefTest {

  @Test
  public void compareCsdlUrlRef() {
    CsdlUrlRef csdlUrlRef1 = new CsdlUrlRef();
    csdlUrlRef1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlUrlRef1.setAnnotations(csdlAnnotations);
    
    CsdlUrlRef csdlUrlRef2 = new CsdlUrlRef();
    csdlUrlRef2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlUrlRef2.setAnnotations(csdlAnnotations);
    
    assertTrue(csdlUrlRef1.equals(csdlUrlRef2));
    assertNotNull(csdlUrlRef1.toString());
  }
  
  @Test
  public void compareCsdlUrlRefWithNoAnnot() {
    CsdlUrlRef csdlUrlRef1 = new CsdlUrlRef();
    csdlUrlRef1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    
    CsdlUrlRef csdlUrlRef2 = new CsdlUrlRef();
    csdlUrlRef2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    
    assertTrue(csdlUrlRef1.equals(csdlUrlRef2));
    assertNotNull(csdlUrlRef1.toString());
  }
  
  @Test
  public void negCompareCsdlUrlRefWithNullValue() {
    CsdlUrlRef csdlUrlRef1 = new CsdlUrlRef();
    csdlUrlRef1.setValue(null);
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlUrlRef1.setAnnotations(csdlAnnotations);
    
    CsdlUrlRef csdlUrlRef2 = new CsdlUrlRef();
    csdlUrlRef2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlUrlRef2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlUrlRef1.equals(csdlUrlRef2));
    assertNotNull(csdlUrlRef1.toString());
  }
  
  @Test
  public void negCompareCsdlUrlRefWithOtherNullValue() {
    CsdlUrlRef csdlUrlRef1 = new CsdlUrlRef();
    csdlUrlRef1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlUrlRef1.setAnnotations(csdlAnnotations);
    
    CsdlUrlRef csdlUrlRef2 = new CsdlUrlRef();
    csdlUrlRef2.setValue(null);
    csdlUrlRef2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlUrlRef1.equals(csdlUrlRef2));
  }
  
  @Test
  public void compareCsdlUrlRefWithBothNullValue() {
    CsdlUrlRef csdlUrlRef1 = new CsdlUrlRef();
    csdlUrlRef1.setValue(null);
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlUrlRef1.setAnnotations(csdlAnnotations);
    
    CsdlUrlRef csdlUrlRef2 = new CsdlUrlRef();
    csdlUrlRef2.setValue(null);
    csdlUrlRef2.setAnnotations(csdlAnnotations);
    
    assertTrue(csdlUrlRef1.equals(csdlUrlRef2));
  }
  
  @Test
  public void negCompareCsdlUrlRefWithDiffAnnot() {
    CsdlUrlRef csdlUrlRef1 = new CsdlUrlRef();
    csdlUrlRef1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlUrlRef1.setAnnotations(csdlAnnotations);
    
    CsdlUrlRef csdlUrlRef2 = new CsdlUrlRef();
    csdlUrlRef2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlUrlRef2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlUrlRef1.equals(csdlUrlRef2));
  }
  
  @Test
  public void negCompareCsdlUrlRefWithDiffAnnotSize() {
    CsdlUrlRef csdlUrlRef1 = new CsdlUrlRef();
    csdlUrlRef1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlUrlRef1.setAnnotations(csdlAnnotations);
    
    CsdlUrlRef csdlUrlRef2 = new CsdlUrlRef();
    csdlUrlRef2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term2"));
    csdlUrlRef2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlUrlRef1.equals(csdlUrlRef2));
  }
  
  @Test
  public void negCompareCsdlUrlRefWithNullObj() {
    CsdlUrlRef csdlUrlRef1 = new CsdlUrlRef();
    csdlUrlRef1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlUrlRef1.setAnnotations(csdlAnnotations);
    
    CsdlUrlRef csdlUrlRef2 = null;
    
    assertFalse(csdlUrlRef1.equals(csdlUrlRef2));
  }
  
  @Test
  public void negCompareCsdlUrlRefWithDiffInstance() {
    CsdlUrlRef csdlUrlRef1 = new CsdlUrlRef();
    csdlUrlRef1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlUrlRef1.setAnnotations(csdlAnnotations);
    
    CsdlPath path = new CsdlPath();
    
    assertFalse(csdlUrlRef1.equals(path));
  }
}
