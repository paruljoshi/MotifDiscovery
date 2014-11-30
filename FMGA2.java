import java.io.*;
import java.util.*;

public class FMGA2 {
	String Promoter;
	// String[] motif=new String[10];
	// String[] child=new String[2];
	String c = new String();

	double[] fitness = new double[10];
	float WM[][];
	int nos;
	boolean flag;
	static Candidate[] can;
	Candidate[] next_can;
	// NO OF SEQUENCES
	char PossBase[] = { 'A', 'C', 'G', 'T', 'M', 'R', 'W', 'S', 'Y', 'K', 'V',
			'H', 'D', 'B', 'N' };
	String Base = "ATGC";
	int motifLength;
	int pop_size;
	int parent_per; // PARENT AMOUNT IN NEXT GEN
	int parent;
	Candidate Pred_Motif;
	String consensus = new String();// PARENT NUMBER IN INTEGER
	String ambiguity = "MRWSYKVHDBN"; // IUPAC CODING LETTERS
	char ambCode[][] = { // CORRESPONDING CHARACTER ARRAY CONTAING BASES FOR
			// EACH IUPAC CODE
			{ 'A', 'C' }, { 'A', 'G' }, { 'A', 'T' }, { 'C', 'G' },
			{ 'C', 'T' }, { 'G', 'T' }, { 'A', 'C', 'G' }, { 'A', 'C', 'T' },
			{ 'A', 'G', 'T' }, { 'C', 'G', 'T' }, { 'G', 'A', 'T', 'C' },

	};
	double sum = 0;
	String[] name = new String[10];
	String[] seq = new String[10];
	int[] Seqlength = new int[10];
	Candidate c1 = new Candidate();

	double wm[][];
	double weightMatrix[][] = new double[4][motifLength];

	public FMGA2() {
		Scanner sc = new Scanner(System.in);
		System.out.println("enter the population size:");
		pop_size = sc.nextInt();
		System.out.println("enter the motif length");
		motifLength = sc.nextInt();

		System.out.println("Enter the promoter sequence of the same length");
		Promoter = sc.next();
		System.out
				.println("enter no of Candidate motif required in each sequence:");
		int cm = sc.nextInt();
		// System.out.println("Enter the no of motif patterns :");
		// nos=sc.nextInt();
		System.out.println("Enter the % of parents in new gen :");
		parent_per = sc.nextInt();
		parent = parent_per * pop_size / 100;
		Candidate[] can = new Candidate[pop_size];
		/*
		 * for(int i=0;i<pop_size;i+=2)
		 * {System.out.println("enter the sequences"+i+":"); String m=sc.next();
		 * //FIRST RANDOM POPULATION FORMED BY INPUT PARENTS+THEIR RANDOM
		 * CROSSOVER EITH A DEFAULT sTRING can[i]=new Candidate(m);
		 * sum+=can[i].getFitness(); can[i+1]=crossover(m,"AGATG");
		 * sum+=can[i+1].getFitness(); }
		 * 
		 * for(int i=0;i<pop_size;i++){ //Candidate c=can[i];
		 * System.out.println(can[i].getName()); }
		 */
         Input(motifLength);
         WM = mut(can);
 		Pm(WM);
 		Cons();
         
	}

	public void Input(int motifLength) {

		String[] temp = new String[4];
		Candidate[] Initial = new Candidate[4];

		int ml = motifLength;
		int n = 0;
		// System.out.println("enter motif length");
		// ml=sc.nextInt();

		Random r = new Random();
		try {
			FileReader f = new FileReader("MIG1.txt");
			BufferedReader br = new BufferedReader(f);
			String s;
			int i = 0, j = 0;
			while ((s = br.readLine()) != null) {
				String st = s;

				// System.out.println(s);
				n = st.length();

				if (n != 0 && i < 10) {
					if (n <= 20) {

						name[i] = st;
						System.out.println(name[i]);
						System.out.println(n);

					} else {
						String s1 = st.replaceAll("\\s+", "");

						seq[j] = s1;
						System.out.println(seq[j]);
						Seqlength[j] = s1.length();
						System.out.println(Seqlength[j]);

					}
					j = i;
					i++;
				} else {
					i = i - 1;
				}

			}
			for (int i1 = 0; i1 < pop_size; i1++) {
				n = Seqlength[i1];
				int t = r.nextInt(n);
				int m, k, l;
				m = k = l = t;
				while (m == t)
					m = r.nextInt(n);
				while (k == t || k == m)
					k = r.nextInt(n);
				while (l == t || l == m || k == l)
					l = r.nextInt(n);

				temp[0] = (seq[i1].substring(t, t + ml));
				// Candidate s1=new Candidate(c1);
				// v vll make a Candidate array named-Initials and best
				// candidate
				// motif
				temp[1] = seq[i1].substring(m, m + ml);
				// Candidate s2=new Candidate(c2);
				// in these 4 Candidate motifs vl be chosen for can
				temp[2] = seq[i1].substring(k, k + ml);
				// Candidate s3=new Candidate(c3);

				temp[3] = seq[i1].substring(l, l + ml);
				// Candidate s4=new Candidate(c4);

				// Candidate[] Initial =new Candidate[4];
				for (int d = 0; d < 4; d++) {
					Candidate c = new Candidate(temp[d]);
					Initial[d] = c;
					
					System.out.println(Initial[d].getName()+" "+Initial[d].getFitness());
				}

				flag = true;
				Arrays.sort(Initial, Collections.reverseOrder());
				can[i1] = Initial[0];
				System.out.println(can[i1].getName());

			}
		} catch (Exception e) {
		}

		
	}

