package it.polito.tdp.genes.model;

public class Event implements Comparable<Event>{

	public enum EventType{
		INIZIO_STUDIO,
		CAMBIO_GENE
	}
	
	private EventType type;
	private Genes gene;
	private int mese;
	
	
	
	
	public Event(EventType type, Genes gene, int mese) {
		super();
		this.type = type;
		this.gene = gene;
		this.mese = mese;
		//this.ingegnere = ingegnere;
	}
	public EventType getType() {
		return type;
	}
	public Genes getGene() {
		return gene;
	}
	public int getMese() {
		return mese;
	}
/*	public int getIngegnere() {
		return ingegnere;
	}*/
	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return (int)(this.mese-o.mese);
	}

	
	
}
