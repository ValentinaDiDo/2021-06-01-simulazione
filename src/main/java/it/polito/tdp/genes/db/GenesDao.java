package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Genes;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public List<Genes> getEssentialGenes(){
		String sql = "SELECT Distinct GeneID, Essential, Chromosome "
				+ "FROM genes "
				+ "WHERE essential = 'essential' "
				+ "ORDER BY GeneID ASC";
		List<Genes> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<Adiacenza> getAdiacenze(Map<String, Genes> map){
		String sql = "SELECT i.GeneID1 as id1, i.GeneID2 as id2 , g1.Chromosome as c1, g2.Chromosome as c2, i.Expression_Corr as cor "
				+ "FROM interactions i, Genes g1, Genes g2 "
				+ "WHERE g1.GeneID>g2.GeneID "
				+ "	AND ( (g1.GeneID = i.GeneID1 AND g2.GeneID = i.GeneID2) OR (g1.GeneID = i.GeneID2 AND g2.GeneID = i.GeneID1) ) "
				+ "	AND g1.Essential='Essential' AND g2.Essential='Essential' "
				+ "GROUP BY i.GeneID1, i.GeneID2, g1.Chromosome, g2.Chromosome, i.Expression_Corr";
		List<Adiacenza> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				String id1 = res.getString("id1");
				String id2 = res.getString("id2");
				
				if(map.containsKey(id2) && map.containsKey(id1)) {
					Genes g1 = map.get(id1);
					Genes g2 = map.get(id2);
					
					double peso = 0;
					if(res.getInt("c1") == res.getInt("c2")) {
						//CROMOSOMI UGUALI
						peso = Math.abs(res.getDouble("cor"))*2;
					}else {
						//CROMOSOMI DIVERSI
						peso = Math.abs(res.getDouble("cor"));
					}
					Adiacenza a = new Adiacenza(g1,g2,peso);
					result.add(a);
				}
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
