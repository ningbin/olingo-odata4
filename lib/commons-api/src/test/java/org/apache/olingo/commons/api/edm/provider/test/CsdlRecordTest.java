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
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.commons.api.edm.provider.CsdlAnnotation;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression.ConstantExpressionType;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPath;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPropertyValue;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlRecord;
import org.junit.Test;

public class CsdlRecordTest {

  @Test
  public void compareCsdlRecord() {
    CsdlRecord record1 = new CsdlRecord();
    record1.setType("Type1");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    record1.setAnnotations(csdlAnnotations);
    List<CsdlPropertyValue> propertyValues = new ArrayList<CsdlPropertyValue>();
    propertyValues.add(new CsdlPropertyValue());
    record1.setPropertyValues(propertyValues);
    
    CsdlRecord record2 = new CsdlRecord();
    record2.setType("Type1");
    record2.setAnnotations(csdlAnnotations);
    record2.setPropertyValues(propertyValues);
    
    assertTrue(record1.equals(record2));
    assertNotNull(record1.toString());
  }
  
  @Test
  public void compareCsdlRecordWithNoAnnotAndProp() {
    CsdlRecord record1 = new CsdlRecord();
    record1.setType("Type1");
    
    CsdlRecord record2 = new CsdlRecord();
    record2.setType("Type1");
    
    assertTrue(record1.equals(record2));
    assertNotNull(record1.toString());
  }
  
  @Test
  public void compareCsdlRecordWithAllNullValues() {
    CsdlRecord record1 = new CsdlRecord();
    record1.setType(null);
    record1.setAnnotations(null);
    record1.setPropertyValues(null);
    
    CsdlRecord record2 = new CsdlRecord();
    record2.setType(null);
    record2.setAnnotations(null);
    record2.setPropertyValues(null);
    
    assertTrue(record1.equals(record2));
    assertNotNull(record1.toString());
  }
  
  @Test
  public void negCompareCsdlRecordWithTypeNull() {
    CsdlRecord record1 = new CsdlRecord();
    record1.setType(null);
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    record1.setAnnotations(csdlAnnotations);
    List<CsdlPropertyValue> propertyValues = new ArrayList<CsdlPropertyValue>();
    propertyValues.add(new CsdlPropertyValue());
    record1.setPropertyValues(propertyValues);
    
    CsdlRecord record2 = new CsdlRecord();
    record2.setType("Type1");
    record2.setAnnotations(csdlAnnotations);
    record2.setPropertyValues(propertyValues);
    
    assertFalse(record1.equals(record2));
  }
  
  @Test
  public void negCompareCsdlRecordWithOtherTypeNull() {
    CsdlRecord record1 = new CsdlRecord();
    record1.setType("Type1");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    record1.setAnnotations(csdlAnnotations);
    List<CsdlPropertyValue> propertyValues = new ArrayList<CsdlPropertyValue>();
    propertyValues.add(new CsdlPropertyValue());
    record1.setPropertyValues(propertyValues);
    
    CsdlRecord record2 = new CsdlRecord();
    record2.setType(null);
    record2.setAnnotations(csdlAnnotations);
    record2.setPropertyValues(propertyValues);
    
    assertFalse(record1.equals(record2));
  }
  
  @Test
  public void negCompareCsdlRecordWithDiffAnnot() {
    CsdlRecord record1 = new CsdlRecord();
    record1.setType("Type1");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    record1.setAnnotations(csdlAnnotations);
    List<CsdlPropertyValue> propertyValues = new ArrayList<CsdlPropertyValue>();
    propertyValues.add(new CsdlPropertyValue());
    record1.setPropertyValues(propertyValues);
    
    CsdlRecord record2 = new CsdlRecord();
    record2.setType("Type1");
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    record2.setAnnotations(csdlAnnotations);
    record2.setPropertyValues(propertyValues);
    
    assertFalse(record1.equals(record2));
  }
  
  @Test
  public void negCompareCsdlRecordWithAnnotNull() {
    CsdlRecord record1 = new CsdlRecord();
    record1.setType("Type1");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    record1.setAnnotations(null);
    List<CsdlPropertyValue> propertyValues = new ArrayList<CsdlPropertyValue>();
    propertyValues.add(new CsdlPropertyValue());
    record1.setPropertyValues(propertyValues);
    
    CsdlRecord record2 = new CsdlRecord();
    record2.setType("Type1");
    record2.setAnnotations(csdlAnnotations);
    record2.setPropertyValues(propertyValues);
    
    assertFalse(record1.equals(record2));
    assertNotNull(record1.toString());
  }
  
  @Test
  public void negCompareCsdlRecordWithOtherAnnotNull() {
    CsdlRecord record1 = new CsdlRecord();
    record1.setType("Type1");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    record1.setAnnotations(csdlAnnotations);
    List<CsdlPropertyValue> propertyValues = new ArrayList<CsdlPropertyValue>();
    propertyValues.add(new CsdlPropertyValue());
    record1.setPropertyValues(propertyValues);
    
    CsdlRecord record2 = new CsdlRecord();
    record2.setType("Type1");
    record2.setAnnotations(null);
    record2.setPropertyValues(propertyValues);
    
    assertFalse(record1.equals(record2));
    assertNotNull(record2.toString());
  }
  
