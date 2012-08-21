package net.objectzoo.events.impl

import net.objectzoo.delegates.Action4

import net.objectzoo.events.helpers.EventSubscriberHolder
import static org.mockito.Mockito.*

import static extension org.jnario.lib.Should.*

describe Event4Caller
{
	val holderMock = mock(typeof(EventSubscriberHolder))
			as EventSubscriberHolder<Action4<Object, Object, Object, Object>>
	before subject.subscriberHolder = holderMock
	
	fact "subscribe calls subscriber holder"
	{
		val Action4 subscriber = [a1, a2, a3, a4|]
		
		subject.subscribe(subscriber)
		
		verify(holderMock).subscribe(subscriber)
	}
	
	fact "unsubscribe calls subscriber holder"
	{
		val Action4 subscriber = [a1, a2, a3, a4|]
		
		subject.unsubscribe(subscriber)
		
		verify(holderMock).unsubscribe(subscriber)
	}
	
	fact "invoke retrieves subscriber"
	{
		subject.invoke(new Object, new Object, new Object, new Object)
		
		verify(holderMock).subscriber
	}

	fact "invoke calls subscriber"
	{
		val subscriberMock = mock(typeof(Action4))
		doReturn(subscriberMock).when(holderMock).subscriber
		val argument1 = new Object
		val argument2 = new Object
		val argument3 = new Object
		val argument4 = new Object
		
		subject.invoke(argument1, argument2, argument3, argument4)
		
		verify(subscriberMock).invoke(argument1, argument2, argument3, argument4)
	}
}
