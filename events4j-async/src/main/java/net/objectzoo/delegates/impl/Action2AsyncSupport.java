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

import net.objectzoo.delegates.Action2;
import net.objectzoo.delegates.Action2Async;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.adapters.Action2ToAction2Async;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * This is an abstract support class that can be used to quickly implement asynchronous actions.
 * <p>
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link Action2#accept(Object, Object)} method.
 * <p>
 * The {@link Executor} to use for the asynchronous invocations can be chosen during creation of
 * {@code ActionAsyncSupport} instances. If no explicit executor is given the a default executor is
 * used. The default executor can be set using the
 * {@link AsyncExecutor#setDefaultExecutor(Executor)} property or is created automatically by the
 * {@link AsyncExecutor}.
 *
 * @param <T1> The type of the {@code Action2Async}'s first parameter
 * @param <T2> The type of the {@code Action2Async}'s second parameter
 * @author tilmann
 */
public abstract class Action2AsyncSupport<T1, T2> implements Action2<T1, T2>, Action2Async<T1, T2>
{
    private final Action2Async<T1, T2> asyncDelegate;

    /**
     * Creates a new {@code Action2AsyncSupport} that uses the default executor
     */
    public Action2AsyncSupport()
    {
        this(null);
    }

    /**
     * Create a new {@code Action2AsyncSupport} that uses the given executor
     *
     * @param executor the {@link Executor} to use for asynchronous invocations
     */
    public Action2AsyncSupport(Executor executor)
    {
        asyncDelegate = new Action2ToAction2Async<>(this, executor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void accept(T1 parameter1, T2 parameter2);

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionAsyncResult beginAccept(Consumer<ActionAsyncResult> callback, Object asyncState,
                                         T1 parameter1, T2 parameter2)
    {
        return asyncDelegate.beginAccept(callback, asyncState, parameter1, parameter2);
    }
}
