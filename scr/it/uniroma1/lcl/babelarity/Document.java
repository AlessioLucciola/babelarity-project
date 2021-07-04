package it.uniroma1.lcl.babelarity;

/**
 * Interfaccia che definisce un Documento.
 * Ogni documento � composto da un titolo, un id e il contenuto del documento.
 * @author alessio
 *
 */
public interface Document extends LinguisticObject
{	
	/**
	 * Ritorna l'id del documento.
	 * @return String id.
	 */
	String getId();
	
	/**
	 * Ritorna il titolo del documento.
	 * @return String title.
	 */
	
	String getTitle();
	/**
	 * Ritorna il contenuto del documento.
	 * @return String content.
	 */
	String getContent();
}
