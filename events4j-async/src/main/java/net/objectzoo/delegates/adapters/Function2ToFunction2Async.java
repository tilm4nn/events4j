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
import java.util.function.BiFunction;
import java.util.function.Consumer;

import net.objectzoo.delegates.Function0;
import net.objectzoo.delegates.Function2;
import net.objectzoo.delegates.Function2Async;
import net.objectzoo.delegates.FunctionAsyncResult;
import net.objectzoo.delegates.impl.AsyncExecutor;

/**
 * An adapter that converts a conventional {@link Function2} or {@link BiFunction} to an
 * {@link Function2Async}.
 * 
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link Function2#apply(Object, Object)} method.
 * 
 * The {@link Executor} to use for the asynchronous invocations can be chosen during creation of
 * this adapter. If no explicit executor is given the a default executor is used. The default
 * executor can be set using the {@link AsyncExecutor#setDefaultExecutor(Executor)} property or is
 * created automatically by the {@link AsyncExecutor}.
 * 
 * @author tilmann
 * 
 * @param <T1>
 *        The type of the {@code Func2}'s first parameter
 * @param <T2>
 *        The type of the {@code Func2}'s second parameter
 * @param <R>
 *        The type of the {@code Func2}'s return value
 */
public class Function2ToFunction2Async<T1, T2, R> implements Function2Async<T1, T2, R>
{
	private final BiFunction<T1, T2, R> function;
	private final AsyncExecutor asyncExecutor;
	
	/**
	 * Converts the given {@link Function2} to the interface {@link Function2Async} using the
	 * default executor
	 * 
	 * @param function
	 *        the function to be converted
	 */
	public Function2ToFunction2Async(BiFunction<T1, T2, R> function)
	{
		this(function, null);
	}
	
	/**
	 * Converts the given {@link Function2} to the interface {@link Function2Async} and executes
	 * asynchronous call using the given {@link Executor}
	 * 
	 * @param function
	 *        the function to be converted
	 * @param executor
	 *        the executor used for the asynchronous calls
	 */
	public Function2ToFunction2Async(BiFunction<T1, T2, R> function, Executor executor)
	{
		this.function = function;
		this.asyncExecutor = new AsyncExecutor(executor);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public FunctionAsyncResult<R> beginApply(Consumer<? super FunctionAsyncResult<R>> callback,
											 Object asyncState, final T1 parameter1,
											 final T2 parameter2)
	{
		Function0<R> callable = Function2.bindParameters(function, parameter1, parameter2);
		return asyncExecutor.execute(callable, callback, asyncState);
	}
}
