package it.uniroma1.lcl.mynn;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Matteo Di Fazio
 * 
 * Implementa una rete neurale costruita con una lista
 * di strati di neuroni. Le reti effettive estendono questa classe.
 */
public class ReteNeurale implements IReteNeurale
{
	private String name;
	private String rete = "";
	private LinkedList<Layer> listaDiLayer = new LinkedList<>();
	
	public ReteNeurale(LinkedList<Layer> listaDiLayer)
	{
		this.listaDiLayer = listaDiLayer;
	}
	
	/**
	 * Questo metodo computa la somma pesata di ogni neurone dello strato, la dà come input
	 * alla funzione di attivazione e aggiunge il risultato calcolato ad un array di 
	 * double che infine verrà restituito. Se la rete ha più di uno strato, i numeri reali 
	 * all'interno dell'array calcolato dal metodo diventano gli input per ogni neurone 
	 * dello strato successivo.
	 * 
	 * @param values : è un array che contiene gli stessi input per ogni neurone dello strato.
	 */
	@Override
	public double[] process(double[] values)
	{
		for(int k=0; k < listaDiLayer.size(); k++)
		{
			int numeroNeuroni = listaDiLayer.get(k).getNumeroNeuroni();
			double [] result = new double [numeroNeuroni];
			double [][] pesi = listaDiLayer.get(k).getPesi();
			FunzioneDiAttivazione f = listaDiLayer.get(k).getFunzioneDiAttivazione();
			for(int i=0; i < result.length; i++)
			{
				double sommaPesata = 0;
				for(int j=0; j < values.length; j++) sommaPesata += values[j]*pesi[i][j];
				sommaPesata += pesi[i][values.length];
				result[i] = f.process(sommaPesata);
			}
			values = result;
		}
		return values;
	}
	
	/**
	 * Metodo quasi identico al process, cambia il risultato calcolato e poi aggiunto
	 * all'array dei risultati che è frutto del calcolo della derivata della funzione di 
	 * attivazione.
	 */
	public double[] processDerivata(double[] values)
	{
		for(int k=0; k < listaDiLayer.size(); k++)
		{
			int numeroNeuroni = listaDiLayer.get(k).getNumeroNeuroni();
			double [] result = new double [numeroNeuroni];
			double [][] pesi = listaDiLayer.get(k).getPesi();
			FunzioneDiAttivazione f = listaDiLayer.get(k).getFunzioneDiAttivazione();
			for(int i=0; i < result.length; i++)
			{
				double sommaPesata = 0;
				for(int j=0; j < values.length; j++) sommaPesata += values[j]*pesi[i][j];
				sommaPesata += pesi[i][values.length];
				result[i] = f.processDerivata(sommaPesata);
			}
			values = result;
		}
		return values;
	}
	
	/**
	 * Questo metodo calcola il process sui valori di input, calcola l'errore frutto della
	 * differenza tra ouput atteso e il risultato appena calcolato dal process, aggiorna i pesi
	 * in accordo con la formula di aggiornamento distinguendo il caso in cui la rete sia un
	 * percettrone da quello in cui la rete abbia un singolo strato con una funzione di attivazione 
	 * diversa da "Step" ed infine ritorna l'errore.
	 * 
	 * @param values: sono i valori di input.
	 * @param output: sono i valori di output che la rete si aspetta a fronte degli input.
	 */
	@Override
	public double trainIstanza(double[] values, double output[])
	{
		double learningRate = 0.2;
		double [][] pesi = listaDiLayer.get(0).getPesi();
		double [] result = this.process(values);
		double errore = 0.0;
		//calcola l'errore e aggiorna i pesi per ogni neurone
		for(int i=0; i < result.length; i++)
		{
			double error = output[i] - result[i];
			errore += Math.abs(error);
			int j;
			if(this.isPercettrone())
			{
				for(j=0; j < values.length; j++)
				{
					pesi[i][j] += learningRate*error*values[j];
				}
				//aggiorna il threshold
				pesi[i][j] += learningRate*error*1;
			}
			else if(this.isSingleLayerNN())
			{
				double [] processDerivata = new double [result.length];
				processDerivata = this.processDerivata(values);
				for(j=0; j < values.length; j++)
				{
					pesi[i][j] += learningRate*error*processDerivata[i]*values[j];
				}
				//aggiorna il threshold
				pesi[i][j] += learningRate*error*processDerivata[i]*1;
			}
			// se è una rete multistrato ritorna 0 perchè non ho fornito l'implementazione per questo caso
			else {return 0.0;}
		}
		return errore;
	}
	
	/**
	 * Questo metodo fa addestramento aggiornando i pesi tramite trainIstanza fin quando l'errore
	 * calcolato diventa minore di 0.03.
	 * 
	 * @param inputs: è l'insieme di addestramento sotto forma di matrice di double.
	 * @param outputs: sono gli output che la rete si aspetta per ogni istanza dell'insieme di addestramento.
	 */
	@Override
	public void train(double[][] inputs, double[][] outputs)
	{
		while(true)
		{
			double somma_errori = 0.0;
			for(int i=0; i < inputs.length; i++)
			{
				double [] input = inputs[i]; double [] output = outputs[i];
				double errori = this.trainIstanza(input, output);
				somma_errori += errori;
			}
			if(somma_errori < 0.03) break;
		}
	}
	
	/**
	 * Ritorna la rete nel formato definito dalle specifiche.
	 */
	@Override
	public String toString()
	{
		return rete;
	}
	
	//Metodi getter
	
	/**
	 * Ritorna il nome della rete neurale.
	 */
	@Override
	public String getNome()
	{
		return name;
	}
	
	public LinkedList<Layer> getListaDiLayer()
	{
		return listaDiLayer;
	}
	
	//Metodi setter
	
	/**
	 * Questo metodo setta il formato della rete neurale
	 * leggendo il file di configurazione della rete.
	 */
	public void setFormatoRete(String filename) 
	{
		try
		{
			Files.newBufferedReader(Paths.get(filename)).lines().forEach(l -> {
				rete += l;
				rete += "\n";
			});
			rete = toString().trim();
		}
		catch (Exception e)
		{
			throw new RuntimeException("Il file di testo " + filename + " non è stato trovato!");
		}
	}
	
	/**
	 * Setta il nome della rete neurale.
	 */
	public void setName()
	{
		String [] text = rete.split("\n");
		for(int i=0; i<text.length; i++)
		{
			if(text[i].startsWith("nome=")) name = text[i].substring(text[i].indexOf("=")+1);
		}
	}
	
	// metodi di verifica 
	
	/**
	 * Determina se la rete è un Percettrone andando a verificare se la rete
	 * è formata da un solo layer e se ha la funzione di attivazione Step.
	 */
	public boolean isPercettrone()
	{
		return listaDiLayer.size() == 1 && listaDiLayer.get(0).getFunzioneDiAttivazione().getClass().getSimpleName().equals("Step");
	}
	
	/**
	 * Verifica se la rete ha un solo strato
	 */
	public boolean isSingleLayerNN()
	{
		return listaDiLayer.size() == 1;
	}
	
}
