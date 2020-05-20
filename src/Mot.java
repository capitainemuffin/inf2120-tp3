import java.util.ArrayList;

/**
 * Représente un mot, chaque mot possède comme attribut une liste des histoires dans lesquels il apparait ainsi que son occurence
 * dans chacunes de ces histoires. Il possède aussi comme attribut son inverseDocumentFrequency.
 **/
public class Mot {

    /**
     * Représente une Pair contenant l'histoire et l'occurence du <code>Mot</code> dans cette histoire.
     */
    private class Pair {

        private Histoire histoire;
        private int occurence;

        /**
         * Constructeur d'une Pair, place l'occurence à 1.
         *
         * @param histoire l'histoire
         */
        public Pair(Histoire histoire) {

            this.histoire = histoire;
            this.occurence = 1;

        }

        /**
         * Retourne l'histoire de la Pair
         *
         * @return Histoire l'histoire
         */
        public Histoire getHistoire() {
            return histoire;
        }

        /**
         * Retourne l'occurence de la Pair
         *
         * @return int occurence
         */
        public int getOccurence() {
            return occurence;
        }

        /**
         * Incrémente l'attribut occurence de la Pair de 1.
         */
        public void incrementer() {
            this.occurence++;
        }

    }

    private String mot;
    private Double inverseDocumentFrequency;
    private ArrayList<Pair> histoiresEtOccurences;

    /**
     * Constructeur d'un Mot.
     *
     * @param mot      le String du mot
     * @param histoire l'histoire dans laquelle ce mot apparait.
     */
    Mot(String mot, Histoire histoire) {

        this.mot = mot;
        this.histoiresEtOccurences = new ArrayList<>();
        ajouterHistoire(histoire);

    }

    /**
     * Retourne un String du mot
     *
     * @return String qui représente le mot
     */
    public String getMot() {

        return this.mot;

    }

    /**
     * Ajoute une <code>Pair</code> représentant l'histoire et l'occurence de ce <code>Mot</code> dans l'histoire
     * dans l'ArrayList <code>histoiresEtOccurences</code>.
     * <p>
     * Utilise la recherche binaire pour voir si la <code>Pair</code> de cette histoire éxiste déjà dans la liste.
     * Si elle existe : incrémente l'occurence de cette pair de 1. Sinon : créer la Pair et l'ajouter dans la liste.
     * <p>
     *
     * @param histoire l'instance d'histoire qu'on cherche à placer dans la liste <code>histoiresEtOccurences</code>
     */
    public void ajouterHistoire(Histoire histoire) {

        int debut = 0;
        int fin = this.histoiresEtOccurences.size() - 1;
        boolean cleTrouve = false;

        if (fin != -1) {

            while (debut <= fin) {

                int milieu = (fin + debut) / 2;
                Pair valeurMilieu = this.histoiresEtOccurences.get(milieu);
                int resultat = valeurMilieu.getHistoire().compareTo(histoire);

                if (resultat > 0) {

                    fin = milieu - 1;

                } else if (resultat < 0) {

                    debut = milieu + 1;

                } else {
                    //valeur trouvée
                    cleTrouve = true;
                    this.histoiresEtOccurences.get(milieu).incrementer();
                    debut = fin + 1;

                }

            }

        }

        if (!cleTrouve) {

            this.histoiresEtOccurences.add(debut, new Pair(histoire));

        }

    }

    /**
     * Mets à jour le <code>inverseDocumentFrequency</code> du <code>Mot</code>.
     */
    public void calculerIDF() {

        double x = Math.log(74.0 / this.quantiteHistoireAvecCemot());
        this.inverseDocumentFrequency = x / Math.log(2.0);

    }

    /**
     * Retourne le nombre d'histoires dans lequel se retrouve le <code>Mot</code>
     *
     * @return int qui représente le nombre d'histoires
     */
    private int quantiteHistoireAvecCemot() {

        return this.histoiresEtOccurences.size();

    }

