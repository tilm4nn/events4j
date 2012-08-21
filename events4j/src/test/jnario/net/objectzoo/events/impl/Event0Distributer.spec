package net.objectzoo.events.impl

import net.objectzoo.delegates.Action0

import net.objectzoo.events.helpers.EventSubscriberRegistry
import static org.mockito.Mockito.*

import static extension org.jnario.lib.Should.*

describe Event0Distributor
{
	val registryMock = mock(typeof(EventSubscriberRegistry)) as EventSubscriberRegistry<Action0>
	before subject.registry = registryMock
	
	fact "subscribe calls subscriber holder"
	{
		val Action0 subscriber = [|]
		
		subject.subscribe(subscriber)
		
		verify(registryMock).subscribe(subscriber)
	}
	
	fact "unsubscribe calls subscriber holder"
	{
		val Action0 subscriber = [|]
		
		subject.unsubscribe(subscriber)
		
		verify(registryMock).unsubscribe(subscriber)
	}
	
	fact "invoke retrieves subscribers"
	{
		subject.invoke
		
		verify(registryMock).subscribers
	}

	fact "invoke calls subscribers"
	{
		val subscriberMock1 = mock(typeof(Action0))
		val subscriberMock2 = mock(typeof(Action0))
		doReturn(<Action0>list(subscriberMock1, subscriberMock2)).when(registryMock).subscribers
		
		subject.invoke
		
		verify(subscriberMock1).invoke
		verify(subscriberMock2).invoke
	}
}
