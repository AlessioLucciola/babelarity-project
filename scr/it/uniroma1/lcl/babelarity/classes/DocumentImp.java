package it.uniroma1.lcl.babelarity.classes;

import it.uniroma1.lcl.babelarity.Document;

/**
 * Classe che implementa l'interfaccia Document e si occupa della istanziazione dei documenti.
 * Ogni documento � composto da un titolo, un id e il contenuto del documento.
 * @author aless
 *
 */
public class DocumentImp implements Document
{
	/**
	 * ID del documento.
	 */
	private String id;
	
	/**
	 * Titolo del documento.
	 */
	private String title;
	
	/**
	 * Contenuto del documento.
	 */
	private String content;
	
	/**
	 * Ritorna l'id del documento.
	 * @return String id.
	 */
	public String getId() {return id;}
	
	/**
	 * Ritorna il titolo del documento.
	 * @return String title.
	 */
	public String getTitle() {return title;}
	
	/**
	 * Ritorna il contenuto del documento.
	 * @return String content.
	 */
	public String getContent() {return content;}
	
	/**
	 * Costruttore del documento: ogni documento � composto da un id, un titolo e il contenuto.
	 * @param id
	 * @param title
	 * @param content
	 */
	public DocumentImp(String id, String title, String content)
	{
		this.id = id;
		this.title = title;
		this.content = content;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj != null && obj instanceof Document && id.equals(((Document)obj).getId())
						   && title.equals(((Document)obj).getTitle())
						   && content.equals(((Document)obj).getContent());
	}
	
	@Override
	public int hashCode()
	{
		return Integer.parseInt(id)+title.length()+content.length();
	}

}
