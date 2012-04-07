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
		
		for( String subscriber : subscribers )
		{
			recipients.add( "file:data/userQueues/" + subscriber );
		}
		
		return recipients;
	}
}
