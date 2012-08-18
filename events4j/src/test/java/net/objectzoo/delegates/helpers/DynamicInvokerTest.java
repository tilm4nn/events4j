package net.objectzoo.delegates.helpers;

import static junit.framework.Assert.assertEquals;

import java.lang.reflect.Method;

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;
import net.objectzoo.delegates.Func0;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

public class DynamicInvokerTest
{
	Mockery context;
	
	@Before
	public void before()
	{
		context = new Mockery();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void dynamicInvoke_invokes_action_with_correct_arguments()
	{
		final Action<Object> action = context.mock(Action.class);
		DynamicInvoker sut = new DynamicInvoker(Action.class, action);
		final Object expected = new Object();
		
		context.checking(new Expectations()
		{
			{
				one(action).invoke(with(expected));
			}
		});
		
		sut.dynamicInvoke(expected);
		
		context.assertIsSatisfied();
	}
	
	@Test
	public void dynamicInvoke_returns_correct_return_value()
	{
		final Func0<?> func = context.mock(Func0.class);
		DynamicInvoker sut = new DynamicInvoker(Func0.class, func);
		final Object expected = new Object();
		
		context.checking(new Expectations()
		{
			{
				one(func).invoke();
				will(returnValue(expected));
			}
		});
		
		Object actual = sut.dynamicInvoke();
		
		assertEquals(expected, actual);
		context.assertIsSatisfied();
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = IllegalArgumentException.class)
	public void dynamicInvoke_throws_exception_on_wrong_argument_count()
	{
		final Action<Object> action = context.mock(Action.class);
		DynamicInvoker sut = new DynamicInvoker(Action.class, action);
		
		sut.dynamicInvoke(new Object(), new Object());
	}
	
	@Test(expected = MyRuntimeException.class)
	public void dynamicInvoke_rethrows_original_RuntimeException()
	{
		final Action0 action = context.mock(Action0.class);
		DynamicInvoker sut = new DynamicInvoker(Action0.class, action);
		
		context.checking(new Expectations()
		{
			{
				one(action).invoke();
				will(throwException(new MyRuntimeException()));
			}
		});
		
		sut.dynamicInvoke();
	}
	
	@Test
	public void getInvokeMethod_returns_correct_method() throws SecurityException,
		NoSuchMethodException
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
