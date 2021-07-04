package it.uniroma1.lcl.babelarity.classes;

import it.uniroma1.lcl.babelarity.Synset;

/**
 *  Un synset � un insieme di parole con la stessa parte del discorso (part-of-speech o POS) 
 *  che possono essere usate e sostituite in un certo contesto. 
 *  Ogni synset ha un suo ID, relazioni con altri synset, una sua POS, la definizione del synset 
 *  e una lista di lemmi.
 * 	@author alessio
 *
 */
public class SynsetImp implements Synset
{
	/**
	 * ID del synset.
	 */
	private String id;
	
	/**
	 * Lemmi del synset.
	 */
	private String glosses;
	
	/**
	 * Definizioni del synset.
	 */
	private String definitions;
	
	/**
	 * POS (Part-of-speech) del synset.
	 */
	private SynsetPos POS;
	
	/**
	 * Costruttore del synset: Ogni synset � costruito con un ID, i suoi lemmi, il Part-of-speech
	 * e le definizioni.
	 * @param id
	 * @param glosses
	 * @param POS
	 * @param definitions
	 */
	public SynsetImp(String id, String glosses, SynsetPos POS, String definitions)
	{
		this.id = id;
		this.glosses = glosses;
		this.definitions = definitions;
		this.POS = POS;
	}
	
	/**
	 * Ritorna l'id del synset.
	 * @return String ID.
	 */
	public String getID() {return id;}
	
	/**
	 * Ritorna l'insieme dei lemmi.
	 * @return Set<String> lemmas;
	 */
	public String getDefinitions() {return definitions;}
	
	/**
	 * Ritorna la lista delle definizioni del synset.
	 * @return List<String> def.
	 */
	public String getGlosses() {return glosses;}
	
	/**
	 * Ritorna il Part-of-speech del synset.
	 * @return String Pos.
	 */
	public SynsetPos getPOS() {return POS;}
	
	@Override
	public int hashCode()
	{
		int idCode = Integer.parseInt(id.substring(3, id.length()-1));

		switch(POS)
		{
		case N : return idCode+1;
		case V : return idCode+2;
		case A : return idCode+3;
		case R : return idCode+4;
		default : return idCode;
		}
		
	}
}
