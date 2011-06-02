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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.objectzoo.delegates.DynamicFunc;

public class DynamicInvoker implements DynamicFunc
{
	private final Object target;
	
	private final Method invokeMethod;
	
	public <T> DynamicInvoker(Class<T> targetType, T target)
	{
		this.target = target;
		this.invokeMethod = getInvokeMethod(targetType, "invoke");
	}
	
	public Object dynamicInvoke(Object... params)
	{
		return invokeMethod(invokeMethod, target, params);
	}
	
	static Object invokeMethod(Method method, Object target, Object[] params)
	{
		try
		{
			return method.invoke(target, params);
		}
		catch (InvocationTargetException e)
		{
			Throwable cause = e.getCause();
			if (cause instanceof RuntimeException)
			{
				throw ((RuntimeException) cause);
			}
			else
			{
				throw new IllegalStateException(
					"This DynamicInvoker cannot rethrow an exception thrown by the method "
						+ method.getName() + " of type " + target.getClass().getName(), e);
			}
		}
		catch (IllegalAccessException e)
		{
			throw new IllegalStateException("This DynamicInvoker cannot access the method "
				+ method.getName() + " of type " + target.getClass().getName(), e);
		}
	}
	
	static Method getInvokeMethod(Class<?> targetType, String methodName)
	{
		
		for (Method method : targetType.getMethods())
		{
			if (methodName.equals(method.getName()))
			{
				method.setAccessible(true);
				return method;
			}
		}
		
		throw new IllegalArgumentException("The given type " + targetType.getName()
			+ " does not have an " + methodName + " method.");
	}
	
}
