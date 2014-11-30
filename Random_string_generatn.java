class Random_string_generatn  
{  
  public Random_string_generatn()  
  {  
    final int LENGTH = 6;  
    StringBuffer sb = new StringBuffer();  
    for (int x = 0; x < LENGTH; x++)  
    {  
      sb.append((char)((int)(Math.random()*26)+97));  
    }  
    System.out.println(sb.toString());  
  }  
  public static void main(String[] args){new Random_string_generatn();}  
}  