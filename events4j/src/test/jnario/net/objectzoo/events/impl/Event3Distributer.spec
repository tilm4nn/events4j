package net.objectzoo.events.impl

import net.objectzoo.delegates.Action3

import net.objectzoo.events.helpers.EventSubscriberRegistry
import static org.mockito.Mockito.*

import static extension org.jnario.lib.Should.*

describe Event3Distributor
{
	val registryMock = mock(typeof(EventSubscriberRegistry)) as EventSubscriberRegistry<Action3>
	before subject.registry = registryMock
	
	fact "subscribe calls subscriber holder"
	{
		val Action3 subscriber = [a1, a2, a3|]
		
		subject.subscribe(subscriber)
		
		verify(registryMock).subscribe(subscriber)
	}
	
	fact "unsubscribe calls subscriber holder"
	{
		val Action3 subscriber = [a1, a2, a3|]
		
		subject.unsubscribe(subscriber)
		
		verify(registryMock).unsubscribe(subscriber)
	}
	
	fact "invoke retrieves subscribers"
	{
		subject.invoke(new Object, new Object, new Object)
		
		verify(registryMock).subscribers
	}

	fact "invoke calls subscribers"
	{
		val subscriberMock1 = mock(typeof(Action3))
		val subscriberMock2 = mock(typeof(Action3))
		doReturn(<Action3>list(subscriberMock1, subscriberMock2)).when(registryMock).subscribers
		val argument1 = new Object
		val argument2 = new Object
		val argument3 = new Object
		
		subject.invoke(argument1, argument2, argument3)
		
		verify(subscriberMock1).invoke(argument1, argument2, argument3)
		verify(subscriberMock2).invoke(argument1, argument2, argument3)
	}
}
