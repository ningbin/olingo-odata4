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
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlCast;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPath;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression.ConstantExpressionType;
import org.junit.Test;

public class CsdlCastTest {

  @Test
  public void compareCsdlCast() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(2);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("1234"));
    csdlCast1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));
    csdlCast2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));

    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertTrue(csdlCast1.equals(csdlCast2));
    assertNotNull(csdlCast1.toString());
  }
  
  @Test
  public void compareCsdlCastWithNoAnnot() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(2);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("1234"));
    csdlCast1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));

    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));
    csdlCast2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));

    assertTrue(csdlCast1.equals(csdlCast2));
    assertNotNull(csdlCast1.toString());
  }
  
  @Test
  public void negCompareCsdlCastWithTypeNull() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType(null);
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(2);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));

    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
    assertNotNull(csdlCast1.toString());
  }
  
  @Test
  public void negCompareCsdlCastWithSecondTypeNull() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(2);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType(null);
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));

    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
  }
  
  @Test
  public void negCompareCsdlApplyWithBothNullTypes() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType(null);
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(2);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType(null);
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));

    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertTrue(csdlCast1.equals(csdlCast2));
  }
  
  @Test
  public void negCompareCsdlCastWithDiffAnnot() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(2);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));

    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
  }
  
  @Test
  public void negCompareCsdlCastWithDiffAnnotSize() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(2);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));

    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term2"));
    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
  }
  
  @Test
  public void negCompareCsdlCastWithNullAnnot() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(2);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));
    csdlCast2.setAnnotations(null);
    
    assertFalse(csdlCast1.equals(csdlCast2));
    assertNotNull(csdlCast2.toString());
  }
  
  @Test
  public void negCompareCsdlCastWithOtherAnnotNull() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(2);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(null);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));
    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
    assertNotNull(csdlCast1.toString());
  }
  
  @Test
  public void negCompareCsdlCastWithBothAnnotNull() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(2);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("1234"));
    csdlCast1.setAnnotations(null);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));
    csdlCast2.setAnnotations(null);
    
    assertTrue(csdlCast1.equals(csdlCast2));
  }
  
  @Test
  public void negCompareCsdlCastWithDiffPrec() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(2);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(20);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));

    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term2"));
    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
  }
  
  @Test
  public void negCompareCsdlCastWithOtherPrecNull() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(null);
    csdlCast1.setScale(2);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));
    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
    assertNotNull(csdlCast2.toString());
  }
  
  @Test
  public void negCompareCsdlCastWithNullPrec() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(2);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(null);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));

    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term2"));
    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
    assertNotNull(csdlCast2.toString());
  }
  
  @Test
  public void negCompareCsdlCastWithDiffScale() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(3);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));

    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term2"));
    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
  }
  
  @Test
  public void negCompareCsdlCastWithNullScale() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(3);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(null);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));

    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term2"));
    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
    assertNotNull(csdlCast2.toString());
  }
  
  @Test
  public void negCompareCsdlCastWithOtherScaleNull() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(null);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(3);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));
    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
    assertNotNull(csdlCast2.toString());
  }
  
  @Test
  public void negCompareCsdlCastWithDiffMaxLen() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(3);
    csdlCast1.setMaxLength(20);
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));

    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term2"));
    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
  }
  
  @Test
  public void negCompareCsdlCastWithNullMaxLen() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(3);
    csdlCast1.setMaxLength(20);
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(null);
    csdlCast2.setSrid(SRID.valueOf("1234"));

    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term2"));
    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
    assertNotNull(csdlCast2.toString());
  }
  
  @Test
  public void negCompareCsdlCastWithDiffSrid() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(3);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("12345"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(3);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));

    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term2"));
    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
  }
  
  @Test
  public void negCompareCsdlCastWithOtherMaxLenNull() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(3);
    csdlCast1.setMaxLength(null);
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(3);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("1234"));
    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
    assertNotNull(csdlCast1.toString());
  }
  
  @Test
  public void negCompareCsdlCastWithNullSrid() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(3);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(null);

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(3);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(SRID.valueOf("12345"));

    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term2"));
    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
    assertNotNull(csdlCast1.toString());
  }
  
  @Test
  public void negCompareCsdlCastWithNullOtherSrid() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(3);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("12345"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(3);
    csdlCast2.setMaxLength(10);
    csdlCast2.setSrid(null);

    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term2"));
    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
    assertNotNull(csdlCast2.toString());
  }
  
  
  @Test
  public void negCompareCsdlCastWithNull() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(3);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("12345"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = null;
    
    assertFalse(csdlCast1.equals(csdlCast2));
  }
  
  @Test
  public void negCompareCsdlCastWithDiffInstance() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(3);
    csdlCast1.setMaxLength(10);
    csdlCast1.setSrid(SRID.valueOf("12345"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlPath path = new CsdlPath().setValue("value");
    
    assertFalse(csdlCast1.equals(path));
  }
  
  @Test
  public void negCompareCsdlCastWithNullValue() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(2);
    csdlCast1.setMaxLength(10);
    csdlCast1.setValue(null);
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlCast2.setSrid(SRID.valueOf("1234"));

    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
    assertNotNull(csdlCast1.toString());
  }
  
  @Test
  public void negCompareCsdlCastWithSecondNullValue() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(2);
    csdlCast1.setMaxLength(10);
    csdlCast1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setValue(null);
    csdlCast2.setSrid(SRID.valueOf("1234"));

    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
  }
  
  @Test
  public void negCompareCsdlCastWithSecondDiffValue() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setPrecision(10);
    csdlCast1.setScale(2);
    csdlCast1.setMaxLength(10);
    csdlCast1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    csdlCast1.setSrid(SRID.valueOf("1234"));

    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlCast1.setAnnotations(csdlAnnotations);
    
    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setPrecision(10);
    csdlCast2.setScale(2);
    csdlCast2.setMaxLength(10);
    csdlCast2.setValue(new CsdlConstantExpression(ConstantExpressionType.Int));
    csdlCast2.setSrid(SRID.valueOf("1234"));

    csdlCast2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlCast1.equals(csdlCast2));
  }
  
  @Test
  public void compareCsdlCastWithNoValues() {
    CsdlCast csdlCast1 = new CsdlCast();
    csdlCast1.setType("type1");
    csdlCast1.setValue(new CsdlConstantExpression(ConstantExpressionType.String));

    CsdlCast csdlCast2 = new CsdlCast();
    csdlCast2.setType("type1");
    csdlCast2.setValue(new CsdlConstantExpression(ConstantExpressionType.String));
    
    assertTrue(csdlCast1.equals(csdlCast2));
    assertNotNull(csdlCast1.toString());
  }
}
