
import java.io.*;
import java.util.*;

//import FMGA1.Candidate;
public class FMGA3{
	String Promoter;
	//String[] motif=new String[10];
	//String[] child=new String[2];
	String c=new String();

	double[] fitness=new double[10];
	float WM[][];
	//int nos;  
	int COUNT=0;
	boolean flag;
	 Candidate[] can;
	Candidate[] next_can;
	Candidate[] mate_pool;
	Candidate Initial[];
	//NO OF SEQUENCES
	char PossBase[]={'A','C','G','T','M','R','W','S','Y','K','V','H','D','B','N'};
	String Base="ATGC";
	int motifLength;
	int pop_size;
	int parent_per;                                                    //PARENT AMOUNT IN NEXT GEN
	int parent;       
	Candidate Pred_Motif;
	Candidate Prev_Pred_Motif;
	String[] ambiguities;
	 String consensus=new String();//PARENT NUMBER IN INTEGER
	String ambiguity="MRWSYKVHDBN";                                 //IUPAC CODING LETTERS
	char ambCode[][]={                                              //CORRESPONDING CHARACTER ARRAY CONTAING BASES FOR EACH IUPAC CODE
			{'A','C'},
			{'A','G'},
			{'A','T'},
			{'C','G'},
			{'C','T'},
			{'G','T'},
			{'A','C','G'},
			{'A','C','T'},
			{'A','G','T'},
			{'C','G','T'},
			{'G','A','T','C'},
			
	};
	double sum=0;
	
	
			
