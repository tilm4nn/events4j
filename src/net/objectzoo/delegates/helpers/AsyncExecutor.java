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
package net.objectzoo.delegates.helpers;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import net.objectzoo.delegates.FuncAsyncCallback;
import net.objectzoo.delegates.FuncAsyncResult;

public class AsyncExecutor
{
	public AsyncExecutor()
	{
		this(null);
	}
	
	public AsyncExecutor(Executor executor)
	{
		this.executor = executor;
	}
	
	private final Executor executor;
	
	private static Executor defaultExecutor;
	
	static Executor createDefaultExecutor()
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
	
	public static Executor getDefaultExecutor()
	{
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
	
	public static void setDefaultExecutor(Executor defaultExecutor)
	{
		synchronized (AsyncExecutor.class)
		{
			AsyncExecutor.defaultExecutor = defaultExecutor;
		}
	}
	
	Executor getExecutor()
	{
		if (executor != null)
		{
			return executor;
		}
		return getDefaultExecutor();
	}
	
	public <R> FuncAsyncResult<R> execute(Callable<R> callable,
										  FuncAsyncCallback<? super R> callback, Object asyncState)
	{
		if (callable == null)
		{
			throw new IllegalArgumentException("callable=null");
		}
		
		@SuppressWarnings("unchecked")
		AsyncFutureTask<R> futureTask = new AsyncFutureTask<R>(callable,
			(FuncAsyncCallback<R>) callback, asyncState);
		
		getExecutor().execute(futureTask);
		
		return futureTask;
	}
}
