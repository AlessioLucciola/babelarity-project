package it.uniroma1.lcl.babelarity;

import java.nio.file.Path;

import it.uniroma1.lcl.babelarity.classes.CorpusManagerImp;

/**
 * Interfaccia responsabile del parsing, caricamento e salvataggio dei documenti.
 * Questa interfaccia implementa il singleton pattern.
 * @author alessio
 *
 */
public interface CorpusManager extends Iterable<Document>
{
	/**
	 * Medoto che restituisce una instanza di CorpusManagerImp (Singleton Pattern)
	 * @return CorpusManagerImp
	 */
	public static CorpusManager getInstance() {return CorpusManagerImp.getInstance();}
	
	/**
	 * Restituisce una nuova istanza di Document parsando un file di testo di cui � fornito il percorso in input.
	 * @param path (Path del file)
	 * @return Document
	 */
	Document parseDocument(Path path);
	
	/**
	 * Carica da disco l�oggetto Document identificato dal suo ID.
	 * @param id (id del documento)
	 * @return Document
	 */
	Document loadDocument(String id);
	
	/**
	 * Salva su disco l�oggetto Document passato in input.
	 * @param document
	 */
	void saveDocument(Document document);

}
