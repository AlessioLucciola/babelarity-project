package it.uniroma1.lcl.babelarity.similarity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import it.uniroma1.lcl.babelarity.MiniBabelNet;
import it.uniroma1.lcl.babelarity.Synset;
import it.uniroma1.lcl.babelarity.classes.MiniBabelNetImp;

/**
 * Classe che permette di calcolare il grado di similarit� tra due synset.
 * @author alessio
 */
public class BabelSemanticSimilarity
{
	private static MiniBabelNet istanza = MiniBabelNetImp.getInstance();
	
	/**
	 * Metodo che dati in input due synset, restituisce
	 * un double che equivale al loro grado di similarit�.
	 * @param LinguisticObject o1 
	 * @param LinguisticObject o2
	 * @return double
	 */
	public static double computeSimilarity(Synset s1, Synset s2)
	{
		Integer bfs = breadthFirstSearch(s1, s2);
		if (bfs != null) return 1/((double)bfs+1);
		return 0.0;
	}

	/**
	 * Metodo che dati in input due synset, ne calcola il loro percorso minimo.
	 * @param s1
	 * @param s2
	 * @return
	 */
	private static Integer breadthFirstSearch(Synset s1, Synset s2)
	{
		//Prima di avviare il BFS, si controlla che i due synset da controllare non siano uguali.
		//In questo caso il loro percorso minimo � uguale a 0.
		if (s1.getID().equals(s2.getID())) return 0;
		
		//Lista in cui aggiungere tutti i nodi da visitare.
		Queue<List<String>> queue = new LinkedList<List<String>>();
		//Lista in cui verranno aggiunti tutti i nodi gi� visitati e quindi da ignorare.
		List<String> visitati = new ArrayList<String> ();
		
		//Radice del grafo: la radice del grafo � una lista di stringa in cui troviamo
		//l'id del nodo come primo elemento e la profondit� a cui si trova (in questo caso 0).
		List<String> starter = new ArrayList<String> ();
		starter.add(s1.getID());
		starter.add("0");
		//Si aggiunge la radice alla lista dei nodi visitati.
		visitati.add(s1.getID());
		//Si aggiunge la radice alla coda.
		queue.add(starter);
		
		//Partendo dalla radice si analizzano tutte le relazioni ad essa collegate che vengono aggiunte
		//alla coda e questo processo si ripete finch� non si visita il synset2, oppure finch� non ci sono
		//pi� relazioni da controllare (In questo caso non esiste un percorso di lunghezza minimo).
		while(!queue.isEmpty())
		{
			List<String> head = queue.peek(); //Si salva il valore in testa della coda.
			queue.poll(); //Si elimina il valore in testa della coda.
			
			String id = head.get(0); //id del nodo corrente.
			int prof = Integer.parseInt(head.get(1)); //profondit� del nodo corrente.
			
			List<String> rel = istanza.getRelations(id); //Si ricava la lista delle relazioni collegate al nodo corrente.
			if (!rel.isEmpty() || rel != null)
			{
				rel = relationStringFilter(rel);
				if (rel.contains(s2.getID())) return prof+1;
				for (String r : rel)
				{
					char type = r.toCharArray()[r.length()-1]; //Si analizzano solo le relazioni di tipo "NOUN".
					if (type == 'n' && !visitati.contains(r))
					{
						List<String> newRel = new ArrayList<String> ();
						newRel.add(r);
						newRel.add(Integer.toString(prof+1));
						visitati.add(r);
						queue.add(newRel);
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Metodo che data in input una lista si stringhe (in questo caso id di relazioni)
	 * le filtra nel seguente modo:
	 * Es. Input -> bn:000987645_has-kind 
	 * 	   Output-> bn:000987645 			(Si ricava l'id della relazione)
	 * @param List<String>
	 * @return List<String>
	 */
	private static List<String> relationStringFilter(List<String> list)
	{
		return list.stream().map(s -> s.split("_")[0]).distinct().collect(Collectors.toList());
	}
}
