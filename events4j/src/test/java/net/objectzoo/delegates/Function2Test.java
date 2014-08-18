package net.objectzoo.delegates;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.function.BiFunction;

import org.junit.Test;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Function2Test
{
	private final Object argument1 = new Object();
	private final Object argument2 = new Object();
	private final Object expectedResult = new Object();
	
	@Test
	public void toAction_creates_action_that_invokes_apply()
	{
		Function2 mockFunction = mock(Function2.class);
		Function2 sut = (t1, t2) -> mockFunction.apply(t1, t2);
		
		Action2 action2 = sut.toAction();
		
		action2.accept(argument1, argument2);
		verify(mockFunction).apply(argument1, argument2);
	}
	
	@Test
	public void from_bifunction_creates_function_that_invokes_bifunction()
	{
		BiFunction mockFunction = mock(BiFunction.class);
		
		Function2 function2 = Function2.from(mockFunction);
		
		doReturn(expectedResult).when(mockFunction).apply(anyObject(), anyObject());
		Object result = function2.apply(argument1, argument2);
		verify(mockFunction).apply(argument1, argument2);
		assertThat(result, is(expectedResult));
	}
	
	@Test
	public void bindParameter_creates_function0_that_ivokes_function_with_bound_arguments()
	{
		Function2 mockFunction = mock(Function2.class);
		Function2 sut = (t1, t2) -> mockFunction.apply(t1, t2);
		
		Function0 function0 = sut.bindParameters(argument1, argument2);
		
		doReturn(expectedResult).when(mockFunction).apply(anyObject(), anyObject());
		Object result = function0.get();
		verify(mockFunction).apply(argument1, argument2);
		assertThat(result, is(expectedResult));
	}
}
