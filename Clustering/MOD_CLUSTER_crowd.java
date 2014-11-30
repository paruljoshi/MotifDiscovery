import java.io.*;
import java.util.*;

public class MOD_CLUSTER_crowd {
	int motifLength;
	int nm;
	int mut_per;
	Candidate[] can;
	Candidate[] Initial;
	Candidate[] pool;
	Candidate[] mate_pool;
	Candidate[] next_can;
	Candidate[] temp;
	Candidate[] Transition_pool;
	String[] parent12;
	String[] child12;
	char[] Base={'A','T','G','C'};
	Random rand=new Random();
	int[] temp_size;
	int p_size;
	int pop_size;
	int mut;
	int sum;
	char flag;
	int z=0;
	int iter_no;
	static float start_time;
	
	 ArrayList<ArrayList<Candidate>> main=new ArrayList<ArrayList<Candidate>>();
	 ArrayList<Candidate> cl=new ArrayList<Candidate>();
	public MOD_CLUSTER_crowd(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Motif Length : ");
		int ml = sc.nextInt();
		motifLength=ml;
		
		
		 System.out.println("Enter the % of mutation in new gen :");
		 mut_per=sc.nextInt();
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
			 //parent=(mut_per*pop_size)/100;
			Initial=new Candidate[nm];
			int randomindex[] = new int[nm];

			System.out.println("Random Position are : ");
			for (int i = 0; i < chromosomes.size(); i++) {
				int range = chromosomes.get(i).length();
				for (int j = 0; j < nm; j++) {
				
					randomindex[j] = r.nextInt(range - ml);
				}
				System.out.println(geneName.get(i));
				for (int j = 0; j < nm; j++) {
					
					String s1=chromosomes.get(i).substring(randomindex[j],randomindex[j] + ml);
					Candidate c=new Candidate(s1);
					c.setPosition(randomindex[j]);
					Initial[j]=c;
					
					System.out.println(Initial[j].getName()+"   "+Initial[j].getPosition());
				}
				//the Initial ARRAY FORMED OUT OF EVERY SEQUENCE IN THEN SEND TO THE FITNESS FUNCTION 
				//AND THEN THE BEST CANDIDATE MOTIF OUT OF THE INITIAL ARRAY IS SELECTED AND SEND IN THE
				//FIRST RANDOM GENRATION ARRAY i.e CAN
				
				//CALLING FITNESS FUNCTION'
				FitnessCalculation(Initial);
				flag='0';
				Arrays.sort(Initial,Collections.reverseOrder());
				System.out.println("random candidate motifs");
				for(int k=0;k<Initial.length;k++){
					System.out.println(Initial[k].getName()+"   "+Initial[k].getFitness());
				}
				//FIRST RANDOM POPULATION CREATED WITH BEST CANDIDATE MOTIFS FROM EACH SEQUENCE//
				can[i]=Initial[0];

				System.out.println();
			}
			flag='0';
			Arrays.sort(can,Collections.reverseOrder());
			for(int i=0;i<can.length;i++){
				System.out.println(can[i].getName()+"   "+can[i].getFitness());
			}
            
			System.out.println();
			makingCluster();
			
			
			
			fr.close();
			System.out.println("Number of DataSet Inputted: " + count);
			//System.exit(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	
		// categorising the consensus strings according to hamming distance
		public void makingCluster() {
			for (int i = 0; i < can.length; i++) {

				// Calculate Hamming distance
				int hdistance = 0;
				for (int j = 0; j < can[0].getName().length(); j++) {
					// condition of hamming distance
					if (can[i].getName().charAt(j) != can[0].getName().charAt(j)) {
						hdistance++;
					}
				}

				// adding the consensus to cluster arraylist of index hdistance
				can[i].setClusterno(hdistance);
			}
			flag='2';
			Arrays.sort(can);
			int k=0;
			temp_size=new int[motifLength+1];
			for(int l=0;l<motifLength+1;l++)
			{
				for(;k<can.length;k++)
				{				
					if(can[k].getClusterno()==l)
						temp_size[l]++;
					else
						
						break;
						
				}
			}
			if(temp_size[0]>can.length-1)
			{
				System.out.println("MOTIF : "+can[0].getName());
				long exit_time=System.currentTimeMillis();
				System.out.println("TIME TAKEN : <="+((exit_time-start_time)/1000)+1+"seconds");
				System.exit(1);
			}
			else
				categorizeCluster();
		}
		
		public void categorizeCluster()
		{
			int j=0;
			z=0;
			pool=new Candidate[can.length];
			//int k=0;
			for(int i=0;i<motifLength+1;i++)
			{
				if(temp_size[i]==0)
					continue;
				temp=new Candidate[temp_size[i]];
				
				for(int l=0;j<can.length;j++,l++)
				{
					if(i==can[j].getClusterno())
						{temp[l]=can[j];}
					else{
						break;
					}	
					
				}
				if(temp_size[i]==0)
					continue;
				else
					if(temp_size[i]==1)
						pool[z++]=temp[0];
					else{
					flag='0';
					Arrays.sort(temp,Collections.reverseOrder());
					pool[z++]=temp[0];
					pool[z++]=RW(temp);
				}
			}
			disp();
			Trans_pool();
			mut();
			next_gen();	
			iteration();
		}
		
		public void iteration()
		{
			while(true)
			{
				System.out.println("GENERATION NO. : "+(iter_no++));
				makingCluster();
			}
		}
		
		
		
		
		public void Trans_pool(){
			//CALL FOR CROSSOVER NEXT AFTER CREATION OF POOL
			Transition_pool=new Candidate[2*z];
			for(int m=0;m<2*z;m+=2){
				
				int x=0;
				int p=rand.nextInt(z);
				while (x == p)
					x = rand.nextInt(z);
				
			Crossover(pool[p].getName(),pool[x].getName());
			String a=child12[0];
			String b=child12[1];
			Candidate c1=new Candidate(a);
			Candidate c2=new Candidate(b);
			Transition_pool[m]=c1;
			Transition_pool[m+1]=c2;
		
				
			}
			//DISPLAYING TRANSITION POOL IN SORTED FORM w.r.t FITNESS//
			FitnessCalculation(Transition_pool);
			flag='0';
			Arrays.sort(Transition_pool,Collections.reverseOrder());
			System.out.println();
			System.out.println("TRANSITION POOL:");
			for(int i=0;i<2*z;i++){
				System.out.println(Transition_pool[i].getName()+"    "+Transition_pool[i].getFitness());
				
			}
			
		}
	
		public void disp(){
			System.out.println("POOL");
			System.out.println("MOTIF NAME"+"    "+"CLUSTER NO");
			for (int i = 0; i <z; i++) {
				System.out.println(pool[i].getName()+"\t"+pool[i].getClusterno());
				}
			}
				
		
		public Candidate RW(Candidate[] temp1)
		{
			sum=0;
			for(int l=0;l<temp1.length;l++){
					sum+=temp1[l].getFitness();
			}
			double[] cf=new double[temp1.length];
			cf[0]=temp1[1].getFitness()/sum;
		
			for(int i=1;i<temp1.length-1;i++)
			{
				cf[i]=cf[i-1]+temp1[i+1].getFitness()/sum;
			}
			for(int i=0;i<cf.length;i++)
			{
				double n;
				do{
					n= Math.random();
				}while(n>sum);
				for(int j=0;j<cf.length;j++)
				{
					if(cf[j]>=n){
						int t=(temp1[j+1].getCount()+1);
						temp1[j+1].setCount(t);
						break;
					}	
				}
			}
			flag='1';
			Arrays.sort(temp1,Collections.reverseOrder());
			
			return temp1[0];
			
		}
	
	
	//FITNESS CALCULATION FUNCTION
	public Candidate[] FitnessCalculation(Candidate[] c){
		int count=c.length;
		float fit=0;
		float f=0;
		float cA[] = new float[motifLength];
		float cT[] = new float[motifLength];
		float cG[] = new float[motifLength];
		float cC[] = new float[motifLength];

		for (int i = 0; i < motifLength; i++) {
			for (int j = 0; j < c.length; j++) {
				if (c[j].getName().charAt(i) == 'A') {
					cA[i]++;
				} else if (c[j].getName().charAt(i) == 'T') {
					cT[i]++;
				} else if (c[j].getName().charAt(i) == 'G') {
					cG[i]++;
				} else if (c[j].getName().charAt(i) == 'C') {
					cC[i]++;
				}
			}
		}
		
		//Printing the Count Matrix for finding the Consensus
		System.out.print("    ");
		for(int i=0;i<motifLength;i++) {
			System.out.print((i+1) + " ");
		}
		System.out.print("\nA : ");
		for(int i=0;i<motifLength;i++) {
			System.out.print(cA[i] + " ");
		}
		System.out.print("\nT : ");
		for(int i=0;i<motifLength;i++) {
			System.out.print(cT[i] + " ");
		}
		System.out.print("\nG : ");
		for(int i=0;i<motifLength;i++) {
			System.out.print(cG[i] + " ");
		}
		System.out.print("\nC : ");
		for(int i=0;i<motifLength;i++) {
			System.out.print(cC[i] + " ");
		}

		System.out.println("\n\n");
		// Printing the Probability Matrix for finding consensus
		System.out.print("    ");
		
		for(int i=0;i<motifLength;i++) {
			System.out.print("  " + (i+1) + " ");
		}
		System.out.print("\nA : ");
		for(int i=0;i<motifLength;i++) {
			System.out.print((cA[i]/(double)count) + " ");
		}
		System.out.print("\nT : ");
		for(int i=0;i<motifLength;i++) {
			System.out.print((cT[i]/(double)count) + " ");
		}
		System.out.print("\nG : ");
		for(int i=0;i<motifLength;i++) {
			System.out.print((cG[i]/(double)count) + " ");
		}
		System.out.print("\nC : ");
		for(int i=0;i<motifLength;i++) {
			System.out.print((cC[i]/(double)count) + " ");
		}
		//finding fitness of each element in the Candidate[] c array//
		for(int i=0;i<c.length;i++){
			
			for(int j=0;j<motifLength;j++){
				
				if (c[i].getName().charAt(j) == 'A') {
					f=cA[j]/count;
				} else if (c[i].getName().charAt(j) == 'T') {
					f=cT[j]/count;
				} else if (c[i].getName().charAt(j) == 'G') {
					f=cG[j]/count;
				} else if (c[i].getName().charAt(j) == 'C') {
					f=cC[j]/count;
				}
				fit+=f;
			}
			
			c[i].setFitness(fit*10000);
			fit=0;
		}
		
		return c;
	}
	
		//CROSSOVER FUNCTION -WITH SINGLE POINT,TWO POINT,,AND MASK CROSSOVER//
	public void Crossover(String s1,String s2) {
		 
			String[] parent12 = {s1,s2 };
			//CALL TYPE OF CROSSOVER REQUIRED//
			 child12 = singleSiteCrossover(parent12);
			System.out.println(child12[0] + " " + child12[1]);
			

			
		}

		public static String[] singleSiteCrossover(String[] parent) {

			Random r = new Random();
			int crossSite = r.nextInt(parent[0].length());
			// int crossSite = 5;

			String child[] = new String[2];
			child[0] = parent[0].substring(0, crossSite)
					+ parent[1].substring(crossSite);
			child[1] = parent[1].substring(0, crossSite)
					+ parent[0].substring(crossSite);

			return child;
		}

		public static String[] twoPointCrossover(String[] parent) {

			Random r = new Random();
			int crossSite1 = r.nextInt(parent[0].length());
			int crossSite2 = r.nextInt(parent[0].length());
			// int crossSite1 = 3;
			// int crossSite2 = 6;

			String child[] = new String[2];
			child[0] = parent[0].substring(0, crossSite1)
					+ parent[1].substring(crossSite1, crossSite2)
					+ parent[0].substring(crossSite2);
			child[1] = parent[1].substring(0, crossSite1)
					+ parent[0].substring(crossSite1, crossSite2)
					+ parent[1].substring(crossSite2);

			return child;
		}
		public static String[] uniformCrossover(String[] parent) {
			Random r = new Random();
			double n;
			char parent1[] = parent[0].toCharArray();
			char parent2[] = parent[1].toCharArray();

			String[] child = new String[2];
			for (int i = 0; i < parent1.length; i++) {
				n = r.nextDouble();
				if (n > 0.5) {
					char temp = parent1[i];
					parent1[i] = parent2[i];
					parent2[i] = temp;
				}
			}

			child[0] = new String(parent1);
			child[1] = new String(parent2);

			return child;
		}

		public static String[] maskCrossover(String[] parent) {
			int mask[] = new int[parent[0].length()];
			Random r = new Random();
			for (int i = 0; i < mask.length; i++) {
				mask[i] = r.nextInt(2);
			}

			char parent1[] = parent[0].toCharArray();
			char parent2[] = parent[1].toCharArray();

			for (int i = 0; i < parent1.length; i++) {
				if (mask[i] == 1) {
					char temp = parent1[i];
					parent1[i] = parent2[i];
					parent2[i] = temp;
				}
			}
			String child[] = new String[2];
			child[0] = new String(parent1);
			child[1] = new String(parent2);
			return child;
		}
		
		//MUTATION FUNCTION ONLY REPLACING LOW FITNESS MOTIFS AT THE END OF TRANSITION_POOL//
		public void mut(){
			mut=((mut_per*(2*z))/100);
			for(int i=2*z-1;i>(2*z-mut);i--){
			int l=	Transition_pool[i].getName().length();
			int n=rand.nextInt(l);
			int repl=rand.nextInt(4);
			//System.out.println(Transition_pool[i].getName());
			//System.out.println("after mutation");
			//---Transition_pool[i].setName(Transition_pool[i].getName().replace(Transition_pool[i].getName().charAt(n), Base[repl]));
			//System.out.println(Transition_pool[i].getName());
			StringBuffer str=new StringBuffer(Transition_pool[i].getName());
			str.setCharAt(n,Base[repl]);
			String s=new String(str);
			Transition_pool[i].setName(s);
			}
			FitnessCalculation(Transition_pool);
			flag='0';
			Arrays.sort(Transition_pool,Collections.reverseOrder());
						
		}
		public void next_gen(){
			System.out.println();
			System.out.println("***NEW POPULATION*** NO. : "+iter_no);
			if(Transition_pool.length<can.length)
				can=new Candidate[Transition_pool.length];
			for(int i=0;i<can.length;i++){
				can[i]=Transition_pool[i];
				System.out.println(can[i].getName()+"    "+can[i].getFitness());
			}
			
		
		}
	

	
	
	
	
	
	public static void main(String args[]){
		start_time=System.currentTimeMillis();
		MOD_CLUSTER_crowd cl=new MOD_CLUSTER_crowd();
		long exit_time=System.currentTimeMillis();
		System.out.println("TIME TAKEN:"+((exit_time-start_time)/1000)+1+"seconds");
	}
	//MAKING CLUSTERS OF THE RECIEVED GENRATION//
	
	
	//CANDIDATE CLASS DEFINING CANDIDATE OBJECT STRUCTURE //
	public class Candidate implements Comparable<Candidate>{
		String motif;
		double Fit;
		int position;
		int count;
		int cluster_no;
		public Candidate(){
			this.motif=null;
			//this.Fit=0;
		}
		public Candidate(String s){
			this.motif=s;
		
		}
		
		public String getName(){
			return motif;
		}
		public void setName(String s){
			this.motif=s;
		}
		public void setFitness(double fit){
			this.Fit=fit;
		}
		public double getFitness(){
			return Fit;
		}
		public void setPosition(int pos){
			this.position=pos;
		}
		public int getPosition(){
			return position;
		}
		public void setCount(int count){
			this.count=count;
		}
		public int getCount(){
			return count;
		}
		public void setClusterno(int cluster_no){
			this.cluster_no=cluster_no;
		}
		public int getClusterno(){
			return cluster_no;
		}
		public int compareTo(Candidate o){
			if (flag=='0')
				return  (int) ((int)this.Fit-o.Fit);
			else
				if(flag=='1')
				return (int) ((int)this.count-o.count);
				else
					if(flag=='2')
					return (int) ((int)this.cluster_no-o.cluster_no);
			return 0;
		}
		public String toString(){
			return "motif:"+motif+"fitness:"+Fit+"COUNT"+count+"CLUSTER NO"+cluster_no;
		}

		
		}


		
		
	}
	


