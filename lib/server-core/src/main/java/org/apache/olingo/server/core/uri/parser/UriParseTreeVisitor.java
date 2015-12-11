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
package org.apache.olingo.server.core.uri.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.edm.EdmAction;
import org.apache.olingo.commons.api.edm.EdmActionImport;
import org.apache.olingo.commons.api.edm.EdmComplexType;
import org.apache.olingo.commons.api.edm.EdmElement;
import org.apache.olingo.commons.api.edm.EdmEntityContainer;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmEnumType;
import org.apache.olingo.commons.api.edm.EdmFunction;
import org.apache.olingo.commons.api.edm.EdmFunctionImport;
import org.apache.olingo.commons.api.edm.EdmNavigationProperty;
import org.apache.olingo.commons.api.edm.EdmPrimitiveType;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.EdmProperty;
import org.apache.olingo.commons.api.edm.EdmReturnType;
import org.apache.olingo.commons.api.edm.EdmSingleton;
import org.apache.olingo.commons.api.edm.EdmStructuredType;
import org.apache.olingo.commons.api.edm.EdmType;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.constants.EdmTypeKind;
import org.apache.olingo.commons.api.ex.ODataRuntimeException;
import org.apache.olingo.commons.core.edm.primitivetype.EdmPrimitiveTypeFactory;
import org.apache.olingo.server.api.uri.UriInfoKind;
import org.apache.olingo.server.api.uri.UriInfoResource;
import org.apache.olingo.server.api.uri.UriParameter;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.apache.olingo.server.api.uri.UriResourceFunction;
import org.apache.olingo.server.api.uri.UriResourceNavigation;
import org.apache.olingo.server.api.uri.UriResourcePartTyped;
import org.apache.olingo.server.api.uri.UriResourceRoot;
import org.apache.olingo.server.api.uri.queryoption.SelectItem;
import org.apache.olingo.server.api.uri.queryoption.expression.BinaryOperatorKind;
import org.apache.olingo.server.api.uri.queryoption.expression.Expression;
import org.apache.olingo.server.api.uri.queryoption.expression.MethodKind;
import org.apache.olingo.server.api.uri.queryoption.expression.UnaryOperatorKind;
import org.apache.olingo.server.core.uri.UriInfoImpl;
import org.apache.olingo.server.core.uri.UriParameterImpl;
import org.apache.olingo.server.core.uri.UriResourceActionImpl;
import org.apache.olingo.server.core.uri.UriResourceComplexPropertyImpl;
import org.apache.olingo.server.core.uri.UriResourceCountImpl;
import org.apache.olingo.server.core.uri.UriResourceEntitySetImpl;
import org.apache.olingo.server.core.uri.UriResourceFunctionImpl;
import org.apache.olingo.server.core.uri.UriResourceImpl;
import org.apache.olingo.server.core.uri.UriResourceItImpl;
import org.apache.olingo.server.core.uri.UriResourceLambdaAllImpl;
import org.apache.olingo.server.core.uri.UriResourceLambdaAnyImpl;
import org.apache.olingo.server.core.uri.UriResourceLambdaVarImpl;
import org.apache.olingo.server.core.uri.UriResourceNavigationPropertyImpl;
import org.apache.olingo.server.core.uri.UriResourcePrimitivePropertyImpl;
import org.apache.olingo.server.core.uri.UriResourceRefImpl;
import org.apache.olingo.server.core.uri.UriResourceRootImpl;
import org.apache.olingo.server.core.uri.UriResourceSingletonImpl;
import org.apache.olingo.server.core.uri.UriResourceStartingTypeFilterImpl;
import org.apache.olingo.server.core.uri.UriResourceTypedImpl;
import org.apache.olingo.server.core.uri.UriResourceValueImpl;
import org.apache.olingo.server.core.uri.UriResourceWithKeysImpl;
import org.apache.olingo.server.core.uri.antlr.UriLexer;
import org.apache.olingo.server.core.uri.antlr.UriParserBaseVisitor;
import org.apache.olingo.server.core.uri.antlr.UriParserParser;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.AllEOFContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.AllExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.AltAddContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.AltAllContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.AltAndContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.AltAnyContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.AltComparismContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.AltEqualityContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.AltHasContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.AltMultContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.AltOrContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.AnyExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.ArrayOrObjectContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.BinaryLiteralContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.BooleanNonCaseLiteralContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.CastExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.CeilingMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.ConcatMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.ConstSegmentContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.ContainsMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.DateLiteralContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.DateMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.DatetimeoffsetLiteralContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.DayMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.DecimalLiteralContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.DurationLiteralContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.EndsWithMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.EntityEOFContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.EnumLiteralContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.ExpandCountOptionContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.ExpandItemContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.ExpandItemsContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.ExpandItemsEOFContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.ExpandOptionContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.ExpandPathContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.ExpandPathExtensionContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.ExpandRefOptionContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.FilterContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.FilterExpressionEOFContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.FloorMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.FractionalsecondsMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.GeoDistanceMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.GeoIntersectsMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.GeoLengthMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.GuidLiteralContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.HourMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.IndexOfMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.InlinecountContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.IntLiteralContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.IsofExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.LengthMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.LevelsContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.MaxDateTimeMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.MemberExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.MetadataEOFContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.MinDateTimeMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.MinuteMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.MonthMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.NameValueOptListContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.NameValuePairContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.NamespaceContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.NaninfinityLiteralContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.NowMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.NullruleLiteralContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.OrderByContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.OrderByEOFContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.OrderByItemContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.OrderListContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.PathSegmentContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.PathSegmentsContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.PrimitiveLiteralContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.QueryOptionContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.QueryOptionsContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.RootExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.RoundMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.SearchSpecialTokenContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.SecondMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.SelectContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.SelectEOFContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.SelectItemContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.SelectSegmentContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.SkipContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.SkiptokenContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.StartsWithMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.StringLiteralContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.SubstringMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.TimeMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.TimeofdayLiteralContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.ToLowerMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.ToUpperMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.TopContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.TotalOffsetMinutesMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.TotalsecondsMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.TrimMethodCallExprContext;
import org.apache.olingo.server.core.uri.antlr.UriParserParser.YearMethodCallExprContext;
import org.apache.olingo.server.core.uri.parser.UriParserSemanticException.MessageKeys;
import org.apache.olingo.server.core.uri.queryoption.CountOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.ExpandItemImpl;
import org.apache.olingo.server.core.uri.queryoption.ExpandOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.FilterOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.LevelsOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.OrderByItemImpl;
import org.apache.olingo.server.core.uri.queryoption.OrderByOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.QueryOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.SelectItemImpl;
import org.apache.olingo.server.core.uri.queryoption.SelectOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.SkipOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.SkipTokenOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.SystemQueryOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.TopOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.expression.AliasImpl;
import org.apache.olingo.server.core.uri.queryoption.expression.BinaryImpl;
import org.apache.olingo.server.core.uri.queryoption.expression.EnumerationImpl;
import org.apache.olingo.server.core.uri.queryoption.expression.LiteralImpl;
import org.apache.olingo.server.core.uri.queryoption.expression.MemberImpl;
import org.apache.olingo.server.core.uri.queryoption.expression.MethodImpl;
import org.apache.olingo.server.core.uri.queryoption.expression.TypeLiteralImpl;
import org.apache.olingo.server.core.uri.queryoption.expression.UnaryImpl;

/**
 * Converts the URI parse tree as generated by ANTLR into an internal representation
 * suitable to give to an application.
 * While converting the tree is only validated against the EDM if necessary.
 * <br>
 * Attention:
 * <ul>
 * <li>This UriVisitor is at somes point more lax than the original ABNF.</li>
 * <li>It is more tolerable against additional white spaces.</li>
 * </ul>
 * Currently not supported are
 * <ul>
 * <li>parsing the context of $metadata</li>
 * <li>parsing $search</li>
 * </ul>
 */
public class UriParseTreeVisitor extends UriParserBaseVisitor<Object> {

  public UriContext context = null;

  public Edm edm;

  public EdmEntityContainer edmEntityContainer;

  // --- class ---
  public UriParseTreeVisitor(final Edm edm, final UriContext context) {
    this.edm = edm;
    this.context = context;
    edmEntityContainer = edm.getEntityContainer();
  }

  @Override
  protected Object aggregateResult(final Object aggregate, final Object nextResult) {
    if (aggregate != null) {
      return aggregate;
    } else {
      return nextResult;
    }
  }

  private FullQualifiedName getFullNameFromContext(final NamespaceContext vNS, final String odi) {
    String namespace = vNS.getText();
    namespace = namespace.substring(0, namespace.length() - 1); // vNS contains a trailing point that has to be removed
    return new FullQualifiedName(namespace, odi);
  }

  private UriContext.LambdaVariables getLambdaVar(final String odi) {
    for (UriContext.LambdaVariables item : context.allowedLambdaVariables) {
      if (item.name.equals(odi)) {
        return item;
      }
    }
    return null;
  }

