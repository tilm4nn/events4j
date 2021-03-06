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

import net.objectzoo.delegates.Function3;
import net.objectzoo.delegates.Function3Async;
import net.objectzoo.delegates.FunctionAsyncResult;
import net.objectzoo.delegates.adapters.Function3ToFunction3Async;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * This is an abstract support class that can be used to quickly implement asynchronous functions.
 * <p>
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link Function3#apply(Object, Object, Object)} method.
 * <p>
 * The {@link Executor} to use for the asynchronous invocations can be chosen during creation of
 * {@code ActionAsyncSupport} instances. If no explicit executor is given the a default executor is
 * used. The default executor can be set using the
 * {@link AsyncExecutor#setDefaultExecutor(Executor)} property or is created automatically by the
 * {@link AsyncExecutor}.
 *
 * @param <T1> The type of the {@code FuncAsync}'s first parameter
 * @param <T2> The type of the {@code FuncAsync}'s second parameter
 * @param <T3> The type of the {@code FuncAsync}'s third parameter
 * @param <R>  The type of the functions result
 * @author tilmann
 */
public abstract class Function3AsyncSupport<T1, T2, T3, R> implements Function3<T1, T2, T3, R>,
    Function3Async<T1, T2, T3, R>
{
    private final Function3Async<T1, T2, T3, R> asyncDelegate;

    /**
     * Creates a new {@code Function3AsyncSupport} that uses the default executor
     */
    public Function3AsyncSupport()
    {
        this(null);
    }

    /**
     * Create a new {@code Function3AsyncSupport} that uses the given executor
     *
     * @param executor the {@link Executor} to use for asynchronous invocations
     */
    public Function3AsyncSupport(Executor executor)
    {
        asyncDelegate = new Function3ToFunction3Async<>(this, executor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract R apply(T1 parameter1, T2 parameter2, T3 parameter3);

    /**
     * {@inheritDoc}
     */
    @Override
    public FunctionAsyncResult<R> beginApply(Consumer<? super FunctionAsyncResult<R>> callback,
                                             Object asyncState, T1 parameter1, T2 parameter2,
                                             T3 parameter3)
    {
        return asyncDelegate.beginApply(callback, asyncState, parameter1, parameter2, parameter3);
    }
}
