package pne.project.tsp.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Content.CType;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import pne.project.tsp.beans.Graph;

public class FileReader {

	/**
	 * read a tsp file and gives nodes positions
	 * 
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

				tabPos = new double[tabSize + 1][2];

				/* get first position */
				if (line.split(" ")[0].equals("NODE_COORD_SECTION")) {
					line = scanner.nextLine();
					while (scanner.hasNextLine()) {
						try {
							/* traitement */
							String[] tabPosition = line.split(" ");

							/* indice */
							int indice = Integer.parseInt(tabPosition[0]);

							/* x */
							double x = Double.parseDouble(tabPosition[1]);
							tabPos[indice][0] = x;
							if (x > bg.getxMax()) {
								bg.setxMax(x);
							} else if (x < bg.getxMin()) {
								bg.setxMin(x);
							}

							/* y */
							double y = Double.parseDouble(tabPosition[2]);
							tabPos[indice][1] = y;
							if (y > bg.getyMax()) {
								bg.setyMax(y);
							} else if (y < bg.getyMin()) {
								bg.setyMin(y);
							}
							line = scanner.nextLine();
						} catch (NumberFormatException e) {
							scanner.close();
							return tabPos;
						}
					}
				}
			}
			scanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return tabPos;
	}

	/**
	 * read a xml file and build the graph
	 * 
	 * @param filePath
	 * @return Graph obtains from xml file in param.
	 */
	public static Graph buildGraphFromXml(String filePath) {
		SAXBuilder sxb = new SAXBuilder();
		Document document = null;
		try {
			document = sxb.build(new File(filePath));

		} catch (Exception e) {
			e.printStackTrace();
		}

		Element rootElement = document.getRootElement();
		List<Element> i_graphElement = rootElement.getChildren("graph");

		/* finding graph size in filePath */
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(filePath);
		m.find();
		int iGraphSize = Integer.parseInt(m.group());

		double[][] io_tabInt = new double[iGraphSize][iGraphSize];

		return fillGraphWithParam(i_graphElement, io_tabInt, iGraphSize);

	}

	/**
	 * 
	 * @param i_graphElement
	 * @param io_tabInt
	 * @param iGraphSize
	 * @return Graph with tableAdja
	 */
	private static Graph fillGraphWithParam(List<Element> i_graphElement, double[][] io_tabInt, int iGraphSize) {
		int i = 0;
		/* Vertex */
		for (Element elemGraph : i_graphElement) {
			List<Content> listVertex = elemGraph.getContent();

			/* Vertex */
			for (Content vertex : listVertex) {
				if (vertex.getCType().compareTo(CType.Element) == 0) {
					Element Vertex = (Element) vertex;

					/* Edge */
					for (Content edge : Vertex.getContent()) {
						if (edge.getCType().compareTo(CType.Element) == 0) {
							Element Edge = (Element) edge;

							Attribute cost = Edge.getAttribute("cost");
							double costToLink = 0;
							try {
								costToLink = cost.getDoubleValue();
							} catch (DataConversionException e) {
								e.printStackTrace();
							}
							int node = Integer.parseInt(Edge.getValue());
							io_tabInt[i][node] = costToLink;
						}
					}
					i++;
				}
			}
		}
		Graph result = new Graph(io_tabInt, iGraphSize);
		return result;
	}

}
