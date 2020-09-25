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

import java.util.List;
import java.util.Locale;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;

/**
 * ConfigProducerHome
 * 
 */
public final class ConfigProducerHome
{
    private static IConfigProducerDAO _dao = SpringContextService.getBean( "configProducerDAO" );

    /**
     * Constructor
     */
    private ConfigProducerHome( )
    {
    }

    /**
     * This method add a new config with different forms entry selected by AdminUser
     * 
     * @param plugin
     *            The plugin
     * @param configProducer
     *            configuration
     * @param listIdQuestion
     *            The list of entry id that appear in configuration
     */
    public static void addNewConfig( Plugin plugin, ConfigProducer configProducer, List<Integer> listIdQuestion )
    {
        _dao.addNewConfig( plugin, configProducer, listIdQuestion );
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
    public static ConfigProducer loadConfig( Plugin plugin, int nIdConfig )
    {
        return _dao.loadConfig( plugin, nIdConfig );
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
    public static List<ConfigProducer> loadListProducerConfig( Plugin plugin, int nIdForms )
    {
        return _dao.loadListProducerConfig( plugin, nIdForms );
    }

    /**
     * This method load a list of id Question by id config
     * 
     * @param plugin
     *            The plugin
     * @param nIdConfig
     *            The config id
     * @return The id entry list
     */
    public static List<Integer> loadListConfigQuestion( Plugin plugin, int nIdConfig )
    {
        return _dao.loadListConfigQuestion( plugin, nIdConfig );
    }

    /**
     * This method delete a config by id
     * 
     * @param plugin
     *            plugin
     * @param nIdConfigProducer
     *            id config producer
     */
    public static void deleteProducerConfig( Plugin plugin, int nIdConfigProducer )
    {
        _dao.deleteProducerConfig( plugin, nIdConfigProducer );
    }

    /**
     * This method modify a config
     * 
     * @param plugin
     *            plugin
     * @param configProducer
     *            configuration
     * @param listIdQuestion
     *            list of id entry
     */
    public static void modifyProducerConfig( Plugin plugin, ConfigProducer configProducer, List<Integer> listIdQuestion )
    {
        _dao.modifyProducerConfig( plugin, configProducer, listIdQuestion );
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
    public static void copyProducerConfig( Plugin plugin, int nIdConfig, Locale locale )
    {
        _dao.copyProducerConfig( plugin, nIdConfig, locale );
    }

    /**
     * This method check if a config exists for a specific form
     * 
     * @param plugin
     *            plugin
     * @param nIdForm
     *            id of form
     */
    public static void deleteByForm( Plugin plugin, int nIdForm )
    {
        _dao.deleteByForm( plugin, nIdForm );
    }

    /**
     * This method check if an entry is used by a config
     * 
     * @param plugin
     *            plugin
     * @param nIdQuestion
     *            id of entry
     * @return true an entry is used by a config otherwise false
     */
    public static boolean checkQuestion( Plugin plugin, int nIdQuestion )
    {
        return _dao.checkQuestion( plugin, nIdQuestion );
    }

    /**
     * This method loads a default config
     * 
     * @param plugin
     *            plugin
     * @param nIdForms
     *            id forms
     * @param docType
     * @return id config
     */
    public static ConfigProducer loadDefaultConfig( Plugin plugin, int nIdForms, DocumentType docType )
    {
        return _dao.loadDefaultConfig( plugin, nIdForms, docType );
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
    public static List<ConfigProducer> loadDefaultConfigList( Plugin plugin, int nIdForms )
    {
        return _dao.loadDefaultConfigList( plugin, nIdForms );
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
    public static void createDefaultConfig( Plugin plugin, int nIdForms, int nIdConfig, DocumentType docType )
    {
        _dao.createDefaultConfig( plugin, nIdForms, nIdConfig, docType );
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
    public static void updateDefaultConfig( Plugin plugin, int nIdForms, int nIdConfig, DocumentType docType )
    {
        _dao.updateDefaultConfig( plugin, nIdForms, nIdConfig, docType );
    }

    /**
     * Remove all the default config by given form id
     * 
     * @param plugin
     *            The plugin
     * @param nIdForm
     *            The id form
     */
    public static void removeAllDefaultConfigOfForm( Plugin plugin, int nIdForm )
    {
        _dao.removeAllDefaultConfigOfForm( plugin, nIdForm );
    }
}
