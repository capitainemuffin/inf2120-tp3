import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.w3c.dom.CharacterData;

public class Paragraphe extends ArrayList< Phrase > {
	private static final long serialVersionUID = 1L;

	private static Pattern PATTRON_DESCRIPTION_FIN_PHRASE = Pattern.compile( Texte.REGEX_DESCRIPTION_FIN_PHRASE );

	
	
	protected String texte;
	
	
	
	public Paragraphe( CharacterData parXmlCharacterData ) {
		super( PATTRON_DESCRIPTION_FIN_PHRASE.splitAsStream( parXmlCharacterData.getData() )
				                             .map( Phrase::new )
				                             .filter( x -> ! x.isEmpty() )
				                             .collect( Collectors.toList() ) );
	}

	
	
	public String getTexte() {
		return texte;
	}

	public void setTexte(String _texte) {
		this.texte = _texte;
	}

}
