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

import net.objectzoo.delegates.DynamicFunc;
import net.objectzoo.delegates.Func;
import net.objectzoo.delegates.Func0;
import net.objectzoo.delegates.Func2;
import net.objectzoo.delegates.Func3;
import net.objectzoo.delegates.Func4;
import net.objectzoo.delegates.helpers.DynamicInvoker;

/**
 * This adapter converts a given {@code FuncX} to a {@link DynamicFunc}.
 * 
 * The calls to the {@link DynamicFunc#dynamicInvoke(Object...)} method are forwarded to the
 * original func's {@code invoke} method. If a call to {@code dynamicInvoke} has the wrong argument
 * count regarding the original func an {@link IllegalArgumentException} is thrown.
 * 
 * @author tilmann
 */
public class FuncXToDynamicFunc implements DynamicFunc
{
	private final DynamicInvoker dynamicInvoker;
	
	/**
	 * Convert the given {@link Func} to the interface {@link DynamicFunc}
	 * 
	 * @param func
	 *        the func to be converted
	 */
	public FuncXToDynamicFunc(Func<?, ?> func)
	{
		this(Func.class, func);
	}
	
	/**
	 * Convert the given {@link Func0} to the interface {@link DynamicFunc}
	 * 
	 * @param func
	 *        the func to be converted
	 */
	public FuncXToDynamicFunc(Func0<?> func)
	{
		this(Func0.class, func);
	}
	
	/**
	 * Convert the given {@link Func2} to the interface {@link DynamicFunc}
	 * 
	 * @param func
	 *        the func to be converted
	 */
	public FuncXToDynamicFunc(Func2<?, ?, ?> func)
	{
		this(Func2.class, func);
	}
	
	/**
	 * Convert the given {@link Func3} to the interface {@link DynamicFunc}
	 * 
	 * @param func
	 *        the func to be converted
	 */
	public FuncXToDynamicFunc(Func3<?, ?, ?, ?> func)
	{
		this(Func3.class, func);
	}
	
	/**
	 * Convert the given {@link Func4} to the interface {@link DynamicFunc}
	 * 
	 * @param func
	 *        the func to be converted
	 */
	public FuncXToDynamicFunc(Func4<?, ?, ?, ?, ?> func)
	{
		this(Func4.class, func);
	}
	
	/**
	 * Convert the given {@code FuncX} to the interface {@link DynamicFunc}
	 * 
	 * @param <FuncType>
	 *        the type of func to be converted
	 * @param funcType
	 *        the type of func to be converted
	 * @param func
	 *        the func to be converted
	 */
	private <FuncType> FuncXToDynamicFunc(Class<FuncType> funcType, FuncType func)
	{
		dynamicInvoker = new DynamicInvoker(funcType, func);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException
	 *         if the parameter count given to this method does not match the parameter count of the
	 *         underlying original func.
	 */
	@Override
	public Object dynamicInvoke(Object... params)
	{
		return dynamicInvoker.dynamicInvoke(params);
	}
	
}
