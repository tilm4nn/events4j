package net.objectzoo.delegates;

import org.junit.Test;

import java.util.function.Supplier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("rawtypes")
public class Action0Test
{
    @Test
    public void from_supplier_creates_action0_that_invokes_supplier()
    {
        Supplier mockSupplier = mock(Supplier.class);

        Action0 action0 = Action0.from(mockSupplier);

        action0.start();
        verify(mockSupplier).get();
    }

    @Test
    public void toRunnable_creates_runnable_that_calls_start() throws Exception
    {
        Action0 mockAction = mock(Action0.class);
        Action0 sut = mockAction::start;

        Runnable runnable = sut.toRunnable();

        runnable.run();
        verify(mockAction).start();
    }

}