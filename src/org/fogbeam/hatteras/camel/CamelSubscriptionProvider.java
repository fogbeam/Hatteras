package org.fogbeam.hatteras.camel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
		
				
		Multimap<String, String> mapper = HashMultimap.create();
		for( EventSubscription sub : extSubscriptions )
		{
			mapper.put( sub.getxQueryExpression(), sub.getOwner().getUserId() );
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
			List<String> subOwners = cs.getSubscribers();
			for(String subOwner : subOwners )
			{
				System.out.println( subOwner );
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
		
		CamelSubscription sub1 = new CamelSubscription();
		sub1.setXQueryExpression( "/oagis:RFQ[@refType='abc']" );
		sub1.addSubscriber( "prhodes" );
		sub1.addSubscriber( "mtwain" );
		dummyData.add(sub1);
		
		CamelSubscription sub2 = new CamelSubscription();
		sub2.setXQueryExpression( "/oagis:RFQ[@refType='def']" );
		sub2.addSubscriber( "dawnlove" );
		sub2.addSubscriber( "tinamn" );
		sub2.addSubscriber( "pinamn" );
		dummyData.add(sub2);
		
		CamelSubscription sub3 = new CamelSubscription();
		sub3.setXQueryExpression( "/oagis:RFQ[@refType='ghi']" );
		sub3.addSubscriber( "mstanley" );
		sub3.addSubscriber( "dlemos" );
		dummyData.add(sub3);		
		
		return dummyData;
	}
	
}