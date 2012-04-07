package org.fogbeam.hatteras.camel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CamelSubscription 
{
	private String xQueryExpression;
	private List<String> subscribers = new ArrayList<String>();
	
	public String getXQueryExpression() {
		return xQueryExpression;
	}
	
	public void setXQueryExpression( final String xQueryExpression ) {
		this.xQueryExpression = xQueryExpression;
	}

	public List<String> getSubscribers()
	{
		return subscribers;
	}
	
	public void setSubscribers( final List<String> subscribers )
	{
		this.subscribers = subscribers;
	}
	
	public void addAllSubscribers( final Collection<String> subscribers )
	{
		this.subscribers.addAll( subscribers );
	}
	
	public void addSubscriber( final String subscriber )
	{
		this.subscribers.add( subscriber );
	}

}
