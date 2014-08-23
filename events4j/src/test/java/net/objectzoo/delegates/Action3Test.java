package net.objectzoo.delegates;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Action3Test
{
    private final Object argument1 = new Object();
    private final Object argument2 = new Object();
    private final Object argument3 = new Object();

    @Test
    public void from_function3_creates_action3_that_invokes_function3_with_correct_arguments()
    {
        Function3 mockFunction = mock(Function3.class);

        Action3 action3 = Action3.from(mockFunction);

        action3.accept(argument1, argument2, argument3);
        verify(mockFunction).apply(argument1, argument2, argument3);
    }

    @Test
    public void bindParameters_creates_action0_that_ivokes_action_with_bound_arguments()
        throws Exception
    {
        Action3 mockAction = mock(Action3.class);
        Action3 sut = mockAction::accept;

        Action0 action0 = sut.bindParameters(argument1, argument2, argument3);

        action0.start();
        verify(mockAction).accept(argument1, argument2, argument3);
    }
}