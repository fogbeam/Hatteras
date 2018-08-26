package org.fogbeam.hatteras.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CamelDownloadLogger implements Processor 
{

	@Override
	public void process( Exchange exchange )
			throws Exception 
	{
	        System.out.println( "Exchange: " + exchange.toString());
		System.out.println( "Body:\n" + exchange.getIn().getBody());
	}
}
