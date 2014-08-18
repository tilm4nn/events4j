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
import java.util.function.Supplier;

import net.objectzoo.delegates.Function0;
import net.objectzoo.delegates.Function0Async;
import net.objectzoo.delegates.FunctionAsyncResult;
import net.objectzoo.delegates.impl.AsyncExecutor;

/**
 * An adapter that converts a conventional {@link Function0} or {@link Supplier} to an
 * {@link Function0Async}.
 * 
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link Function0#apply()} method.
 * 
 * The {@link Executor} to use for the asynchronous invocations can be chosen during creation of
 * this adapter. If no explicit executor is given the a default executor is used. The default
 * executor can be set using the {@link AsyncExecutor#setDefaultExecutor(Executor)} property or is
 * created automatically by the {@link AsyncExecutor}.
 * 
 * @author tilmann
 * 
 * @param <R>
 *        The type of the {@code Func0}'s return value
 */
public class Function0ToFunction0Async<R> implements Function0Async<R>
{
	private final Supplier<R> function;
	private final AsyncExecutor asyncExecutor;
	
	/**
	 * Converts the given {@link Function0} to the interface {@link Function0Async} using the
	 * default executor
	 * 
	 * @param function
	 *        the function to be converted
	 */
	public Function0ToFunction0Async(Supplier<R> function)
	{
		this(function, null);
	}
	
	/**
	 * Converts the given {@link Function0} to the interface {@link Function0Async} and executes
	 * asynchronous call using the given {@link Executor}
	 * 
	 * @param function
	 *        the function to be converted
	 * @param executor
	 *        the executor used for the asynchronous calls
	 */
	public Function0ToFunction0Async(Supplier<R> function, Executor executor)
	{
		this.function = function;
		this.asyncExecutor = new AsyncExecutor(executor);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public FunctionAsyncResult<R> beginGet(Consumer<? super FunctionAsyncResult<R>> callback,
										   Object asyncState)
	{
		return asyncExecutor.execute(function, callback, asyncState);
	}
}
