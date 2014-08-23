package net.objectzoo.delegates;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Function4Test
{
    private final Object argument1 = new Object();
    private final Object argument2 = new Object();
    private final Object argument3 = new Object();
    private final Object argument4 = new Object();
    private final Object expectedResult = new Object();

    @Test
    public void toAction_creates_action_that_invokes_apply()
    {
        Function4 mockFunction = mock(Function4.class);
        Function4 sut = mockFunction::apply;

        Action4 action4 = sut.toAction();

        action4.accept(argument1, argument2, argument3, argument4);
        verify(mockFunction).apply(argument1, argument2, argument3, argument4);
    }

    @Test
    public void bindParameter_creates_function0_that_ivokes_function_with_bound_arguments()
    {
        Function4 mockFunction = mock(Function4.class);
        Function4 sut = mockFunction::apply;

        Function0 function0 = sut.bindParameters(argument1, argument2, argument3, argument4);

        doReturn(expectedResult).when(mockFunction).apply(anyObject(), anyObject(), anyObject(),
            anyObject());
        Object result = function0.get();
        verify(mockFunction).apply(argument1, argument2, argument3, argument4);
        assertThat(result, is(expectedResult));
    }
}
