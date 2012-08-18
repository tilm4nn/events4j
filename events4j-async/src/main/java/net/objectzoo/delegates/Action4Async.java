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

/**
 * An {@code Action4Async} is a reference to a procedure without return value that can be invoked
 * asynchronously in another thread. With {@code Action4Async} it is possible to track the status
 * and outcome using the {@link ActionAsyncResult} returned upon invocation or by defining an
 * {@link ActionAsyncCallback} for the invocation.
 * 
 * @author tilmann
 * 
 * @param <T1>
 *        The type of the {@code Action4Async}'s first parameter
 * @param <T2>
 *        The type of the {@code Action4Async}'s second parameter
 * @param <T3>
 *        The type of the {@code Action4Async}'s third parameter
 * @param <T4>
 *        The type of the {@code Action4Async}'s fourth parameter
 */
public interface Action4Async<T1, T2, T3, T4>
{
	/**
	 * Asynchronously invoke this {@code Action4Async} with the given parameter value, callback and
	 * asyncState.
	 * 
	 * Associated with each call to {@code beginInvoke} is an {@link ActionAsyncCallback} instance
	 * returned by the call and given to the callback that can be used to track the status and
	 * outcome of the invocation.
	 * 
	 * @param callback
	 *        the {@link ActionAsyncCallback}, if given to {@code beginInvoke}, is invoked upon
	 *        completion of the {@code Action4Async}'s invocation and receives the same
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
	 * @param parameter4
	 *        the fourth parameter's value for the invocation
	 * @return the {@link ActionAsyncResult} associated with this asynchronous invocation
	 */
	public ActionAsyncResult beginInvoke(ActionAsyncCallback callback, Object asyncState,
										 T1 parameter1, T2 parameter2, T3 parameter3, T4 parameter4);
}
