package it.uniroma1.lcl.babelarity;

import java.util.List;
import java.util.Set;

import it.uniroma1.lcl.babelarity.classes.MiniBabelNetImp;
import it.uniroma1.lcl.babelarity.classes.SynsetPos;


public interface MiniBabelNet
{
	/**
	 * Singleton pattern: La classe si istanzia richiamando staticamente questo metodo
	 * che salva un nuovo oggetto MiniBabelNet nel campo "istanza".
	 * Se si richiama questo metodo, verr� restituito lo stesso oggetto.
	 * @return MiniBabelNet
	 */
	static MiniBabelNet getInstance() {return MiniBabelNetImp.getInstance();}
	
	/**
	 * Metodo che data una stringa in input, restituisce l'insieme di tutti
	 * i Synset che contengono la stringa tra i loro significati.
	 * @param String word
	 * @return Set<Synset>
	 */
	Set<Synset> getSynsets(String word);
	
	/**
	 * Metodo che dato in input un stringa contenente l'id di un synset,
	 * restituisce il synset corrispondente a quell'id.
	 * @param String id
	 * @return Synset
	 */
	Synset getSynset(String id);
	
	/**
	 * Metodo che data in input una stringa contenente una parola,
	 * restituisce il suo lemma (ossia la parola non declinata).
	 * Se nessun lemma della parola � stato trovato, restituisce null.
	 * @param String word
	 * @return String
	 */
	String getLemma(String word);
	
	/**
	 * Restituisce le informazioni inerenti al Synset fornito in input sotto forma di stringa.
	 * Il formato della stringa � il seguente:
	 * ID\tPOS\tLEMMI\tGLOSSE\tRELAZIONI
	 * Le componenti LEMMI, GLOSSE e RELAZIONI possono contenere pi� elementi, questi sono separati dal carattere ";"
	 * Le relazioni devono essere condificate nel seguente formato:
	 * TARGETSYNSET_RELNAME   es. bn:00081546n_has-kind
	 * 
	 * es: bn:00047028n	NOUN	word;intelligence;news;tidings	Information about recent and important events	bn:0000001n_has-kind;bn:0000001n_is-a
	 * 
	 * @param s
	 * @return
	 */
	String getSynsetSummary(Synset s);
	
	/**
	 * Metodo che dati in input due oggetti linguistici, restituisce
	 * un double che equivale al loro grado di similarit�.
	 * @param LinguisticObject o1 
	 * @param LinguisticObject o2
	 * @return double
	 */
	double computeSimilarity(LinguisticObject o1, LinguisticObject o2);
	
	/**
	 * Metodo che dato in input un id di un synset,
	 * restituisce la lista delle relazioni di quel synset.
	 * @param id
	 * @return List<String>
	 */
	List<String> getRelations(String id);

	/**
	 * Metodo che data in input un id di un synset,
	 * ne ricava il POS.
	 * @param string
	 * @return SynsetPos
	 */
	SynsetPos getPos(String id);

	/**
	 * Metodo che si occupa di ritornare, se presente, il lemma di una parola.
	 * Questa parola viene prima "ripulita" di qualsiasi carattere proibito (es. punteggiatura)
	 * e si controlla che la parola non sia nelle stopword ossia nelle parole da ingnorare.
	 * @param word
	 * @return String
	 */
	String lemmaFinder(String word);
}

