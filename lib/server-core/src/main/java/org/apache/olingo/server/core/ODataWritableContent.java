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
package org.apache.olingo.server.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

import org.apache.olingo.commons.api.data.AbstractEntityCollectionObject;
import org.apache.olingo.commons.api.data.EntityMediaObject;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.ex.ODataRuntimeException;
import org.apache.olingo.server.api.ODataContent;
import org.apache.olingo.server.api.ODataContentWriteErrorCallback;
import org.apache.olingo.server.api.ODataContentWriteErrorContext;
import org.apache.olingo.server.api.ODataLibraryException;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.serializer.SerializerStreamResult;
import org.apache.olingo.server.core.serializer.FixedFormatSerializerImpl;
import org.apache.olingo.server.core.serializer.SerializerStreamResultImpl;
import org.apache.olingo.server.core.serializer.json.ODataJsonSerializer;

/**
 * Stream supporting implementation of the ODataContent
 * and contains the response content for the OData request.
 * <p/>
 * If an error occur during a <code>write</code> method <b>NO</b> exception
 * will be thrown but if registered the
 * org.apache.olingo.server.api.ODataContentWriteErrorCallback is called.
 */
public class ODataWritableContent implements ODataContent {
  private StreamContent streamContent;

  private static abstract class StreamContent {
    protected AbstractEntityCollectionObject entitySet;
    protected ServiceMetadata metadata;
    protected EdmEntityType entityType;
    protected EntityCollectionSerializerOptions options;
    protected EntityMediaObject mediaEntity;
    protected InputStream inputStream;

    public StreamContent(AbstractEntityCollectionObject iterator, EdmEntityType entityType, ServiceMetadata metadata,
        EntityCollectionSerializerOptions options) {
      this.entitySet = iterator;
      this.entityType = entityType;
      this.metadata = metadata;
      this.options = options;
    }

    public StreamContent(EntityMediaObject mediaEntity) {
    	this.mediaEntity = mediaEntity;
    }
    
    public StreamContent(InputStream inputStream) {
    	this.inputStream = inputStream;
    }
    
    protected abstract void writeEntity(AbstractEntityCollectionObject entity, OutputStream outputStream) 
        throws SerializerException;
    
    protected abstract void writeBinary(EntityMediaObject mediaEntity, OutputStream outputStream)
    		throws SerializerException;
    
    protected abstract void writeBinary(InputStream inputStream, OutputStream outputStream)
    		throws SerializerException;

    public void write(OutputStream out) {
      try {
    	  if (inputStream != null) {
    		  writeBinary(inputStream, out);
    	  }else if (mediaEntity != null) {
    		  writeBinary(mediaEntity, out);
    	  } else {
    		  writeEntity(entitySet, out);
    	  }
      } catch (SerializerException e) {
        final ODataContentWriteErrorCallback errorCallback = options.getODataContentWriteErrorCallback();
        if (errorCallback != null) {
          final WriteErrorContext errorContext = new WriteErrorContext(e);
          errorCallback.handleError(errorContext, Channels.newChannel(out));
        }
      }
    }
  }

  private static class StreamContentForJson extends StreamContent {
    private ODataJsonSerializer jsonSerializer;

    public StreamContentForJson(AbstractEntityCollectionObject iterator, EdmEntityType entityType,
        ODataJsonSerializer jsonSerializer, ServiceMetadata metadata,
        EntityCollectionSerializerOptions options) {
      super(iterator, entityType, metadata, options);

      this.jsonSerializer = jsonSerializer;
    }

    protected void writeEntity(AbstractEntityCollectionObject entity, 
        OutputStream outputStream) throws SerializerException {
      try {
        jsonSerializer.entityCollectionIntoStream(metadata, entityType, entity, options, outputStream);
        outputStream.flush();
      } catch (final IOException e) {
        throw new ODataRuntimeException("Failed entity serialization", e);
      }
    }

	@Override
	protected void writeBinary(EntityMediaObject mediaEntity, 
			OutputStream outputStream) throws SerializerException {
		throw new ODataRuntimeException("Not Implemented in Entity Handling");
	}
	
	@Override
	protected void writeBinary(InputStream inputStream, 
			OutputStream outputStream) throws SerializerException {
		throw new ODataRuntimeException("Not Implemented in Entity Handling");
	}
  }
  
  private static class StreamContentForMedia extends StreamContent {
	    private FixedFormatSerializerImpl fixedFormatSerializer;

	    public StreamContentForMedia(EntityMediaObject mediaEntity, 
	    		FixedFormatSerializerImpl fixedFormatSerializer) {
	      super(mediaEntity);

	      this.fixedFormatSerializer = fixedFormatSerializer;
	    }
	    
