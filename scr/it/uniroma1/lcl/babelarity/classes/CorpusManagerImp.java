package it.uniroma1.lcl.babelarity.classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import it.uniroma1.lcl.babelarity.CorpusManager;
import it.uniroma1.lcl.babelarity.Document;

/**
 * Classe che si occupa del caricamento dei documenti.
 * Implementa il singleton pattern.
 * @author alessio
 *
 */
public class CorpusManagerImp implements CorpusManager
{
	/**
	 * Campo in cui vengono salvati tutti i documenti caricati nel programma.
	 */
	private Set<Document> savedDocuments = new HashSet<Document>();
	
	/**
	 * Campo statico di CorpusManager per il singleton pattern.
	 */
	private static CorpusManager istance;
	
	/**
	 * Path in cui vengono salvati i nuovi documenti.
	 */
	private static final Path NEW_DOCS = Paths.get("resources/savedDocuments");
	
	/**
	 * Singleton pattern: la classe viene istanziata con il seguente metodo.
	 * @return CorpusManager.
	 */
	public static CorpusManager getInstance() 
	{
		if (istance == null) istance = new CorpusManagerImp();
		return istance;
	}
	
	/**
	 * Costruttore della classe.
	 */
	private CorpusManagerImp()
	{
		if (!Files.exists(NEW_DOCS))
		{
			new File(NEW_DOCS.toString()).mkdirs();
		}
	}

	/**
	 * Metodo che si occupa del caricamento e del parsing dei documenti.
	 * @param Path path del documento.
	 */
	public Document parseDocument(Path path)
	{	
		Document document = null;
		try (BufferedReader br = Files.newBufferedReader(path))
		{
			//si ricava il titolo e l'id dalla prima riga del documento
			String[] firstLine = br.readLine().split("\t");
			String title = firstLine[0];
			String id = firstLine[1];
			StringBuilder content = new StringBuilder();
			
			//si ricava il contenuto del documento dalle righe successive
			while (br.ready())
			{
				content.append(br.readLine()).append('\n');
			}
			
			document = new DocumentImp(id, title, content.toString());
			saveDocumentInApp(document);
		} 
		catch (IOException e) {e.printStackTrace();}
		
		return document;
	}

	/**
	 * Classe che si occupa del caricamento dei documenti.
	 * @return Document
	 */
	@Override
	public Document loadDocument(String id)
	{
		Iterator<Document> docIterator = iterator();
		
		while(docIterator.hasNext())
		{
			Document document = docIterator.next();
			String docId = document.getId();
			
			if (docId.equals(id)) return document;
		}
		return null;
	}

	/**
	 * Metodo che si occupa del salvataggio dei documenti.
	 * @param Document document
	 */
	@Override
	public void saveDocument(Document document)
	{
		String title = document.getTitle();
		String id = document.getId();
		String content = document.getContent();
			
		StringBuilder doc = new StringBuilder();
		doc.append(title + "\t" + id + "\n" + content);
			
		try(BufferedWriter bw = Files.newBufferedWriter(Paths.get(NEW_DOCS+"/"+title+".txt")))
		{
			bw.write(doc.toString());
		}
		catch (IOException e) {e.printStackTrace();}
	}
	
	/**
	 * Metodo che si occupa di salvare un documento all'interno del programma.
	 * @param document
	 */
	public void saveDocumentInApp(Document document) {savedDocuments.add(document);}
	
	/**
	 * Metodo che ritorna un insieme in cui si trovano i documenti salvati nel programma.
	 * @return Set<Document> 
	 */
	public Set<Document> getSavedDocuments() {return savedDocuments;}
	
	/**
	 * Iteratore sui documenti salvati nel programma.
	 */
	@Override
	public Iterator<Document> iterator()
	{
		Iterator<Document> iterator = getSavedDocuments().iterator();
		return iterator;
	}
}