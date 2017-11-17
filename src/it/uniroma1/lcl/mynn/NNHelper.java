package it.uniroma1.lcl.mynn;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Matteo Di Fazio
 * 
 * Questa classe fornisce metodi utili alla creazione della rete neurale.
 *
 */
public class NNHelper
{
	/**
	 * Questo metodo ricava le informazioni necessarie per costruire gli strati letti dal file,
	 * li costruisce, li aggiunge a una linked list in modo tale da preservare l'ordine degli
	 * strati letti da file ed infine ritorna la lista.
	 * 
	 * @param filename : è il file che contiene tutte le informazioni necessarie 
	 * alla costruzione della rete.
	 */
	public static LinkedList<Layer> setListaDiLayer(String filename)
	{
		LinkedList<Layer> list = new LinkedList<>();
		try
		{
			//crea una lista che contiene solamente le linee del file di testo che fanno riferimento ai layer
			LinkedList<String> file  = Files.newBufferedReader(Paths.get(filename)).lines().filter(l -> l.startsWith("l")).collect(Collectors.toCollection(LinkedList::new));
			//per ogni linea mi ricavo le informazioni necessarie per creare lo strato
			for(String l: file)
			{
				FunzioneDiAttivazione f = null;
				int input = 0;
				int output = 0;
				double [][] weights = null;
				//crea un array che contiene le parole della linea in considerazione
				String [] array = l.split(" ");
				ArrayList<String> token = new ArrayList<>(Arrays.asList(array));
				for(String t:token)
				{
					if(t.contains("nome=")) continue;
					//uso la reflection per instanzare la funzione di attivazione
					if(t.contains("activation"))
					{
						String activationFunction = t.substring(t.indexOf("=")+1);
						Class<? extends FunzioneDiAttivazione> c = Class.forName("it.uniroma1.lcl.mynn." + activationFunction).asSubclass(FunzioneDiAttivazione.class);
						f = c.newInstance();
					}
					if(t.contains("input")) input = Integer.parseInt(t.substring(t.indexOf("=")+1));
					if(t.contains("output")) output = Integer.parseInt(t.substring(t.indexOf("=")+1));
					//costruisce i pesi
					if(t.contains("weights"))
					{
						t = t.substring(t.indexOf("=")+1);
						t = t.replaceAll("\\],\\[", " ").replaceAll("\\[", "").replaceAll("\\]", " ").trim();
						String [] a = t.split(" "); int rows = a.length; int cols = a[0].split(",").length;
						double [][] pesi = new double [rows][cols];
						for(int i=0; i<a.length; i++)
						{
							String [] arr = a[i].split(",");
							double n = 0.0;
							for(int j=0; j<arr.length; j++)
							{
								n = Double.parseDouble(arr[j]);
								pesi[i][j] = n;
							}
						}
						weights = pesi;
					}
				}
				//se i pesi non sono specificati nel file setto valori random tra 0 e 1
				if(weights == null)
				{
					weights = new double[output][input+1];
					for(int i=0; i<output; i++)
					{
						for(int j=0; j<= input; j++)
						{
							weights[i][j] = new Random().nextDouble();
						}
					}
				}
				//creo il layer e lo aggiungo alla lista
				list.add(new Layer(output, input, f, weights));
			}
			return list;
		}
		catch (Exception e)
		{
			throw new RuntimeException();
		}
	}
	
	/**
	 * Verifica se il formato dei layer che costituiscono la rete è corretto, ovvero
	 * controlla che il numero di neuroni di uno strato corrisponda al numero di unità in
	 * input allo strato successivo.
	 * 
	 * @param list : è la lista contenente i layer della rete
	 */
	public static boolean isTheLayerFormatCorrect(LinkedList<Layer> list)
	{
		if(list.size() == 1) return true;
		boolean verifica = false;
		for(int i=0; i<list.size()-1; i++)
		{
			verifica = list.get(i).getNumeroNeuroni() == list.get(i+1).getNumeroInput();
			if(verifica == false) return verifica;
		}
		return verifica;
	}
}
