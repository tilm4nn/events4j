package net.objectzoo.events.impl

import net.objectzoo.delegates.Action4

import net.objectzoo.events.helpers.EventSubscriberRegistry
import static org.mockito.Mockito.*

import static extension org.jnario.lib.Should.*

describe Event4Distributor
{
	val registryMock = mock(typeof(EventSubscriberRegistry)) as EventSubscriberRegistry<Action4>
	before subject.registry = registryMock
	
	fact "subscribe calls subscriber holder"
	{
		val Action4 subscriber = [a1, a2, a3, a4|]
		
		subject.subscribe(subscriber)
		
		verify(registryMock).subscribe(subscriber)
	}
	
	fact "unsubscribe calls subscriber holder"
	{
		val Action4 subscriber = [a1, a2, a3, a4|]
		
		subject.unsubscribe(subscriber)
		
		verify(registryMock).unsubscribe(subscriber)
	}
	
	fact "invoke retrieves subscribers"
	{
		subject.invoke(new Object, new Object, new Object, new Object)
		
		verify(registryMock).subscribers
	}

	fact "invoke calls subscribers"
	{
		val subscriberMock1 = mock(typeof(Action4))
		val subscriberMock2 = mock(typeof(Action4))
		doReturn(<Action4>list(subscriberMock1, subscriberMock2)).when(registryMock).subscribers
		val argument1 = new Object
		val argument2 = new Object
		val argument3 = new Object
		val argument4 = new Object
		
		subject.invoke(argument1, argument2, argument3, argument4)
		
		verify(subscriberMock1).invoke(argument1, argument2, argument3, argument4)
		verify(subscriberMock2).invoke(argument1, argument2, argument3, argument4)
	}
}
