import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Logiciel qui anaylyse des nouvelles de Science-Fiction. Cette analyse va produire les mots clefs liés à une histoire
 * et 2 résumés de l'histoire.
 */
public class Principal {

    /**
     * Retourne l'index de la phrase ayant le meilleur score dans la liste de Phrases en excluant celles
     * déjà présentes dans le résumé.
     *
     * @param phrases           la liste de Phrases
     * @param motsDansLhistoire une liste des <code>Mot</code> contenus dans l'histoire
     * @param histoire          l'histoire
     * @param indexes           les index déjà présents dans le résumé
     * @return l'index de la meilleure phrase dans la liste.
     */
    private static int indexMeilleurePhrase(List<Phrase> phrases, List<Mot> motsDansLhistoire, Histoire histoire, ArrayList<Integer> indexes) {

        int index = 0;
        double scoreMax = 0.0;

        for (int i = 0; i < phrases.size(); i++) {

            double score = phrases.get(i).scorePhrase(motsDansLhistoire, histoire);

            if (score > scoreMax && !indexes.contains(i)) {

                index = i;
                scoreMax = score;

            }
        }

        return index;

    }

    /**
     * Construit un tableau des index des 6 phrases contenants les mots les plus importants d'une histoire.
     * Si les 6 phrases ne sont pas trouvées avec le premier mot le plus important : va completer le tableau avec
     * le mot le plus important suivant...et ainsi de suite jusqu'à ce que 6 phrases soient trouvées.
     *
     * @param phrases           les phrases à analyser
     * @param motsDansLhistoire les mots qui font partie de l'histoire
     * @param histoire          l'histoire qu'on veut résumer
     * @return un tableau d'index
     */
    private static ArrayList<Integer> indexMeilleurMot(List<Phrase> phrases, List<Mot> motsDansLhistoire, Histoire histoire) {

        ArrayList<Integer> indexTrouver = new ArrayList<>();

        while (indexTrouver.size() <= 6) {

            //Trouver le mot le plus important
            Mot leMot = motsDansLhistoire.stream()
                    .sorted((mot1, mot2) -> mot2.compareToDansMemeHistoire(histoire, mot1))
                    .findFirst().get();

            //Commence à 1 car la première phrase est déjà dans le résumé
            //Fini à phrases.size() - 1 car on ne veut pas la dernière phrase de l'histoire dans le résumé
            for (int i = 1; i < phrases.size() - 1 && indexTrouver.size() <= 6; i++) {

                if (leMot.estDansPhrase(phrases.get(i)) && !indexTrouver.contains(i)) {

                    indexTrouver.add(i);

                }

            }

            //Enlever le mot avant de retourner dans la boucle (si 6 phrases pas atteint)
            motsDansLhistoire.remove(leMot);

        }

        return indexTrouver;
    }

    /**
     * Imprime la phrase ayant le meilleur score et les 2 suivantes si possible,
     * Parmis les phrases restantes : Imprime la phrase ayant le meilleur score et la suivante si possible.
     * Parmis les phrases restantes : Imprime la phrase ayant le meilleur score.
     *
     * @param histoire          l'histoire à résumer
     * @param motsDansLhistoire une liste des <code>Mot</code> faisant partie de cette histoire
     * @param writer            pour écrire dans le fichier
     */
    private static void imprimerResumer1(Histoire histoire, List<Mot> motsDansLhistoire, PrintWriter writer) {

        //Un tableau d'index pour savoir qu'elle phrase afficher
        ArrayList<Integer> indexes = new ArrayList<>();

        //Toutes les phrases de l'histoire
        List<Phrase> phrases = histoire.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        //ajoute l'index de la phrase la plus importante de l'histoire dans le tableau d'index
        int index = indexMeilleurePhrase(phrases, motsDansLhistoire, histoire, indexes);
        indexes.add(index);

        //ajoute la phrase suivante si possible
        if (index + 1 < phrases.size()) {

            indexes.add(index + 1);

        }

        //ajoute la phrase suivante si possible
        if (index + 2 < phrases.size()) {

            indexes.add(index + 2);

        }

        //Trouver la meilleure phrase parmis celles pas dans le résumé
        index = indexMeilleurePhrase(phrases, motsDansLhistoire, histoire, indexes);
        indexes.add(index);

        //Affiche la phrase suivante si elle existe et si elle n'est pas déjà dans le résumé
        if (index + 1 < phrases.size() && !indexes.contains(index + 1)) {

            indexes.add(index + 1);

        }

        //Trouver la meilleure phrase de ce qu'il reste dans la liste
        index = indexMeilleurePhrase(phrases, motsDansLhistoire, histoire, indexes);
        indexes.add(index);

        //Tri les index du plus petit au plus grand et les affiches
        writer.println("\nPREMIER RESUME");
        indexes.stream()
                .sorted()
                .forEach(indx -> writer.println(phrases.get(indx).getPhraseOriginale()));

    }

