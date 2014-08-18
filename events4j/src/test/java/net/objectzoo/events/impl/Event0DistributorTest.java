package net.objectzoo.events.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

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
	public void accept_calls_subscribers()
	{
		subject.start();
		
		ArgumentCaptor<Consumer<Action0>> consumerArgument = (ArgumentCaptor) ArgumentCaptor.forClass(Consumer.class);
		verify(registryMock).callWithEachSubscriber(consumerArgument.capture());
		
		Action0 subscriberMock = mock(Action0.class);
		
		consumerArgument.getValue().accept(subscriberMock);
		verify(subscriberMock).start();
	}
	
	private static Action0 someAction0()
	{
		return new Action0()
		{
			@Override
			public void start()
			{
			}
		};
	}
}
