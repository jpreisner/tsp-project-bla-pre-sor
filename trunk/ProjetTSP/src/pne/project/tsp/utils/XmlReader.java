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

public class XmlReader {

	public static Graph buildGraphFromXml(String path) {
		//FIXME taille dynamique
		double[][] tabInt = new double[50][50];
			
		SAXBuilder sxb = new SAXBuilder();
		Document document = null;
		try {
			document = sxb.build(new File(path));

		} catch (Exception e) {
			e.printStackTrace();
		}

		Element racine = document.getRootElement();
		List<Element> graph = racine.getChildren("graph");
		int i=1;
		/* Vertex */
		for (Element elemGraph : graph) {
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
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							int node = Integer.parseInt(Edge.getValue());
							tabInt[i-1][node] = costToLink;
							System.out.println("Cout du noeud "+i+" au noeud : "+node+",  "+costToLink+".");
						}
					}	
					i++;

				}
			}

		}
		Graph result = new Graph(tabInt);
		return result ;
	}

	public static void main(String[] args) {
		buildGraphFromXml("att48.xml");
	}
}
