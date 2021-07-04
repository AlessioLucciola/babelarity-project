package it.uniroma1.lcl.babelarity.classes;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.uniroma1.lcl.babelarity.BabelUploader;
import it.uniroma1.lcl.babelarity.Document;
import it.uniroma1.lcl.babelarity.LinguisticObject;
import it.uniroma1.lcl.babelarity.MiniBabelNet;
import it.uniroma1.lcl.babelarity.Synset;
import it.uniroma1.lcl.babelarity.Word;
import it.uniroma1.lcl.babelarity.strategypattern.DocumentSimilarity;
import it.uniroma1.lcl.babelarity.strategypattern.LexicalSimilarity;
import it.uniroma1.lcl.babelarity.strategypattern.SemanticSimilarity;
import it.uniroma1.lcl.babelarity.strategypattern.Similarity;

/**
 * La classe ​MiniBabelNet​Imp rappresenta l’implementazione della omonima rete semantica ed 
 * espone un metodo per calcolare la similarità tra oggetti linguistici ed implementa 
 * il pattern strategy per impostare le implementazioni di similarità tra i diversi tipi 
 * di oggetti linguistici.
 * @author alessio
 *
 */
public class MiniBabelNetImp implements MiniBabelNet
{
	/**
	 * Istanza di MiniBabelNet (Strategy Pattern)
	 */
	private static MiniBabelNet istance;
	
	/**
	 * Istanza di Similarity (Strategy Pattern). 
	 */
	private Similarity similarity;
	
	/**
	 * Istanza di BabelUploader (Strategy Pattern).
	 */
	private BabelUploader babelUploader;

	/**
	 * Singleton pattern: La classe si istanzia richiamando staticamente questo metodo
	 * che salva un nuovo oggetto MiniBabelNet nel campo "istance".
	 * Se si richiama nuovamente questo metodo, verrà restituito lo stesso oggetto.
	 * @return MiniBabelNet
	 */
	public static MiniBabelNet getInstance()
	{
		if (istance == null) istance = new MiniBabelNetImp();
		return istance;
	}
	
	/**
	 * Costruttore di MiniBabelNetImp.
	 * Implementa il singleton pattern.
	 */
	private MiniBabelNetImp()
	{
		if (babelUploader == null) babelUploader = BabelUploader.getInstance();
	}
	
	/**
	 * Metodo che data in input una stringa, restituisce l'insieme di tutti
	 * i Synset che contengono la stringa tra i loro significati.
	 * @param String word
	 * @return Set<Synset>
	 */
	@Override
	public Set<Synset> getSynsets(String word)
	{
		Set<Synset> synsets = new HashSet<Synset>();
		Map<String, String> dictionary = babelUploader.getDictionary();
		for (String id : dictionary.keySet())
		{
			List<String> definitions = Arrays.asList(dictionary.get(id).split(" "));
			if (definitions.contains(word)) synsets.add(getSynset(id));
		}
		return synsets;
	}
	
	/**
	 * Metodo che dato in input un stringa contenente l'id di un synset,
	 * restituisce il synset corrispondente a quell'id.
	 * 
	 * Un synset è formato dalle seguenti parti:
	 * 1)String id;
	 * 2)String glosses;
	 * 3)SynsetPos pos;
	 * 4)String dictionary;
	 * 
	 * @param String id
	 * @return Synset
	 */
	@Override
	public Synset getSynset(String id)
	{
		String glosses = babelUploader.getGlosses().get(id);
		SynsetPos pos = getPos(id); 
		String dictionary = babelUploader.getDictionary().get(id);
		
		return new SynsetImp(id, glosses, pos, dictionary);
	}

	/**
	 * Metodo che data in input una stringa contenente una Word,
	 * restituisce il suo lemma (ossia la parola non declinata).
	 * Se non si trova alcun lemma della parola, restituisce null.
	 * @param String word
	 * @return String
	 */
	@Override
	public String getLemma(String word)
	{
		return babelUploader.getLemmas().get(word);
	}
	
	/**
	 * Metodo che dato in input l'id di un synset,
	 * restituisce la lista delle relazioni di quel synset.
	 * @param String id
	 * @return List<String>
	 */
	public List<String> getRelations(String id)
	{
		return babelUploader.getRelations().get(id);
	}
	
