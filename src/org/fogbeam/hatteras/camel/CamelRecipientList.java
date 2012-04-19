package org.fogbeam.hatteras.camel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.RecipientList;

public class CamelRecipientList
{
	@RecipientList
	public List<String> routeTo( String body, Exchange exchange )
	{
		// System.out.println( "body: " + body );
		
		ArrayList<String> recipients = new ArrayList<String>();
		// recipients.add("file:data/userQueues/userA");
		Map headers = exchange.getIn().getHeaders();
		Set<Map.Entry> entries = headers.entrySet();
		for( Map.Entry entry : entries )
		{
			System.out.println( "header: " + entry.getKey() + " : " + entry.getValue() );
		}
		
		Map<String,Object> props = exchange.getProperties();
		for( String key : props.keySet() )
		{
			System.out.println( "property: " + key + " : " + props.get(key));
		}
		
		
		List<String> subscribers = (List<String>)exchange.getProperty("subscribers");
		
		// note: this bit will be more for a load balancing sort of thing...
		// we'd do something like, for example, send a JMS message to some subset of
		// 26 different queues, where the queue corresponds to the first letter of the
		// subscriber id or something.   Then the processing that reads the message off
		// of the queue will be responsible for delivering it to each of the subscribers
		// that are mentioned in the header for that message (based on who is logged in)
		
		// for( String subscriber : subscribers )
		// {
			// recipients.add( "file:data/userQueues/" + subscriber );
			// recipients.add( "jms:queue:foobar2");
		// }
		
		try
		{
			StringBuilder builder = new StringBuilder();
			for( String subscriber : subscribers )
			{
				builder.append( subscriber + " " );
			}
		
			String strSubHeader = builder.toString().trim();		
			exchange.getIn().setHeader( "subscribers", strSubHeader );

			String xmlUuid = exchange.getProperty( "xmlUuid" ).toString();
			System.out.println( "got xmlUuid as \"" + xmlUuid + "\" from Exchange property");
			exchange.getIn().setHeader( "eventUuid", xmlUuid );
		
			String matchedExpression = exchange.getProperty( "matchedExpression" ).toString();
			System.out.println( "got matchedExpression as \"" + matchedExpression + "\" from Exchange property");
			exchange.getIn().setHeader(  "matchedExpression", matchedExpression );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
		// recipients.add( "jms:queue:foobar2");
		recipients.add( "jms:queue:eventSubscriptionInQueue" );
		return recipients;
	}
}
