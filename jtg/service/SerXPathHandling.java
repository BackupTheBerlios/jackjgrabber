package service;



import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author QSE2419
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SerXPathHandling {
	
	public static void insertElementAt(Element parentElement, Element newElement, int index)  {
	//setzt das Element an der angegebenen Stelle (-1) ein
	  List list = parentElement.content();
	  list.add(index-1, newElement);
	}
	
	public static void insertElement(Element parentElement, Element newElement)  {
	//setzt das Element ein
	  List list = parentElement.content();
	  list.add(newElement);
	}
		
	public static Node getFirstDescendentNode(Node node) {
		return node.selectSingleNode("descendant::node()[1]");
	}
		
	public static Node getLastDescendentNode(Node node) {
		return node.selectSingleNode("descendant::node()[last]");
	}
		
	public static Node getFirstFollowingNode(Node node) {
		return node.selectSingleNode("following-sibling::node()[1]/*");
	}
		
	public static Node getFirstPrecedinggNode(Node node) {
		return node.selectSingleNode("preceding-sibling::node()[1]/*");
	}
	
	public static List getDescendentNodes(Node node) {
		return node.selectNodes("descendant::*");
	}
	
	public static List getNodes(String xPathExpreassion, Document document) {
		return document.selectNodes(xPathExpreassion);
	}
}
