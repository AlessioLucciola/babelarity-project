package it.uniroma1.lcl.babelarity;

import it.uniroma1.lcl.babelarity.classes.WordImp;

/**
 * Interfaccia Word con un solo metodo statico fromString(String s).
 * @author alessio
 *
 */
public interface Word extends LinguisticObject
{
	/**
	 * Metodo statico che data in input una stringa s, restituisce un nuova
	 * istanza di Word.
	 * @param s
	 * @return
	 */
	static Word fromString(String s)
	{
		return new WordImp(s);
	}
}
