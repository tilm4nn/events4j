/*
 * The MIT License
 *
 * Copyright (C) 2014 Tilmann Kuhn
 *
 * http://www.object-zoo.net
 * mailto:events4j@object-zoo.net
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.objectzoo.delegates;

import net.objectzoo.delegates.adapters.Action0ToAction0Async;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * An {@code Action0Async} is a reference to a procedure without return value that can be invoked
 * asynchronously in another thread. With {@code Action0Async} it is possible to track the status
 * and outcome using the {@link ActionAsyncResult} returned upon invocation or by defining a
 * {@link Consumer} as callback for the invocation.
 *
 * @author tilmann
 */
public interface Action0Async
{
    /**
     * Converts the given {@link Action0} to the interface {@code Action0Async} using the default
     * executor. Conversion is done using the adapter {@link Action0ToAction0Async}.
     *
     * @param action the action to be converted
     */
    public static Action0Async from(Action0 action)
    {
        return new Action0ToAction0Async(action);
    }

    /**
     * Converts the given {@link Action0} to the interface {@code Action0Async} and executes
     * asynchronous call using the given {@link Executor}. Conversion is done using the adapter
     * {@link Action0ToAction0Async}.
     *
     * @param action   the action to be converted
     * @param executor the executor used for the asynchronous calls
     */
    public static Action0Async from(Action0 action, Executor executor)
    {
        return new Action0ToAction0Async(action, executor);
    }

    /**
     * Asynchronously invoke this {@code Action0Async} with the given parameter value, callback and
     * asyncState.
     * <p>
     * Associated with each call to {@code beginInvoke} is an {@link ActionAsyncResult} instance
     * returned by the call and given to the callback that can be used to track the status and
     * outcome of the invocation.
     *
     * @param callback   the {@link Consumer}, if given to {@code beginInvoke}, is invoked upon
     *                   completion of the {@code Action0Async}'s invocation and receives the same
     *                   {@link ActionAsyncResult} that is returned by the call to {@code beginInvoke}.
     * @param asyncState the asyncState is an arbitrary reference that, if given to {@code beginInvoke}, can be
     *                   retrieved from this invocations {@link ActionAsyncResult#getAsyncState()}
     * @return the {@link ActionAsyncResult} associated with this asynchronous invocation
     */
    public ActionAsyncResult beginStart(Consumer<ActionAsyncResult> callback, Object asyncState);
}
