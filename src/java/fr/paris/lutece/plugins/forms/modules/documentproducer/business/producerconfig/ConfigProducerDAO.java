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
package fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * Allow to access ProducerConfig data
 * 
 */
public class ConfigProducerDAO implements IConfigProducerDAO
{
    private static final String SQL_QUERY_INSERT_CONFIG_PRODUCER = "INSERT INTO forms_config_producer (id_config,name,id_question_name_file,id_form,config_type,text_file_name,type_config_file_name,extract_empty) VALUES ( ? , ? , ? , ? , ? , ? , ?, ? );";
    private static final String SQL_QUERY_INSERT_CONFIG_QUESTION = "INSERT INTO forms_config_question (id_config,id_question) VALUES ( ? , ? );";
    private static final String SQL_QUERY_SELECT_MAX_ID = "SELECT max(id_config) FROM forms_config_producer";
    private static final String SQL_QUERY_SELECT_CONFIG = "SELECT id_config, name, id_question_name_file, id_form, config_type, text_file_name, type_config_file_name,extract_empty FROM forms_config_producer WHERE id_config = ? ;";
    private static final String SQL_QUERY_SELECT_CONFIG_BY_FORMS = "SELECT id_config, name, id_question_name_file, id_form, config_type, text_file_name, type_config_file_name FROM forms_config_producer WHERE id_form = ? ;";
    private static final String SQL_QUERY_SELECT_CONFIG_QUESTION = "SELECT id_question FROM forms_config_question WHERE id_config = ? ;";
    private static final String SQL_QUERY_DELETE_CONFIG_PRODUCER = "DELETE FROM forms_config_producer WHERE id_config = ? ";
    private static final String SQL_QUERY_DELETE_CONFIG_QUESTION = "DELETE FROM forms_config_question WHERE id_config = ? ";
    private static final String SQL_QUERY_UPDATE_CONFIG_QUESTION = "UPDATE forms_config_producer SET name = ? , id_question_name_file = ? , config_type = ? , text_file_name = ? , type_config_file_name = ?, extract_empty = ? WHERE id_config = ? ;";
    private static final String SQL_QUERY_SELECT_BY_FORMS = "SELECT id_config, name, id_question_name_file, id_form, config_type, text_file_name, type_config_file_name FROM forms_config_producer WHERE id_form = ? ;";
    private static final String SQL_QUERY_DELETE_BY_FORMS = "DELETE FROM forms_config_producer WHERE id_form = ? ;";
    private static final String SQL_QUERY_SELECT_QUESTION = "SELECT id_config, id_question FROM forms_config_question WHERE id_question = ? ;";
    private static final String PARAMETER_COPY_NAME = "module.forms.documentproducer.create.producer.config.copy.name";
    private static final String SQL_QUERY_SELECT_CONFIG_DEFAULT = "SELECT fcp.id_config, fcp.name, fcp.id_question_name_file, fcp.id_form, fcp.config_type, fcp.text_file_name, fcp.type_config_file_name,fcp.extract_empty FROM forms_default_config fdc LEFT JOIN forms_config_producer fcp ON fcp.id_config = fdc.id_config  WHERE fdc.id_form = ? AND fdc.config_type = ? ;";
    private static final String SQL_QUERY_SELECT_CONFIG_DEFAULT_LIST = "SELECT fcp.id_config, fcp.name, fcp.id_question_name_file, fcp.id_form, fcp.config_type, fcp.text_file_name, fcp.type_config_file_name,fcp.extract_empty FROM forms_default_config fdc LEFT JOIN forms_config_producer fcp ON fcp.id_config = fdc.id_config  WHERE fdc.id_form = ? ;";
    private static final String SQL_QUERY_SAVE_CONFIG_DEFAULT = "INSERT INTO forms_default_config (id_config, id_form, config_type ) VALUES ( ? , ?, ? ) ;";
    private static final String SQL_QUERY_UPDATE_CONFIG_DEFAULT = "UPDATE forms_default_config SET id_config = ? WHERE id_form = ? AND config_type = ? ;";
    private static final String SQL_QUERY_DELETE_CONFIG_DEFAULT_BY_ID_FORM = "DELETE FROM forms_default_config WHERE id_form = ? ;";

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewConfig( Plugin plugin, ConfigProducer configProducer, List<Integer> listIdQuestion )
    {
        int nIdConfig = 1;
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_MAX_ID, plugin ) )
        {
            daoUtil.executeQuery( );

            if ( daoUtil.next( ) )
            {
                nIdConfig = daoUtil.getInt( 1 ) + 1;
            }
            configProducer.setIdProducerConfig( nIdConfig );

        }

        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_CONFIG_PRODUCER, plugin ) )
        {
            daoUtil.setInt( 1, nIdConfig );
            daoUtil.setString( 2, configProducer.getName( ) );
            daoUtil.setInt( 3, configProducer.getIdQuestionFileName( ) );
            daoUtil.setInt( 4, configProducer.getIdForm( ) );
            daoUtil.setString( 5, configProducer.getType( ) );
            daoUtil.setString( 6, configProducer.getTextFileName( ) );
            daoUtil.setString( 7, configProducer.getTypeConfigFileName( ) );
            daoUtil.setBoolean( 8, configProducer.getExtractFilled( ) );

            daoUtil.executeUpdate( );
        }

        if ( !listIdQuestion.isEmpty( ) )
        {
            for ( Integer idQuestion : listIdQuestion )
            {
                try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_CONFIG_QUESTION, plugin ) )
                {
                    daoUtil.setInt( 1, nIdConfig );
                    daoUtil.setInt( 2, idQuestion.intValue( ) );
                    daoUtil.executeUpdate( );
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigProducer loadConfig( Plugin plugin, int nIdConfig )
    {
        ConfigProducer configProducer = new ConfigProducer( );

        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_CONFIG, plugin ) )
        {
            daoUtil.setInt( 1, nIdConfig );
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                configProducer.setIdProducerConfig( daoUtil.getInt( 1 ) );
                configProducer.setName( daoUtil.getString( 2 ) );
                configProducer.setIdQuestionFileName( daoUtil.getInt( 3 ) );
                configProducer.setIdForm( daoUtil.getInt( 4 ) );
                configProducer.setType( daoUtil.getString( 5 ) );
                configProducer.setTextFileName( daoUtil.getString( 6 ) );
                configProducer.setTypeConfigFileName( daoUtil.getString( 7 ) );
                configProducer.setExtractFilled( daoUtil.getBoolean( 8 ) );
            }
        }

        return configProducer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ConfigProducer> loadListProducerConfig( Plugin plugin, int nIdForms )
    {
        List<ConfigProducer> listProducerConfig = new ArrayList<>( );

        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_CONFIG_BY_FORMS, plugin ) )
        {
            daoUtil.setInt( 1, nIdForms );
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                ConfigProducer producerConfig = new ConfigProducer( );
                producerConfig.setIdProducerConfig( daoUtil.getInt( 1 ) );
                producerConfig.setName( daoUtil.getString( 2 ) );
                producerConfig.setIdQuestionFileName( daoUtil.getInt( 3 ) );
                producerConfig.setIdForm( nIdForms );
                producerConfig.setType( daoUtil.getString( 5 ) );
                producerConfig.setTextFileName( daoUtil.getString( 6 ) );
                producerConfig.setTypeConfigFileName( daoUtil.getString( 7 ) );
                listProducerConfig.add( producerConfig );
            }
        }

        return listProducerConfig;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> loadListConfigQuestion( Plugin plugin, int nIdConfig )
    {
        List<Integer> listIdQuestion = new ArrayList<>( );

        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_CONFIG_QUESTION, plugin ) )
        {
            daoUtil.setInt( 1, nIdConfig );
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                listIdQuestion.add( Integer.valueOf( daoUtil.getInt( 1 ) ) );
            }
        }

        return listIdQuestion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProducerConfig( Plugin plugin, int nIdConfigProducer )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_CONFIG_PRODUCER, plugin ) )
        {
            daoUtil.setInt( 1, nIdConfigProducer );
            daoUtil.executeUpdate( );
        }

        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_CONFIG_QUESTION, plugin ) )
        {
            daoUtil.setInt( 1, nIdConfigProducer );
            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyProducerConfig( Plugin plugin, ConfigProducer configProducer, List<Integer> listIdQuestion )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_CONFIG_QUESTION, plugin ) )
        {
            daoUtil.setInt( 1, configProducer.getIdProducerConfig( ) );
            daoUtil.executeUpdate( );
        }

        if ( !listIdQuestion.isEmpty( ) )
        {
            for ( Integer idQuestion : listIdQuestion )
            {
                try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_CONFIG_QUESTION, plugin ) )
                {
                    daoUtil.setInt( 1, configProducer.getIdProducerConfig( ) );
                    daoUtil.setInt( 2, idQuestion.intValue( ) );
                    daoUtil.executeUpdate( );
                }
            }
        }

        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_CONFIG_QUESTION, plugin ) )
        {
            daoUtil.setString( 1, configProducer.getName( ) );
            daoUtil.setInt( 2, configProducer.getIdQuestionFileName( ) );
            daoUtil.setString( 3, configProducer.getType( ) );
            daoUtil.setString( 4, configProducer.getTextFileName( ) );
            daoUtil.setString( 5, configProducer.getTypeConfigFileName( ) );
            daoUtil.setBoolean( 6, configProducer.getExtractFilled( ) );
            daoUtil.setInt( 7, configProducer.getIdProducerConfig( ) );
            daoUtil.executeUpdate( );

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void copyProducerConfig( Plugin plugin, int nIdConfig, Locale locale )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_CONFIG, plugin ) )
        {
            daoUtil.setInt( 1, nIdConfig );
            daoUtil.executeQuery( );

            List<Integer> listIdQuestion = loadListConfigQuestion( plugin, nIdConfig );

            while ( daoUtil.next( ) )
            {
                ConfigProducer configProducer = new ConfigProducer( );
                configProducer.setName( I18nService.getLocalizedString( PARAMETER_COPY_NAME, locale ) + " " + daoUtil.getString( 2 ) );
                configProducer.setIdQuestionFileName( daoUtil.getInt( 3 ) );
                configProducer.setIdForm( daoUtil.getInt( 4 ) );
                configProducer.setType( daoUtil.getString( 5 ) );
                configProducer.setTextFileName( daoUtil.getString( 6 ) );
                configProducer.setTypeConfigFileName( daoUtil.getString( 7 ) );
                configProducer.setExtractFilled( daoUtil.getBoolean( 8 ) );

                addNewConfig( plugin, configProducer, listIdQuestion );
            }

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteByForm( Plugin plugin, int nIdForm )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_FORMS, plugin ) )
        {
            daoUtil.setInt( 1, nIdForm );
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                try ( DAOUtil daoUtil2 = new DAOUtil( SQL_QUERY_DELETE_CONFIG_QUESTION, plugin ) )
                {
                    daoUtil2.setInt( 1, daoUtil.getInt( 1 ) );
                    daoUtil2.executeUpdate( );
                }
            }

        }

        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_FORMS, plugin ) )
        {
            daoUtil.setInt( 1, nIdForm );
            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkQuestion( Plugin plugin, int nIdQuestion )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_QUESTION, plugin ) )
        {
            daoUtil.setInt( 1, nIdQuestion );
            daoUtil.executeQuery( );
            return daoUtil.next( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigProducer loadDefaultConfig( Plugin plugin, int nIdForms, DocumentType docType )
    {
        ConfigProducer defaultConfigProducer = new ConfigProducer( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_CONFIG_DEFAULT, plugin ) )
        {
            daoUtil.setInt( 1, nIdForms );
            daoUtil.setString( 2, docType.toString( ) );
            daoUtil.executeQuery( );

            if ( daoUtil.next( ) )
            {
                defaultConfigProducer.setIdProducerConfig( daoUtil.getInt( 1 ) );
                defaultConfigProducer.setName( daoUtil.getString( 2 ) );
                defaultConfigProducer.setIdQuestionFileName( daoUtil.getInt( 3 ) );
                defaultConfigProducer.setIdForm( daoUtil.getInt( 4 ) );
                defaultConfigProducer.setType( daoUtil.getString( 5 ) );
                defaultConfigProducer.setTextFileName( daoUtil.getString( 6 ) );
                defaultConfigProducer.setTypeConfigFileName( daoUtil.getString( 7 ) );
                defaultConfigProducer.setExtractFilled( daoUtil.getBoolean( 8 ) );
            }
        }

        return defaultConfigProducer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ConfigProducer> loadDefaultConfigList( Plugin plugin, int nIdForms )
    {
        List<ConfigProducer> listProducerConfig = new ArrayList<>( );

        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_CONFIG_DEFAULT_LIST, plugin ) )
        {
            daoUtil.setInt( 1, nIdForms );
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                ConfigProducer producerConfig = new ConfigProducer( );
                producerConfig.setIdProducerConfig( daoUtil.getInt( 1 ) );
                producerConfig.setName( daoUtil.getString( 2 ) );
                producerConfig.setIdQuestionFileName( daoUtil.getInt( 3 ) );
                producerConfig.setIdForm( nIdForms );
                producerConfig.setType( daoUtil.getString( 5 ) );
                producerConfig.setTextFileName( daoUtil.getString( 6 ) );
                producerConfig.setTypeConfigFileName( daoUtil.getString( 7 ) );
                listProducerConfig.add( producerConfig );
            }
        }

        return listProducerConfig;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createDefaultConfig( Plugin plugin, int nIdForms, int nIdConfig, DocumentType docType )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SAVE_CONFIG_DEFAULT, plugin ) )
        {
            daoUtil.setInt( 1, nIdConfig );
            daoUtil.setInt( 2, nIdForms );
            daoUtil.setString( 3, docType.toString( ) );
            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateDefaultConfig( Plugin plugin, int nIdForms, int nIdConfig, DocumentType docType )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_CONFIG_DEFAULT, plugin ) )
        {
            daoUtil.setInt( 1, nIdConfig );
            daoUtil.setInt( 2, nIdForms );
            daoUtil.setString( 3, docType.toString( ) );
            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllDefaultConfigOfForm( Plugin plugin, int nIdForm )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_CONFIG_DEFAULT_BY_ID_FORM, plugin ) )
        {
            daoUtil.setInt( 1, nIdForm );
            daoUtil.executeUpdate( );
        }
    }

}
