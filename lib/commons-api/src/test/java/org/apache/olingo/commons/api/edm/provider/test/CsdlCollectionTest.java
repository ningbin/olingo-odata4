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
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.commons.api.edm.provider.annotation.CsdlCast;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlCollection;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlConstantExpression.ConstantExpressionType;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlExpression;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlLogicalOrComparisonExpression;
import org.apache.olingo.commons.api.edm.provider.
annotation.CsdlLogicalOrComparisonExpression.LogicalOrComparisonExpressionType;
import org.junit.Test;

public class CsdlCollectionTest {
  @Test
  public void compareCsdlColl() {
    CsdlCollection csdlCollection1 = new CsdlCollection();
    List<CsdlExpression> items = new ArrayList<CsdlExpression>();
    items.add(new CsdlConstantExpression(ConstantExpressionType.String));
    items.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    items.add(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlCollection1.setItems(items);
    
    CsdlCollection csdlCollection2 = new CsdlCollection();
    csdlCollection2.setItems(items);
    
    assertTrue(csdlCollection1.equals(csdlCollection2));
  }

  @Test
  public void negCompareCsdlCollWithNullObj() {
    CsdlCollection csdlCollection1 = new CsdlCollection();
    List<CsdlExpression> items = new ArrayList<CsdlExpression>();
    items.add(new CsdlConstantExpression(ConstantExpressionType.String));
    items.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    items.add(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlCollection1.setItems(items);
    
    assertFalse(csdlCollection1.equals(null));
  }
  
  @Test
  public void negCompareCsdlCollWithDiffInstance() {
    CsdlCollection csdlCollection1 = new CsdlCollection();
    List<CsdlExpression> items = new ArrayList<CsdlExpression>();
    items.add(new CsdlConstantExpression(ConstantExpressionType.String));
    items.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    items.add(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlCollection1.setItems(items);
    
    CsdlCast csdlCast = new CsdlCast();
    
    assertFalse(csdlCollection1.equals(csdlCast));
  }
  
  @Test
  public void negCompareCsdlCollWithDiffItems() {
    CsdlCollection csdlCollection1 = new CsdlCollection();
    List<CsdlExpression> items = new ArrayList<CsdlExpression>();
    items.add(new CsdlConstantExpression(ConstantExpressionType.String));
    items.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    items.add(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlCollection1.setItems(items);
    
    CsdlCollection csdlCollection2 = new CsdlCollection();
    items = new ArrayList<CsdlExpression>();
    items.add(new CsdlConstantExpression(ConstantExpressionType.Int));
    items.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    items.add(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlCollection2.setItems(items);
    
    assertFalse(csdlCollection1.equals(csdlCollection2));
  }
  
  @Test
  public void negCompareCsdlCollWithDiffItemsSize() {
    CsdlCollection csdlCollection1 = new CsdlCollection();
    List<CsdlExpression> items = new ArrayList<CsdlExpression>();
    items.add(new CsdlConstantExpression(ConstantExpressionType.String));
    items.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    items.add(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlCollection1.setItems(items);
    
    CsdlCollection csdlCollection2 = new CsdlCollection();
    items = new ArrayList<CsdlExpression>();
    items.add(new CsdlLogicalOrComparisonExpression(LogicalOrComparisonExpressionType.And));
    items.add(new CsdlConstantExpression(ConstantExpressionType.Bool));
    csdlCollection2.setItems(items);
    
    assertFalse(csdlCollection1.equals(csdlCollection2));
  }
}
