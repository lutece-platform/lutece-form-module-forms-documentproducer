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

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Configuration to generate PDF. AdminUser can select different forms entry to make them appear in the PDF.
 * 
 */
public class ConfigProducer implements IConfigProducer
{
    private int _nIdProducerConfig;
    @NotEmpty( message = "#i18n{module.forms.documentproducer.validation.config.producer.notEmpty}" )
    private String _strName;
    private int _nIdQuestionFileName;
    private int _nIdForm;
    private String _strType;
    private String _strTypeConfigFileName;
    private String _strTextFileName;
    private Boolean _bExtractFilled;

    /**
     * @return the _nIdProducerConfig
     */
    @Override
    public int getIdProducerConfig( )
    {
        return _nIdProducerConfig;
    }

    /**
     * @param nIdProducerConfig
     *            the _nIdProducerConfig to set
     */
    @Override
    public void setIdProducerConfig( int nIdProducerConfig )
    {
        _nIdProducerConfig = nIdProducerConfig;
    }

    /**
     * @return the _strName
     */
    @Override
    public String getName( )
    {
        return _strName;
    }

    /**
     * @param nIdQuestionFileName
     *            the _nIdQuestionFileName to set
     */
    @Override
    public void setIdQuestionFileName( int nIdQuestionFileName )
    {
        _nIdQuestionFileName = nIdQuestionFileName;
    }

    /**
     * @return the _nIdQuestionFileName
     */
    @Override
    public int getIdQuestionFileName( )
    {
        return _nIdQuestionFileName;
    }

    /**
     * @param strName
     *            the _strName to set
     */
    @Override
    public void setName( String strName )
    {
        _strName = strName;
    }

    /**
     * @return the _nIdForms
     */
    @Override
    public int getIdForm( )
    {
        return _nIdForm;
    }

    /**
     * @param nIdForms
     *            the _nIdForms to set
     */
    @Override
    public void setIdForm( int nIdForms )
    {
        _nIdForm = nIdForms;
    }

    /**
     * @return the _strType
     */
    @Override
    public String getType( )
    {
        return _strType;
    }

    /**
     * @param strType
     *            the _strType to set
     */
    @Override
    public void setType( String strType )
    {
        _strType = strType;
    }

    /**
     * @return the _strTypeConfigFileName
     */
    @Override
    public String getTypeConfigFileName( )
    {
        return _strTypeConfigFileName;
    }

    /**
     * @param strTypeConfigFileName
     *            the _strTypeConfigFileName to set
     */
    @Override
    public void setTypeConfigFileName( String strTypeConfigFileName )
    {
        _strTypeConfigFileName = strTypeConfigFileName;
    }

    /**
     * @return the _strTextFileName
     */
    @Override
    public String getTextFileName( )
    {
        return _strTextFileName;
    }

    /**
     * @param strTextFileName
     *            the _strTextFileName to set
     */
    @Override
    public void setTextFileName( String strTextFileName )
    {
        _strTextFileName = strTextFileName;
    }

    /**
     * @return the _bExtractFilled
     */
    @Override
    public Boolean getExtractFilled( )
    {
        return _bExtractFilled;
    }

    /**
     * @param bExtractFilled
     *            the _bExtractFilled to set
     */
    @Override
    public void setExtractFilled( Boolean bExtractFilled )
    {
        this._bExtractFilled = bExtractFilled;
    }
}
