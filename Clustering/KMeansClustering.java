package motifidentification;

//import java.util.*;

public class KMeansClustering {
	public static void main(String[] args) {

		KMeansClustering kmc = new KMeansClustering();

		int nclusters = 2;
		int pointMatrix[][] = { { 1, 1 }, { 2, 1 }, { 4, 3 }, { 5, 4 } };
		int distanceMatrix[][] = new int[nclusters][pointMatrix.length];
		int centroids[][] = new int[nclusters][2];

		// initialize centroids
		for (int i = 0; i < nclusters; i++) {
			centroids[i][0] = pointMatrix[i][0];
			centroids[i][1] = pointMatrix[i][1];
		}

		for (int k = 0; k < 2; k++) {
			distanceMatrix = kmc.calculateDistanceMatrix(distanceMatrix,
					centroids, pointMatrix);
			distanceMatrix = kmc.adjustDistanceMatrix(distanceMatrix);
			centroids = kmc.calculateCentroids(distanceMatrix, pointMatrix);
		}

		for (int i = 0; i < nclusters; i++) {
			for (int j = 0; j < pointMatrix.length; j++) {
				System.out.print(distanceMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	public int[][] calculateCentroids(int distanceMatrix[][],
			int pointMatrix[][]) {
		int centroids[][] = new int[2][distanceMatrix.length];
		int xtemp, ytemp;
		int xcount, ycount;
		for (int i = 0; i < distanceMatrix.length; i++) {
			xtemp = ytemp = 0;
			xcount = ycount = 0;
			for (int j = 0; j < distanceMatrix[i].length; j++) {
				if (distanceMatrix[i][j] == 1) {
					xtemp += pointMatrix[j][0];
					xcount++;
					ytemp += pointMatrix[j][1];
					ycount++;
				}
			}

			centroids[i][0] = xtemp / xcount;
			centroids[i][1] = ytemp / ycount;
		}

		return centroids;
	}

	public int[][] adjustDistanceMatrix(int distanceMatrix[][]) {

		for (int j = 0; j < distanceMatrix[0].length; j++) {
			int mintemp = Integer.MAX_VALUE;
			for (int i = 0; i < distanceMatrix.length; i++) {
				if (distanceMatrix[i][j] < mintemp) {
					mintemp = distanceMatrix[i][j];
				}
			}

			int tcount = 0;
			for (int i = 0; i < distanceMatrix.length; i++) {
				if (distanceMatrix[i][j] != mintemp) {
					distanceMatrix[i][j] = 0;
				} else {
					if (tcount == 0) {
						distanceMatrix[i][j] = 1;
						tcount++;
					} else {
						distanceMatrix[i][j] = 0;
					}
				}
			}
		}
		return distanceMatrix;
	}

	public int[][] calculateDistanceMatrix(int distanceMatrix[][],
			int centroids[][], int pointMatrix[][]) {

		for (int i = 0; i < distanceMatrix.length; i++) {
			for (int j = 0; j < pointMatrix.length; j++) {
				distanceMatrix[i][j] = (int) Math.sqrt(Math.pow(centroids[i][0]
						- pointMatrix[j][0], 2)
						+ Math.pow(centroids[i][1] - pointMatrix[j][1], 2));
			}
		}
		return distanceMatrix;
	}
}
