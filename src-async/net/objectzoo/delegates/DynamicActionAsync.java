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
 * A {@code DynamicActionAsync} is a reference to a procedure with an arbitrary number of parameters
 * and without return value that can be invoked asynchronously in another thread. With
 * {@code DynamicActionAsync} it is possible to track the status and outcome using the
 * {@link ActionAsyncResult} returned upon invocation or by defining an {@link ActionAsyncCallback}
 * for the invocation.
 * 
 * @author tilmann
 */
public interface DynamicActionAsync
{
	/**
	 * Asynchronously invoke this {@code DynamicActionAsync} with the given parameter values,
	 * callback and asyncState.
	 * 
	 * Associated with each call to {@code beginDynamicInvoke} is an {@link ActionAsyncCallback}
	 * instance returned by the call and given to the callback that can be used to track the status
	 * and outcome of the invocation.
	 * 
	 * @param callback
	 *        the {@link ActionAsyncCallback}, if given to {@code beginDynamicInvoke}, is invoked
	 *        upon completion of the {@code DynamicActionAsync}'s invocation and receives the same
	 *        {@link ActionAsyncResult} that is returned by the call to {@code beginDynamicInvoke}.
	 * @param asyncState
	 *        the asyncState is an arbitrary reference that, if given to {@code beginDynamicInvoke},
	 *        can be retrieved from this invocations {@link ActionAsyncResult#getAsyncState()}
	 * @param params
	 *        the parameter values for the invocation
	 * @return the {@link ActionAsyncResult} associated with this asynchronous invocation
	 */
	public ActionAsyncResult beginDynamicInvoke(ActionAsyncCallback callback, Object asyncState,
												Object... params);
}
