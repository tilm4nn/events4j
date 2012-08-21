package net.objectzoo.events.impl

import net.objectzoo.delegates.DynamicAction

import net.objectzoo.events.helpers.EventSubscriberRegistry
import static org.mockito.Mockito.*

import static extension org.jnario.lib.Should.*

describe DynamicEventDistributor
{
	val registryMock = mock(typeof(EventSubscriberRegistry)) as EventSubscriberRegistry<DynamicAction>
	before subject.registry = registryMock
	
	fact "subscribe calls subscriber holder"
	{
		val DynamicAction subscriber = [args|]
		
		subject.subscribe(subscriber)
		
		verify(registryMock).subscribe(subscriber)
	}
	
	fact "unsubscribe calls subscriber holder"
	{
		val DynamicAction subscriber = [args|]
		
		subject.unsubscribe(subscriber)
		
		verify(registryMock).unsubscribe(subscriber)
	}
	
	fact "invoke retrieves subscribers"
	{
		subject.dynamicInvoke(new Object)
		
		verify(registryMock).subscribers
	}

	fact "invoke calls subscribers"
	{
		val subscriberMock1 = mock(typeof(DynamicAction))
		val subscriberMock2 = mock(typeof(DynamicAction))
		doReturn(<DynamicAction>list(subscriberMock1, subscriberMock2)).when(registryMock).subscribers
		val argument1 = new Object
		val argument2 = new Object
		val argument3 = new Object
		val argument4 = new Object
		
		subject.dynamicInvoke(argument1, argument2, argument3, argument4)
		
		verify(subscriberMock1).dynamicInvoke(argument1, argument2, argument3, argument4)
		verify(subscriberMock2).dynamicInvoke(argument1, argument2, argument3, argument4)
	}
}
