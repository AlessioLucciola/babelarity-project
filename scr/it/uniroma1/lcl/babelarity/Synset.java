package it.uniroma1.lcl.babelarity;

import it.uniroma1.lcl.babelarity.classes.SynsetPos;

/**
 *  Un synset � un insieme di parole con la stessa parte del discorso (part-of-speech o POS) 
 *  che possono essere usate e sostituite in un certo contesto. 
 *  Ogni synset ha un suo ID, relazioni con altri synset, una sua POS, la definizione del synset 
 *  e una lista di lemmi.
 * @author alessio
 *
 */
public interface Synset extends LinguisticObject
{
	/**
	 * Ritorna l'id del synset.
	 * @return String ID.
	 */
	public String getID();
	
	/**
	 * Ritorna l'insieme dei lemmi.
	 * @return Set<String> lemmas;
	 */
	public String getDefinitions();
	
	/**
	 * Ritorna la lista delle definizioni del synset.
	 * @return List<String> glosses.
	 */
	public String getGlosses();
	
	/**
	 * Ritorna il Part-of-speech del synset.
	 * @return String Pos.
	 */
	public SynsetPos getPOS();
}
