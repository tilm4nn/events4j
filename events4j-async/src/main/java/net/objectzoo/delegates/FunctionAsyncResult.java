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
package net.objectzoo.delegates;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A {@code FuncAsyncResult} represents the result of an {@link FunctionAsync} invocation. Methods are
 * provided to check if the invocation is complete, to wait for its completion, and to check the
 * outcome of the {@code FuncAsync}. The outcome can be checked using the method
 * {@link #endReturn()} when the {@code FuncAsync} has completed, blocking if necessary until
 * it is ready. Cancellation is performed by the {@link #cancel(boolean)} method. Additional methods
 * are provided to determine if the {@code FuncAsync} completed normally or was cancelled. Once a
 * {@code FuncAsync} has completed, the {@code FuncAsync} cannot be cancelled.
 * 
 * @see FunctionAsync
 * @author tilmann
 * @param <R>
 *        The result type returned by the {@code FuncAsync}
 */
public interface FunctionAsyncResult<R> extends ActionAsyncResult
{
	/**
	 * Retrieves the async state object associated with the {@code FuncAsync} invocation.
	 * 
	 * @return the async state object given in the call to
	 *         {@link FunctionAsync#beginApply(FuncAsyncCallback, Object, Object)}
	 */
	public Object getAsyncState();
	
	/**
	 * Waits if necessary for the {@code FuncAsync} to complete.
	 * 
	 * @throws CancellationException
	 *         if the {@code FuncAsync} was cancelled
	 * @throws ExecutionException
	 *         if the {@code FuncAsync} threw an exception
	 * @throws InterruptedException
	 *         if the current thread was interrupted while waiting
	 */
	public void end() throws InterruptedException, ExecutionException;
	
	/**
	 * Waits if necessary for at most the given time for the {@code FuncAsync} to complete.
	 * 
	 * @param timeout
	 *        the maximum time to wait
	 * @param unit
	 *        the time unit of the timeout argument
	 * @throws CancellationException
	 *         if the {@code FuncAsync} was cancelled
	 * @throws ExecutionException
	 *         if the {@code FuncAsync} threw an exception
	 * @throws InterruptedException
	 *         if the current thread was interrupted while waiting
	 * @throws TimeoutException
	 *         if the wait timed out
	 */
	public void end(long timeout, TimeUnit unit) throws InterruptedException,
		ExecutionException, TimeoutException;
	
	/**
	 * Attempts to cancel invocation of this {@code FuncAsync}. This attempt will fail if the
	 * {@code FuncAsync} has already completed, has already been cancelled, or could not be
	 * cancelled for some other reason. If successful, and this {@code FuncAsync} has not started
	 * when {@link #cancel(boolean)} is called, this {@code FuncAsync} should never run. If the
	 * {@code FuncAsync} has already started, then the {@code mayInterruptIfRunning} parameter
	 * determines whether the thread executing this {@code FuncAsync} should be interrupted in an
	 * attempt to stop the {@code FuncAsync}.
	 * 
	 * <p>
	 * After this method returns, subsequent calls to {@link #isDone} will always return
	 * {@code true}. Subsequent calls to {@link #isCancelled} will always return {@code true} if
	 * this method returned {@code true}.
	 * 
	 * @param mayInterruptIfRunning
	 *        {@code true} if the thread executing this {@code uncAsync} should be interrupted;
	 *        otherwise, in-progress {@code FuncAsync}s are allowed to complete
	 * @return {@code false} if the {@code FuncAsync} could not be cancelled, typically because it
	 *         has already completed normally; {@code true} otherwise
	 */
	boolean cancel(boolean mayInterruptIfRunning);
	
	/**
	 * Returns {@code true} if this {@code FuncAsync} was cancelled before it completed normally.
	 * 
	 * @return {@code true} if this {@code FuncAsync} was cancelled before it completed
	 */
	boolean isCancelled();
	
	/**
	 * Returns {@code true} if this {@code FuncAsync} completed.
	 * 
	 * Completion may be due to normal termination, an exception, or cancellation -- in all of these
	 * cases, this method will return {@code true}.
	 * 
	 * @return {@code true} if this {@code FuncAsync} completed
	 */
	boolean isDone();
	
	/**
	 * Waits if necessary for the {@code FuncAsync} to complete, and then retrieves its return
	 * value.
	 * 
	 * @return the return value of the {@code FuncAsync}
	 * @throws CancellationException
	 *         if the {@code FuncAsync} was cancelled
	 * @throws ExecutionException
	 *         if the {@code FuncAsync} threw an exception
	 * @throws InterruptedException
	 *         if the current thread was interrupted while waiting
	 */
	R endReturn() throws InterruptedException, ExecutionException;
	
	/**
	 * Waits if necessary for at most the given time for the {@code FuncAsync} to complete, and then
	 * retrieves its return value, if available.
	 * 
	 * @param timeout
	 *        the maximum time to wait
	 * @param unit
	 *        the time unit of the timeout argument
	 * @return the return value of the {@code FuncAsync}
	 * @throws CancellationException
	 *         if the {@code FuncAsync} was cancelled
	 * @throws ExecutionException
	 *         if the {@code FuncAsync} threw an exception
	 * @throws InterruptedException
	 *         if the current thread was interrupted while waiting
	 * @throws TimeoutException
	 *         if the wait timed out
	 */
	R endReturn(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException,
		TimeoutException;
}
