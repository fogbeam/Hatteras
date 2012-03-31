package org.fogbeam.hatteras;

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
				Thread.sleep( 30000 );
			}
			catch( InterruptedException e )
			{
				
			}
			
			System.out.println( "..." );
		}
	}
	
}
