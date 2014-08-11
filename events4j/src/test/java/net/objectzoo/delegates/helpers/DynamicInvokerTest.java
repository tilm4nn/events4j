package net.objectzoo.delegates.helpers;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Method;

import org.junit.Test;

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;
import net.objectzoo.delegates.Func0;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DynamicInvokerTest
{
	@Test
	public void dynamicInvoke_invokes_action_with_correct_arguments()
	{
		final Action mockAction = mock(Action.class);
		DynamicInvoker sut = new DynamicInvoker(Action.class, mockAction);
		final Object expected = new Object();
		
		sut.dynamicInvoke(expected);
		
		verify(mockAction).invoke(expected);
	}
	
	@Test
	public void dynamicInvoke_returns_correct_return_value()
	{
		final Func0 mockFunc = mock(Func0.class);
		DynamicInvoker sut = new DynamicInvoker(Func0.class, mockFunc);
		final Object expected = new Object();
		doReturn(expected).when(mockFunc).invoke();
		
		Object actual = sut.dynamicInvoke();
		
		assertEquals(expected, actual);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void dynamicInvoke_throws_exception_on_wrong_argument_count()
	{
		final Action mockAction = mock(Action.class);
		DynamicInvoker sut = new DynamicInvoker(Action.class, mockAction);
		
		sut.dynamicInvoke(new Object(), new Object());
	}
	
	@Test(expected = MyRuntimeException.class)
	public void dynamicInvoke_rethrows_original_RuntimeException()
	{
		final Action0 mockAction = mock(Action0.class);
		DynamicInvoker sut = new DynamicInvoker(Action0.class, mockAction);
		doThrow(new MyRuntimeException()).when(mockAction).invoke();
		
		sut.dynamicInvoke();
	}
	
	@Test
	public void getInvokeMethod_returns_correct_method() throws SecurityException, NoSuchMethodException
	{
		Method expected = Func0.class.getMethod("invoke", new Class[0]);
		
		Method actual = DynamicInvoker.getInvokeMethod(Func0.class, "invoke");
		
		assertEquals(expected, actual);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getInvokeMethod_throws_exception_on_invalid_method_name()
	{
		DynamicInvoker.getInvokeMethod(Object.class, "invoke");
	}
	
	private static class MyRuntimeException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;
	}
}
