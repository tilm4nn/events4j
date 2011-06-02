package net.objectzoo.delegates.helpers;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0Async;
import net.objectzoo.delegates.Func0Async;
import net.objectzoo.delegates.FuncAsync;
import net.objectzoo.delegates.FuncAsyncCallback;
import net.objectzoo.delegates.FuncAsyncResult;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

public class DynamicAsyncInvokerTest
{
	Mockery context;
	
	@Before
	public void before()
	{
		context = new Mockery();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void beginDynamicInvoke_invokes_action_with_correct_arguments()
	{
		final FuncAsync<Object, ?> func = context.mock(FuncAsync.class);
		DynamicAsyncInvoker sut = new DynamicAsyncInvoker(FuncAsync.class, func);
		final FuncAsyncCallback<Object> expectedCallback = context.mock(FuncAsyncCallback.class);
		final Object expectedParam = new Object();
		final Object expectedAsyncState = new Object();
		
		context.checking(new Expectations()
		{
			{
				one(func).beginInvoke(with(expectedCallback), with(expectedAsyncState),
					with(expectedParam));
			}
		});
		
		sut.beginDynamicInvoke(expectedCallback, expectedAsyncState, expectedParam);
		
		context.assertIsSatisfied();
	}
	
	@Test
	public void beginDynamicInvoke_returns_correct_FuncAsyncResult() throws Exception
	{
		final Func0Async<?> func = context.mock(Func0Async.class);
		DynamicAsyncInvoker sut = new DynamicAsyncInvoker(Func0Async.class, func);
		final FuncAsyncResult<?> expected = context.mock(FuncAsyncResult.class);
		
		context.checking(new Expectations()
		{
			{
				one(func).beginInvoke(null, null);
				will(returnValue(expected));
			}
		});
		
		Object actual = sut.beginDynamicInvoke(null, null);
		
		assertEquals(expected, actual);
		context.assertIsSatisfied();
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = IllegalArgumentException.class)
	public void beginDynamicInvoke_throws_exception_on_wrong_argument_count()
	{
		final Action<Object> action = context.mock(Action.class);
		DynamicAsyncInvoker sut = new DynamicAsyncInvoker(Action.class, action);
		
		sut.beginDynamicInvoke(null, null, new Object(), new Object());
	}
	
	@Test(expected = MyRuntimeException.class)
	public void beginDynamicInvoke_rethrows_original_RuntimeException()
	{
		final Action0Async action = context.mock(Action0Async.class);
		DynamicAsyncInvoker sut = new DynamicAsyncInvoker(Action0Async.class, action);
		
		context.checking(new Expectations()
		{
			{
				one(action).beginInvoke(null, null);
				will(throwException(new MyRuntimeException()));
			}
		});
		
		sut.beginDynamicInvoke(null, null);
	}
	
	@Test
	public void computeAsycBeginInvokeParameters_puts_callback_state_and_params_in_array()
	{
		Object[] expected = new Object[4];
		expected[0] = new Object();
		expected[1] = new Object();
		expected[2] = new Object();
		expected[3] = new Object();
		
		Object[] params = new Object[2];
		params[0] = expected[2];
		params[1] = expected[3];
		
		Object[] actual = DynamicAsyncInvoker.computeAsycBeginInvokeParameters(expected[0],
			expected[1], params);
		
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void computeAsycBeginInvokeParameters_puts_callback_state_and_no_parameters_in_array()
	{
		Object[] expected = new Object[2];
		expected[0] = new Object();
		expected[1] = new Object();
		
		Object[] actual = DynamicAsyncInvoker.computeAsycBeginInvokeParameters(expected[0],
			expected[1]);
		
		assertArrayEquals(expected, actual);
	}
	
	private static void assertArrayEquals(Object[] expected, Object[] actual)
	{
		if (expected == null)
		{
			assertNull(actual);
		}
		else
		{
			assertNotNull(actual);
			assertEquals(expected.length, actual.length);
			for (int i = 0; i < expected.length; i++)
			{
				assertEquals(expected[i], actual[i]);
			}
		}
	}
	
	private static class MyRuntimeException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;
	}
}
