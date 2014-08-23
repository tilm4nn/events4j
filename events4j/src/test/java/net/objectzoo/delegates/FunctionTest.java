package net.objectzoo.delegates;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class FunctionTest
{
    private final Object argument = new Object();
    private final Object expectedResult = new Object();

    @Test
    public void toAction_creates_action_that_invokes_apply()
    {
        Function mockFunction = mock(Function.class);
        Function sut = mockFunction::apply;

        Action action = sut.toAction();

        action.accept(argument);
        verify(mockFunction).apply(argument);
    }

    @Test
    public void from_function_creates_function_that_invokes_function()
    {
        java.util.function.Function mockFunction = mock(java.util.function.Function.class);

        Function function = Function.from(mockFunction);

        doReturn(expectedResult).when(mockFunction).apply(anyObject());
        Object result = function.apply(argument);
        verify(mockFunction).apply(argument);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void bindParameter_creates_function0_that_ivokes_function_with_bound_arguments()
    {
        Function mockFunction = mock(Function.class);
        Function sut = mockFunction::apply;

        Function0 function0 = sut.bindParameter(argument);

        doReturn(expectedResult).when(mockFunction).apply(anyObject());
        Object result = function0.get();
        verify(mockFunction).apply(argument);
        assertThat(result, is(expectedResult));
    }
}
