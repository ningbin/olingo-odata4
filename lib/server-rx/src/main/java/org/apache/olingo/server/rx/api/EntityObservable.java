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
  
  @Override
  public boolean equals (Object obj) {
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof EntityObservable)) {
      return false;
    }
    EntityObservable entityObservable = (EntityObservable) obj;
    
    return getAnnotations().equals(entityObservable.getAnnotations())
        && (next == null ? entityObservable.next == null : next.equals(entityObservable.next))
        && (count == null ? entityObservable.count == null : count.equals(entityObservable.count))
        && (getBaseURI() == null ? entityObservable.getBaseURI() == null : 
          getBaseURI().equals(entityObservable.getBaseURI()))
        && (getTitle() == null ? entityObservable.getTitle() == null : 
          getTitle().equals(entityObservable.getTitle()))
        && (getId() == null ? entityObservable.getId() == null : 
          getId().equals(entityObservable.getId()));
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((count == null) ? 0 : count.hashCode());
    result = prime * result + ((next == null) ? 0 : next.hashCode());
    result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
    result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
    result = prime * result + ((getBaseURI() == null) ? 0 : getBaseURI().hashCode());
    result = prime * result + ((getAnnotations() == null) ? 0 : getAnnotations().hashCode());
    return result;
  }
}
