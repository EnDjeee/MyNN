package it.uniroma1.lcl.mynn;

/**
 * Questa è l'interfaccia della funzione di attivazione. Comprende la definizione dell'unico
 * metodo che dovrà poi essere implementato nelle diverse classi che rappresentano le
 * diverse funzioni di attivazione e che implementano questa interfaccia.
 */
public interface FunzioneDiAttivazione
{
	/**
	 * Questo metodo prende in input la somma pesata dei pesi e degli input del neurone e ritorna
	 * il risultato,ovvero un numero reale, della funzione di attivazione effettiva.
	 * 
	 * @param sommaPesata : è la somma pesata che verrà data in pasto al process della funzione di attivazione.
	 */
	double process(double sommaPesata);
	
	/**
	 * Metodo simile a process con la differenza che il risultato ritornato è frutto della
	 * derivata della funzione di attivazione effettiva.
	 */
	double processDerivata(double sommaPesata);
}
