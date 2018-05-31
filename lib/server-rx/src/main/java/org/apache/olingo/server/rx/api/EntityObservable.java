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

import java.net.URI;
import java.util.List;

import org.apache.olingo.commons.api.data.AbstractEntityCollectionObject;
import org.apache.olingo.commons.api.data.Operation;
import org.apache.olingo.commons.api.ex.ODataNotSupportedException;

import io.reactivex.Observable;


/**
 * Data representation as an Observable for a collection of single entities.
 */
public abstract class EntityObservable extends AbstractEntityCollectionObject{
  
  private URI next;
  private Integer count;
    
  /**
   * {@inheritDoc}
   * <p/>
   * <b>ATTENTION:</b> <code>iterator</code> is not supported.
   */
/*  @Override
  public final Iterator<Entity> iterator() {
    //"iterator is not supported for iteration over Entities."
    throw new ODataNotSupportedException("Entity Observable does not support iterator()");
  }*/
  
  /**
   * {@inheritDoc}
   * <p/>
   * <b>ATTENTION:</b> <code>getOperations</code> is not supported by default.
   */
  @Override
  public List<Operation> getOperations() {
    //"Remove is not supported for iteration over Entities."
    throw new ODataNotSupportedException("Entity Observable does not support getOperations() by default");
  }
  /**
   * Gets count
   * 
   */
  public Integer getCount() {
    return count;
  }
  
  /**
   * Gets observable
   * 
   */
  @SuppressWarnings("rawtypes")
  public abstract Observable getObservable() ;
  

  /**
   * Gets next link.
   *
   */
  public URI getNext() {
    return next;
  }

  /**
   * {@inheritDoc}
   * <p/>
   * <b>ATTENTION:</b> <code>getDeltaLink</code> is not supported by default.
   */
  public URI getDeltaLink() {
    throw new ODataNotSupportedException("Entity Observable does not support getDeltaLink()");
  }
  
  /**
   * Sets next link.
   *
   * @param next next link.
   */
  public void setNext(final URI next) {
    this.next = next;
  }
  
  /**
   * Sets count.
   *
   * @param count count value.
   */
  public void setCount(final Integer count) {
    this.count = count;
  }
}
