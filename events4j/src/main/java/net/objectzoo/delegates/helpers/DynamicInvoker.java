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

/**
 * This helper class is used to dynamically invoke the "invoke" method of actions or funcs using
 * reflection.
 * 
 * @author tilmann
 */
public class DynamicInvoker implements DynamicFunc
{
	private final Object target;
	private final Method invokeMethod;
	
	/**
	 * Creates a new {@code DynamicInvoker} that invokes the the "invoke" method of the target type
	 * on the given target instance.
	 * 
	 * @param <TargetType>
	 *        the type defining the invoke method
	 * @param targetType
	 *        the type defining the invoke method
	 * @param target
	 *        the target instance for the invocation
	 */
	public <TargetType> DynamicInvoker(Class<TargetType> targetType, TargetType target)
	{
		this.target = target;
		this.invokeMethod = getInvokeMethod(targetType, "invoke");
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException
	 *         if the parameter count given to this method does not match the parameter count of the
	 *         invoke method defined at construction time.
	 */
	@Override
	public Object dynamicInvoke(Object... params)
	{
		return invokeMethod(invokeMethod, target, params);
	}
	
	/**
	 * Invokes the method method on the target instance with the given parameters
	 * 
	 * @param method
	 *        the method to be invoked
	 * @param target
	 *        the target instance for the invocation
	 * @param params
	 *        the parameters for the invocation
	 * @return the return value of the invocation
	 * @throws IllegalArgumentException
	 *         if the parameter count given to this method does not match the parameter count of the
	 *         method.
	 * @throws IllegalStateException
	 *         if the method throws an {@link Exception} that is not a subclass of
	 *         {@link RuntimeException} or if the current thread has no access to the method.
	 */
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
	
	/**
	 * Retrieves the first method with the given name from the given target type and sets it
	 * accessible.
	 * 
	 * @param targetType
	 *        the type to retrieve the method from
	 * @param methodName
	 *        the name of the method to retrieve
	 * @return the method retrieved
	 * @throws IllegalArgumentException
	 *         if no method with the given name can be found in the target type
	 */
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
