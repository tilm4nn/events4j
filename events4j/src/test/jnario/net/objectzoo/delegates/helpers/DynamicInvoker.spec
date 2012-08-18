package net.objectzoo.delegates.helpers

import net.objectzoo.delegates.Action0
import net.objectzoo.delegates.Func0
import net.objectzoo.delegates.Action

import static net.objectzoo.delegates.helpers.DynamicInvoker.*
import static org.mockito.Mockito.*

import static extension org.jnario.lib.Should.*

describe DynamicInvoker
{
	context "dynamicInvoke"
	{
		fact "throws exception on wrong argument count"
		{
			val sut = new DynamicInvoker(typeof(Action),
				[ Object o | ]
			)
			
			sut.dynamicInvoke(new Object, new Object) should throw IllegalArgumentException
		}
		
		fact "throws original RuntimeException"
		{
			val sut = new DynamicInvoker(typeof(Action0),
				[ | throw new MyRuntimeException]
			)
		
			sut.dynamicInvoke should throw MyRuntimeException
		}
		
		fact "invokes action with correct arguments"
		{
			val mockAction = mock(typeof(Action))
			val sut = new DynamicInvoker(typeof(Action), mockAction)
			val argument = new Object
		
			sut.dynamicInvoke(argument)
		
			verify(mockAction).invoke(argument)
		}
		
		fact "returns correct return value"
		{
			val expected = new Object
			val sut = new DynamicInvoker(typeof(Func0),
				[ | expected]
			)
			
			val actual = sut.dynamicInvoke
			
			actual should be expected
		}
	}
	
	context "getInvokeMethod"
	{
		fact "returns correct method"
		{
			val expected = typeof(Func0).getMethod("invoke")
			
			val actual = getInvokeMethod(typeof(Func0), "invoke")
			
			actual should be expected
		}
		
		fact "throws exception on invalid method name"
		{
			getInvokeMethod(typeof(Object), "invoke") should throw IllegalArgumentException
		}
	}
}
