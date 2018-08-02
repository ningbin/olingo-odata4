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

import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression.ConstantExpressionType;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPath;
import org.junit.Test;

public class CsdlConstantExpressionTest {

  @Test
  public void compareCsdlConstExp1() {
    CsdlConstantExpression exp1 = new CsdlConstantExpression(ConstantExpressionType.Binary);
    exp1.setValue("value1");
    
    CsdlConstantExpression exp2 = new CsdlConstantExpression(ConstantExpressionType.Binary);
    exp2.setValue("value1");
    
    assertTrue(exp1.equals(exp2));
  }
  
  @Test
  public void compareCsdlConstExp2() {
    CsdlConstantExpression exp1 = new CsdlConstantExpression(ConstantExpressionType.Binary, "value1");
    
    CsdlConstantExpression exp2 = new CsdlConstantExpression(ConstantExpressionType.Binary, "value1");
    
    assertTrue(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareCsdlConstExpWithDiffValues() {
    CsdlConstantExpression exp1 = new CsdlConstantExpression(ConstantExpressionType.Binary, "value1");
    
    CsdlConstantExpression exp2 = new CsdlConstantExpression(ConstantExpressionType.Binary, "value2");
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareCsdlConstExpWithDiffTypes() {
    CsdlConstantExpression exp1 = new CsdlConstantExpression(ConstantExpressionType.Date, "value1");
    
    CsdlConstantExpression exp2 = new CsdlConstantExpression(ConstantExpressionType.Binary, "value1");
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareCsdlConstExpWithDiffConst() {
    CsdlConstantExpression exp1 = new CsdlConstantExpression(ConstantExpressionType.Date);
    
    CsdlConstantExpression exp2 = new CsdlConstantExpression(ConstantExpressionType.Binary, "value2");
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareCsdlConstExpWithNullValue() {
    CsdlConstantExpression exp1 = new CsdlConstantExpression(ConstantExpressionType.Date, null);
    
    CsdlConstantExpression exp2 = new CsdlConstantExpression(ConstantExpressionType.Date, "value2");
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareCsdlConstExpWithNullType() {
    CsdlConstantExpression exp1 = new CsdlConstantExpression(null, "value1");
    
    CsdlConstantExpression exp2 = new CsdlConstantExpression(ConstantExpressionType.Date, "value1");
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void compareCsdlConstExpWithBothNullType() {
    CsdlConstantExpression exp1 = new CsdlConstantExpression(null, "value1");
    
    CsdlConstantExpression exp2 = new CsdlConstantExpression(null, "value1");
    
    assertTrue(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareCsdlConstExpWithOtherNullType() {
    CsdlConstantExpression exp1 = new CsdlConstantExpression(ConstantExpressionType.Date, "value1");
    
    CsdlConstantExpression exp2 = new CsdlConstantExpression(null, "value1");
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareCsdlConstExpWithOtherNullValue() {
    CsdlConstantExpression exp1 = new CsdlConstantExpression(ConstantExpressionType.Binary, "value1");
    
    CsdlConstantExpression exp2 = new CsdlConstantExpression(ConstantExpressionType.Binary, null);
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void compareCsdlConstExpWithBothNullValues() {
    CsdlConstantExpression exp1 = new CsdlConstantExpression(ConstantExpressionType.Binary, null);
    
    CsdlConstantExpression exp2 = new CsdlConstantExpression(ConstantExpressionType.Binary, null);
    
    assertTrue(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareCsdlConstExpWithNullObj() {
    CsdlConstantExpression exp1 = new CsdlConstantExpression(ConstantExpressionType.Date, "value1");
    
    CsdlConstantExpression exp2 = null;
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void negCompareCsdlConstExpWithOtherInstance() {
    CsdlConstantExpression exp1 = new CsdlConstantExpression(ConstantExpressionType.Date, "value1");
    
    CsdlPath exp2 = new CsdlPath();
    
    assertFalse(exp1.equals(exp2));
  }
  
  @Test
  public void testFromString() {
    assertEquals(ConstantExpressionType.Binary, 
        CsdlConstantExpression.ConstantExpressionType.fromString("Binary"));
  }
}
