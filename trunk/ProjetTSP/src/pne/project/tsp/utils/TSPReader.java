package pne.project.tsp.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class TSPReader {

	/**
	 * @param filePath
	 * @return int[][] corresponding to node positions
	 */
	public static int[][] getPositionsFromTsp(String filePath) {
		Scanner scanner;
		int tabSize = 0;
		int[][] tabPos = null;
		
		try {
			scanner = new Scanner(new File(filePath));		 
			while (scanner.hasNextLine()) {
			    String line = scanner.nextLine();

			    /* get dimension */
			    if (line.split(" ")[0].equals("DIMENSION")){
			    	String[] a = line.split(" ");
			    	tabSize = Integer.parseInt(a[a.length-1]);
			    }

			    tabPos = new int[tabSize][2];
			    int i =0;
			    
			    /* get first position */
			    if (line.split(" ")[0].equals("1")){
			    	while(scanner.hasNextLine()){
			    		/* traitement */
			    		tabPos[i][0] = Integer.parseInt(line.split(" ")[1]);		
			    		tabPos[i][1] = Integer.parseInt(line.split(" ")[2]);		
			    		
			    		i++;
			    		line = scanner.nextLine();
			    	}
			    }
			}			 
			scanner.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
//		for(int j=0;j<tabSize;j++){
//			for(int k=0;k<2;k++){
//				System.out.println("tabpos i "+j+", j "+k+" = "+tabPos[j][k]);
//			}
//		}
		
		return tabPos;
	}

}
