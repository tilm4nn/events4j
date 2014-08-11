package net.objectzoo.events.helpers;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import net.objectzoo.delegates.Action0;

public class EventSubscriberRegistryTest
{
	EventSubscriberRegistry<Action0> subject = new EventSubscriberRegistry<Action0>();
	
	@Test(expected = IllegalArgumentException.class)
	public void subscribe_throws_exception_for_null_subscriber()
	{
		subject.subscribe(null);
	}
	
	@Test
	public void subscribe_subscribes_the_given_subscriber()
	{
		Action0 subscriber = someAction0();
		
		subject.subscribe(subscriber);
		
		assertThat(subject.getSubscribers(), hasItem(subscriber));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void unsubscribe_throws_exception_for_null_subscriber()
	{
		subject.unsubscribe(null);
	}
	
	@Test
	public void unsubscribe_unsubscribes_the_given_subscriber()
	{
		Action0 subscriber = someAction0();
		subject.subscribe(subscriber);
		
		subject.unsubscribe(subscriber);
		
		assertThat(subject.getSubscribers(), not(hasItem(subscriber)));
	}
	
	private static Action0 someAction0()
	{
		return new Action0()
		{
			@Override
			public void invoke()
			{
			}
		};
	}
}
