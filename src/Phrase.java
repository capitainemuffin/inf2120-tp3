import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Phrase extends ArrayList<String> {
    private static final long serialVersionUID = 1L;

    private static Pattern PATTRON_DESCRIPTION_FIN_MOT = Pattern.compile(Texte.REGEX_DESCRIPTION_FIN_MOT);


    protected String phraseOriginale;


    public Phrase(String phraseOriginale) {
        super(PATTRON_DESCRIPTION_FIN_MOT.splitAsStream(phraseOriginale)
                .filter(x -> x != "")
                .collect(Collectors.toList()));

        this.phraseOriginale = phraseOriginale;

    }


    public String getPhraseOriginale() {
        return phraseOriginale + ".";
    }

    public void setPhraseOriginale(String phraseOriginale) {
        this.phraseOriginale = phraseOriginale;
    }

    /**
     * Retourne le score de cette phrase en additionnant les scores de tous ses <code>Mot</code>
     * Utilise la recherche binaire pour trouver l'instance de Mot à utiliser.
     *
     * @param motsDansLhistoire la liste des mots dans l'histoire
     * @param histoire          l'histoire contenant cette phrase
     * @return le score de la phrase
     */
    public double scorePhrase(List<Mot> motsDansLhistoire, Histoire histoire) {
        Double total = 0.0;
        Boolean trouver = false;


        for (String mot : this) {

            mot = mot.toLowerCase();
            int debut = 0;
            int fin = motsDansLhistoire.size() - 1;

            while (debut <= fin) {

                int milieu = (fin + debut) / 2;
                String motDuMilieu = motsDansLhistoire.get(milieu).getMot();
                int resultat = motDuMilieu.compareTo(mot);

                if (resultat < 0) {

                    debut = milieu + 1;

                } else if (resultat > 0) {

                    fin = milieu - 1;

                } else {
                    //valeur trouvée
                    double valeur = motsDansLhistoire.get(milieu).valeurTotale(histoire);
                    total += valeur;
                    fin = debut - 1;
                    trouver = true;
                }

            }

            if (!trouver) {

                throw new RuntimeException("Un mot présent dans la phrase n'a pas été trouvé parmis " +
                        "les instances Mot afin de pouvoir ajouter son score Le mot : " + mot);

            }

        }

        return total;

    }

}
