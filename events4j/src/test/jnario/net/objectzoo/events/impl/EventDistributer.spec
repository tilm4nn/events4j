package net.objectzoo.events.impl

import net.objectzoo.delegates.Action

import net.objectzoo.events.helpers.EventSubscriberRegistry
import static org.mockito.Mockito.*

import static extension org.jnario.lib.Should.*

describe EventDistributor
{
	val registryMock = mock(typeof(EventSubscriberRegistry)) as EventSubscriberRegistry<Action>
	before subject.registry = registryMock
	
	fact "subscribe calls subscriber holder"
	{
		val Action subscriber = [a|]
		
		subject.subscribe(subscriber)
		
		verify(registryMock).subscribe(subscriber)
	}
	
	fact "unsubscribe calls subscriber holder"
	{
		val Action subscriber = [a|]
		
		subject.unsubscribe(subscriber)
		
		verify(registryMock).unsubscribe(subscriber)
	}
	
	fact "invoke retrieves subscribers"
	{
		subject.invoke(new Object)
		
		verify(registryMock).subscribers
	}

	fact "invoke calls subscribers"
	{
		val subscriberMock1 = mock(typeof(Action))
		val subscriberMock2 = mock(typeof(Action))
		doReturn(<Action>list(subscriberMock1, subscriberMock2)).when(registryMock).subscribers
		val argument = new Object
		
		subject.invoke(argument)
		
		verify(subscriberMock1).invoke(argument)
		verify(subscriberMock2).invoke(argument)
	}
}
