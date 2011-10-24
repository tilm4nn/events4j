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

import net.objectzoo.delegates.Action4Async;
import net.objectzoo.delegates.ActionAsyncCallback;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.Func4Async;

/**
 * This adapter converts a {@link Func4Async} to an {@link Action4Async}.
 * 
 * @author tilmann
 * 
 * @param <T1>
 *        The type of the {@code Func4Async}'s/{@code Action4Async}'s first parameter
 * @param <T2>
 *        The type of the {@code Func4Async}'s/{@code Action4Async}'s second parameter
 * @param <T3>
 *        The type of the {@code Func4Async}'s/{@code Action4Async}'s third parameter
 * @param <T4>
 *        The type of the {@code Func4Async}'s/{@code Action4Async}'s fourth parameter
 */
public class Func4AsyncToAction4Async<T1, T2, T3, T4> implements Action4Async<T1, T2, T3, T4>
{
	private final Func4Async<T1, T2, T3, T4, ?> func;
	
	/**
	 * Convert the given {@link Func4Async} to the interface {@link Action4Async}
	 * 
	 * @param func
	 *        the func to be converted
	 */
	public Func4AsyncToAction4Async(Func4Async<T1, T2, T3, T4, ?> func)
	{
		this.func = func;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionAsyncResult beginInvoke(ActionAsyncCallback callback, Object asyncState,
										 T1 parameter1, T2 parameter2, T3 parameter3, T4 parameter4)
	{
		
		return func.beginInvoke(new ActionCallbackToFuncCallback(callback), asyncState, parameter1,
			parameter2, parameter3, parameter4);
	}
}
