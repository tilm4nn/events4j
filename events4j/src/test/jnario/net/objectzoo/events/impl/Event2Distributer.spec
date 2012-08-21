package net.objectzoo.events.impl

import net.objectzoo.delegates.Action2

import net.objectzoo.events.helpers.EventSubscriberRegistry
import static org.mockito.Mockito.*

import static extension org.jnario.lib.Should.*

describe Event2Distributor
{
	val registryMock = mock(typeof(EventSubscriberRegistry)) as EventSubscriberRegistry<Action2>
	before subject.registry = registryMock
	
	fact "subscribe calls subscriber holder"
	{
		val Action2 subscriber = [a1, a2|]
		
		subject.subscribe(subscriber)
		
		verify(registryMock).subscribe(subscriber)
	}
	
	fact "unsubscribe calls subscriber holder"
	{
		val Action2 subscriber = [a1, a2|]
		
		subject.unsubscribe(subscriber)
		
		verify(registryMock).unsubscribe(subscriber)
	}
	
	fact "invoke retrieves subscribers"
	{
		subject.invoke(new Object, new Object)
		
		verify(registryMock).subscribers
	}

	fact "invoke calls subscribers"
	{
		val subscriberMock1 = mock(typeof(Action2))
		val subscriberMock2 = mock(typeof(Action2))
		doReturn(<Action2>list(subscriberMock1, subscriberMock2)).when(registryMock).subscribers
		val argument1 = new Object
		val argument2 = new Object
		
		subject.invoke(argument1, argument2)
		
		verify(subscriberMock1).invoke(argument1, argument2)
		verify(subscriberMock2).invoke(argument1, argument2)
	}
}
