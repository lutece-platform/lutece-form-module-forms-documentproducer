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
package fr.paris.lutece.plugins.forms.modules.documentproducer.utils;

import fr.paris.lutece.plugins.forms.service.FormsPlugin;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import fr.paris.lutece.plugins.forms.business.Form;
import fr.paris.lutece.plugins.forms.business.FormHome;
import fr.paris.lutece.plugins.forms.business.FormQuestionResponse;
import fr.paris.lutece.plugins.forms.business.FormQuestionResponseHome;
import fr.paris.lutece.plugins.forms.business.FormResponse;
import fr.paris.lutece.plugins.forms.business.FormResponseHome;
import fr.paris.lutece.plugins.forms.business.FormResponseStep;
import fr.paris.lutece.plugins.forms.business.FormResponseStepHome;
import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.QuestionHome;
import fr.paris.lutece.plugins.forms.business.Step;
import fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig.IConfigProducer;
import fr.paris.lutece.plugins.genericattributes.business.Response;
import fr.paris.lutece.util.string.StringUtil;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

/**
 * class for generate PDF document from forms records
 */
public final class PDFUtils
{
	// PROPERTIES
    private static final String PROPERTY_POLICE_NAME = "forms.pdfgenerate.font.name";

    // PORPERTIES DATE
    private static final String PROPERTY_POLICE_FORMAT_DATE = "forms.pdfgenerate.format.date";
    private static final String PROPERTY_POLICE_SIZE_DATE = "forms.pdfgenerate.font.size.date";
    private static final String PROPERTY_POLICE_STYLE_DATE = "forms.pdfgenerate.font.style.date";
    private static final String PROPERTY_POLICE_ALIGN_DATE = "forms.pdfgenerate.font.align.date";

    // PROPERTIES TITLE FORM
    private static final String PROPERTY_POLICE_SIZE_TITLE_FORMS = "forms.pdfgenerate.font.size.title.forms";
    private static final String PROPERTY_POLICE_STYLE_TITLE_FORMS = "forms.pdfgenerate.font.style.title.forms";
    private static final String PROPERTY_POLICE_SPACING_BEFORE_TITLE_FORMS = "forms.pdfgenerate.font.spacing_before.title.forms";
    private static final String PROPERTY_POLICE_SPACING_AFTER_TITLE_FORMS = "forms.pdfgenerate.font.spacing_after.title.forms";

    // PROPERTIES STEPS
    private static final String PROPERTY_POLICE_SIZE_STEP = "forms.pdfgenerate.font.size.step";
    private static final String PROPERTY_POLICE_STYLE_STEP = "forms.pdfgenerate.font.style.step";
    private static final String PROPERTY_POLICE_MARGIN_LEFT_STEP = "forms.pdfgenerate.margin.left.step";
    private static final String PROPERTY_POLICE_SPACING_BEFORE_STEP = "forms.pdfgenerate.font.spacing_before.step";
    private static final String PROPERTY_POLICE_SPACING_AFTER_STEP = "forms.pdfgenerate.font.spacing_after.step";

    // PROPERTIES QUESTION
    private static final String PROPERTY_POLICE_SIZE_QUESTION = "forms.pdfgenerate.font.size.question";
    private static final String PROPERTY_POLICE_STYLE_QUESTION = "forms.pdfgenerate.font.style.question";
    private static final String PROPERTY_POLICE_MARGIN_LEFT_QUESTION = "forms.pdfgenerate.margin.left.question";
    private static final String PROPERTY_POLICE_SPACING_BEFORE_QUESTION = "forms.pdfgenerate.font.spacing_before.question";
    private static final String PROPERTY_POLICE_SPACING_AFTER_QUESTION = "forms.pdfgenerate.font.spacing_after.question";

    // PROPERTIES RESPONSE
    private static final String PROPERTY_POLICE_SIZE_RESPONSE = "forms.pdfgenerate.font.size.response";
    private static final String PROPERTY_POLICE_STYLE_RESPONSE = "forms.pdfgenerate.font.style.response";
    private static final String PROPERTY_POLICE_MARGIN_LEFT_RESPONSE = "forms.pdfgenerate.margin.left.response";
    private static final String PROPERTY_POLICE_SPACING_BEFORE_RESPONSE = "forms.pdfgenerate.font.spacing_before.response";
    private static final String PROPERTY_POLICE_SPACING_AFTER_RESPONSE = "forms.pdfgenerate.font.spacing_after.response";

