package net.objectzoo.events.impl;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.delegates.Action4;
import net.objectzoo.events.helpers.EventSubscriberRegistry;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Event4DistributorTest
{
	private EventSubscriberRegistry registryMock = mock(EventSubscriberRegistry.class);
	private Event4Distributor subject = new Event4Distributor();
	
	@Before
	public void setMock()
	{
		subject.registry = registryMock;
	}
	
	@Test
	public void subscribe_calls_subscriber_holder()
	{
		Action4 subscriber = someAction4();
		
		subject.subscribe(subscriber);
		
		verify(registryMock).subscribe(subscriber);
	}
	
	@Test
	public void unsubscribe_calls_subscriber_holder()
	{
		Action4 subscriber = someAction4();
		
		subject.unsubscribe(subscriber);
		
		verify(registryMock).unsubscribe(subscriber);
		
	}
	
	@Test
	public void invoke_retrieves_subscribers()
	{
		subject.invoke(new Object(), new Object(), new Object(), new Object());
		
		verify(registryMock).getSubscribers();
	}
	
	@Test
	public void invoke_calls_subscribers()
	{
		Action4 subscriberMock1 = mock(Action4.class);
		Action4 subscriberMock2 = mock(Action4.class);
		doReturn(asList(subscriberMock1, subscriberMock2)).when(registryMock).getSubscribers();
		Object argument1 = new Object();
		Object argument2 = new Object();
		Object argument3 = new Object();
		Object argument4 = new Object();
		
		subject.invoke(argument1, argument2, argument3, argument4);
		
		verify(subscriberMock1).invoke(argument1, argument2, argument3, argument4);
		verify(subscriberMock2).invoke(argument1, argument2, argument3, argument4);
	}
	
	private static Action4 someAction4()
	{
		return new Action4()
		{
			@Override
			public void invoke(Object parameter1, Object parameter2, Object parameter3, Object parameter4)
			{
			}
		};
	}
}
