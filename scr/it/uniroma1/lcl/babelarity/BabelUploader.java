package it.uniroma1.lcl.babelarity;

import java.util.List;
import java.util.Map;
import java.util.Set;

import it.uniroma1.lcl.babelarity.classes.BabelUploaderImp;

public interface BabelUploader
{
	/**
	 * Metodo che ritorna un'istanza di BabelUploaderImp (Singleton Pattern)
	 * @return BabelUploaderImp
	 */
	public static BabelUploader getInstance() {return BabelUploaderImp.getInstance();}
	
	/**
	 * Metodo che ritorna i lemma.
	 * @return Map<String, String>
	 */
	Map<String, String> getLemmas();
	
	/**
	 * Metodo che ritorna le glosse.
	 * @return Map<String, String>
	 */
	Map<String, String> getGlosses();
	
	/**
	 * Metodo che ritorna tutte le words dei synset.
	 * @return Map<String, String>
	 */
	Map<String, String> getDictionary();
	
	/**
	 * Metodo che ritorna le stopwords.
	 * @return Set<String>
	 */
	Set<String> getStopwords();
	
	/**
	 * Metodo che ritorna le relazioni tra i vari synset.
	 * @return Map<String, List<String>>
	 */
	Map<String, List<String>> getRelations();
}