  public UriResourceTypedImpl readResourcePathSegment(final PathSegmentContext ctx) {

    final boolean checkFirst =
        context.contextUriInfo.getLastResourcePart() == null
            || context.contextUriInfo.getLastResourcePart() instanceof UriResourceRootImpl;

    String odi = ctx.vODI.getText();

    boolean searchInContainer = true;
    // validate if context type and according property is available
    // otherwise search in container for first element
    if (checkFirst && ctx.vNS == null && !context.contextTypes.isEmpty()) {
      EdmType sourceType = context.contextTypes.peek();
      if (sourceType instanceof EdmStructuredType) {
        EdmStructuredType str = (EdmStructuredType) sourceType;
        EdmElement property = str.getProperty(odi);
        if (property != null) {
          searchInContainer = false;
        }
      }
    }

    if (searchInContainer) {
      final List<UriResource> parts = context.contextUriInfo.getUriResourceParts();
      // check EntitySet
      EdmEntitySet edmEntitySet = edmEntityContainer.getEntitySet(odi);
      if (edmEntitySet != null
          && (parts.isEmpty() || !(parts.get(0) instanceof UriResourcePartTyped)
              || parts.get(0) instanceof UriResourceRoot)) {
        ensureNamespaceIsNull(ctx.vNS);
        context.contextUriInfo.addResourcePart(
            new UriResourceEntitySetImpl().setEntitSet(edmEntitySet));
        return null;
      }

      // check Singleton
      EdmSingleton edmSingleton = edmEntityContainer.getSingleton(odi);
      if (edmSingleton != null
          && (parts.isEmpty() || !(parts.get(0) instanceof UriResourcePartTyped)
              || parts.get(0) instanceof UriResourceRoot)) {
        ensureNamespaceIsNull(ctx.vNS);
        context.contextUriInfo.addResourcePart(
            new UriResourceSingletonImpl().setSingleton(edmSingleton));
        return null;
      }

      // check ActionImport
      EdmActionImport edmActionImport = edmEntityContainer.getActionImport(odi);
      if (edmActionImport != null
          && (parts.isEmpty() || !(parts.get(0) instanceof UriResourcePartTyped)
              || parts.get(0) instanceof UriResourceRoot)) {
        ensureNamespaceIsNull(ctx.vNS);
        context.contextUriInfo.addResourcePart(
            new UriResourceActionImpl().setActionImport(edmActionImport));
        return null;
      }

      // check FunctionImport
      EdmFunctionImport edmFunctionImport = edmEntityContainer.getFunctionImport(odi);
      
      if(edmFunctionImport != null && context.contextReadingQueryPart) {
        throw wrap(new UriParserSemanticException("Function Imports are not allowed in $filter or $orderby", 
            UriParserSemanticException.MessageKeys.FUNCTION_IMPORT_NOT_ALLOWED, odi));
      }
      
      if (edmFunctionImport != null
          && (parts.isEmpty() || !(parts.get(0) instanceof UriResourcePartTyped)
              || parts.get(0) instanceof UriResourceRoot)) {

        // read the URI parameters
        if (ctx.vlNVO.isEmpty()) {
          throw wrap(new UriParserSyntaxException(
              "Function imports must have a (possibly empty) parameter list written in parentheses",
              UriParserSyntaxException.MessageKeys.SYNTAX));
        }
        context.contextReadingFunctionParameters = true;
        @SuppressWarnings("unchecked")
        List<UriParameter> parameters = (List<UriParameter>) ctx.vlNVO.get(0).accept(this);
        context.contextReadingFunctionParameters = false;

        // mark parameters as consumed
        ctx.vlNVO.remove(0);

        UriResourceFunctionImpl uriResource = new UriResourceFunctionImpl()
            .setFunctionImport(edmFunctionImport, parameters);

        // collect parameter names
        List<String> names = new ArrayList<String>();
        for (UriParameter item : parameters) {
          names.add(item.getName());
        }

        // get function from function import
        EdmFunction function = edmFunctionImport.getUnboundFunction(names);
        if (function == null) {
          StringBuilder tmp = new StringBuilder();
          for (String name : names) {
            tmp.append((tmp.length() != 0 ? "," : "")).append(name);
          }
          throw wrap(new UriParserSemanticException("Function of functionimport '" + edmFunctionImport.getName()
              + "' with parameters [" + tmp.toString() + "] not found",
              UriParserSemanticException.MessageKeys.FUNCTION_NOT_FOUND, edmFunctionImport.getName(), tmp.toString()));
        }

        ensureNamespaceIsNull(ctx.vNS);
        uriResource.setFunction(edmFunctionImport.getUnboundFunction(names));
        context.contextUriInfo.addResourcePart(uriResource);
        return null;
      }
    }

    EdmType sourceType;
    boolean sourceIsCollection = false;
    final UriResource lastResourcePart = context.contextUriInfo.getLastResourcePart();

    if (lastResourcePart == null) {
      if (context.contextTypes.isEmpty()) {
        if (checkFirst && ctx.vNS == null) {
          throw wrap(new UriParserSemanticException(
              "Cannot find EntitySet, Singleton, ActionImport or FunctionImport with name '" + odi + "'.",
              UriParserSemanticException.MessageKeys.RESOURCE_NOT_FOUND, odi));
        }
        throw wrap(new UriParserSemanticException(
            "Resource part '" + odi + "' can only applied on typed resource parts",
            UriParserSemanticException.MessageKeys.RESOURCE_PART_ONLY_FOR_TYPED_PARTS, odi));
      }
      sourceType = context.contextTypes.peek();
      sourceIsCollection = context.isCollection;
    } else if (lastResourcePart instanceof UriResourcePartTyped) {
      sourceType = Parser.getTypeInformation((UriResourcePartTyped) lastResourcePart);
      sourceIsCollection = ((UriResourcePartTyped) lastResourcePart).isCollection();
    } else {
      throw wrap(new UriParserSemanticException(
          "Resource part '" + odi + "' can only be applied on typed resource parts.",
          UriParserSemanticException.MessageKeys.RESOURCE_PART_ONLY_FOR_TYPED_PARTS, odi));
    }

    if (ctx.vNS == null) { // without namespace

      // first check for lambda variable because a newly add property should not shadow a long used lambda variable
      UriContext.LambdaVariables lVar = getLambdaVar(odi);
      if (lVar != null) {
        UriResourceLambdaVarImpl lambdaResource = new UriResourceLambdaVarImpl();
        lambdaResource.setVariableText(lVar.name);
        lambdaResource.setType(lVar.type);
        lambdaResource.setCollection(lVar.isCollection);
        context.contextUriInfo.addResourcePart(lambdaResource);
        return null;
      }

      if (!(sourceType instanceof EdmStructuredType)) {
        throw wrap(new UriParserSemanticException(
            "Cannot parse '" + odi + "'; previous path segment is not a structural type.",
            UriParserSemanticException.MessageKeys.RESOURCE_PART_MUST_BE_PRECEDED_BY_STRUCTURAL_TYPE, odi));
      }

      if ((ctx.depth() <= 2 // path evaluation for the resource path
          || lastResourcePart instanceof UriResourceTypedImpl
          || lastResourcePart instanceof UriResourceNavigationPropertyImpl)
          && sourceIsCollection) {
        throw wrap(new UriParserSemanticException("Property '" + odi + "' is not allowed after collection.",
            UriParserSemanticException.MessageKeys.PROPERTY_AFTER_COLLECTION, odi));
      }

      EdmStructuredType structType = (EdmStructuredType) sourceType;

      EdmElement property = structType.getProperty(odi);
      if (property == null) {
        throw wrap(new UriParserSemanticException("Property '" + odi + "' not found in type '"
            + structType.getFullQualifiedName().getFullQualifiedNameAsString() + "'",
            ctx.depth() > 2 ? // path evaluation inside an expression or for the resource path?
                UriParserSemanticException.MessageKeys.EXPRESSION_PROPERTY_NOT_IN_TYPE
                : UriParserSemanticException.MessageKeys.PROPERTY_NOT_IN_TYPE,
            structType.getFullQualifiedName().getFullQualifiedNameAsString(), odi));
      }

      if (property instanceof EdmProperty) {
        if (((EdmProperty) property).isPrimitive()
            || property.getType().getKind() == EdmTypeKind.ENUM
            || property.getType().getKind() == EdmTypeKind.DEFINITION) {
          // create simple property
          UriResourcePrimitivePropertyImpl simpleResource = new UriResourcePrimitivePropertyImpl()
              .setProperty((EdmProperty) property);
          context.contextUriInfo.addResourcePart(simpleResource);
          return null;
        } else {
          // create complex property
          UriResourceComplexPropertyImpl complexResource = new UriResourceComplexPropertyImpl()
              .setProperty((EdmProperty) property);
          context.contextUriInfo.addResourcePart(complexResource);
          return null;
        }
      } else if (property instanceof EdmNavigationProperty) {
        // create navigation property
        if (context.contextVisitExpandResourcePath && ctx.vlNVO.size() > 0) {
          throw wrap(new UriParserSemanticException(
              "Navigation properties in expand system query options must not be followed by a key.",
              UriParserSemanticException.MessageKeys.KEY_NOT_ALLOWED));
        }

        UriResourceNavigationPropertyImpl navigationResource = new UriResourceNavigationPropertyImpl()
            .setNavigationProperty((EdmNavigationProperty) property);
        context.contextUriInfo.addResourcePart(navigationResource);
        return null;
      } else {
        throw wrap(new UriParserSemanticException("Unkown type for property '" + property + "'",
            UriParserSemanticException.MessageKeys.UNKNOWN_PROPERTY_TYPE, property.getName()));
      }

    } else { // with namespace

      FullQualifiedName fullFilterName = getFullNameFromContext(ctx.vNS, odi);

      // EdmType lastType = getLastType(lastTyped);
      if (sourceType instanceof EdmEntityType) {

        EdmEntityType filterEntityType = edm.getEntityType(fullFilterName);
        if (filterEntityType != null) {
          // is entity type cast
          if (!(filterEntityType.compatibleTo(sourceType))) {
            throw wrap(new UriParserSemanticException(
                "Entity typefilter not compatible to previous path segment: " + fullFilterName.toString(),
                UriParserSemanticException.MessageKeys.INCOMPATIBLE_TYPE_FILTER, fullFilterName.toString()));
          }

          if (lastResourcePart == null) {
            // this may be the case if a member expression within a filter starts with a typeCast
            UriResourceStartingTypeFilterImpl uriResource = new UriResourceStartingTypeFilterImpl()
                .setType(filterEntityType)
                .setCollection(sourceIsCollection);
            if (sourceIsCollection) {
              uriResource.setCollectionTypeFilter(filterEntityType);
            } else {
              uriResource.setEntryTypeFilter(filterEntityType);
            }
            context.contextUriInfo.addResourcePart(uriResource);
            return null;
          } else {

            // check if last segment may contain key properties
            if (lastResourcePart instanceof UriResourceWithKeysImpl) {
              UriResourceWithKeysImpl lastPartWithKeys = (UriResourceWithKeysImpl) lastResourcePart;

              if (!lastPartWithKeys.isCollection()) {
                if (lastPartWithKeys.getTypeFilterOnEntry() != null) {
                  throw wrap(new UriParserSemanticException("Entry typefilters are not chainable, used '"
                      + getName(filterEntityType) + "' behind '"
                      + getName(lastPartWithKeys.getTypeFilterOnEntry()) + "'",
                      UriParserSemanticException.MessageKeys.TYPE_FILTER_NOT_CHAINABLE,
                      getName(lastPartWithKeys.getTypeFilterOnEntry()), getName(filterEntityType)));
                }
                lastPartWithKeys.setEntryTypeFilter(filterEntityType);
                return null;
              } else {
                if (lastPartWithKeys.getTypeFilterOnCollection() != null) {
                  throw wrap(new UriParserSemanticException("Collection typefilters are not chainable, used '"
                      + getName(filterEntityType) + "' behind '"
                      + getName(lastPartWithKeys.getTypeFilterOnCollection()) + "'",
                      UriParserSemanticException.MessageKeys.TYPE_FILTER_NOT_CHAINABLE,
                      getName(lastPartWithKeys.getTypeFilterOnCollection()), getName(filterEntityType)));
                }
                lastPartWithKeys.setCollectionTypeFilter(filterEntityType);
                return null;
              }
            } else if (lastResourcePart instanceof UriResourceTypedImpl) {
              UriResourceTypedImpl lastPartTyped = (UriResourceTypedImpl) lastResourcePart;
              if (lastPartTyped.getTypeFilter() != null) {
                throw wrap(new UriParserSemanticException("Typefilters are not chainable, used '"
                    + getName(filterEntityType) + "' behind '"
                    + getName(lastPartTyped.getTypeFilter()) + "'",
                    UriParserSemanticException.MessageKeys.TYPE_FILTER_NOT_CHAINABLE,
                    getName(lastPartTyped.getTypeFilter()), getName(filterEntityType)));
              }

              lastPartTyped.setTypeFilter(filterEntityType);
              return null;
            } else {
              throw wrap(new UriParserSemanticException("Path segment before '" + getName(filterEntityType)
                  + "' not typed",
                  UriParserSemanticException.MessageKeys.PREVIOUS_PART_NOT_TYPED, getName(filterEntityType)));
            }
          }
        }

      } else if (sourceType instanceof EdmComplexType) {

        EdmComplexType filterComplexType = edm.getComplexType(fullFilterName);

        if (filterComplexType != null) {

          // is complex type cast
          if (!(filterComplexType.compatibleTo(sourceType))) {
            throw wrap(new UriParserSemanticException(
                "Complex typefilter '" + getName(sourceType) + "'not compatible type of previous path segment '"
                    + getName(filterComplexType) + "'",
                UriParserSemanticException.MessageKeys.INCOMPATIBLE_TYPE_FILTER, getName(sourceType)));
          }

          // is simple complex type cast
          if (lastResourcePart == null) {
            // this may be the case if a member expression within a filter starts with a typeCast
            UriResourceStartingTypeFilterImpl uriResource = new UriResourceStartingTypeFilterImpl()
                .setType(filterComplexType)
                .setCollection(sourceIsCollection);

            if (sourceIsCollection) {
              uriResource.setCollectionTypeFilter(filterComplexType);
            } else {
              uriResource.setEntryTypeFilter(filterComplexType);
            }
            context.contextUriInfo.addResourcePart(uriResource);
            return null;
          } else {
            if (lastResourcePart instanceof UriResourceWithKeysImpl) {
              // e.g. in case of function returning complex data or a list of complex data
              UriResourceWithKeysImpl lastPartWithKeys = (UriResourceWithKeysImpl) lastResourcePart;

              if (!lastPartWithKeys.isCollection()) {
                if (lastPartWithKeys.getTypeFilterOnEntry() != null) {
                  throw wrap(new UriParserSemanticException("Entry typefilters are not chainable, used '"
                      + getName(filterComplexType) + "' behind '"
                      + getName(lastPartWithKeys.getTypeFilterOnEntry()) + "'",
                      UriParserSemanticException.MessageKeys.TYPE_FILTER_NOT_CHAINABLE,
                      getName(lastPartWithKeys.getTypeFilterOnEntry()), getName(filterComplexType)));
                }
                lastPartWithKeys.setEntryTypeFilter(filterComplexType);
                return null;
              } else {
                if (lastPartWithKeys.getTypeFilterOnCollection() != null) {
                  throw wrap(new UriParserSemanticException("Collection typefilters are not chainable, used '"
                      + getName(filterComplexType) + "' behind '"
                      + getName(lastPartWithKeys.getTypeFilterOnCollection()) + "'",
                      UriParserSemanticException.MessageKeys.TYPE_FILTER_NOT_CHAINABLE,
                      getName(lastPartWithKeys.getTypeFilterOnCollection()), getName(filterComplexType)));
                }
                lastPartWithKeys.setCollectionTypeFilter(filterComplexType);
                return null;
              }

            } else if (lastResourcePart instanceof UriResourceTypedImpl) {
              UriResourceTypedImpl lastPartTyped = (UriResourceTypedImpl) lastResourcePart;
              if (lastPartTyped.getTypeFilter() != null) {
                throw wrap(new UriParserSemanticException("Typefilters are not chainable, used '"
                    + getName(filterComplexType) + "' behind '"
                    + getName(lastPartTyped.getTypeFilter()) + "'",
                    UriParserSemanticException.MessageKeys.TYPE_FILTER_NOT_CHAINABLE,
                    getName(lastPartTyped.getTypeFilter()), getName(filterComplexType)));
              }

              lastPartTyped.setTypeFilter(filterComplexType);
              return null;
            } else {
              throw wrap(new UriParserSemanticException("Path segment before '" + getName(filterComplexType)
                  + "' not typed",
                  UriParserSemanticException.MessageKeys.PREVIOUS_PART_NOT_TYPED, getName(filterComplexType)));
            }
          }
        }
      }

      FullQualifiedName fullBindingTypeName = sourceType.getFullQualifiedName();

      // check for action
      EdmAction action = edm.getBoundAction(fullFilterName, fullBindingTypeName, sourceIsCollection);
      if (action != null) {
        UriResourceActionImpl pathInfoAction = new UriResourceActionImpl();
        pathInfoAction.setAction(action);
        context.contextUriInfo.addResourcePart(pathInfoAction);
        return null;
      }

      // do a check for bound functions (which requires a parameter list)
      if (ctx.vlNVO.size() == 0) {
        throw wrap(new UriParserSemanticException("Unknown type for type cast " + fullFilterName.toString()
            + " not found", UriParserSemanticException.MessageKeys.UNKNOWN_TYPE, fullFilterName.toString()));
      }

      context.contextReadingFunctionParameters = true;
      @SuppressWarnings("unchecked")
      List<UriParameter> parameters = (List<UriParameter>) ctx.vlNVO.get(0).accept(this);
      context.contextReadingFunctionParameters = false;

      // get names of function parameters
      List<String> names = new ArrayList<String>();
      for (UriParameter item : parameters) {
        names.add(item.getName());
      }

      EdmFunction function = edm.getBoundFunction(fullFilterName, fullBindingTypeName, sourceIsCollection, names);

      if (function != null) {
        UriResourceFunctionImpl pathInfoFunction = new UriResourceFunctionImpl()
            .setFunction(function)
            .setParameters(parameters);
        context.contextUriInfo.addResourcePart(pathInfoFunction);

        // mark parameters as consumed
        ctx.vlNVO.remove(0);
        return null;
      }

      // check for unbound function in the $filter case ( where the previous resource segment is a $it)
      function = edm.getUnboundFunction(fullFilterName, names);

      if (function != null) {
        UriResourceFunctionImpl pathInfoFunction = new UriResourceFunctionImpl()
            .setFunction(function)
            .setParameters(parameters);
        context.contextUriInfo.addResourcePart(pathInfoFunction);

        // mark parameters as consumed
        ctx.vlNVO.remove(0);
        return null;
      }

      throw wrap(new UriParserSemanticException("Unknown resource path segment:" + fullFilterName.toString(),
          UriParserSemanticException.MessageKeys.UNKNOWN_PART, fullFilterName.toString()));
    }
  }

