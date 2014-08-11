package net.objectzoo.delegates.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0Async;
import net.objectzoo.delegates.Func0Async;
import net.objectzoo.delegates.FuncAsync;
import net.objectzoo.delegates.FuncAsyncCallback;
import net.objectzoo.delegates.FuncAsyncResult;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DynamicAsyncInvokerTest
{
	@Test
	public void beginDynamicInvoke_invokes_action_with_correct_arguments()
	{
		final FuncAsync func = mock(FuncAsync.class);
		DynamicAsyncInvoker sut = new DynamicAsyncInvoker(FuncAsync.class, func);
		final FuncAsyncCallback expectedCallback = mock(FuncAsyncCallback.class);
		final Object expectedParam = new Object();
		final Object expectedAsyncState = new Object();
		
		sut.beginDynamicInvoke(expectedCallback, expectedAsyncState, expectedParam);
		
		verify(func).beginInvoke(expectedCallback, expectedAsyncState, expectedParam);
	}
	
	@Test
	public void beginDynamicInvoke_returns_correct_FuncAsyncResult() throws Exception
	{
		final Func0Async<?> func = mock(Func0Async.class);
		DynamicAsyncInvoker sut = new DynamicAsyncInvoker(Func0Async.class, func);
		final FuncAsyncResult<?> expected = mock(FuncAsyncResult.class);
		doReturn(expected).when(func).beginInvoke(null, null);
		
		Object actual = sut.beginDynamicInvoke(null, null);
		
		assertEquals(expected, actual);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void beginDynamicInvoke_throws_exception_on_wrong_argument_count()
	{
		final Action<Object> action = mock(Action.class);
		DynamicAsyncInvoker sut = new DynamicAsyncInvoker(Action.class, action);
		
		sut.beginDynamicInvoke(null, null, new Object(), new Object());
	}
	
	@Test(expected = MyRuntimeException.class)
	public void beginDynamicInvoke_rethrows_original_RuntimeException()
	{
		final Action0Async action = mock(Action0Async.class);
		DynamicAsyncInvoker sut = new DynamicAsyncInvoker(Action0Async.class, action);
		doThrow(new MyRuntimeException()).when(action).beginInvoke(null, null);
		
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
