package net.objectzoo.events.impl;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.delegates.Action3;
import net.objectzoo.events.helpers.EventSubscriberHolder;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Event3CallerTest
{
	private EventSubscriberHolder holderMock = mock(EventSubscriberHolder.class);
	private Event3Caller subject = new Event3Caller();
	
	@Before
	public void setMock()
	{
		subject.subscriberHolder = holderMock;
	}
	
	@Test
	public void subscribe_calls_subscriber_holder()
	{
		Action3 subscriber = someAction3();
		
		subject.subscribe(subscriber);
		
		verify(holderMock).subscribe(subscriber);
	}
	
	@Test
	public void unsubscribe_calls_subscriber_holder()
	{
		Action3 subscriber = someAction3();
		
		subject.unsubscribe(subscriber);
		
		verify(holderMock).unsubscribe(subscriber);
	}
	
	@Test
	public void invoke_retrieves_subscriber()
	{
		subject.invoke(new Object(), new Object(), new Object());
		
		verify(holderMock).getSubscriber();
	}
	
	@Test
	public void invoke_calls_subscriber()
	{
		Action3 subscriberMock = mock(Action3.class);
		doReturn(subscriberMock).when(holderMock).getSubscriber();
		Object argument1 = new Object();
		Object argument2 = new Object();
		Object argument3 = new Object();
		
		subject.invoke(argument1, argument2, argument3);
		
		verify(subscriberMock).invoke(argument1, argument2, argument3);
	}
	
	private static Action3 someAction3()
	{
		return new Action3()
		{
			@Override
			public void invoke(Object parameter1, Object parameter2, Object parameter3)
			{
			}
		};
	}
}
