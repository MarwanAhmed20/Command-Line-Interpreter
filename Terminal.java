package CommandLineInterpreter;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files ;
import java.nio.file.Path;
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
import java.nio.channels.FileChannel;
import java.nio.file.FileSystemException;
import java.io.FileNotFoundException;


public class Terminal {
	
	public static ArrayList<String> listOfhelp = new ArrayList<String>();
	public static void initializelistOfhelp() 
	{
		listOfhelp.add("cd: Changes current directory to another directory");
		listOfhelp.add("pwd: Displays the path of current directory");
		listOfhelp.add("cp: Copies file to any director");
		listOfhelp.add("mv: Moves files");
		listOfhelp.add("ls: List all the files in current directory");
		listOfhelp.add("rm: Removes a file ");
		listOfhelp.add("mkdir: Creates a new directory");
		listOfhelp.add("rmdir: Removes a directory");
		listOfhelp.add("clear: Clears the console");
		listOfhelp.add("cat: Displays contents of a file and concatenates files and display output");
		listOfhelp.add("help: Displays List all commands and functionalities");
		listOfhelp.add("args: Displays List all commands arguments");
		listOfhelp.add("more: Let us display and scroll down the output in one direction only");
		
	}
	
	
	
	public static void pwd (File f) {
		
		System.out.println(f.getAbsolutePath());
	}
	
	public static void print(File[] ar)
	{
		//System.out.println(ar.length);
		for(int i=0;i<ar.length;i++)
		{
			System.out.println(ar[i].getName());
		}
	}
	
	public static File[] ls (String currentDir, boolean Hidden) {

		File f = new File(currentDir) ;
		File arr[] = f.listFiles() ;
		//ArrayList<File> listOfCommands = new ArrayList<File>();
		//File ar[] = null ;
		
		if(Hidden==true)
		{
			for(int i=0;i<arr.length;i++)
			{
				if(arr[i].isHidden())
					System.out.print(".");
				   // System.out.print(arr[i].getName()+"\n");
				   // ar[i]=arr[i];
				//	listOfCommands.add(arr[i]);
			}
			return arr;
			//System.out.println();
		}else
		{
			for(int i=0 ;i<arr.length;i++)
			{
				if(!arr[i].isHidden())
				{
					//System.out.print(arr[i].getName()+"\n");
					//ar[i]=arr[i];
				}
			}
			return arr;
		}
	}
		
		
	static void clear() {
		
		for (int i=0 ; i < 10 ; i++ ) {
			System.out.println();
		}
	}
	
	public static File cd (String path, String curr) 
	{
		File x = new File(curr);
		for(int i=0;i<x.listFiles().length;i++) 
		{
			if(x.listFiles()[i].getName().equals(path))
				return x.listFiles()[i];
		}
		File temp = new File(path);
		if(!temp.exists()) 
		{
			temp = new File(curr);
		}
		return temp;
	}

	public static void cp(File arr[], String command) throws IOException
	{
		int l = command.lastIndexOf(' ') ;
		if(l==-1)
		{
			System.out.println("Not enough arguments for cp");
			return ;
		}
		String lastArg = command.substring(l+1, command.length()) ;
		String checker = Parser.lookup(arr, lastArg) ;
		if(checker.equals("Not found"))
		{
			System.out.println("destination not found");
			return ;
		}
		File dest = new File(checker) ;
		if(command.indexOf(' ')==l) 
		{
			String arg = command.substring(0, l) ;
			checker = Parser.lookup(arr, arg) ;
			if(checker.equals("Not found"))
			{
				System.out.println("Error: no such file named "+arg);
				return ;
			}
			try {
				
				File f = new File(checker) ;
				//System.out.println(f);
				Path fi = dest.toPath().resolve(f.toPath().getFileName());
				//System.out.println(fi);
				File dest2 =new File(fi.toString());
				//System.out.println(dest2);
				dest2.createNewFile();
			
				InputStream in = new FileInputStream(f);
				OutputStream out = new FileOutputStream(dest2);
			
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			}catch(FileNotFoundException e){}
		}
	}
	
	static void displayHelp () {
		
		initializelistOfhelp() ;
		for(int i=0;i<listOfhelp.size();i++)
		{
			System.out.print(listOfhelp.get(i)+"\n");	
		}
	}

