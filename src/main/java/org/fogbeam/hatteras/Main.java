package org.fogbeam.hatteras;

import java.util.List;

import org.apache.camel.CamelContext;
import org.fogbeam.hatteras.subscription.SubscriptionService;
import org.fogbeam.quoddy.EventSubscription;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main implements Runnable
{
	private ApplicationContext appContext;
	
	@SuppressWarnings("unused")
	public static void main( String[] args ) 
	{
		System.out.println( "Starting up..." );
		
		Main main = new Main();
		// Thread mainThread = new Thread(main);
		main.init();
		// mainThread.start();
		
		try 
		{
			Thread.sleep( 15000 );
			
			main.startCamel();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		System.out.println( "Hatteras running..." );
		
	}
	
	public void init() 
	{
		this.appContext = new ClassPathXmlApplicationContext( "applicationContext.xml" );
	}
	
	private void startCamel() throws Exception {
		
		CamelContext context = (CamelContext)appContext.getBean( "camelContext" );
		
		context.start();
	}
	
	public void run() 
	{
		while( true )
		{
			try
			{
				Thread.sleep( 60000 );
				
				// get the list of Subscriptions from the Quoddy server
				SubscriptionService subService = appContext.getBean( "subscriptionService", SubscriptionService.class );
				
				List<EventSubscription> subscriptions = subService.getExternalSubscriptions();
				
				for( EventSubscription sub : subscriptions )
				{
					System.out.println( sub );
				}
				
				System.out.println( "----------------------------------" );
				
			}
			catch( InterruptedException e )
			{
				e.printStackTrace();
			}
			
			System.out.println( "..." );
		}
	}
	
}
