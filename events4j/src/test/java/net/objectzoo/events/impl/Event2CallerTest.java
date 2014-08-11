package net.objectzoo.events.impl;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.delegates.Action2;
import net.objectzoo.events.helpers.EventSubscriberHolder;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Event2CallerTest
{
	private EventSubscriberHolder holderMock = mock(EventSubscriberHolder.class);
	private Event2Caller subject = new Event2Caller();
	
	@Before
	public void setMock()
	{
		subject.subscriberHolder = holderMock;
	}
	
	@Test
	public void subscribe_calls_subscriber_holder()
	{
		Action2 subscriber = someAction2();
		
		subject.subscribe(subscriber);
		
		verify(holderMock).subscribe(subscriber);
	}
	
	@Test
	public void unsubscribe_calls_subscriber_holder()
	{
		Action2 subscriber = someAction2();
		
		subject.unsubscribe(subscriber);
		
		verify(holderMock).unsubscribe(subscriber);
	}
	
	@Test
	public void invoke_retrieves_subscriber()
	{
		subject.invoke(new Object(), new Object());
		
		verify(holderMock).getSubscriber();
	}
	
	@Test
	public void invoke_calls_subscriber()
	{
		Action2 subscriberMock = mock(Action2.class);
		doReturn(subscriberMock).when(holderMock).getSubscriber();
		Object argument1 = new Object();
		Object argument2 = new Object();
		
		subject.invoke(argument1, argument2);
		
		verify(subscriberMock).invoke(argument1, argument2);
	}
	
	private static Action2 someAction2()
	{
		return new Action2()
		{
			@Override
			public void invoke(Object parameter1, Object parameter2)
			{
			}
		};
	}
}
