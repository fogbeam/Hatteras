package org.fogbeam.hatteras;

public class Main implements Runnable
{
	// 
	
	public static void main( String[] args )
	{
		System.out.println( "Starting up..." );
		
		Main main = new Main();
		Thread mainThread = new Thread(main);
		
		mainThread.start();
		
		System.out.println( "Hatteras running..." );
		
	}
	
	public void init() 
	{
		
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
