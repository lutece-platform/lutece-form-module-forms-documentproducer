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

import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.List;
import java.util.Locale;

/**
 * IConfigProducerDAO
 */
public interface IConfigProducerDAO
{
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
    void addNewConfig( Plugin plugin, ConfigProducer configProducer, List<Integer> listIdQuestion );

    /**
     * This method load a config
     * 
     * @param plugin
     *            plugin
     * @param nIdConfig
     *            config id
     * @return a ConfigProducer
     */
    ConfigProducer loadConfig( Plugin plugin, int nIdConfig );

    /**
     * This method load a list of config by forms id and type
     * 
     * @param plugin
     *            The plugin
     * @param nIdForms
     *            The id of forms
     * @return The ProducerConfig list
     */
    List<ConfigProducer> loadListProducerConfig( Plugin plugin, int nIdForms );

    /**
     * This method load a list of id Question by id config
     * 
     * @param plugin
     *            The plugin
     * @param nIdConfig
     *            The config id
     * @return The id entry list
     */
    List<Integer> loadListConfigQuestion( Plugin plugin, int nIdConfig );

    /**
     * This method delete a config by id
     * 
     * @param plugin
     *            plugin
     * @param nIdConfigProducer
     *            id config producer
     */
    void deleteProducerConfig( Plugin plugin, int nIdConfigProducer );

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
    void modifyProducerConfig( Plugin plugin, ConfigProducer configProducer, List<Integer> listIdQuestion );

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
    void copyProducerConfig( Plugin plugin, int nIdConfig, Locale locale );

    /**
     * This method delete all config by id forms
     * 
     * @param plugin
     *            plugin
     * @param nIdForm
     *            id of forms
     */
    void deleteByForm( Plugin plugin, int nIdForm );

    /**
     * This method check if an entry is used by a config
     * 
     * @param plugin
     *            plugin
     * @param nIdQuestion
     *            id of entry
     * @return true an entry is used by a config otherwise false
     */
    boolean checkQuestion( Plugin plugin, int nIdQuestion );

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
    ConfigProducer loadDefaultConfig( Plugin plugin, int nIdForms, DocumentType docType );

    /**
     * This method loads a default config
     * 
     * @param plugin
     *            plugin
     * @param nIdForms
     *            id forms
     * @return id config
     */
    List<ConfigProducer> loadDefaultConfigList( Plugin plugin, int nIdForms );

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
    void createDefaultConfig( Plugin plugin, int nIdForms, int nIdConfig, DocumentType docType );

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
    void updateDefaultConfig( Plugin plugin, int nIdForms, int nIdConfig, DocumentType docType );

    /**
     * The method delete all the default config for a form
     * 
     * @param plugin
     *            the plugin
     * @param nIdForm
     *            the id form
     */
    void removeAllDefaultConfigOfForm( Plugin plugin, int nIdForm );
}
