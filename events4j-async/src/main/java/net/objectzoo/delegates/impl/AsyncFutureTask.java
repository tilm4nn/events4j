/*
 * The MIT License
 *
 * Copyright (C) 2014 Tilmann Kuhn
 *
 * http://www.object-zoo.net
 * mailto:events4j@object-zoo.net
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.objectzoo.delegates.impl;

import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.FunctionAsyncResult;

import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * This {@link FutureTask} subclass is used internally for the asynchronous invocation of actions
 * and funcions and implements the interfaces {@link FunctionAsyncResult} and {@link ActionAsyncResult} so
 * that it directly serves as return value for the asynchronous invocations and as parameter given
 * to the callback methods.
 *
 * @param <R> the type of the return value of the invocation
 * @author tilmann
 */
public class AsyncFutureTask<R> extends FutureTask<R> implements FunctionAsyncResult<R>,
    ActionAsyncResult
{
    private final Object asyncState;
    private final Consumer<? super FunctionAsyncResult<R>> callback;

    /**
     * Creates a new {@code AsyncFutureTask} that invokes the given callable and holds the given
     * asyncState and callback.
     *
     * @param callable   the callable to be invoked asynchronously
     * @param callback   the callback to be called when the invocation has finished
     * @param asyncState the asynchronous state object to be returned by this asynchronous result
     */
    public AsyncFutureTask(Callable<R> callable, Consumer<? super FunctionAsyncResult<R>> callback,
                           Object asyncState)
    {
        super(callable);
        this.asyncState = asyncState;
        this.callback = callback;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getAsyncState()
    {
        return asyncState;
    }

    /**
     * This methods overrides the super class method to call the callback when the invocation has
     * finished.
     */
    @Override
    protected void done()
    {
        if (callback != null)
        {
            callback.accept(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void end() throws InterruptedException, ExecutionException
    {
        get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void end(long timeout, TimeUnit unit) throws InterruptedException,
        ExecutionException, TimeoutException
    {
        get(timeout, unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public R endReturn() throws InterruptedException, ExecutionException
    {
        return get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public R endReturn(long timeout, TimeUnit unit) throws InterruptedException,
        ExecutionException, TimeoutException
    {
        return get(timeout, unit);
    }
}
