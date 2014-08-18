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
package net.objectzoo.delegates;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

import net.objectzoo.delegates.adapters.Action3ToAction3Async;

/**
 * An {@code Action3Async} is a reference to a procedure without return value that can be invoked
 * asynchronously in another thread. With {@code Action3Async} it is possible to track the status
 * and outcome using the {@link ActionAsyncResult} returned upon invocation or by defining an
 * {@link ActionAsyncCallback} for the invocation.
 * 
 * @author tilmann
 * 
 * @param <T1>
 *        The type of the {@code Action3Async}'s first parameter
 * @param <T2>
 *        The type of the {@code Action3Async}'s second parameter
 * @param <T3>
 *        The type of the {@code Action3Async}'s third parameter
 */
public interface Action3Async<T1, T2, T3>
{
	/**
	 * Asynchronously invoke this {@code Action3Async} with the given parameter value, callback and
	 * asyncState.
	 * 
	 * Associated with each call to {@code beginInvoke} is an {@link ActionAsyncCallback} instance
	 * returned by the call and given to the callback that can be used to track the status and
	 * outcome of the invocation.
	 * 
	 * @param callback
	 *        the {@link ActionAsyncCallback}, if given to {@code beginInvoke}, is invoked upon
	 *        completion of the {@code Action3Async}'s invocation and receives the same
	 *        {@link ActionAsyncResult} that is returned by the call to {@code beginInvoke}.
	 * @param asyncState
	 *        the asyncState is an arbitrary reference that, if given to {@code beginInvoke}, can be
	 *        retrieved from this invocations {@link ActionAsyncResult#getAsyncState()}
	 * @param parameter1
	 *        the first parameter's value for the invocation
	 * @param parameter2
	 *        the second parameter's value for the invocation
	 * @param parameter3
	 *        the third parameter's value for the invocation
	 * @return the {@link ActionAsyncResult} associated with this asynchronous invocation
	 */
	public ActionAsyncResult beginAccept(Consumer<ActionAsyncResult> callback, Object asyncState,
										 T1 parameter1, T2 parameter2, T3 parameter3);
	
	/**
	 * Converts the given {@link Action3} to the interface {@code Action3Async} using the default
	 * executor. Conversion is done using the adapter {@link Action3ToAction3Async}.
	 * 
	 * @param action
	 *        the action to be converted
	 */
	public static <T1, T2, T3> Action3Async<T1, T2, T3> from(Action3<T1, T2, T3> action)
	{
		return new Action3ToAction3Async<>(action);
	}
	
	/**
	 * Converts the given {@link Action3} to the interface {@code Action3Async} and executes
	 * asynchronous call using the given {@link Executor}. Conversion is done using the adapter
	 * {@link Action3ToAction3Async}.
	 * 
	 * @param action
	 *        the action to be converted
	 * @param executor
	 *        the executor used for the asynchronous calls
	 */
	public static <T1, T2, T3> Action3Async<T1, T2, T3> from(Action3<T1, T2, T3> action,
															 Executor executor)
	{
		return new Action3ToAction3Async<>(action, executor);
	}
}