  /**
   * Ensures that the namespace of the first resource parts is null
   * @param vNS namespace or null
   */
  private void ensureNamespaceIsNull(final NamespaceContext vNS) {
    if (vNS != null && context.contextUriInfo.getLastResourcePart() == null) {
      // First resource part and namespace is not null!
      throw wrap(new UriParserSemanticException("Namespace is not allowed for EntitySets, Singeltons, "
          + " Action Imports and Function Imports. Found " + vNS.getText(),
          UriParserSemanticException.MessageKeys.NAMESPACE_NOT_ALLOWED_AT_FIRST_ELEMENT, vNS.getText()));
    }
  }

  private String getName(final EdmType type) {
    return type.getFullQualifiedName().getFullQualifiedNameAsString();
  }

  @Override
  public Object visitAllEOF(final AllEOFContext ctx) {
    context.contextUriInfo = new UriInfoImpl().setKind(UriInfoKind.all);
    return null;
  }

  @Override
  public Object visitAllExpr(final AllExprContext ctx) {
    UriResourceLambdaAllImpl all = new UriResourceLambdaAllImpl();

    UriResource obj = context.contextUriInfo.getLastResourcePart();
    if (!(obj instanceof UriResourcePartTyped)) {
      throw wrap(new UriParserSemanticException("all only allowed on typed path segments",
          UriParserSemanticException.MessageKeys.ONLY_FOR_TYPED_PARTS, "all"));
    } else if (obj instanceof UriResourceNavigation) {
      if (!((UriResourceNavigation) obj).getKeyPredicates().isEmpty()) {
        throw wrap(new UriParserSemanticException(
            "Any lamdba expression must not be following navigation properties with key predicates.",
            UriParserSemanticException.MessageKeys.KEY_NOT_ALLOWED));
      }
    }

    UriContext.LambdaVariables var = new UriContext.LambdaVariables();
    var.name = ctx.vLV.getText();
    var.type = Parser.getTypeInformation((UriResourcePartTyped) obj);
    var.isCollection = false;

    all.setLamdaVariable(ctx.vLV.getText());
    context.allowedLambdaVariables.push(var);
    all.setExpression((Expression) ctx.vLE.accept(this));
    context.allowedLambdaVariables.pop();
    return all;
  }

  @Override
  public Expression visitAltAdd(final AltAddContext ctx) {
    int tokenIndex = ctx.vO.getType();
    return new BinaryImpl(
        (Expression) ctx.vE1.accept(this),
        tokenIndex == UriLexer.ADD ? BinaryOperatorKind.ADD : BinaryOperatorKind.SUB,
        (Expression) ctx.vE2.accept(this));
  }

  @Override
  public Object visitAltAll(final AltAllContext ctx) {

    UriInfoImpl uriInfoImplpath = new UriInfoImpl().setKind(UriInfoKind.resource);

    uriInfoImplpath.addResourcePart((UriResourceImpl) super.visitAltAll(ctx));

    EdmType startType = removeUriResourceStartingTypeFilterImpl(uriInfoImplpath);

    return new MemberImpl(uriInfoImplpath, startType);
  }

  private EdmType removeUriResourceStartingTypeFilterImpl(final UriInfoImpl uriInfoImplpath) {

    List<UriResource> segments = uriInfoImplpath.getUriResourceParts();
    if (segments.size() == 0) {
      return null;
    }

    UriResource segment = segments.get(0);
    if (segment instanceof UriResourceStartingTypeFilterImpl) {
      UriResourceStartingTypeFilterImpl startingTypeFilter = (UriResourceStartingTypeFilterImpl) segment;

      EdmType type = null;
      if (startingTypeFilter.getTypeFilterOnEntry() != null) {
        type = startingTypeFilter.getTypeFilterOnEntry();
      } else if (startingTypeFilter.getTypeFilterOnCollection() != null) {
        type = startingTypeFilter.getTypeFilterOnCollection();
      } else {
        type = startingTypeFilter.getType();
      }

      uriInfoImplpath.removeResourcePart(0);
      return type;
    }

    return null;
  }

  @Override
  public Expression visitAltAnd(final AltAndContext ctx) {
    return new BinaryImpl(
        (Expression) ctx.vE1.accept(this),
        BinaryOperatorKind.AND,
        (Expression) ctx.vE2.accept(this));
  }

  @Override
  public Object visitAltAny(final AltAnyContext ctx) {
    UriInfoImpl uriInfoImplpath = new UriInfoImpl().setKind(UriInfoKind.resource);

    uriInfoImplpath.addResourcePart((UriResourceImpl) super.visitAltAny(ctx));

    EdmType startType = removeUriResourceStartingTypeFilterImpl(uriInfoImplpath);

    return new MemberImpl(uriInfoImplpath, startType);
  }

  @Override
  public Expression visitAltComparism(final AltComparismContext ctx) {
    int tokenIndex = ctx.vO.getType();
    BinaryOperatorKind kind = null;
    if (tokenIndex == UriLexer.GT) {
      kind = BinaryOperatorKind.GT;
    } else if (tokenIndex == UriLexer.GE) {
      kind = BinaryOperatorKind.GE;
    } else if (tokenIndex == UriLexer.LT) {
      kind = BinaryOperatorKind.LT;
    } else if (tokenIndex == UriLexer.LE) {
      kind = BinaryOperatorKind.LE;
    }
    return new BinaryImpl(
        (Expression) ctx.vE1.accept(this),
        kind,
        (Expression) ctx.vE2.accept(this));
  }

