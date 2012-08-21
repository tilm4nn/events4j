package net.objectzoo.events.impl

import net.objectzoo.delegates.Action3

import net.objectzoo.events.helpers.EventSubscriberHolder
import static org.mockito.Mockito.*

import static extension org.jnario.lib.Should.*

describe Event3Caller
{
	val holderMock = mock(typeof(EventSubscriberHolder))
			as EventSubscriberHolder<Action3<Object, Object, Object>>
	before subject.subscriberHolder = holderMock
	
	fact "subscribe calls subscriber holder"
	{
		val Action3 subscriber = [a1, a2, a3|]
		
		subject.subscribe(subscriber)
		
		verify(holderMock).subscribe(subscriber)
	}
	
	fact "unsubscribe calls subscriber holder"
	{
		val Action3 subscriber = [a1, a2, a3|]
		
		subject.unsubscribe(subscriber)
		
		verify(holderMock).unsubscribe(subscriber)
	}
	
	fact "invoke retrieves subscriber"
	{
		subject.invoke(new Object, new Object, new Object)
		
		verify(holderMock).subscriber
	}

	fact "invoke calls subscriber"
	{
		val subscriberMock = mock(typeof(Action3))
		doReturn(subscriberMock).when(holderMock).subscriber
		val argument1 = new Object
		val argument2 = new Object
		val argument3 = new Object
		
		subject.invoke(argument1, argument2, argument3)
		
		verify(subscriberMock).invoke(argument1, argument2, argument3)
	}
}