	// FUNCTION FOR NEXT GEN POP
	public void next_gen() {

		Random rand = null;
		Candidate[] pool = new Candidate[pop_size]; // FORMATION OF MATING POOL
													// -FORMED BY ROULETTE WHEEL
													// SELECTION +BEST FITNESS
													// PARENTS
		// int percent=parent_per*pop_size/100;
		flag = true;
		int i = 0;
		Arrays.sort(can, Collections.reverseOrder()); // CAN ARRAY SORTED IN
														// DESCEN ORDER
		for (i = 0; i < parent; i++) // BEST PARENTS FROM CAN ACC TO FITNESS
										// TAKEN FROM CAN AND ADDED TO POOL
										// =PARENT
		{
			pool[i] = can[i];
			next_can[i] = can[i];
		}

		RW();
		for (; i < pop_size; i++) {
			pool[i] = can[i - parent];
		}
		int p1 = rand.nextInt(parent);
		int p2 = 0;
		while (p2 <= parent) {
			p2 = rand.nextInt(pop_size);

			// can[parent]=crossover(m,"AGATGAG");
		}
	}

	public float[][] mut(Candidate[] can) {
		float weightMatrix[][] = new float[4][motifLength];
		String t = "";
		for (int i = 0; i < motifLength; i++) {
			for (int j = 0; j < pop_size; j++) {
				System.out.println(can[j].getName());
				t = can[j].getName();

				char ch = t.charAt(i);
				System.out.println(ch);
				int b = Base.indexOf(ch);
				System.out.println(b);

				weightMatrix[b][i] += 1.0 / nos;

				// System.out.println(weightMatrix[b][i]);

			}

		}
		disp_WM(weightMatrix);

		// =Cons(weightMatrix);
		System.out.println("Consensus string:" + consensus);

		return weightMatrix;

	}

	// Finding the predicted motif from weight matrix-consensus string
	@SuppressWarnings("null")
	public void Pm(float[][] wm) {

		int k = 0, t = 0, value = 0;
		float f;

		String temp = new String();

		String st = "";
		for (int i = 0; i < motifLength; i++) {
			for (int j = 1; j < 4; j++) {

				if (wm[j][i] != 0) {

					if (wm[j - 1][i] == wm[j][i]) { // in consensus deriving
													// strings of form CONTAING
													// AMBIGUITY CODE LETTER
													// WONT BE USEFUL
													// AS SAID BY SIR,THEREFORE
													// IT WILL GIVE CONSENSUS IN
													// FORM ATGC CODES only

						t = j;
					}
					if (wm[j - 1][i] > wm[j][i]) {

						t = j - 1;

					} else {
						t = j;
					}
				}

				else {
					continue;
				}

				if (wm[value][i] > wm[t][i]) {
					continue;

				} else
					value = t;

			}

			st += Base.charAt(value);

			System.out.println("st" + st);

		}

		System.out.println("Pm:" + st);

	}

	public String Cons() {

		String s = new String();
		;
		char arr[];
		int k = 0;
		for (int i = 0; i < motifLength; i++) {
			for (int j = 0; j < 4; j++) {
				if (WM[j][i] != 0) {
					s += Base.charAt(j);

				} else
					continue;
			}
			System.out.println(s);
			if (s.length() == 1) {
				consensus += s;

			} else {
				arr = s.toCharArray();
				for (int c = 0; c < ambCode.length; c++) {

					if (ambCode[c].length == arr.length) {
						String str;
						str = String.copyValueOf(ambCode[c]);
						for (int j = 0; j < arr.length; j++) {
							if (str.indexOf(arr[j]) < 0) {
								break;
							} else if (arr[j] == str.charAt(arr.length - 1)
									&& (j == arr.length - 1))
							// if(j==arr.length-1)
							{
								k = c;
								System.out.println(k);
								break;
							}
							continue;

						}
						// k=c;
						// continue;
					}
					consensus += ambiguity.charAt(k);
					break;
				}

			}
			s = "";

		}
		System.out.println("cons:" + consensus);
		return consensus;

	}

