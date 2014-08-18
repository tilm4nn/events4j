package net.objectzoo.events.helpers;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;

import org.junit.Test;
import org.mockito.Mockito;

import net.objectzoo.delegates.Action0;

public class EventSubscriberHolderTest
{
	private EventSubscriberHolder<Action0> sut = new EventSubscriberHolder<Action0>();
	
	@Test(expected = NullPointerException.class)
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
		
		assertThat(sut.subscriber.get(), is(subscriber));
	}
	
	@Test(expected = NullPointerException.class)
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
		
		assertFalse(sut.subscriber.isPresent());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void callWithSubscriber_calls_given_consumer_when_subscribed()
	{
		Action0 subscriber = someAction0();
		sut.subscribe(subscriber);
		Consumer<Action0> actionConsumer = mock(Consumer.class);
		
		sut.callWithSubscriber(actionConsumer);
		
		verify(actionConsumer).accept(subscriber);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void callWithSubscriber_does_not_call_given_consumer_when_not_subscribed()
	{
		Consumer<Action0> actionConsumer = mock(Consumer.class);
		
		sut.callWithSubscriber(actionConsumer);
		
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
