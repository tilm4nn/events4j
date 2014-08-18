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

import java.util.concurrent.Executor;
import java.util.function.Consumer;

import net.objectzoo.delegates.FunctionAsyncResult;
import net.objectzoo.delegates.Function0;
import net.objectzoo.delegates.Function4;
import net.objectzoo.delegates.Function4Async;
import net.objectzoo.delegates.impl.AsyncExecutor;

/**
 * An adapter that converts a conventional {@link Function4} to an {@link Function4Async}.
 * 
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link Function4#apply(Object, Object, Object, Object)} method.
 * 
 * The {@link Executor} to use for the asynchronous invocations can be chosen during creation of
 * this adapter. If no explicit executor is given the a default executor is used. The default
 * executor can be set using the {@link AsyncExecutor#setDefaultExecutor(Executor)} property or is
 * created automatically by the {@link AsyncExecutor}.
 * 
 * @author tilmann
 * 
 * @param <T1>
 *        The type of the {@code Func4}'s first parameter
 * @param <T2>
 *        The type of the {@code Func4}'s second parameter
 * @param <T3>
 *        The type of the {@code Func4}'s third parameter
 * @param <T4>
 *        The type of the {@code Func4}'s third parameter
 * @param <R>
 *        The type of the {@code Func4}'s return value
 */
public class Function4ToFunction4Async<T1, T2, T3, T4, R> implements
	Function4Async<T1, T2, T3, T4, R>
{
	private final Function4<T1, T2, T3, T4, R> function;
	private final AsyncExecutor asyncExecutor;
	
	/**
	 * Converts the given {@link Function4} to the interface {@link Function4Async} using the
	 * default executor
	 * 
	 * @param function
	 *        the function to be converted
	 */
	public Function4ToFunction4Async(Function4<T1, T2, T3, T4, R> function)
	{
		this(function, null);
	}
	
	/**
	 * Converts the given {@link Function4} to the interface {@link Function4Async} and executes
	 * asynchronous call using the given {@link Executor}
	 * 
	 * @param function
	 *        the function to be converted
	 * @param executor
	 *        the executor used for the asynchronous calls
	 */
	public Function4ToFunction4Async(Function4<T1, T2, T3, T4, R> function, Executor executor)
	{
		this.function = function;
		this.asyncExecutor = new AsyncExecutor(executor);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public FunctionAsyncResult<R> beginApply(Consumer<? super FunctionAsyncResult<R>> callback,
										 Object asyncState, T1 parameter1, T2 parameter2,
										 T3 parameter3, T4 parameter4)
	{
		Function0<R> callable = Function4.bindParameters(function, parameter1, parameter2,
			parameter3, parameter4);
		return asyncExecutor.execute(callable, callback, asyncState);
	}
}