	    public StreamContentForMedia(InputStream inputStream, 
	    		FixedFormatSerializerImpl fixedFormatSerializer) {
	      super(inputStream);

	      this.fixedFormatSerializer = fixedFormatSerializer;
	    }

	    protected void writeEntity(AbstractEntityCollectionObject entity, 
	        OutputStream outputStream) throws SerializerException {
	    	throw new ODataRuntimeException("Not Implemented in Entity Handling");
	    }

		@Override
		protected void writeBinary(EntityMediaObject mediaEntity, 
				OutputStream outputStream) throws SerializerException {
			fixedFormatSerializer.binaryIntoStreamed(mediaEntity, outputStream);
		}
		
		@Override
		protected void writeBinary(InputStream inputStream, 
				OutputStream outputStream) throws SerializerException {
			fixedFormatSerializer.binaryIntoStreamed(inputStream, outputStream);
		}
	  }

  @Override
  public void write(WritableByteChannel writeChannel) {
    this.streamContent.write(Channels.newOutputStream(writeChannel));
  }

  @Override
  public void write(OutputStream stream) {
    write(Channels.newChannel(stream));
  }

  private ODataWritableContent(StreamContent streamContent) {
    this.streamContent = streamContent;
  }

  public static ODataWritableContentBuilder with(AbstractEntityCollectionObject entitySet, EdmEntityType entityType,
      ODataSerializer serializer, ServiceMetadata metadata,
      EntityCollectionSerializerOptions options) {
    return new ODataWritableContentBuilder(entitySet, entityType, serializer, metadata, options);
  }

  public static ODataWritableContentBuilder with(EntityMediaObject mediaEntity, 
		  FixedFormatSerializerImpl fixedFormatSerializer) {
	  return new ODataWritableContentBuilder(mediaEntity, fixedFormatSerializer);
  }
  
  public static ODataWritableContentBuilder with(InputStream inputStream, 
		  FixedFormatSerializerImpl fixedFormatSerializer) {
	  return new ODataWritableContentBuilder(inputStream, fixedFormatSerializer);
  }
  
  public static class WriteErrorContext implements ODataContentWriteErrorContext {
    private ODataLibraryException exception;

    public WriteErrorContext(ODataLibraryException exception) {
      this.exception = exception;
    }

    @Override
    public Exception getException() {
      return exception;
    }

    @Override
    public ODataLibraryException getODataLibraryException() {
      return exception;
    }
  }

  public static class ODataWritableContentBuilder {
    private ODataSerializer serializer;
    private AbstractEntityCollectionObject entities;
    private ServiceMetadata metadata;
    private EdmEntityType entityType;
    private EntityCollectionSerializerOptions options;
    private FixedFormatSerializerImpl fixedFormatSerializer;
    private EntityMediaObject mediaEntity;
    private InputStream inputStream;

    public ODataWritableContentBuilder(AbstractEntityCollectionObject entities, EdmEntityType entityType,
        ODataSerializer serializer,
        ServiceMetadata metadata, EntityCollectionSerializerOptions options) {
      this.entities = entities;
      this.entityType = entityType;
      this.serializer = serializer;
      this.metadata = metadata;
      this.options = options;
    }
    
    public ODataWritableContentBuilder(EntityMediaObject mediaEntity, FixedFormatSerializerImpl fixedFormatSerializer) {
    	this.mediaEntity = mediaEntity;
    	this.fixedFormatSerializer = fixedFormatSerializer;
    }
    
    public ODataWritableContentBuilder(InputStream inputStream, FixedFormatSerializerImpl fixedFormatSerializer) {
    	this.inputStream = inputStream;
    	this.fixedFormatSerializer = fixedFormatSerializer;
    }

    public ODataContent buildContent() {
      if (serializer instanceof ODataJsonSerializer) {
        StreamContent input = new StreamContentForJson(entities, entityType,
            (ODataJsonSerializer) serializer, metadata, options);
        return new ODataWritableContent(input);
      } else if (fixedFormatSerializer instanceof FixedFormatSerializerImpl) {
    	  StreamContent input;
    	  if (inputStream != null) {
    		  input = new StreamContentForMedia(inputStream, fixedFormatSerializer);
    	  } else {
    		  input = new StreamContentForMedia(mediaEntity, fixedFormatSerializer);
    	  }
    	  return new ODataWritableContent(input);
      }
      throw new ODataRuntimeException("No suitable serializer found");
    }

    public SerializerStreamResult build() {
      return SerializerStreamResultImpl.with().content(buildContent()).build();
    }
  }
}
