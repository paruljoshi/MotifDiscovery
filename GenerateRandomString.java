public class GenerateRandomString {
   private static final String ALPHA_NUM = "0BCDE";  
   public static void main(String[] args) { 
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
}