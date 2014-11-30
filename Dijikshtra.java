import java.util.*;
public static void main(String[] args) {
	int dest;
	System.out.println("Dijikstra GA...");
	System.out.println("Enter the Destination Route");
	Scanner sc=new Scanner(System.in);
		dest=sc.nextInt();
	try {
		Crossover c = new Crossover();
		} 
	catch () {
		System.out.println(" ");
	}
}
public class Dijikshtra {

	static int graph[][] = {
				{ 0,41,0,0,0,60 },
				{ 0,0,51,0,32,0},
				{ 0,0,0,50,0,0 },
				{ 45,0,0,0,0,38 },
				{ 0,0, 32, 36,0,0 },
				{ 0,29,0,0,21,0 } };

public class GenerateRandomString {  
   private static final String ALPHA_NUM = "0BCDE"; { 
   	int i; 
      GenerateRandomString grs = new GenerateRandomString();  
      	for(i=0;i<10;i++)
      System.out.println(grs.getAlphaNumeric(5));  
   }  
   public String getAlphaNumeric(int len) {  
      StringBuffer sb = new StringBuffer(len);  
      for (int i=0;  i<len;  i++) {  
         int ndx = (int)(Math.random()*ALPHA_NUM.length());  
         sb.append(ALPHA_NUM.charAt(ndx));  
      }  
      return sb.toString();  
   }  
   	
   	