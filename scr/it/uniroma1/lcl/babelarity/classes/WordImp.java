package it.uniroma1.lcl.babelarity.classes;

import it.uniroma1.lcl.babelarity.Word;

/**
 * Classe che implementa l'interfaccia Word.
 * Questa classe si occupa della creazione dell'oggetto linguistico Word.
 * @author aless
 *
 */
public class WordImp implements Word
{
	/**
	 * Stringa che definisce il significato dell'oggetto.
	 */
	private String word;
	
	/**
	 * Costruttore della classe Word che prende in input una stringa.
	 * @param word
	 */
	public WordImp(String word)
	{
		this.word = word;
	}
	
	/**
	 * Ritorna il significato dell'oggetto Word.
	 * @return String word.
	 */
	@Override
	public String toString()
	{
		return word;
	}
}
