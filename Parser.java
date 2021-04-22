package CommandLineInterpreter;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files ;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.nio.file.FileSystemException;




public class Parser {
	private static ArrayList<String> listOfCommands = new ArrayList<String>();
	
	public static void initializeListOfCommands() 

	{
		listOfCommands.add("clear");
		listOfCommands.add("cd");
		listOfCommands.add("cp");
		listOfCommands.add("mkdir");
		listOfCommands.add("ls");
		listOfCommands.add("pwd");
		listOfCommands.add("mv");
		listOfCommands.add("rm");
		listOfCommands.add("cat");
		listOfCommands.add("more");
		listOfCommands.add("rmdir");
		listOfCommands.add("help");
		listOfCommands.add("args");
		listOfCommands.add("date");
		
	}

	static String lookup(File arr[], String fileName)
	{
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i].getName().equals(fileName))
				return arr[i].getAbsolutePath() ;
		}
		return "Not found" ;
	}
	
	public static boolean validate(String command) 
	{
		if(listOfCommands.contains(command))
			return true;
		else
			return false;
	}

	
	public static File toCall(String submainCommand,String command,String mainCommand, File file, String argument , int idx) throws IOException {
		int j=0;
		/*String secondArgument=new String();
		if(argument.contains(" "))
		{
			int in=argument.indexOf(" ");
			if(in==argument.length())
			{
			argument=argument.substring(0,in);
			j=1;
			}
			else
			{
				argument=argument.substring(0,in);
				//secondArgument=argument.substring(in+1,argument.length());
				j=2;
			}
		}*/
		if(mainCommand.equals("cd")) 
		{
			if (file.equals(Terminal.cd(argument,file.getAbsolutePath())) && idx != -1)
				System.out.println("invalid path!");
			else
				file = Terminal.cd(argument, file.getAbsolutePath());	
		}
		
		else if(mainCommand.equals("pwd")) 
		{
			if(argument.length()>0)
				System.out.println("pwd doesn't take arg ");	
			else 
				Terminal.pwd(file); 
		}
			
		else if(mainCommand.equals("ls"))
		{
			if(argument.length()>0)
			{
				if(!argument.equals("-a"))
					System.out.println("Invalid argument");
				else
				{
					File[] tr=Terminal.ls (file.getAbsolutePath(), true);
					Terminal.print(tr);
				}
			}else
			{
			    File[] tr = Terminal.ls (file.getAbsolutePath(), false);
				Terminal.print(tr);
			}
		}
		
		else if(mainCommand.equals("cp"))
		{
			File arr[] = file.listFiles() ;
			try {
				Terminal.cp (arr, argument) ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		else if(mainCommand.equals("mv"))
		{
			File arr[] = file.listFiles() ;
			try {
				Terminal.mv(arr, argument) ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		else if(mainCommand.equals("clear"))
		{
			Terminal.clear() ;
		}
		
		else if(mainCommand.equals("cat"))
		{
			File arr[] = file.listFiles();
			if(j==1)
			{
			submainCommand=submainCommand.substring(0,submainCommand.length()-1);
			}
			if(submainCommand.indexOf(" ")==submainCommand.lastIndexOf(" "))
			{
				Terminal.cat(arr, argument);
			}
		}

		else if(mainCommand.equals("date"))
		{
			Date date = new Date() ;
			System.out.println(date.toString());
		}
		
		else if(mainCommand.equals("help"))
		{
			Terminal.displayHelp() ;
		}
		
		else if(mainCommand.equals("args"))
		{
			Terminal.displayArgs(argument) ;
		}
		
		else if(mainCommand.equals("mkdir")) 
		{
			if(idx == -1)
			{
				System.out.println("missing directory name");
			}else
			{
				if(!Terminal.mkdir(argument, file.getAbsolutePath()))
				{
					System.out.println("already exist please try again");
				}
			}
		}
		
		else if(mainCommand.equals("rm")) 
		{
			if(!Terminal.rm(argument, file.getAbsolutePath())) 
			
				System.out.println("Wrong Pramaters Please try again");
			
		}
		
		else if(mainCommand.equals("rmdir")) 
		{
			if(!Terminal.rm("-d " + argument, file.getAbsolutePath())) 
				System.out.println("Wrong pramater Please try again");
			
		}
		
		else if(mainCommand.equals("more"))
		{
			File arr[] = file.listFiles() ;
			if(idx == -1)
				System.out.println("Not enough arguments for more");
			else
				Terminal.more(arr, argument);
		}		return file;
	}
	
	public static File toCallPipe(String subCommand,File file,String sb ,String h, int idx, String argument) throws IOException
	{
		int j=0;
		if(argument.contains(" "))
		{
			int in=argument.indexOf(" ");
			argument=argument.substring(0,argument.length()-1);
			j=1;
		}
		
		System.out.println(argument);
		if(sb.equals("cd")) 
		{
			if (file.equals(Terminal.cd(h,file.getAbsolutePath())) && idx != -1)
				System.out.println("invalid path!");
			else
				file = Terminal.cd(h, file.getAbsolutePath());	
		}
		else if(sb.equals("pwd")) 
		{
			if(h.length()>0)
				System.out.println("pwd doesn't take arg ");	
			else 
			{
				Terminal.pwd(file); 
			}
		}
		else if(sb.equals("ls"))
		{
			if(h.length()>0)
			{
				if(!h.equals("-a"))
					System.out.println("Invalid argument");
				else
				{
					File[] tr=Terminal.ls (file.getAbsolutePath(), true);
					Terminal.print(tr);
				}
			}else
			{
			    File[] tr = Terminal.ls (file.getAbsolutePath(), false);
				Terminal.print(tr);
			}
		}
		else if(sb.equals("cp"))
		{
			File arr[] = file.listFiles() ;
			try {
				
				Terminal.cp (arr, argument) ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		else if(sb.equals("mv"))
		{
			File arr[] = file.listFiles() ;
			System.out.println(argument + "   "+h);
			try {
				Terminal.mv(arr, argument) ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		else if(sb.equals("clear"))
		{
			Terminal.clear() ;
		}
		
		else if(sb.equals("cat"))
		{
			File arr[] = file.listFiles() ;
			if(subCommand.indexOf(' ')==subCommand.lastIndexOf(' '))
			{
				Terminal.cat(arr, h) ;
			}
		}

		else if(sb.equals("date"))
		{
			Date date = new Date() ;
			System.out.println(date.toString());
		}
		
		else if(sb.equals("help"))
		{
			Terminal.displayHelp() ;
		}
		
		else if(sb.equals("args"))
		{
			Terminal.displayArgs(argument) ;
		}
		
		else if(sb.equals("mkdir")) 
		{
			if(idx == -1)
			{
				System.out.println("missing directory name");
			}else
			{
				if(!Terminal.mkdir(h, file.getAbsolutePath()))
				{
					System.out.println("already exist please try again");
				}
			}
		}
		
		else if(sb.equals("rm")) 
		{
			if(!Terminal.rm(h, file.getAbsolutePath())) 
			
				System.out.println("Wrong Pramaters Please try again");
			
		}
		
		else if(sb.equals("rmdir")) 
		{
			if(!Terminal.rm("-d " + h, file.getAbsolutePath())) 
				System.out.println("Wrong pramater Please try again");
			
		}
		
		else if(sb.equals("more"))
		{
			File arr[] = file.listFiles() ;
			if(idx == -1)
				System.out.println("Not enough arguments for more");
			else
				Terminal.more(arr, h);
		}
		
		return file;
		
	}
	
	
	
}
