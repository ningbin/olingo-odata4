/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.commons.core.data;

import java.io.Writer;
import java.util.Collections;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.olingo.commons.api.Constants;
import org.apache.olingo.commons.api.data.Entry;
import org.apache.olingo.commons.api.data.Feed;
import org.apache.olingo.commons.api.data.Link;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.edm.constants.ODataServiceVersion;
import org.apache.olingo.commons.api.format.ContentType;

public class AtomSerializer extends AbstractAtomDealer {

  private static final XMLOutputFactory FACTORY = XMLOutputFactory.newInstance();

  private final AtomPropertySerializer propSerializer;

  public AtomSerializer(final ODataServiceVersion version) {
    super(version);
    this.propSerializer = new AtomPropertySerializer(version);
  }

  private void startDocument(final XMLStreamWriter writer, final String rootElement) throws XMLStreamException {
    writer.writeStartDocument();
    writer.setDefaultNamespace(Constants.NS_ATOM);

    writer.writeStartElement(rootElement);

    namespaces(writer);
  }

  private void property(final Writer outWriter, final Property property) throws XMLStreamException {
    final XMLStreamWriter writer = FACTORY.createXMLStreamWriter(outWriter);

    writer.writeStartDocument();

    propSerializer.property(writer, property);

    writer.writeEndDocument();
    writer.flush();
  }

  private void links(final XMLStreamWriter writer, final List<Link> links) throws XMLStreamException {
    for (Link link : links) {
      writer.writeStartElement(Constants.ATOM_ELEM_LINK);

      if (StringUtils.isNotBlank(link.getRel())) {
        writer.writeAttribute(Constants.ATTR_REL, link.getRel());
      }
      if (StringUtils.isNotBlank(link.getTitle())) {
        writer.writeAttribute(Constants.ATTR_TITLE, link.getTitle());
      }
      if (StringUtils.isNotBlank(link.getHref())) {
        writer.writeAttribute(Constants.ATTR_HREF, link.getHref());
      }
      if (StringUtils.isNotBlank(link.getType())) {
        writer.writeAttribute(Constants.ATTR_TYPE, link.getType());
      }

      if (link.getInlineEntry() != null || link.getInlineFeed() != null) {
        writer.writeStartElement(Constants.PREFIX_METADATA, Constants.ATOM_ELEM_INLINE,
                version.getNamespaceMap().get(ODataServiceVersion.NS_METADATA));

        if (link.getInlineEntry() != null) {
          writer.writeStartElement(Constants.ATOM_ELEM_ENTRY);
          entry(writer, link.getInlineEntry());
          writer.writeEndElement();
        }
        if (link.getInlineFeed() != null) {
          writer.writeStartElement(Constants.ATOM_ELEM_FEED);
          feed(writer, link.getInlineFeed());
          writer.writeEndElement();
        }

        writer.writeEndElement();
      }

      writer.writeEndElement();
    }
  }

  private void common(final XMLStreamWriter writer, final AbstractODataObject object) throws XMLStreamException {
    if (StringUtils.isNotBlank(object.getTitle())) {
      writer.writeStartElement(Constants.ATOM_ELEM_TITLE);
      writer.writeAttribute(Constants.ATTR_TYPE, TYPE_TEXT);
      writer.writeCharacters(object.getTitle());
      writer.writeEndElement();
    }

    if (StringUtils.isNotBlank(object.getSummary())) {
      writer.writeStartElement(Constants.ATOM_ELEM_SUMMARY);
      writer.writeAttribute(Constants.ATTR_TYPE, "text");
      writer.writeCharacters(object.getSummary());
      writer.writeEndElement();
    }
  }

  private void properties(final XMLStreamWriter writer, final List<Property> properties) throws XMLStreamException {
    for (Property property : properties) {
      propSerializer.property(writer, property, false);
    }
  }