	/**
	 * Metodo che dato in input un Synset, ne restituisce un riassunto sotto forma di stringa.
	 * Questa stringa è strutturatq nel seguente modo:
	 * ID\tPOS\tLEMMI\tGLOSSE\tRELAZIONI del synset.
	 * @param Synset s
	 * @return String
	 */
	@Override
	public String getSynsetSummary(Synset s)
	{
		Synset syn = (SynsetImp) s;
		
		String id = syn.getID();
		String pos = syn.getPOS().toString();
		String[] glosses = syn.getGlosses().split("  ");
		String definitions = syn.getDefinitions();
		List<String> relations = getRelations(id);
		
		StringBuilder summary = new StringBuilder();
		summary.append(id+"\t"+pos+"\t"+definitions+"\t");
		for (String str : glosses) summary.append(str+";");
		summary.append("\t");
		for (String str : relations)
		{
			summary.append(str+";");
		}
		return summary.toString();
	}
	
	/**
	 * Metodo che si occupa della modifica della similarità.
	 * @param similarity
	 */
	public void setSimilarity(Similarity similarity)
	{
		this.similarity = similarity;
	}

	/**
	 * Metodo che dati in input due oggetti linguistici dello stesso tipo, restituisce
	 * un double che equivale al loro grado di similarità.
	 * Se i due oggetti linguistici sono di due tipi differenti allora viene restituito il valore 0.0.
	 * @param LinguisticObject o1 
	 * @param LinguisticObject o2
	 * @return double
	 */
	@Override
	public double computeSimilarity(LinguisticObject o1, LinguisticObject o2)
	{
		if (checkSimilarity(o1, o2)) return similarity.computeSimilarity(o1, o2);
		else return 0.0;
	}
	
	/**
	 * Metodo che in base agli oggetti linguistici dati in input, sceglie il metodo di calcolo della 
	 * similarità adatto a calcolarla. Se i due oggetti non sono nulli e sono dello stesso tipo, 
	 * allora viene restituito true e viene scelto il metodo di calcolo della similarità adeguato, 
	 * altrimenti restituisce false.
	 * @param o1
	 * @param o2
	 */
	private boolean checkSimilarity(LinguisticObject o1, LinguisticObject o2)
	{
		if (o1 instanceof Word && o2 instanceof Word)
		{
			similarity = new LexicalSimilarity();
			return true;
		}
		else if (o1 instanceof Document && o2 instanceof Document)
		{
			similarity = new DocumentSimilarity();
			return true;
		}
		else if (o1 instanceof Synset && o2 instanceof Synset)
		{
			similarity = new SemanticSimilarity();
			return true;
		}
		else
		{
			System.out.println("Grado di similarità non calcolabile tra "+o1.toString()+" e "+o2.toString());
			return false;
		}
	}
	
	/**
	 * Metodo che dato in input l'id di un synset, restituisce il pos del synset (Part-of-speech).
	 * @param String id
	 * @return SynsetPos
	 */
	public SynsetPos getPos(String id)
	{
		String POS = id.substring(id.length()-1).toUpperCase();
		SynsetPos sp = SynsetPos.valueOf(POS);
		return sp;
	}

	/**
	 * Metodo che si occupa di ritornare, se presente, il lemma di una parola.
	 * Questa parola viene prima "ripulita" di qualsiasi carattere proibito (es. punteggiatura)
	 * e si controlla che la parola non sia nelle stopword ossia nelle parole da ingnorare.
	 * Se il lemma non è stato trovato, restituisce null.
	 * @param word
	 * @return String
	 */
	public String lemmaFinder(String word)
	{
		Set<String> notToCount = babelUploader.getStopwords();
		String cleanedString = word.replaceAll("[.|,|(|)|[|]|*|#|-|_|;|&|%|{|}|=|+|'|:|?|/|' ']", "").toLowerCase();
		String lemma = getLemma(cleanedString);
		if (lemma != null && !notToCount.contains(lemma))
		{
			return lemma;
		}
		return null;
	}

}