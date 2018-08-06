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

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityIterator;
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
  
  @Test
  public void compareObservableTest() throws URISyntaxException {
    EntityObservable observable1 = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable1.setCount(1);
    URI next = new URI("next");
    observable1.setNext(next);
    observable1.setBaseURI(next);
    observable1.setId(next);
    
    EntityObservable observable2 = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable2.setCount(1);
    observable2.setNext(next);
    observable2.setBaseURI(next);
    observable2.setId(next);
    assertTrue(observable1.equals(observable2));
    assertNotNull(observable1.toString());
  }
  
  @Test
  public void negCompareObservableWithNullNext() throws URISyntaxException {
    EntityObservable observable1 = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable1.setCount(1);
    URI next = new URI("next");
    observable1.setNext(null);
    
    EntityObservable observable2 = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable2.setCount(1);
    observable2.setNext(next);
    assertFalse(observable1.equals(observable2));
    assertNotNull(observable1.toString());
  }
  
  @Test
  public void negCompareObservableWithOtherNextNull() throws URISyntaxException {
    EntityObservable observable1 = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable1.setCount(1);
    URI next = new URI("http://next");
    observable1.setNext(next);
    
    EntityObservable observable2 = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable2.setCount(1);
    observable2.setNext(null);
    assertFalse(observable1.equals(observable2));
  }
  
  @Test
  public void negCompareObservableWithBothNextNonNull() throws URISyntaxException {
    EntityObservable observable1 = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable1.setCount(1);
    URI next = new URI("http://next");
    observable1.setNext(next);
    
    EntityObservable observable2 = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable2.setCount(1);
    next = new URI("http://next1");
    observable2.setNext(next);
    assertFalse(observable1.equals(observable2));
  }
  
  @Test
  public void negCompareObservableWithBothNull() throws URISyntaxException {
    EntityObservable observable1 = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable1.setCount(1);
    observable1.setNext(null);
    
    EntityObservable observable2 = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable2.setCount(1);
    observable2.setNext(null);
    assertTrue(observable1.equals(observable2));
  }
  
  @Test
  public void negCompareObservableWithDiffCount() throws URISyntaxException {
    EntityObservable observable1 = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable1.setCount(1);
    observable1.setNext(null);
    
    EntityObservable observable2 = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable2.setCount(2);
    observable2.setNext(null);
    assertFalse(observable1.equals(observable2));
  }
  
  @Test
  public void negCompareObservableWithBothCountNull() throws URISyntaxException {
    EntityObservable observable1 = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable1.setNext(null);
    observable1.setCount(null);
    
    EntityObservable observable2 = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable2.setCount(null);
    observable2.setNext(null);
    assertTrue(observable1.equals(observable2));
    assertNotNull(observable1.toString());
  }
  
  @Test
  public void negCompareObservableWithNullObj() throws URISyntaxException {
    EntityObservable observable1 = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable1.setNext(null);
    
    EntityObservable observable2 = null;
    assertFalse(observable1.equals(observable2));
  }
  
  @Test
  public void negCompareObservableWithOtherInstance() throws URISyntaxException {
    EntityObservable observable1 = new EntityObservable() {
      @Override
      public Observable<Entity> getObservable() {
        return null;
      }
    };
    observable1.setNext(null);
    
    EntityIterator iterator = new EntityIterator() {
      
      @Override
      public Entity next() {
        return null;
      }
      
      @Override
      public boolean hasNext() {
        return false;
      }
    };
    assertFalse(observable1.equals(iterator));
  }
}