  private void entry(final XMLStreamWriter writer, final Entry entry) throws XMLStreamException {
    if (version == ODataServiceVersion.V40 && entry.getContextURL() != null) {
      writer.writeAttribute(
              version.getNamespaceMap().get(ODataServiceVersion.NS_METADATA),
              Constants.CONTEXT,
              entry.getContextURL().toASCIIString());
    }

    if (entry.getBaseURI() != null) {
      writer.writeAttribute(XMLConstants.XML_NS_URI, Constants.ATTR_XML_BASE, entry.getBaseURI().toASCIIString());
    }

    if (StringUtils.isNotBlank(entry.getId())) {
      writer.writeStartElement(Constants.ATOM_ELEM_ID);
      writer.writeCharacters(entry.getId());
      writer.writeEndElement();
    }

    writer.writeStartElement(Constants.ATOM_ELEM_CATEGORY);
    writer.writeAttribute(Constants.ATOM_ATTR_SCHEME, version.getNamespaceMap().get(ODataServiceVersion.NS_SCHEME));
    writer.writeAttribute(Constants.ATOM_ATTR_TERM, entry.getType());
    writer.writeEndElement();

    if (entry instanceof AbstractODataObject) {
      common(writer, (AbstractODataObject) entry);
    }

    links(writer, entry.getAssociationLinks());
    links(writer, entry.getNavigationLinks());
    links(writer, entry.getMediaEditLinks());

    writer.writeStartElement(Constants.ATOM_ELEM_CONTENT);
    if (entry.isMediaEntry()) {
      if (StringUtils.isNotBlank(entry.getMediaContentType())) {
        writer.writeAttribute(Constants.ATTR_TYPE, entry.getMediaContentType());
      }
      if (StringUtils.isNotBlank(entry.getMediaContentSource())) {
        writer.writeAttribute(Constants.ATOM_ATTR_SRC, entry.getMediaContentSource());
      }
      writer.writeEndElement();

      writer.writeStartElement(version.getNamespaceMap().get(ODataServiceVersion.NS_METADATA), Constants.PROPERTIES);
      properties(writer, entry.getProperties());
    } else {
      writer.writeAttribute(Constants.ATTR_TYPE, ContentType.APPLICATION_XML);
      writer.writeStartElement(version.getNamespaceMap().get(ODataServiceVersion.NS_METADATA), Constants.PROPERTIES);
      properties(writer, entry.getProperties());
      writer.writeEndElement();
    }
    writer.writeEndElement();
  }

  private void entry(final Writer outWriter, final Entry entry) throws XMLStreamException {
    final XMLStreamWriter writer = FACTORY.createXMLStreamWriter(outWriter);

    startDocument(writer, Constants.ATOM_ELEM_ENTRY);

    entry(writer, entry);

    writer.writeEndElement();
    writer.writeEndDocument();
    writer.flush();
  }

  private void feed(final XMLStreamWriter writer, final Feed feed) throws XMLStreamException {
    if (version == ODataServiceVersion.V40 && feed.getContextURL() != null) {
      writer.writeAttribute(
              version.getNamespaceMap().get(ODataServiceVersion.NS_METADATA),
              Constants.CONTEXT,
              feed.getContextURL().toASCIIString());
    }

    if (feed.getBaseURI() != null) {
      writer.writeAttribute(XMLConstants.XML_NS_URI, Constants.ATTR_XML_BASE, feed.getBaseURI().toASCIIString());
    }

    if (feed.getCount() != null) {
      writer.writeStartElement(
              version.getNamespaceMap().get(ODataServiceVersion.NS_METADATA), Constants.ATOM_ELEM_COUNT);
      writer.writeCharacters(Integer.toString(feed.getCount()));
      writer.writeEndElement();
    }

    if (StringUtils.isNotBlank(feed.getId())) {
      writer.writeStartElement(Constants.ATOM_ELEM_ID);
      writer.writeCharacters(feed.getId());
      writer.writeEndElement();
    }

    if (feed instanceof AbstractODataObject) {
      common(writer, (AbstractODataObject) feed);
    }

    for (Entry entry : feed.getEntries()) {
      writer.writeStartElement(Constants.ATOM_ELEM_ENTRY);
      entry(writer, entry);
      writer.writeEndElement();
    }

    if (feed.getNext() != null) {
      final LinkImpl next = new LinkImpl();
      next.setRel(Constants.NEXT_LINK_REL);
      next.setHref(feed.getNext().toASCIIString());

      links(writer, Collections.<Link>singletonList(next));
    }
  }

  private void feed(final Writer outWriter, final Feed feed) throws XMLStreamException {
    final XMLStreamWriter writer = FACTORY.createXMLStreamWriter(outWriter);

    startDocument(writer, Constants.ATOM_ELEM_FEED);

    feed(writer, feed);

    writer.writeEndElement();
    writer.writeEndDocument();
    writer.flush();
  }

  private void link(final Writer outWriter, final Link link) throws XMLStreamException {
    final XMLStreamWriter writer = FACTORY.createXMLStreamWriter(outWriter);

    writer.writeStartDocument();

    writer.writeStartElement(Constants.ELEM_LINKS);
    writer.writeDefaultNamespace(version.getNamespaceMap().get(ODataServiceVersion.NS_DATASERVICES));

    writer.writeStartElement(Constants.ELEM_URI);
    writer.writeCharacters(link.getHref());
    writer.writeEndElement();

    writer.writeEndElement();

    writer.writeEndDocument();
    writer.flush();
  }

  public <T> void write(final Writer writer, final T obj) throws XMLStreamException {
    if (obj instanceof Feed) {
      feed(writer, (Feed) obj);
    } else if (obj instanceof Entry) {
      entry(writer, (Entry) obj);
    } else if (obj instanceof Property) {
      property(writer, (Property) obj);
    } else if (obj instanceof Link) {
      link(writer, (Link) obj);
    }
  }
}