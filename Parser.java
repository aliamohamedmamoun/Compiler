
package compiler;



import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser
{

    /**
     * @param args the command line arguments
     */
    static int count = 0; //global variable
    static int LISTCOUNT=0;
 public   static  List<String> IDLIST = new ArrayList<String>();
    static  String[] lineNo = new String[100];
    static String[] tokenNo = new String[100];
    static String[] tokenSpecifier = new String[100];
   static CodeGenerator generator=new CodeGenerator();
   static InfixToPostfix infix=new InfixToPostfix();
   static  List<String> Assignstmt = new ArrayList<String>();
  //  public static void main(String[] args) throws IOException
   public static void Parse() throws IOException
    {
    	Long start = System.currentTimeMillis();
       String test = getToken(count);          //getToken de method betgeb el token mn el array w badeha rakam el line el 3ayza abd2 mn 3ando
        System.out.println(PROGRAM(test));
        String[] assembly = generator.Assembly.toArray(new String[generator.Assembly.size()]);

//        for(int i=0;i<assembly.length;i++){
//        	 if(i==1){
//        		 assembly[i]=generator.GETEXTERNALREF();
//        		 System.out.println(assembly[i]);
//        	 }else
//        	 System.out.println(assembly[i]);
  //      }
         
    Writer writer = null;
    writer = new BufferedWriter(new FileWriter("ASSEMBLYOutput.asm"));
    for(int i=0;i<assembly.length;i++){
    	if(i==1){
   		 assembly[i]=generator.GETEXTERNALREF();
    	 writer.write(assembly[i]);
    	((BufferedWriter) writer).newLine();	
    	}else{
    		 writer.write(assembly[i]);
       ((BufferedWriter) writer).newLine();	
           }
    // writer.close();
}
    writer.close();
    long finish = System.currentTimeMillis();
    
    System.out.println("TIME OF Parsing And code generation: "+(finish-start)+ " ms");
    }

   public static String getToken(int count) throws FileNotFoundException //ba3melaha calling kol ma a3oz token
    {
        //ba2ara el outputfile of lexical analysis w ba2asemo le 3 arrays lineNo,tokenNo,Specifier
        FileReader readProgram = null;
        int noOfTokens = 0;
        readProgram = new FileReader("lexicalOutput.txt");
        Scanner programScanner = new Scanner(readProgram);
        List<String> lines = new ArrayList<String>();
        while (programScanner.hasNextLine())
        {
            lines.add(programScanner.nextLine());
            noOfTokens++;
        }

        String[] line = lines.toArray(new String[noOfTokens]);
        String regex = "(\\s*)([a-zA-Z0-9_]+)(\\s*)([a-zA-Z0-9_]+)(\\s*)([a-zA-Z0-9_\\-]+)";
       //  String regex = "(\\s*)(\\w+)(\\s*)(\\w+)(\\s*)(\\w+)";
      //  String regex="(\\s*)([a-zA-Z0-9]+)(\\s*)([a-zA-Z0-9]+)(\\s*)([a-zA-Z0-9_]+)";
     //   String regex="(\\s*)([a-zA-Z0-9]+)(\\s*)([a-zA-Z0-9]+)(\\s*)([a-zA-Z0-9_]+)";       
    //   String regex = "(\\s*)([a-zA-Z0-9_]+)(\\s*)([a-zA-Z0-9_]+)(\\s*)([a-zA-Z0-9_]+)"; 
        //String[] lineNo = new String[100];
//        String[] tokenNo = new String[100];
      //  String[] tokenSpecifier = new String[100];
        for (int i = 0; i < noOfTokens; i++)
        {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(line[i]);
            while (matcher.find())
            {
                lineNo[i] = matcher.group(2);
                tokenNo[i] = matcher.group(4);
                tokenSpecifier[i] = matcher.group(6);
            }
            //   System.out.println(lineNo[i]+"         "+tokenNo[i]+"          "+tokenSpecifier[i]);
        }

        //for(int j=3;j<10;j++){
        //tool ma el count less than el no of tokens ely fel file ha el fe 3shan aeraf eny wasalt lel end bs
        if (count >= noOfTokens)
        {
            System.out.println("End parsing");
            String token = tokenNo[count];
           
            return token;
        } else
        {
           // System.out.println(count);
            
        	System.out.println(lineNo[count ]+" "+ tokenNo[count]); //de 3shan a3ara which line and token just testing
            String token = tokenNo[count];
            return token;
        }
        //	}
        //return null;	

    }

    public static Boolean IDLIST(String token) throws FileNotFoundException
    {

        Boolean found = false;
        if (token.equals("17"))
        {
        	IDListGenerator();
            found = true;
            //System.out.println(token);
            ++count;
            token = getToken(count);

            //System.out.println(token);
            while (token.equals("14") && found == true)
            {
                ++count;
                token = getToken(count);
                //System.out .println(token);
                if (token.equals("17"))
                {
                	IDListGenerator();
                    ++count;
                    token = getToken(count);

                    //System.out.println(token);
                } else
                { 
                    found = false;
                }
            }
        }
        if (found == true)
        {
            System.out.println("IDLiST success");
            return found;
        } else
        {
            System.out.println("IDLIST failure");
            return found;
        }

    }
    public static List<String> IDListGenerator(){
    	IDLIST.add(tokenSpecifier[count]);
    	System.out.println(tokenSpecifier[count]);
    	LISTCOUNT++;
		return IDLIST;
    	
    }
    public static Boolean READ(String token) throws FileNotFoundException
    {
        Boolean found = false;
        if (token.equals("7"))
        {
            //System.out.println(token);
            ++count;
            token = getToken(count);
            //System.out.println(token);
            if (token.equals("15"))
            {
                ++count;
                token = getToken(count);
                //System.out.println(token);
                if (IDLIST(token).equals(true))
                {
                    //System.out.println("idlist true");
                    //++count;
                    token = getToken(count);
                    if (token.equals("16"))
                    {
                        found = true;
                        // ++count;
                        token = getToken(count);
                        //	System.out.println(token);
                    }
                }
            }
            if (found == true)
            {
                System.out.println("READ success");
              LISTCOUNT=generator.READGENERATOR(IDLIST, LISTCOUNT);
              IDLIST.clear();
                return found;
            } else
            {
                System.out.println("READ failure");
                return found;
            }
        }
        return found;
    }

    public static Boolean WRITE(String token) throws FileNotFoundException
    {
        //int count=15;
        Boolean found = false;
        if (token.equals("8"))
        {
            //System.out.println(token);
            ++count;
            token = getToken(count);
            //System.out.println(token);
            if (token.equals("15"))
            {
                ++count;
                token = getToken(count);
                //System.out.println(token);
                if (IDLIST(token).equals(true))
                {
                    System.out.println("idlist true");
                    //++count;
                    token = getToken(count);
                    if (token.equals("16"))
                    {
                        found = true;
                        //++count;
                        token = getToken(count);
                        //System.out.println(token);
                    }
                }
            }
            if (found == true)
            {
                System.out.println("WRITE success");
                LISTCOUNT=generator.WRITEGENERATOR(IDLIST, LISTCOUNT);
                IDLIST.clear();
                return found;
            } else
            {
                System.out.println("WRITE failure");
                return found;
            }
        }
        return found;
    }
    
    public static Boolean ASSIGN(String token) throws FileNotFoundException
    {
    	
        Boolean found = false;
        if (token.equals("17"))
        {
        	Assignstmt.add(tokenSpecifier[count]);
        	System.out.println("token specifier");
        	System.out.println(tokenSpecifier[count]);
            ++count;
            token = getToken(count);
            
            if (token.equals("12"))
            { 
            	Assignstmt.add(tokenNo[count]);
            	System.out.println("token number");
            	System.out.println(tokenNo[count]);
                ++count;
                token = getToken(count);
            //    System.out.println("in assign before EXPCHECK "+ token);
                if (EXP(token))
                {
              //      System.out.println("in assign After EXPCHECK "+ token);
                    found = true;
                }else if(token.equals("15")){
                	Assignstmt.add(tokenNo[count]);
                	System.out.println("token number");
                	System.out.println(tokenNo[count]);
                	++count;
                    token = getToken(count);
                    while(!token.equals("11")){
                    	if(token.equals("13")||token.equals("18")||token.equals("16")){
                    	Assignstmt.add(tokenNo[count]);
                    	System.out.println("token number");
                    	System.out.println(tokenNo[count]);
                    	}else{
                    		 Assignstmt.add(tokenSpecifier[count]);
                         	System.out.println("token specifier");
                         	System.out.println(tokenSpecifier[count]);
                    	}
                    	++count;
                        token = getToken(count);
                    }
                    if(token.equals("11")){
                    	found=true;
                    }
                }
            }
        }
        if (found == true)
        {
        	String[] result= infix.getAssinStmt(Assignstmt);
        	String[] resultInfix=infix.infixToPostfix(result);
        //	infix.infixToPostfix(result);
        	//System.out.println(resultInfix);
        	infix.evaluatePostfix(resultInfix);
            System.out.println("ASSIN success");
            System.out.println(Assignstmt);
            Assignstmt.clear();
            return found;
        } else
        {
            System.out.println("ASSIN failure");
            return found;
        }

    }

    public static Boolean EXP(String token) throws FileNotFoundException
    {
        Boolean found = false;
        System.out.println("in EXP before TOKENCHECK "+ token);
        
        if (token.equals("17"))
        {
            System.out.println("in EXP before Factor check "+ token);
            Assignstmt.add(tokenSpecifier[count]);
        	System.out.println("token specifier");
        	System.out.println(tokenSpecifier[count]);
            ++count;
            token = getToken(count);
            found = true;
            System.out.println(token);
            System.out.println("alia in EXP");
            while (found == true && ((token.equals("13")) || token.equals("18")))
            {
            	if(token.equals("13")||token.equals("18")){
            	Assignstmt.add(tokenNo[count]);
            	System.out.println("token number");
            	System.out.println(tokenNo[count]);
            	}else{
            		Assignstmt.add(tokenSpecifier[count]);
                	System.out.println("token specifier");
                	System.out.println(tokenSpecifier[count]);
            	}
            	
            	++count;
                System.out.println("EXP");
                token = getToken(count);
                System.out.println("in While EXP: "+token);
                if (!token.equals("17"))
                {
                    System.out.println("eh ba2a");
                    System.out.println(token);
                   if(FACTOR(token).equals(true)){
                	   found=true;
                   }else
                	   found =false;
                }
                Assignstmt.add(tokenSpecifier[count]);
            	System.out.println("token specifier");
            	System.out.println(tokenSpecifier[count]);
                count++;
                 token = getToken(count);
            
            }}
            if (found == true)
            {
                System.out.println("EXP success");
                
            } else
            {
                System.out.println("EXP failure");
                
            }
			return found;
        }
       

    

    public static Boolean FACTOR(String token) throws FileNotFoundException
    {
        Boolean found = false;
System.out.println("in Factor before TOKENCHECK "+ token);
        if (token.equals("17"))
        {
        	Assignstmt.add(tokenSpecifier[count]);
    	    System.out.println("token specifier");
    	    System.out.println(tokenSpecifier[count]);
        	System.out.println("in Factor After TOKENCHECK "+ token);
            found = true;
            ++count;
            token = getToken(count);
            //System.out.println(token);
        }
        else if (token.equals("15"))
        {
        	Assignstmt.add(tokenNo[count]);
        	System.out.println("token number");
        	System.out.println(tokenNo[count]);
            ++count;
            token = getToken(count);
            System.out.println("fffffffffffff");
		System.out.println(token);
            if (EXP(token).equals(true))
            {
            	Assignstmt.add(tokenSpecifier[count]);
            	System.out.println("token specifier");
            	System.out.println(tokenSpecifier[count]);
            	//--count;
                token = getToken(count);
            	System.out.println(token);
                	System.out.println("da5al fel factor w el exp true");
                if (token.equals("16"))
                {
                	Assignstmt.add(tokenNo[count]);
                	System.out.println("token number");
                	System.out.println(tokenNo[count]);
                	System.out.println("FACTOR TOKEN ");
                    found = true;
                    //++count;
                   // token = getToken(count);
                   // System.out.println(token);
                }
            }
        }

        if (found == true)
        {
            System.out.println("FACTOR success");
            return found;
        } else
        {
            System.out.println("FACTOR failure");
            return found;
        }
    }

    public static Boolean STMT(String token) throws FileNotFoundException
    {
        Boolean found = false;
        if (token.equals("17"))
        {
            if (ASSIGN(token).equals(true))
            {
                found = true;
            }
        } else if (token.equals("7"))
        {
            //System.out.println("READ");
            if (READ(token).equals(true))
            {
                found = true;
            }
        } else if (token.equals("8"))
        {
            if (WRITE(token).equals(true))
            {
                found = true;
            }
//        }else if(token.equals("5")){
//        	System.out.println("55555");
//			found=true;
        }
        if (found == true)
        {
            System.out.println(" STMT success");
            return found;
        } else
        {
        	System.out.println(token);
            System.out.println("STMT failure");
            return found;
        }
    }

    public static Boolean STMTLIST(String token) throws FileNotFoundException
    {
        Boolean found = false;
        if (STMT(token).equals(true))
        {
            ++count;
            token = getToken(count);
            	System.out.println(token);
            	
            found = true;
            while (token.equals("11") || STMT(token).equals(true))
            {
                ++count;
                token = getToken(count);
                	System.out.println(token);
                	
            }
        }

        if (found == true)
        {
            System.out.println(" STMTLIST success");
            return found;
        } else
        {
  
            System.out.println("STMTLIST failure");
            return found;
        }
    }

    public static Boolean PROGRAM(String token) throws FileNotFoundException
    {
        Boolean found = false;

        if (token.equals("1"))
        {
            //	System.out.println("ggggggg");
            ++count;
            token = getToken(count);
            //System.out.println(token);
            if (PROGRAMNAME(token).equals(true))
            {
                ++count;
                token = getToken(count);
                //System.out.println(token);	
                if (token.equals("2"))
                {
                    ++count;
                    token = getToken(count);
                    System.out.println("dddddddddddddddddddddddddddddddddddddddddd");
//                    	System.out.println(token);
//                    	LISTCOUNT=generator.DECLISTGENERATOR(IDLIST,count);
//                    	IDLIST.clear();
                    int tempCount=count;
                   
                    if (IDLIST(token).equals(true))
                    {
                    
                    	LISTCOUNT=generator.DECLISTGENERATOR(IDLIST,tempCount);
                    	IDLIST.clear();
                        //++count;
                        token = getToken(count);
                        // 	System.out.println(token);
                        if (token.equals("3"))
                        {
                            System.out.println("BEGIN");
                            ++count;
                            token = getToken(count);
                            if (STMTLIST(token).equals(true))
                            {
                                //++count;
                               token = getToken(count);
                               	System.out.println(token);
                             System.out.println("rrrrrrrrrrrrrr");
                               if (token.equals("5"))
                               {System.out.println("88888");
                               generator.GENERATETEMP();
                                    found = true;
                              }
                            }
                        }
                    }
                }}
                if (found == true)
                {
                    System.out.println(" STMTLIST success");
                    return found;
                } else
                {
                    System.out.println("STMTLIST failure");
                    return found;
                }
           
           
    }
        return found;
    }

    public static Boolean PROGRAMNAME(String token) throws FileNotFoundException
    {
        Boolean found = false;
        if (token.matches("(\\s*)([a-zA-Z0-9]+)(\\s*)"))
        {
            found = true;
        }
        if (found == true)
        {
            System.out.println(" pragramName success");
          //   System.out.println(tokenSpecifier[count]);
            generator.PROGRAMNAMEGENERATOR(tokenSpecifier[count]);
            return found;
        } else
        {
            System.out.println("programName failure");
            return found;
        }
    }
   public static String getSpecifier(int count){
	return tokenSpecifier[count];
	   
   }

}







