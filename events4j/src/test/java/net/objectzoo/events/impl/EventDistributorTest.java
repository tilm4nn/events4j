package net.objectzoo.events.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

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
	public void accept_calls_subscribers()
	{
		Object argument1 = new Object();
		subject.accept(argument1);
		
		ArgumentCaptor<Consumer<Action>> consumerArgument = (ArgumentCaptor) ArgumentCaptor.forClass(Consumer.class);
		verify(registryMock).callWithEachSubscriber(consumerArgument.capture());
		
		Action subscriberMock = mock(Action.class);
		
		consumerArgument.getValue().accept(subscriberMock);
		verify(subscriberMock).accept(argument1);
	}
	
	private static Action someAction()
	{
		return new Action()
		{
			@Override
			public void accept(Object parameter1)
			{
			}
		};
	}
}
