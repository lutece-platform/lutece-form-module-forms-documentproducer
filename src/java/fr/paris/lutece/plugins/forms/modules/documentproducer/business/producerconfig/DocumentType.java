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
package fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig;

import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;

/**
 * This enum represents several ususal document type
 */
public enum DocumentType
{
    PDF( ".pdf" ), CSV( ".csv" );

    private final String _strExtension;

    /**
     * Constructor for DocumentType
     * @param strExtension 
     */
    DocumentType( String strExtension )
    {
        _strExtension = strExtension;
    }

    /**
     * Get the file extennsion of a document type
     * @return the extension of a document type
     */
    public String getExtension( )
    {
        return _strExtension;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString( )
    {
        switch( this )
        {
            case PDF:
                return "PDF";
            case CSV:
                return "CSV";
        }
        return null;
    }

    /**
     * Get document type from string doc type
     * @param strDocType
     * @return the DocumentType obj
     */
    public DocumentType getFromString( String strDocType )
    {
        switch( strDocType )
        {
            case "PDF":
                return DocumentType.PDF;
            case "CSV":
                return DocumentType.CSV;
        }
        return null;
    }

    /**
     * Get a reference list of the enum
     * @return a reference list of the enum
     */
    public static ReferenceList toReferenceList( )
    {
        ReferenceList refList = new ReferenceList( );
        for ( DocumentType docType : DocumentType.values( ) )
        {
            ReferenceItem item = new ReferenceItem( );
            item.setCode( docType.toString( ) );
            item.setName( docType.toString( ) );

            refList.add( item );
        }
        return refList;
    }
}
