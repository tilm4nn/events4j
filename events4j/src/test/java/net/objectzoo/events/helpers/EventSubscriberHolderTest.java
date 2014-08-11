package net.objectzoo.events.helpers;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import net.objectzoo.delegates.Action0;

public class EventSubscriberHolderTest
{
	private EventSubscriberHolder<Action0> sut = new EventSubscriberHolder<Action0>();
	
	@Test(expected = IllegalArgumentException.class)
	public void subscribe_throws_exception_for_null_subscriber()
	{
		sut.subscribe(null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void subscribe_throws_exception_when_subscriber_is_already_set()
	{
		sut.subscribe(someAction0());
		
		sut.subscribe(someAction0());
	}
	
	@Test
	public void subscribe_subscribes_the_given_subscriber()
	{
		Action0 subscriber = someAction0();
		
		sut.subscribe(subscriber);
		
		assertThat(sut.getSubscriber(), is(subscriber));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void unsubscribe_throws_exception_for_null_subscriber()
	{
		sut.unsubscribe(null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void unsubscribe_throws_exception_when_another_subscriber_is_set()
	{
		sut.subscribe(someAction0());
		
		sut.unsubscribe(someAction0());
	}
	
	@Test
	public void unsubscribe_unsubscribes_the_given_subscriber()
	{
		Action0 subscriber = someAction0();
		sut.subscribe(subscriber);
		
		sut.unsubscribe(subscriber);
		
		assertThat(sut.getSubscriber(), is((Action0) null));
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
