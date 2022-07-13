/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Model;
import it.polito.tdp.genes.model.Vicini;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	Graph<Genes, DefaultWeightedEdge> grafo;

	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbGeni"
    private ComboBox<Genes> cmbGeni; // Value injected by FXMLLoader

    @FXML // fx:id="btnGeniAdiacenti"
    private Button btnGeniAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtIng"
    private TextField txtIng; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.setText("Creazione grafo in corso..\n");
    	this.model.CreaGrafo();
    	
    	this.grafo = this.model.getGrafo();
    	
    	txtResult.appendText("\nVERTICI: "+this.grafo.vertexSet().size());
    	txtResult.appendText("\nARCHI: "+this.grafo.edgeSet().size());
    	
    	this.cmbGeni.getItems().clear();
    	List<Genes> genes = new ArrayList<>(this.grafo.vertexSet());
    	Collections.sort(genes);
    	this.cmbGeni.getItems().addAll(genes);

    }

    @FXML
    void doGeniAdiacenti(ActionEvent event) {
    	if(this.grafo == null) {
    		txtResult.setText("DEVI PRIMA CREARE IL GRAFO\n");
    	}else {
    		if(this.cmbGeni.getValue() == null) {
    			txtResult.appendText("\nSELEZIONA UN GENE");
    		}else {
    			Genes scelto = this.cmbGeni.getValue();
    			
    			List<Vicini> vicini = new ArrayList<>();
    			for(Genes g : Graphs.neighborListOf(this.grafo, scelto)) {
    				Vicini v = new Vicini (g, this.grafo.getEdgeWeight(this.grafo.getEdge(scelto, g)));
    				vicini.add(v);
    			}
    			Collections.sort(vicini);
    			txtResult.setText("Vicini di: "+scelto.toString());
    			for(Vicini v : vicini)
    				txtResult.appendText("\n"+v.toString());
    		}
    	}
    	
    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGeni != null : "fx:id=\"cmbGeni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGeniAdiacenti != null : "fx:id=\"btnGeniAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtIng != null : "fx:id=\"txtIng\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
    
}
