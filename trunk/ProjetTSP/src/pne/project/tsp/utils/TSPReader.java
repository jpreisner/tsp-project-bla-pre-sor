package pne.project.tsp.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TSPReader {

	/**
	 * @param filePath
	 * @return int[][] corresponding to node positions
	 */
	public static double[][] getPositionsFromTsp(String filePath, BoundsGraph bg) {
		Scanner scanner;
		int tabSize = 0;
		double[][] tabPos = null;

		try {
			scanner = new Scanner(new File(filePath));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				/* finding graph size in filePath */
				Pattern p = Pattern.compile("\\d+");
				Matcher m = p.matcher(filePath); 
				m.find();
				tabSize = Integer.parseInt(m.group());	
				
				tabPos = new double[tabSize][2];
				int i = 0;

				/* get first position */
				if (line.split(" ")[0].equals("1")) {
					while (scanner.hasNextLine()) {
						try{						
							/* traitement */
							/* x */
							double x = Double.parseDouble(line.split(" ")[1]);
							tabPos[i][0] = x;
							if (x > bg.getxMax()) {
								bg.setxMax(x);
							} else if (x < bg.getxMin()) {
								bg.setxMin(x);
							}
	
							/* y */
							double y = Double.parseDouble(line.split(" ")[2]);
							tabPos[i][1] = y;
							if (y > bg.getyMax()) {
								bg.setyMax(y);
							} else if (y < bg.getyMin()) {
								bg.setyMin(y);
							}
	
							i++;
//							System.out.println(line);
	
							line = scanner.nextLine();
							}catch (NumberFormatException e){
								break;
							}
					}
				}
			}
			scanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// for(int j=0;j<tabSize;j++){
		// for(int k=0;k<2;k++){
		// System.out.println("tabpos i "+j+", j "+k+" = "+tabPos[j][k]);
		// }
		// }

		return tabPos;
	}

}
