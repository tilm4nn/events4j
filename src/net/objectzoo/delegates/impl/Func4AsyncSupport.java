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
package net.objectzoo.delegates.impl;

import java.util.concurrent.Executor;

import net.objectzoo.delegates.Func4;
import net.objectzoo.delegates.Func4Async;
import net.objectzoo.delegates.FuncAsyncCallback;
import net.objectzoo.delegates.FuncAsyncResult;
import net.objectzoo.delegates.adapters.Func4ToFunc4Async;

public abstract class Func4AsyncSupport<T1, T2, T3, T4, R> implements Func4<T1, T2, T3, T4, R>,
	Func4Async<T1, T2, T3, T4, R>
{
	
	public Func4AsyncSupport()
	{
		this(null);
	}
	
	public Func4AsyncSupport(Executor executor)
	{
		asyncDelegate = new Func4ToFunc4Async<T1, T2, T3, T4, R>(this, executor);
	}
	
	private final Func4Async<T1, T2, T3, T4, R> asyncDelegate;
	
	@Override
	public abstract R invoke(T1 parameter1, T2 parameter2, T3 parameter3, T4 parameter4);
	
	@Override
	public FuncAsyncResult<R> beginInvoke(FuncAsyncCallback<? super R> callback, Object asyncState,
										  T1 parameter1, T2 parameter2, T3 parameter3, T4 parameter4)
	{
		return asyncDelegate.beginInvoke(callback, asyncState, parameter1, parameter2, parameter3,
			parameter4);
	}
}
