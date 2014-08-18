package net.objectzoo.events.helpers;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;

import org.junit.Test;
import org.mockito.Mockito;

import net.objectzoo.delegates.Action0;

public class EventSubscriberRegistryTest
{
	EventSubscriberRegistry<Action0> sut = new EventSubscriberRegistry<Action0>();
	
	@Test(expected = NullPointerException.class)
	public void subscribe_throws_exception_for_null_subscriber()
	{
		sut.subscribe(null);
	}
	
	@Test
	public void subscribe_subscribes_the_given_subscriber()
	{
		Action0 subscriber = someAction0();
		
		sut.subscribe(subscriber);
		
		assertThat(sut.subscribers, hasItem(subscriber));
	}
	
	@Test(expected = NullPointerException.class)
	public void unsubscribe_throws_exception_for_null_subscriber()
	{
		sut.unsubscribe(null);
	}
	
	@Test
	public void unsubscribe_unsubscribes_the_given_subscriber()
	{
		Action0 subscriber = someAction0();
		sut.subscribe(subscriber);
		
		sut.unsubscribe(subscriber);
		
		assertThat(sut.subscribers, not(hasItem(subscriber)));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void callWithEachSubscriber_calls_given_consumer_when_subscribed()
	{
		Action0 subscriber1 = someAction0();
		Action0 subscriber2 = someAction0();
		sut.subscribe(subscriber1);
		sut.subscribe(subscriber2);
		Consumer<Action0> actionConsumer = mock(Consumer.class);
		
		sut.callWithEachSubscriber(actionConsumer);
		
		verify(actionConsumer).accept(subscriber1);
		verify(actionConsumer).accept(subscriber2);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void callWithEachSubscriber_does_not_call_given_consumer_when_not_subscribed()
	{
		Consumer<Action0> actionConsumer = mock(Consumer.class);
		
		sut.callWithEachSubscriber(actionConsumer);
		
		Mockito.verifyZeroInteractions(actionConsumer);
	}
	
	private static Action0 someAction0()
	{
		// This does not work because the returned values are equal!
		// return () -> {};
		
		return new Action0()
		{
			@Override
			public void start()
			{
			}
		};
	}
}
