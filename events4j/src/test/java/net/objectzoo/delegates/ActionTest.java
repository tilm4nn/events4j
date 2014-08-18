package net.objectzoo.delegates;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Test;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ActionTest
{
	private final Object argument = new Object();
	
	@Test
	public void from_function_creates_action_that_invokes_function_with_correct_arguments()
	{
		Function mockFunction = mock(Function.class);
		
		Action action = Action.from(mockFunction);
		
		action.accept(argument);
		verify(mockFunction).apply(argument);
	}
	
	@Test
	public void from_consumer_creates_action_that_invokes_consumer_with_correct_arguments()
	{
		Consumer mockConsumer = mock(Consumer.class);
		
		Action action = Action.from(mockConsumer);
		
		action.accept(argument);
		verify(mockConsumer).accept(argument);
	}
	
	@Test
	public void bindParameter_creates_action0_that_ivokes_action_with_bound_arguments()
		throws Exception
	{
		Action mockAction = mock(Action.class);
		Action sut = (t) -> mockAction.accept(t);
		
		Action0 action0 = sut.bindParameter(argument);
		
		action0.start();
		verify(mockAction).accept(argument);
	}
}