<jsp:useBean id="documentproducerJspBean" scope="session" class="fr.paris.lutece.plugins.forms.modules.documentproducer.web.DocumentProducerJspBean" />
<% documentproducerJspBean.init( request, documentproducerJspBean.RIGHT_MANAGE_CONFIG_PRODUCER );
   String strContent = documentproducerJspBean.processController( request, response );%>
<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />
<%= strContent %>
<%@ include file="../../../../AdminFooter.jsp" %>