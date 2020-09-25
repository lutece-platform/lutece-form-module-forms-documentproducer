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
package fr.paris.lutece.plugins.forms.modules.documentproducer.web;

import fr.paris.lutece.plugins.forms.business.Form;
import fr.paris.lutece.plugins.forms.business.FormHome;
import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.QuestionHome;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig.ConfigProducer;
import fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig.DocumentType;
import fr.paris.lutece.plugins.forms.modules.documentproducer.service.ConfigProducerService;
import fr.paris.lutece.plugins.forms.modules.documentproducer.service.FormsDocumentProducerResourceIdService;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.util.mvc.admin.MVCAdminJspBean;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.url.UrlItem;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * DocumentProducerJspBean
 * 
 */
@Controller( controllerJsp = "ManageConfigProducer.jsp", controllerPath = "jsp/admin/plugins/forms/modules/documentproducer/", right = "CONFIG_DOCUMENT_PRODUCER_MANAGEMENT" )
public class DocumentProducerJspBean extends MVCAdminJspBean
{
    // RIGHT
    public static final String RIGHT_MANAGE_CONFIG_PRODUCER = "CONFIG_DOCUMENT_PRODUCER_MANAGEMENT";

    // Templates
    private static final String TEMPLATE_CREATE_CONFIG = "admin/plugins/forms/modules/documentproducer/create_new_configproducer.html";
    private static final String TEMPLATE_MANAGE_CONFIG = "admin/plugins/forms/modules/documentproducer/manage_configproducer.html";
    private static final String TEMPLATE_MANAGE_DEFAULT_CONFIG = "admin/plugins/forms/modules/documentproducer/manage_default_configproducer.html";
    private static final String TEMPLATE_MODIFY_CONFIG = "admin/plugins/forms/modules/documentproducer/modify_configproducer.html";
    private static final String TEMPLATE_SELECT_FORM = "admin/plugins/forms/modules/documentproducer/select_form.html";

    // Parameters
    private static final String PARAMETER_ID_FORM = "id_form";
    private static final String PARAMETER_ID_CONFIG_PRODUCER = "id_config_producer";
    private static final String PARAMETER_CONFIG_QUESTION = "config_question";

    // Markers
    private static final String MARK_QUESTION_LIST = "question_list";
    private static final String MARK_QUESTION_REF_LIST = "question_ref_list";
    private static final String MARK_CONFIG_LIST = "config_list";
    private static final String MARK_ID_QUESTION_LIST = "id_question_list";
    private static final String MARK_CONFIG_PRODUCER = "config_producer";
    private static final String MARK_FORM = "form";
    private static final String MARK_DOCUMENT_TYPE_REF_LIST = "document_type_ref_list";
    private static final String MARK_DOCUMENT_TYPE_DEFAULT = "default_document_type_ref_list";
    private static final String MARK_FORM_REF_LIST = "form_ref_list";
    private static final String MARK_MAP_DEFAULT_CONFIG_REF_LIST = "map_default_config_ref_list";
    private static final String MARK_MAP_DEFAULT_CONFIG_DEFAULT_VALUE = "map_default_config_default_value";

    // Properties
    private static final String PROPERTY_ID_ENTRIES_TYPE_ALLOWED = "forms.filter.entries.type.config.default";

    // Messages (I18n keys)
    private static final String MESSAGE_DELETED_CONFIG = "module.forms.documentproducer.message.producer.config.deleted";
    private static final String MESSAGE_CREATED_CONFIG = "module.forms.documentproducer.message.producer.config.created";
    private static final String MESSAGE_SAVE_ADVANCED_PARAM_CONFIG_PRODUCER = "module.forms.documentproducer.message.producer.advancedParam.saved";
    private static final String MESSAGE_CONFIRM_REMOVE_CONFIG_PRODUCER = "module.forms.documentproducer.message.producer.config.confirm.deletion";
    private static final String MESSAGE_MODIFY_CONFIG_PRODUCER = "module.forms.documentproducer.message.producer.config.modified";

