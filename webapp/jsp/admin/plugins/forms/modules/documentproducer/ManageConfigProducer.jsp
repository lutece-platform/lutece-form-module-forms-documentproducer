<%@page import="fr.paris.lutece.plugins.forms.modules.documentproducer.web.DocumentProducerJspBean"%>

${ documentProducerJspBean.init( pageContext.request, DocumentProducerJspBean.RIGHT_MANAGE_CONFIG_PRODUCER ) }
${ pageContext.setAttribute( 'strContent', documentProducerJspBean.processController( pageContext.request, pageContext.response ) ) }
<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />
${ pageContext.getAttribute( 'strContent' ) }
<%@ include file="../../../../AdminFooter.jsp" %>