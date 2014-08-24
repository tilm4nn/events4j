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
package net.objectzoo.delegates.adapters;

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action3;
import net.objectzoo.delegates.Action3Async;
import net.objectzoo.delegates.impl.AsyncExecutor;

import java.util.concurrent.Executor;

/**
 * An adapter that converts an {@link Action3} to a new {@link Action3}, that makes an asynchronous
 * calls to the original Action0 upon invocation. This adapter provides no possibility to check for
 * the completion of the original action nor to retrieve thrown exceptions. Thus it is recommended
 * to use {@link Action3ToAction3Async} adapter instead to achieve a similar result.
 * <p>
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link Action3#accept(Object, Object, Object)} method.
 * <p>
 * The {@link Executor} to use for the asynchronous invocations can be chosen during creation of
 * this adapter. If no explicit executor is given the a default executor is used. The default
 * executor can be set using the {@link AsyncExecutor#setDefaultExecutor(Executor)} property or is
 * created automatically by the {@link AsyncExecutor}.
 *
 * @param <T1> The type of the {@code Action}'s first parameter
 * @param <T2> The type of the {@code Action}'s second parameter
 * @param <T3> The type of the {@code Action}'s third parameter
 * @author tilmann
 */
public class Action3ToAsyncAction3<T1, T2, T3> implements Action3<T1, T2, T3>
{
    private final Action3Async<T1, T2, T3> actionAsync;

    /**
     * Makes the given {@link Action} asynchronous using the default executor
     *
     * @param action the action to be called asynchronously
     */
    public Action3ToAsyncAction3(Action3<T1, T2, T3> action)
    {
        this(action, null);
    }

    /**
     * Makes the given {@link Action} asynchronous using the given executor
     *
     * @param action   the action to be called asynchronously
     * @param executor the executor used for the asynchronous calls
     */
    public Action3ToAsyncAction3(Action3<T1, T2, T3> action, Executor executor)
    {
        actionAsync = new Action3ToAction3Async<>(action, executor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(T1 parameter1, T2 parameter2, T3 parameter3)
    {
        actionAsync.beginAccept(null, null, parameter1, parameter2, parameter3);
    }
}
