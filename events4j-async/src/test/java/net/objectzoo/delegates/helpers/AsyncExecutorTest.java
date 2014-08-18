package net.objectzoo.delegates.helpers;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.objectzoo.delegates.Action0;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.FunctionAsyncResult;
import net.objectzoo.delegates.impl.AsyncExecutor;
import net.objectzoo.delegates.impl.AsyncFutureTask;

public class AsyncExecutorTest
{
	private Executor executor;
	private final Supplier<?> mockSupplier = mock(Supplier.class);;
	private final Action0 mockAction0 = mock(Action0.class);
	
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
