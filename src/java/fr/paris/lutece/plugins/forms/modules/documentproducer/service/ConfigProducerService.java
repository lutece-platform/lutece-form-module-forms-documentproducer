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
package fr.paris.lutece.plugins.forms.modules.documentproducer.service;

import fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig.ConfigProducer;
import fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig.ConfigProducerHome;
import fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig.DocumentType;
import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * ConfigProducerService
 *
 */
public class ConfigProducerService
{
    /**
     * This method add a new config with different forms entry selected by AdminUser
     * 
     * @param plugin
     *            The plugin
     * @param configProducer
     *            configuration
     * @param listIdEntry
     *            The list of entry id that appear in configuration
     */
    public void addNewConfig( Plugin plugin, ConfigProducer configProducer, List<Integer> listIdEntry )
    {
        ConfigProducerHome.addNewConfig( plugin, configProducer, listIdEntry );
    }

    /**
     * This method load a config
     * 
     * @param plugin
     *            plugin
     * @param nIdConfig
     *            config id
     * @return ConfigProducer
     */
    public ConfigProducer loadConfig( Plugin plugin, int nIdConfig )
    {
        return ConfigProducerHome.loadConfig( plugin, nIdConfig );
    }

    /**
     * This method load a list of config by forms id and type
     * 
     * @param plugin
     *            The plugin
     * @param nIdForms
     *            The id of forms
     * @return The ProducerConfig list
     */
    public List<ConfigProducer> loadListProducerConfig( Plugin plugin, int nIdForms )
    {
        return ConfigProducerHome.loadListProducerConfig( plugin, nIdForms );
    }

    /**
     * This method load a list of id Entry by id config
     * 
     * @param plugin
     *            The plugin
     * @param nIdConfig
     *            The config id
     * @return The id entry list
     */
    public List<Integer> loadListConfigQuestion( Plugin plugin, int nIdConfig )
    {
        if ( ( nIdConfig == -1 ) || ( nIdConfig == 0 ) )
        {
            return new ArrayList<>( );
        }

        return ConfigProducerHome.loadListConfigQuestion( plugin, nIdConfig );
    }

    /**
     * This method delete a config by id
     * 
     * @param plugin
     *            plugin
     * @param nIdConfigProducer
     *            id config producer
     */
    public void deleteProducerConfig( Plugin plugin, int nIdConfigProducer )
    {
        ConfigProducerHome.deleteProducerConfig( plugin, nIdConfigProducer );
    }

    /**
     * This method modify a config
     * 
     * @param plugin
     *            plugin
     * @param configProducer
     *            configuration
     * @param listIdEntry
     *            list of id entry
     */
    public void modifyProducerConfig( Plugin plugin, ConfigProducer configProducer, List<Integer> listIdEntry )
    {
        ConfigProducerHome.modifyProducerConfig( plugin, configProducer, listIdEntry );
    }

    /**
     * This method copy a config
     * 
     * @param plugin
     *            plugin
     * @param nIdConfig
     *            id configproduducer
     * @param locale
     *            locale
     */
    public void copyProducerConfig( Plugin plugin, int nIdConfig, Locale locale )
    {
        ConfigProducerHome.copyProducerConfig( plugin, nIdConfig, locale );
    }

    /**
     * This method loads a default config for a given docType
     * 
     * @param plugin
     *            plugin
     * @param nIdForms
     *            id forms
     * @param docType
     * @return id config
     */
    public ConfigProducer loadDefaultConfig( Plugin plugin, int nIdForms, DocumentType docType )
    {
        return ConfigProducerHome.loadDefaultConfig( plugin, nIdForms, docType );
    }

    /**
     * This method loads a default config
     * 
     * @param plugin
     *            plugin
     * @param nIdForms
     *            id forms
     * @return id config
     */
    public List<ConfigProducer> loadDefaultConfigList( Plugin plugin, int nIdForms )
    {
        return ConfigProducerHome.loadDefaultConfigList( plugin, nIdForms );
    }

    /**
     * This method add default config
     * 
     * @param plugin
     *            plugin
     * @param nIdForms
     *            id forms
     * @param nIdConfig
     *            id config
     * @param docType
     */
    public void createDefaultConfig( Plugin plugin, int nIdForms, int nIdConfig, DocumentType docType )
    {
        ConfigProducerHome.createDefaultConfig( plugin, nIdForms, nIdConfig, docType );
    }

    /**
     * This method update default config
     * 
     * @param plugin
     *            plugin
     * @param nIdForms
     *            id forms
     * @param nIdConfig
     *            id config
     * @param docType
     */
    public void updateDefaultConfig( Plugin plugin, int nIdForms, int nIdConfig, DocumentType docType )
    {
        ConfigProducerHome.updateDefaultConfig( plugin, nIdForms, nIdConfig, docType );
    }

    /**
     * This method update default config
     * 
     * @param plugin
     *            plugin
     * @param nIdForm
     *            the if form
     */
    public void removeAllDefaultConfigByIdForm( Plugin plugin, int nIdForm )
    {
        ConfigProducerHome.removeAllDefaultConfigOfForm( plugin, nIdForm );
    }
}
