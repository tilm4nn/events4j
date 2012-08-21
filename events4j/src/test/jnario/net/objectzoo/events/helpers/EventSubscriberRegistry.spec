package net.objectzoo.events.helpers

import net.objectzoo.delegates.Action0

import static net.objectzoo.delegates.helpers.DynamicInvoker.*
import static org.mockito.Mockito.*

import static extension org.jnario.lib.Should.*

describe EventSubscriberRegistry
{
	val subject = new EventSubscriberRegistry<Action0>
	
	context "subscribe"
	{
		fact "throws exception for null subscriber"
		{
			subject.subscribe(null) should throw IllegalArgumentException
		}
		
		fact "subscribes the given subscriber"
		{
			val subscriber = [ | ] as Action0
			
			subject.subscribe(subscriber)
			
			subject.getSubscribers should contain subscriber
		}
	}
	
	context "unsubscribe"
	{
		fact "throws exception for null subscriber"
		{
			subject.unsubscribe(null) should throw IllegalArgumentException
		}
		
		fact "unsubscribes the given subscriber"
		{
			val subscriber = [ | ] as Action0
			subject.subscribe(subscriber)
			
			subject.unsubscribe(subscriber)
			
			subject.getSubscribers should not contain subscriber
		}
	}
}
