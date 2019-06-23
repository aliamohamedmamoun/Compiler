package compiler;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {
static List<String> Assembly=new ArrayList<String>();
static String EXTREF= "EXTREF     "; 
static int LOCCTR=0;
static String REGA=null;
static  String[] T=new String[5];
static String temp;
static int j=0;
static Parser parse =new Parser();
public static int READGENERATOR(List<String> IDLIST,int LISTCOUNT){
	String[] idlist = IDLIST.toArray(new String[IDLIST.size()]);
	if(!EXTREF.contains("XREAD")){
	EXTREF=EXTREF+"XREAD,";
	System.out.println(EXTREF);
	}
	 System.out.println("         +JSUB   XREAD");
	 Assembly.add("         +JSUB   XREAD");
	 System.out.println("         WORD      "+LISTCOUNT);
	 Assembly.add("         WORD      "+LISTCOUNT);
	
	int size=IDLIST.size();
	 for(int i=0;i<size;i++){
		 System.out.println("         WORD      "+idlist[i]);
		 Assembly.add("         WORD      "+idlist[i]);
		// IDLIST.remove(i);
	 }
	 return LISTCOUNT=0;
}
public static int WRITEGENERATOR(List<String> IDLIST,int LISTCOUNT){
	String[] idlist = IDLIST.toArray(new String[IDLIST.size()]);
	if(!EXTREF.contains("XWRITE")){
	EXTREF=EXTREF+"XWRITE,";
	System.out.println("EXTERNAAAAAAAAAAAL");
	System.out.println(EXTREF);
	}
	 System.out.println("         +JSUB   XWRITE");
	 Assembly.add("         +JSUB   XWRITE");
	 System.out.println("         WORD      "+LISTCOUNT);
	 Assembly.add("         WORD      "+LISTCOUNT);
	 int size=IDLIST.size();
	 for(int i=0;i<size;i++){
		 System.out.println("         WORD      "+idlist[i]);
		 Assembly.add("          WORD      "+idlist[i]);
		// IDLIST.remove(i+1);
	 }
	 return LISTCOUNT=0;
}
public static void PROGRAMNAMEGENERATOR(String programName){
System.out.println(programName+"     STARTS   0");
Assembly.add(programName+"      STARTS   0");
System.out.println(EXTREF);
Assembly.add(EXTREF);
System.out.println("         STL   RETADR");
Assembly.add("         STL   RETADR");
LOCCTR=LOCCTR+3;
System.out.println("          J   {EXADDR}");
Assembly.add("          J   {EXADDR}");
System.out.println("RETADR RESW    1");
Assembly.add("RETADR RESW     1");
}
public static int DECLISTGENERATOR(List<String> IDLIST,int tempCount){
	String[] idlist = IDLIST.toArray(new String[IDLIST.size()]);
	int size=IDLIST.size();
	 for(int i=0;i<size;i++){
		System.out.println(parse.getSpecifier(tempCount+i));
		String specifier=parse.getSpecifier(tempCount+i);
		tempCount++;
		System.out.println(specifier+"      RESW     1");
		Assembly.add(specifier+"      RESW     1");
		// IDLIST.remove(i+1);
	}
	return 0;
}
public static void ADDGENERATOR(String val1,String val2,String[] result){
	  String c=result[0];
	  System.out.println(c);
	  System.out.println(REGA);
	  if(REGA==null){
		  System.out.println("         LDA        "+val1);
		  Assembly.add("         LDA       "+val1);
		  System.out.println("         ADD       "+val2);
		  Assembly.add("         ADD       "+val2);
		  System.out.println("         STA       "+c);
		  Assembly.add("         STA       "+c);
		  REGA=c;
	  }else if(REGA!=null){ 
		  if(REGA==c){
			  System.out.println("REGA not equal null "+temp);
			  System.out.println("         ADD       "+val2);
			  Assembly.add("         ADD       "+val2);
			  System.out.println("         STA       "+c);
			  Assembly.add("         STA       "+c);
			  REGA=c;
			  }else if(REGA==val1){
				  System.out.println("         ADD       "+val2);
				  Assembly.add("         ADD       "+val2);
				  System.out.println("         STA       "+c);
				  Assembly.add("         STA       "+c);
				  REGA=c;
			  }else if(REGA==val2){
				  System.out.println("         ADD       "+val1);
				  Assembly.add("         ADD       "+val1);
				  System.out.println("         STA       "+c);
				  Assembly.add("         STA       "+c);
				  REGA=c;
			  }
				  else{ 
			  System.out.println("REGA not equal null else"+temp);
		  T[j]=REGA;
		  System.out.println("         STA       T"+j);
		  Assembly.add("         STA       T"+j); 
		  System.out.println("         LDA       "+val1);
		  Assembly.add("         LDA       "+val1);
		  System.out.println("         ADD       "+val2);
		  Assembly.add("         ADD       "+val2);
		  System.out.println("         STA       "+c);
		  Assembly.add("         STA       "+c);
		  REGA=c;
		  j++;
	  }
		  temp=c;
		  }
	   System.out.println(temp);
	  System.out.println("ADDGENERATOR succes");
	  }
	  
public static void MULGENERATOR(String val1,String val2,String[] result){
	  String c=result[0];
	  if(REGA==null){
		  System.out.println("         LDA       "+val1);
		  Assembly.add("         LDA        "+val1);
		  System.out.println("         MUL       "+val2);
		  Assembly.add("         MUL       "+val2);
		  System.out.println("         STA       "+c);
		  Assembly.add("         STA       "+c);
		  REGA=c;
	  }else if(REGA!=null) {
		  if(REGA==c){
			  System.out.println("REGA not equal null "+temp);
			  System.out.println("         MUL       "+val2);
			  Assembly.add("         MUL       "+val2);
			  System.out.println("         STA       "+c);
			  Assembly.add("         STA       "+c);
		  } else if(REGA==val1){
				  System.out.println("         MUL       "+val2);
				  Assembly.add("         MUL       "+val2);
				  System.out.println("         STA       "+c);
				  Assembly.add("         STA       "+c);
				  REGA=c;
			  }else if(REGA==val2){
				  System.out.println("         MUL       "+val1);
				  Assembly.add("         MUL       "+val1);
				  System.out.println("         STA       "+c);
				  Assembly.add("         STA       "+c);
				  REGA=c;
		  }else{
			  System.out.println("REGA not equal null else"+temp);
			  T[j]=REGA;
			 
		  System.out.println("         STA       T"+j);
		  Assembly.add("         STA       T"+j); 
		  System.out.println("         LDA       "+val1);
		  Assembly.add("         LDA       "+val1);
		  System.out.println("         MUL    "+val2);
		  Assembly.add("         MUL       "+val2);
		  System.out.println("         STA       "+c);
		  Assembly.add("         STA       "+c);
		  REGA=c;
		  j++;
		  }
		  temp=c;
		  }
	  System.out.println("MULGENERATOR succes"); 
}
public static void GENERATETEMP(){
	for(int k=0;k<j;k++){
		
		 System.out.println("T"+k+"       RESW      1");
		  Assembly.add("T"+k+"       RESW      1"); 
	}
	 System.out.println("        END    ");
	  Assembly.add("         END    "); 
}
public static String GETEXTERNALREF(){
	return EXTREF;
}
}