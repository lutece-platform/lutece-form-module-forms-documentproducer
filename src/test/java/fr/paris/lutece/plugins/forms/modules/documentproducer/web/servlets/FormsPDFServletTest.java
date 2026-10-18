/*
 * Copyright (c) 2002-2026, City of Paris
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

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.test.mocks.MockHttpServletRequest;
import fr.paris.lutece.test.mocks.MockHttpServletResponse;

/**
 * Test of the FormsPDFServlet
 */
public class FormsPDFServletTest extends LuteceTestCase
{
    /**
     * A non numeric id of form response is refused, it must not raise a NumberFormatException
     */
    @Test
    public void testDoDownloadPDFNonNumericId( )
    {
        assertAccessDenied( "abc" );
    }

    /**
     * A missing id of form response is refused
     */
    @Test
    public void testDoDownloadPDFMissingId( )
    {
        assertAccessDenied( null );
    }

    /**
     * An unknown id of form response is refused
     */
    @Test
    public void testDoDownloadPDFUnknownId( )
    {
        assertAccessDenied( String.valueOf( Integer.MAX_VALUE ) );
    }

    /**
     * Check that the download of the given id of form response is refused
     * 
     * @param strIdFormResponse
     *            the id of form response, may be null
     */
    private void assertAccessDenied( String strIdFormResponse )
    {
        MockHttpServletRequest request = new MockHttpServletRequest( );
        if ( strIdFormResponse != null )
        {
            request.addParameter( FormsPDFServlet.PARAMETER_ID_FORM_RESPONSE, strIdFormResponse );
        }
        MockHttpServletResponse response = new MockHttpServletResponse( );

        assertThrows( AccessDeniedException.class, ( ) -> new FormsPDFServlet( ).doDownloadPDF( request, response ) );
    }
}
