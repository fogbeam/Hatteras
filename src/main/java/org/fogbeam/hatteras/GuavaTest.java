package org.fogbeam.hatteras;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.fogbeam.hatteras.camel.CamelSubscription;
import org.fogbeam.hatteras.subscription.Subscriber;
import org.fogbeam.quoddy.EventSubscription;
import org.fogbeam.quoddy.User;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class GuavaTest
{
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{

		List<EventSubscription> extSubscriptions = new ArrayList<EventSubscription>();
		
		User u1 = new User();
		u1.setUserId( "testuser1" );
		u1.setUuid( "ABC123" );
		
		User u2 = new User();
		u2.setUserId( "testuser2" );
		u2.setUuid( "ABC124" );
		
		User u3 = new User();
		u3.setUserId( "testuser3" );
		u3.setUuid( "ABC125" );
		
		User u4 = new User();
		u4.setUserId( "testuser4" );
		u4.setUuid( "ABC126" );
		
		EventSubscription e1 = new EventSubscription();
		e1.setxQueryExpression( "xQuery1" );
		e1.setOwner( u1 );
		
		EventSubscription e2 = new EventSubscription();
		e2.setxQueryExpression( "xQuery2" );
		e2.setOwner( u2 );
		
		EventSubscription e3 = new EventSubscription();
		e3.setxQueryExpression( "xQuery3" );
		e3.setOwner( u3 );
		
		EventSubscription e4 = new EventSubscription();
		e4.setxQueryExpression( "xQuery4" );
		e4.setOwner( u4 );
		
		EventSubscription e5 = new EventSubscription();
		e5.setxQueryExpression( "xQuery1" );
		e5.setOwner( u2 );
		
		EventSubscription e6 = new EventSubscription();
		e6.setxQueryExpression( "xQuery1" );
		e6.setOwner( u2 );
		
		EventSubscription e7 = new EventSubscription();
		e7.setxQueryExpression( "xQuery2" );
		e7.setOwner( u3 );
		
		EventSubscription e8 = new EventSubscription();
		e8.setxQueryExpression( "xQuery5" );
		e8.setOwner( u1 );
		
		EventSubscription e9 = new EventSubscription();
		e9.setxQueryExpression( "xQuery5" );
		e9.setOwner( u2 );
		
		EventSubscription e10 = new EventSubscription();
		e10.setxQueryExpression( "xQuery5" );
		e10.setOwner( u3 );
		
		EventSubscription e11 = new EventSubscription();
		e11.setxQueryExpression( "xQuery5" );
		e11.setOwner( u4 );
		
		EventSubscription e12 = new EventSubscription();
		e12.setxQueryExpression( "xQuery6" );
		e12.setOwner( u4 );
		
		extSubscriptions.add( e1 );
		extSubscriptions.add( e2 );
		extSubscriptions.add( e3 );
		extSubscriptions.add( e4 );
		extSubscriptions.add( e5 );
		extSubscriptions.add( e6 );
		extSubscriptions.add( e7 );
		extSubscriptions.add( e8 );
		extSubscriptions.add( e9 );
		extSubscriptions.add( e10 );
		extSubscriptions.add( e12 );
		extSubscriptions.add( e11 );
		
		
		List<CamelSubscription> c = new ArrayList<CamelSubscription>();
		
				
		Multimap<String, Subscriber> mapper = HashMultimap.create();
		for( EventSubscription sub : extSubscriptions )
		{
			Subscriber subscriber = new Subscriber(sub.getOwner().getUserId(),
												   sub.getOwner().getUuid(),
												   sub.getUuid() );
			
			mapper.put( sub.getxQueryExpression(), subscriber );
		}
		
		// build up the new list
		Set<String> xQueryExpressions = mapper.keySet();
		for( String xQuery : xQueryExpressions )
		{
			CamelSubscription newSub = new CamelSubscription();
			newSub.setXQueryExpression( xQuery );
			newSub.addAllSubscribers( mapper.get( xQuery ) );
			c.add( newSub );
		}
		
		for( CamelSubscription aSub : c )
		{
			System.out.println( aSub.getXQueryExpression() + ":" );
			List<Subscriber> subscribers = aSub.getSubscribers();
			for( Subscriber subscriber : subscribers ) 
			{
				System.out.println( subscriber.getSubscriberUserId() + " " + subscriber.getSubscriberUuid() );
			}

			System.out.println( "-----------\n" );
		}
		
	}
}