    /**
     * Test si ce <code>Mot</code> est dans l'<code>Histoire</code> passée en paramètre
     * Utilise recherche binaire pour retrouver rapidement la Pair dans la liste
     *
     * @param histoire l'histoire qu'on veut vérifier
     * @return true si le mot est dans l'histoire, false sinon
     */
    public boolean estDansHistoire(Histoire histoire) {

        boolean estDansHistoire = false;
        int debut = 0;
        int fin = this.histoiresEtOccurences.size() - 1;

        while (debut <= fin) {

            int milieu = (fin + debut) / 2;
            Pair valeurMilieu = this.histoiresEtOccurences.get(milieu);
            int resultat = valeurMilieu.getHistoire().compareTo(histoire);

            if (resultat > 0) {

                fin = milieu - 1;

            } else if (resultat < 0) {

                debut = milieu + 1;

            } else {

                //valeur trouvée
                estDansHistoire = true;
                debut = fin + 1;

            }

        }

        return estDansHistoire;

    }

    /**
     * Test si ce mot est dans la phrase qu'on passe en paramètre
     *
     * @param phrase la phrase qu'on veut vérifier
     * @return true si le mot est dans la phrase, false sinon
     */
    public boolean estDansPhrase(Phrase phrase) {

        boolean estDedans = false;

        for (String mot : phrase) {

            mot = mot.toLowerCase();

            if (mot.equals(this.getMot())) {

                estDedans = true;

            }

        }

        return estDedans;

    }

    /**
     * Retourne le nombre d'occurence de ce mot pour une histoire donnée
     *
     * @param histoire l'instance d'histoire pour laquelle on veut savoir l'occurence du mot.
     * @return int représentant l'occurence.
     */
    private int getOccurencePourCetteHistoire(Histoire histoire) {

        int debut = 0;
        int fin = this.histoiresEtOccurences.size() - 1;
        int occurence = -1;

        while (debut <= fin) {

            int milieu = (fin + debut) / 2;
            Pair valeurMilieu = this.histoiresEtOccurences.get(milieu);
            int resultat = valeurMilieu.getHistoire().compareTo(histoire);

            if (resultat > 0) {

                fin = milieu - 1;

            } else if (resultat < 0) {

                debut = milieu + 1;

            } else {

                //valeur trouvée
                occurence = this.histoiresEtOccurences.get(milieu).getOccurence();
                debut = fin + 1;

            }

        }

        if (occurence == -1) {

            throw new RuntimeException("L'histoire est introuvable");

        }

        return occurence;

    }

    /**
     * Retourne la valeur de ce mot pour une histoire donnée, qui est le résultat de son IDF * TF(histoire)
     *
     * @param histoire l'histoire
     * @return un double qui représente le score du <code>Mot</code>
     */
    public double valeurTotale(Histoire histoire) {

        return this.getOccurencePourCetteHistoire(histoire) * this.inverseDocumentFrequency;

    }

    /**
     * Compare 2 mots d'une même histoire entre eux, en se basant sur leur valeure Totale (IDF * TF(histoire)
     *
     * @param histoire l'histoire dont on veut comparer 2 mots
     * @param mot      le mot avec lequel on veut comparer l'instance
     * @return 1 si l'instance à une plus grande valeur, -1 si le mot passé en paramètre à une plus grande valeur,
     * 0 si les 2 mots ont le même score.
     */
    public int compareToDansMemeHistoire(Histoire histoire, Mot mot) {

        int valeur = 0;

        if (this.valeurTotale(histoire) > mot.valeurTotale(histoire)) {

            valeur = 1;

        } else if (this.valeurTotale(histoire) < mot.valeurTotale(histoire)) {

            valeur = -1;

        }

        return valeur;
    }

    /**
     * @return le String du Mot avec la première lettre en Majuscule
     */
    public String toString() {

        return this.mot.substring(0, 1).toUpperCase() + this.mot.substring(1);

    }

}
