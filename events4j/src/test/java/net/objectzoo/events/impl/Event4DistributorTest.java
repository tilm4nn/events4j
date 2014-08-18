package net.objectzoo.events.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

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
	public void accept_calls_subscribers()
	{
		Object argument1 = new Object();
		Object argument2 = new Object();
		Object argument3 = new Object();
		Object argument4 = new Object();
		subject.accept(argument1, argument2, argument3, argument4);
		
		ArgumentCaptor<Consumer<Action4>> consumerArgument = (ArgumentCaptor) ArgumentCaptor.forClass(Consumer.class);
		verify(registryMock).callWithEachSubscriber(consumerArgument.capture());
		
		Action4 subscriberMock = mock(Action4.class);
		
		consumerArgument.getValue().accept(subscriberMock);
		verify(subscriberMock).accept(argument1, argument2, argument3, argument4);
	}
	
	private static Action4 someAction4()
	{
		return new Action4()
		{
			@Override
			public void accept(Object parameter1, Object parameter2, Object parameter3,
							   Object parameter4)
			{
			}
		};
	}
}
