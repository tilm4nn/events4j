package net.objectzoo.delegates;

import org.junit.Test;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Action2Test
{
    private final Object argument1 = new Object();
    private final Object argument2 = new Object();

    @Test
    public void from_bifunction_creates_action2_that_invokes_bifunction_with_correct_arguments()
    {
        BiFunction mockFunction = mock(BiFunction.class);

        Action2 action2 = Action2.from(mockFunction);

        action2.accept(argument1, argument2);
        verify(mockFunction).apply(argument1, argument2);
    }

    @Test
    public void from_biconsumer_creates_action2_that_invokes_biconsumer_with_correct_arguments()
    {
        BiConsumer mockConsumer = mock(BiConsumer.class);

        Action2 action2 = Action2.from(mockConsumer);

        action2.accept(argument1, argument2);
        verify(mockConsumer).accept(argument1, argument2);
    }

    @Test
    public void bindParameters_creates_action0_that_ivokes_action_with_bound_arguments()
        throws Exception
    {
        Action2 mockAction = mock(Action2.class);
        Action2 sut = mockAction::accept;

        Action0 action0 = sut.bindParameters(argument1, argument2);

        action0.start();
        verify(mockAction).accept(argument1, argument2);
    }
}