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
