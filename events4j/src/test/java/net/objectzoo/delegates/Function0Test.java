package net.objectzoo.delegates;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Function0Test
{
    private final Object expectedResult = new Object();

    @Test
    public void toCallable_creates_callable_that_invokes_get() throws Exception
    {
        Function0 mockFunction = mock(Function0.class);
        Function0 sut = mockFunction::get;

        Callable callable = sut.toCallable();

        doReturn(expectedResult).when(mockFunction).get();
        Object result = callable.call();
        verify(mockFunction).get();
        assertThat(result, is(expectedResult));
    }

    @Test
    public void toAction_creates_action0_that_invokes_get()
    {
        Function0 mockFunction = mock(Function0.class);
        Function0 sut = mockFunction::get;

        Action0 action0 = sut.toAction();

        action0.start();
        verify(mockFunction).get();
    }

    @Test
    public void from_supplier_creates_function0_that_invokes_supplier() throws Exception
    {
        Supplier mockSupplier = mock(Supplier.class);

        Function0 function0 = Function0.from(mockSupplier);

        doReturn(expectedResult).when(mockSupplier).get();
        Object result = function0.get();
        verify(mockSupplier).get();
        assertThat(result, is(expectedResult));
    }
}
