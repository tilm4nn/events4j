/*
 * The MIT License
 * 
 * Copyright (C) 2011 Tilmann Kuhn
 * 
 * http://www.object-zoo.net
 * 
 * mailto:events4j@object-zoo.net
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package net.objectzoo.events.impl;

import java.util.concurrent.Executor;

import net.objectzoo.delegates.Action2;
import net.objectzoo.delegates.Action2Async;
import net.objectzoo.delegates.ActionAsyncCallback;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.adapters.Action2ToAction2Async;
import net.objectzoo.delegates.impl.AsyncExecutor;

/**
 * The {@code Event2AsyncAdapter} is class that adapts an {@link Event2Delegate} to
 * {@link Event2AsyncDelegate} so that the original delegates subscribers can be invoked
 * asynchronously.
 * 
 * All asynchronous event distributions are executed in another thread.
 * 
 * The {@link Executor} to use for the asynchronous event distributions can be chosen during
 * creation of this helper. If no explicit executor is given the a default executor is used. The
 * default executor can be set using the {@link AsyncExecutor#setDefaultExecutor(Executor)} property
 * or is created automatically by the {@link AsyncExecutor}.
 * 
 * @author tilmann
 * 
 * @param <T1>
 *        The type of the first information parameter the event provides
 * @param <T2>
 *        The type of the second information parameter the event provides
 */
public class Event2AsyncAdapter<T1, T2> implements Event2AsyncDelegate<T1, T2>
{
	private final Action2Async<T1, T2> asyncDelegate;
	
	private final Event2Delegate<T1, T2> delegate;
	
	/**
	 * Creates a {@code Event2AsyncDistributor} using the default executor
	 * 
	 * @param delegate
	 *        the original delegate to be invoked asynchronously
	 * @throws IllegalArgumentException
	 *         if the given delegate is {@code null}
	 */
	public Event2AsyncAdapter(Event2Delegate<T1, T2> delegate) throws IllegalArgumentException
	{
		this(delegate, null);
	}
	
	/**
	 * Creates a {@code Event2AsyncDistributor} using the given {@link Executor}
	 * 
	 * @param executor
	 *        the executor used for the asynchronous event distributions
	 * @param delegate
	 *        the original delegate to be invoked asynchronously
	 * @throws IllegalArgumentException
	 *         if the given delegate is {@code null}
	 */
	public Event2AsyncAdapter(Event2Delegate<T1, T2> delegate, Executor executor)
		throws IllegalArgumentException
	{
		this.delegate = delegate;
		asyncDelegate = new Action2ToAction2Async<T1, T2>(delegate, executor);
	}
	
	/**
	 * This {@code beginInvoke} implementation invokes all event subscribers in another thread in
	 * the order they have been subscribed.
	 * 
	 * @param callback
	 *        the {@link ActionAsyncCallback}, if given to {@code beginInvoke}, is invoked upon
	 *        completion of invocation of all event subscribers and receives the same
	 *        {@link ActionAsyncResult} that is returned by the call to {@code beginInvoke}.
	 * @param asyncState
	 *        the asyncState is an arbitrary reference that, if given to {@code beginInvoke}, can be
	 *        retrieved from this invocations {@link ActionAsyncResult#getAsyncState()}
	 * @param parameter1
	 *        the first parameter to invoke the subscribers with
	 * @param parameter2
	 *        the second parameter to invoke the subscribers with
	 * @return the {@link ActionAsyncResult} associated with this asynchronous invocation
	 */
	@Override
	public ActionAsyncResult beginInvoke(ActionAsyncCallback callback, Object asyncState,
										 T1 parameter1, T2 parameter2)
	{
		return asyncDelegate.beginInvoke(callback, asyncState, parameter1, parameter2);
	}
	
	@Override
	public void invoke(T1 parameter1, T2 parameter2)
	{
		delegate.invoke(parameter1, parameter2);
	}
	
	@Override
	public void subscribe(Action2<? super T1, ? super T2> action)
	{
		delegate.subscribe(action);
	}
	
	@Override
	public void unsubscribe(Action2<? super T1, ? super T2> action)
	{
		delegate.unsubscribe(action);
	}
}
