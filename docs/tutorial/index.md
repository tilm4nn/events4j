# Delegates

Delegates are placeholders for regular Java methods that can be stored in fields or used as parameters in method calls. There are different categories of delegates: standard delegates and asynchronous delegates. And there are two different types of delegates: functions that have a return value and actions that don't.

## Standard Delegates

### Actions

The most simple form of a standard delegate is an action. The action represents a Java method without return value that takes one argument parameter and is defined by the following interface:

    public interface Action<T>
    {
        void invoke(T parameter);
    }

To create an action you simply implement the `Action<T>` interface.

	Action<String> printlnAction = new Action<String>()
	{
		@Override
		public void invoke(String parameter)
		{
			System.out.println(parameter);
		}
	};

And then the action can be called of course.

    printlnAction.invoke("Print this!");

In addition to the `Action<T>` there are also an `Action0`, `Action2<T1, T2>`, `Action3<T1, T2, T3>` and `Action4<T1, T2, T3, T4>` that take zero, two, three or four parameters as well as the `DynamicAction` that takes an arbitrary number of parameters.

### Funcs

Functions, or short funcs, are similar to actions with the addition that they have a return value. Here is the definition of the `Func<T, R>` interface.

    public interface Func<T, R>
    {
        R invoke(T parameter);
    }

And an exemplary implementation

	Func<String, String> toUpperCaseFunc = new Func<String, String>()
	{		
		@Override
		public String invoke(String parameter)
		{
			return parameter.toUpperCase();
		}
	};

Of course the func delegate can easily be called and there are plenty of additional implementations like `Func0<R>`, `Func2<T1, T2, R>` ... `DynamicFunc` but there is no magic about that. The interesting part starts with asynchronous delegates and events.

## Asynchronous Delegates

Let's say you want to call a function asynchronously in another thread. (Actions will work in a similar manner.) The only thing you have to do is to convert it to an asynchronous function and call that instead.

    FuncAsync<String, String> toUpperCaseFuncAsync = new FuncToFuncAsync<String, String>(toUpperCaseFunc);
    toUpperCaseFuncAsync.beginInvoke(null, null, "Upper case this!");

But what does an `FuncAsync<T, R>` look like? Here it is:

    public interface FuncAsync<T, R>
    {
    	FuncAsyncResult<R> beginInvoke(FuncAsyncCallback<? super R> callback,
                                       Object asyncState,
                                       T parameter);
    }

This might be familiar to some of you who already used asynchronous delegates in .NET. The call of the asynchronous function takes an optional callback that is invoked upon completion, an optional async state object that can be used to forward arbitrary information to the callback and the function's parameter of course.  
The return value of the call is a `FundAsyncResult<R>` that can be used to wait for the asynchronous call to complete and retrieve the return value. This result object is also given to the callback automatically:

    public interface FuncAsyncCallback<R>
    {
    	void invocationFinished(FuncAsyncResult<R> result);
    }

There is nothing special about the callback so let's straight look at the biggest part, the asynchronous result:

    public interface FuncAsyncResult<R> extends ActionAsyncResult
    {
     	Object getAsyncState();
    	boolean isDone();
    	R endInvokeReturn() throws InterruptedException, ExecutionException;
    	R endInvokeReturn(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException,
    		TimeoutException;

        // Some other methods skipped...
    }

Here is the async state object again! The method `isDone()` allows to check whether  the invocation of the function is finished. A special role play the two `endInvokeReturn(...)` methods. They wait until the invocation is finished and then return the result of the invocation. This might be the return value of the function or an exception thrown by the function supplied as cause to the  `ExecutionException` that is thrown in this case.

Putting it all together and using the `FuncAsyncSupport<T, R>` gives us a really small exampe of usage:

	FuncAsync<String, String> toUpperCaseAsyncFunc = new FuncAsyncSupport<String, String>()
	{
		@Override
		public String invoke(String parameter)
		{
			return parameter.toUpperCase();
		}
	};
	
	FuncAsyncCallback<String> printlnAsyncCallback = new FuncAsyncCallback<String>()
	{
		@Override
		public void invocationFinished(FuncAsyncResult<String> result)
		{
			try
			{
				System.out.println(result.endInvokeReturn());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	};
	
	toUpperCaseAsyncFunc.beginInvoke(printlnAsyncCallback, null, "Upper case this!");

# Events

An event is a source of signals and/or information that can be observed by subscribing action instances to it:

    public interface Event<T>
    {
    	void subscribe(Action<? super T> action);
    	void unsubscribe(Action<? super T> action);
    }

As already seen for the delegates this interface also exists in several variants from `Event0` to `DynamicEvent`.

Events4J offers some predefined implementations of the event interfaces for example the

    public class EventDistributor<T> implements Action<T>, Event<T>
    {
    	// Methods skipped
    }

that not only implements the the event interface but also the action interface, that can be called to fire the event and thus have the distributor invoke all subscribed action instances.

Actions combined with events make it easy to create a software design with loose coupling between separate functional units as shown in the following example.

    class FileReader
    {
    	private EventDistributor<String> lineReadDistributor = new EventDistributor<String>();
    	
    	public Event<String> lineReadEvent()
    	{
    		return lineReadDistributor;
    	}
    	
    	public void readFile(String filename) throws IOException
    	{
    		BufferedReader reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(filename)));
    		try
    		{
    			for (String line = reader.readLine(); line != null; line = reader.readLine())
    			{
    				lineReadDistributor.invoke(line);
    			}
    		}
    		finally
    		{
    			reader.close();
    		}
    	}
    }
    
    class ConsoleWriter
    {
    	public Action<String> writeToConsoleAction()
    	{
    		return writeToConsoleAction;
    	}
    	
    	private Action<String> writeToConsoleAction = new Action<String>()
    	{
    		@Override
    		public void invoke(String line)
    		{
    			System.out.println(line);
    		}
    	};
    }
    
    public class Program
    {
    	public static void main(String... args) throws IOException
    	{
    		FileReader fileReader = new FileReader();
    		ConsoleWriter consoleWriter = new ConsoleWriter();
    		
    		fileReader.lineReadEvent().subscribe(consoleWriter.writeToConsoleAction());
    		
    		fileReader.readFile("test.txt");
    	}
    }