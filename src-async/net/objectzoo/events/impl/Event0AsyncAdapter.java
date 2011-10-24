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

import net.objectzoo.delegates.Action0;
import net.objectzoo.delegates.Action0Async;
import net.objectzoo.delegates.ActionAsyncCallback;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.adapters.Action0ToAction0Async;
import net.objectzoo.delegates.impl.AsyncExecutor;

/**
 * The {@code Event0AsyncAdapter} is class that adapts an {@link Event0Delegate} to
 * {@link Event0AsyncDelegate} so that the original delegates subscribers can be invoked
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
 */
public class Event0AsyncAdapter implements Event0AsyncDelegate
{
	private final Action0Async asyncDelegate;
	
	private final Event0Delegate delegate;
	
	/**
	 * Creates a {@code Event0AsyncDistributor} using the default executor
	 * 
	 * @param delegate
	 *        the original delegate to be invoked asynchronously
	 * @throws IllegalArgumentException
	 *         if the given delegate is {@code null}
	 */
	public Event0AsyncAdapter(Event0Delegate delegate) throws IllegalArgumentException
	{
		this(delegate, null);
	}
	
	/**
	 * Creates a {@code Event0AsyncDistributor} using the given {@link Executor}
	 * 
	 * @param executor
	 *        the executor used for the asynchronous event distributions
	 * @param delegate
	 *        the original delegate to be invoked asynchronously
	 * @throws IllegalArgumentException
	 *         if the given delegate is {@code null}
	 */
	public Event0AsyncAdapter(Event0Delegate delegate, Executor executor)
		throws IllegalArgumentException
	{
		this.delegate = delegate;
		asyncDelegate = new Action0ToAction0Async(delegate, executor);
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
	 * @return the {@link ActionAsyncResult} associated with this asynchronous invocation
	 */
	@Override
	public ActionAsyncResult beginInvoke(ActionAsyncCallback callback, Object asyncState)
	{
		return asyncDelegate.beginInvoke(callback, asyncState);
	}
	
	@Override
	public void subscribe(Action0 action)
	{
		delegate.subscribe(action);
	}
	
	@Override
	public void unsubscribe(Action0 action)
	{
		delegate.unsubscribe(action);
	}
	
	@Override
	public void invoke()
	{
		delegate.invoke();
	}
}
