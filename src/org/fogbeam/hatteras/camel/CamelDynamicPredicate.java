package org.fogbeam.hatteras.camel;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;
import javax.xml.xquery.XQSequence;
import javax.xml.xquery.XQStaticContext;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

import ch.ethz.mxquery.xqj.MXQueryXQDataSource;

public class CamelDynamicPredicate implements Predicate 
{
	private CamelSubscriptionProvider subscriptionProvider;
	
	@Override
	public boolean matches(Exchange exchange)
	{
		try
		{
			String body = exchange.getIn().getBody(String.class);
			System.out.println( "evaluating...." + body );
			
			XQDataSource ds = new MXQueryXQDataSource();
			XQConnection conn = ds.getConnection();
			XMLInputFactory factory = XMLInputFactory.newInstance();
			Reader reader = new StringReader( body );
			XMLStreamReader streamReader = factory.createXMLStreamReader(reader);
			javax.xml.namespace.QName rns = new javax.xml.namespace.QName("ext");
			
			// get all of our subscriptions, iterate over them, and anywhere we find a match
			// add the associated user to the routing slip header
			List<CamelSubscription> subs = subscriptionProvider.getSubscriptions();

			XQStaticContext cntxt = conn.getStaticContext();
			cntxt.declareNamespace( "oagis", "http://www.openapplications.org/oagis/9" );
			// change the implementation defaults
			conn.setStaticContext(cntxt);
			
			String dec = "declare variable $ext external; ";
			boolean first = true;
			
			List<String> allSubscribers = new ArrayList<String>();
			
			for( CamelSubscription sub : subs )
			{
				String query;
				if( first )
				{
					query = dec + "$ext";
				}
				else
				{
					query = "$ext";
				}
				first = false;
				
				query = query + sub.getXQueryExpression();
				System.out.println( "processing query: " + query );

				XQPreparedExpression exp = conn.prepareExpression(query,cntxt);
				exp.bindDocument( rns, streamReader, null);					
				
				// does it match?
				XQResultSequence result = exp.executeQuery();
				XQSequence sequence = conn.createSequence(result);
				if( sequence.isBeforeFirst() )
				{
					// it matches
					System.out.println( "MATCH FOUND" );
					allSubscribers.addAll(sub.getSubscribers());
				
				}
				else
				{
					// no match
					System.out.println( "NO MATCH" );
				}
				
				result.close();
			}
			
			exchange.setProperty( "subscribers", allSubscribers );
			exchange.getOut().setHeader("subscribers", allSubscribers );
			
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		
		return true;
	}
	
	public void setSubscriptionProvider( CamelSubscriptionProvider subscriptionProvider )
	{
		this.subscriptionProvider = subscriptionProvider;
	}
	
}
