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
package net.objectzoo.delegates.helpers;

import java.lang.reflect.Method;

import net.objectzoo.delegates.DynamicFuncAsync;
import net.objectzoo.delegates.FuncAsyncCallback;
import net.objectzoo.delegates.FuncAsyncResult;

public class DynamicAsyncInvoker implements DynamicFuncAsync
{
	
	private final Object target;
	private final Method invokeMethod;
	
	public <T> DynamicAsyncInvoker(Class<T> targetType, T target)
	{
		this.target = target;
		this.invokeMethod = DynamicInvoker.getInvokeMethod(targetType, "beginInvoke");
	}
	
	public FuncAsyncResult<?> beginDynamicInvoke(FuncAsyncCallback<Object> callback,
												 Object asyncState, Object... params)
	{
		Object[] invokeParameters = computeAsycBeginInvokeParameters(callback, asyncState, params);
		
		return (FuncAsyncResult<?>) DynamicInvoker.invokeMethod(invokeMethod, target,
			invokeParameters);
	}
	
	static Object[] computeAsycBeginInvokeParameters(Object callback, Object asyncState,
													 Object... params)
	{
		if (params == null) params = new Object[0];
		Object[] invokeParameters = new Object[2 + params.length];
		invokeParameters[0] = callback;
		invokeParameters[1] = asyncState;
		for (int i = 0; i < params.length; i++)
		{
			invokeParameters[i + 2] = params[i];
		}
		return invokeParameters;
	}
}
