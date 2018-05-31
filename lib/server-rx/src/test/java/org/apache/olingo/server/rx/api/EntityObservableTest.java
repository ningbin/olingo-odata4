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
package org.apache.olingo.server.rx.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.ex.ODataNotSupportedException;
import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class EntityObservableTest {
  
  @Test(expected = ODataNotSupportedException.class)
  public void getOperationsTest(){
    EntityObservable observable = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable.getOperations();
  }
  
  @Test(expected = ODataNotSupportedException.class)
  public void getDeltaLinkTest(){
    EntityObservable observable = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable.getDeltaLink();
  }
  
  @Test
  public void getNextTest() throws URISyntaxException{
    EntityObservable observable = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    URI next = new URI("next");
    observable.setNext(next );
    assertEquals(observable.getNext(), next);
  }
  
  @Test
  public void getCountTest(){
    EntityObservable observable = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable.setCount(1);
    assertEquals(observable.getCount(), new Integer(1));
  }
  
  @Test
  public void getObservableTest() {
    EntityObservable observable = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return new Observable<Entity>() {

          @Override
          protected void subscribeActual(Observer<? super Entity> observer) {
          }};
      }
    };
    assertNotNull(observable.getObservable());
  }
}
