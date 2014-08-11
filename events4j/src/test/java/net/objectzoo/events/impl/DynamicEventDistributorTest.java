package net.objectzoo.events.impl;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.delegates.DynamicAction;
import net.objectzoo.events.helpers.EventSubscriberRegistry;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DynamicEventDistributorTest
{
	private EventSubscriberRegistry registryMock = mock(EventSubscriberRegistry.class);
	private DynamicEventDistributor subject = new DynamicEventDistributor();
	
	@Before
	public void setMock()
	{
		subject.registry = registryMock;
	}
	
	@Test
	public void subscribe_calls_subscriber_holder()
	{
		DynamicAction subscriber = someDynamicAction();
		
		subject.subscribe(subscriber);
		
		verify(registryMock).subscribe(subscriber);
	}
	
	@Test
	public void unsubscribe_calls_subscriber_holder()
	{
		DynamicAction subscriber = someDynamicAction();
		
		subject.unsubscribe(subscriber);
		
		verify(registryMock).unsubscribe(subscriber);
	}
	
	@Test
	public void dynamicInvoke_retrieves_subscribers()
	{
		subject.dynamicInvoke(new Object());
		
		verify(registryMock).getSubscribers();
	}
	
	@Test
	public void dynamicInvoke_calls_subscribers()
	{
		DynamicAction subscriberMock1 = mock(DynamicAction.class);
		DynamicAction subscriberMock2 = mock(DynamicAction.class);
		doReturn(asList(subscriberMock1, subscriberMock2)).when(registryMock).getSubscribers();
		Object argument1 = new Object();
		Object argument2 = new Object();
		Object argument3 = new Object();
		Object argument4 = new Object();
		
		subject.dynamicInvoke(argument1, argument2, argument3, argument4);
		
		verify(subscriberMock1).dynamicInvoke(argument1, argument2, argument3, argument4);
		verify(subscriberMock2).dynamicInvoke(argument1, argument2, argument3, argument4);
	}
	
	private static DynamicAction someDynamicAction()
	{
		return new DynamicAction()
		{
			@Override
			public void dynamicInvoke(Object... params)
			{
			}
		};
	}
}
