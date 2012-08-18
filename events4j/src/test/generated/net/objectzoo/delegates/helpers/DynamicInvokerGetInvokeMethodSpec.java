package net.objectzoo.delegates.helpers;

import java.lang.reflect.Method;
import net.objectzoo.delegates.Func0;
import net.objectzoo.delegates.helpers.DynamicInvoker;
import net.objectzoo.delegates.helpers.DynamicInvokerSpec;
import org.hamcrest.StringDescription;
import org.jnario.lib.Should;
import org.jnario.runner.ExampleGroupRunner;
import org.jnario.runner.Named;
import org.jnario.runner.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@SuppressWarnings("all")
@RunWith(ExampleGroupRunner.class)
@Named("getInvokeMethod")
public class DynamicInvokerGetInvokeMethodSpec extends DynamicInvokerSpec {
  @Test
  @Named("returns correct method")
  @Order(99)
  public void returnsCorrectMethod() throws Exception {
    final Method expected = Func0.class.getMethod("invoke");
    final Method actual = DynamicInvoker.getInvokeMethod(Func0.class, "invoke");
    boolean _should_be = Should.should_be(actual, expected);
    Assert.assertTrue("\nExpected actual should be expected but"
     + "\n     actual is " + new StringDescription().appendValue(actual).toString()
     + "\n     expected is " + new StringDescription().appendValue(expected).toString() + "\n", _should_be);
    
  }
  
  @Test
  @Named("throws exception on invalid method name")
  @Order(99)
  public void throwsExceptionOnInvalidMethodName() throws Exception {
    try{
      DynamicInvoker.getInvokeMethod(Object.class, "invoke");
      Assert.fail("Expected " + IllegalArgumentException.class.getName() + " in \n     getInvokeMethod(typeof(Object), \"invoke\")\n with:"
       + "\n     getInvokeMethod(typeof(Object), \"invoke\") is " + new StringDescription().appendValue(DynamicInvoker.getInvokeMethod(Object.class, "invoke")).toString());
    }catch(IllegalArgumentException e){
      // expected
    }
  }
}
