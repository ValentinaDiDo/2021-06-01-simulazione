package it.polito.tdp.genes.model;

public class Vicini implements Comparable<Vicini>{

	Genes gene;
	double peso;
	public Vicini(Genes gene, double peso) {
		super();
		this.gene = gene;
		this.peso = peso;
	}
	public Genes getGene() {
		return gene;
	}
	public double getPeso() {
		return peso;
	}
	@Override
	public int compareTo(Vicini o) {
		// TODO Auto-generated method stub
		return (int)(o.peso-this.peso);
	}
	public String toString() {
		return this.gene.getGeneId()+" ("+this.peso+")";
	}
	
	
}
