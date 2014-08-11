package net.objectzoo.delegates.helpers;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.delegates.impl.AsyncExecutor;
import net.objectzoo.delegates.impl.AsyncFutureTask;

public class AsyncExecutorTest
{
	private Executor executor;
	private Callable<?> callable;
	
	@Before
	public void before()
	{
		// Reset default executor 
		AsyncExecutor.setDefaultExecutor(null);
		
		callable = mock(Callable.class);
	}
	
	@Test
	public void ececute_uses_configured_default_executor()
	{
		executor = mock(Executor.class);
		AsyncExecutor.setDefaultExecutor(executor);
		AsyncExecutor sut = new AsyncExecutor();
		
		sut.execute(callable, null, null);
		
		verify(executor).execute(any(AsyncFutureTask.class));
	}
	
	@Test
	public void ececute_uses_executor_given_at_construction_time() throws Exception
	{
		executor = mock(Executor.class);
		AsyncExecutor sut = new AsyncExecutor(executor);
		
		sut.execute(callable, null, null);
		
		verify(executor).execute(any(AsyncFutureTask.class));
	}
	
	@Test
	public void execute_creates_future_task_wich_calls_original_callable() throws Exception
	{
		executor = new SyncExecutor();
		AsyncExecutor sut = new AsyncExecutor(executor);
		
		sut.execute(callable, null, null);
		
		verify(callable).call();
	}
	
	@Test
	public void endInvokeReturn_returns_value_from_original_callable() throws Exception
	{
		executor = new SyncExecutor();
		AsyncExecutor sut = new AsyncExecutor(executor);
		final Object expected = new Object();
		doReturn(expected).when(callable).call();
		
		Object actual = sut.execute(callable, null, null).endInvokeReturn();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void endInvoke_returns_exception_from_original_callable() throws Exception
	{
		executor = new SyncExecutor();
		AsyncExecutor sut = new AsyncExecutor(executor);
		final Exception expected = new Exception();
		doThrow(expected).when(callable).call();
		
		try
		{
			sut.execute(callable, null, null).endInvoke();
		}
		catch (ExecutionException e)
		{
			assertEquals(expected, e.getCause());
		}
	}
	
	private static class SyncExecutor implements Executor
	{
		@Override
		public void execute(Runnable command)
		{
			command.run();
		}
	}
}
