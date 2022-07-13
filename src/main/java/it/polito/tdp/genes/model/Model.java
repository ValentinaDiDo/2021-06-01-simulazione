package it.polito.tdp.genes.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	GenesDao dao;
	Graph<Genes, DefaultWeightedEdge> grafo;
	List<Genes> genes;
	Map<String, Genes> mGenes;
	List<Adiacenza> adiacenze;
	
	public Model() {
		this.dao = new GenesDao();
	}
	
	public void CreaGrafo() {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		this.genes = this.dao.getEssentialGenes();
		
		this.mGenes = new TreeMap<>();		
		for(Genes g : this.genes) {
			this.mGenes.put(g.getGeneId(), g);
		}
		
		Graphs.addAllVertices(this.grafo, this.genes);
		
		this.adiacenze = this.dao.getAdiacenze(mGenes);
		
		for(Adiacenza a: this.adiacenze) {
			Graphs.addEdge(this.grafo, a.getG1(), a.getG2(), a.getPeso());
		}
	}

	public Graph<Genes, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	public Map<Genes, Integer> simula(int ingegneri, Genes partenza){
		Map<Genes, Integer> geniStudiati;
		Simulatore simulatore = new Simulatore(grafo, partenza, ingegneri);
		simulatore.init();
		simulatore.run();
		
		return simulatore.getGeniStudiati();

	}
}
