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

import net.objectzoo.delegates.Action2;
import net.objectzoo.delegates.Func2;

/**
 * This adapter converts a {@link Func2} to an {@link Action2}.
 * 
 * @author tilmann
 * 
 * @param <T1>
 *        The type of the {@code Func2}'s/{@code Action2}'s first parameter
 * @param <T2>
 *        The type of the {@code Func2}'s/{@code Action2}'s second parameter
 */
public class Func2ToAction2<T1, T2> implements Action2<T1, T2>
{
	private final Func2<T1, T2, ?> func;
	
	/**
	 * Convert the given {@link Func2} to the interface {@link Action2}
	 * 
	 * @param func
	 *        the func to be converted
	 */
	public Func2ToAction2(Func2<T1, T2, ?> func)
	{
		this.func = func;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invoke(T1 parameter1, T2 parameter2)
	{
		func.invoke(parameter1, parameter2);
	}
	
}
