package net.objectzoo.events.impl

import net.objectzoo.delegates.Action0

import net.objectzoo.events.helpers.EventSubscriberHolder
import static org.mockito.Mockito.*

import static extension org.jnario.lib.Should.*

describe Event0Caller
{
	val holderMock = mock(typeof(EventSubscriberHolder)) as EventSubscriberHolder<Action0>
	before subject.subscriberHolder = holderMock
	
	fact "subscribe calls subscriber holder"
	{
		val Action0 subscriber = [|]
		
		subject.subscribe(subscriber)
		
		verify(holderMock).subscribe(subscriber)
	}
	
	fact "unsubscribe calls subscriber holder"
	{
		val Action0 subscriber = [|]
		
		subject.unsubscribe(subscriber)
		
		verify(holderMock).unsubscribe(subscriber)
	}
	
	fact "invoke retrieves subscriber"
	{
		subject.invoke
		
		verify(holderMock).subscriber
	}

	fact "invoke calls subscriber"
	{
		val subscriberMock = mock(typeof(Action0))
		doReturn(subscriberMock).when(holderMock).subscriber
		
		subject.invoke
		
		verify(subscriberMock).invoke
	}
}
