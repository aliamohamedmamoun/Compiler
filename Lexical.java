package compiler;





import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexical {
	
//static	Parser parse=new Parser();
	public static void main(String[] args) throws IOException {
		Long start = System.currentTimeMillis();
		Parser parse=new Parser();
		Hashtable<String,String> TokenCoding = new Hashtable<String,String> () {{
		    put("PROGRAM","1");
		    put("VAR","2");
		    put("BEGIN","3");
		    put("END","4");
		    put("END.","5");
		    put("FOR","6");
		    put("READ","7");
		    put("WRITE","8");
		    put("TO","9");
		    put("DO","10");
		    put(";","11");
		    put(":=","12");
		    put("+","13");
		    put(",","14");
		    put("(","15");
		    put(")","16");
		    put("id","17");
		    put("*","18");
		  
		     }};
		   //read mn file line by line
			 //String[] identifier = null ;

			   FileReader readProgram=null;
				int noOfRows=0 ;
			    readProgram = new FileReader("input.txt");
			    Scanner programScanner= new Scanner(readProgram);
			    List<String> lines = new ArrayList<String>();
			    while(programScanner.hasNextLine()){
				lines.add(programScanner.nextLine());

				noOfRows++;
			}
			String[] line = lines.toArray(new String[noOfRows]);//array fe el file line by line
			
			 String strInp = new String();
			 //String fullCheckRegex = "\\s*(PROGRAM)\\s+([A-Z][A-Z0-9]*)\\s+(VAR)\\s+([A-Z][A-Z0-9]*(?:\\s*,\\s*[A-Z][A-Z0-9]*)*)\\s+(BEGIN)\\s+((?:(?:[A-Z][A-Z0-9]*\\s*:=\\s*[A-Z][A-Z0-9]*\\s*(?:[\\+,\\-,\\*,\\/]{1}\\s*(?:[A-Z][A-Z0-9]*|[0-9]+))*\\s*;)|(?:READ\\s*\\(\\s*[A-Z][A-Z0-9]*(?:\\s*,\\s*[A-Z_][A-Z0-9_]*)*\\))\\s*|(?:WRITE\\([A-Z_][A-Z0-9_]*(?:\\s*,\\s*[A-Z_][A-Z0-9_]*)*\\))\\s*|\\s*)+)\\s*(END.)";
			
//			String fullCheckRegex= "(\\s*)(PROGRAM)(\\s+)([a-zA-Z0-9_ ]+)(\\s*)([a-zA-Z0-9_]+)(\\s*)([A-Z]+)(\\s*)(((\\s*)([a-zA-Z0-9_]+)(\\s*)(,)?)+)(\\s*)(BEGIN)(\\s*)(\\w+)(\\s*)(\\({1})((\\w+)(((\\,{1})(\\w+))*)(\\s*)(\\){1})(\\s*)(\\;?))((\\s*)(\\w+)(\\s*)(\\:=)((\\s*)(\\(?)(\\w+)((((\\s*)(\\)?)((\\+|\\-|\\*))(\\(?)(\\s*)(\\w+)?)+))?(\\s*)(\\)?)(\\s*)(;)?))+(\\s*)(\\w+)(\\s*)(\\({1})((\\w+)(((\\,{1})(\\w+))*)(\\s*)(\\){1})(\\s*)(\\;*))(END.)";
//		
//			        for(int i=0;i<lines.size();i++){strInp = strInp + " " + lines.get(i);}//getting kolo f line wa7ed
//			        System.out.println(strInp);
//			        
//
//			        Pattern patterntest;
//			        Matcher matchertest;
//			       patterntest = Pattern.compile(fullCheckRegex);
//			          matchertest = patterntest.matcher(strInp);
//			          boolean r=false;
//			          if(matchertest.matches()){
//			        	  r=true;
//						  System.out.println("full regex isssssssssss   "+r);
//			          }else{
//			        	  System.out.println("nooooooooooooooooooooooo");
//			          }
//			
			 
			
			String regex="(PROGRAM|VAR|BEGIN|END\\.|END|FOR|READ|WRITE|TO|DO|;|:=|\\+|,|\\(|\\)|\\w+|\\*)";
			  Matcher matcher;
		        Pattern pattern;
		        String token;
		        int[] lineNo=new int[100];
		    	String[] tokenNo=new String[100];
		    	String[] tokenSpecifier=new String[100];
		    	int j=0;
		        for(int i=0;i<noOfRows;i++)
		    	{                 pattern = Pattern.compile(regex);
		    		          matcher = pattern.matcher(line[i]);
		    		          
		    		          while(matcher.find()){
		    		        	  
		    		        	token=matcher.group();
		    		        	if(token.equals("PROGRAM")){
		    		        		lineNo[j]=i+1;
		    		   			  tokenNo[j]=TokenCoding.get("PROGRAM");
		    		   			  tokenSpecifier[j]="-";
		    		   			  j++;
		    		        	}else if(token.equals("VAR")){
		    		        		lineNo[j]=i+1;
			    		   			  tokenNo[j]=TokenCoding.get("VAR");
			    		   			  tokenSpecifier[j]="-";
			    		   			  j++;
		    		        	}else if(token.equals("BEGIN")){
		    		        		lineNo[j]=i+1;
			    		   			  tokenNo[j]=TokenCoding.get("BEGIN");
			    		   			  tokenSpecifier[j]="-";
			    		   			  j++;
		    		        	}else if(token.equals("END")){
		    		        		lineNo[j]=i+1;
			    		   			  tokenNo[j]=TokenCoding.get("END");
			    		   			  tokenSpecifier[j]="-";
			    		   			  j++;
		    		        	}else if(token.equals("END.")){
		    		        		lineNo[j]=i+1;
			    		   			  tokenNo[j]=TokenCoding.get("END.");
			    		   			  tokenSpecifier[j]="-";
			    		   			  j++;
		    		        	}else if(token.equals("FOR")){
		    		        		lineNo[j]=i+1;
			    		   			  tokenNo[j]=TokenCoding.get("FOR");
			    		   			  tokenSpecifier[j]="-";
			    		   			  j++;
		    		        	}else if(token.equals("READ")){
		    		        		lineNo[j]=i+1;
			    		   			  tokenNo[j]=TokenCoding.get("READ");
			    		   			  tokenSpecifier[j]="-";
			    		   			  j++;
		    		        	}else if(token.equals("WRITE")){
		    		        		lineNo[j]=i+1;
			    		   			  tokenNo[j]=TokenCoding.get("WRITE");
			    		   			  tokenSpecifier[j]="-";
			    		   			  j++;
		    		        	}else if(token.equals("TO")){
		    		        		lineNo[j]=i+1;
			    		   			  tokenNo[j]=TokenCoding.get("TO");
			    		   			  tokenSpecifier[j]="-";
			    		   			  j++;
		    		        	}else if(token.equals("DO")){
		    		        		lineNo[j]=i+1;
			    		   			  tokenNo[j]=TokenCoding.get("DO");
			    		   			  tokenSpecifier[j]="-";
			    		   			  j++;
		    		        	}else if(token.equals(";")){
		    		        		lineNo[j]=i+1;
			    		   			  tokenNo[j]=TokenCoding.get(";");
			    		   			  tokenSpecifier[j]="-";
			    		   			  j++;
		    		        	}else if(token.equals(":=")){
		    		        		lineNo[j]=i+1;
			    		   			  tokenNo[j]=TokenCoding.get(":=");
			    		   			  tokenSpecifier[j]="-";
			    		   			  j++;
		    		        	}else if(token.equals("+")){
		    		        		lineNo[j]=i+1;
			    		   			  tokenNo[j]=TokenCoding.get("+");
			    		   			  tokenSpecifier[j]="-";
			    		   			  j++;
		    		        	}else if(token.equals(",")){
		    		        		lineNo[j]=i+1;
			    		   			  tokenNo[j]=TokenCoding.get(",");
			    		   			  tokenSpecifier[j]="-";
			    		   			  j++;
		    		        	}else if(token.equals("(")){
		    		        		lineNo[j]=i+1;
			    		   			  tokenNo[j]=TokenCoding.get("(");
			    		   			  tokenSpecifier[j]="-";
			    		   			  j++;
		    		        	}else if(token.equals(")")){
		    		        		lineNo[j]=i+1;
			    		   			  tokenNo[j]=TokenCoding.get(")");
			    		   			  tokenSpecifier[j]="-";
			    		   			  j++;
		    		        	}else if(token.equals("*")){
		    		        		lineNo[j]=i+1;
			    		   			  tokenNo[j]=TokenCoding.get("*");
			    		   			  tokenSpecifier[j]="-";
			    		   			  j++;
		    		        	}else if(token.matches("([a-zA-Z0-9]+)")||token.matches("([a-zA-Z0-9]+)(_)([a-zA-Z0-9]+)")){
		    		        		lineNo[j]=i+1;
		    		                tokenNo[j]=TokenCoding.get("id");
			    		   			  tokenSpecifier[j]=token;
			    		   			  j++;
		    		        	}
		    		        	  
		    		          }
		    		        
		    	}
		        System.out.println("Line No   Token No   Token Specifier ");
		        for(int k=0;k<=j-1;k++){
		       	 System.out.println(lineNo[k]+"         "+tokenNo[k]+"          "+tokenSpecifier[k]);
		        }
		        
		        //writing table in file
		        Writer writer = null;
		        writer = new BufferedWriter(new FileWriter("lexicalOutput.txt"));
		        for(int q=0;q<j;q++)
		               {
		           writer.write(lineNo[q]+"     "+tokenNo[q]+"     "+tokenSpecifier[q]);
		           ((BufferedWriter) writer).newLine();	
		               }
		         writer.close();
		         parse.Parse();
		         
		         long finish = System.currentTimeMillis();
		         
		         System.out.println("TIME OF LEXICAL: "+(finish-start)+ " ms");
	}
	
	}

