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
package net.objectzoo.delegates.adapters;

import net.objectzoo.delegates.ActionAsyncCallback;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.FuncAsyncCallback;
import net.objectzoo.delegates.FuncAsyncResult;
import net.objectzoo.delegates.impl.AsyncExecutor;

/**
 * This adapter converts an {@link ActionAsyncCallback} to a {@link FuncAsyncCallback}. This is a
 * utility that allows to the {@link AsyncExecutor} to be shared for action and func execution.
 * 
 * @author tilmann
 */
public class ActionCallbackToFuncCallback implements FuncAsyncCallback<Object>
{
	private final ActionAsyncCallback actionCallback;
	
	/**
	 * Converts the given {@link ActionAsyncCallback} to {@link FuncAsyncCallback}
	 * 
	 * @param actionCallback
	 *        the callback to be converted
	 */
	public ActionCallbackToFuncCallback(ActionAsyncCallback actionCallback)
	{
		this.actionCallback = actionCallback;
	}
	
	/**
	 * This implementation just forwards the given {@link FuncAsyncResult} that is a subclass of
	 * {@link ActionAsyncResult} to the {@link ActionAsyncCallback} given at creation time.
	 * 
	 * @param result
	 *        the asynchronous result to be forwarded
	 */
	@Override
	public void invocationFinished(FuncAsyncResult<Object> result)
	{
		actionCallback.invocationFinished(result);
	}
}
