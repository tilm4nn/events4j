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

public class FuncXToDynamicFunc implements DynamicFunc
{
	
	private final DynamicInvoker dynamicInvoker;
	
	public FuncXToDynamicFunc(Func<?, ?> func)
	{
		this(Func.class, func);
	}
	
	public FuncXToDynamicFunc(Func0<?> func)
	{
		this(Func0.class, func);
	}
	
	public FuncXToDynamicFunc(Func2<?, ?, ?> func)
	{
		this(Func2.class, func);
	}
	
	public FuncXToDynamicFunc(Func3<?, ?, ?, ?> func)
	{
		this(Func3.class, func);
	}
	
	public FuncXToDynamicFunc(Func4<?, ?, ?, ?, ?> func)
	{
		this(Func4.class, func);
	}
	
	private <T> FuncXToDynamicFunc(Class<T> funcType, T func)
	{
		dynamicInvoker = new DynamicInvoker(funcType, func);
	}
	
	@Override
	public Object dynamicInvoke(Object... params)
	{
		return dynamicInvoker.dynamicInvoke(params);
	}
	
}
