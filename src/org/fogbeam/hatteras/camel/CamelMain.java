package org.fogbeam.hatteras.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelMain 
{
	public static void main(String[] args) throws Exception
	{
		ApplicationContext appContext = new ClassPathXmlApplicationContext( "camel13ApplicationContext.xml" );
		
		CamelContext context = (CamelContext)appContext.getBean( "camelContext" );
		
		context.start();
		
		Thread.sleep(25000);		
		
		context.stop();
		
		System.out.println( "done" );
	}

}
