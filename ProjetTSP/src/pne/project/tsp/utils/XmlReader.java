package pne.project.tsp.utils;

import java.io.File;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Content.CType;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.managers.GraphManager;

public class XmlReader {

	/**
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
		
		String[] sGraphSize = rootElement.getChild("description").getValue().split(" ");
		int iGraphSize = Integer.parseInt(sGraphSize[0]);
		double[][] io_tabInt = new double[iGraphSize][iGraphSize];
		
		return fillGraphWithParam(i_graphElement,io_tabInt, iGraphSize);		

	}

	/**
	 * 
	 * @param i_graphElement
	 * @param io_tabInt
	 * @param iGraphSize
	 * @return Graph with tableAdja
	 */
	private static Graph fillGraphWithParam(List<Element> i_graphElement, double[][] io_tabInt, int iGraphSize) {
		int i=0;
		/* Vertex */
		for (Element elemGraph : i_graphElement) {
			List<Content> listVertex = elemGraph.getContent();

			/* Vertex */
			for (Content vertex : listVertex) {
				if (vertex.getCType().compareTo(CType.Element)==0){
					Element Vertex = (Element) vertex;
					
					/* Edge */
					for (Content edge : Vertex.getContent()) {
						if (edge.getCType().compareTo(CType.Element)==0){
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
							System.out.println("Cout du noeud "+(i)+" au noeud : "+node+",  "+costToLink+".");
						}
					}	
					i++;
				}
			}
		}
		Graph result = new Graph(io_tabInt,iGraphSize);
		return result ;		
	}

	public static void main(String[] args) {
		Graph g1 = buildGraphFromXml("br17.xml");
		GraphManager.writeLinearProgram(g1,"D:/results.txt");
	}
}
