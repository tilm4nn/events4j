package net.objectzoo.events.impl;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

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
	public void invoke_retrieves_subscriber()
	{
		subject.invoke(new Object());
		
		verify(holderMock).getSubscriber();
	}
	
	@Test
	public void invoke_calls_subscriber()
	{
		Action subscriberMock = mock(Action.class);
		doReturn(subscriberMock).when(holderMock).getSubscriber();
		Object argument1 = new Object();
		
		subject.invoke(argument1);
		
		verify(subscriberMock).invoke(argument1);
	}
	
	private static Action someAction()
	{
		return new Action()
		{
			@Override
			public void invoke(Object parameter1)
			{
			}
		};
	}
}
