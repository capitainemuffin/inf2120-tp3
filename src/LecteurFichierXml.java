import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class LecteurFichierXml {
	protected DocumentBuilder builder;
	
	public LecteurFichierXml() {
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch( ParserConfigurationException e ) {
			Erreur.DOCUMENT_BUILDER.lancer();
		}
	}
	
	public Document lireDocument( String nomFichier ) {
		Document resultat = null;
		
		try {
			resultat = builder.parse( new File( nomFichier ) );
		} catch( SAXException e ) {
			Erreur.XML_INCORRECT.lancer();
		} catch( IOException e ) {
			Erreur.OUVERTURE_FICHIER.lancer();
		}
		
		return resultat;
	}
}