	static void displayArgs(String arg)
	{
		if (arg.equals("cd"))
			System.out.println("cd: arg1: DestinationPath");
		else if (arg.equals("ls"))
			System.out.println("ls: takes no argument");
		else if (arg.equals("cp"))
			System.out.println("cp: arg1: SourcePath, arg2: DestinationPath");
		else if (arg.equals("mv"))
			System.out.println("mv:arg1: SourcePath, arg2: DestinationPath");
		else if (arg.equals("mkdir"))
			System.out.println("mkdir: arg1: name of a directory you want to craete");
		else if (arg.equals("rmdir"))
			System.out.println("rmdir: arg1: name of a directory you want to delete");
		else if (arg.equals("ls"))
			System.out.println("rm: arg1: name of a file you want to delete");
		else if (arg.equals("cat"))
			System.out.println("cat:  name of a file you want to display");
		else 
			System.out.println("invalid argument");
	}

	public static void mv(File arr[], String command) throws IOException
	{
		int l = command.lastIndexOf(' ') ;
		if(l==-1)
		{
			System.out.println("Not enough arguments for mv");
			return ;
		}
		String lastArg = command.substring(l+1, command.length()) ;
		String checker = Parser.lookup(arr, lastArg) ;
		if(checker.equals("Not found"))
		{
			System.out.println("Error: destination not found");
			return ;
		}
		File dest = new File(checker) ;
		if(command.indexOf(' ')==l) 
		{
			String arg = command.substring(0, l) ;
			checker = Parser.lookup(arr, arg) ;
			if(checker.equals("Not found"))
			{
				System.out.println("Error: no such file named "+arg);
				return ;
			}
			try {
				
				File f = new File(checker) ;
				Path fi = dest.toPath().resolve(f.toPath().getFileName());
				File dest2 =new File(fi.toString());
				dest2.createNewFile();
				Path fi2 = f.toPath();
				File f2 =new File(fi2.toString());
				InputStream in = new FileInputStream(f);
				OutputStream out = new FileOutputStream(dest2);
				
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) 
				{
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
				f2.delete() ;
			}catch(FileNotFoundException e){}
		}
	}

	public static boolean rm(String path, String curr) {
		String[] command = path.split("\\s+");
		if (command.length > 2) {
			return false;
		}
		if (command.length == 1) {
			File a = new File(curr + "\\" + path);
			if (!a.isDirectory() && a.exists())
				return a.delete();
			a = new File(path);
			if (!a.isDirectory())
				return a.delete();
		}
		if (command.length == 2) {
			if (command[0].equals("-d")) {
				File z = new File(curr + "\\" + command[1]);
				
				if (z.isDirectory()) {
					return z.delete();
				}
				z = new File(command[1]);
				
				if (z.isDirectory())
					return z.delete();
			} 
		}
		return false;
	}

	public static boolean mkdir(String pathOrname, String curr) {
		File f = new File(curr + "\\" + pathOrname);
		// System.out.println(curr + "\\" + pathOrname);
		boolean flag = f.mkdir();
		if (flag)
			return flag;
		File f2 = new File(pathOrname);
		boolean flag2 = f2.mkdir();
		if (flag2)
			return flag2;
		return false;
	}

