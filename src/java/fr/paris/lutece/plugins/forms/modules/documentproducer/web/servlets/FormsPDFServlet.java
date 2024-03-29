/*
 * Copyright (c) 2002-2021, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.forms.modules.documentproducer.web.servlets;

import fr.paris.lutece.plugins.forms.business.Form;
import fr.paris.lutece.plugins.forms.business.FormResponse;
import fr.paris.lutece.plugins.forms.business.FormResponseHome;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig.ConfigProducer;
import fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig.DocumentType;
import fr.paris.lutece.plugins.forms.modules.documentproducer.service.ConfigProducerService;
import fr.paris.lutece.plugins.forms.modules.documentproducer.service.FormsDocumentProducerPlugin;
import fr.paris.lutece.plugins.forms.modules.documentproducer.service.FormsDocumentProducerResourceIdService;
import fr.paris.lutece.plugins.forms.modules.documentproducer.utils.PDFUtils;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

/**
 * FormsPDFServlet
 * 
 */
public class FormsPDFServlet
{

    // PARAMETERS
    public static final String PARAMETER_ID_FORM_RESPONSE = "id_form_response";

    private static final ConfigProducerService _manageConfigProducerService = SpringContextService.getBean( "forms-documentproducer.manageConfigProducer" );

    /**
     * Method to download a PDF file generated by form response
     * 
     * @param request
     *            request
     * @param response
     *            response
     * @throws AccessDeniedException
     *             exception if user does not have the permission
     */
    public void doDownloadPDF( HttpServletRequest request, HttpServletResponse response ) throws AccessDeniedException
    {
        Plugin plugin = PluginService.getPlugin( FormsDocumentProducerPlugin.PLUGIN_NAME );
        String strIdFormResponse = request.getParameter( PARAMETER_ID_FORM_RESPONSE );
        int nIdFormResponse = Integer.parseInt( strIdFormResponse );
        FormResponse formResponse = FormResponseHome.findByPrimaryKey( nIdFormResponse );

        if ( ( formResponse == null ) || !RBACService.isAuthorized( Form.RESOURCE_TYPE, Integer.toString( formResponse.getFormId( ) ),
                FormsDocumentProducerResourceIdService.PERMISSION_GENERATE_PDF, AdminUserService.getAdminUser( request ) ) )
        {
            throw new AccessDeniedException( "Unauthorized" );
        }

        ConfigProducer configProducer = _manageConfigProducerService.loadDefaultConfig( plugin, formResponse.getFormId( ), DocumentType.PDF );

        PDFUtils.doDownloadPDF( request, response, plugin, configProducer,
                _manageConfigProducerService.loadListConfigQuestion( plugin, configProducer.getIdProducerConfig( ) ), getLocale( ), nIdFormResponse );

    }
}
