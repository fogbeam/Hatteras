package org.fogbeam.hatteras.summary;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Scanner;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQStaticContext;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import ch.ethz.mxquery.xqj.MXQueryXQDataSource;

public class SummaryProcessor implements Processor
{
	@Override
	public void process( Exchange exchange ) throws Exception
	{
		/* generates a summary based on the contents of the event */
		String msgXML = exchange.getIn().getBody( String.class );
		
		
	}
	
	
	public static void main( String[] args ) throws Exception
	{
		
		String body = readFile( "../camel-jms/test3.xml" );
		System.out.println( "evaluating....\n" + body );
		
		XQDataSource ds = new MXQueryXQDataSource();
		XQConnection conn = ds.getConnection();
		XMLInputFactory factory = XMLInputFactory.newInstance();
		Reader reader = new StringReader( body );
		XMLStreamReader streamReader = factory.createXMLStreamReader(reader);
		javax.xml.namespace.QName rns = new javax.xml.namespace.QName("ext");
		

		XQStaticContext cntxt = conn.getStaticContext();
		cntxt.declareNamespace( "oagis", "http://www.openapplications.org/oagis/9" );
		// change the implementation defaults
		conn.setStaticContext(cntxt);
		
		String dec = "declare variable $ext external; ";
		
		
		
		
		System.out.println( "done" );
	}
	
	
	
	private static String readFile(String pathname) throws IOException {

	    File file = new File(pathname);
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner = new Scanner(file);
	    String lineSeparator = System.getProperty("line.separator");

	    try {
	        while(scanner.hasNextLine()) {        
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	        return fileContents.toString();
	    } finally {
	        scanner.close();
	    }
	}	
	
}