	static void cat(File arr[], String fileName)
	{
		String absPath = Parser.lookup(arr, fileName) ;
		if(absPath.equals("Not found"))
		{
			System.out.println("No such file named: "+fileName);
			return ;
		}
		File f = new File(absPath) ;
		try {
			Scanner input = new Scanner(f) ;
			while(input.hasNextLine())
			{
				System.out.println(input.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void more(File arr[], String fileName) throws IOException {

		String absPath = Parser.lookup(arr, fileName) ;
		if(absPath.equals("Not found"))
		{
			System.out.println("No such file named: "+fileName);
			return ;
		}
		File f = new File(absPath) ;
		try {
			FileInputStream a = new FileInputStream(f);
			BufferedReader br = new BufferedReader(new InputStreamReader(a));
			String line;
			int x,counter = 0;
			Scanner in = new Scanner(System.in);
			while((line = br.readLine()) != null)
			{
				System.out.println(line);
				counter++;
				if (counter % 5 == 0) {
					System.out.println("press 1 to continue || 2 to quit ");
					x = in.nextInt();
					if (x == 2)
						break;
				}
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static String redirect(String command,File file,String mainCommand) {
		
		if(command.indexOf('>')!=command.lastIndexOf('>')&&command.lastIndexOf('>')-command.indexOf('>')!=1)
		{ 
			System.out.println("Invalid usage of operators (>) and (>>)");
			return "notfount";
		}
    	
    	File arr[] = file.listFiles() ;
		String file2Name = command.substring(command.lastIndexOf(' ')+1, command.length()) ;
		String checker = Parser.lookup(arr, file2Name) ;
		if(checker.equals("Not found")) 
		{
			System.out.println("No such file named: "+file2Name);
			return "notfount";
		}
		File f2 = new File(checker) ;
		if(mainCommand.equals("pwd"))
		{
			if(command.contains(">>"))
				try {
					Files.write(f2.toPath(), file.getAbsolutePath().getBytes(), StandardOpenOption.APPEND) ;
				} catch (IOException e) {
					e.printStackTrace();
				}
			else
				try {
					Files.write(f2.toPath(), file.getAbsolutePath().getBytes(), StandardOpenOption.TRUNCATE_EXISTING) ;
				} catch (IOException e) {
					e.printStackTrace();
				}
		}else if(mainCommand.equals("ls"))
		{
			if(command.contains(">>"))
				try {
					Files.write(f2.toPath(), Arrays.toString(arr).getBytes(), StandardOpenOption.APPEND) ;
				} catch (IOException e) {
					e.printStackTrace();
				}
			else
				try {
					Files.write(f2.toPath(), Arrays.toString(arr).getBytes(), StandardOpenOption.TRUNCATE_EXISTING) ;
					
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		else if(mainCommand.equals("cat"))
		{	
			Scanner input = new Scanner(System.in);
			String s;
			boolean t=true;
			if(command.contains(">>"))
			{
				try {
					for(int i=0;t==true;i++) {
						s=input.next();
						
						if(s.equals("z")) {
							t=false;
							return s;
						}
						else {
							Files.write(f2.toPath(), s.getBytes(), StandardOpenOption.APPEND) ;
						}
						
					}
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else
			{
				f2.delete();
				try {
					File f1 = new File(checker) ;
					f1.createNewFile();
					for(int i=0;t==true;i++) {
						s=input.next();
						
						if(s.equals("1")) {
							t=false;
							return s;
						}
						else {
							Files.write(f1.toPath(), s.getBytes(), StandardOpenOption.APPEND) ;
						}
						
					}
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			}
		else if(mainCommand.equals("help"))
		{
			
			if(command.contains(">>"))
				try {
					Files.write(f2.toPath(), listOfhelp.toString().getBytes(), StandardOpenOption.APPEND) ;
				} catch (IOException e) {
					e.printStackTrace();
				}
			else
				try {
					Files.write(f2.toPath(), listOfhelp.toString().getBytes(), StandardOpenOption.TRUNCATE_EXISTING) ;
					
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		else if(mainCommand.equals("date"))
		{
			
			if(command.contains(">>"))
				try {
					Date date = new Date() ;
					Files.write(f2.toPath(), date.toString().getBytes(), StandardOpenOption.APPEND) ;
				} catch (IOException e) {
					e.printStackTrace();
				}
			else
				try {
					Date date = new Date() ;
					Files.write(f2.toPath(), date.toString().getBytes(), StandardOpenOption.TRUNCATE_EXISTING) ;
					
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		else if(mainCommand.equals("cat"))
		{	
			Scanner input = new Scanner(System.in);
			String s;
			boolean t=true;
			if(command.contains(">>"))
			{
				try {
					for(int i=0;t==true;i++) {
						s=input.next();
						
						if(s.equals("z")) {
							t=false;
							return s;
						}
						else {
							Files.write(f2.toPath(), s.getBytes(), StandardOpenOption.APPEND) ;
						}
						
					}
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else
			{
				try {
					for(int i=0;t==true;i++) {
						s=input.next();
						
						if(s.equals("z")) {
							t=false;
							return s;
						}
						else {
							Files.write(f2.toPath(), s.getBytes(), StandardOpenOption.TRUNCATE_EXISTING) ;
						}
						
					}
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		}
		
		
		return "found";
		
	}
	
	
}
