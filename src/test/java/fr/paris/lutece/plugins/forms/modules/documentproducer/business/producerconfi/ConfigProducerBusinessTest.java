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
package fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfi;

import java.util.Collections;
import java.util.List;

import fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig.ConfigProducer;
import fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig.ConfigProducerHome;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.test.LuteceTestCase;

public class ConfigProducerBusinessTest extends LuteceTestCase
{

    private Plugin _plugin;

    @Override
    protected void setUp( ) throws Exception
    {
        super.setUp( );
        _plugin = PluginService.getPlugin( "forms-documentproducer" );
    }

    public void testCRUD( )
    {
        ConfigProducer producer = new ConfigProducer( );
        producer.setIdForm( 1 );
        producer.setIdQuestionFileName( 2 );
        producer.setName( "name" );
        producer.setTextFileName( "text" );
        producer.setType( "type" );
        producer.setTypeConfigFileName( "config" );
        producer.setExtractFilled( false );

        ConfigProducerHome.addNewConfig( _plugin, producer, Collections.singletonList( 3 ) );

        ConfigProducer loaded = ConfigProducerHome.loadConfig( _plugin, producer.getIdProducerConfig( ) );
        assertEquals( producer.getIdForm( ), loaded.getIdForm( ) );
        assertEquals( producer.getIdQuestionFileName( ), loaded.getIdQuestionFileName( ) );
        assertEquals( producer.getName( ), loaded.getName( ) );
        assertEquals( producer.getTextFileName( ), loaded.getTextFileName( ) );
        assertEquals( producer.getType( ), loaded.getType( ) );
        assertEquals( producer.getTypeConfigFileName( ), loaded.getTypeConfigFileName( ) );
        assertEquals( producer.getExtractFilled( ), loaded.getExtractFilled( ) );

        List<Integer> list = ConfigProducerHome.loadListConfigQuestion( _plugin, producer.getIdProducerConfig( ) );
        assertEquals( 1, list.size( ) );
        assertTrue( list.contains( 3 ) );

        producer.setIdQuestionFileName( 5 );
        producer.setName( "name2" );
        producer.setTextFileName( "text2" );
        producer.setType( "type2" );
        producer.setTypeConfigFileName( "config2" );
        producer.setExtractFilled( true );

        ConfigProducerHome.modifyProducerConfig( _plugin, producer, Collections.singletonList( 6 ) );

        loaded = ConfigProducerHome.loadConfig( _plugin, producer.getIdProducerConfig( ) );
        assertEquals( producer.getIdQuestionFileName( ), loaded.getIdQuestionFileName( ) );
        assertEquals( producer.getName( ), loaded.getName( ) );
        assertEquals( producer.getTextFileName( ), loaded.getTextFileName( ) );
        assertEquals( producer.getType( ), loaded.getType( ) );
        assertEquals( producer.getTypeConfigFileName( ), loaded.getTypeConfigFileName( ) );
        assertEquals( producer.getExtractFilled( ), loaded.getExtractFilled( ) );

        list = ConfigProducerHome.loadListConfigQuestion( _plugin, producer.getIdProducerConfig( ) );
        assertEquals( 1, list.size( ) );
        assertTrue( list.contains( 6 ) );

        ConfigProducerHome.deleteProducerConfig( _plugin, producer.getIdProducerConfig( ) );

        loaded = ConfigProducerHome.loadConfig( _plugin, producer.getIdProducerConfig( ) );
        assertEquals( 0, loaded.getIdForm( ) );
        list = ConfigProducerHome.loadListConfigQuestion( _plugin, producer.getIdProducerConfig( ) );
        assertEquals( 0, list.size( ) );
    }
}
