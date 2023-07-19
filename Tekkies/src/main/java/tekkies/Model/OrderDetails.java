package tekkies.Model;

import java.math.BigDecimal;
import java.sql.Date;

public class OrderDetails {

	private int order_id = 0;
	private int sneaker_id = 0;
	private int qty = 0;
	private BigDecimal price = new BigDecimal(0);
	
	public int getOrderID()
	{
		return order_id;
	}
	
	public void setOrderID(int order_id)
	{
		this.order_id = order_id;
	}
	
	public int getSneakerID()
	{
		return sneaker_id;
	}
	
	public void setSneakerID(int sneaker_id)
	{
		this.sneaker_id = sneaker_id;
	}
	
	public int getQty()
	{
		return qty;
	}
	
	public void setQty(int qty)
	{
		this.qty = qty;
	}
	
	public BigDecimal getPrice()
	{
		return price;
	}
	
	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}

	@Override
	public String toString() {
		return "OrderDetails [orderId=" + order_id + ", sneakerId=" + sneaker_id + ", qty=" + qty + ", price=" + price + "]";
	}
}
