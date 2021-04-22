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




public class CommandLine {

	//private static ArrayList<String> listOfCommands = new ArrayList<String>();
	
	public static void main(String[] args) throws IOException
	{
		Parser.initializeListOfCommands();
		File file = new File("C:\\");
		Scanner input = new Scanner(System.in);
		String command;
		
		while (true) {
			System.out.print(file.getAbsolutePath() + " ");
			command = input.nextLine(); // the input
			String[] arrOfCommands = command.split(";");
			//System.out.println(arrOfCommands);
			for(int j=0;j<arrOfCommands.length;j++)
			{	
				command = arrOfCommands[j];
				boolean isValid = false;
				int idx = -1;
				int check=0;
				String argument = new String(); 
				String mainCommand = new String(); 
				String subCommand = new String();
				String submainCommand = new String();
				String subSubCommand=new String();
				String h=new String();
				String argumentSubSubCommand=new String();
				for(int i=0;i<command.length();i++) 
				{
					if (command.contains("|"))
					{
						int com=command.indexOf("|");
						int subcom=command.indexOf(" ");
						
						submainCommand=command.substring(0,com);
						
						mainCommand=submainCommand.substring(0,subcom);
						
						
						if(Parser.validate(mainCommand))
						{
							argument = command.substring(subcom+1, submainCommand.length());
							idx=com+2;
							subCommand =command.substring(idx,command.length());
							if(subCommand.contains(" "))
							{
								int indexSubCommand=subCommand.indexOf(" ");
								subSubCommand=subCommand.substring(0,indexSubCommand);
								if(Parser.validate(subSubCommand))
								{
									argumentSubSubCommand=subCommand.substring(indexSubCommand+1,subCommand.length());
									isValid = true;
									check=2;
									break;		
								}
							}else
							{
								if(Parser.validate(subCommand))
								{
								subSubCommand=subCommand;
								isValid = true;
								check=2;
								break;
								}
							}
						}	
					}
					else if (command.contains(">")) {
						int subcom=command.indexOf(" ");
						mainCommand = command.substring(0, subcom);
						check=3;
						isValid = true;
					}
					else if(command.charAt(i)==' ' && !command.contains("|") && !command.contains(">"))
					{
						//System.out.println("hello");
						mainCommand = command.substring(0, i);
						if(Parser.validate(mainCommand)) 
						{
							
							idx = i+1;							
							argument = command.substring(idx, command.length());
							isValid = true;
							check=1;
							break;
						}
					}
					else if(i==command.length()-1) 
					{
						mainCommand = command.substring(0,i+1);
						if(Parser.validate(mainCommand)) 
						{
							isValid = true;
							check=1;
							break;
						}	
					}
					
				}
				
				if(!isValid) 
				{
					System.out.println("command not exist!");
					continue;
				}
			    if(check==1)
			    {
			    	
			    	file=Parser.toCall(subCommand,command,mainCommand,file,argument,idx);
			    	
			    }
			    else if (check==2)
			    {
			    
			    	file=Parser.toCall(submainCommand,command,mainCommand,file,argument,idx);
			    	System.out.println(file);
			    	file = Parser.toCallPipe(subCommand,file,subSubCommand,argumentSubSubCommand,idx,argument);
			    }
			    else if (check==3) {
			    	if(Terminal.redirect(command, file, mainCommand).equals("found"))
			    	{
				    	Terminal.redirect(command, file, mainCommand);
			    	}
			    }
			}
		}
	}
}

