package it.uniroma1.lcl.babelarity.strategypattern;

import it.uniroma1.lcl.babelarity.LinguisticObject;
import it.uniroma1.lcl.babelarity.Synset;
import it.uniroma1.lcl.babelarity.similarity.BabelSemanticSimilarity;

//Strategy Pattern

public class SemanticSimilarity implements Similarity
{
	/**
	 * Metodo che dati in input due oggetti linguistici, restituisce
	 * un double che equivale al loro grado di similarit�.
	 * @param LinguisticObject o1 
	 * @param LinguisticObject o2
	 * @return double
	 */
	@Override
	public double computeSimilarity(LinguisticObject o1, LinguisticObject o2)
	{
		return BabelSemanticSimilarity.computeSimilarity(((Synset)o1), ((Synset)o2));
	}

}