  @Test
  public void negCompareCsdlRecordWithDiffAnnotSize() {
    CsdlRecord record1 = new CsdlRecord();
    record1.setType("Type1");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    record1.setAnnotations(csdlAnnotations);
    List<CsdlPropertyValue> propertyValues = new ArrayList<CsdlPropertyValue>();
    propertyValues.add(new CsdlPropertyValue());
    record1.setPropertyValues(propertyValues);
    
    CsdlRecord record2 = new CsdlRecord();
    record2.setType("Type1");
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term2"));
    record2.setAnnotations(csdlAnnotations);
    record2.setPropertyValues(propertyValues);
    
    assertFalse(record1.equals(record2));
  }
  
  @Test
  public void negCompareCsdlRecordWithDiffPropertyValues() {
    CsdlRecord record1 = new CsdlRecord();
    record1.setType("Type1");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    record1.setAnnotations(csdlAnnotations);
    List<CsdlPropertyValue> propertyValues = new ArrayList<CsdlPropertyValue>();
    propertyValues.add(new CsdlPropertyValue());
    record1.setPropertyValues(propertyValues);
    
    CsdlRecord record2 = new CsdlRecord();
    record2.setType("Type1");
    record2.setAnnotations(csdlAnnotations);
    propertyValues = new ArrayList<CsdlPropertyValue>();
    propertyValues.add(new CsdlPropertyValue().setValue(new CsdlConstantExpression(ConstantExpressionType.String)));
    record2.setPropertyValues(propertyValues);
    
    assertFalse(record1.equals(record2));
  }
  
  @Test
  public void negCompareCsdlRecordWithNullPropertyValues() {
    CsdlRecord record1 = new CsdlRecord();
    record1.setType("Type1");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    record1.setAnnotations(csdlAnnotations);
    List<CsdlPropertyValue> propertyValues = new ArrayList<CsdlPropertyValue>();
    propertyValues.add(new CsdlPropertyValue());
    record1.setPropertyValues(null);
    
    CsdlRecord record2 = new CsdlRecord();
    record2.setType("Type1");
    record2.setAnnotations(csdlAnnotations);
    record2.setPropertyValues(propertyValues);
    
    assertFalse(record1.equals(record2));
    assertNotNull(record1.toString());
  }
  
  @Test
  public void negCompareCsdlRecordWithOtherPropertyValuesNull() {
    CsdlRecord record1 = new CsdlRecord();
    record1.setType("Type1");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    record1.setAnnotations(csdlAnnotations);
    List<CsdlPropertyValue> propertyValues = new ArrayList<CsdlPropertyValue>();
    propertyValues.add(new CsdlPropertyValue());
    record1.setPropertyValues(propertyValues);
    
    CsdlRecord record2 = new CsdlRecord();
    record2.setType("Type1");
    record2.setAnnotations(csdlAnnotations);
    record2.setPropertyValues(null);
    
    assertFalse(record1.equals(record2));
    assertNotNull(record1.toString());
  }
  
  @Test
  public void negCompareCsdlRecordWithDiffPropertyValuesSize() {
    CsdlRecord record1 = new CsdlRecord();
    record1.setType("Type1");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    record1.setAnnotations(csdlAnnotations);
    List<CsdlPropertyValue> propertyValues = new ArrayList<CsdlPropertyValue>();
    propertyValues.add(new CsdlPropertyValue());
    record1.setPropertyValues(propertyValues);
    
    CsdlRecord record2 = new CsdlRecord();
    record2.setType("Type1");
    record2.setAnnotations(csdlAnnotations);
    propertyValues = new ArrayList<CsdlPropertyValue>();
    propertyValues.add(new CsdlPropertyValue().setValue(new CsdlConstantExpression(ConstantExpressionType.String)));
    propertyValues.add(new CsdlPropertyValue());
    record2.setPropertyValues(propertyValues);
    
    assertFalse(record1.equals(record2));
  }
  
  @Test
  public void negCompareCsdlRecordWithNullObj() {
    CsdlRecord record1 = new CsdlRecord();
    record1.setType("Type1");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    record1.setAnnotations(csdlAnnotations);
    List<CsdlPropertyValue> propertyValues = new ArrayList<CsdlPropertyValue>();
    propertyValues.add(new CsdlPropertyValue());
    record1.setPropertyValues(propertyValues);
    
    CsdlRecord record2 = null;
    
    assertFalse(record1.equals(record2));
  }
  
  @Test
  public void negCompareCsdlRecordWithOtherInstance() {
    CsdlRecord record1 = new CsdlRecord();
    record1.setType("Type1");
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    record1.setAnnotations(csdlAnnotations);
    List<CsdlPropertyValue> propertyValues = new ArrayList<CsdlPropertyValue>();
    propertyValues.add(new CsdlPropertyValue());
    record1.setPropertyValues(propertyValues);
    
    CsdlPath record2 = new CsdlPath();
    
    assertFalse(record1.equals(record2));
  }
}
