package net.objectzoo.events.helpers

import net.objectzoo.delegates.Action0

import static net.objectzoo.delegates.helpers.DynamicInvoker.*
import static org.mockito.Mockito.*

import static extension org.jnario.lib.Should.*

describe EventSubscriberHolder
{
	val sut = new EventSubscriberHolder<Action0>
	
	context "subscribe"
	{
		fact "throws exception for null subscriber"
		{
			sut.subscribe(null) should throw IllegalArgumentException
		}
		
		fact "throws exception when subscriber is already set"
		{
			sut.subscribe([ | ]);
			
			sut.subscribe([ | ]) should throw IllegalStateException
		}
		
		fact "subscribes the given subscriber"
		{
			val subscriber = [ | ] as Action0
			
			sut.subscribe(subscriber)
			
			sut.getSubscriber should be subscriber
		}
	}
	
	context "unsubscribe"
	{
		fact "throws exception for null subscriber"
		{
			sut.unsubscribe(null) should throw IllegalArgumentException
		}
		
		fact "throws exception when another subscriber is set"
		{
			sut.subscribe([ | ]);
			
			sut.unsubscribe([ | ]) should throw IllegalStateException
		}
		
		fact "unsubscribes the given subscriber"
		{
			val subscriber = [ | ] as Action0
			sut.subscribe(subscriber)
			
			sut.unsubscribe(subscriber)
			
			sut.getSubscriber should be null
		}
	}
}
