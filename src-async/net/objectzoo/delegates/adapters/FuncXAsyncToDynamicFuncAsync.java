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

/**
 * This adapter converts a given {@code FuncXAsync} to a {@link DynamicFuncAsync}.
 * 
 * The calls to the
 * {@link DynamicFuncAsync#beginDynamicInvoke(FuncAsyncCallback, Object, Object...)} method are
 * forwarded to the original func's {@code beginInvoke} method. If a call to
 * {@code beginDynamicInvoke} has the wrong argument count regarding the original func an
 * {@link IllegalArgumentException} is thrown.
 * 
 * @author tilmann
 */
public class FuncXAsyncToDynamicFuncAsync implements DynamicFuncAsync
{
	private final DynamicAsyncInvoker dynamicInvoker;
	
	/**
	 * Convert the given {@link FuncAsync} to the interface {@link DynamicFuncAsync}
	 * 
	 * @param func
	 *        the func to be converted
	 */
	public FuncXAsyncToDynamicFuncAsync(FuncAsync<?, ?> func)
	{
		this(FuncAsync.class, func);
	}
	
	/**
	 * Convert the given {@link Func0Async} to the interface {@link DynamicFuncAsync}
	 * 
	 * @param func
	 *        the func to be converted
	 */
	public FuncXAsyncToDynamicFuncAsync(Func0Async<?> func)
	{
		this(Func0Async.class, func);
	}
	
	/**
	 * Convert the given {@link Func2Async} to the interface {@link DynamicFuncAsync}
	 * 
	 * @param func
	 *        the func to be converted
	 */
	public FuncXAsyncToDynamicFuncAsync(Func2Async<?, ?, ?> func)
	{
		this(Func2Async.class, func);
	}
	
	/**
	 * Convert the given {@link Func3Async} to the interface {@link DynamicFuncAsync}
	 * 
	 * @param func
	 *        the func to be converted
	 */
	public FuncXAsyncToDynamicFuncAsync(Func3Async<?, ?, ?, ?> func)
	{
		this(Func3Async.class, func);
	}
	
	/**
	 * Convert the given {@link Func4Async} to the interface {@link DynamicFuncAsync}
	 * 
	 * @param func
	 *        the func to be converted
	 */
	public FuncXAsyncToDynamicFuncAsync(Func4Async<?, ?, ?, ?, ?> func)
	{
		this(Func4Async.class, func);
	}
	
	/**
	 * Convert the given {@code FuncXAsync} to the interface {@link DynamicFuncAsync}
	 * 
	 * @param <FuncType>
	 *        the type of func to be converted
	 * @param funcType
	 *        the type of func to be converted
	 * @param func
	 *        the func to be converted
	 */
	private <FuncType> FuncXAsyncToDynamicFuncAsync(Class<FuncType> funcType, FuncType func)
	{
		dynamicInvoker = new DynamicAsyncInvoker(funcType, func);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException
	 *         if the parameter count given to this method does not match the parameter count of the
	 *         underlying original func.
	 */
	@Override
	public FuncAsyncResult<?> beginDynamicInvoke(FuncAsyncCallback<Object> callback,
												 Object asyncState, Object... params)
	{
		
		return dynamicInvoker.beginDynamicInvoke(callback, asyncState, params);
	}
}
