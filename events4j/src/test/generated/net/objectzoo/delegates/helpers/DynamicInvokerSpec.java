package net.objectzoo.delegates.helpers;

import net.objectzoo.delegates.helpers.DynamicInvokerDynamicInvokeSpec;
import net.objectzoo.delegates.helpers.DynamicInvokerGetInvokeMethodSpec;
import org.jnario.runner.Contains;
import org.jnario.runner.ExampleGroupRunner;
import org.jnario.runner.Named;
import org.junit.runner.RunWith;

@Contains({ DynamicInvokerDynamicInvokeSpec.class, DynamicInvokerGetInvokeMethodSpec.class })
@SuppressWarnings("all")
@RunWith(ExampleGroupRunner.class)
@Named("DynamicInvoker")
public class DynamicInvokerSpec {
}