	double wm[][];
	double weightMatrix[][]=new double[4][motifLength];
	public FMGA3(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Motif Length : ");
		int ml = sc.nextInt();
		motifLength=ml;
		String[] ambiguities=new String[motifLength];
		System.out.println("Enter the promoter sequence of the same length");
		Promoter =sc.next();
		 System.out.println("Enter the % of parents in new gen :");
		 parent_per=sc.nextInt();
		 //parent=(parent_per*pop_size)/100;
		ArrayList<String> chromosomes;
		ArrayList<String> geneName;
		ArrayList<String> geneCode;
		String temp;
		int count = 0;

		try {
			FileReader fr = new FileReader("MIG1.txt");
			Scanner in = new Scanner(fr);
			//Scanner sc;
			chromosomes = new ArrayList<String>();
			geneName = new ArrayList<String>();
			geneCode = new ArrayList<String>();
			temp = in.next();
			while (in.hasNext()) {
				String chromosome = "";
				geneName.add(temp);
				geneCode.add(in.next());

				while (true) {
					if (!in.hasNext()) {
						break;
					}
					temp = in.next();
					if (temp.charAt(0) == '>') {
						break;
					}
					chromosome += temp;
				}

				if (chromosome != "") {
					chromosomes.add(chromosome);
				}

				count++;

			}

			for (int i = 0; i < count; i++) {
				System.out.println("Gene Name is : " + geneName.get(i));
				System.out.println("Gene Code is : " + geneCode.get(i));
				System.out.println(chromosomes.get(i));
				System.out.println();
			}

			
			
			// int ml = 6;
			System.out.println("Enter No. of Motif's in each Chromosome : ");
			int nm = sc.nextInt();
			// int nm = 4;

			Random r = new Random();
			
			can= new Candidate[chromosomes.size()];
			//Candidate[] next_can= new Candidate[chromosomes.size()];
			pop_size=can.length;
			 parent=(parent_per*pop_size)/100;
			Initial=new Candidate[nm];
			int randomindex[] = new int[nm];

			System.out.println("Random Position are : ");
			for (int i = 0; i < chromosomes.size(); i++) {
				int range = chromosomes.get(i).length();
				for (int j = 0; j < nm; j++) {
					//String s1=seq[i1].substring(t, t+ml); 
				//	Initial[j]=r.nextInt(range - ml);
					randomindex[j] = r.nextInt(range - ml);
				}
				System.out.println(geneName.get(i));
				for (int j = 0; j < nm; j++) {
					System.out.println(randomindex[j]
							+ " "
							+ chromosomes.get(i).substring(randomindex[j],
									randomindex[j] + ml));
					String s1=chromosomes.get(i).substring(randomindex[j],randomindex[j] + ml);
					Candidate c=new Candidate(s1);
					Initial[j]=c;
					System.out.println(Initial[j].getName()+"   "+Initial[j].getFitness());
				}
				flag=true;
				Arrays.sort(Initial,Collections.reverseOrder());
				System.out.println("random candidate motifs");
				for(int k=0;k<Initial.length;k++){
					System.out.println(Initial[k].getName()+"   "+Initial[k].getFitness());
				}
				can[i]=Initial[0];

				System.out.println();
			}
			Arrays.sort(can,Collections.reverseOrder());
			for(int i=0;i<can.length;i++){
				System.out.println(can[i].getName()+"   "+can[i].getFitness());
			}
            
			System.out.println();
			WM=mut(can);
			 Prev_Pred_Motif=Pm(WM);
			ambiguities=Cons();
			M_Pool_gen(ambiguities);
			next_can=next_gen();
			iteration(next_can);
			
			
			
			fr.close();
			System.out.println("Number of DataSet Inputted: " + count);
			//System.exit(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	public Candidate[] next_gen()
	{
		next_can=new Candidate[can.length];
		//Random rand=null;
		Candidate[] pool=new Candidate[pop_size];                            //FORMATION OF MATING POOL -FORMED BY ROULETTE WHEEL SELECTION +BEST FITNESS PARENTS
		//int percent=parent_per*pop_size/100;
		flag=true;
		int i=0;
		Arrays.sort(can,Collections.reverseOrder());                                //CAN ARRAY SORTED IN DESCEN ORDER
		for(i=0;i<parent;i++)                                                        // BEST PARENTS FROM CAN ACC TO FITNESS TAKEN FROM CAN AND ADDED TO POOL =PARENT
			{
			pool[i]=can[i];
			next_can[i]=can[i];
			}
		pool[i]=Prev_Pred_Motif;
		Random r=new Random();
		int p=r.nextInt(parent+1);
		int q=r.nextInt(mate_pool.length);
		next_can[i]=crossover(pool[p].getName(),mate_pool[q].getName());
		//RW();
		i++;
		for(;i<pop_size;i++)
			{
			
			//rand=null;
			p=r.nextInt(parent+1);
			q=r.nextInt(mate_pool.length);
			next_can[i]=crossover(pool[p].getName(),mate_pool[q].getName());
			}
		System.out.println("POOL");
		for(int k=0;k<next_can.length;k++){
			System.out.println(next_can[k].getName()+"   "+next_can[k].getFitness());
		}
		return next_can;
	}
	public void iteration(Candidate[] next_can)
	{
		
		
		while(COUNT<10 &&  Prev_Pred_Motif!=Pred_Motif)
		{
			//Prev_Pred_Motif=Pred_Motif;
			can=next_can;
			System.out.println("COUNT:"+(COUNT++));
			WM=mut(can);
			Pred_Motif=Pm(WM);
			ambiguities=Cons();
			M_Pool_gen(ambiguities);
			String s1=Prev_Pred_Motif.getName();
			String s2=Pred_Motif.getName();
			if(s1==s2)
			{
				System.out.println(Pred_Motif.getName());
			}
			else
			{	
				Prev_Pred_Motif=Pred_Motif;
				Pred_Motif=null;
			next_gen();
		}
		
		}
		
		
	}
	
	public Candidate crossover(String s1,String s2){
		 Random rand = null;
		 String child0=new String();
		 String child1 = child0;
		 rand=new Random();
		 int n= rand.nextInt(motifLength);
		 //---System.out.println("SITE OF CROSSOVER:"+n);
		 double Cfit[]=new double[2];
			 
		 
		child0 =s1.substring(0, n)+s2.substring(n, motifLength);
		child1=s2.substring(0, n)+s1.substring(n, motifLength);
		//---System.out.println("child1:"+child0+"    "+"child2:"+child1);
		 
			
			Candidate c1= new Candidate(child0);
				 Candidate c2=new Candidate(child1);
				
				
					 //---System.out.println("Simple score:"+c1.getFitness());
					 Cfit[0]=c1.getFitness()-ExPenalty(c1.getName());
					 //---System.out.println("CHILD"+(0+1)+":"+Cfit[0]);

					 //---System.out.println("Simple score:"+c2.getFitness());
					 Cfit[1]=c2.getFitness()-ExPenalty(c2.getName());
					 //---System.out.println("CHILD"+(1+1)+":"+Cfit[1]);
					 
				 
				 if(Cfit[0]>Cfit[1])
				 {
					 
					 return c1;
				 }
				 else
					 return c2;
			
		 
	 }
	
	 //FUNCTION FOR FINDING PENALTY VALUE
	 public	 double ExPenalty(String s)
	 {
		 double exp=0.0;
		 double value=0.0;
		 for(int i=0;i<motifLength;i++){
			 char t=s.charAt(i);
			 if(Base.indexOf(t)>0)
			 {
				 continue;
			 }
			 else{
				 if(t=='N'){
					 value=0.5;
				 }
				 else
					 value=0.3;
			 }
		 }
		 exp+=value;
		 //---System.out.println(s+" "+"penalty:"+"  "+exp);
		 return exp;
	 }
	
	
	
	
	 public void M_Pool_gen(String[] ambiguities)                              
	 {
		 String prnts="";
		 Random rand=null;
		 mate_pool=new Candidate[pop_size-parent-1];                      //M_POOL ONLY INCLUDES THE PARENTS GENRATED BY
		 for(int j=0;j<mate_pool.length;j++){                             //RW.....I.E POP_SIZE-PARENTS
			 prnts="";
		 for(int i=0;i<ambiguities.length;i++)
			 {
			 		rand=new Random();
					int n= rand.nextInt(ambiguities[i].length());
					prnts+=ambiguities[i].charAt(n);
				  
			 }
		 
		  mate_pool[j]=new Candidate(prnts);
		  //---System.out.println("mp "+j+":"+mate_pool[j].getName()+"\t:"+mate_pool[j].getFitness());		  
		 }
		 /*flag=true;
		 Arrays.sort(mate_pool,Collections.reverseOrder());                      
		 for(int k=0;k<mate_pool.length;k++)
			 System.out.println("mp "+k+":"+mate_pool[k].getName()+"\t:"+mate_pool[k].getFitness());	*/
		 RW();
	 }
	 

	public Candidate[] RW()
	{
		sum=0;
		for(int l=0;l<mate_pool.length;l++)
			sum+=mate_pool[l].getFitness();
		System.out.println(sum);
		double[] cf=new double[mate_pool.length];
		cf[0]=mate_pool[0].getFitness()/sum;
	
		for(int i=1;i<mate_pool.length;i++)
		{
			cf[i]=cf[i-1]+mate_pool[i].getFitness()/sum;
		}
		for(int i=0;i<mate_pool.length;i++)
		{
			double n;
			do{
				n= Math.random();
			}
				while(n>sum);
			for(int j=0;j<mate_pool.length;j++)
			{
				if(cf[j]>=n){
					int t=(mate_pool[j].getCount()+1);
					mate_pool[j].setCount(t);
					break;
				}
					
			}
		}
		/*for(int k=0;k<mate_pool.length;k++)
			 System.out.println("mp "+k+":"+mate_pool[k].getName()+"\t:"+mate_pool[k].getFitness()+"\t:"+mate_pool[k].getCount());*/	
		flag=false;
		Arrays.sort(mate_pool,Collections.reverseOrder());
		//---for(int k=0;k<mate_pool.length;k++)
			//--- System.out.println("mp "+k+":"+mate_pool[k].getName()+"\t:"+mate_pool[k].getFitness()+"\t:"+mate_pool[k].getCount());	
		return mate_pool;
		
	}
	public	float[][] mut(Candidate[] can){
		 float weightMatrix[][]=new float[4][motifLength];
		  String t="";
		  
			for(int i=0;i<motifLength;i++){
				for(int j=0;j<can.length;j++){
					//---System.out.println(can[j].getName());
					t=can[j].getName();
					
					char ch=t.charAt(i);
					//System.out.println(ch);
					int b=Base.indexOf(ch);
					//System.out.println(b);
					
					weightMatrix[b][i]+=1.0/pop_size;                            
					
					//System.out.println(weightMatrix[b][i]);
				         
				}
			
			}
			disp_WM(weightMatrix);
			
	        //=Cons(weightMatrix);
		   //System.out.println("Consensus string:"+consensus );
		   
		   return weightMatrix;
		
			
		}
	 //Finding the predicted motif from weight matrix-consensus string
	 //@SuppressWarnings("null")
	public Candidate Pm(float[][] wm){
		 
		 int k = 0,t = 0,value=0;
		 float f;
		
		 
		 String temp=new String();
		 
		 String st="";
		 for(int i=0;i<motifLength;i++){
				for(int j=1;j<4;j++){
					
					if(wm[j][i]!=0){
					
					
					
						if(wm[j-1][i]==wm[j][i]){                                //in consensus deriving strings of form CONTAING AMBIGUITY CODE LETTER WONT BE USEFUL
                           													//AS SAID BY SIR,THEREFORE IT WILL GIVE CONSENSUS IN FORM ATGC CODES only
						
							
						t=j;
						}
				if(wm[j-1][i]>wm[j][i]){
				
					t=j-1;
					
					
				}
				else
				{
					t=j;
				}
							}
					
					else {
						continue;
					}
					
					if(wm[value][i]>wm[t][i]){
						continue;
						
					}
					else
						value=t;
			
					}
								
				st+=Base.charAt(value);
				
				//---System.out.println("st"+st);
						 
		 }
		 Candidate c=new Candidate(st);
		 
		 //Pred_Motif=c;
		 System.out.println("Pm:"+st);
		 return c;
		 
	 }
	 public String[] Cons(){
		 consensus="";
		 String s=new String();;
		 char arr[];
		 int k=0;
		 String[] ambiguities=new String[motifLength];
			 for(int i=0;i<motifLength;i++){
					for(int j=0;j<4;j++){
						if(WM[j][i]!=0){
						s+=Base.charAt(j);
			 
		 }
						else
							continue;
					}
					System.out.println(s);
					if(s.length()==1)
					{
						consensus+=s;
					}
					else{
						arr=s.toCharArray();
						outer: for(int c=0;c<ambCode.length;c++){
							
								if(ambCode[c].length==arr.length){
									String str;
									str=String.copyValueOf(ambCode[c]);
									for(int j=0;j<arr.length;j++){
									if(str.indexOf(arr[j])<0){
										continue outer;
									}	
									else
										if((j==arr.length-1))
									//if(j==arr.length-1)
										{
										k=c;
										System.out.println(k);
										break;
									}
									continue;
									
									
								}
									//k=c;
									//continue;					
								}
								else
									continue outer;
								consensus+=ambiguity.charAt(k);
								break;
							}
						
					}
					ambiguities[i]=s;
					s="";
						
					}
	System.out.println("cons:"+consensus);
			 return ambiguities;
					
			 }
	 
	 void disp_WM(float[][] wm )
		{
			for(int i=0;i<wm.length;i++)
			{
				System.out.println();
				for(int j=0;j<motifLength;j++)
					System.out.print(wm[i][j]+"\t");
			}
		}
	public static void main(String args[]){
		FMGA3 f=new FMGA3();
	}

	
	//CANDIDATE CLASS DEFINING CANDIDATE OBJECT STRUCTURE //
	public class Candidate implements Comparable<Candidate>{
		String motif;
		double Fit;
		int count;
		public Candidate(){
			this.motif=null;
			this.Fit=0;
		}
		public Candidate(String s){
			this.motif=s;
			this.Fit=TFS(s);
		}
		public double TFS(String s){
			
			 
			double value=0,sum=0;
			for(int i=0;i<motifLength;i++){
				char s1=s.charAt(i);
				char p=Promoter.charAt(i);
				if(p==s1)
				{
					value=1;
					//System.out.println(value);
				}
				else
				{
					if(ambiguity.indexOf(p)!=-1)
					{
						int a=ambiguity.indexOf(p);
					
						for(int j=0;j<ambCode[a].length;j++){
					
							if(s1==ambCode[a][j]){
								value=0.5;
								//System.out.println(value);
								break;
							}
							else
							{
								continue;
							}
									               
				}
						
			}
					else{
						value=0;
						
					}
		}
				sum+=value;
				value=0;
		}
			
			return sum;
		}
		public String getName(){
			return motif;
		}
		public void setName(String s){
			this.motif=s;
		}
		public double getFitness(){
			return Fit;
		}
		public void setCount(int count){
			this.count=count;
		}
		public int getCount(){
			return count;
		}
		public int compareTo(Candidate o){
			if (flag==true)
				return  (int) ((int)this.Fit-o.Fit);
			else
				return (int) ((int)this.count-o.count);
		}
		public String toString(){
			return "motif:"+motif+"fitness:"+Fit+"COUNT"+count;
		}
		
		
	}
	
}
