package net.objectzoo.events.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

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
	public void accept_calls_subscribers()
	{
		Object argument1 = new Object();
		Object argument2 = new Object();
		Object argument3 = new Object();
		subject.accept(argument1, argument2, argument3);
		
		ArgumentCaptor<Consumer<Action3>> consumerArgument = (ArgumentCaptor) ArgumentCaptor.forClass(Consumer.class);
		verify(registryMock).callWithEachSubscriber(consumerArgument.capture());
		
		Action3 subscriberMock = mock(Action3.class);
		
		consumerArgument.getValue().accept(subscriberMock);
		verify(subscriberMock).accept(argument1, argument2, argument3);
	}
	
	private static Action3 someAction3()
	{
		return new Action3()
		{
			@Override
			public void accept(Object parameter1, Object parameter2, Object parameter3)
			{
			}
		};
	}
}
