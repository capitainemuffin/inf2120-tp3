import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EvaluateurRequeteXPath {
	protected XPath xpath;
	
	public EvaluateurRequeteXPath() {
		xpath =  XPathFactory.newInstance().newXPath();
	}
	
	public Integer attributEntier( Node xmlNode, String nomAttribut ) {
		Integer resultat = 0;
		
		Node temp = elementNoeud( xmlNode, Texte.XPATH_ATTRIBUT + nomAttribut );
		if( null != temp ) {
			resultat = Integer.parseInt( temp.getNodeValue() );
		}
		
		return resultat;
	}
	
	public String attributTexte( Node xmlNode, String nomAttribut ) {
		String resultat = "";
		
		Node temp = elementNoeud( xmlNode, Texte.XPATH_ATTRIBUT + nomAttribut );
		if( null != temp ) {
			resultat = temp.getNodeValue();
		}

		return resultat;
	}

	public <T extends Enum< T > > T attributEnum( Class< T > typeEnum, Node xmlNode, String nomAttribut ) {
		T resultat = null;
		
		Node temp = elementNoeud( xmlNode, Texte.XPATH_ATTRIBUT + nomAttribut );
		if( null != temp ) {
			resultat = Enum.valueOf( typeEnum, temp.getNodeValue() );
		}

		return resultat;
	}
	
	public Node elementNoeud( Node xmlNode, String chemin ) {
		Node resultat = null;
		
		try {
			resultat = (Node) xpath.evaluate( chemin, xmlNode, XPathConstants.NODE );
		} catch( XPathExpressionException e ) {
			Erreur.XPATH_INCORRECT.lancer();
		}
		
		return resultat;
	}
	
	public NodeList elementListeNoeud( Node xmlNode, String chemin ) {
		NodeList resultat = null;
		
		try {
			resultat = ((NodeList) xpath.evaluate( chemin, xmlNode, XPathConstants.NODESET ));
		} catch( XPathExpressionException e ) {
			Erreur.XPATH_INCORRECT.lancer();
		}
		
		return resultat;
	}
}
