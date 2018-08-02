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

import org.apache.olingo.commons.api.edm.provider.annotation.CsdlLabeledElementReference;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPath;
import org.junit.Test;

public class CsdlLabeledElementReferenceTest {
  
  @Test
  public void compareCsdlLabeledElementReference() {
    CsdlLabeledElementReference labeledElementReference1 = new CsdlLabeledElementReference();
    labeledElementReference1.setValue("value");
    
    CsdlLabeledElementReference labeledElementReference2 = new CsdlLabeledElementReference();
    labeledElementReference2.setValue("value");
    
    assertTrue(labeledElementReference1.equals(labeledElementReference2));
  }
  
  @Test
  public void negCompareCsdlLabeledElementReferenceNullInstance() {
    CsdlLabeledElementReference labeledElementReference1 = new CsdlLabeledElementReference();
    labeledElementReference1.setValue("value");
    
    CsdlLabeledElementReference labeledElementReference2 = null;
    
    assertFalse(labeledElementReference1.equals(labeledElementReference2));
  }
  
  @Test
  public void negCompareCsdlLabeledElementReferenceOtherObj() {
    CsdlLabeledElementReference labeledElementReference1 = new CsdlLabeledElementReference();
    labeledElementReference1.setValue("value");
    
    CsdlPath path = new CsdlPath();
    
    assertFalse(labeledElementReference1.equals(path));
  }
  
  @Test
  public void negCompareCsdlLabeledElementReferenceWithNullValue() {
    CsdlLabeledElementReference labeledElementReference1 = new CsdlLabeledElementReference();
    labeledElementReference1.setValue(null);
    
    CsdlLabeledElementReference labeledElementReference2 = new CsdlLabeledElementReference();
    labeledElementReference2.setValue("value");
    
    assertFalse(labeledElementReference1.equals(labeledElementReference2));
  }
  
  @Test
  public void negCompareCsdlLabeledElementReferenceWithOtherNullValue() {
    CsdlLabeledElementReference labeledElementReference1 = new CsdlLabeledElementReference();
    labeledElementReference1.setValue("value");
    
    CsdlLabeledElementReference labeledElementReference2 = new CsdlLabeledElementReference();
    labeledElementReference2.setValue(null);
    
    assertFalse(labeledElementReference1.equals(labeledElementReference2));
  }
  
  @Test
  public void compareCsdlLabeledElementReferenceWithNullValues() {
    CsdlLabeledElementReference labeledElementReference1 = new CsdlLabeledElementReference();
    labeledElementReference1.setValue(null);
    
    CsdlLabeledElementReference labeledElementReference2 = new CsdlLabeledElementReference();
    labeledElementReference2.setValue(null);
    
    assertTrue(labeledElementReference1.equals(labeledElementReference2));
  }
}
