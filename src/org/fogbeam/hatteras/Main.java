package org.fogbeam.hatteras;

import java.util.Iterator;
import java.util.List;

import org.fogbeam.hatteras.subscription.SubscriptionService;
import org.fogbeam.quoddy.EventSubscription;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main implements Runnable
{
	private ApplicationContext appContext;
	
	public static void main( String[] args )
	{
		System.out.println( "Starting up..." );
		
		Main main = new Main();
		Thread mainThread = new Thread(main);
		main.init();
		mainThread.start();
		
		System.out.println( "Hatteras running..." );
		
	}
	
	public void init() 
	{
		this.appContext = new ClassPathXmlApplicationContext( "applicationContext.xml" );
	}
	
	public void run() 
	{
		while( true )
		{
			try
			{
				Thread.sleep( 15000 );
				
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
				
			}
			
			System.out.println( "..." );
		}
	}
	
}
