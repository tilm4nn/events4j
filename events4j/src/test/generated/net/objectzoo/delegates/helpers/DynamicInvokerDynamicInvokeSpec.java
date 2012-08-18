package net.objectzoo.delegates.helpers;

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;
import net.objectzoo.delegates.Func0;
import net.objectzoo.delegates.helpers.DynamicInvoker;
import net.objectzoo.delegates.helpers.DynamicInvokerSpec;
import net.objectzoo.delegates.helpers.MyRuntimeException;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.hamcrest.StringDescription;
import org.jnario.lib.Should;
import org.jnario.runner.ExampleGroupRunner;
import org.jnario.runner.Named;
import org.jnario.runner.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@SuppressWarnings("all")
@RunWith(ExampleGroupRunner.class)
@Named("dynamicInvoke")
public class DynamicInvokerDynamicInvokeSpec extends DynamicInvokerSpec {
  @Test
  @Named("throws exception on wrong argument count")
  @Order(99)
  public void throwsExceptionOnWrongArgumentCount() throws Exception {
    final Procedure1<Object> _function = new Procedure1<Object>() {
        public void apply(final Object o) {
        }
      };
    DynamicInvoker _dynamicInvoker = new DynamicInvoker(Action.class, new Action<Object>() {
        public void invoke(Object parameter) {
          _function.apply(parameter);
        }
    });
    final DynamicInvoker sut = _dynamicInvoker;
    try{
      Object _object = new Object();
      Object _object_1 = new Object();
      sut.dynamicInvoke(_object, _object_1);
      Assert.fail("Expected " + IllegalArgumentException.class.getName() + " in \n     sut.dynamicInvoke(new Object, new Object)\n with:"
       + "\n     sut is " + new StringDescription().appendValue(sut).toString()
       + "\n     new Object is " + new StringDescription().appendValue(_object).toString());
    }catch(IllegalArgumentException e){
      // expected
    }
  }
  
  @Test
  @Named("throws original RuntimeException")
  @Order(99)
  public void throwsOriginalRuntimeException() throws Exception {
    final Procedure0 _function = new Procedure0() {
        public void apply() {
          MyRuntimeException _myRuntimeException = new MyRuntimeException();
          throw _myRuntimeException;
        }
      };
    DynamicInvoker _dynamicInvoker = new DynamicInvoker(Action0.class, new Action0() {
        public void invoke() {
          _function.apply();
        }
    });
    final DynamicInvoker sut = _dynamicInvoker;
    try{
      sut.dynamicInvoke();
      Assert.fail("Expected " + MyRuntimeException.class.getName() + " in \n     sut.dynamicInvoke\n with:"
       + "\n     sut is " + new StringDescription().appendValue(sut).toString());
    }catch(MyRuntimeException e){
      // expected
    }
  }
  
  @Test
  @Named("invokes action with correct arguments")
  @Order(99)
  public void invokesActionWithCorrectArguments() throws Exception {
    final Action mockAction = Mockito.<Action>mock(Action.class);
    DynamicInvoker _dynamicInvoker = new DynamicInvoker(Action.class, mockAction);
    final DynamicInvoker sut = _dynamicInvoker;
    Object _object = new Object();
    final Object argument = _object;
    sut.dynamicInvoke(argument);
    Action _verify = Mockito.<Action<Object>>verify(mockAction);
    _verify.invoke(argument);
  }
  
  @Test
  @Named("returns correct return value")
  @Order(99)
  public void returnsCorrectReturnValue() throws Exception {
    Object _object = new Object();
    final Object expected = _object;
    final Function0<Object> _function = new Function0<Object>() {
        public Object apply() {
          return expected;
        }
      };
    DynamicInvoker _dynamicInvoker = new DynamicInvoker(Func0.class, new Func0<Object>() {
        public Object invoke() {
          return _function.apply();
        }
    });
    final DynamicInvoker sut = _dynamicInvoker;
    final Object actual = sut.dynamicInvoke();
    boolean _should_be = Should.should_be(actual, expected);
    Assert.assertTrue("\nExpected actual should be expected but"
     + "\n     actual is " + new StringDescription().appendValue(actual).toString()
     + "\n     expected is " + new StringDescription().appendValue(expected).toString() + "\n", _should_be);
    
  }
}
