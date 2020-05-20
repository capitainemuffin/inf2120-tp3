import java.util.ArrayList;
import java.util.Collection;

/**
 * Représente la liste de tous les <code>Mot</code> du document.
 */
public class Mots extends ArrayList<Mot> {

    /**
     * Constructeur pour la liste de <code>Mot</code>
     *
     * @param liste le document dont on veut ajouter les mots
     */
    public Mots(ListeDocument liste) {

        liste.stream()
                .flatMap(Collection::stream)
                .forEach(histoire -> {
                    histoire.stream().flatMap(Collection::stream)
                            .flatMap(Collection::stream)
                            .forEach(mot -> this.ajouterMot(mot, histoire));
                });

    }

    /**
     * Ajoute le mot à la liste.
     * Utilise la fouille binaire pour trouver si le mot existe déjà dans la liste.
     * Si le mot existe : le mets à jour, Sinon : ajoute le mot dans la liste
     *
     * @param mot le mot à ajouter
     */
    private void ajouterMot(String mot, Histoire histoire) {

        mot = mot.toLowerCase();
        int debut = 0;
        int fin = this.size() - 1;
        boolean cleTrouve = false;

        while (debut <= fin) {

            int milieu = (fin + debut) / 2;
            String valeurMilieu = this.get(milieu).getMot();
            int resultat = valeurMilieu.compareTo(mot);

            if (resultat < 0) {

                debut = milieu + 1;

            } else if (resultat > 0) {

                fin = milieu - 1;

            } else {

                // Clé trouvée
                this.get(milieu).ajouterHistoire(histoire);
                cleTrouve = true;
                debut = fin + 1;

            }

        }

        //Si élément non trouvé: ajouter l'élement dans la liste.
        if (!cleTrouve) {
            this.add(debut, new Mot(mot, histoire));

        }

    }

    /**
     * Ajoute le inverseDocumentFrequency pour tous les <code>Mot</code> de la liste
     */
    public void mettreAJourIDF() {
        this.forEach(Mot::calculerIDF);
    }

}
