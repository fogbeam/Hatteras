package org.fogbeam.hatteras.camel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.fogbeam.hatteras.subscription.Subscriber;
import org.fogbeam.hatteras.subscription.SubscriptionService;
import org.fogbeam.quoddy.EventSubscription;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class CamelSubscriptionProvider 
{
	private final List<CamelSubscription> subscriptions = new ArrayList<CamelSubscription>();
	private SubscriptionService subscriptionService;
	
	
	public CamelSubscriptionProvider()
	{	
	}
	
	/* we'll set this up to be called by a Quartz scheduled job */
	public void updateSubscriptionList()
	{
		System.out.println( "Updating Subscription List" );
		
		// get all the external subscriptions
		
		List<EventSubscription> extSubscriptions = subscriptionService.getExternalSubscriptions();
		List<CamelSubscription> c = new ArrayList<CamelSubscription>();
		
				
		Multimap<String, Subscriber> mapper = HashMultimap.create();
		for( EventSubscription sub : extSubscriptions )
		{
			Subscriber subscriber = new Subscriber();
			subscriber.setSubscriberUserId( sub.getOwner().getUserId() );
			subscriber.setSubscriberUuid( sub.getOwner().getUuid() );
			subscriber.setSubscriptionUuid( sub.getUuid() );
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
		
		// in a synchronized block
		// replace the contents of the existing list with our new list
		synchronized( subscriptions )
		{
			subscriptions.clear();
			subscriptions.addAll( c );
		}
		
		System.out.println( "Done updating subscription list!" );
		for( CamelSubscription cs : subscriptions )
		{
			System.out.println( "\n" + cs.getXQueryExpression() + ":" );
			List<Subscriber> subOwners = cs.getSubscribers();
			for(Subscriber subscriber : subOwners )
			{
				System.out.println( subscriber.getSubscriberUserId() 
									+ " : " 
									+ subscriber.getSubscriberUuid() );
			}
			System.out.println( "-------------" );
		}
		
	}
	
	public List<CamelSubscription> getSubscriptions()
	{
		synchronized( subscriptions )
		{
			return subscriptions;
		}
	}
	
	public SubscriptionService getSubscriptionService()
	{
		return subscriptionService;
	}
	
	public void setSubscriptionService( SubscriptionService subscriptionService )
	{
		this.subscriptionService = subscriptionService;
	}
	
	@SuppressWarnings("unused")
	private List<CamelSubscription> getDummyData()
	{
		List<CamelSubscription> dummyData = new ArrayList<CamelSubscription>();
		// load some dummy data into the subscriptions list...
		// /oagis:RFQ[@refType='abc']
		
		Subscriber prhodes = new Subscriber( "prhodes", "ABC123", "DEF921" );
		Subscriber mtwain = new Subscriber( "mtwain", "ABC124", "DEF821" );
		Subscriber dawnlove = new Subscriber( "dawnlove", "ABC125", "DEF721" );
		Subscriber tinman = new Subscriber( "tinman", "ABC126", "DEF621" );
		Subscriber pinman = new Subscriber( "pinman", "ABC127", "DEF521" );
		Subscriber mstanley = new Subscriber( "mstanley", "ABC128", "DEF421" );
		Subscriber dlemos = new Subscriber( "dlemos", "ABC129", "DEF321" );
		
		CamelSubscription sub1 = new CamelSubscription();
		sub1.setXQueryExpression( "/oagis:RFQ[@refType='abc']" );
		sub1.addSubscriber( prhodes );
		sub1.addSubscriber( mtwain );
		dummyData.add(sub1);
		
		CamelSubscription sub2 = new CamelSubscription();
		sub2.setXQueryExpression( "/oagis:RFQ[@refType='def']" );
		sub2.addSubscriber( dawnlove );
		sub2.addSubscriber( tinman );
		sub2.addSubscriber( pinman );
		dummyData.add(sub2);
		
		CamelSubscription sub3 = new CamelSubscription();
		sub3.setXQueryExpression( "/oagis:RFQ[@refType='ghi']" );
		sub3.addSubscriber( mstanley );
		sub3.addSubscriber( dlemos );
		dummyData.add(sub3);		
		
		return dummyData;
	}
}