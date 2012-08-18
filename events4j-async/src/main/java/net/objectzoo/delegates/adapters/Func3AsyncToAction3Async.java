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

import net.objectzoo.delegates.Action3Async;
import net.objectzoo.delegates.ActionAsyncCallback;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.Func3Async;

/**
 * This adapter converts a {@link Func3Async} to an {@link Action3Async}.
 * 
 * @author tilmann
 * 
 * @param <T1>
 *        The type of the {@code Func3Async}'s/{@code Action3Async}'s first parameter
 * @param <T2>
 *        The type of the {@code Func3Async}'s/{@code Action3Async}'s second parameter
 * @param <T3>
 *        The type of the {@code Func3Async}'s/{@code Action3Async}'s third parameter
 */
public class Func3AsyncToAction3Async<T1, T2, T3> implements Action3Async<T1, T2, T3>
{
	private final Func3Async<T1, T2, T3, ?> func;
	
	/**
	 * Convert the given {@link Func3Async} to the interface {@link Action3Async}
	 * 
	 * @param func
	 *        the func to be converted
	 */
	public Func3AsyncToAction3Async(Func3Async<T1, T2, T3, ?> func)
	{
		this.func = func;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionAsyncResult beginInvoke(ActionAsyncCallback callback, Object asyncState,
										 T1 parameter1, T2 parameter2, T3 parameter3)
	{
		
		return func.beginInvoke(new ActionCallbackToFuncCallback(callback), asyncState, parameter1,
			parameter2, parameter3);
	}
}
