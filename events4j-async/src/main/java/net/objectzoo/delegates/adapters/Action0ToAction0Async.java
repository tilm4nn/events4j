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

import net.objectzoo.delegates.Action0;
import net.objectzoo.delegates.Action0Async;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.impl.AsyncExecutor;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * An adapter that converts a conventional {@link Action0} to an {@link Action0Async}.
 * <p>
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link Action0#start()} method.
 * <p>
 * The {@link Executor} to use for the asynchronous invocations can be chosen during creation of
 * this adapter. If no explicit executor is given the a default executor is used. The default
 * executor can be set using the {@link AsyncExecutor#setDefaultExecutor(Executor)} property or is
 * created automatically by the {@link AsyncExecutor}.
 *
 * @author tilmann
 */
public class Action0ToAction0Async implements Action0Async
{
    private final Action0 action;
    private final AsyncExecutor asyncExecutor;

    /**
     * Converts the given {@link Action0} to the interface {@link Action0Async} using the default
     * executor
     *
     * @param action the action to be converted
     */
    public Action0ToAction0Async(Action0 action)
    {
        this(action, null);
    }

    /**
     * Converts the given {@link Action0} to the interface {@link Action0Async} and executes
     * asynchronous call using the given {@link Executor}
     *
     * @param action   the action to be converted
     * @param executor the executor used for the asynchronous calls
     */
    public Action0ToAction0Async(Action0 action, Executor executor)
    {
        this.action = action;
        this.asyncExecutor = new AsyncExecutor(executor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionAsyncResult beginStart(Consumer<ActionAsyncResult> callback, Object asyncState)
    {
        return asyncExecutor.execute(action, callback, asyncState);
    }
}
