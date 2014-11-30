import java.util.*;

public class charinput {

public static void main(String args[]){

Scanner in = new Scanner(System.in);

// first we'll read in a string, and convert it to a character

System.out.println("Put in a character ");

String s1 = in.nextLine();

char c1 = s1.charAt(0);

// now we read in a string, and find the third character

System.out.println("Enter a word ");

String s2 = in.nextLine();

char c2 = s2.charAt(2);

// print out the results

System.out.println("The first character was " + c1);

System.out.println("The third character in the word was " +c2);

}

}