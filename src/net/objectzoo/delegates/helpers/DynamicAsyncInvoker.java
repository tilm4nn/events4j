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

/**
 * This helper class is used to dynamically invoke the "beginInvoke" method of asynchronous actions
 * or funcs using reflection.
 * 
 * @author tilmann
 */
public class DynamicAsyncInvoker implements DynamicFuncAsync
{
	private final Object target;
	private final Method invokeMethod;
	
	/**
	 * Creates a new {@code DynamicAsyncInvoker} that invokes the the "beginInvoke" method of the
	 * target type on the given target instance.
	 * 
	 * @param <TargetType>
	 *        the type defining the invoke method
	 * @param targetType
	 *        the type defining the invoke method
	 * @param target
	 *        the target instance for the invocation
	 */
	public <TargetType> DynamicAsyncInvoker(Class<TargetType> targetType, TargetType target)
	{
		this.target = target;
		this.invokeMethod = DynamicInvoker.getInvokeMethod(targetType, "beginInvoke");
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException
	 *         if the parameter count given to this method does not match the parameter count of the
	 *         beginInvoke method defined at construction time.
	 */
	@Override
	public FuncAsyncResult<?> beginDynamicInvoke(FuncAsyncCallback<Object> callback,
												 Object asyncState, Object... params)
	{
		Object[] invokeParameters = computeAsycBeginInvokeParameters(callback, asyncState, params);
		
		return (FuncAsyncResult<?>) DynamicInvoker.invokeMethod(invokeMethod, target,
			invokeParameters);
	}
	
	/**
	 * Computes the parameter array to be given to the invocation of the "beginInvoke" method
	 * 
	 * @param callback
	 *        the callback parameter of the method
	 * @param asyncState
	 *        the asyncState parameter of the method
	 * @param params
	 *        the func parameters of the method
	 * @return an array containing all parameters as accepted by the "beginInvoke" method
	 */
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