    // PROPERTIES IMAGE
    private static final String PROPERTY_IMAGE_URL = "forms.pdfgenerate.image.url";
    private static final String PROPERTY_IMAGE_ALIGN = "forms.pdfgenerate.image.align";
    private static final String PROPERTY_IMAGE_FITWIDTH = "forms.pdfgenerate.image.fitWidth";
    private static final String PROPERTY_IMAGE_FITHEIGHT = "forms.pdfgenerate.image.fitHeight";
    
    // PROPERTIES ENTRYTYPE
    private static final String PROPERTY_ENTRYTYPE_GEOLOCALIZED_ID = "forms.pdfgenerate.entrytype.geolocalized.id";
	private static final String PROPERTY_ENTRYTYPE_FIELD_TITLE_ADRESS = "forms.pdfgenerate.entrytype.field.title.adress";

    // CONSTANTS
    private static final String DEFAULT_TYPE_FILE_NAME = "default";
    private static final String TEXT_TYPE_FILE_NAME = "text";
    private static final String FORM_QUESTION_TYPE_FILE_NAME = "form_question";

    /**
     * Constructor
     */
    private PDFUtils( )
    {
    }

    /**
     * method to create PDF without config
     * 
     * @param request
     *            request
     * @param strNameFile
     *            PDF name
     * @param out
     *            OutputStream
     * @param nIdRecord
     *            the id record
     * @param bExtractNotFilledField
     *            if true, extract empty fields, false otherwise
     */
    public static void doCreateDocumentPDF( HttpServletRequest request, String strNameFile, OutputStream out, int nIdRecord, Boolean bExtractNotFilledField )
    {
        List<Integer> listIdEntryConfig = new ArrayList<Integer>( );
        doCreateDocumentPDF( request, strNameFile, out, nIdRecord, listIdEntryConfig, bExtractNotFilledField );
    }

    /**
     * method to create PDF
     * 
     * @param request
     *            request
     * @param strNameFile
     *            PDF name
     * @param out
     *            OutputStream
     * @param nIdRecord
     *            the id record
     * @param listIdEntryConfig
     *            list of config id entry
     * @param bExtractNotFilledField
     *            if true, extract empty fields, false otherwise
     */
    public static void doCreateDocumentPDF( HttpServletRequest request, String strNameFile, OutputStream out, int nIdRecord, List<Integer> listIdEntryConfig,
            Boolean bExtractNotFilledField )
    {
        AdminUser adminUser = AdminUserService.getAdminUser( request );
        Locale locale = request.getLocale( );
        doCreateDocumentPDF( adminUser, locale, strNameFile, out, nIdRecord, listIdEntryConfig, bExtractNotFilledField );
    }

    /**
     * method to create PDF
     * 
     * @param adminUser
     *            The admin user
     * @param locale
     *            The locale
     * @param strNameFile
     *            PDF name
     * @param out
     *            OutputStream
     * @param nIdFormResponse
     *            the id of the form response
     * @param listIdEntryConfig
     *            list of config id entry
     * @param bExtractFilledField
     *            if true, extract only filled fields, false
     */
    public static void doCreateDocumentPDF( AdminUser adminUser, Locale locale, String strNameFile, OutputStream out, int nIdFormResponse,
            List<Integer> listIdEntryConfig, Boolean bExtractFilledField )
    {
        Document document = new Document( PageSize.A4 );

        Plugin plugin = PluginService.getPlugin( FormsPlugin.PLUGIN_NAME );

        FormResponse formResponse = FormResponseHome.findByPrimaryKey( nIdFormResponse );
        Form form = FormHome.findByPrimaryKey( formResponse.getFormId( ) );

        try
        {
            PdfWriter.getInstance( document, out );
        }
        catch( DocumentException e )
        {
            AppLogService.error( e );
        }

        document.open( );

        if ( formResponse.getCreation( ) != null )
        {
            SimpleDateFormat monthDayYearformatter = new SimpleDateFormat( AppPropertiesService.getProperty( PROPERTY_POLICE_FORMAT_DATE ) );

            Font fontDate = new Font( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_NAME, 0 ), AppPropertiesService.getPropertyInt(
                    PROPERTY_POLICE_SIZE_DATE, 0 ), AppPropertiesService.getPropertyInt( PROPERTY_POLICE_STYLE_DATE, 0 ) );

            Paragraph paragraphDate = new Paragraph( new Phrase( monthDayYearformatter.format( formResponse.getCreation( ) ), fontDate ) );

            paragraphDate.setAlignment( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_ALIGN_DATE, 0 ) );

