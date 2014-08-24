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

package net.objectzoo.delegates.helpers;

import net.objectzoo.delegates.Action0;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.FunctionAsyncResult;
import net.objectzoo.delegates.impl.AsyncExecutor;
import net.objectzoo.delegates.impl.AsyncFutureTask;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class AsyncExecutorTest
{
    private final Supplier<?> mockSupplier = mock(Supplier.class);
    private final Action0 mockAction0 = mock(Action0.class);
    private Executor executor;

    @Before
    @After
    public void resetDefaultExecutor()
    {
        AsyncExecutor.setDefaultExecutor(null);
    }

    @Test
    public void execute_suplier_uses_configured_default_executor() throws Exception
    {
        executor = spy(new InCallingThreadExecutor());
        AsyncExecutor.setDefaultExecutor(executor);
        AsyncExecutor sut = new AsyncExecutor();

        sut.execute(mockSupplier, null, null).end();

        verify(executor).execute(any(AsyncFutureTask.class));
    }

    @Test
    public void execute_suplier_uses_executor_given_at_construction_time() throws Exception
    {
        executor = spy(new InCallingThreadExecutor());
        AsyncExecutor sut = new AsyncExecutor(executor);

        sut.execute(mockSupplier, null, null).end();

        verify(executor).execute(any(AsyncFutureTask.class));
    }

    @Test
    public void execute_suplier_uses_created_default_executor() throws Exception
    {
        executor = spy(AsyncExecutor.getDefaultExecutor());
        AsyncExecutor.setDefaultExecutor(executor);
        AsyncExecutor sut = new AsyncExecutor();

        sut.execute(mockSupplier, null, null).end();

        verify(executor).execute(any(AsyncFutureTask.class));
    }

    @Test
    public void execute_suplier_creates_future_task_wich_calls_original_callable() throws Exception
    {
        executor = new InCallingThreadExecutor();
        AsyncExecutor sut = new AsyncExecutor(executor);

        sut.execute(mockSupplier, null, null).end();

        verify(mockSupplier).get();
    }

    @Test
    public void execute_action0_provides_async_state_in_result() throws Exception
    {
        executor = new InCallingThreadExecutor();
        AsyncExecutor sut = new AsyncExecutor(executor);

        Object asyncState = new Object();
        ActionAsyncResult result = sut.execute(mockAction0, null, asyncState);

        assertThat(result.getAsyncState(), is(asyncState));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void execute_supplier_calls_callback_with_result() throws Exception
    {
        Consumer callback = mock(Consumer.class);
        executor = new InCallingThreadExecutor();
        AsyncExecutor sut = new AsyncExecutor(executor);

        FunctionAsyncResult result = sut.execute(mockSupplier, callback, null);

        verify(callback).accept(result);
    }

    @Test
    public void execute_action0_creates_future_task_wich_calls_original_callable() throws Exception
    {
        executor = new InCallingThreadExecutor();
        AsyncExecutor sut = new AsyncExecutor(executor);

        sut.execute(mockAction0, null, null).end();

        verify(mockAction0).start();
    }

    @Test
    public void endReturn_returns_value_from_original_callable() throws Exception
    {
        executor = new InCallingThreadExecutor();
        AsyncExecutor sut = new AsyncExecutor(executor);
        final Object expected = new Object();
        doReturn(expected).when(mockSupplier).get();

        Object actual = sut.execute(mockSupplier, null, null).endReturn();

        assertEquals(expected, actual);
    }

    @Test
    public void end_returns_exception_from_original_callable() throws Exception
    {
        executor = new InCallingThreadExecutor();
        AsyncExecutor sut = new AsyncExecutor(executor);
        final RuntimeException expected = new RuntimeException();
        doThrow(expected).when(mockAction0).start();

        try
        {
            sut.execute(mockAction0, null, null).end();
        }
        catch (ExecutionException e)
        {
            assertEquals(expected, e.getCause());
        }
    }

    private static class InCallingThreadExecutor implements Executor
    {
        @Override
        public void execute(Runnable command)
        {
            command.run();
        }
    }
}
