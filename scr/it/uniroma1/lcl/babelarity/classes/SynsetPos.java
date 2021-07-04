package it.uniroma1.lcl.babelarity.classes;

/**
 * Enumerazione che gestisce il POS (Part of speech) di un synset.
 * Il pos pu� essere un nome (noun), avverbio (adverb), aggettivo (adjective) o un verbo (verb).
 * Il Pos corrisponde all'ultima lettera dell'id di un synset e pu� essere "N", "V", "A" O "V".
 * @author alessio
 *
 */
public enum SynsetPos 
{
	//Ogni costante dell'enumerazione ha un suo valore.
	N("NOUN"), V("ADV"), A("ADJ"), R("VERB");
	
	/**
	 * Campo riservato al valore della costante.
	 */
	private final String value;
	
	/**
	 * Costruttore privato dell'enumeratore.
	 * @param value
	 */
	private SynsetPos(String value)
	{
		this.value = value;
	}
	
	/**
	 * Metodo che ritorna il valore della costante.
	 * @return String
	 */
	public String getValue()
	{
	    return value;
	}

	@Override
	public String toString()
	{
	    return getValue();
	}
}
