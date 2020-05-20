import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ListeDocument extends ArrayList< Magasine > {
	private static final long serialVersionUID = 1L;

	
	private static final String XPATH_EXTRACTION_DOCUMENTS = Texte.XPATH_RACINE + Texte.XML_ELEMENT_LISTE + Texte.XPATH_PAS + Texte.XML_ELEMENT_DOCUMENT;

	
	public ListeDocument( String nomFichier ) {
		super();
		
		LecteurFichierXml lecteurXml = new LecteurFichierXml();
		Document listeFichierXml = lecteurXml.lireDocument( nomFichier );
		
		EvaluateurRequeteXPath xpath = new EvaluateurRequeteXPath();
		
		NodeList fichiersXmlNode = (NodeList) xpath.elementListeNoeud( listeFichierXml, XPATH_EXTRACTION_DOCUMENTS );
		
		for( int i = 0; i < fichiersXmlNode.getLength(); ++ i ) {
			add( new Magasine( xpath.attributTexte( fichiersXmlNode.item( i ), Texte.XML_ATTRIBUTE_FICHIER ) ) );
		}			
	}
}