    private static final String MESSAGE_PAGE_TITLE_MANAGE_CONFIG_PRODUCER = "module.forms.documentproducer.message.pageTitleManageConfigProducer";
    private static final String MESSAGE_PAGE_TITLE_CREATE_CONFIG_PRODUCER = "module.forms.documentproducer.message.pageTitleCreateConfigProducer";
    private static final String MESSAGE_PAGE_TITLE_MODIFY_CONFIG_PRODUCER = "module.forms.documentproducer.message.pageTitleModifyConfigProducer";
    private static final String MESSAGE_PAGE_TITLE_MANAGE_ADVANCED_PARAM_CONFIG_PRODUCER = "module.forms.documentproducer.message.pageTitleManageAdvancedParamConfigProducer";
    private static final String MESSAGE_PAGE_TITLE_SELECT_FORM = "module.forms.documentproducer.message.pageTitleSelectForm";

    // CONSTANTS
    private static final String COMMA = ",";

    // VIEWS
    private static final String VIEW_MANAGE_CONFIG_PRODUCER = "getManageConfigProducer";
    private static final String VIEW_GET_CREATE_CONFIG_PRODUCER = "getCreateConfigProducer";
    private static final String VIEW_CONFIRM_DELETE_CONFIG_PRODUCER = "getConfirmDeleteConfigProducer";
    private static final String VIEW_GET_MODIFY_CONFIG_PRODUCER = "getModifyConfigProducer";
    private static final String VIEW_MANAGE_ADVANCED_PARAM_CONFIG_PRODUCER = "getManageAdvancedParametersConfigProducer";
    private static final String VIEW_SELECT_FORM = "getSelectForm";

    // ACTIONS
    private static final String ACTION_CREATE_CONFIG_PRODUCER = "doCreateConfigProducer";
    private static final String ACTION_DELETE_CONFIG_PRODUCER = "doDeleteConfigProducer";
    private static final String ACTION_MODIFY_CONFIG_PRODUCER = "doModifyConfigProducer";
    private static final String ACTION_SAVE_ADVANCED_PARAM_CONFIG_PRODUCER = "doSaveAdvancedParamConfigProducer";
    private static final String ACTION_CREATE_COPY_CONFIG_PRODUCER = "doCreateCopyConfigProducer";

    // BEANS
    private static final ConfigProducerService _manageConfigProducerService = SpringContextService.getBean( "forms-documentproducer.manageConfigProducer" );
    // BEANS VALIDATION
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "modules.forms.documentproducer.model.entity.config.producer.attribute.";

    // SESSION VARIABLE
    ConfigProducer _configProducer;
    Form _form;

    @View( value = VIEW_SELECT_FORM, defaultView = true )
    public String getSelectForm( HttpServletRequest request ) throws AccessDeniedException
    {
        String strIdForm = request.getParameter( PARAMETER_ID_FORM );

        if ( strIdForm != null )
        {
            return redirectView( request, VIEW_MANAGE_CONFIG_PRODUCER );
        }

        List<Form> listAuthorizedForm = (List<Form>) RBACService.getAuthorizedCollection( FormHome.getFormList( ),
                FormsDocumentProducerResourceIdService.PERMISSION_MANAGE_DOCUMENTPRODUCER, AdminUserService.getAdminUser( request ) );
        Map<String, Object> model = getModel( );
        model.put( MARK_FORM_REF_LIST, ReferenceList.convert( listAuthorizedForm, "id", "title", true ) );

        return getPage( MESSAGE_PAGE_TITLE_SELECT_FORM, TEMPLATE_SELECT_FORM, model );
    }

