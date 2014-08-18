package net.objectzoo.events.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import net.objectzoo.delegates.Action2;
import net.objectzoo.events.helpers.EventSubscriberRegistry;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Event2DistributorTest
{
	private EventSubscriberRegistry registryMock = mock(EventSubscriberRegistry.class);
	private Event2Distributor subject = new Event2Distributor();
	
	@Before
	public void setMock()
	{
		subject.registry = registryMock;
	}
	
	@Test
	public void subscribe_calls_subscriber_holder()
	{
		Action2 subscriber = someAction2();
		
		subject.subscribe(subscriber);
		
		verify(registryMock).subscribe(subscriber);
	}
	
	@Test
	public void unsubscribe_calls_subscriber_holder()
	{
		Action2 subscriber = someAction2();
		
		subject.unsubscribe(subscriber);
		
		verify(registryMock).unsubscribe(subscriber);
		
	}
	
	@Test
	public void invoke_calls_subscribers()
	{
		Object argument1 = new Object();
		Object argument2 = new Object();
		subject.accept(argument1, argument2);
		
		ArgumentCaptor<Consumer<Action2>> consumerArgument = (ArgumentCaptor) ArgumentCaptor.forClass(Consumer.class);
		verify(registryMock).callWithEachSubscriber(consumerArgument.capture());
		
		Action2 subscriberMock = mock(Action2.class);
		
		consumerArgument.getValue().accept(subscriberMock);
		verify(subscriberMock).accept(argument1, argument2);
	}
	
	private static Action2 someAction2()
	{
		return new Action2()
		{
			@Override
			public void accept(Object parameter1, Object parameter2)
			{
			}
		};
	}
}
