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
import net.objectzoo.delegates.ActionAsync;
import net.objectzoo.delegates.impl.AsyncExecutor;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * An adapter that converts an {@link Action} or {@link Consumer} to a new {@link Action}, that
 * makes an asynchronous calls to the original Action0 upon invocation. This adapter provides no
 * possibility to check for the completion of the original action nor to retrieve thrown exceptions.
 * Thus it is recommended to use {@link ActionToActionAsync} adapter instead to achieve a similar
 * result.
 * <p>
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link Action#accept(Object)} method.
 * <p>
 * The {@link Executor} to use for the asynchronous invocations can be chosen during creation of
 * this adapter. If no explicit executor is given the a default executor is used. The default
 * executor can be set using the {@link AsyncExecutor#setDefaultExecutor(Executor)} property or is
 * created automatically by the {@link AsyncExecutor}.
 *
 * @param <T> The type of the {@code Action}'s parameter
 * @author tilmann
 */
public class ActionToAsyncAction<T> implements Action<T>
{
    private final ActionAsync<T> actionAsync;

    /**
     * Makes the given {@link Action} asynchronous using the default executor
     *
     * @param action the action to be called asynchronously
     */
    public ActionToAsyncAction(Consumer<T> action)
    {
        this(action, null);
    }

    /**
     * Makes the given {@link Action} asynchronous using the given executor
     *
     * @param action   the action to be called asynchronously
     * @param executor the executor used for the asynchronous calls
     */
    public ActionToAsyncAction(Consumer<T> action, Executor executor)
    {
        actionAsync = new ActionToActionAsync<>(action, executor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(T parameter)
    {
        actionAsync.beginAccept(null, null, parameter);
    }
}
