package net.objectzoo.events.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import net.objectzoo.delegates.Action0;
import net.objectzoo.events.helpers.EventSubscriberHolder;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Event0CallerTest
{
	private EventSubscriberHolder holderMock = mock(EventSubscriberHolder.class);
	private Event0Caller subject = new Event0Caller();
	
	@Before
	public void setMock()
	{
		subject.subscriberHolder = holderMock;
	}
	
	@Test
	public void subscribe_calls_subscriber_holder()
	{
		Action0 subscriber = someAction0();
		
		subject.subscribe(subscriber);
		
		verify(holderMock).subscribe(subscriber);
	}
	
	@Test
	public void unsubscribe_calls_subscriber_holder()
	{
		Action0 subscriber = someAction0();
		
		subject.unsubscribe(subscriber);
		
		verify(holderMock).unsubscribe(subscriber);
	}
	
	@Test
	public void accept_calls_subscriber()
	{
		subject.start();
		
		ArgumentCaptor<Consumer<Action0>> consumerArgument = (ArgumentCaptor) ArgumentCaptor.forClass(Consumer.class);
		verify(holderMock).callWithSubscriber(consumerArgument.capture());
		
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
