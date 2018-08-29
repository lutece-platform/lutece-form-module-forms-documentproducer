/*
 * Copyright (c) 2002-2018, Mairie de Paris
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
package fr.paris.lutece.plugins.forms.modules.documentproducer.service;

import fr.paris.lutece.plugins.forms.business.Form;
import fr.paris.lutece.plugins.forms.business.FormHome;
import fr.paris.lutece.plugins.forms.service.FormsPlugin;
import fr.paris.lutece.plugins.forms.service.FormsResourceIdService;
import fr.paris.lutece.portal.service.rbac.Permission;
import fr.paris.lutece.portal.service.rbac.ResourceIdService;
import fr.paris.lutece.portal.service.rbac.ResourceType;
import fr.paris.lutece.portal.service.rbac.ResourceTypeManager;
import fr.paris.lutece.util.ReferenceList;

import java.util.Locale;

/**
 * FormsDocumentProducerResourceIdService
 *
 */
public class FormsDocumentProducerResourceIdService extends ResourceIdService
{
    /** Permission for generate zip */
    public static final String PERMISSION_GENERATE_PDF = "DOCUMENTPRODUCER";
    public static final String PERMISSION_MANAGE_DOCUMENTPRODUCER = "MANAGE_DOCUMENTPRODUCER";
    private static final String PROPERTY_LABEL_RESOURCE_TYPE = "forms.permission.label.resource_type_forms";
    private static final String PROPERTY_LABEL_GENERATE_PDF = "module.forms.documentproducer.permission.label.generate_pdf";
    private static final String PROPERTY_LABEL_MANAGE_DOCUMENTPRODUCER = "module.forms.documentproducer.permission.label.manage_documentproducer";

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferenceList getResourceIdList( Locale locale )
    {
        return FormHome.getFormsReferenceList( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle( String strId, Locale locale )
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register( )
    {
        // Override the resource type FORMS_FORMS_TYPE
        ResourceType rt = ResourceTypeManager.getResourceType( Form.RESOURCE_TYPE );

        if ( rt == null )
        {
            rt = new ResourceType( );
            rt.setResourceIdServiceClass( FormsResourceIdService.class.getName( ) );
            rt.setPluginName( FormsPlugin.PLUGIN_NAME );
            rt.setResourceTypeKey( Form.RESOURCE_TYPE );
            rt.setResourceTypeLabelKey( PROPERTY_LABEL_RESOURCE_TYPE );
        }

        Permission p = new Permission( );
        p.setPermissionKey( PERMISSION_GENERATE_PDF );
        p.setPermissionTitleKey( PROPERTY_LABEL_GENERATE_PDF );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( PERMISSION_MANAGE_DOCUMENTPRODUCER );
        p.setPermissionTitleKey( PROPERTY_LABEL_MANAGE_DOCUMENTPRODUCER );
        rt.registerPermission( p );

        ResourceTypeManager.registerResourceType( rt );
    }
}
