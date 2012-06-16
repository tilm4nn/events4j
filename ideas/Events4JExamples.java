import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Func;
import net.objectzoo.delegates.FuncAsync;
import net.objectzoo.delegates.FuncAsyncCallback;
import net.objectzoo.delegates.FuncAsyncResult;
import net.objectzoo.delegates.adapters.FuncToFuncAsync;
import net.objectzoo.delegates.impl.FuncAsyncSupport;
import net.objectzoo.events.Event;
import net.objectzoo.events.impl.EventDistributor;

class test2
{
	Action<String> writelineAction = new Action<String>()
	{
		@Override
		public void invoke(String s)
		{
			System.out.println(s);
		}
	};
	
	Func<String, String> toUpperCaseFunc = new Func<String, String>()
	{
		@Override
		public String invoke(String parameter)
		{
			return parameter.toUpperCase();
		}
	};
	
	void test()
	{
		writelineAction.invoke("Print this!");
		
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
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (ExecutionException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		toUpperCaseAsyncFunc.beginInvoke(printlnAsyncCallback, null, "Upper case this!");
	}
	
	FuncAsync<String, String> toUpperCaseAsyncFunc = new FuncToFuncAsync<String, String>(
		toUpperCaseFunc);
	
}

class FileReader
{
	private EventDistributor<String> lineReadDistributor = new EventDistributor<String>();
	
	public Event<String> lineReadEvent()
	{
		return lineReadDistributor;
	}
	
	public void readFile(String filename) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
			filename)));
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

public class test
{
	public static void main(String... args) throws IOException
	{
		FileReader fileReader = new FileReader();
		ConsoleWriter consoleWriter = new ConsoleWriter();
		
		fileReader.lineReadEvent().subscribe(consoleWriter.writeToConsoleAction());
		
		fileReader.readFile("test.txt");
	}
}