	void disp_WM(float[][] wm) {
		for (int i = 0; i < wm.length; i++) {
			System.out.println();
			for (int j = 0; j < motifLength; j++)
				System.out.print(wm[i][j] + "\t");
		}
	}

	public Candidate crossover(String s1, String s2) {
		Random rand = null;
		String child0 = new String();
		String child1 = child0;
		rand = new Random();
		int n = rand.nextInt(motifLength);

		System.out.println("SITE OF CROSSOVER:" + n);
		double Cfit[] = new double[2];

		child0 = s1.substring(0, n) + s2.substring(n, motifLength);
		child1 = s2.substring(0, n) + s1.substring(n, motifLength);
		System.out.println("child1:" + child0 + "    " + "child2:" + child1);

		Candidate c1 = new Candidate(child0);
		Candidate c2 = new Candidate(child1);

		System.out.println("Simple score:" + c1.getFitness());
		Cfit[0] = c1.getFitness() - ExPenalty(c1.getName());
		System.out.println("CHILD" + (0 + 1) + ":" + Cfit[0]);

		System.out.println("Simple score:" + c2.getFitness());
		Cfit[1] = c2.getFitness() - ExPenalty(c2.getName());
		System.out.println("CHILD" + (1 + 1) + ":" + Cfit[1]);

		if (Cfit[0] > Cfit[1]) {

			return c1;
		} else
			return c2;

	}

	// FUNCTION FOR FINDING PENALTY VALUE
	public double ExPenalty(String s) {
		double exp = 0.0;
		double value = 0.0;
		for (int i = 0; i < motifLength; i++) {
			char t = s.charAt(i);
			if (Base.indexOf(t) > 0) {
				continue;
			} else {
				if (t == 'N') {
					value = 0.5;
				} else
					value = 0.3;
			}
		}
		exp += value;
		System.out.println(s + " " + "penalty:" + "  " + exp);
		return exp;
	}

	public static void main(String[] args) {
		FMGA2 f = new FMGA2();
	}

	public Candidate[] RW() {
		double[] cf = new double[can.length];
		for (int i = parent; i < can.length; i++) {
			cf[i - parent] += can[i].getFitness() / sum;
		}
		for (int i = parent; i < can.length - parent; i++) {
			Random rand = null;
			double n = Math.random();
			for (int j = 0; j < can.length - parent; j++) {
				if (cf[j] >= n) {
					int t = (can[j].getCount() + 1);
					can[j].setCount(t);
				}

			}
		}
		flag = false;
		Arrays.sort(can, Collections.reverseOrder());
		return can;

	}

	public class Candidate implements Comparable<Candidate> {
		String motif;
		double Fit;
		int count;

		public Candidate() {
			this.motif = null;
			this.Fit = 0;
		}

		public Candidate(String s) {
			this.motif = s;
			this.Fit = TFS(s);
		}

		public double TFS(String s) {

			double value = 0, sum = 0;
			for (int i = 0; i < motifLength; i++) {
				char s1 = s.charAt(i);
				char p = Promoter.charAt(i);
				if (p == s1) {
					value = 1;
					// System.out.println(value);
				} else {
					if (ambiguity.indexOf(p) != -1) {
						int a = ambiguity.indexOf(p);

						for (int j = 0; j < ambCode[a].length; j++) {

							if (s1 == ambCode[a][j]) {
								value = 0.5;
								// System.out.println(value);
								break;
							} else {
								continue;
							}

						}

					} else {
						value = 0;

					}
				}
				sum += value;
				value = 0;
			}

			return sum;
		}

		public String getName() {
			return motif;
		}

		public void setName(String s) {
			this.motif = s;
		}

		public double getFitness() {
			return Fit;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public int getCount() {
			return count;
		}

		public int compareTo(Candidate o) {
			if (flag == true)
				return (int) ((int) this.Fit - o.Fit);
			else
				return (int) ((int) this.count - o.count);
		}

		public String toString() {
			return "motif:" + motif + "fitness:" + Fit;
		}

	}
}
