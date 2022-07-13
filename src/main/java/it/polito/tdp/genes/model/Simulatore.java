package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.genes.model.Event.EventType;

public class Simulatore {

	//DATI IN INGRESSO
	Graph<Genes, DefaultWeightedEdge> grafo;
	Genes partenza;
	int INGEGNERI;
	
	//DATI IN USCITA
	Map<Genes, Integer> geniStudiati; //DECREMENTO IL NUMERO DI ING CHE STUDIANO IL GENE QUANDO L'ING CAMBIA GENE
	
	//STATO DEL MONDO
	int MesiMAx; //36 MESI
	List<Genes> daStudiare;
	
	//CODA DEGLI EVENTI
	PriorityQueue<Event> queue;

	public Simulatore(Graph<Genes, DefaultWeightedEdge> grafo, Genes partenza, int iNGEGNERI) {
		super();
		this.grafo = grafo;
		this.partenza = partenza;
		INGEGNERI = iNGEGNERI;
	}
	
	public void init() {
		this.MesiMAx = 36;
		this.daStudiare = new ArrayList<>(this.grafo.vertexSet());
		this.queue = new PriorityQueue<>();
		
		this.geniStudiati = new TreeMap<>();
		 
		//INIZIO A POPOLARE LA CODA
		this.geniStudiati.put(partenza, this.INGEGNERI); //IL GENE SCELTO E' STUDIATO INIZIALMENTE DA TUTTI GLI ING
		
		for(int i=0; i<this.INGEGNERI; i++) {
			Event e = new Event(EventType.INIZIO_STUDIO, partenza, 1);
			this.queue.add(e);
		}
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			if(e.getMese() < 36){
				processEvent(e);
			}
		}
	}

	private void processEvent(Event e) {
		switch(e.getType()) {
		
		
		case INIZIO_STUDIO:
			
			double prob = Math.random();
			
			if(prob < 0.3) {
				//CONTINUO A STUDIARE LO STESSO GENE
				Event nuovo = new Event(EventType.INIZIO_STUDIO, e.getGene(), e.getMese()+1);
				this.queue.add(nuovo);
				System.out.println("Studio gene: "+nuovo.getGene().toString()+" mese: "+nuovo.getMese());
				//non modifico la mappa perché allo stesso gene sarà asociato lo stesso numero di ingegneri
			}else {
				//CAMBIO GENE
				Genes nuovo = nuovoGene(e.getGene());
				
				//AGGIORNO LA MAPPA
				this.geniStudiati.put(e.getGene(), this.geniStudiati.get(e.getGene())-1);
				
				if(this.geniStudiati.containsKey(nuovo)) {
					this.geniStudiati.put(nuovo, this.geniStudiati.get(nuovo)+1);
				}else {
					this.geniStudiati.put(nuovo, 1);
				}
				
				//CREO L'EVENTO E LO AGGIUNGO ALLA QUEUE
				Event prossimo = new Event(EventType.INIZIO_STUDIO, nuovo, e.getMese()+1);
				this.queue.add(prossimo);
				
				System.out.println("Inizio studio nuovo gene: "+prossimo.getGene().toString()+" mese: "+prossimo.getMese());
			
			}
			
			break;
			
		case CAMBIO_GENE: //INUTILE SI FA TUTTO IN INIZIO STUDIO
			break;
		}
		
	}
	
	public Genes nuovoGene(Genes precedente) {
		Genes scelto = null;
		
		double sommaVicini = 0;
		
		for(Genes g : Graphs.neighborListOf(this.grafo, precedente)) {
			sommaVicini += this.grafo.getEdgeWeight(this.grafo.getEdge(precedente, g));
		}
		
		List<Vicini> vicini = new ArrayList<>();
		for(Genes g : Graphs.neighborListOf(this.grafo, precedente)) {
			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(precedente, g)) / sommaVicini;
			Vicini v = new Vicini(g, peso);
			vicini.add(v);
			System.out.println(v.toString());
		}
		Collections.sort(vicini);
		scelto = vicini.get(0).getGene();
		
		return scelto;
	}

	public Map<Genes, Integer> getGeniStudiati() {
		return geniStudiati;
	}
	
	
}
