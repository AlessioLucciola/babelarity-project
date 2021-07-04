package it.uniroma1.lcl.babelarity.similarity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import it.uniroma1.lcl.babelarity.Document;
import it.uniroma1.lcl.babelarity.MiniBabelNet;

/**
 * Classe che permette di calcolare il grado di similarit� tra due documenti.
 * @author alessio
 */
public class BabelDocumentSimilarity
{
	private static MiniBabelNet miniBabelNet = MiniBabelNet.getInstance();
	
	/**
	 * Metodo che dati in input due documenti, restituisce
	 * un double che equivale al loro grado di similarit�.
	 * @param LinguisticObject o1 
	 * @param LinguisticObject o2
	 * @return double
	 */
	public static double computeSimilarity(Document d1, Document d2)
	{
		double d = weightedOverlap(d1, d2);
		return d;
	}
	
	/**
	 * Metodo che dato in input un Document doc, ne ricava il suo vettore ossia
	 * una mappa dove le chiavi sono le parole del documento e il valore associato alla chiave
	 * sono le volte in cui la parola appare nel documento.
	 * @param Document doc.
	 * @return Map<String, Integer> vector.
	 */
	private static Map<String, Integer> getDocumentVector(Document doc)
	{
		String[] content = doc.getContent().split(" ");
		Map<String, Integer> vector = new HashMap<String, Integer> ();
		for (String s : content)
		{
			s = miniBabelNet.lemmaFinder(s);
			if (s != null)
			{
				if (vector.containsKey(s)) vector.put(s, vector.get(s)+1);
				else vector.put(s, 1);
			}
		}
		
		return vector;
	}
	
	/**
	 * Metodo che data in input una mappa dove le chiavi sono
	 * stringhe e i valori sono interi, si occupa di riordinarla secondo i suoi valori.
	 * @return
	 */
	private static Map<String, Integer> vectorSortingByValue(Map<String, Integer> vector)
	{
		Map<String, Integer> sortedVector = vector.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
		return sortedVector;
	}
	
	/**
	 * Metodo che dati in input due vettori, calcola il loro grado di similarit� con
	 * il metodo "weightedOverlap".
	 * @return double
	 */
	private static double weightedOverlap(Document d1, Document d2)
	{
		//Si ricavano i vettori dei due documenti ordinati in base ai valori.
		Set<String> vettD1 = vectorSortingByValue(getDocumentVector(d1)).keySet();
		Set<String> vettD2 = vectorSortingByValue(getDocumentVector(d2)).keySet();
		
		//Si crea una copia delle chiavi di uno dei vettori e si utilizza il metodo
		//"retainAll(Collection c)" dove c � il secondo vettore, in modo da ottenere 
		//l'intersezione (chiavi in comune) tra i due vettori.
		Set<String> commonWords = new HashSet<String> (vettD1);
		commonWords.retainAll(vettD2);

		List<String> listD1 = new ArrayList<String> (vettD1);
		List<String> listD2 = new ArrayList<String> (vettD2);
		
		double partialSum = 0.0;
		for (String s : commonWords) //Per ogni parola nella lista delle parole comuni...
		{
			double posD1 = listD1.indexOf(s)+1; //Si ricava la loro posizione nei due vettori
			double posD2 = listD2.indexOf(s)+1;
			double sum = 1/(posD1+posD2);
			partialSum += sum;
		}
		
		double divisionSum = 0.0;
		for (double x=1; x<commonWords.size(); x++)
		{
			divisionSum += 1/(2*x);
		}
		
		double totalSum = partialSum / divisionSum;
		
		return totalSum;
	}
}