            try
            {
                document.add( paragraphDate );
            }
            catch( DocumentException e )
            {
                AppLogService.error( e );
            }
        }

        Image image;

        try
        {

            image = Image.getInstance(
                    ImageIO.read( new File( AppPathService.getAbsolutePathFromRelativePath( AppPropertiesService.getProperty( PROPERTY_IMAGE_URL ) ) ) ), null );
            image.setAlignment( AppPropertiesService.getPropertyInt( PROPERTY_IMAGE_ALIGN, 0 ) );
            float fitWidth;
            float fitHeight;

            try
            {
                fitWidth = Float.parseFloat( AppPropertiesService.getProperty( PROPERTY_IMAGE_FITWIDTH ) );
                fitHeight = Float.parseFloat( AppPropertiesService.getProperty( PROPERTY_IMAGE_FITHEIGHT ) );
            }
            catch( NumberFormatException e )
            {
                fitWidth = 100f;
                fitHeight = 100f;
            }

            image.scaleToFit( fitWidth, fitHeight );

            try
            {
                document.add( image );
            }
            catch( DocumentException e )
            {
                AppLogService.error( e );
            }
        }
        catch( BadElementException | IOException e )
        {
            AppLogService.error( e );
        }

        Font fontTitle = new Font( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_NAME, 0 ), AppPropertiesService.getPropertyInt(
                PROPERTY_POLICE_SIZE_TITLE_FORMS, 0 ), AppPropertiesService.getPropertyInt( PROPERTY_POLICE_STYLE_TITLE_FORMS, 0 ) );

        fontTitle.isUnderlined( );

        Paragraph paragraphHeader = new Paragraph( new Phrase( form.getTitle( ), fontTitle ) );
        paragraphHeader.setAlignment( Element.ALIGN_CENTER );
        paragraphHeader.setSpacingBefore( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_SPACING_BEFORE_TITLE_FORMS, 0 ) );
        paragraphHeader.setSpacingAfter( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_SPACING_AFTER_TITLE_FORMS, 0 ) );

        addElementToDocument( document, paragraphHeader );

        builderPDFWithFormQuestionResponse( document, formResponse, listIdEntryConfig, bExtractFilledField );
        document.close( );
    }

    /**
     * method to builder PDF with forms entry
     * 
     * @param document
     *            document pdf
     * @param plugin
     *            plugin
     * @param nIdRecord
     *            id record
     * @param listEntry
     *            list of entry
     * @param listIdEntryConfig
     *            list of config id entry
     * @param locale
     *            the locale
     * @param bExtractNotFilledField
     *            if true, extract empty fields, false
     */
    private static void builderPDFWithFormQuestionResponse( Document document, FormResponse formResponse, List<Integer> listIdEntryConfig,
            boolean bExtractFilled )
    {
        List<FormResponseStep> listFormResponseStep = FormResponseStepHome.findStepsByFormResponse( formResponse.getId( ) );

        for ( FormResponseStep formResponseStep : listFormResponseStep )
        {
            Step step = formResponseStep.getStep( );
            List<FormQuestionResponse> listFormQuestionResponseOfStep = FormQuestionResponseHome.findQuestionsByStepAndFormResponse( formResponse.getId( ) , step.getId());
            
            if ( !listFormQuestionResponseOfStep.isEmpty()  )
            {
                boolean bPrintStep = false;
                
                for ( FormQuestionResponse formQuestionResponseOfStep : listFormQuestionResponseOfStep )
                {
                    // Print the question
                    Question questionOfStep = QuestionHome.findByPrimaryKey( formQuestionResponseOfStep.getQuestion( ).getId( ) );

                    if ( listIdEntryConfig.isEmpty( ) || listIdEntryConfig.contains( questionOfStep.getId( )) )
                    {
                        bPrintStep = true;
                    }
                }
                
                if ( bPrintStep )
                {
                    Font fontStepTitle = new Font( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_NAME, 0 ), AppPropertiesService.getPropertyInt(
                    PROPERTY_POLICE_SIZE_STEP, 0 ), Font.UNDERLINE );

                    Paragraph paragraphTitleStep = new Paragraph( new Phrase( step.getTitle( ), fontStepTitle ) );
                    paragraphTitleStep.setAlignment( Element.ALIGN_LEFT );
                    paragraphTitleStep.setIndentationLeft( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_MARGIN_LEFT_STEP, 0 ) );
                    paragraphTitleStep.setSpacingBefore( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_SPACING_BEFORE_STEP, 0 ) );
                    paragraphTitleStep.setSpacingAfter( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_SPACING_AFTER_STEP, 0 ) );

                    addElementToDocument( document, paragraphTitleStep );
                }
                
                for ( FormQuestionResponse formQuestionResponseOfStep : listFormQuestionResponseOfStep )
                {
                    // Print the question
                    Question questionOfStep = QuestionHome.findByPrimaryKey( formQuestionResponseOfStep.getQuestion( ).getId( ) );

                    if ( listIdEntryConfig.isEmpty( ) || listIdEntryConfig.contains( questionOfStep.getId( )) )
                    {
                        Font fontQuestionTitle = new Font( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_NAME, 0 ), AppPropertiesService.getPropertyInt(
                                PROPERTY_POLICE_SIZE_QUESTION, 0 ), AppPropertiesService.getPropertyInt( PROPERTY_POLICE_STYLE_QUESTION, 0 ) );

                        Paragraph paragraphTitleQuestion = new Paragraph( new Phrase( questionOfStep.getTitle( ), fontQuestionTitle ) );
                        paragraphTitleQuestion.setAlignment( Element.ALIGN_LEFT );
                        paragraphTitleQuestion.setIndentationLeft( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_MARGIN_LEFT_QUESTION, 0 ) );
                        paragraphTitleQuestion.setSpacingBefore( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_SPACING_BEFORE_QUESTION, 0 ) );
                        paragraphTitleQuestion.setSpacingAfter( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_SPACING_AFTER_QUESTION, 0 ) );

                        addElementToDocument( document, paragraphTitleQuestion );

                        // Print the responses

                        Font fontResponse = new Font( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_NAME, 0 ), AppPropertiesService.getPropertyInt(
                                PROPERTY_POLICE_SIZE_RESPONSE, 0 ), AppPropertiesService.getPropertyInt( PROPERTY_POLICE_STYLE_RESPONSE, 0 ) );

                        if ( formQuestionResponseOfStep.getEntryResponse( ).size( ) == 1 )
                        {
                            // One element, build a paragraph
                            Paragraph paragraphResponse = new Paragraph( new Phrase( formQuestionResponseOfStep.getEntryResponse( ).get( 0 )
                                    .getToStringValueResponse( ), fontResponse ) );
                            paragraphResponse.setAlignment( Element.ALIGN_LEFT );
                            paragraphResponse.setIndentationLeft( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_MARGIN_LEFT_RESPONSE, 0 ) );
                            paragraphResponse.setSpacingBefore( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_SPACING_BEFORE_RESPONSE, 0 ) );
                            paragraphResponse.setSpacingAfter( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_SPACING_AFTER_RESPONSE, 0 ) );

                            addElementToDocument( document, paragraphResponse );
                        }
                        else
                        {
                            com.lowagie.text.List listValue = new com.lowagie.text.List( true );
                            listValue.setPreSymbol( "- " );
                            listValue.setNumbered( false );
                            listValue.setIndentationLeft( AppPropertiesService.getPropertyInt( PROPERTY_POLICE_MARGIN_LEFT_RESPONSE, 0 ) );

                            // If many elements, build a list
                            for ( Response response : formQuestionResponseOfStep.getEntryResponse( ) )
                            {
                                if ( response.getEntry( ).getEntryType( ).getIdType( ) == AppPropertiesService.getPropertyInt( PROPERTY_ENTRYTYPE_GEOLOCALIZED_ID, 0 ) ) 
                                {
                                    if ( AppPropertiesService.getProperty( PROPERTY_ENTRYTYPE_FIELD_TITLE_ADRESS ).equals( response.getField().getTitle( ) ) ) 
                                    {
                                        listValue.add( new ListItem( response.getToStringValueResponse( ), fontResponse ) );
                                        break;
                                    }
                                } else
                                {
                                    listValue.add( new ListItem( response.getToStringValueResponse( ), fontResponse ) );
                                }
                            }

                            addElementToDocument( document, listValue );
                        }
                    }

                }
            }
        }
    }

    /**
     * Method to download a PDF file generated by forms record
     * 
     * @param request
     *            request
     * @param response
     *            response
     * @param plugin
     *            plugin
     * @param configProducer
     *            configuration
     * @param listIdQuestionConfig
     * @param locale
     *            locale
     */
    public static void doDownloadPDF( HttpServletRequest request, HttpServletResponse response, Plugin plugin, IConfigProducer configProducer,
            List<Integer> listIdQuestionConfig, Locale locale, int nIdFormResponse )
    {
        FormResponse formResponse = FormResponseHome.findByPrimaryKey( nIdFormResponse );
        Form forms = FormHome.findByPrimaryKey( formResponse.getFormId( ) );

        String strName = getFileNameFromConfig( forms, configProducer, nIdFormResponse, locale );
        String strFileName = doPurgeNameFile( strName ) + ".pdf";

        response.setHeader( "Content-Disposition", "attachment ;filename=\"" + strFileName + "\"" );
        response.setHeader( "Pragma", "public" );
        response.setHeader( "Expires", "0" );
        response.setHeader( "Cache-Control", "must-revalidate,post-check=0,pre-check=0" );

        response.setContentType( "application/pdf" );

        OutputStream os = null;

        try
        {
            os = response.getOutputStream( );

            if ( listIdQuestionConfig != null )
            {
                doCreateDocumentPDF( request, strName, os, nIdFormResponse, listIdQuestionConfig, configProducer.getExtractFilled( ) );
            }
            else
            {
                doCreateDocumentPDF( request, strName, os, nIdFormResponse, configProducer.getExtractFilled( ) );
            }
        }
        catch( IOException e )
        {
            AppLogService.error( e );
        }
        finally
        {
            IOUtils.closeQuietly( os );
        }
    }

    public static String getFileNameFromConfig( Form form, IConfigProducer configProducer, int nIdRecord, Locale locale )
    {
        String strTypeConfigFileName = configProducer.getTypeConfigFileName( );
        if ( strTypeConfigFileName.equals( DEFAULT_TYPE_FILE_NAME ) )
        {
            return StringUtil.replaceAccent( form.getTitle( ) ).replace( " ", "_" ) + "_" + nIdRecord;
        }
        if ( strTypeConfigFileName.equals( TEXT_TYPE_FILE_NAME ) )
        {
            return StringUtil.replaceAccent( configProducer.getTextFileName( ) + "_" + nIdRecord );
        }
        if ( strTypeConfigFileName.equals( FORM_QUESTION_TYPE_FILE_NAME ) )
        {

            FormQuestionResponse formQuestionResponse = FormQuestionResponseHome.findFormQuestionResponseByResponseQuestion( nIdRecord, configProducer.getIdQuestionFileName( ) ).get( 0 );
            
            if( formQuestionResponse != null )
            {
                return formQuestionResponse.getEntryResponse().get( 0 ).getResponseValue();
            }
        }
        return null;
    }

    /**
     * Method to purge file name, (replace accent and punctuation)
     * 
     * @param strNameFile
     *            name file
     * @return purged name file
     */
    public static String doPurgeNameFile( String strNameFile )
    {
        return StringUtil.replaceAccent( strNameFile ).replaceAll( "\\W", "_" );
    }

    private static void addElementToDocument( Document document, Element element )
    {
        try
        {
            document.add( element );
        }
        catch( DocumentException e )
        {
            AppLogService.error( e );
        }
    }
}
