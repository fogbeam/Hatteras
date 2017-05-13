package org.fogbeam.hatteras.subscription;

public class Subscriber
{
	private String subscriberUserId;
	private String subscriberUuid;
	private String subscriptionUuid;
	
	
	public Subscriber()
	{}
	
	public Subscriber( final String subscriberUserId,  
					   final String subscriberUuid, 
					   final String subscriptionUuid )
	{
		this.subscriberUserId = subscriberUserId;
		this.subscriberUuid = subscriberUuid;
		this.subscriptionUuid = subscriptionUuid;
	}
	
	public String getSubscriberUserId()
	{
		return subscriberUserId;
	}
	
	public void setSubscriberUserId( String subscriberUserId )
	{
		this.subscriberUserId = subscriberUserId;
	}
	
	public String getSubscriberUuid()
	{
		return subscriberUuid;
	}
	
	public void setSubscriberUuid( String subscriberUuid )
	{
		this.subscriberUuid = subscriberUuid;
	}
	
	public String getSubscriptionUuid()
	{
		return subscriptionUuid;
	}
	
	public void setSubscriptionUuid( String subscriptionUuid )
	{
		this.subscriptionUuid = subscriptionUuid;
	}
}