    /**
     * Display the page to manage configuration
     * 
     * @param request
     *            request
     * @return configuration html page
     * @throws fr.paris.lutece.portal.service.admin.AccessDeniedException
     */
    @View( value = VIEW_MANAGE_CONFIG_PRODUCER )
    public String getManageConfigProducer( HttpServletRequest request ) throws AccessDeniedException
    {
        String strIdForm = request.getParameter( PARAMETER_ID_FORM );

        if ( strIdForm == null )
        {
            if ( _form == null )
            {
                return redirectView( request, VIEW_SELECT_FORM );
            }
        }
        else
        {
            _form = FormHome.findByPrimaryKey( Integer.parseInt( request.getParameter( PARAMETER_ID_FORM ) ) );
        }

        _configProducer = new ConfigProducer( );

        checkAuthorized( _form );

        Map<String, Object> model = new HashMap<>( );

        List<ConfigProducer> listConfigProducer = _manageConfigProducerService.loadListProducerConfig( getPlugin( ), _form.getId( ) );
        model.put( MARK_CONFIG_LIST, listConfigProducer );

        return getPage( MESSAGE_PAGE_TITLE_MANAGE_CONFIG_PRODUCER, TEMPLATE_MANAGE_CONFIG, model );
    }

    /**
     * Display the page to create a new configuration
     * 
     * @param request
     *            request
     * @return create html page
     * @throws fr.paris.lutece.portal.service.admin.AccessDeniedException
     */
    @View( value = VIEW_GET_CREATE_CONFIG_PRODUCER )
    public String createConfigProducer( HttpServletRequest request ) throws AccessDeniedException
    {
        _configProducer = new ConfigProducer( );

        Map<String, Object> model = getModel( );

        checkAuthorized( _form );

        // Filter the question list to keep only strings and num entry types
        List<Question> listQuestionTextNum = getTextAndNumQuestion( QuestionHome.getListQuestionByIdForm( _form.getId( ) ) );

        model.put( MARK_DOCUMENT_TYPE_REF_LIST, DocumentType.toReferenceList( ) );
        model.put( MARK_QUESTION_LIST, listQuestionTextNum );
        model.put( MARK_QUESTION_REF_LIST, ReferenceList.convert( listQuestionTextNum, "id", "title", true ) );
        model.put( MARK_CONFIG_PRODUCER, _configProducer );
        model.put( MARK_DOCUMENT_TYPE_DEFAULT, DocumentType.PDF.toString( ) );

        return getPage( MESSAGE_PAGE_TITLE_CREATE_CONFIG_PRODUCER, TEMPLATE_CREATE_CONFIG, model );
    }

