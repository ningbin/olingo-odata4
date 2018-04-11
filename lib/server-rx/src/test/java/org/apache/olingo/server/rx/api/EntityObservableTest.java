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
  public void getIteratorTest(){
    EntityObservable observable = new EntityObservable() {
      @Override
      public Observable<Entity>  getObservable() {
        return null;
      }
    };
    observable.iterator();
  }
  
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
            // TODO Auto-generated method stub
            
          }};
      }
    };
    assertNotNull(observable.getObservable());
  }
}
