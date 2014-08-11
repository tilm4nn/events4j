package net.objectzoo.events.impl;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.delegates.Action;
import net.objectzoo.events.helpers.EventSubscriberRegistry;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EventDistributorTest
{
	private EventSubscriberRegistry registryMock = mock(EventSubscriberRegistry.class);
	private EventDistributor subject = new EventDistributor();
	
	@Before
	public void setMock()
	{
		subject.registry = registryMock;
	}
	
	@Test
	public void subscribe_calls_subscriber_holder()
	{
		Action subscriber = someAction();
		
		subject.subscribe(subscriber);
		
		verify(registryMock).subscribe(subscriber);
	}
	
	@Test
	public void unsubscribe_calls_subscriber_holder()
	{
		Action subscriber = someAction();
		
		subject.unsubscribe(subscriber);
		
		verify(registryMock).unsubscribe(subscriber);
		
	}
	
	@Test
	public void invoke_retrieves_subscribers()
	{
		subject.invoke(new Object());
		
		verify(registryMock).getSubscribers();
	}
	
	@Test
	public void invoke_calls_subscribers()
	{
		Action subscriberMock1 = mock(Action.class);
		Action subscriberMock2 = mock(Action.class);
		doReturn(asList(subscriberMock1, subscriberMock2)).when(registryMock).getSubscribers();
		Object argument1 = new Object();
		
		subject.invoke(argument1);
		
		verify(subscriberMock1).invoke(argument1);
		verify(subscriberMock2).invoke(argument1);
	}
	
	private static Action someAction()
	{
		return new Action()
		{
			@Override
			public void invoke(Object parameter1)
			{
			}
		};
	}
}
