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

import net.objectzoo.delegates.ActionAsync;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.adapters.ActionToActionAsync;
import net.objectzoo.delegates.impl.AsyncExecutor;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * The {@code EventAsyncAdapter} is class that adapts an {@link EventDelegate} to
 * {@link EventAsyncDelegate} so that the original delegates subscribers can be invoked
 * asynchronously.
 * <p>
 * All asynchronous event distributions are executed in another thread.
 * <p>
 * The {@link Executor} to use for the asynchronous event distributions can be chosen during
 * creation of this helper. If no explicit executor is given the a default executor is used. The
 * default executor can be set using the {@link AsyncExecutor#setDefaultExecutor(Executor)} property
 * or is created automatically by the {@link AsyncExecutor}.
 *
 * @param <T> The type of the information parameter the event provides
 * @author tilmann
 */
public class EventAsyncAdapter<T> implements EventAsyncDelegate<T>
{
    private final ActionAsync<T> asyncDelegate;

    private final EventDelegate<T> delegate;

    /**
     * Creates a {@code EventAsyncDistributor} using the default executor
     *
     * @param delegate the original delegate to be invoked asynchronously
     * @throws IllegalArgumentException if the given delegate is {@code null}
     */
    public EventAsyncAdapter(EventDelegate<T> delegate) throws IllegalArgumentException
    {
        this(delegate, null);
    }

    /**
     * Creates a {@code EventAsyncDistributor} using the given {@link Executor}
     *
     * @param executor the executor used for the asynchronous event distributions
     * @param delegate the original delegate to be invoked asynchronously
     * @throws IllegalArgumentException if the given delegate is {@code null}
     */
    public EventAsyncAdapter(EventDelegate<T> delegate, Executor executor)
        throws IllegalArgumentException
    {
        this.delegate = delegate;
        asyncDelegate = new ActionToActionAsync<>(delegate, executor);
    }

    /**
     * This {@code beginInvoke} implementation invokes all event subscribers in another thread in
     * the order they have been subscribed.
     *
     * @param callback   the {@link Consumer}, if given to {@code beginInvoke}, is invoked upon
     *                   completion of invocation of all event subscribers and receives the same
     *                   {@link ActionAsyncResult} that is returned by the call to {@code beginInvoke}.
     * @param asyncState the asyncState is an arbitrary reference that, if given to {@code beginInvoke}, can be
     *                   retrieved from this invocations {@link ActionAsyncResult#getAsyncState()}
     * @param parameter  the parameter to invoke the subscribers with
     * @return the {@link ActionAsyncResult} associated with this asynchronous invocation
     */
    @Override
    public ActionAsyncResult beginAccept(Consumer<ActionAsyncResult> callback, Object asyncState,
                                         T parameter)
    {
        return asyncDelegate.beginAccept(callback, asyncState, parameter);
    }

    @Override
    public void accept(T parameter)
    {
        delegate.accept(parameter);
    }

    @Override
    public void subscribe(Consumer<? super T> action)
    {
        delegate.subscribe(action);
    }

    @Override
    public void unsubscribe(Consumer<? super T> action)
    {
        delegate.unsubscribe(action);
    }
}
