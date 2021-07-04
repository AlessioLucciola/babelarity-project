package it.uniroma1.lcl.babelarity.similarity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import it.uniroma1.lcl.babelarity.MiniBabelNet;
import it.uniroma1.lcl.babelarity.Word;
import it.uniroma1.lcl.babelarity.classes.SynsetImp;

/**
 * Classe che permette di calcolare il grado di similarità tra due Word.
 * @author alessio
 *
 */
public class BabelLexicalSimilarity
{
	private static MiniBabelNet miniBabelNet = MiniBabelNet.getInstance();
	
	/**
	 * Metodo che dati in input due word, restituisce
	 * un double che equivale al loro grado di similarità.
	 * @param LinguisticObject o1 
	 * @param LinguisticObject o2
	 * @return double
	 */
	public static double computeSimilarity(Word w1, Word w2)
	{
		Map<String, Integer> mapW1 = wordsCounter(getGlosses(w1));
		Map<String, Integer> mapW2 = wordsCounter(getGlosses(w2));
		Integer defLenghts = getDefinitionLenghts(mapW1)+getDefinitionLenghts(mapW2);
		double commonWords = getCommonWordNumber(mapW1, mapW2)*2;
		double result = commonWords/(double)defLenghts;
		return result;
	}
	
	/**
	 * Metodo che data in input una Word w, ricava tutte le definizioni dei synset in cui appare la word.
	 * @param Word w
	 * @return String[] glosses
	 */
	private static String getGlosses(Word w)
	{
		String allGlosses = miniBabelNet.getSynsets(w.toString()).stream().map(s -> ((SynsetImp)s).getGlosses()).collect(Collectors.joining("  "));
		return allGlosses;
	}

	/**
	 * Metodo che dato in input l'insieme delle definizioni sotto forma di stringa,
	 * crea una mappa in cui le chiavi sono le parole, e i valori sono le volte in cui
	 * la parola appare nella stringa.
	 * @param w
	 * @return
	 */
	private static Map<String, Integer> wordsCounter(String glosses)
	{
		String[] allWords = glosses.split(" ");
		Map<String, Integer> def = new HashMap<String, Integer> ();
		
		for (String s : allWords)
		{
			s = miniBabelNet.lemmaFinder(s);
			if (s != null)
			{
				if (def.containsKey(s)) def.put(s, def.get(s)+1);
				else def.put(s, 1);
			}
		}
		
		return def;
	}
	
	/**
	 * Metodo che data in input una mappa contenente come chiavi le parole
	 * di un glossario e come valori il numero di volte in cui le parole appaiono
	 * nel testo, conta il numero totale delle parole nelle definizioni.
	 * @param map
	 * @return
	 */
	private static Integer getDefinitionLenghts(Map<String, Integer> map)
	{
		int sum = map.values().stream().mapToInt(Integer::intValue).sum();
		return sum;
	}
	
	/**
	 * Metodo che date in input due mappe contenenti come chiavi le parole
	 * di un glossario e come valori il numero di volte in cui le parole appaiono
	 * nel testo, conta il numero totale delle parole in comune tra le due mappe.
	 * @param map
	 * @return
	 */
	private static int getCommonWordNumber(Map<String, Integer> map1, Map<String, Integer> map2)
	{
		int commonWords = 0;
		
		Set<String> keySetMap1 = map1.keySet();
		keySetMap1.retainAll(map2.keySet());
		
		for (String s : keySetMap1)
		{
			int valueDef1 = map1.get(s);
			int valueDef2 = map2.get(s);
				
			int minValue = Math.min(valueDef1, valueDef2);
			commonWords += minValue;
		}
		return commonWords;
	}
}
