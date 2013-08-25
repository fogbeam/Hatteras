package org.fogbeam.hatteras.subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fogbeam.quoddy.EventSubscription;
import org.fogbeam.quoddy.jaxrs.collection.EventSubscriptionCollection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SubscriptionService
{
	private RestTemplate restTemplate;
	
	public List<EventSubscription> getExternalSubscriptions()
	{
		List<EventSubscription> subscriptions = new ArrayList<EventSubscription>();
		
		Map<String, String> urlVariables = new HashMap<String, String>();
		
		ResponseEntity<EventSubscriptionCollection> response = null;
		try
		{
			response = 
					restTemplate.getForEntity( "http://localhost:8080/quoddy2/api/eventsubscription/", EventSubscriptionCollection.class, urlVariables );
		}
		catch( Exception e )
		{
			// TODO: add a proper logger
			e.printStackTrace();
			System.out.println( "Exception occurred trying to download subscription list" );
			System.err.println( "Exception occurred trying to download subscription list" );
		}
		
		if( response != null )
		{
			EventSubscriptionCollection subscriptionCollection = response.getBody();
		
			List<EventSubscription> subscriptionsColl = subscriptionCollection.getSubscriptions();
		
			subscriptions.addAll( subscriptionsColl );
		}
		
		return subscriptions;
	}

	public void setRestTemplate( RestTemplate restTemplate )
	{
		this.restTemplate = restTemplate;
	}
	
	public RestTemplate getRestTemplate()
	{
		return restTemplate;
	}
}
