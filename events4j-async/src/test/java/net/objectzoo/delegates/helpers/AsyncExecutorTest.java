package net.objectzoo.delegates.helpers;

import static junit.framework.Assert.assertEquals;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import net.objectzoo.delegates.impl.AsyncExecutor;
import net.objectzoo.delegates.impl.AsyncFutureTask;

public class AsyncExecutorTest
{
	private Mockery context;
	private Executor executor;
	private Callable<?> callable;
	
	@Before
	public void before()
	{
		// Reset default executor 
		AsyncExecutor.setDefaultExecutor(null);
		
		context = new Mockery();
		
		callable = context.mock(Callable.class);
	}
	
	@Test
	public void ececute_uses_configured_default_executor()
	{
		executor = context.mock(Executor.class);
		AsyncExecutor.setDefaultExecutor(executor);
		AsyncExecutor sut = new AsyncExecutor();
		
		context.checking(new Expectations()
		{
			{
				oneOf(executor).execute(with(any(AsyncFutureTask.class)));
			}
		});
		
		sut.execute(callable, null, null);
		context.assertIsSatisfied();
	}
	
	@Test
	public void ececute_uses_executor_given_at_construction_time() throws Exception
	{
		executor = context.mock(Executor.class);
		AsyncExecutor sut = new AsyncExecutor(executor);
		
		context.checking(new Expectations()
		{
			{
				oneOf(executor).execute(with(any(AsyncFutureTask.class)));
			}
		});
		
		sut.execute(callable, null, null);
		context.assertIsSatisfied();
	}
	
	@Test
	public void execute_creates_future_task_wich_calls_original_callable() throws Exception
	{
		executor = new SyncExecutor();
		AsyncExecutor sut = new AsyncExecutor(executor);
		
		context.checking(new Expectations()
		{
			{
				oneOf(callable).call();
				will(returnValue(null));
			}
		});
		
		sut.execute(callable, null, null);
		context.assertIsSatisfied();
	}
	
	@Test
	public void endInvokeReturn_returns_value_from_original_callable() throws Exception
	{
		executor = new SyncExecutor();
		AsyncExecutor sut = new AsyncExecutor(executor);
		final Object expected = new Object();
		
		context.checking(new Expectations()
		{
			{
				oneOf(callable).call();
				will(returnValue(expected));
			}
		});
		
		Object actual = sut.execute(callable, null, null).endInvokeReturn();
		
		assertEquals(expected, actual);
		context.assertIsSatisfied();
	}
	
	@Test
	public void endInvoke_returns_exception_from_original_callable() throws Exception
	{
		executor = new SyncExecutor();
		AsyncExecutor sut = new AsyncExecutor(executor);
		final Exception expected = new Exception();
		
		context.checking(new Expectations()
		{
			{
				oneOf(callable).call();
				will(throwException(expected));
			}
		});
		
		try
		{
			sut.execute(callable, null, null).endInvoke();
		}
		catch (ExecutionException e)
		{
			assertEquals(expected, e.getCause());
		}
		
		context.assertIsSatisfied();
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
