package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	
	private List<Esame> partenza;
	private Set<Esame> soluzioneMigliore;
	private double mediaSoluzioneMigliore;

	public Model() {
		super();
		
		//serve per riempire la list, prendendo i dati dal database
		EsameDAO dao = new EsameDAO();
		this.partenza = dao.getTuttiEsami();
	}
	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		
		Set <Esame> parziale = new HashSet<Esame>();
		
		//meglio farlo qui il new della soluzione migliore così 
		//ogni volta che l'utente clicca sul bottone riparte da zero
		
		this.soluzioneMigliore = new HashSet<Esame>();
		this.mediaSoluzioneMigliore =0;
		
		//avrò quindi la chiamata al metodo ricorsivo
		//livello 0 della ricorsione inizialmente
		//numero Crediti mi servira per terminare tipo
		
		//cerca (parziale, 0, numeroCrediti);
		
		cerca2(parziale, 0, numeroCrediti);
		
		return soluzioneMigliore;
		//System.out.println("TODO!");
		//return null;	
	}
	
	private void cerca2(Set<Esame> parziale, int L, int m) {
		
	//casi terminali
		
		int crediti = this.sommaCrediti(parziale);
		
		if (crediti>m)
			return;
		
		if (crediti == m) {
			double media = this.calcolaMedia(parziale);
			
			if ( media > mediaSoluzioneMigliore) {
				//questa parziale deve sovrascrivere quella che avevo prima
				//anche la media viene sovrascritta
				
				soluzioneMigliore = new HashSet <> (parziale);
				mediaSoluzioneMigliore   = media;
			}
			
			return;
		}
		
		
		// se arrivo qui sicuramnte crediti <m  e posso farlo tranne 
		//nel caso in cui gli ho già considerati tutti

		// L= PARTENZA.size() = N -> non ci sono più esami da aggiungere
		
		
		if (L == partenza .size ()) {
			return; //la sol non va bene
		}
		
		// se non riento nei casi di terminazione vado avanti ad anallizzare i vari rami
		//generare i sotto_problemi
		//partenza L è da aggiungere oppure no?
		
		parziale.add(partenza.get(L));
		
		cerca2 (parziale, L+1, m);
		
		parziale.remove(partenza.get(L));
		cerca (parziale, L+1, m);
	}

	
	private void cerca(Set<Esame> parziale, int L, int m) {
		// TODO Auto-generated method stub
		//casi terminali
		
		int crediti = this.sommaCrediti(parziale);
		
		if (crediti>m)
			return;
		
		if (crediti == m) {
			double media = this.calcolaMedia(parziale);
			
			if ( media > mediaSoluzioneMigliore) {
				//questa parziale deve sovrascrivere quella che avevo prima
				//anche la media viene sovrascritta
				
				soluzioneMigliore = new HashSet <> (parziale);
				mediaSoluzioneMigliore   = media;
			}
			
			return;
		}
		
		
		// se arrivo qui sicuramnte crediti <m  e posso farlo tranne 
		//nel caso in cui gli ho già considerati tutti

		// L= PARTENZA.size() = N -> non ci sono più esami da aggiungere
		
		
		if (L == partenza .size ()) {
			return; //la sol non va bene
		}
		
		// se non riento nei casi di terminazione vado avanti ad anallizzare i vari rami
		//generare i sotto_problemi
		for (Esame e : partenza) {
			if (!parziale.contains(e)) {
				parziale.add(e);
				cerca(parziale, L+1, m);
				parziale.remove(e);
			}
		}
		
		
		
	}

	public double calcolaMedia(Set<Esame> esami) {
		
		int crediti = 0;
		int somma = 0;
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return somma/crediti;
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}

}
