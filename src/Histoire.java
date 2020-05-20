import java.util.ArrayList;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Histoire extends ArrayList<Paragraphe> implements Comparable<Histoire> {
    private static final long serialVersionUID = 1L;


    private static final String XPATH_TROUVER_TEXTES = Texte.XPATH_DESCENDANT + Texte.XPATH_TEXT;


    protected int page;
    protected int partie;
    protected String auteur;
    protected String titre;


    public Histoire(Node histoireXmlNode) {
        EvaluateurRequeteXPath xpath = new EvaluateurRequeteXPath();

        page = xpath.attributEntier(histoireXmlNode, Texte.XML_ATTRIBUTE_PAGE);
        partie = xpath.attributEntier(histoireXmlNode, Texte.XML_ATTRIBUTE_PARTIE);

        auteur = xpath.attributTexte(histoireXmlNode, Texte.XML_ATTRIBUTE_AUTEUR);
        titre = xpath.attributTexte(histoireXmlNode, Texte.XML_ATTRIBUTE_TITRE);

        NodeList paragraphesXmlNode = xpath.elementListeNoeud(histoireXmlNode, XPATH_TROUVER_TEXTES);

        for (int i = 0; i < paragraphesXmlNode.getLength(); ++i) {
            Node paragrapheXml = paragraphesXmlNode.item(i);

            add(new Paragraphe((CharacterData) paragrapheXml));

            if (!isEmpty() && get(size() - 1).isEmpty()) {
                remove(size() - 1);
            }
        }
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPartie() {
        return partie;
    }

    public void setPartie(int partie) {
        this.partie = partie;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Compare tous les attributs de 2 histoires, pour pouvoir les trier dans une liste
     *
     * @param histoire l'histoire qu'on veut comparer avec l'instance en cours
     * @return 1 si l'instance doit être classée après, -1 si l'instance doit être classée avant, 0 si c'est la même histoire.
     */
    @Override
    public int compareTo(Histoire histoire) {

        int score = 0;

        if (this != histoire) {

            if (this.getTitre().compareTo(histoire.getTitre()) > 0) {

                score = 1;

            } else if (this.getTitre().compareTo(histoire.getTitre()) < 0) {

                score = -1;

            } else if (this.getPartie() > histoire.getPartie()) {

                score = 1;

            } else if (this.getPartie() < histoire.getPartie()) {

                score = -1;

            } else if (this.getAuteur().compareTo(histoire.getAuteur()) > 0) {

                score = 1;

            } else if (this.getAuteur().compareTo(histoire.getAuteur()) < 0) {

                score = -1;

            } else if (this.getPage() > histoire.getPage()) {

                score = 1;

            } else if (this.getPage() < histoire.getPage()) {

                score = -1;

            } else {

                score = 1;

            }

        }

        return score;
    }
}
