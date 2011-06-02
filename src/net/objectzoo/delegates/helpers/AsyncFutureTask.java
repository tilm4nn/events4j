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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.FuncAsyncCallback;
import net.objectzoo.delegates.FuncAsyncResult;

class AsyncFutureTask<R> extends FutureTask<R> implements FuncAsyncResult<R>, ActionAsyncResult
{
	
	private final Object asyncState;
	
	private final FuncAsyncCallback<R> callback;
	
	public Object getAsyncState()
	{
		return asyncState;
	}
	
	public AsyncFutureTask(Callable<R> callable, FuncAsyncCallback<R> callback, Object asyncState)
	{
		
		super(callable);
		this.asyncState = asyncState;
		this.callback = callback;
	}
	
	@Override
	protected void done()
	{
		if (callback != null)
		{
			callback.invocationFinished(this);
		}
	}
	
	@Override
	public void endInvoke() throws InterruptedException, ExecutionException
	{
		get();
	}
	
	@Override
	public void endInvoke(long timeout, TimeUnit unit) throws InterruptedException,
		ExecutionException, TimeoutException
	{
		get(timeout, unit);
	}

	@Override
	public R endInvokeReturn() throws InterruptedException, ExecutionException
	{
		return get();
	}

	@Override
	public R endInvokeReturn(long timeout, TimeUnit unit) throws InterruptedException,
		ExecutionException, TimeoutException
	{
		return get(timeout, unit);
	}
}
