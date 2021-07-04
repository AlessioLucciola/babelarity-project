package it.uniroma1.lcl.babelarity.strategypattern;

import it.uniroma1.lcl.babelarity.LinguisticObject;

//STRATEGY PATTERN

public interface Similarity
{
	/**
	 * Metodo che dati in input due oggetti linguistici, restituisce
	 * un double che equivale al loro grado di similarit�.
	 * @param LinguisticObject o1 
	 * @param LinguisticObject o2
	 * @return double
	 */
	double computeSimilarity(LinguisticObject o1, LinguisticObject o2);
}
