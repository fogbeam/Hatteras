package org.fogbeam.hatteras.camel;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class CamelRouteBuilder extends RouteBuilder 
{
	private Processor 					downloadLogger;
	private CamelDynamicPredicate     subscriptionPredicate;
	
	
	public void configure() throws Exception 
	{	
		//   = new Camel12DynamicPredicate(); 
		
		from(
				"ftp://fogbeam.org/rfqs?username=prhodes&password=@dilbert&delete=false&disconnect=true&consumer.delay=25000"
		)
		.process(downloadLogger)
		.choice()
		.when( this.subscriptionPredicate )
		//.to( "file:data/camel13" );
		.bean(CamelRecipientList.class);
	}	

	public void setDownloadLogger( Processor downloadLogger )
	{
		this.downloadLogger = downloadLogger;
	}
	
	public void setSubscriptionPredicate( CamelDynamicPredicate subscriptionPredicate )
	{
		this.subscriptionPredicate = subscriptionPredicate;
	}
}
