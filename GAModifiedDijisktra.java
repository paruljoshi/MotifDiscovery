
//package com.softtechdesign.ga.examples;

// import com.softtechdesign.ga.*;

public class GAModifiedDijisktra extends GAString {

	static int graph[][] = {

			{ Integer.MAX_VALUE, 41, Integer.MAX_VALUE, Integer.MAX_VALUE,
					Integer.MAX_VALUE, 60 },
			{ Integer.MAX_VALUE, Integer.MAX_VALUE, 51, Integer.MAX_VALUE, 32,
					Integer.MAX_VALUE },
			{ Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 50,
					Integer.MAX_VALUE, Integer.MAX_VALUE },
			{ 45, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
					Integer.MAX_VALUE, 38 },
			{ Integer.MAX_VALUE, Integer.MAX_VALUE, 32, 36, Integer.MAX_VALUE,
					Integer.MAX_VALUE },
			{ Integer.MAX_VALUE, 29, Integer.MAX_VALUE, Integer.MAX_VALUE, 21,
					Integer.MAX_VALUE } };

	public GAModifiedDijisktra() throws GAException {

		super(4, // size of chromosome
				300, // population has N chromosomes
				0.7, // crossover probability
				10, // random selection chance % (regardless of fitness)
				2000, // max generations
				0, // num prelim runs (to build good breeding stock for
					// final/full run)
				25, // max generations per prelim run
				0.06, // chromosome mutation prob.
				0, // number of decimal places in chrom
				"01", // gene space (possible gene values)
				Crossover.ctOnePoint, // crossover type
				true); // compute statisitics?

	}

	public static void main(String[] args) {
		System.out.println("GAModified Dijikstra GA...");
		try {
			GAModifiedDijisktra gadijik = new GAModifiedDijisktra();
			Thread threadBinaryOnes = new Thread(gadijik);
			threadBinaryOnes.start();
		} catch (GAException gae) {
			System.out.println(gae.getMessage());
		}
	}

	@Override
	protected double getFitness(int iChromIndex) {
		// TODO Auto-generated method stub
		int value = 0;
		String s = this.getChromosome(iChromIndex).getGenesAsStr();
		int currindex = 0;

		for (int i = 1; i < 5; i++) {
			if (s.charAt(i - 1) == '1') {
				if (i == 2) {
					i = 3;
				}
				int temp = graph[currindex][i];
				if (temp == Integer.MAX_VALUE) {
					return 0;
				}
				value += temp;
				currindex = i;
			}
		}
		
		int temp = graph[currindex][2];
		if (temp == Integer.MAX_VALUE) {
			if (value == 0) {
				return 0;
			}
		}

		if (value == 0) {
			return 0;
		} else {
			return 1.0 / value;
		}
	}

}

/*class DijikNextOpt extends GAString {
	public DijikNextOpt() throws GAException {

		super(4, // size of chromosome
				300, // population has N chromosomes
				0.7, // crossover probability
				10, // random selection chance % (regardless of fitness)
				2000, // max generations
				0, // num prelim runs (to build good breeding stock for
					// final/full run)
				25, // max generations per prelim run
				0.06, // chromosome mutation prob.
				0, // number of decimal places in chrom
				"01", // gene space (possible gene values)
				Crossover.ctOnePoint, // crossover type
				true); // compute statisitics?

	}
}*/