
public class Texte {
	public static final String NOM_FICHIER_LISTE_MAGASINE = "listeDocument.xml";
	
	
	public static final String MSSG_ERREUR = "Erreur : ";
	public static final String MSSG_ERREUR_DOCUMENT_BUILDER = "Le DocumentBuilder ne peut etre construit avec les conditions demandees.";
	public static final String MSSG_ERREUR_OUVERTURE_FICHIER = "Le fichier ne peut etre ouvert.";
	public static final String MSSG_ERREUR_XML_INCORRECT = "Erreur dans le XML du fichier lu.";
	public static final String MSSG_ERREUR_XPATH_INCORRECT = "L'expression XPath ne peu pas etre evaluee.";
	
	
	public static final String REGEX_DESCRIPTION_FIN_MOT = "[^-\\p{Alpha}]+";
	public static final String REGEX_DESCRIPTION_FIN_PHRASE = "[\\.\\?\\!\"]";
	
	
	public static final String XML_ATTRIBUTE_ANNEE = "annee";
	public static final String XML_ATTRIBUTE_AUTEUR = "auteur";
	public static final String XML_ATTRIBUTE_EDITEUR = "editeur";
	public static final String XML_ATTRIBUTE_EDITEUR_CONSULTANT = "editeur_consultant";
	public static final String XML_ATTRIBUTE_FICHIER = "fichier";
	public static final String XML_ATTRIBUTE_MOIS = "mois";
	public static final String XML_ATTRIBUTE_NO = "no";
	public static final String XML_ATTRIBUTE_PAGE = "page";
	public static final String XML_ATTRIBUTE_PARTIE = "partie";
	public static final String XML_ATTRIBUTE_PROPRIETAIRE = "proprietaire";
	public static final String XML_ATTRIBUTE_TITRE = "titre";
	public static final String XML_ATTRIBUTE_VOL = "vol";

	public static final String XML_ELEMENT_CHAPITRE = "chapitre";
	public static final String XML_ELEMENT_CONTENUS = "contenus";
	public static final String XML_ELEMENT_DOCUMENT = "document";
	public static final String XML_ELEMENT_HISTOIRE = "histoire";
	public static final String XML_ELEMENT_LICENSE = "license";
	public static final String XML_ELEMENT_LISTE = "liste";
	public static final String XML_ELEMENT_MAGASINE = "magasine";
	public static final String XML_ELEMENT_SEPARATION = "separation";
	
	public static final String XPATH_ATTRIBUT = "@";
	public static final String XPATH_AXE = "::";
	public static final String XPATH_DESCENDANT = "descendant" + XPATH_AXE;
	public static final String XPATH_NOEUD_CONTEXT = ".";
	public static final String XPATH_PAS = "/";
	public static final String XPATH_RACINE = "/";
	public static final String XPATH_TEXT = "text()";

}
