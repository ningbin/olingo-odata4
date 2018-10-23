package org.apache.olingo.client.core.communication.request.invoke;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.api.domain.ClientInvokeResult;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.http.HttpMethod;
import org.junit.Test;

public class ODataInvokeRequestTest {


  @Test
  public void testRequest() throws URISyntaxException {
    ODataClient client = ODataClientFactory.getClient();
    Class<ClientInvokeResult> reference = ClientInvokeResult.class;
    HttpMethod method = HttpMethod.GET;
    URI uri = new URI("test");
    ODataInvokeRequestImpl req = new ODataInvokeRequestImpl<ClientInvokeResult>(client, reference, method, uri);
    assertNotNull(req);
    assertNotNull(req.getAccept());
    assertNotNull(req.getContentType());
    assertNotNull(req.getDefaultFormat());
    assertNotNull(req.getHeader());
    assertNotNull(req.getHeaderNames());
    assertNull(req.getIfMatch());
    assertNull(req.getIfNoneMatch());
    assertNotNull(req.getHttpRequest());
    assertNotNull(req.getMethod());
    assertNull(req.getPayload());
    assertNotNull(req.getPOSTParameterFormat());
    assertNotNull(req.getPOSTParameterFormat());
    assertNull(req.getPrefer());
    assertNotNull(req.getResponseTemplate());
    assertNotNull(req.getURI());
    assertNotNull(req.addCustomHeader("custom", "header"));
    assertNotNull(req.setAccept("json"));
    assertNotNull(req.setContentType("json"));
    req.setFormat(ContentType.APPLICATION_ATOM_XML);
  }
}
