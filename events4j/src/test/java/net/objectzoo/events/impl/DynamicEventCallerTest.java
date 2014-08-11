package net.objectzoo.events.impl;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.delegates.DynamicAction;
import net.objectzoo.events.helpers.EventSubscriberHolder;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DynamicEventCallerTest
{
	private EventSubscriberHolder holderMock = mock(EventSubscriberHolder.class);
	private DynamicEventCaller sut = new DynamicEventCaller();
	
	@Before
	public void setMock()
	{
		sut.subscriberHolder = holderMock;
	}
	
	@Test
	public void subscribe_calls_subscriber_holder()
	{
		DynamicAction subscriber = someDynamicAction();
		
		sut.subscribe(subscriber);
		
		verify(holderMock).subscribe(subscriber);
	}
	
	@Test
	public void unsubscribe_calls_subscriber_holder()
	{
		DynamicAction subscriber = someDynamicAction();
		
		sut.unsubscribe(subscriber);
		
		verify(holderMock).unsubscribe(subscriber);
	}
	
	@Test
	public void invoke_retrieves_subscriber()
	{
		sut.dynamicInvoke(new Object());
		
		verify(holderMock).getSubscriber();
	}
	
	@Test
	public void invoke_calls_subscriber()
	{
		DynamicAction subscriberMock = mock(DynamicAction.class);
		doReturn(subscriberMock).when(holderMock).getSubscriber();
		Object argument1 = new Object();
		Object argument2 = new Object();
		Object argument3 = new Object();
		Object argument4 = new Object();
		
		sut.dynamicInvoke(argument1, argument2, argument3, argument4);
		
		verify(subscriberMock).dynamicInvoke(argument1, argument2, argument3, argument4);
	}
	
	private static DynamicAction someDynamicAction()
	{
		return new DynamicAction()
		{
			@Override
			public void dynamicInvoke(Object... params)
			{
			}
		};
	}
}
