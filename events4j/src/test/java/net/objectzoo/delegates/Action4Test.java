package net.objectzoo.delegates;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Action4Test
{
	private final Object argument1 = new Object();
	private final Object argument2 = new Object();
	private final Object argument3 = new Object();
	private final Object argument4 = new Object();
	
	@Test
	public void from_function4_crates_action4_that_invokes_function4_with_correct_arguments()
	{
		Function4 mockFunction = mock(Function4.class);
		
		Action4 action4 = Action4.from(mockFunction);
		
		action4.accept(argument1, argument2, argument3, argument4);
		verify(mockFunction).apply(argument1, argument2, argument3, argument4);
	}
	
	@Test
	public void bindParameters_creates_action0_that_ivokes_action_with_bound_arguments()
		throws Exception
	{
		Action4 mockAction = mock(Action4.class);
		Action4 sut = (t1, t2, t3, t4) -> mockAction.accept(t1, t2, t3, t4);
		
		Action0 action0 = sut.bindParameters(argument1, argument2, argument3, argument4);
		
		action0.start();
		verify(mockAction).accept(argument1, argument2, argument3, argument4);
	}
}