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

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.objectzoo.delegates.Action0;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.FunctionAsyncResult;
import net.objectzoo.delegates.FunctionAsync;

/**
 * This is a helper class that is used for the asynchronous invocation of actions and funcs.
 * 
 * An {@link Executor} is used for the asynchronous invocations. The {@code Executor} to be used is
 * determined with the following steps.
 * 
 * 1. The {@code Executor} given at construction time is used.
 * 
 * 2. The default {@code Executor} that has been set is used.
 * 
 * 3. A new default {@code Executor} is created and used.
 * 
 * The default {@code Executor} automatically created by this {@code AsyncExecutor} is a
 * {@link ThreadPoolExecutor} with at most one single @ Thread} working at the asynchronous
 * operations and an unbounded {@link LinkedBlockingQueue} as backend storage. This guarantees that
 * all asynchronous invocation are performed one after each other in the order that they have been
 * queued and bears the risk of a {@link OutOfMemoryError} if permanently new asynchronous
 * invocations are queued faster than they can be completed by a single {@code Thread}.
 * 
 * @author tilmann
 */
public class AsyncExecutor
{
	private final Executor executor;
	private static volatile Executor defaultExecutor;
	
	/**
	 * Creates a new {@code AsyncExecutor} that uses the default {@link Executor} for asynchronous
	 * exection. The default {@link Executor} can be set using the
	 * {@link #setDefaultExecutor(Executor)} property.
	 */
	public AsyncExecutor()
	{
		this(null);
	}
	
	/**
	 * Creates a new {@code AsyncExecutor} that uses the given {@link Executor} for asynchronous
	 * execution. If {@code null} is given the default {@link Executor} is used.
	 * 
	 * @param executor
	 *        the {@link Executor} used for asynchronous execution
	 */
	public AsyncExecutor(Executor executor)
	{
		this.executor = executor;
	}
	
	private static Executor createDefaultExecutor()
	{
		final ThreadGroup threadGroup = new ThreadGroup(AsyncExecutor.class.getName());
		
		ThreadFactory threadFactory = new ThreadFactory()
		{
			@Override
			public Thread newThread(Runnable r)
			{
				Thread thread = new Thread(threadGroup, r);
				thread.setDaemon(true);
				return thread;
			}
		};
		
		return new ThreadPoolExecutor(0, 1, 1, TimeUnit.MINUTES,
			new LinkedBlockingQueue<Runnable>(), threadFactory);
	}
	
	/**
	 * Retrieves the default {@link Executor} creating a new one of none has been set or created
	 * before.
	 * 
	 * @return the default {@link Executor}
	 */
	public static Executor getDefaultExecutor()
	{
		// Implementation uses correct double checked singleton for JAVA 5.0 and above.
		if (defaultExecutor == null)
		{
			synchronized (AsyncExecutor.class)
			{
				if (defaultExecutor == null)
				{
					defaultExecutor = createDefaultExecutor();
				}
			}
		}
		return defaultExecutor;
	}
	
	/**
	 * Sets the default {@link Executor}
	 * 
	 * @param defaultExecutor
	 *        the new default {@link Executor}
	 */
	public static void setDefaultExecutor(Executor defaultExecutor)
	{
		synchronized (AsyncExecutor.class)
		{
			AsyncExecutor.defaultExecutor = defaultExecutor;
		}
	}
	
	private Executor getExecutor()
	{
		if (executor != null)
		{
			return executor;
		}
		return getDefaultExecutor();
	}
	
	/**
	 * Executes the given {@link Callable} asynchronously, creates a {@link FunctionAsyncResult} and
	 * takes care of calling the given {@link FuncAsyncCallback} as required by the specification of
	 * {@link FunctionAsync#beginApply(FuncAsyncCallback, Object, Object)}.
	 * 
	 * @param <R>
	 *        the return value type of the {@code Callable}
	 * @param callableSupplier
	 *        the Supplier to be called to carry out the invocation and to obtain the return value
	 * @param callback
	 *        the callback to be called when the asynchronous invocation has finished
	 * @param asyncState
	 *        the asynchronous state object to be used in the asynchronous result
	 * @return the asynchronous result object for the invocation
	 */
	public <R> FunctionAsyncResult<R> execute(Supplier<R> callableSupplier,
										  Consumer<? super FunctionAsyncResult<R>> callback,
										  Object asyncState)
	{
		Objects.requireNonNull(callableSupplier);
		
		Callable<R> callable = () -> {
			return callableSupplier.get();
		};
		
		return execute(callback, asyncState, callable);
	}
	
	public ActionAsyncResult execute(Action0 callableAction0, Consumer<ActionAsyncResult> callback,
									 Object asyncState)
	{
		Objects.requireNonNull(callableAction0);
		
		Callable<Object> callable = () -> {
			callableAction0.start();
			return null;
		};
		
		return execute(callback, asyncState, callable);
	}
	
	private <R> FunctionAsyncResult<R> execute(Consumer<? super FunctionAsyncResult<R>> callback,
										   Object asyncState, Callable<R> callable)
	{
		AsyncFutureTask<R> futureTask = new AsyncFutureTask<R>(callable, callback, asyncState);
		getExecutor().execute(futureTask);
		return futureTask;
	}
}
