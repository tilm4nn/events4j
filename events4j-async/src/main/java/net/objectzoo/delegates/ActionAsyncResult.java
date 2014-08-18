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
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ActionAsync OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package net.objectzoo.delegates;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A {@code ActionAsyncResult} represents the result of an {@link ActionAsync} invocation. Methods
 * are provided to check if the invocation is complete, to wait for its completion and check the
 * outcome. The outcome can be checked using the method {@link #end()} when the
 * {@code ActionAsync} has completed, blocking if necessary until it is ready. Cancellation is
 * performed by the {@link #cancel(boolean)} method. Additional methods are provided to determine if
 * the {@code ActionAsync} completed normally or was cancelled. Once an {@code ActionAsync} has
 * completed, the {@code ActionAsync} cannot be cancelled.
 * 
 * @see ActionAsync
 * @author tilmann
 */
public interface ActionAsyncResult
{
	/**
	 * Retrieves the async state object associated with this {@code ActionAsync} invocation.
	 * 
	 * @return the async state object given in the call to
	 *         {@link FunctionAsync#beginApply(FuncAsyncCallback, Object, Object)}
	 */
	public Object getAsyncState();
	
	/**
	 * Waits if necessary for the {@code ActionAsync} to complete.
	 * 
	 * @throws CancellationException
	 *         if the {@code ActionAsync} was cancelled
	 * @throws ExecutionException
	 *         if the {@code ActionAsync} threw an exception
	 * @throws InterruptedException
	 *         if the current thread was interrupted while waiting
	 */
	public void end() throws InterruptedException, ExecutionException;
	
	/**
	 * Waits if necessary for at most the given time for the {@code ActionAsync} to complete.
	 * 
	 * @param timeout
	 *        the maximum time to wait
	 * @param unit
	 *        the time unit of the timeout argument
	 * @throws CancellationException
	 *         if the {@code ActionAsync} was cancelled
	 * @throws ExecutionException
	 *         if the {@code ActionAsync} threw an exception
	 * @throws InterruptedException
	 *         if the current thread was interrupted while waiting
	 * @throws TimeoutException
	 *         if the wait timed out
	 */
	public void end(long timeout, TimeUnit unit) throws InterruptedException,
		ExecutionException, TimeoutException;
	
	/**
	 * Attempts to cancel the invocation of this {@code ActionAsync}. This attempt will fail if the
	 * {@code ActionAsync} has already completed, has already been cancelled, or could not be
	 * cancelled for some other reason. If successful, and this {@code ActionAsync} has not started
	 * when {@link #cancel(boolean)} is called, this {@code ActionAsync} should never run. If the
	 * {@code ActionAsync} has already started, then the {@code mayInterruptIfRunning} parameter
	 * determines whether the thread executing this {@code ActionAsync} should be interrupted in an
	 * attempt to stop the {@code ActionAsync}.
	 * 
	 * After this method returns, subsequent calls to {@link #isDone} will always return
	 * {@code true}. Subsequent calls to {@link #isCancelled} will always return {@code true} if
	 * this method returned {@code true}.
	 * 
	 * @param mayInterruptIfRunning
	 *        {@code true} if the thread executing this {@code ActionAsync} should be interrupted;
	 *        otherwise, in-progress {@code ActionAsync}s are allowed to complete
	 * @return {@code false} if the {@code ActionAsync} could not be cancelled, typically because it
	 *         has already completed normally; {@code true} otherwise
	 */
	boolean cancel(boolean mayInterruptIfRunning);
	
	/**
	 * Returns {@code true} if this {@code ActionAsync} was cancelled before it completed normally.
	 * 
	 * @return {@code true} if this {@code ActionAsync} was cancelled before it completed
	 */
	boolean isCancelled();
	
	/**
	 * Returns {@code true} if this {@code ActionAsync} completed.
	 * 
	 * Completion may be due to normal termination, an exception, or cancellation -- in all of these
	 * cases, this method will return {@code true}.
	 * 
	 * @return {@code true} if this {@code ActionAsync} completed
	 */
	boolean isDone();
}
