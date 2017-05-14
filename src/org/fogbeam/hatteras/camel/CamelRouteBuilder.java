package org.fogbeam.hatteras.camel;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class CamelRouteBuilder extends RouteBuilder 
{
	private Processor 					downloadLogger;
	private Processor					existDBProcessor;
	private CamelDynamicPredicate     subscriptionPredicate;
	
	
	public void configure() throws Exception 
	{	
		
		from( "jms:queue:foobar" )
			.process( this.downloadLogger )
			.process( this.existDBProcessor )
			.choice()
			.when( this.subscriptionPredicate )
			.bean( CamelRecipientList.class );
	}	

	public void setDownloadLogger( Processor downloadLogger )
	{
		this.downloadLogger = downloadLogger;
	}
	
	public void setSubscriptionPredicate( CamelDynamicPredicate subscriptionPredicate )
	{
		this.subscriptionPredicate = subscriptionPredicate;
	}
	
	public void setExistDBProcessor( Processor existDBProcessor )
	{
		this.existDBProcessor = existDBProcessor;
	}
}