  @Override
  public Object visitEntityEOF(final EntityEOFContext ctx) {
    String odi = ctx.vODI.getText();
    FullQualifiedName fullName = getFullNameFromContext(ctx.vNS, odi);

    EdmEntityType type = edm.getEntityType(fullName);
    if (type == null) {
      throw wrap(new UriParserSemanticException("Expected EntityTypeName",
          UriParserSemanticException.MessageKeys.UNKNOWN_ENTITY_TYPE, fullName.toString()));
    }
    context.contextUriInfo.setEntityTypeCast(type);

    // contextUriInfo = uriInfo;
    context.contextTypes.push(context.contextUriInfo.getEntityTypeCast());
    context.isCollection = true;  // TODO: check!

    return null;
  }

  @Override
  public Expression visitAltEquality(final AltEqualityContext ctx) {
    int tokenIndex = ctx.vO.getType();
    return new BinaryImpl(
        (Expression) ctx.vE1.accept(this),
        tokenIndex == UriLexer.EQ_ALPHA ? BinaryOperatorKind.EQ : BinaryOperatorKind.NE,
        (Expression) ctx.vE2.accept(this));
  }

  @Override
  public Object visitAltHas(final AltHasContext ctx) {
    return new BinaryImpl(
        (Expression) ctx.vE1.accept(this),
        BinaryOperatorKind.HAS,
        (Expression) ctx.vE2.accept(this));
  }

  @Override
  public Object visitMetadataEOF(final MetadataEOFContext ctx) {

    context.contextUriInfo = new UriInfoImpl().setKind(UriInfoKind.metadata);
    return null;
  }

  @Override
  public Expression visitAltMult(final AltMultContext ctx) {
    int tokenIndex = ctx.vO.getType();
    BinaryOperatorKind kind;
    if (tokenIndex == UriLexer.MUL) {
      kind = BinaryOperatorKind.MUL;
    } else if (tokenIndex == UriLexer.DIV) {
      kind = BinaryOperatorKind.DIV;
    } else {
      kind = BinaryOperatorKind.MOD;
    }
    return new BinaryImpl(
        (Expression) ctx.vE1.accept(this),
        kind,
        (Expression) ctx.vE2.accept(this));
  }

  @Override
  public Expression visitAltOr(final AltOrContext ctx) {
    return new BinaryImpl(
        (Expression) ctx.vE1.accept(this),
        BinaryOperatorKind.OR,
        (Expression) ctx.vE2.accept(this));
  }

  @Override
  public Object visitAnyExpr(final AnyExprContext ctx) {
    UriResourceLambdaAnyImpl any = new UriResourceLambdaAnyImpl();
    if (ctx.vLV != null) {
      UriResourceImpl lastResourcePart = (UriResourceImpl) context.contextUriInfo.getLastResourcePart();
      if (!(lastResourcePart instanceof UriResourcePartTyped)) {
        throw wrap(new UriParserSemanticException("any only allowed on typed path segments",
            UriParserSemanticException.MessageKeys.ONLY_FOR_TYPED_PARTS, "any"));
      } else if (lastResourcePart instanceof UriResourceNavigation) {
        if (!((UriResourceNavigation) lastResourcePart).getKeyPredicates().isEmpty()) {
          throw wrap(new UriParserSemanticException(
              "Any lamdba expression must not be following navigation properties with key predicates",
              UriParserSemanticException.MessageKeys.KEY_NOT_ALLOWED));
        }
      }

      UriContext.LambdaVariables var = new UriContext.LambdaVariables();
      var.name = ctx.vLV.getText();
      var.type = Parser.getTypeInformation((UriResourcePartTyped) lastResourcePart);
      var.isCollection = false;

      any.setLamdaVariable(ctx.vLV.getText());
      context.allowedLambdaVariables.push(var);
      any.setExpression((Expression) ctx.vLE.accept(this));
      context.allowedLambdaVariables.pop();
    }
    return any;
  }

  @Override
  public Object visitBooleanNonCaseLiteral(final BooleanNonCaseLiteralContext ctx) {
    final String text = ctx.getText().toLowerCase();
    return new LiteralImpl(text.equals("false") ? "false" : "true",
        EdmPrimitiveTypeFactory.getInstance(EdmPrimitiveTypeKind.Boolean));
  }

  @Override
  public Expression visitCastExpr(final CastExprContext ctx) {
    List<Expression> parameters = new ArrayList<Expression>();
    if (ctx.vE1 != null) {
      // is optional parameter
      parameters.add((Expression) ctx.vE1.accept(this));
    }

    String namespace = ctx.vNS.getText();
    namespace = namespace.substring(0, namespace.length() - 1);

    FullQualifiedName fullName = new FullQualifiedName(namespace, ctx.vODI.getText());
    EdmType type = getType(fullName);
    parameters.add(new TypeLiteralImpl(type));
    return new MethodImpl(MethodKind.CAST, parameters);
  }

  private EdmType getType(final FullQualifiedName fullName) {
    EdmType type = null;

    type = edm.getEntityType(fullName);
    if (type != null) {
      return type;
    }

    type = edm.getComplexType(fullName);
    if (type != null) {
      return type;
    }

    type = edm.getTypeDefinition(fullName);
    if (type != null) {
      return type;
    }

    type = edm.getEnumType(fullName);
    if (type != null) {
      return type;
    }

    if (fullName.getNamespace().equals(EdmPrimitiveType.EDM_NAMESPACE)) {
      final EdmPrimitiveTypeKind typeKind = EdmPrimitiveTypeKind.valueOf(fullName.getName());
      type = EdmPrimitiveTypeFactory.getInstance(typeKind);
      if (type != null) {
        return type;
      }
    }

    return null;

  }

  @Override
  public Expression visitCeilingMethodCallExpr(final CeilingMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.CEILING, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Expression visitConcatMethodCallExpr(final ConcatMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.CONCAT, Arrays.asList(
        (Expression) ctx.vE1.accept(this),
        (Expression) ctx.vE2.accept(this)));
  }

  @Override
  public Object visitConstSegment(final ConstSegmentContext ctx) {
    UriInfoImpl uriInfoResource = context.contextUriInfo;
    UriResource pathInfo = uriInfoResource.getLastResourcePart();

    if (ctx.vV != null) {
      if (pathInfo instanceof UriResourcePartTyped) {
        if (!((UriResourcePartTyped) pathInfo).isCollection()) {
          context.contextUriInfo.addResourcePart(new UriResourceValueImpl());
        } else {
          throw wrap(new UriParserSemanticException("$value only allowed on typed path segments",
              UriParserSemanticException.MessageKeys.ONLY_FOR_TYPED_PARTS, "$value"));
        }
        return null;
      } else {
        throw wrap(new UriParserSemanticException("$value only allowed on typed path segments",
            UriParserSemanticException.MessageKeys.ONLY_FOR_TYPED_PARTS, "$value"));
      }

    } else if (ctx.vC != null) {
      if (pathInfo instanceof UriResourcePartTyped) {
        if (((UriResourcePartTyped) pathInfo).isCollection()) {
          context.contextUriInfo.addResourcePart(new UriResourceCountImpl());
        } else {
          throw wrap(new UriParserSemanticException("$count only allowed on collection properties",
              UriParserSemanticException.MessageKeys.ONLY_FOR_COLLECTIONS, "$count"));
        }
      } else {
        throw wrap(new UriParserSemanticException("$count only allowed on typed properties",
            UriParserSemanticException.MessageKeys.ONLY_FOR_TYPED_PARTS, "$count"));
      }
    } else if (ctx.vR != null) {
      if (pathInfo instanceof UriResourcePartTyped) {
        EdmType type = ((UriResourcePartTyped) pathInfo).getType();
        if (type instanceof EdmEntityType) {
          context.contextUriInfo.addResourcePart(new UriResourceRefImpl());
        } else {
          throw wrap(new UriParserSemanticException("$ref only allowed on entity types",
              UriParserSemanticException.MessageKeys.ONLY_FOR_ENTITY_TYPES, "$ref"));
        }
      } else {
        throw wrap(new UriParserSemanticException("$ref only allowed on typed properties",
            UriParserSemanticException.MessageKeys.ONLY_FOR_TYPED_PROPERTIES, "$ref"));
      }

    } else if (ctx.vAll != null) {
      context.contextUriInfo.addResourcePart((UriResourceLambdaAllImpl) ctx.vAll.accept(this));
    } else if (ctx.vAny != null) {
      context.contextUriInfo.addResourcePart((UriResourceLambdaAnyImpl) ctx.vAny.accept(this));
    }
    return null;
  }

