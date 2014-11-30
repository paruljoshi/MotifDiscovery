import java.util.*;
class Dijikshtra {
private String cities= "ABCDEF";
public static void main(String args[]) {
			int graph[][] = {
				{ 0, 41, 0, 0, 0, 60 },
				{ 0, 0, 51, 0, 32, 0},
				{ 0, 0, 0, 50, 0, 0 },
				{ 45, 0, 0, 0, 0, 38 },
				{ 0, 0, 32, 36, 0, 0 },
				{ 0, 29, 0, 0, 21, 0 } };
		int i;
		char src,dest;
		System.out.println("Dijikstra GA...");
		Scanner in=new Scanner(System.in);
		System.out.println("Enter the Source Node >>");
		String s1=in.nextLine();
		src=s1.charAt(0);
		System.out.println("Enter the Destination Route >>");
		String s2=in.nextLine();
		dest=s2.charAt(0);
	    Dijikshtra dj = new Dijikshtra();  
      	for(i=0;i<10;i++)
      System.out.println(dj.getcities(5));  
   }  
   public String getcities(int len) {  
      StringBuffer sb = new StringBuffer(len);  
      for (int i=0;  i<len;  i++) {  
       		int val=(int) (Math.random()*cities.length());
   		sb.append(cities.charAt(val)).toString();
  }  
      return sb.toString();
   }  
}