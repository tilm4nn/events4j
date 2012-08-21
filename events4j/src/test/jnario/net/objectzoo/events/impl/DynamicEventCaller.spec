package net.objectzoo.events.impl

import net.objectzoo.delegates.DynamicAction

import net.objectzoo.events.helpers.EventSubscriberHolder
import static org.mockito.Mockito.*

import static extension org.jnario.lib.Should.*

describe DynamicEventCaller
{
	val holderMock = mock(typeof(EventSubscriberHolder)) as EventSubscriberHolder<DynamicAction>
	before subject.subscriberHolder = holderMock
	
	fact "subscribe calls subscriber holder"
	{
		val DynamicAction subscriber = [args|]
		
		subject.subscribe(subscriber)
		
		verify(holderMock).subscribe(subscriber)
	}
	
	fact "unsubscribe calls subscriber holder"
	{
		val DynamicAction subscriber = [args|]
		
		subject.unsubscribe(subscriber)
		
		verify(holderMock).unsubscribe(subscriber)
	}
	
	fact "invoke retrieves subscriber"
	{
		subject.dynamicInvoke(new Object)
		
		verify(holderMock).subscriber
	}

	fact "invoke calls subscriber"
	{
		val subscriberMock = mock(typeof(DynamicAction))
		doReturn(subscriberMock).when(holderMock).subscriber
		val argument1 = new Object
		val argument2 = new Object
		val argument3 = new Object
		val argument4 = new Object
		
		subject.dynamicInvoke(argument1, argument2, argument3, argument4)
		
		verify(subscriberMock).dynamicInvoke(argument1, argument2, argument3, argument4)
	}
}