  @Override
  public Expression visitContainsMethodCallExpr(final ContainsMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.CONTAINS, Arrays.asList(
        (Expression) ctx.vE1.accept(this),
        (Expression) ctx.vE2.accept(this)));
  }

  @Override
  public Object visitDateMethodCallExpr(final DateMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.DATE, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Expression visitDayMethodCallExpr(final DayMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.DAY, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Expression visitGeoDistanceMethodCallExpr(final GeoDistanceMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.GEODISTANCE, Arrays.asList(
        (Expression) ctx.vE1.accept(this),
        (Expression) ctx.vE2.accept(this)));
  }

  @Override
  public Object visitEndsWithMethodCallExpr(final EndsWithMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.ENDSWITH, Arrays.asList(
        (Expression) ctx.vE1.accept(this),
        (Expression) ctx.vE2.accept(this)));
  }

  @Override
  public Object visitEnumLiteral(final EnumLiteralContext ctx) {
    // get type
    final String odi = ctx.vODI.getText();

    final FullQualifiedName fullName = getFullNameFromContext(ctx.vNS, odi);
    final EdmEnumType edmEnumType = edm.getEnumType(fullName);
    if (edmEnumType == null) {
      throw wrap(new UriParserSemanticException(
          "Enum type '" + fullName.getFullQualifiedNameAsString() + "' not found!",
          UriParserSemanticException.MessageKeys.UNKNOWN_TYPE, fullName.getFullQualifiedNameAsString()));
    }

    String valueString = ctx.vValues.getText();
    valueString = valueString.substring(1, valueString.length() - 1);
    String[] values = valueString.split(",");
    return new EnumerationImpl(edmEnumType, Arrays.asList(values));
  }

  @Override
  public Object visitExpandItems(final ExpandItemsContext ctx) {
    ExpandOptionImpl expand = new ExpandOptionImpl();
    expand.setText(ctx.getText());
    for (ExpandItemContext eI : ctx.vlEI) {
      expand.addExpandItem((ExpandItemImpl) eI.accept(this));
    }

    return expand;
  }

  @Override
  public Object visitExpandItem(final ExpandItemContext ctx) {

    ExpandItemImpl expandItem = null;
    if (ctx.vS != null) {
      expandItem = new ExpandItemImpl().setIsStar(true);
      if (ctx.vR != null) {
        expandItem.setIsRef(true);
      } else if (ctx.vM != null) {
        LevelsOptionImpl levels = new LevelsOptionImpl().setMax();
        levels.setText(ctx.vM.getText());
        try {
          expandItem.setSystemQueryOption(levels);
        } catch (ODataRuntimeException e) {
          // Thrown if duplicated system query options are detected
          throw wrap(new UriParserSyntaxException("Double system query option!", e,
              UriParserSyntaxException.MessageKeys.DOUBLE_SYSTEM_QUERY_OPTION, e.getMessage()));
        }
      } else if (ctx.vL != null) {
        LevelsOptionImpl levels = new LevelsOptionImpl();
        String text = ctx.vL.getText();
        levels.setText(text);
        levels.setValue(Integer.parseInt(text));
        try {
          expandItem.setSystemQueryOption(levels);
        } catch (ODataRuntimeException e) {
          // Thrown if duplicated system query options are detected
          throw wrap(new UriParserSyntaxException("Double system query option!", e,
              UriParserSyntaxException.MessageKeys.DOUBLE_SYSTEM_QUERY_OPTION, e.getMessage()));
        }
      }

    } else if (ctx.vEP != null) {
      expandItem = (ExpandItemImpl) ctx.vEP.accept(this);

      if (ctx.vEPE != null) {
        ExpandItemImpl contextExpandItemPathBU = context.contextExpandItemPath;
        context.contextExpandItemPath = expandItem;

        @SuppressWarnings("unchecked")
        List<SystemQueryOptionImpl> list = (List<SystemQueryOptionImpl>) ctx.vEPE.accept(this);
        try {
          for (SystemQueryOptionImpl option : list) {
            expandItem.setSystemQueryOption(option);
          }
        } catch (ODataRuntimeException e) {
          // Thrown if duplicated system query options are detected
          throw wrap(new UriParserSyntaxException("Double system query option!", e,
              UriParserSyntaxException.MessageKeys.DOUBLE_SYSTEM_QUERY_OPTION, e.getMessage()));
        }
        context.contextExpandItemPath = contextExpandItemPathBU;
      }
    }

    return expandItem;

  }

  @Override
  public Object visitExpandPath(final ExpandPathContext ctx) {
    ExpandItemImpl expandItem = new ExpandItemImpl();

    // save context
    ExpandItemImpl contextExpandItemPathBU = context.contextExpandItemPath;
    UriInfoImpl uriInfoResourceBU = context.contextUriInfo;

    // set tmp context
    context.contextExpandItemPath = expandItem;
    context.contextUriInfo = new UriInfoImpl().setKind(UriInfoKind.resource);

    context.contextVisitExpandResourcePath = true;
    super.visitExpandPath(ctx);
    context.contextVisitExpandResourcePath = false;

    EdmType startType = removeUriResourceStartingTypeFilterImpl(context.contextUriInfo);
    expandItem.setResourcePath(context.contextUriInfo);
    if (startType != null) {
      expandItem.setTypeFilter(startType);
    }

    // reset context
    context.contextUriInfo = uriInfoResourceBU;
    context.contextExpandItemPath = contextExpandItemPathBU;

    // test
    validate(uriInfoResourceBU.asUriInfoResource(), expandItem);
    //

    return expandItem;
  }

  private void validate(UriInfoResource uriInfoResource, ExpandItemImpl expandItem) {
    if (uriInfoResource != null) {
      EdmEntityType type = getEntityType(uriInfoResource);
      EdmEntityType name = getEntityType(expandItem.getResourcePath());

      if (name != null && type != null) {
        EdmElement property = type.getProperty(name.getName());
        if (!(property instanceof EdmNavigationProperty)) {
          throw wrap(new UriParserSemanticException(
              "NavigationProperty '" + name.getName() + "' not found in type '"
                  + type.getFullQualifiedName().getFullQualifiedNameAsString() + "'",
              UriParserSemanticException.MessageKeys.EXPRESSION_PROPERTY_NOT_IN_TYPE,
              name.getFullQualifiedName().getFullQualifiedNameAsString(),
              type.getFullQualifiedName().getFullQualifiedNameAsString()));
        }
      }
    }
  }

  private EdmEntityType getEntityType(UriInfoResource test) {
    List<UriResource> parts = test.getUriResourceParts();
    if (!parts.isEmpty()) {
      UriResource lastPart = parts.get(parts.size() - 1);
      if (lastPart instanceof UriResourceEntitySet) {
        UriResourceEntitySet entitySet = (UriResourceEntitySet) lastPart;
        return entitySet.getEntityType();
      }
    }
    return null;
  }

  @Override
  public Object visitExpandPathExtension(final ExpandPathExtensionContext ctx) {
    List<SystemQueryOptionImpl> list = new ArrayList<SystemQueryOptionImpl>();

    EdmType targetType = null;
    boolean isColl = false;
    if (context.contextExpandItemPath == null) {
      // use the type of the last resource path segement
      UriResourceTypedImpl lastSegment = (UriResourceTypedImpl) context.contextUriInfo.getLastResourcePart();
      targetType = Parser.getTypeInformation(lastSegment);
      isColl = lastSegment.isCollection();
    } else {
      if (context.contextExpandItemPath.getResourcePath() == null) {
        // use the type of the last resource path segement
        UriResourceTypedImpl lastSegment = (UriResourceTypedImpl) context.contextUriInfo.getLastResourcePart();
        targetType = Parser.getTypeInformation(lastSegment);
        isColl = lastSegment.isCollection();
      } else {
        // use the type of the last ''expand'' path segement
        UriInfoImpl info = (UriInfoImpl) context.contextExpandItemPath.getResourcePath();
        targetType = Parser.getTypeInformation((UriResourcePartTyped) info.getLastResourcePart());
        isColl = ((UriResourcePartTyped) info.getLastResourcePart()).isCollection();
      }
    }

    context.contextTypes.push(targetType);
    context.isCollection = isColl;

    if (ctx.vC != null) {
      UriInfoImpl resourcePath = (UriInfoImpl) context.contextExpandItemPath.getResourcePath();
      resourcePath.addResourcePart(new UriResourceCountImpl());

      for (ExpandCountOptionContext s : ctx.vlEOC) {
        list.add((SystemQueryOptionImpl) s.accept(this));
      }
    } else if (ctx.vR != null) {
      UriInfoImpl resourcePath = (UriInfoImpl) context.contextExpandItemPath.getResourcePath();
      resourcePath.addResourcePart(new UriResourceRefImpl());

      for (ExpandRefOptionContext s : ctx.vlEOR) {
        list.add((SystemQueryOptionImpl) s.accept(this));
      }
    } else {
      for (ExpandOptionContext s : ctx.vlEO) {
        list.add((SystemQueryOptionImpl) s.accept(this));
      }
    }

    context.contextTypes.pop();
    return list;

  }

  @Override
  public Object visitFilter(final FilterContext ctx) {
    context.contextReadingQueryPart = true;
    final FilterOptionImpl result = new FilterOptionImpl().setExpression((Expression) ctx.children.get(2)
                                                          .accept(this));
    context.contextReadingQueryPart = false;

    return result;
  }

  @Override
  public Object visitFilterExpressionEOF(final FilterExpressionEOFContext ctx) {
    context.contextReadingQueryPart = true;
    final FilterOptionImpl result = new FilterOptionImpl().setExpression((Expression) ctx.children.get(0)
                                                          .accept(this));
    context.contextReadingQueryPart = false;

    return result;
  }

  @Override
  public Expression visitFloorMethodCallExpr(final FloorMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.FLOOR, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Expression visitFractionalsecondsMethodCallExpr(final FractionalsecondsMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.FRACTIONALSECONDS, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Expression visitGeoLengthMethodCallExpr(final GeoLengthMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.GEOLENGTH, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Expression visitHourMethodCallExpr(final HourMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.HOUR, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Expression visitIndexOfMethodCallExpr(final IndexOfMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.INDEXOF, Arrays.asList(
        (Expression) ctx.vE1.accept(this),
        (Expression) ctx.vE2.accept(this)));
  }

  @Override
  public Object visitInlinecount(final InlinecountContext ctx) {
    final String text = ctx.children.get(2).getText();
    return new CountOptionImpl()
        .setValue(text.equalsIgnoreCase("true") ? true : false)
        .setText(text);
  }

  @Override
  public Expression visitGeoIntersectsMethodCallExpr(final GeoIntersectsMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.GEOINTERSECTS, Arrays.asList(
        (Expression) ctx.vE1.accept(this),
        (Expression) ctx.vE2.accept(this)));
  }

  @Override
  public Expression visitIsofExpr(final IsofExprContext ctx) {
    List<Expression> parameters = new ArrayList<Expression>();
    if (ctx.vE1 != null) {
      parameters.add((Expression) ctx.vE1.accept(this));
    }

    String namespace = ctx.vNS.getText();
    namespace = namespace.substring(0, namespace.length() - 1);

    FullQualifiedName fullName = new FullQualifiedName(namespace, ctx.vODI.getText());
    EdmType type = getType(fullName);
    parameters.add(new TypeLiteralImpl(type));

    return new MethodImpl(MethodKind.ISOF, parameters);
  }

  @Override
  public Expression visitLengthMethodCallExpr(final LengthMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.LENGTH, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Object visitLevels(final LevelsContext ctx) {

    LevelsOptionImpl levels = new LevelsOptionImpl();

    String text = ctx.children.get(2).getText();

    if (text.equals("max")) {
      levels.setMax();
    } else {
      levels.setValue(Integer.parseInt(text));
    }
    levels.setText(text);

    return levels;

  }

  @Override
  public Expression visitMaxDateTimeMethodCallExpr(final MaxDateTimeMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.MAXDATETIME, null);
  }

  @Override
  public Object visitMemberExpr(final MemberExprContext ctx) {

    UriInfoImpl uriInfoImplpath = new UriInfoImpl().setKind(UriInfoKind.resource);

    if (context.contextTypes.isEmpty()) {
      throw wrap(new UriParserSemanticException("Expression '" + ctx.getText() + "' is not allowed as key value.",
          UriParserSemanticException.MessageKeys.INVALID_KEY_VALUE, ctx.getText()));
    }

    if (ctx.vIt != null || ctx.vIts != null) {
      UriResourceItImpl pathInfoIT = new UriResourceItImpl();
      pathInfoIT.setType(context.contextTypes.peek());
      pathInfoIT.setCollection(context.isCollection);
      uriInfoImplpath.addResourcePart(pathInfoIT);
    }

    if (ctx.vPs != null) {
      // save the context
      UriInfoImpl backupUriInfoPath = context.contextUriInfo;

      // set temporary uriInfoPath
      context.contextUriInfo = uriInfoImplpath;

      ctx.vPs.accept(this);

      // reset context
      context.contextUriInfo = backupUriInfoPath;
    }

    if (ctx.vALL != null) {
      uriInfoImplpath.addResourcePart((UriResourceImpl) ctx.vALL.accept(this));
    }
    if (ctx.vANY != null) {
      uriInfoImplpath.addResourcePart((UriResourceImpl) ctx.vANY.accept(this));
    }

    EdmType startType = removeUriResourceStartingTypeFilterImpl(uriInfoImplpath);

    return new MemberImpl(uriInfoImplpath, startType);
  }

  @Override
  public Expression visitMinDateTimeMethodCallExpr(final MinDateTimeMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.MINDATETIME, null);
  }

  @Override
  public Expression visitMinuteMethodCallExpr(final MinuteMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.MINUTE, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Expression visitMonthMethodCallExpr(final MonthMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.MONTH, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Object visitNameValueOptList(final NameValueOptListContext ctx) {
    if (ctx.vVO != null) {
      // This branch is chosen if the key predicate is a common expression e.g. EntitySet(0)

      // is single key predicate without a name
      String valueText = ctx.vVO.getText();
      Expression expression = null;
      try {
        expression = (Expression) ctx.vVO.accept(this);
      } catch (final RuntimeException e) {
        throw wrap(new UriParserSemanticException("Invalid key value: " + valueText, e,
            UriParserSemanticException.MessageKeys.INVALID_KEY_VALUE, valueText));
      }

      // get type of last resource part
      UriResource last = context.contextUriInfo.getLastResourcePart();
      if (!(last instanceof UriResourcePartTyped)) {
        throw wrap(new UriParserSemanticException("Parameters list on untyped resource path segment not allowed",
            UriParserSemanticException.MessageKeys.PARAMETERS_LIST_ONLY_FOR_TYPED_PARTS));

      } else if (last instanceof UriResourceFunction) {
        // Handle functions
        final UriResourceFunction uriResourceFunction =
            (UriResourceFunction) context.contextUriInfo.getLastResourcePart();
        final EdmReturnType returnType = uriResourceFunction.getFunction().getReturnType();

        if (returnType.getType().getKind() != EdmTypeKind.ENTITY || !returnType.isCollection()) {
          throw wrap(new UriParserSemanticException("No keys allowed",
              UriParserSemanticException.MessageKeys.KEY_NOT_ALLOWED));
        } else {
          // The functions returns a collection of entities
          // Get the EDM Type and determine how many key predicates are needed. In this case only one
          // key predicate is allowed. If the entity type needs more than one key predicate, the client
          // has to use the key value syntax e.g. EntitySet(ID=1,Order=2)
          final EdmEntityType entityType = (EdmEntityType) uriResourceFunction.getFunction().getReturnType().getType();
          final List<String> lastKeyPredicates = entityType.getKeyPredicateNames();

          if (lastKeyPredicates.size() == 1) {
            return Collections.singletonList(new UriParameterImpl()
                .setName(lastKeyPredicates.get(0))
                .setText(valueText)
                .setExpression(expression));
          } else {
            throw wrap(new UriParserSemanticException("Wrong number of key properties.",
                UriParserSemanticException.MessageKeys.WRONG_NUMBER_OF_KEY_PROPERTIES,
                Integer.toString(lastKeyPredicates.size()), "1"));
          }
        }
      } else {
        // Handle EntitySets
        EdmEntityType lastType = (EdmEntityType) ((UriResourcePartTyped) last).getType();

        // get list of keys for lastType
        List<String> lastKeyPredicates = lastType.getKeyPredicateNames();

        // If there is exactly one key defined in the EDM, then this key is the key written in the URI,
        // so fill the keylist with this key and return.
        if (lastKeyPredicates.size() == 1) {
          return Collections.singletonList(new UriParameterImpl()
              .setName(lastKeyPredicates.get(0))
              .setText(valueText)
              .setExpression(expression));
        }

        // There are more keys defined in the EDM, but only one is written in the URI. This is allowed only if
        // referential constraints are defined on this navigation property which can be used to fill up all
        // required keys.
        // For using referential constraints the last resource part must be a navigation property.
        if (!(context.contextUriInfo.getLastResourcePart() instanceof UriResourceNavigationPropertyImpl)) {
          throw wrap(new UriParserSemanticException("Wrong number of key properties.",
              UriParserSemanticException.MessageKeys.WRONG_NUMBER_OF_KEY_PROPERTIES,
              Integer.toString(lastKeyPredicates.size()), "1"));
        }
        UriResourceNavigationPropertyImpl lastNav = (UriResourceNavigationPropertyImpl) last;

        // get the partner of the navigation property
        EdmNavigationProperty partner = lastNav.getProperty().getPartner();
        if (partner == null) {
          throw wrap(new UriParserSemanticException("Wrong number of key properties.",
              UriParserSemanticException.MessageKeys.WRONG_NUMBER_OF_KEY_PROPERTIES,
              Integer.toString(lastKeyPredicates.size()), "1"));
        }

        // create the keylist
        List<UriParameterImpl> list = new ArrayList<UriParameterImpl>();

        // Find the keys not filled by referential constraints
        // and collect the other keys filled by referential constraints.
        String missedKey = null;
        for (String item : lastKeyPredicates) {
          String property = partner.getReferencingPropertyName(item);
          if (property != null) {
            list.add(new UriParameterImpl().setName(item).setReferencedProperty(property));
          } else {
            if (missedKey == null) {
              missedKey = item;
            } else {
              // two of more keys are missing
              throw wrap(new UriParserSemanticException("Not enough referential constraints defined",
                  UriParserSemanticException.MessageKeys.NOT_ENOUGH_REFERENTIAL_CONSTRAINTS));
            }
          }
        }

        // the missing key is the one which is defined in the URI
        list.add(new UriParameterImpl().setName(missedKey).setText(valueText).setExpression(expression));

        return list;
      }
    } else if (ctx.vNVL != null) {
      // The client provided a list of key values pairs e.g. EntitySet(ID=1,Order=2)
      List<UriParameterImpl> list = new ArrayList<UriParameterImpl>();

      for (ParseTree c : ctx.vNVL.vlNVP) {
        list.add((UriParameterImpl) c.accept(this));
      }

      if (context.contextReadingFunctionParameters) {
        return list;
      }

      UriResource last = context.contextUriInfo.getLastResourcePart();

      // get type of last resource part
      if (!(last instanceof UriResourcePartTyped)) {
        throw wrap(new UriParserSemanticException("Parameters list on untyped resource path segment not allowed",
            UriParserSemanticException.MessageKeys.PARAMETERS_LIST_ONLY_FOR_TYPED_PARTS));
      }
      if (last instanceof UriResourceFunction) {
        final UriResourceFunction uriResourceFunction = (UriResourceFunction) context.contextUriInfo
            .getLastResourcePart();
        final EdmReturnType returnType = uriResourceFunction.getFunction().getReturnType();

        if (returnType.getType().getKind() != EdmTypeKind.ENTITY || !returnType.isCollection()) {
          throw wrap(new UriParserSemanticException("No keys allowed",
              UriParserSemanticException.MessageKeys.KEY_NOT_ALLOWED));
        } else {
          // The functions returns a collection of entities
          // Get the EDM Type and determine how many key predicates are needed.
          // In case of functions all key predicates must be provided by the client.
          final EdmEntityType entityType = (EdmEntityType) uriResourceFunction.getFunction().getReturnType().getType();
          final List<String> lastKeyPredicates = entityType.getKeyPredicateNames();

          if (lastKeyPredicates.size() == list.size()) {
            return list;
          } else {
            throw wrap(new UriParserSemanticException("Wrong number of key properties.",
                UriParserSemanticException.MessageKeys.WRONG_NUMBER_OF_KEY_PROPERTIES,
                Integer.toString(lastKeyPredicates.size()), "1"));
          }
        }
      } else {
        // Handle entity sets
        EdmEntityType lastType = (EdmEntityType) ((UriResourcePartTyped) last).getType();

        // get list of keys for lastType
        List<String> lastKeyPredicates = lastType.getKeyPredicateNames();

        // check if all key are filled from the URI
        if (list.size() == lastKeyPredicates.size()) {
          return list;
        }

        // if not, check if the missing key predicates can be satisfied with help of the defined
        // referential constraints
        // for using referential constraints the last resource part must be a navigation property
        if (!(context.contextUriInfo.getLastResourcePart() instanceof UriResourceNavigationPropertyImpl)) {
          throw wrap(new UriParserSemanticException("Wrong number of key properties.",
              UriParserSemanticException.MessageKeys.WRONG_NUMBER_OF_KEY_PROPERTIES,
              Integer.toString(lastKeyPredicates.size()), Integer.toString(list.size())));
        }
        UriResourceNavigationPropertyImpl lastNav = (UriResourceNavigationPropertyImpl) last;

        // get the partner of the navigation property
        EdmNavigationProperty partner = lastNav.getProperty().getPartner();
        if (partner == null) {
          throw wrap(new UriParserSemanticException("Wrong number of key properties.",
              UriParserSemanticException.MessageKeys.WRONG_NUMBER_OF_KEY_PROPERTIES,
              Integer.toString(lastKeyPredicates.size()), Integer.toString(list.size())));
        }

        // fill missing keys from referential constraints
        for (String key : lastKeyPredicates) {
          boolean found = false;
          for (UriParameterImpl item : list) {
            if (item.getName().equals(key)) {
              found = true;
              break;
            }
          }

          if (!found) {
            String property = partner.getReferencingPropertyName(key);
            if (property != null) {
              // store the key name as referenced property
              list.add(0, new UriParameterImpl().setName(key).setReferencedProperty(property));
            }
          }
        }

        // check again if all key predicates are filled from the URI
        if (list.size() == lastKeyPredicates.size()) {
          return list;
        } else {
          throw wrap(new UriParserSemanticException("Wrong number of key properties.",
              UriParserSemanticException.MessageKeys.WRONG_NUMBER_OF_KEY_PROPERTIES,
              Integer.toString(lastKeyPredicates.size()), Integer.toString(list.size())));
        }
      }
    } else {
      // No key predicates are provided by the client

      if (context.contextReadingFunctionParameters) {
        return Collections.emptyList();
      } else {
        final UriResource last = context.contextUriInfo.getLastResourcePart();
        final int number = last instanceof UriResourcePartTyped ? ((EdmEntityType) ((UriResourcePartTyped) last)
            .getType()).getKeyPredicateNames().size() : 0;
        throw wrap(new UriParserSemanticException("Wrong number of key properties.",
            UriParserSemanticException.MessageKeys.WRONG_NUMBER_OF_KEY_PROPERTIES, Integer.toString(number), "0"));
      }
    }
  }

  @Override
  public UriParameterImpl visitNameValuePair(final NameValuePairContext ctx) {
    UriParameterImpl uriParameter = new UriParameterImpl();
    uriParameter.setName(ctx.vODI.getText());

    if (ctx.vCOM != null) {
      final String text = ctx.vCOM.getText();
      uriParameter.setText("null".equals(text) ? null : text);
      uriParameter.setExpression((Expression) ctx.vCOM.accept(this));
    } else {
      uriParameter.setAlias("@" + ctx.vALI.getText());
    }

    return uriParameter;
  }

  @Override
  public Object visitNaninfinityLiteral(final NaninfinityLiteralContext ctx) {
    return new LiteralImpl(ctx.getText(),
        EdmPrimitiveTypeFactory.getInstance(EdmPrimitiveTypeKind.Decimal));
  }

  @Override
  public Expression visitNowMethodCallExpr(final NowMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.NOW, null);
  }

  @Override
  public Object visitNullruleLiteral(final NullruleLiteralContext ctx) {
    return new LiteralImpl("null", null);
  }

  @Override
  public Object visitOrderBy(final OrderByContext ctx) {

    OrderByOptionImpl orderBy = new OrderByOptionImpl();

    for (OrderByItemContext item : ((OrderListContext) ctx.getChild(2)).vlOI) {
      OrderByItemImpl oItem = (OrderByItemImpl) item.accept(this);
      orderBy.addOrder(oItem);
    }

    return orderBy;
  }

  @Override
  public Object visitOrderByEOF(final OrderByEOFContext ctx) {
    context.contextReadingQueryPart = true;
    
    OrderByOptionImpl orderBy = new OrderByOptionImpl();

    for (OrderByItemContext item : ((OrderListContext) ctx.getChild(0)).vlOI) {
      OrderByItemImpl oItem = (OrderByItemImpl) item.accept(this);
      orderBy.addOrder(oItem);
    }

    context.contextReadingFunctionParameters = false;
    return orderBy;
  }

  @Override
  public Object visitOrderByItem(final OrderByItemContext ctx) {
    OrderByItemImpl oItem = new OrderByItemImpl();
    if (ctx.vD != null) {
      oItem.setDescending(true);
    }

    oItem.setExpression((Expression) ctx.vC.accept(this));
    return oItem;
  }

  @Override
  public Object visitPathSegment(final PathSegmentContext ctx) {
    readResourcePathSegment(ctx);
    /*
     * if (contextUriInfo.getLastResourcePart() == null ||
     * contextUriInfo.getLastResourcePart() instanceof UriResourceRootImpl) {
     * 
     * } else {
     * readNextPathInfoSegment(ctx);
     * }
     */
    UriResourceImpl pathInfoSegment = (UriResourceImpl) context.contextUriInfo.getLastResourcePart();

    if (ctx.vlNVO.size() > 0) {
      // check for keyPredicates
      if (pathInfoSegment instanceof UriResourceWithKeysImpl) {
        if (ctx.vlNVO.size() > 1) {
          throw wrap(new UriParserSemanticException("More than one key predicates found",
              UriParserSemanticException.MessageKeys.WRONG_NUMBER_OF_KEY_PROPERTIES, "1",
              Integer.toString(ctx.vlNVO.size())));
        }

        @SuppressWarnings("unchecked")
        List<UriParameter> list = (List<UriParameter>) ctx.vlNVO.get(0).accept(this);
        ((UriResourceWithKeysImpl) pathInfoSegment)
            .setKeyPredicates(list);
      } else {
        throw wrap(new UriParserSemanticException("Key properties not allowed",
            UriParserSemanticException.MessageKeys.KEY_NOT_ALLOWED));
      }
    }

    return pathInfoSegment;
  }

  @Override
  public Object visitPathSegments(final PathSegmentsContext ctx) {
    // path segment
    for (PathSegmentContext it : ctx.vlPS) {
      it.accept(this);
    }

    // const segment
    if (ctx.vCS != null) {
      ctx.vCS.accept(this);
    }
    return null;
  }

  @Override
  public Object visitPrimitiveLiteral(final PrimitiveLiteralContext ctx) {
    ParseTree child1 = ctx.children.get(0);

    if (child1 instanceof EnumLiteralContext
        || child1 instanceof BooleanNonCaseLiteralContext
        || child1 instanceof NullruleLiteralContext
        || child1 instanceof NaninfinityLiteralContext
        || child1 instanceof StringLiteralContext
        || child1 instanceof IntLiteralContext
        || child1 instanceof BinaryLiteralContext
        || child1 instanceof DateLiteralContext
        || child1 instanceof DatetimeoffsetLiteralContext
        || child1 instanceof DurationLiteralContext
        || child1 instanceof GuidLiteralContext
        || child1 instanceof TimeofdayLiteralContext
        || child1 instanceof DecimalLiteralContext
        || child1 instanceof BinaryLiteralContext) {
      return child1.accept(this);
    }

    // TODO Implement geography types and set a proper type
    return new LiteralImpl(ctx.getText(), null);
  }

  @Override
  public Object visitBinaryLiteral(BinaryLiteralContext ctx) {
    return new LiteralImpl(ctx.getText(),
        EdmPrimitiveTypeFactory.getInstance(EdmPrimitiveTypeKind.Binary));
  }

  @Override
  public Object visitStringLiteral(final StringLiteralContext ctx) {
    return new LiteralImpl(ctx.getText(),
        EdmPrimitiveTypeFactory.getInstance(EdmPrimitiveTypeKind.String));
  }

  @Override
  public Object visitDecimalLiteral(final DecimalLiteralContext ctx) {
    final EdmType type = EdmPrimitiveTypeFactory.getInstance(
        ctx.getText().contains("e") || ctx.getText().contains("E") ?
            EdmPrimitiveTypeKind.Double :
            EdmPrimitiveTypeKind.Decimal);

    return new LiteralImpl(ctx.getText(), type);
  }

  @Override
  public Object visitIntLiteral(final IntLiteralContext ctx) {
    EdmPrimitiveTypeKind typeKind = null;
    try {
      final long value = Long.parseLong(ctx.getText());
      if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
        typeKind = EdmPrimitiveTypeKind.SByte;
      } else if (value >= 0 && value <= 255) {
        typeKind = EdmPrimitiveTypeKind.Byte;
      } else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
        typeKind = EdmPrimitiveTypeKind.Int16;
      } else if (value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
        typeKind = EdmPrimitiveTypeKind.Int32;
      } else {
        typeKind = EdmPrimitiveTypeKind.Int64;
      }
    } catch (NumberFormatException e) {
      return new LiteralImpl(ctx.getText(),
          EdmPrimitiveTypeFactory.getInstance(EdmPrimitiveTypeKind.Decimal));
    }
    return new LiteralImpl(ctx.getText(),
        EdmPrimitiveTypeFactory.getInstance(typeKind));
  }

  @Override
  public Object visitDateLiteral(final DateLiteralContext ctx) {
    return new LiteralImpl(ctx.getText(),
        EdmPrimitiveTypeFactory.getInstance(EdmPrimitiveTypeKind.Date));
  }

  @Override
  public Object visitDatetimeoffsetLiteral(final DatetimeoffsetLiteralContext ctx) {
    return new LiteralImpl(ctx.getText(),
        EdmPrimitiveTypeFactory.getInstance(EdmPrimitiveTypeKind.DateTimeOffset));
  }

  @Override
  public Object visitDurationLiteral(final DurationLiteralContext ctx) {
    return new LiteralImpl(ctx.getText(),
        EdmPrimitiveTypeFactory.getInstance(EdmPrimitiveTypeKind.Duration));
  }

  @Override
  public Object visitGuidLiteral(final GuidLiteralContext ctx) {
    return new LiteralImpl(ctx.getText(),
        EdmPrimitiveTypeFactory.getInstance(EdmPrimitiveTypeKind.Guid));
  }

  @Override
  public Object visitTimeofdayLiteral(final TimeofdayLiteralContext ctx) {
    return new LiteralImpl(ctx.getText(),
        EdmPrimitiveTypeFactory.getInstance(EdmPrimitiveTypeKind.TimeOfDay));
  }

  @Override
  public Object visitQueryOptions(final QueryOptionsContext ctx) {

    List<QueryOptionImpl> qpList = new ArrayList<QueryOptionImpl>();
    for (QueryOptionContext entityOption : ctx.vlQO) {
      qpList.add((QueryOptionImpl) entityOption.accept(this));
    }

    return qpList;
  }

  @Override
  public Object visitRootExpr(final RootExprContext ctx) {

    UriResource lastResource = context.contextUriInfo.getLastResourcePart();

    if (!(lastResource instanceof UriResourcePartTyped)) {
      throw wrap(new UriParserSemanticException("Resource path not typed",
          UriParserSemanticException.MessageKeys.RESOURCE_PATH_NOT_TYPED));
    }

    UriResourcePartTyped lastType = (UriResourcePartTyped) lastResource;

    UriResourceRootImpl pathInfoRoot = new UriResourceRootImpl();
    pathInfoRoot.setCollection(lastType.isCollection());
    pathInfoRoot.setType(Parser.getTypeInformation(lastType));

    UriInfoImpl uriInfoImplpath = new UriInfoImpl().setKind(UriInfoKind.resource);
    uriInfoImplpath.addResourcePart(pathInfoRoot);

    if (ctx.vPs != null) {
      // store the context uriInfoPath
      UriInfoImpl backupUriInfoPath = context.contextUriInfo;

      // set temporary uriInfoPath to collect the path information of the memberExpression
      context.contextUriInfo = uriInfoImplpath;

      ctx.vPs.accept(this);

      context.contextUriInfo = backupUriInfoPath;

    }
    return new MemberImpl(uriInfoImplpath, null);

  }

  @Override
  public Expression visitRoundMethodCallExpr(final RoundMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.ROUND, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Expression visitSecondMethodCallExpr(final SecondMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.SECOND, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Object visitSelect(final SelectContext ctx) {
    List<SelectItem> selectItems = new ArrayList<SelectItem>();
    for (SelectItemContext si : ctx.vlSI) {
      selectItems.add((SelectItem) si.accept(this));
    }

    return new SelectOptionImpl().setSelectItems(selectItems).setText(ctx.children.get(2).getText());
  }

  @Override
  public Object visitSelectEOF(final SelectEOFContext ctx) {
    context.contextReadingQueryPart = true;
    List<SelectItem> selectItems = new ArrayList<SelectItem>();
    for (SelectItemContext si : ctx.vlSI) {
      selectItems.add((SelectItem) si.accept(this));
    }

    final QueryOptionImpl result = new SelectOptionImpl().setSelectItems(selectItems).setText(ctx.getText());
    context.contextReadingQueryPart = false;
    
    return result;
  }

  @Override
  public Object visitSelectItem(final SelectItemContext ctx) {
    SelectItemImpl selectItem = new SelectItemImpl();

    context.contextSelectItem = selectItem;
    for (SelectSegmentContext si : ctx.vlSS) {
      si.accept(this);
    }
    context.contextSelectItem = null;

    return selectItem;
  }

  @Override
  public Object visitSelectSegment(final SelectSegmentContext ctx) {

    if (ctx.vS != null) {
      if (ctx.vNS != null) {
        String namespace = ctx.vNS.getText();
        namespace = namespace.substring(0, namespace.length() - 1);
        FullQualifiedName fullName = new FullQualifiedName(namespace, "*");
        context.contextSelectItem.addAllOperationsInSchema(fullName);
      } else {
        context.contextSelectItem.setStar(true);
      }
      return null;
    }

    String odi = ctx.vODI.getText();
    if (ctx.vNS == null) {

      EdmType prevType = null;
      if (context.contextSelectItem.getResourcePath() == null) {
        prevType = context.contextTypes.peek();
      } else {
        UriInfoImpl uriInfo = (UriInfoImpl) context.contextSelectItem.getResourcePath();
        UriResource last = uriInfo.getLastResourcePart();

        prevType = Parser.getTypeInformation((UriResourcePartTyped) last);
        if (prevType == null) {
          throw wrap(new UriParserSemanticException("prev segment not typed",
              UriParserSemanticException.MessageKeys.ONLY_FOR_TYPED_PARTS, "select"));
        }
      }

      if (!(prevType instanceof EdmStructuredType)) {
        throw wrap(new UriParserSemanticException("Previous select item is not a structural type",
            UriParserSemanticException.MessageKeys.ONLY_FOR_STRUCTURAL_TYPES, "select"));
      }

      EdmStructuredType structType = (EdmStructuredType) prevType;
      EdmElement element = structType.getProperty(odi);
      if (element == null) {
        throw wrap(new UriParserSemanticException("Previous select item has not property: " + odi,
            UriParserSemanticException.MessageKeys.EXPRESSION_PROPERTY_NOT_IN_TYPE, structType.getName(), odi));
      }

      // create new segment
      // SelectSegmentImpl newSegment = new SelectSegmentImpl().setProperty(property);
      // contextSelectItem.addSegment(newSegment);
      if (element instanceof EdmProperty) {
        EdmProperty property = (EdmProperty) element;
        if (property.isPrimitive()
            || property.getType().getKind() == EdmTypeKind.ENUM
            || property.getType().getKind() == EdmTypeKind.DEFINITION) {

          UriResourcePrimitivePropertyImpl simple = new UriResourcePrimitivePropertyImpl();
          simple.setProperty(property);

          UriInfoImpl uriInfo = (UriInfoImpl) context.contextSelectItem.getResourcePath();
          if (uriInfo == null) {
            uriInfo = new UriInfoImpl().setKind(UriInfoKind.resource);
            uriInfo.addResourcePart(simple);

            EdmType startType = removeUriResourceStartingTypeFilterImpl(uriInfo);
            if (startType != null) {
              context.contextSelectItem.setTypeFilter(startType);
            }

            context.contextSelectItem.setResourcePath(uriInfo);
          } else {
            uriInfo.addResourcePart(simple);
          }
          return this;
        } else {
          UriInfoImpl uriInfo = (UriInfoImpl) context.contextSelectItem.getResourcePath();

          UriResourceComplexPropertyImpl complex = new UriResourceComplexPropertyImpl();
          complex.setProperty(property);

          if (uriInfo == null) {
            uriInfo = new UriInfoImpl().setKind(UriInfoKind.resource);
            uriInfo.addResourcePart(complex);

            EdmType startType = removeUriResourceStartingTypeFilterImpl(uriInfo);
            if (startType != null) {
              context.contextSelectItem.setTypeFilter(startType);
            }

            context.contextSelectItem.setResourcePath(uriInfo);
          } else {
            uriInfo.addResourcePart(complex);
          }
          return this;
        }
      } else {
        throw wrap(new UriParserSemanticException("Only Simple and Complex properties within select allowed",
            UriParserSemanticException.MessageKeys.ONLY_SIMPLE_AND_COMPLEX_PROPERTIES_IN_SELECT));
      }
    } else {
      String namespace = ctx.vNS.getText();
      namespace = namespace.substring(0, namespace.length() - 1);

      FullQualifiedName fullName = new FullQualifiedName(namespace, odi);
      // contextSelectItem.addQualifiedThing(fullName);

      if (context.contextSelectItem.getResourcePath() == null) {
        EdmType prevType = context.contextTypes.peek();

        // check for complex type cast
        if (prevType instanceof EdmComplexType) {
          EdmComplexType ct = edm.getComplexType(fullName);
          if (ct != null) {
            if ((ct.compatibleTo(prevType))) {
              UriResourceStartingTypeFilterImpl resourcePart = new UriResourceStartingTypeFilterImpl();
              resourcePart.setCollectionTypeFilter(ct);

              UriInfoImpl uriInfo = new UriInfoImpl().setKind(UriInfoKind.resource);
              uriInfo.addResourcePart(resourcePart);

              EdmType startType = removeUriResourceStartingTypeFilterImpl(uriInfo);
              if (startType != null) {
                context.contextSelectItem.setTypeFilter(startType);
              }

              context.contextSelectItem.setResourcePath(uriInfo);
              return this;
            }
          }
        } else if (prevType instanceof EdmEntityType) {
          EdmEntityType et = edm.getEntityType(fullName);
          if (et != null) {
            if ((et.compatibleTo(prevType))) {
              UriResourceStartingTypeFilterImpl resourcePart = new UriResourceStartingTypeFilterImpl();
              resourcePart.setCollectionTypeFilter(et);

              UriInfoImpl uriInfo = new UriInfoImpl().setKind(UriInfoKind.resource);
              uriInfo.addResourcePart(resourcePart);

              EdmType startType = removeUriResourceStartingTypeFilterImpl(uriInfo);
              if (startType != null) {
                context.contextSelectItem.setTypeFilter(startType);
              }

              context.contextSelectItem.setResourcePath(uriInfo);
              return this;
            }
          }
        } else {
          throw wrap(new UriParserSemanticException("prev segment must be complex of entity type",
              UriParserSemanticException.MessageKeys.COMPLEX_PROPERTY_OF_ENTITY_TYPE_EXPECTED));
        }

      } else {
        UriInfoImpl uriInfo = (UriInfoImpl) context.contextSelectItem.getResourcePath();
        UriResource last = uriInfo.getLastResourcePart();
        if (!(last instanceof UriResourceTypedImpl)) {
          throw wrap(new UriParserSemanticException("prev segment typed",
              UriParserSemanticException.MessageKeys.ONLY_FOR_TYPED_PARTS, "select"));
        }
        EdmType prevType = Parser.getTypeInformation((UriResourcePartTyped) last);

        if (prevType instanceof EdmComplexType) {
          EdmComplexType ct = edm.getComplexType(fullName);
          if (ct != null) {
            if ((ct.compatibleTo(prevType))) {
              UriResourceStartingTypeFilterImpl resourcePart = new UriResourceStartingTypeFilterImpl();
              resourcePart.setCollectionTypeFilter(ct);

              uriInfo.addResourcePart(resourcePart);
              return this;
            }
          }
        } else if (prevType instanceof EdmEntityType) {
          throw wrap(new UriParserSemanticException("Error",
              UriParserSemanticException.MessageKeys.NOT_FOR_ENTITY_TYPE));
          /*
           * EdmEntityType et = edm.getEntityType(fullName);
           * if (et != null) {
           * if ((et.compatibleTo((EdmStructuralType) prevType))) {
           * UriResourceStartingTypeFilterImpl resourcePart = new UriResourceStartingTypeFilterImpl();
           * resourcePart.setEntryTypeFilter(et);
           * 
           * uriInfo.addResourcePart(resourcePart);
           * return this;
           * }
           * }
           */
        } else {
          throw wrap(new UriParserSemanticException("prev segment must be complex of entity type",
              UriParserSemanticException.MessageKeys.COMPLEX_PROPERTY_OF_ENTITY_TYPE_EXPECTED));
        }
      }

      EdmType prevType = null;
      if (context.contextSelectItem.getResourcePath() == null) {
        prevType = context.contextTypes.peek();
      } else {
        UriInfoImpl uriInfo = (UriInfoImpl) context.contextSelectItem.getResourcePath();
        UriResource last = uriInfo.getLastResourcePart();
        if (!(last instanceof UriResourceTypedImpl)) {
          throw wrap(new UriParserSemanticException("prev segment typed",
              UriParserSemanticException.MessageKeys.PREVIOUS_PART_TYPED));
        }
        prevType = Parser.getTypeInformation((UriResourcePartTyped) last);
      }

      final FullQualifiedName finalTypeName = prevType.getFullQualifiedName();

      // check for action
      EdmAction action = edm.getBoundAction(fullName, finalTypeName, null);

      if (action != null) {
        UriResourceActionImpl uriAction = new UriResourceActionImpl();
        uriAction.setAction(action);

        UriInfoImpl resourcePath = (UriInfoImpl) context.contextSelectItem.getResourcePath();
        resourcePath.addResourcePart(uriAction);
      }

      // check for function
      EdmFunction function = edm.getBoundFunction(fullName, finalTypeName, null, null);

      if (function != null) {
        UriResourceFunctionImpl uriFunction = new UriResourceFunctionImpl();
        uriFunction.setFunction(function);

        UriInfoImpl resourcePath = (UriInfoImpl) context.contextSelectItem.getResourcePath();
        resourcePath.addResourcePart(uriFunction);
      }
    }
    return null;
  }

  @Override
  public Object visitSkip(final SkipContext ctx) {
    final String text = ctx.children.get(2).getText();
    return new SkipOptionImpl()
        .setValue(Integer.parseInt(text))
        .setText(text);
  }

  @Override
  public Object visitSkiptoken(final SkiptokenContext ctx) {
    final String text = ctx.children.get(2).getText();
    return new SkipTokenOptionImpl().setValue(text).setText(text);
  }

  @Override
  public Expression visitStartsWithMethodCallExpr(final StartsWithMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.STARTSWITH, Arrays.asList(
        (Expression) ctx.vE1.accept(this),
        (Expression) ctx.vE2.accept(this)));
  }

  @Override
  public Expression visitSubstringMethodCallExpr(final SubstringMethodCallExprContext ctx) {
    List<Expression> parameters = new ArrayList<Expression>();
    parameters.add((Expression) ctx.vE1.accept(this));
    parameters.add((Expression) ctx.vE2.accept(this));

    if (ctx.vE3 != null) {
      parameters.add((Expression) ctx.vE3.accept(this));
    }

    return new MethodImpl(MethodKind.SUBSTRING, parameters);
  }

  @Override
  public Expression visitTimeMethodCallExpr(final TimeMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.TIME, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Object visitTop(final TopContext ctx) {
    final String text = ctx.children.get(2).getText();
    return new TopOptionImpl()
        .setValue(Integer.parseInt(text))
        .setText(text);
  }

  @Override
  public Expression visitToLowerMethodCallExpr(final ToLowerMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.TOLOWER, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Expression visitTotalOffsetMinutesMethodCallExpr(final TotalOffsetMinutesMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.TOTALOFFSETMINUTES, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Expression visitTotalsecondsMethodCallExpr(final TotalsecondsMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.TOTALSECONDS, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Expression visitToUpperMethodCallExpr(final ToUpperMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.TOUPPER, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Expression visitTrimMethodCallExpr(final TrimMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.TRIM, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  @Override
  public Expression visitYearMethodCallExpr(final YearMethodCallExprContext ctx) {
    return new MethodImpl(MethodKind.YEAR, Collections.singletonList((Expression) ctx.vE1.accept(this)));
  }

  private ParseCancellationException wrap(final UriParserException uriParserException) {
    return new ParseCancellationException(uriParserException);
  }

  @Override
  public Expression visitAltUnary(@NotNull final UriParserParser.AltUnaryContext ctx) {
    return new UnaryImpl(
        ctx.unary().NOT() == null ? UnaryOperatorKind.MINUS : UnaryOperatorKind.NOT,
        (Expression) ctx.commonExpr().accept(this));
  }

  @Override
  public Expression visitAltAlias(@NotNull final UriParserParser.AltAliasContext ctx) {
    return new AliasImpl("@" + ctx.odataIdentifier().getChild(0).getText());
  }

  @Override
  public Object visitSearchSpecialToken(final SearchSpecialTokenContext ctx) {
    throw wrap(new UriParserSemanticException("System query option '$search' not implemented!",
        UriParserSemanticException.MessageKeys.NOT_IMPLEMENTED, "System query option '$search"));
  }

  @Override
  public Object visitArrayOrObject(final ArrayOrObjectContext ctx) {
    if (!context.contextReadingQueryPart) {
      throw wrap(new UriParserSemanticException("Complex parameter are not allowed in resource path",
          MessageKeys.COMPLEX_PARAMETER_IN_RESOURCE_PATH, ctx.getText()));
    }

    return new LiteralImpl(ctx.getText(), null);
  }
  
  @Override
  public Object visitExpandItemsEOF(ExpandItemsEOFContext ctx) {
    context.contextReadingQueryPart = true;
    final Object result = super.visitExpandItemsEOF(ctx);
    context.contextReadingQueryPart = false;
    
    return result;
  }
}
