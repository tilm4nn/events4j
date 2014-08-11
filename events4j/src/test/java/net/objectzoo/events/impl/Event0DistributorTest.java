package net.objectzoo.events.impl;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.delegates.Action0;
import net.objectzoo.events.helpers.EventSubscriberRegistry;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Event0DistributorTest
{
	private EventSubscriberRegistry registryMock = mock(EventSubscriberRegistry.class);
	private Event0Distributor subject = new Event0Distributor();
	
	@Before
	public void setMock()
	{
		subject.registry = registryMock;
	}
	
	@Test
	public void subscribe_calls_subscriber_holder()
	{
		Action0 subscriber = someAction0();
		
		subject.subscribe(subscriber);
		
		verify(registryMock).subscribe(subscriber);
	}
	
	@Test
	public void unsubscribe_calls_subscriber_holder()
	{
		Action0 subscriber = someAction0();
		
		subject.unsubscribe(subscriber);
		
		verify(registryMock).unsubscribe(subscriber);
	}
	
	@Test
	public void invoke_retrieves_subscribers()
	{
		subject.invoke();
		
		verify(registryMock).getSubscribers();
	}
	
	@Test
	public void invoke_calls_subscribers()
	{
		Action0 subscriberMock1 = mock(Action0.class);
		Action0 subscriberMock2 = mock(Action0.class);
		doReturn(asList(subscriberMock1, subscriberMock2)).when(registryMock).getSubscribers();
		
		subject.invoke();
		
		verify(subscriberMock1).invoke();
		verify(subscriberMock2).invoke();
	}
	
	private static Action0 someAction0()
	{
		return new Action0()
		{
			@Override
			public void invoke()
			{
			}
		};
	}
}
