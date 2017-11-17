package it.uniroma1.lcl.mynn;

/**
 * Implementa lo strato di neuroni costruito con il numero di neuroni
 * dello strato, il numero di input per ogni neurone, i pesi associati
 * agli input ed infine la funzione di attivazione che è uguale per 
 * tutti i neuroni dello strato.
 */
public class Layer
{
	private int numeroNeuroni;
	private int numeroInput;
	private FunzioneDiAttivazione funzioneDiAttivazione;
	private double [][] pesi;
	
	public Layer(int numeroNeuroni, int numeroInput, FunzioneDiAttivazione funzioneDiAttivazione, double [][] pesi)
	{
		this.numeroNeuroni = numeroNeuroni;
		this.numeroInput = numeroInput;
		this.funzioneDiAttivazione = funzioneDiAttivazione;
		this.pesi = pesi;
	}
	
	//Questo toString non è richiesto dalle specifiche ma per completezza e chiarezza mi è sembrato giusto crearlo
	public String toString()
	{
		String p = "";
		for(int i=0; i<pesi.length; i++)
		{
			for(int j=0; j<pesi[i].length; j++)
			{
				p += pesi[i][j];
				p += " ";
			}
			p += "\n";
		}
		return "Numero di neuroni: " + numeroNeuroni + "; Numero di input per neurone: " + numeroInput + "; Pesi: " + p + "; Funzione di attivazione: " + funzioneDiAttivazione.getClass().getSimpleName(); 
	}
	
	//metodi getter 
	
	public FunzioneDiAttivazione getFunzioneDiAttivazione()
	{
		return funzioneDiAttivazione;
	}
	
	public int getNumeroNeuroni()
	{
		return numeroNeuroni;
	}
	
	public double [][] getPesi()
	{
		return pesi;
	}
	
	public int getNumeroInput()
	{
		return numeroInput;
	}
	
}
