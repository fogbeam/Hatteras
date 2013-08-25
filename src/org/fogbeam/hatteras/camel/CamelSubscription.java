package org.fogbeam.hatteras.camel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.fogbeam.hatteras.subscription.Subscriber;

public class CamelSubscription 
{
	private String xQueryExpression;
	private List<Subscriber> subscribers = new ArrayList<Subscriber>();
	
	public String getXQueryExpression() {
		return xQueryExpression;
	}
	
	public void setXQueryExpression( final String xQueryExpression ) {
		this.xQueryExpression = xQueryExpression;
	}
	
	public List<Subscriber> getSubscribers()
	{
		return subscribers;
	}
	
	public void setSubscribers( final List<Subscriber> subscribers )
	{
		this.subscribers = subscribers;
	}
	
	public void addAllSubscribers( final Collection<Subscriber> subscribers )
	{
		this.subscribers.addAll( subscribers );
	}
	
	public void addSubscriber( final Subscriber subscriber )
	{
		this.subscribers.add( subscriber );
	}

}
