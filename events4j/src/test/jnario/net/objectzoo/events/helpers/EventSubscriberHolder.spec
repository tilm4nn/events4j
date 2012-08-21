package net.objectzoo.events.helpers

import net.objectzoo.delegates.Action0

import static net.objectzoo.delegates.helpers.DynamicInvoker.*
import static org.mockito.Mockito.*

import static extension org.jnario.lib.Should.*

describe EventSubscriberHolder
{
	val subject = new EventSubscriberHolder<Action0>
	
	context "subscribe"
	{
		fact "throws exception for null subscriber"
		{
			subject.subscribe(null) should throw IllegalArgumentException
		}
		
		fact "throws exception when subscriber is already set"
		{
			subject.subscribe([ | ]);
			
			subject.subscribe([ | ]) should throw IllegalStateException
		}
		
		fact "subscribes the given subscriber"
		{
			val subscriber = [ | ] as Action0
			
			subject.subscribe(subscriber)
			
			subject.getSubscriber should be subscriber
		}
	}
	
	context "unsubscribe"
	{
		fact "throws exception for null subscriber"
		{
			subject.unsubscribe(null) should throw IllegalArgumentException
		}
		
		fact "throws exception when another subscriber is set"
		{
			subject.subscribe([ | ]);
			
			subject.unsubscribe([ | ]) should throw IllegalStateException
		}
		
		fact "unsubscribes the given subscriber"
		{
			val subscriber = [ | ] as Action0
			subject.subscribe(subscriber)
			
			subject.unsubscribe(subscriber)
			
			subject.getSubscriber should be null
		}
	}
}