    /**
     * Mon résumé va inclure la première phrase car, en général, elle met bien le lecteur
     * dans l'ambiance de l'histoire. La dernière phrase de l'histoire ne sera pas incluse
     * car elle pourrait révéler la fin de l'intrigue. Il sera inclut les 6 premières phrases
     * contenants le mot clé le plus important de l'histoire, excluant la première phrase car
     * déjà dans le résumé. Si le compte de 6 n'est pas atteint avec le premier mot le plus important,
     * le résumé sera comblé avec les premières phrases qui contiennent le mot le plus important suivant,
     * si celles-ci ne sont pas déjà dans le résumé...le processus est répété jusqu'à ce que le compte de 6 phrases
     * soit atteint. Les phrases seront affichées dans l'ordre d'apparation dans l'histoire.
     * <p>
     * J'ai prévilégié les premières phrases contenants les mots importants plutôt que les phrases les
     * plus importantes afin de donner des informations sur les personnages et le sujet abordé dans l'histoire
     * tout en m'assurant que les phrases clées divulgant l'intrigue ne soit pas affichées, ces phrases clées
     * sont en général vers la fin d'une histoire et contient plusieurs mots importants en même temps.
     *
     * @param histoire          l'histoire qu'on veur résumer
     * @param motsDansLhistoire une liste des <code>Mot</code> faisant partie de cette histoire
     * @param writer            pour écrire dans le document
     */
    public static void imprimerResumer2(Histoire histoire, List<Mot> motsDansLhistoire, PrintWriter writer) {

        //Toutes les phrases de l'histoire
        List<Phrase> phrases = histoire.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());


        //Un tableau d'index avec les phrases contenant les mots importants
        ArrayList<Integer> indexes = indexMeilleurMot(phrases, motsDansLhistoire, histoire);

        //Ajouter la premiere phrase de l'histoire
        indexes.add(0);

        //Écrire le résumé dans le document
        writer.println("\nDEUXIEME RESUME");
        indexes.stream()
                .sorted()
                .forEach(indx -> writer.println(phrases.get(indx).getPhraseOriginale()));

        writer.println("\n");

    }

    /**
     * Exécution
     */
    public static void main(String[] args) {

        ListeDocument liste = new ListeDocument(Texte.NOM_FICHIER_LISTE_MAGASINE);
        Mots mots = new Mots(liste);
        mots.mettreAJourIDF();

        try {

            PrintWriter writer = new PrintWriter("resultat.txt", "UTF-8");

            System.out.println("En traitement...");

            //Pour chaque magasine
            liste.forEach(magasine -> {

                //Afficher le titre, mois et année de parution du magasine
                Object[] infoMagasine = {magasine.getTitre(), magasine.getMois(), magasine.getAnnee()};
                Stream.of(infoMagasine).forEach(info -> writer.print(info + " "));

                //Pour chaque histoire
                magasine.forEach(histoire -> {

                    //Afficher le titre et l'auteur de l'histoire
                    writer.print("\n");
                    String[] infoHistoire = {histoire.getTitre(), histoire.getAuteur()};
                    Stream.of(infoHistoire).forEach(writer::println);

                    //Garder les mots de cette histoire seulement,
                    List<Mot> motsDansLhistoire = mots.stream()
                            .filter(mot -> mot.estDansHistoire(histoire))
                            .collect(Collectors.toList());

                    //Afficher les 5 mots les plus importants
                    motsDansLhistoire.stream()
                            .sorted((mot2, mot1) -> mot1.compareToDansMemeHistoire(histoire, mot2))
                            .limit(5)
                            .forEach(mot -> writer.print(mot + " "));

                    //Afficher le premier résumé
                    imprimerResumer1(histoire, motsDansLhistoire, writer);

                    //Afficher le deuxième résumé
                    imprimerResumer2(histoire, motsDansLhistoire, writer);

                });
            });

            writer.close();
            System.out.println("Terminé !");

        } catch (Exception e) {

            e.printStackTrace();
            System.exit(-1);

        }

    }
}