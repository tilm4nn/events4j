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

import net.objectzoo.delegates.ActionAsyncCallback;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.DynamicAction;
import net.objectzoo.delegates.DynamicActionAsync;
import net.objectzoo.delegates.adapters.DynamicActionToDynamicActionAsync;
import net.objectzoo.delegates.impl.AsyncExecutor;

/**
 * The {@code DynamicEventAsyncAdapter} is class that adapts an {@link DynamicEventDelegate} to
 * {@link DynamicEventAsyncDelegate} so that the original delegates subscribers can be invoked
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
public class DynamicEventAsyncAdapter implements DynamicEventAsyncDelegate
{
	private final DynamicActionAsync asyncDelegate;
	
	private final DynamicEventDelegate delegate;
	
	/**
	 * Creates a {@code DynamicEventAsyncDistributor} using the default executor
	 * 
	 * @param delegate
	 *        the original delegate to be invoked asynchronously
	 * @throws IllegalArgumentException
	 *         if the given delegate is {@code null}
	 */
	public DynamicEventAsyncAdapter(DynamicEventDelegate delegate) throws IllegalArgumentException
	{
		this(delegate, null);
	}
	
	/**
	 * Creates a {@code DynamicEventAsyncDistributor} using the given {@link Executor}
	 * 
	 * @param executor
	 *        the executor used for the asynchronous event distributions
	 * @param delegate
	 *        the original delegate to be invoked asynchronously
	 * @throws IllegalArgumentException
	 *         if the given delegate is {@code null}
	 */
	public DynamicEventAsyncAdapter(DynamicEventDelegate delegate, Executor executor)
		throws IllegalArgumentException
	{
		this.delegate = delegate;
		asyncDelegate = new DynamicActionToDynamicActionAsync(delegate, executor);
	}
	
	/**
	 * This {@code beginDynamicInvoke} implementation invokes all event subscribers in another
	 * thread in the order they have been subscribed.
	 * 
	 * @param callback
	 *        the {@link ActionAsyncCallback}, if given to {@code beginDynamicInvoke}, is invoked
	 *        upon completion of invocation of all event subscribers and receives the same
	 *        {@link ActionAsyncResult} that is returned by the call to {@code beginInvoke}.
	 * @param asyncState
	 *        the asyncState is an arbitrary reference that, if given to {@code beginInvoke}, can be
	 *        retrieved from this invocations {@link ActionAsyncResult#getAsyncState()}
	 * @param params
	 *        the parameters to invoke the subscribers with
	 * @return the {@link ActionAsyncResult} associated with this asynchronous invocation
	 */
	@Override
	public ActionAsyncResult beginDynamicInvoke(ActionAsyncCallback callback, Object asyncState,
												Object... params)
	{
		return asyncDelegate.beginDynamicInvoke(callback, asyncState, params);
	}
	
	@Override
	public void dynamicInvoke(Object... params)
	{
		delegate.dynamicInvoke(params);
	}
	
	@Override
	public void subscribe(DynamicAction action)
	{
		delegate.subscribe(action);
	}
	
	@Override
	public void unsubscribe(DynamicAction action)
	{
		delegate.unsubscribe(action);
	}
}
