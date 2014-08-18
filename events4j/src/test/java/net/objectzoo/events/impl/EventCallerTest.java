package net.objectzoo.events.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import net.objectzoo.delegates.Action;
import net.objectzoo.events.helpers.EventSubscriberHolder;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EventCallerTest
{
	private EventSubscriberHolder holderMock = mock(EventSubscriberHolder.class);
	private EventCaller subject = new EventCaller();
	
	@Before
	public void setMock()
	{
		subject.subscriberHolder = holderMock;
	}
	
	@Test
	public void subscribe_calls_subscriber_holder()
	{
		Action subscriber = someAction();
		
		subject.subscribe(subscriber);
		
		verify(holderMock).subscribe(subscriber);
	}
	
	@Test
	public void unsubscribe_calls_subscriber_holder()
	{
		Action subscriber = someAction();
		
		subject.unsubscribe(subscriber);
		
		verify(holderMock).unsubscribe(subscriber);
	}
	
	@Test
	public void accept_calls_subscriber()
	{
		Object argument1 = new Object();
		subject.accept(argument1);
		
		ArgumentCaptor<Consumer<Action>> consumerArgument = (ArgumentCaptor) ArgumentCaptor.forClass(Consumer.class);
		verify(holderMock).callWithSubscriber(consumerArgument.capture());
		
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
