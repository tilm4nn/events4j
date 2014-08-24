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
package net.objectzoo.delegates;

import net.objectzoo.delegates.adapters.Function2ToFunction2Async;

import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * A {@code Function2Async} is a reference to a function with return value that can be invoked
 * asynchronously in another thread. With {@code Function2Async} it is possible to track the status and
 * outcome using the {@link FunctionAsyncResult} returned upon invocation or by defining a
 * {@link Consumer} as callback for the invocation.
 *
 * @param <T1> The type of the {@code Function2Async}'s first parameter
 * @param <T2> The type of the {@code Function2Async}'s second parameter
 * @param <R>  The type of the {@code Function2Async}'s return value
 * @author tilmann
 */
public interface Function2Async<T1, T2, R>
{
    /**
     * Converts the given {@link BiFunction} to the interface {@code Function2Async} using the
     * default executor. Conversion is done using the adapter {@link Function2ToFunction2Async}.
     *
     * @param function the function to be converted
     */
    public static <T1, T2, R> Function2Async<T1, T2, R> from(BiFunction<T1, T2, R> function)
    {
        return new Function2ToFunction2Async<>(function);
    }

    /**
     * Converts the given {@link BiFunction} to the interface {@code Function2Async} and executes
     * asynchronous call using the given {@link Executor}. Conversion is done using the adapter
     * {@link Function2ToFunction2Async}.
     *
     * @param function the action to be converted
     * @param executor the executor used for the asynchronous calls
     */
    public static <T1, T2, R> Function2Async<T1, T2, R> from(BiFunction<T1, T2, R> function,
                                                             Executor executor)
    {
        return new Function2ToFunction2Async<>(function, executor);
    }

    /**
     * Asynchronously invoke this {@code Function2Async} with the given parameter value, callback and
     * asyncState.
     * <p>
     * Associated with each call to {@code beginInvoke} is a {@link FunctionAsyncResult} instance
     * returned by the call and given to the callback that can be used to track the status and
     * outcome of the invocation.
     *
     * @param callback   the {@link Consumer}, if given to {@code beginInvoke}, is invoked upon
     *                   completion of the {@code Function2Async}'s invocation and receives the same
     *                   {@link FunctionAsyncResult} that is returned by the call to {@code beginInvoke}.
     * @param asyncState the asyncState is an arbitrary reference that, if given to {@code beginInvoke}, can be
     *                   retrieved from this invocations {@link FunctionAsyncResult#getAsyncState()}
     * @param parameter1 the first parameter's value for the invocation
     * @param parameter2 the second parameter's value for the invocation
     * @return the {@link FunctionAsyncResult} associated with this asynchronous invocation
     */
    public FunctionAsyncResult<R> beginApply(Consumer<? super FunctionAsyncResult<R>> callback,
                                             Object asyncState, T1 parameter1, T2 parameter2);
}
