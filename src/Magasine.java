import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Magasine extends ArrayList< Histoire > {
	private static final long serialVersionUID = 1L;

	
	private static final String XPATH_TROUVER_MAGASINE = Texte.XPATH_RACINE + Texte.XML_ELEMENT_MAGASINE;
	private static final String XPATH_TROUVER_HISTOIRES = Texte.XPATH_NOEUD_CONTEXT + Texte.XPATH_PAS + Texte.XML_ELEMENT_CONTENUS + Texte.XPATH_PAS + Texte.XML_ELEMENT_HISTOIRE;
	
	
	protected int annee;
	protected int no;
	protected String editeur;
	protected String editeurConsultant;
	protected String license;
	protected String proprietaire;
	protected String titre;
	protected Mois mois;
	protected Volume vol;
	
	
	public Magasine( String nomFichier ) {
		super();

		LecteurFichierXml lecteurXml = new LecteurFichierXml();
		Document documentXml = lecteurXml.lireDocument( nomFichier );
		
		EvaluateurRequeteXPath xpath = new EvaluateurRequeteXPath();
		
		Node magasineXmlNode = xpath.elementNoeud( documentXml, XPATH_TROUVER_MAGASINE );
		
		annee = xpath.attributEntier( magasineXmlNode, Texte.XML_ATTRIBUTE_ANNEE );
		no    = xpath.attributEntier( magasineXmlNode, Texte.XML_ATTRIBUTE_NO );
		
		editeur           = xpath.attributTexte( magasineXmlNode, Texte.XML_ATTRIBUTE_EDITEUR );
		editeurConsultant = xpath.attributTexte( magasineXmlNode, Texte.XML_ATTRIBUTE_EDITEUR_CONSULTANT );
		license           = xpath.attributTexte( magasineXmlNode, Texte.XML_ELEMENT_LICENSE );
		proprietaire      = xpath.attributTexte( magasineXmlNode, Texte.XML_ATTRIBUTE_PROPRIETAIRE );
		titre             = xpath.attributTexte( magasineXmlNode, Texte.XML_ATTRIBUTE_TITRE );
		
		mois = xpath.attributEnum( Mois.class, magasineXmlNode, Texte.XML_ATTRIBUTE_MOIS );
		vol  = xpath.attributEnum( Volume.class, magasineXmlNode, Texte.XML_ATTRIBUTE_VOL);
		
		NodeList histoiresXmlNodes = xpath.elementListeNoeud( magasineXmlNode, XPATH_TROUVER_HISTOIRES );

		for( int i = 0; i < histoiresXmlNodes.getLength(); ++ i ) {
			Node histoireXmlNode = histoiresXmlNodes.item( i );
			
			if( Node.ELEMENT_NODE == histoireXmlNode.getNodeType() ) {
				add( new Histoire( histoireXmlNode ) );
			}
		}			
	}

	
	public int getAnnee() {
		return annee;
	}

	public void setAnnee( int annee ) {
		this.annee = annee;
	}

	public int getNo() {
		return no;
	}

	public void setNo( int no ) {
		this.no = no;
	}

	public String getEditeur() {
		return editeur;
	}

	public void setEditeur( String editeur ) {
		this.editeur = editeur;
	}

	public String getEditeurConsultant() {
		return editeurConsultant;
	}

	public void setEditeurConsultant( String editeurConsultant ) {
		this.editeurConsultant = editeurConsultant;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense( String license ) {
		this.license = license;
	}

	public String getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire( String proprietaire ) {
		this.proprietaire = proprietaire;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre( String titre ) {
		this.titre = titre;
	}

	public Mois getMois() {
		return mois;
	}

	public void setMois( Mois mois ) {
		this.mois = mois;
	}

	public Volume getVol() {
		return vol;
	}

	public void setVol( Volume vol ) {
		this.vol = vol;
	}
}
