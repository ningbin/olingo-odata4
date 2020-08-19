/**************************************************************************
 * (C) 2019-2020 SAP SE or an SAP affiliate company. All rights reserved. *
 **************************************************************************/
package org.apache.olingo.server.core.serializer.json;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.api.edm.xml.Include;
import org.apache.olingo.client.api.edm.xml.Reference;
import org.apache.olingo.client.api.edm.xml.XMLMetadata;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmProvider;
import org.apache.olingo.commons.api.edm.provider.CsdlAliasInfo;
import org.apache.olingo.commons.api.edm.provider.CsdlAnnotations;
import org.apache.olingo.commons.api.edm.provider.CsdlComplexType;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainer;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainerInfo;
import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;
import org.apache.olingo.commons.api.edm.provider.CsdlTerm;
import org.apache.olingo.commons.api.edmx.EdmxReference;
import org.apache.olingo.commons.api.edmx.EdmxReferenceInclude;
import org.apache.olingo.commons.api.ex.ODataException;
import org.apache.olingo.commons.api.format.ContentType;

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
public class ODataEdmProvider extends CsdlAbstractEdmProvider {

	private XMLMetadata xmlMetadata;

	private volatile List<CsdlAliasInfo> csdlAliasInfo = null; // created lazily

	public ODataEdmProvider(InputStream edmxFile) {
		ODataClient client = ODataClientFactory.getClient();
		this.xmlMetadata = client.getDeserializer(ContentType.APPLICATION_XML)
				.toMetadata(edmxFile);
	}
	
	@Override
	public List<CsdlSchema> getSchemas() {
		return xmlMetadata.getSchemas();
	}

	@Override
	public CsdlEntityContainer getEntityContainer() throws ODataException {
		List<CsdlSchema> csdlSchemas = xmlMetadata.getSchemas();
		for (CsdlSchema csdlSchema : csdlSchemas) {
			if (csdlSchema.getEntityContainer() != null) {
				return csdlSchema.getEntityContainer();
			}
		}

		return null;
	}

	@Override
	public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) 
			throws ODataException {
		CsdlEntityContainerInfo entityContainerInfo = new CsdlEntityContainerInfo();
		List<CsdlSchema> csdlSchemas = xmlMetadata.getSchemas();
		for (CsdlSchema csdlSchema : csdlSchemas) {
			if (csdlSchema.getEntityContainer() != null) {
				entityContainerInfo.setContainerName(
						new FullQualifiedName(csdlSchema.getNamespace(), 
								csdlSchema.getEntityContainer().getName()));
				return entityContainerInfo;
			}
		}
		return null;

	}

	public List<EdmxReference> getAllEDMXReference(){
		ArrayList<EdmxReference> edmxReferences = new ArrayList<>();
		List<Reference> referenceList = xmlMetadata.getReferences();
		for (Reference ref : referenceList) {
			EdmxReference reference = new EdmxReference(ref.getUri());
			List<Include> includes = ref.getIncludes();
			for (Include include : includes) {
				reference.addInclude(new EdmxReferenceInclude(include.getNamespace(), 
						include.getAlias()));
			}
			edmxReferences.add(reference);
		}
		return edmxReferences;
	}

	@Override
	public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) 
			throws ODataException {
		List<CsdlSchema> csdlSchemas = xmlMetadata.getSchemas();
		String fqnContainer = null ;
		for (CsdlSchema csdlSchema : csdlSchemas) {
			// Only 1 EntityContainer in on Edmx file (V4 SPecification)
			if (csdlSchema.getEntityContainer() == null) {
				continue;
			}
			fqnContainer = csdlSchema.getNamespace()
					+ (((csdlSchema.getEntityContainer() != null) ? ("." + 
			csdlSchema.getEntityContainer().getName()) : ("")));
			if (fqnContainer.equals(entityContainer.getFullQualifiedNameAsString())) {
				return csdlSchema.getEntityContainer().getEntitySet(entitySetName);
			}

		}
		return null;
	}

	@Override
	public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) 
			throws ODataException {
		/*-- Try to fetch the EntityType from the Current Schema --*/
		List<CsdlSchema> csdlSchemas = xmlMetadata.getSchemas();
		for (CsdlSchema csdlSchema : csdlSchemas) {
			if (csdlSchema.getNamespace().equals(entityTypeName.getNamespace())) {
				return csdlSchema.getEntityType(entityTypeName.getName());
			}
		}
		return null;
	}

	public List<EdmxReference> getReferences() {
		List<Reference> refs = xmlMetadata.getReferences();
		ArrayList<EdmxReference> edmxRefList = null;
		if (!refs.isEmpty()) {
			for (Reference refTemp : refs) {
				EdmxReference reference = new EdmxReference(refTemp.getUri());
				for (Include includeTemp : refTemp.getIncludes()) {
					reference.addInclude(new EdmxReferenceInclude(includeTemp.getNamespace(), 
							includeTemp.getAlias()));
				}
				if (edmxRefList == null) {
					edmxRefList = new ArrayList<>();
					edmxRefList.add(reference);
				} else {
					edmxRefList.add(reference);
				}
			}

			return edmxRefList;
		} else {
			return (new ArrayList<>());
		}
	}

	@Override
	public CsdlComplexType getComplexType(FullQualifiedName complexTypeName) 
			throws ODataException {
		List<CsdlSchema> csdlSchemas = xmlMetadata.getSchemas();
		for (CsdlSchema csdlSchema : csdlSchemas) {
			if (csdlSchema.getNamespace().equals(complexTypeName.getNamespace())) {
				return csdlSchema.getComplexType(complexTypeName.getName());
			}
		}
		return null;
	}

	@Override
	public CsdlAnnotations getAnnotationsGroup(FullQualifiedName targetName, String qualifier) 
			throws ODataException {
		List<CsdlSchema> csdlSchemas = xmlMetadata.getSchemas();
		for (CsdlSchema csdlSchema : csdlSchemas) {
			// FIXME: this seems to be wrong
			return csdlSchema.getAnnotationGroup(targetName.getFullQualifiedNameAsString(), 
					qualifier);
		}
		return null;
	}

	@Override
	public List<CsdlAliasInfo> getAliasInfos() throws ODataException {

		if (csdlAliasInfo == null) {
			List<CsdlAliasInfo> theCsdlAliasInfo = new ArrayList<>();
			List<CsdlSchema> csdlSchemas = xmlMetadata.getSchemas();
			for (CsdlSchema csdlSchema : csdlSchemas) {
				theCsdlAliasInfo.add(new CsdlAliasInfo().setAlias(csdlSchema.getAlias()));
			}
			// prevent that list can be read while it is still being built
			csdlAliasInfo = theCsdlAliasInfo;
		}

		return csdlAliasInfo; // NOSONAR
	}

	@Override
	public CsdlTerm getTerm(FullQualifiedName termName) throws ODataException {
		/*-- Try to fetch the Term from the Current Schema --*/
		List<CsdlSchema> csdlSchemas = xmlMetadata.getSchemas();
		for (CsdlSchema csdlSchema : csdlSchemas) {
			if (csdlSchema.getNamespace().equals(termName.getNamespace())) {
				return csdlSchema.getTerm(termName.getName());
			}
		}
		// Create the Term if not found. Required to show the Term name in metadata
		CsdlTerm csdlTerm = new CsdlTerm();
		csdlTerm.setName(termName.getName());
		csdlTerm.setType(termName.getFullQualifiedNameAsString());
		return csdlTerm;
	}
}
