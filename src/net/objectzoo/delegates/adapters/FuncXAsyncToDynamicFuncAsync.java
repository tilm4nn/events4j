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

import net.objectzoo.delegates.DynamicFuncAsync;
import net.objectzoo.delegates.Func0Async;
import net.objectzoo.delegates.Func2Async;
import net.objectzoo.delegates.Func3Async;
import net.objectzoo.delegates.Func4Async;
import net.objectzoo.delegates.FuncAsync;
import net.objectzoo.delegates.FuncAsyncCallback;
import net.objectzoo.delegates.FuncAsyncResult;
import net.objectzoo.delegates.helpers.DynamicAsyncInvoker;

public class FuncXAsyncToDynamicFuncAsync implements DynamicFuncAsync
{
	
	private final DynamicAsyncInvoker dynamicInvoker;
	
	public FuncXAsyncToDynamicFuncAsync(FuncAsync<?, ?> func)
	{
		this(FuncAsync.class, func);
	}
	
	public FuncXAsyncToDynamicFuncAsync(Func0Async<?> func)
	{
		this(Func0Async.class, func);
	}
	
	public FuncXAsyncToDynamicFuncAsync(Func2Async<?, ?, ?> func)
	{
		this(Func2Async.class, func);
	}
	
	public FuncXAsyncToDynamicFuncAsync(Func3Async<?, ?, ?, ?> func)
	{
		this(Func3Async.class, func);
	}
	
	public FuncXAsyncToDynamicFuncAsync(Func4Async<?, ?, ?, ?, ?> func)
	{
		this(Func4Async.class, func);
	}
	
	private <T> FuncXAsyncToDynamicFuncAsync(Class<T> funcType, T func)
	{
		dynamicInvoker = new DynamicAsyncInvoker(funcType, func);
	}
	
	@Override
	public FuncAsyncResult<?> beginDynamicInvoke(FuncAsyncCallback<Object> callback,
												 Object asyncState, Object... params)
	{
		
		return dynamicInvoker.beginDynamicInvoke(callback, asyncState, params);
	}
}
