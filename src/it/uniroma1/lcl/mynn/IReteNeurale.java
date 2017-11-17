package it.uniroma1.lcl.mynn;

import java.util.LinkedList;

/**
 * Questa è l'interfaccia della rete neurale. Comprende la definizione dei metodi richiesti dalle specifiche
 * e l'implementazione del metodo statico "carica".
 */
public interface IReteNeurale
{
	double[] process(double[] values);
	
	double trainIstanza(double[] values, double output[]);
	
	void train(double[][] inputs, double[][] outputs);
	
	String getNome();
	
	/**
	 * Questo metodo statico carica e configura la rete neurale da file. Prende in input il nome del file dal quale 
	 * costruire la rete e a seconda del nome costruisce e ritorna la rete neurale effettiva corrispondente.
	 * 
	 * @param filename : è il file che contiene tutte le informazioni necessarie 
	 * alla costruzione della rete.
	 */
	public static ReteNeurale carica(String filename) 
	{
		try
		{
			if (filename.contains("And"))
			{
				LinkedList<Layer> ll = NNHelper.setListaDiLayer(filename);
				if(NNHelper.isTheLayerFormatCorrect(ll) == false) throw new RuntimeException();
				ReteAnd r = new ReteAnd(ll);
				r.setFormatoRete(filename); r.setName();
				return r;
			}
			if (filename.contains("Xor"))
			{
				LinkedList<Layer> ll = NNHelper.setListaDiLayer(filename);
				if(NNHelper.isTheLayerFormatCorrect(ll) == false) throw new RuntimeException();
				ReteXor r = new ReteXor(ll);
				r.setFormatoRete(filename); r.setName();
				return r;
			}
			if (filename.contains("Squared"))		
			{
				LinkedList<Layer> ll = NNHelper.setListaDiLayer(filename);
				if(NNHelper.isTheLayerFormatCorrect(ll) == false) throw new RuntimeException();
				ReteSquared r = new ReteSquared(ll);
				r.setFormatoRete(filename); r.setName();
				return r;
			}
			if (filename.contains("Sum"))		
			{
				LinkedList<Layer> ll = NNHelper.setListaDiLayer(filename);
				if(NNHelper.isTheLayerFormatCorrect(ll) == false) throw new RuntimeException();
				ReteSum r = new ReteSum(ll);
				r.setFormatoRete(filename); r.setName();
				return r;
			}
			if (filename.contains("Percettrone"))		
			{
				LinkedList<Layer> ll = NNHelper.setListaDiLayer(filename);
				if(NNHelper.isTheLayerFormatCorrect(ll) == false) throw new RuntimeException();
				RetePercettrone r = new RetePercettrone(ll);
				r.setFormatoRete(filename); r.setName();
				return r;
			}
			LinkedList<Layer> ll = NNHelper.setListaDiLayer(filename);
			if(NNHelper.isTheLayerFormatCorrect(ll) == false) throw new RuntimeException();
			ReteOr r = new ReteOr(ll);
			r.setFormatoRete(filename); r.setName();
			return r;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
}
