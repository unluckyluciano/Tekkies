package tekkies.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;




import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PaymentDAO {
	
	private static final String TABLE_NAME = "payment";
	private static final String SELECT_ALL = "SELECT * FROM payment";
	
	private static DataSource ds;
	static Logger logger = Logger.getLogger(PaymentDAO.class.getName());
	
	
	//Inizializzazione DataSource
	static {
		try {
			//Contesto iniziale JNDI
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			//LookUp DataSource
			ds = (DataSource) envCtx.lookup("jdbc/tekkies");

		} catch (NamingException e) {
			PaymentDAO.logger.log(Level.WARNING, "Errore DataSource");
		}
	}
	
	//Funzione per recuperare tutti i dati necessari da una riga del result set
		private Payment getPaymentMethodFromRS(ResultSet rs) throws SQLException {
			Payment bean = new Payment();
			bean.setPayID(rs.getInt("payment_id"));
			bean.setPayUserID(rs.getInt("pay_user_id"));
			bean.setCardOwn(rs.getString("card_own"));
			bean.setNumber(rs.getString("card_num"));
			bean.setCvv(rs.getString("cvv"));
			bean.setExpDate(rs.getDate("exp_date"));
			return bean;
		}
		
		//Liberare risorse al termine della query
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
		
		public Payment doRetrieveByKey(int id) throws SQLException {
			Connection connection = null;
			PreparedStatement ps = null;
			String sql = SELECT_ALL + " WHERE payment_id = ?";
			ResultSet rs = null;
			Payment bean = new Payment();
			
			try {
				connection = ds.getConnection(); 
				
				ps = connection.prepareStatement(sql);
				ps.setInt(1, id);
				
				rs = ps.executeQuery();
				while(rs.next()) {
					bean = getPaymentMethodFromRS(rs);	
				}		
			}
			finally {
				terminateQuery(ps, connection);
			}
			
			return bean;
		}
		
		public Collection<Payment> doRetrieveAll(String order) throws SQLException {
			
			Connection connection = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Collection<Payment> collection = new ArrayList<>(); 
			
			String sql = SELECT_ALL;
			if(order!=null && !order.equals("")) {
				sql = sql + " ORDER BY " + order;
			}
			
			try {
				connection = ds.getConnection(); 
				ps = connection.prepareStatement(sql);
				
				rs = ps.executeQuery();
				while(rs.next()) {
					Payment bean = getPaymentMethodFromRS(rs);
					
					collection.add(bean);
				}		
			}
			finally {
				terminateQuery(ps, connection);
			}
			
			return collection;
		}
		
		public Collection<Payment> doRetrieveByUser(int userId) throws SQLException {
			
			Connection connection = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Collection<Payment> collection = new ArrayList<>(); 
			
			String sql = SELECT_ALL + " WHERE (pay_user_id=?)";
			
			try {
				connection = ds.getConnection(); 
				ps = connection.prepareStatement(sql);
				ps.setInt(1, userId);
				
				rs = ps.executeQuery();
				while(rs.next()) {
					Payment bean = getPaymentMethodFromRS(rs);
					
					collection.add(bean);
				}		
			}
			finally {
				terminateQuery(ps, connection);
			}
			
			return collection;
		}
		
		public void doSave(Payment bean) throws SQLException {
			Connection connection = null;
			PreparedStatement ps = null;
			String sql = "INSERT INTO " + PaymentDAO.TABLE_NAME + " (pay_user_id, cvv, card_num, exp_date, card_own)"
					+ " VALUES (?, ?, ?, ?, ?) ";
			
			try {
				connection = ds.getConnection();
				connection.setAutoCommit(false);

				ps = connection.prepareStatement(sql);
				
				ps.setInt(1, bean.getPayUserID());
				ps.setString(2, bean.getCvv());
				ps.setString(3, bean.getNumber(false));
				ps.setDate(4, bean.getExpDate());
				ps.setString(5, bean.getCardOwn());
				
				ps.executeUpdate();
				connection.commit();		
			}
			finally {
				terminateQuery(ps, connection);
			}
		}
		
		public boolean doDelete(int id) throws SQLException {
			Connection connection = null;
			PreparedStatement ps = null;
			String sql = "DELETE FROM " + PaymentDAO.TABLE_NAME + " WHERE payment_id = ?";
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
}
