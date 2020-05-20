
public enum Erreur {
	DOCUMENT_BUILDER     ( -101, Texte.MSSG_ERREUR_DOCUMENT_BUILDER ),
	OUVERTURE_FICHIER    ( -102, Texte.MSSG_ERREUR_OUVERTURE_FICHIER ),
	XML_INCORRECT        ( -103, Texte.MSSG_ERREUR_XML_INCORRECT ),
	XPATH_INCORRECT      ( -104, Texte.MSSG_ERREUR_XPATH_INCORRECT )
	;
	
	private String _mssg;
	private int _no;
	
	private Erreur( int no, String mssg ) {
		_no = no;
		_mssg = mssg;
	}
	
	public void afficher() {
		afficher( "" );
	}
	
	public void afficher( String complement ) {
		System.out.println( Texte.MSSG_ERREUR + _mssg + "  " + complement );		
	}
	
	public void lancer() {
		lancer( "" );
	}
	
	public void lancer( String complement ) {
		System.err.println( Texte.MSSG_ERREUR + _mssg + "  " + complement );
		System.exit( _no );
	}
}