    /**
     * Create a new configuration
     * 
     * @param request
     *            request
     * @return message to confirm the creation or not
     * @throws fr.paris.lutece.portal.service.admin.AccessDeniedException
     */
    @Action( value = ACTION_CREATE_CONFIG_PRODUCER )
    public String doCreateConfigProducer( HttpServletRequest request ) throws AccessDeniedException
    {
        // Check if user is authorized and if selected questions are authorized
        checkAuthorized( _form );
        List<Integer> listIdQuestion = checkQuestionTypes( request, _form );

        populate( _configProducer, request );

        if ( validateBean( _configProducer, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            _manageConfigProducerService.addNewConfig( getPlugin( ), _configProducer, listIdQuestion );
            addInfo( MESSAGE_CREATED_CONFIG, request.getLocale( ) );
            return redirectView( request, VIEW_MANAGE_CONFIG_PRODUCER );
        }
        else
        {
            return redirectView( request, VIEW_GET_CREATE_CONFIG_PRODUCER );
        }
    }

    /**
     * Message to confirm to delete a configuration
     * 
     * @param request
     *            request
     * @return admin message
     */
    @View( value = VIEW_CONFIRM_DELETE_CONFIG_PRODUCER )
    public String getConfirmDeleteConfigProducer( HttpServletRequest request ) throws AccessDeniedException
    {
        checkAuthorized( _form );
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_CONFIG_PRODUCER ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_DELETE_CONFIG_PRODUCER ) );
        url.addParameter( PARAMETER_ID_CONFIG_PRODUCER, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_CONFIG_PRODUCER, url.getUrl( ),
                AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Delete a configuration
     * 
     * @param request
     *            request
     * @return a message to confirm and redirect to manage page
     */
    @Action( value = ACTION_DELETE_CONFIG_PRODUCER )
    public String doDeleteConfigProducer( HttpServletRequest request )
    {
        String strIdConfigProducer = request.getParameter( PARAMETER_ID_CONFIG_PRODUCER );

        _manageConfigProducerService.deleteProducerConfig( getPlugin( ), Integer.parseInt( strIdConfigProducer ) );
        addInfo( MESSAGE_DELETED_CONFIG, getLocale( ) );
        return redirectView( request, VIEW_MANAGE_CONFIG_PRODUCER );
    }

    /**
     * Display the page to modify a configuration
     * 
     * @param request
     *            request
     * @return modify html page
     */
    @View( value = VIEW_GET_MODIFY_CONFIG_PRODUCER )
    public String getModifyConfigProducer( HttpServletRequest request ) throws AccessDeniedException
    {
        checkAuthorized( _form );
        _configProducer = _manageConfigProducerService.loadConfig( getPlugin( ), Integer.parseInt( request.getParameter( PARAMETER_ID_CONFIG_PRODUCER ) ) );

        Map<String, Object> model = getModel( );

        // Filter the question list to keep only strings and num entry types
        List<Question> listQuestionTextNum = getTextAndNumQuestion( QuestionHome.getListQuestionByIdForm( _form.getId( ) ) );

        model.put( MARK_QUESTION_LIST, listQuestionTextNum );
        model.put( MARK_DOCUMENT_TYPE_REF_LIST, DocumentType.toReferenceList( ) );
        model.put( MARK_DOCUMENT_TYPE_DEFAULT, _configProducer.getType( ) );
        model.put( MARK_CONFIG_PRODUCER, _configProducer );
        model.put( MARK_QUESTION_REF_LIST, ReferenceList.convert( listQuestionTextNum, "id", "title", true ) );
        model.put( MARK_FORM, _form );
        model.put( MARK_ID_QUESTION_LIST, _manageConfigProducerService.loadListConfigQuestion( getPlugin( ), _configProducer.getIdProducerConfig( ) ) );

        return getPage( MESSAGE_PAGE_TITLE_MODIFY_CONFIG_PRODUCER, TEMPLATE_MODIFY_CONFIG, model );
    }

    /**
     * Modify a configuration
     * 
     * @param request
     *            request
     * @return a message to confirm and redirect to manage page
     */
    @Action( value = ACTION_MODIFY_CONFIG_PRODUCER )
    public String doModifyConfigProducer( HttpServletRequest request ) throws AccessDeniedException
    {
        // Check if user is authorized and if selected questions are authorized
        checkAuthorized( _form );
        List<Integer> listIdEntry = checkQuestionTypes( request, _form );

        populate( _configProducer, request );

        if ( validate( _configProducer ).isEmpty( ) )
        {
            _manageConfigProducerService.modifyProducerConfig( getPlugin( ), _configProducer, listIdEntry );
            addInfo( MESSAGE_MODIFY_CONFIG_PRODUCER, request.getLocale( ) );
            return getManageConfigProducer( request );
        }

        Map<String, Object> model = getModel( );
        model.put( MARK_CONFIG_PRODUCER, _configProducer );
        return getPage( MESSAGE_PAGE_TITLE_MODIFY_CONFIG_PRODUCER, TEMPLATE_MANAGE_CONFIG, model );
    }

    /**
     * Copy a configuration
     * 
     * @param request
     *            request
     * @return manage page
     */
    @Action( value = ACTION_CREATE_COPY_CONFIG_PRODUCER )
    public String doCopyConfigProducer( HttpServletRequest request ) throws AccessDeniedException
    {
        checkAuthorized( _form );

        String strIdConfigProducer = request.getParameter( PARAMETER_ID_CONFIG_PRODUCER );
        _manageConfigProducerService.copyProducerConfig( getPlugin( ), Integer.parseInt( strIdConfigProducer ), request.getLocale( ) );

        return redirectView( request, VIEW_MANAGE_CONFIG_PRODUCER );
    }

    /**
     * This method delete an id group entry if it have no child in config list
     * 
     * @param listIdEntry
     *            list of id entry
     */
    private List<Integer> checkQuestionTypes( HttpServletRequest request, Form form )
    {
        String [ ] tabParamConfigQuestion = request.getParameterValues( PARAMETER_CONFIG_QUESTION );
        if ( tabParamConfigQuestion != null && tabParamConfigQuestion.length > 0 )
        {
            Collection<String> listStrIdQuestion = Arrays.asList( request.getParameterValues( PARAMETER_CONFIG_QUESTION ) );
            List<String> listStrIdAuthorizedQuestion = getTextAndNumQuestion( QuestionHome.getListQuestionByIdForm( form.getId( ) ) ).stream( )
                    .map( question -> question.getId( ) ).map( id -> String.valueOf( id ) ).collect( Collectors.toList( ) );

            if ( !listStrIdAuthorizedQuestion.containsAll( listStrIdQuestion ) )
            {
                throw new AppException( );
            }
            else
            {
                return listStrIdQuestion.stream( ).map( strIdQuestion -> Integer.parseInt( strIdQuestion ) ).collect( Collectors.toList( ) );
            }
        }
        return new ArrayList( );
    }

    /**
     * This method displays the html page to choice the default configuration
     * 
     * @param request
     *            request
     * @return html page to manage advanced parameters
     */
    @View( value = VIEW_MANAGE_ADVANCED_PARAM_CONFIG_PRODUCER )
    public String getManageAdvancedParameters( HttpServletRequest request ) throws AccessDeniedException
    {
        checkAuthorized( _form );

        Map<String, Object> model = getModel( );

        List<ConfigProducer> listDefaultConfig = _manageConfigProducerService.loadDefaultConfigList( getPlugin( ), _form.getId( ) );

        List<ConfigProducer> listConfigProducer = _manageConfigProducerService.loadListProducerConfig( getPlugin( ), _form.getId( ) );

        Map<String, ReferenceList> referenceListByDocType = new HashMap<>( );
        Map<String, String> defaultValues = new HashMap<>( );

        for ( DocumentType docType : DocumentType.values( ) )
        {
            ReferenceList refListDocType = new ReferenceList( );

            // Get the defaultConfig for this docType
            Optional<ConfigProducer> defaultConfigDocType = listDefaultConfig.stream( ).filter( config -> config.getType( ).equals( docType.toString( ) ) )
                    .findFirst( );

            if ( defaultConfigDocType.isPresent( ) )
            {
                defaultValues.put( docType.toString( ), String.valueOf( defaultConfigDocType.get( ).getIdProducerConfig( ) ) );
            }

            listConfigProducer.stream( ).filter( config -> config.getType( ).equals( docType.toString( ) ) ).forEach( conf -> {
                ReferenceItem item = new ReferenceItem( );
                item.setCode( String.valueOf( conf.getIdProducerConfig( ) ) );
                item.setName( String.valueOf( conf.getName( ) ) );
                refListDocType.add( item );
            } );

            referenceListByDocType.put( docType.toString( ), refListDocType );
        }

        model.put( MARK_FORM, _form );
        model.put( MARK_MAP_DEFAULT_CONFIG_REF_LIST, referenceListByDocType );
        model.put( MARK_MAP_DEFAULT_CONFIG_DEFAULT_VALUE, defaultValues );

        return getPage( MESSAGE_PAGE_TITLE_MANAGE_ADVANCED_PARAM_CONFIG_PRODUCER, TEMPLATE_MANAGE_DEFAULT_CONFIG, model );
    }

    /**
     * Save default config
     * 
     * @param request
     *            request
     * @return confirm page
     */
    @Action( value = ACTION_SAVE_ADVANCED_PARAM_CONFIG_PRODUCER )
    public String doSaveAdvancedParameters( HttpServletRequest request ) throws AccessDeniedException
    {
        checkAuthorized( _form );

        // First remove all default config for the form
        _manageConfigProducerService.removeAllDefaultConfigByIdForm( getPlugin( ), _form.getId( ) );

        for ( DocumentType docType : DocumentType.values( ) )
        {
            String strIdConfigDefault = request.getParameter( docType.toString( ) );
            if ( strIdConfigDefault != null )
            {
                _manageConfigProducerService.createDefaultConfig( getPlugin( ), _form.getId( ), Integer.parseInt( strIdConfigDefault ), docType );
            }
        }

        addInfo( MESSAGE_SAVE_ADVANCED_PARAM_CONFIG_PRODUCER, request.getLocale( ) );

        return redirectView( request, VIEW_MANAGE_CONFIG_PRODUCER );
    }

    /**
     * Fill the list of entry types
     * 
     * @param strPropertyEntryTypes
     *            the property containing the entry types
     * @return a list of integer
     */
    private static List<Integer> fillListEntryTypes( String strPropertyEntryTypes )
    {
        List<Integer> listEntryTypes = new ArrayList<>( );
        String strEntryTypes = AppPropertiesService.getProperty( strPropertyEntryTypes );

        if ( StringUtils.isNotBlank( strEntryTypes ) )
        {
            String [ ] listAcceptEntryTypesForIdDemand = strEntryTypes.split( COMMA );

            for ( String strAcceptEntryType : listAcceptEntryTypesForIdDemand )
            {
                if ( StringUtils.isNotBlank( strAcceptEntryType ) && StringUtils.isNumeric( strAcceptEntryType ) )
                {
                    int nAcceptedEntryType = Integer.parseInt( strAcceptEntryType );
                    listEntryTypes.add( nAcceptedEntryType );
                }
            }
        }

        return listEntryTypes;
    }

    /**
     * Check if the user is authorized (has the permission 'MANAGE_DOCUMENTPRODUCER')
     * 
     * @param strIdResource
     *            the id resource
     * @return true if the user has the permission, false otherwise
     */
    private void checkAuthorized( Form form ) throws AccessDeniedException
    {
        if ( !StringUtils.isNotBlank( String.valueOf( form.getId( ) ) )
                || !RBACService.isAuthorized( Form.RESOURCE_TYPE, String.valueOf( form.getId( ) ),
                        FormsDocumentProducerResourceIdService.PERMISSION_MANAGE_DOCUMENTPRODUCER, getUser( ) ) )
        {
            throw new AccessDeniedException( "Unauthorized" );
        }
    }

    private List<Question> getTextAndNumQuestion( List<Question> listQuestion )
    {

        List<Integer> listAuthorizedIdEntryType = fillListEntryTypes( PROPERTY_ID_ENTRIES_TYPE_ALLOWED );
        List<Question> listAuthorizedQuestion = new ArrayList<>( );

        for ( Question question : listQuestion )
        {
            if ( listAuthorizedIdEntryType.contains( question.getEntry( ).getEntryType( ).getIdType( ) ) )
            {
                listAuthorizedQuestion.add( question );
            }
        }
        return listAuthorizedQuestion;

    }

    @Override
    protected void populate( Object bean, HttpServletRequest request )
    {
        super.populate( bean, request );
        if ( bean instanceof ConfigProducer )
        {
            if ( _form != null && _form.getId( ) > 0 )
            {
                ( (ConfigProducer) bean ).setIdForm( _form.getId( ) );
            }
        }
    }

}
