package net.objectzoo.events.impl

import net.objectzoo.delegates.Action

import net.objectzoo.events.helpers.EventSubscriberHolder
import static org.mockito.Mockito.*

import static extension org.jnario.lib.Should.*

describe EventCaller
{
	val holderMock = mock(typeof(EventSubscriberHolder))
			as EventSubscriberHolder<Action<Object>>
	before subject.subscriberHolder = holderMock
	
	fact "subscribe calls subscriber holder"
	{
		val Action subscriber = [a|]
		
		subject.subscribe(subscriber)
		
		verify(holderMock).subscribe(subscriber)
	}
	
	fact "unsubscribe calls subscriber holder"
	{
		val Action subscriber = [a|]
		
		subject.unsubscribe(subscriber)
		
		verify(holderMock).unsubscribe(subscriber)
	}
	
	fact "invoke retrieves subscriber"
	{
		subject.invoke(new Object)
		
		verify(holderMock).subscriber
	}

	fact "invoke calls subscriber"
	{
		val subscriberMock = mock(typeof(Action))
		doReturn(subscriberMock).when(holderMock).subscriber
		val argument = new Object
		
		subject.invoke(argument)
		
		verify(subscriberMock).invoke(argument)
	}
}
