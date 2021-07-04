package it.uniroma1.lcl.babelarity.classes;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import it.uniroma1.lcl.babelarity.BabelUploader;

/**
 * Classe che si occupa del caricamento dei dati indispensabili per MiniBabelNet (relazioni, synset ecc..).
 * Questa classe implementa il Singleton Pattern.
 * @author alessio
 *
 */
public class BabelUploaderImp implements BabelUploader
{
	/**
	 * Path in cui sono contenuti i file da cui ricavare le informazioni.
	 */
	private static final Path DOCS = Paths.get("resources/miniBabelNet");
	
	/**
	 * Path in cui � contenuto il file txt con le stopwords.
	 */
	private static final Path STOPWORD_PATH	= Paths.get("resources/stopword/stopword.txt");
	
	/**
	 * Istanza di BabelUploader per il singleton pattern.
	 */
	private static BabelUploaderImp istanza;
	
	/**
	 * Campo destinato ai lemma.
	 */
	private Map<String, String> lemmas = new HashMap<String, String>();
	
	/**
	 * Campo destinato alla relazioni.
	 */
	private Map<String, List<String>> relations = new HashMap<String, List<String>>();
	
	/**
	 * Campo destinato alle glosse.
	 */
	private Map<String, String> glosses = new HashMap<String, String>();
	
	/**
	 * Campo destinato alle words dei synset.
	 */
	private Map<String, String> dictionary = new HashMap<String, String>();
	
	/**
	 * Campo destinato alle stopwords.
	 */
	private Set<String> stopwords = new HashSet<String>();
	
	/**
	 * Singleton pattern: metodo utilizzato per istanziare la classe.
	 * @return
	 */
	public static BabelUploaderImp getInstance()
	{
		if (istanza == null) {istanza = new BabelUploaderImp();}
		return istanza;
	}
	
	/**
	 * Costruttore della classe.
	 */
	private BabelUploaderImp()
	{
		this.lemmas = loadLemmas();
		this.glosses = loadGlosses();
		this.stopwords = loadStopwords();
		this.dictionary = loadDictionary();
		this.relations = loadRelations();
	}

	/**
	 * Metodo che si occupa del caricamento dei lemma.
	 * Ritorna una mappa in cui le chiavi sono delle parole e i valori sono
	 * i lemma corrispondenti alla parola.
	 * @return Map<String, String>
	 */
	public Map<String, String> loadLemmas()
	{
		Map<String, String> lemmaFinalMap = new HashMap<String, String>();
		try
		{
			Set<String> lemmaSet = Files.lines(DOCS.resolve("lemmatization-en.txt")).collect(Collectors.toSet());
			for (String lemma : lemmaSet)
			{
				String[] lemmaArray = lemma.split("\t");
				lemmaFinalMap.put(lemmaArray[0], lemmaArray[1]);
				lemmaFinalMap.put(lemmaArray[1], lemmaArray[1]);
			}
		}
		catch(Exception e) {e.printStackTrace();}
		return lemmaFinalMap;
	}
	
	/**
	 * Metodo che si occupa del caricamento delle relazioni.
	 * Ritorna una mappa in cui le chiavi sono gli id dei synset e come valori troviamo
	 * una lista con gli id che sono in relazione con il synset corrispondente.
	 * @return  Map<String, List<String>
	 */
	private Map<String, List<String>> loadRelations()
	{
		Map<String, List<String>> relationFinalMap = new HashMap<String, List<String>> ();
		
		try
		{
			BufferedReader docReader = Files.newBufferedReader(DOCS.resolve("relations.txt"));
			
			while (docReader.ready())
			{
				String[] line = docReader.readLine().split("\t");
				if (!relationFinalMap.containsKey(line[0])) //Se l'id non  presente nella mappa delle relazioni..
				{
					List<String> newSet = new ArrayList<String>(); //Si associa una nuova lista all'id...
					newSet.add(line[1]+"_"+line[2]); //e si aggiungono le sue relazioni.
					relationFinalMap.put(line[0], newSet);
				}
				else
				{
					List<String> oldSet = relationFinalMap.get(line[0]); //se l'id  presente nella mappa delle relazioni..
					oldSet.add(line[1]+"_"+line[2]); //Si ricava la lista associata all'id e la si aggiorna con nuove relazioni.
					relationFinalMap.put(line[0], oldSet);
				}
			}
		}
		catch(Exception e) {e.printStackTrace();}
		return relationFinalMap;
	}
	
	/**
	 * Metodo che si occupa del caricamento delle glosse.
	 * @return Map<String, String>
	 */
	private Map<String, String> loadGlosses()
	{
		Map<String, String> glossFinalSet = new HashMap<String, String> ();
		
		try
		{
			glossFinalSet = Files.lines(DOCS.resolve("glosses.txt")).filter(s -> s.startsWith("bn:")).map(s -> s.split("\t")).collect(Collectors.toMap(k -> k[0], v -> Arrays.asList(v).stream().skip(1).collect(Collectors.joining("  "))));
		}
		catch(Exception e) {e.printStackTrace();}
		
		return glossFinalSet;
	}
	
	/**
	 * Metodo che si occupa del caricamento delle words dei synset.
	 * Ritorna una mappa in cui le chiavi sono gli id dei synset
	 * e i valori sono le words dei synset corrispondenti.
	 * @return Map<String, String>
	 */
	private Map<String, String> loadDictionary()
	{
		Map<String, String> dictionaryFinalMap = new HashMap<String, String> ();
		
		try
		{
			dictionaryFinalMap = Files.lines(DOCS.resolve("dictionary.txt")).filter(s -> s.startsWith("bn:")).map(s -> s.split("\t")).collect(Collectors.toMap(k -> k[0], v -> Arrays.asList(v).stream().skip(1).collect(Collectors.joining(" "))));
		}
		catch(Exception e) {e.printStackTrace();}
		
		return dictionaryFinalMap;
	}
	
	/**
	 * Metodo che ritorna un insieme contentente le stopword.
	 * @return Set<String>
	 */
	private Set<String> loadStopwords()
	{
		Set<String> stopwordFinalSet = new HashSet<String> ();
		
		try(BufferedReader stopwordDoc = Files.newBufferedReader(STOPWORD_PATH))
		{
			stopwordFinalSet = Files.lines(STOPWORD_PATH).collect(Collectors.toSet());
		}
		catch(Exception e) {e.printStackTrace();}
		
		return stopwordFinalSet;
	}

	/**
	 * Metodo che ritona le relazioni dei synset.
	 * @return Map<String, List<String>>
	 */
	@Override
	public Map<String, List<String>> getRelations() {return relations;}
	
	/**
	 * Metodo che ritorna i lemma dei synset.
	 * @return Map<String, String>
	 */
	@Override
	public Map<String, String> getLemmas() {return lemmas;}
	
	/**
	 * Metodo che ritorna le words dei synset.
	 * @return Map<String, String>
	 */
	@Override
	public Map<String, String> getDictionary() {return dictionary;}
	
	/**
	 * Metodo che ritorna le stopword.
	 * @return Set<String>
	 */
	@Override
	public Set<String> getStopwords() {return stopwords;}
	
	/**
	 * Metodo che ritorna le glosse.
	 * @return Map<String, String>
	 */
	@Override
	public Map<String, String> getGlosses() {return glosses;}
}
