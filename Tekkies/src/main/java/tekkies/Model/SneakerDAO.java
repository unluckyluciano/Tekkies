package tekkies.Model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class SneakerDAO {
	
	private static final String TABLE_NAME = "sneaker";
	private static final String  SELECT_ALL = "SELECT * FROM sneaker";
	
	private static DataSource ds;
	static Logger logger = Logger.getLogger(SneakerDAO.class.getName());
	
	//Inizializzazione DataSource
	static {
		try {
			//Contesto iniziale JNDI
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			//LookUp DataSource
			ds = (DataSource) envCtx.lookup("jdbc/tekkies");

		} catch (NamingException e) {
			SneakerDAO.logger.log(Level.WARNING, "Errore DataSource");
		}
	}
	
	private void terminateQuery(PreparedStatement ps, Connection connection) throws SQLException {
		try {
			if(ps != null) {
				ps.close();
			}
		}
		finally {
			if(connection != null) {
				connection.close();
			}
		}
	}
	
	private String imgConvert(Blob blob) throws IOException, SQLException {
		InputStream inputStream = blob.getBinaryStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
         
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);                  
        }
         
        byte[] imageBytes = outputStream.toByteArray();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            
        inputStream.close();
        outputStream.close();
		
        return base64Image;
	}
	
	private Sneaker getSneakerFromRS(ResultSet rs) throws SQLException {
		Sneaker bean = new Sneaker();
		bean.setSneakerID(rs.getInt("sneaker_id"));
		bean.setDescription(rs.getString("beer_description"));
		bean.setPrice(rs.getBigDecimal("price"));
		bean.setStock(rs.getInt("stock"));
		bean.setBrand(rs.getString("brand"));
		bean.setType(rs.getString("sneakertype"));
		bean.setColor(rs.getString("color"));
		bean.setSize(rs.getInt("size"));
		bean.setInputStreamImage(rs.getBinaryStream("img"));
		
		try {
			bean.setBase64Image(imgConvert(rs.getBlob("img")));
		} 
		catch (IOException e) {
			SneakerDAO.logger.log(Level.WARNING, "Errore Lettura Immagine");
		}
		
		return bean;
	}
	
	private void buildSneakerPS(Sneaker bean, PreparedStatement ps) throws SQLException {
		ps.setInt(1, bean.getSneakerID());
		ps.setString(2, bean.getDescription());
		ps.setBigDecimal(3, bean.getPrice());
		ps.setInt(4, bean.getStock());
		ps.setString(5, bean.getBrand());
		ps.setString(6, bean.getType());
		ps.setString(7, bean.getColor());
		ps.setInt(8, bean.getSize());
		ps.setBinaryStream(9, bean.getInputStreamImage());
	}
	
	public synchronized Sneaker doRetrieveByKey(int id) throws SQLException {
		Sneaker bean = new Sneaker();
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "SELECT * FROM " + SneakerDAO.TABLE_NAME + " WHERE sneaker_id = ?";
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {		
				bean = getSneakerFromRS(rs);
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return bean;
	}
	
	public synchronized Collection<Sneaker> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<Sneaker> collection = new ArrayList<>(); 
		
		String sql = SELECT_ALL;
		if(order!=null && !order.equals("")) {
			sql = sql + " ORDER BY " + order;
		}
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Sneaker bean = getSneakerFromRS(rs);
				collection.add(bean);
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return collection;
	}
	
	public synchronized Collection<Sneaker> doRetrieveNew() throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<Sneaker> collection = new ArrayList<>(); 
		
		String sql = SELECT_ALL + " ORDER BY sneaker_id DESC LIMIT 5";
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Sneaker bean = getSneakerFromRS(rs);
				collection.add(bean);
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return collection;
	}
	
	public synchronized void doSave(Sneaker bean) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO " + SneakerDAO.TABLE_NAME + " (sneaker_desc, price, stock, brand, sneaker_type, color, size, img) " + 
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			ps = connection.prepareStatement(sql);
			buildSneakerPS(bean, ps);
			
			ps.executeUpdate();
			connection.commit();		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
	}
	
	public synchronized void doUpdate(Sneaker bean) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "UPDATE "+ SneakerDAO.TABLE_NAME + " SET sneaker_desc = ?, price= ? , stock= ?, brand = ?, sneaker_type = ?, color = ?,"+
		"size = ?, img = ? WHERE sneaker_id = ?";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(sql);
			
			ps.setString(1, bean.getDescription());
			ps.setBigDecimal(2, bean.getPrice());
			ps.setInt(3, bean.getStock());
			ps.setString(4, bean.getBrand());
			ps.setString(5, bean.getType());
			ps.setString(6, bean.getColor());
			ps.setInt(7, bean.getSize());
			ps.setBinaryStream(8, bean.getInputStreamImage());
			ps.executeUpdate();
			connection.commit();		
		}
		finally {
			terminateQuery(ps, connection);
		}
	}
	
	public synchronized boolean doDelete(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM " + SneakerDAO.TABLE_NAME + " WHERE sneaker_id = ?";
		int result = 0;
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			
			result = ps.executeUpdate();
			connection.commit();		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return (result!=0);
	}
	
	public synchronized  Collection<Sneaker> doRetrieveByNameDynamic(String name) throws SQLException {
		Sneaker bean = new Sneaker();
		Connection connection = null;
		PreparedStatement ps = null;
		Collection<Sneaker> collection = new ArrayList<>(); 
		String sql = "SELECT * FROM " + SneakerDAO.TABLE_NAME + " WHERE sneaker_type LIKE CONCAT('%',?,'%')";
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();
			
			while(rs.next()) {		
				bean = getSneakerFromRS(rs);
				collection.add(bean);
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return collection;
	}

}
