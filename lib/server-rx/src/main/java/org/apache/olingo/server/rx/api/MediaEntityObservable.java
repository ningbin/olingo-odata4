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

import org.apache.olingo.commons.api.data.EntityMediaObject;

import io.reactivex.Observable;

public abstract class MediaEntityObservable extends EntityMediaObject {
	  
	  /**
	   * Gets observable
	   * 
	   */
	  @SuppressWarnings("rawtypes")
	  public abstract Observable getObservable() ;
	  	  
	  @Override
	  public boolean equals (Object obj) {
	    if (obj == null) {
	      return false;
	    }
	    if (!(obj instanceof MediaEntityObservable)) {
	      return false;
	    }
	    MediaEntityObservable mediaEntityObservable = (MediaEntityObservable) obj;
	    
	    return (super.getBytes() == null ? mediaEntityObservable.getBytes() == null : 
	    	super.getBytes().equals(mediaEntityObservable.getBytes()));
	  }
	  
	  @Override
	  public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((super.getBytes() == null) ? 0 : super.getBytes().hashCode());
	    return result;
	  }
}
