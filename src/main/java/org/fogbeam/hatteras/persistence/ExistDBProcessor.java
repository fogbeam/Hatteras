package org.fogbeam.hatteras.persistence;

import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.exist.storage.DBBroker;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

public class ExistDBProcessor implements Processor
{
	
	public final static String URI = "xmldb:exist://localhost:8090/exist/xmlrpc";
	
	@Override
	public void process( Exchange exchange ) throws Exception
	{
		System.out.println( "existDBProcessor got message: " + exchange.toString());

		String msgXML = exchange.getIn().getBody( String.class );		

		System.out.println( "payload:\n" + msgXML );

		String collection = "/db/hatteras";
		System.out.println( "Collection: " + collection );
		
		// initialize driver
		String driver = "org.exist.xmldb.DatabaseImpl";
		Class cl = Class.forName(driver);
		Database database = (Database)cl.newInstance();
		database.setProperty("create-database", "true");
		DatabaseManager.registerDatabase(database);
		
		// try to get collection
		String colURI = URI + collection;
		System.out.println( "looking for collection at: " + colURI );
		Collection col = DatabaseManager.getCollection( colURI );
		if(col == null) {
			System.out.println( "Collection \"" + colURI + "\" not found" );
			
			// collection does not exist: get root collection and create.
			// for simplicity, we assume that the new collection is a
			// direct child of the root collection, e.g. /db/test.
			// the example will fail otherwise.
			try
			{
				Collection root = DatabaseManager.getCollection(URI + DBBroker.ROOT_COLLECTION);
				if( root == null ) 
				{
					System.err.println( "Could not retrieve ROOT collection!!!" );
					return;
				}
				CollectionManagementService mgtService = (CollectionManagementService)root.getService("CollectionManagementService", "1.0");
				String foo = collection.substring((DBBroker.ROOT_COLLECTION + "/").length());
				System.out.println( "Foo: " + foo );
				col = mgtService.createCollection( foo );
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}
		else {
			System.out.println( "Found collection \"" + collection + "\"." );
		}
		
		// create new XMLResource
		String uuid = UUID.randomUUID().toString();
		XMLResource document = (XMLResource)col.createResource(uuid, "XMLResource");
		document.setContent(msgXML);
		System.out.print("storing document " + document.getId() + "...");
		try
		{
			col.storeResource(document);
			exchange.setProperty( "xmlUuid", uuid );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
		System.out.println("ok.");		
	
	}
}
