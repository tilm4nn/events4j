package net.objectzoo.events.impl;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.delegates.Action3;
import net.objectzoo.events.helpers.EventSubscriberRegistry;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Event3DistributorTest
{
	private EventSubscriberRegistry registryMock = mock(EventSubscriberRegistry.class);
	private Event3Distributor subject = new Event3Distributor();
	
	@Before
	public void setMock()
	{
		subject.registry = registryMock;
	}
	
	@Test
	public void subscribe_calls_subscriber_holder()
	{
		Action3 subscriber = someAction3();
		
		subject.subscribe(subscriber);
		
		verify(registryMock).subscribe(subscriber);
	}
	
	@Test
	public void unsubscribe_calls_subscriber_holder()
	{
		Action3 subscriber = someAction3();
		
		subject.unsubscribe(subscriber);
		
		verify(registryMock).unsubscribe(subscriber);
		
	}
	
	@Test
	public void invoke_retrieves_subscribers()
	{
		subject.invoke(new Object(), new Object(), new Object());
		
		verify(registryMock).getSubscribers();
	}
	
	@Test
	public void invoke_calls_subscribers()
	{
		Action3 subscriberMock1 = mock(Action3.class);
		Action3 subscriberMock2 = mock(Action3.class);
		doReturn(asList(subscriberMock1, subscriberMock2)).when(registryMock).getSubscribers();
		Object argument1 = new Object();
		Object argument2 = new Object();
		Object argument3 = new Object();
		
		subject.invoke(argument1, argument2, argument3);
		
		verify(subscriberMock1).invoke(argument1, argument2, argument3);
		verify(subscriberMock2).invoke(argument1, argument2, argument3);
	}
	
	private static Action3 someAction3()
	{
		return new Action3()
		{
			@Override
			public void invoke(Object parameter1, Object parameter2, Object parameter3)
			{
			}
		};
	}
}
