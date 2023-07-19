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
}
