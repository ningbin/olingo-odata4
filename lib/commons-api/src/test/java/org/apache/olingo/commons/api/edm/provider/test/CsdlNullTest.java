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

import org.apache.olingo.commons.api.edm.provider.CsdlAnnotation;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlNull;
import org.apache.olingo.commons.api.edm.provider.annotation.CsdlPath;
import org.junit.Test;

public class CsdlNullTest {

  @Test
  public void compareCsdlNull() {
    CsdlNull csdlNull1 = new CsdlNull();
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlNull1.setAnnotations(csdlAnnotations);
    
    CsdlNull csdlNull2 = new CsdlNull();
    csdlNull2.setAnnotations(csdlAnnotations);
    
    assertTrue(csdlNull1.equals(csdlNull2));
  }
  
  @Test
  public void negCompareCsdlNullWithDiffAnnot() {
    CsdlNull csdlNull1 = new CsdlNull();
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlNull1.setAnnotations(csdlAnnotations);
    
    CsdlNull csdlNull2 = new CsdlNull();
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlNull2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlNull1.equals(csdlNull2));
  }
  
  @Test
  public void negCompareCsdlNullWithDiffAnnotSize() {
    CsdlNull csdlNull1 = new CsdlNull();
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlNull1.setAnnotations(csdlAnnotations);
    
    CsdlNull csdlNull2 = new CsdlNull();
    csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term1"));
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term2"));
    csdlNull2.setAnnotations(csdlAnnotations);
    
    assertFalse(csdlNull1.equals(csdlNull2));
  }
  
  @Test
  public void negCompareCsdlNullWithNullObj() {
    CsdlNull csdlNull1 = new CsdlNull();
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlNull1.setAnnotations(csdlAnnotations);
    
    CsdlNull csdlNull2 = null;
    
    assertFalse(csdlNull1.equals(csdlNull2));
  }
  
  @Test
  public void negCompareCsdlNullWithOtherInstance() {
    CsdlNull csdlNull1 = new CsdlNull();
    List<CsdlAnnotation> csdlAnnotations = new ArrayList<CsdlAnnotation>();
    csdlAnnotations.add(new CsdlAnnotation().setTerm("ns.term"));
    csdlNull1.setAnnotations(csdlAnnotations);
    
    CsdlPath csdlPath = new CsdlPath();
    
    assertFalse(csdlNull1.equals(csdlPath));
  }
}
