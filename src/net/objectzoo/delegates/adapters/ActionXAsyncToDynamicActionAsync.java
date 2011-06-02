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

import net.objectzoo.delegates.Action0Async;
import net.objectzoo.delegates.Action2Async;
import net.objectzoo.delegates.Action3Async;
import net.objectzoo.delegates.Action4Async;
import net.objectzoo.delegates.ActionAsync;
import net.objectzoo.delegates.ActionAsyncCallback;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.DynamicActionAsync;
import net.objectzoo.delegates.helpers.DynamicAsyncInvoker;

public class ActionXAsyncToDynamicActionAsync implements DynamicActionAsync
{
	
	private final DynamicAsyncInvoker dynamicInvoker;
	
	public ActionXAsyncToDynamicActionAsync(ActionAsync<?> action)
	{
		this(ActionAsync.class, action);
	}
	
	public ActionXAsyncToDynamicActionAsync(Action0Async action)
	{
		this(Action0Async.class, action);
	}
	
	public ActionXAsyncToDynamicActionAsync(Action2Async<?, ?> action)
	{
		this(Action2Async.class, action);
	}
	
	public ActionXAsyncToDynamicActionAsync(Action3Async<?, ?, ?> action)
	{
		this(Action3Async.class, action);
	}
	
	public ActionXAsyncToDynamicActionAsync(Action4Async<?, ?, ?, ?> action)
	{
		this(Action4Async.class, action);
	}
	
	private <T> ActionXAsyncToDynamicActionAsync(Class<T> funcType, T func)
	{
		dynamicInvoker = new DynamicAsyncInvoker(funcType, func);
	}
	
	@Override
	public ActionAsyncResult beginDynamicInvoke(ActionAsyncCallback callback, Object asyncState,
												Object... params)
	{
		
		return dynamicInvoker.beginDynamicInvoke(new ActionCallbackToFuncCallback(callback),
			asyncState, params);
	}
}
