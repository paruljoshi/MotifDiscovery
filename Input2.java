import java.io.*;
import java.util.*;
public class Input2 {
	public static void main(String args[]) throws IOException{
		String[] name=new String[10];
		String[] seq=new String[10];
		int[] Seqlength=new int[10];
		Scanner sc=new Scanner(System.in);
		int ml;
		int n=0;
		System.out.println("enter motif length");
		ml=sc.nextInt();
		System.out.println("enter no of Candidate motif required in each sequence:");
		int cm=sc.nextInt();
		Random r = new Random();
		FileReader f=new FileReader("MIG1.txt");
		BufferedReader br=new BufferedReader(f);
		String s;
		int i=0,j=0;
		while((s=br.readLine())!=null){
			String st=s;
			
			//System.out.println(s);
			 n=st.length();
			
			if(n!=0&&i<10){
			if(n<=20){
			
				name[i]=st;
				System.out.println(name[i]);
				System.out.println(n);
				
			}
			else{
			String s1	=st.replaceAll("\\s+", "");
								
					seq[j]=s1;
					System.out.println(seq[j]);
					Seqlength[j]=s1.length();
					System.out.println(Seqlength[j]);
				
	
			}
			j=i;
			i++;
			}
			else
			{
				i=i-1;
			}
			
		
			//System.out.println(n);
		
	
			
		}
		for(int i1=0;i1<seq.length-1;i1++){
			n=Seqlength[i1];
			int t = r.nextInt(n-ml);
			int m, k, l;
			m = k = l = t;
			while (m == t)
				m = r.nextInt(n-ml);
			while (k == t || k == m)
				k = r.nextInt(n-ml);
			while (l == t|| l == m || k == l)
				l = r.nextInt(n-ml);
			
			String s1=seq[i1].substring(t, t+ml);                 //v vll make a Candidate array named-Initials and best candidate motif
			String s2=seq[i1].substring(m, m+ml);                  //in these 4 Candidate motifs vl be chosen for can
			String s3=seq[i1].substring(k, k+ml);
			String s4=seq[i1].substring(l, l+ml);
			System.out.println(s1+"  "+s2+" "+s3+"  "+s4);
			
			
		}
		
		for(int k=0;k<Seqlength.length;k++){
			//System.out.println(name[k]+"   "+seq[k]);
			System.out.println(Seqlength[k]);
		}
		
		
		
	}
}



