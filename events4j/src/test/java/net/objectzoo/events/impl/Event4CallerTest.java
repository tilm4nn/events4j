package net.objectzoo.events.impl;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.delegates.Action4;
import net.objectzoo.events.helpers.EventSubscriberHolder;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Event4CallerTest
{
	private EventSubscriberHolder holderMock = mock(EventSubscriberHolder.class);
	private Event4Caller subject = new Event4Caller();
	
	@Before
	public void setMock()
	{
		subject.subscriberHolder = holderMock;
	}
	
	@Test
	public void subscribe_calls_subscriber_holder()
	{
		Action4 subscriber = someAction4();
		
		subject.subscribe(subscriber);
		
		verify(holderMock).subscribe(subscriber);
	}
	
	@Test
	public void unsubscribe_calls_subscriber_holder()
	{
		Action4 subscriber = someAction4();
		
		subject.unsubscribe(subscriber);
		
		verify(holderMock).unsubscribe(subscriber);
	}
	
	@Test
	public void invoke_retrieves_subscriber()
	{
		subject.invoke(new Object(), new Object(), new Object(), new Object());
		
		verify(holderMock).getSubscriber();
	}
	
	@Test
	public void invoke_calls_subscriber()
	{
		Action4 subscriberMock = mock(Action4.class);
		doReturn(subscriberMock).when(holderMock).getSubscriber();
		Object argument1 = new Object();
		Object argument2 = new Object();
		Object argument3 = new Object();
		Object argument4 = new Object();
		
		subject.invoke(argument1, argument2, argument3, argument4);
		
		verify(subscriberMock).invoke(argument1, argument2, argument3, argument4);
	}
	
	private static Action4 someAction4()
	{
		return new Action4()
		{
			@Override
			public void invoke(Object parameter1, Object parameter2, Object parameter3, Object parameter4)
			{
			}
		};
	}
}
