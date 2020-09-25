/*
 * Copyright (c) 2002-2020, City of Paris
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

/**
 * 
 * IConfigProducer
 * 
 */
public interface IConfigProducer
{
    /**
     * @return the _nIdProducerConfig
     */
    int getIdProducerConfig( );

    /**
     * @param nIdProducerConfig
     *            the _nIdProducerConfig to set
     */
    void setIdProducerConfig( int nIdProducerConfig );

    /**
     * @return the _strName
     */
    String getName( );

    /**
     * @param nIdQuestionFileName
     *            the _nIdQuestionFileName to set
     */
    void setIdQuestionFileName( int nIdQuestionFileName );

    /**
     * @return the _nIdQuestionFileName
     */
    int getIdQuestionFileName( );

    /**
     * @param strName
     *            the _strName to set
     */
    void setName( String strName );

    /**
     * @return the _nIdForm
     */
    int getIdForm( );

    /**
     * @param nIdForm
     *            the _nIdForm to set
     */
    void setIdForm( int nIdForm );

    /**
     * @return the _strType
     */
    String getType( );

    /**
     * @param strType
     *            the _strType to set
     */
    void setType( String strType );

    /**
     * @return the _strTypeConfigFileName
     */
    String getTypeConfigFileName( );

    /**
     * @param strTypeConfigFileName
     *            the _strTypeConfigFileName to set
     */
    void setTypeConfigFileName( String strTypeConfigFileName );

    /**
     * @return the _strTextFileName
     */
    String getTextFileName( );

    /**
     * @param strTextFileName
     *            the _strTextFileName to set
     */
    void setTextFileName( String strTextFileName );

    /**
     * @return the _bExtractNotFilled
     */
    Boolean getExtractFilled( );

    /**
     * @param bExtractFilled
     *            the _bExtractFilled to set
     */
    void setExtractFilled( Boolean bExtractFilled );
}
