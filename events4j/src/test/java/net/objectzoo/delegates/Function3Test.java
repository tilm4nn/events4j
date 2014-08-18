package net.objectzoo.delegates;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Function3Test
{
	private final Object argument1 = new Object();
	private final Object argument2 = new Object();
	private final Object argument3 = new Object();
	private final Object expectedResult = new Object();
	
	@Test
	public void toAction_creates_action_that_invokes_apply()
	{
		Function3 mockFunction = mock(Function3.class);
		Function3 sut = (t1, t2, t3) -> mockFunction.apply(t1, t2, t3);
		
		Action3 action3 = sut.toAction();
		
		action3.accept(argument1, argument2, argument3);
		verify(mockFunction).apply(argument1, argument2, argument3);
	}
	
	@Test
	public void bindParameter_creates_function0_that_ivokes_function_with_bound_arguments()
	{
		Function3 mockFunction = mock(Function3.class);
		Function3 sut = (t1, t2, t3) -> mockFunction.apply(t1, t2, t3);
		
		Function0 function0 = sut.bindParameters(argument1, argument2, argument3);
		
		doReturn(expectedResult).when(mockFunction).apply(anyObject(), anyObject(), anyObject());
		Object result = function0.get();
		verify(mockFunction).apply(argument1, argument2, argument3);
		assertThat(result, is(expectedResult));
	}
}
