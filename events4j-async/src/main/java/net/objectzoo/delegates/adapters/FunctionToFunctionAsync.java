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

import net.objectzoo.delegates.Function0;
import net.objectzoo.delegates.FunctionAsync;
import net.objectzoo.delegates.FunctionAsyncResult;
import net.objectzoo.delegates.impl.AsyncExecutor;

import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * An adapter that converts a conventional {@link Function} to an {@link FunctionAsync}.
 * <p>
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link Function#apply(Object)} method.
 * <p>
 * The {@link Executor} to use for the asynchronous invocations can be chosen during creation of
 * this adapter. If no explicit executor is given the a default executor is used. The default
 * executor can be set using the {@link AsyncExecutor#setDefaultExecutor(Executor)} property or is
 * created automatically by the {@link AsyncExecutor}.
 *
 * @param <T> The type of the {@code Func}'s parameter
 * @param <R> The type of the {@code Func}'s return value
 * @author tilmann
 */
public class FunctionToFunctionAsync<T, R> implements FunctionAsync<T, R>
{
    private final Function<T, R> function;
    private final AsyncExecutor asyncExecutor;

    /**
     * Converts the given {@link Function} to the interface {@link FunctionAsync} using the default
     * executor
     *
     * @param function the function to be converted
     */
    public FunctionToFunctionAsync(Function<T, R> function)
    {
        this(function, null);
    }

    /**
     * Converts the given {@link Function} to the interface {@link FunctionAsync} and executes
     * asynchronous call using the given {@link Executor}
     *
     * @param function the function to be converted
     * @param executor the executor used for the asynchronous calls
     */
    public FunctionToFunctionAsync(Function<T, R> function, Executor executor)
    {
        this.function = function;
        this.asyncExecutor = new AsyncExecutor(executor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FunctionAsyncResult<R> beginApply(Consumer<? super FunctionAsyncResult<R>> callback,
                                             Object asyncState, final T parameter)
    {
        Function0<R> callable = net.objectzoo.delegates.Function.bindParameter(function, parameter);
        return asyncExecutor.execute(callable, callback, asyncState);
    }
}
