package tekkies.Model;

public class CartProduct {

	private Sneaker product = new Sneaker();
	private int qty = 0;
	
	
	public Sneaker getProduct() 
	{
		return product;
	}
	
	public void setProduct(Sneaker product) 
	{
		this.product = product;
	}
	
	public int getQty() 
	{
		return qty;
	}
	
	public void setQty(int qty) 
	{
		if(qty > product.getStock()) 
		{
			qty = product.getStock();
		}
		this.qty = qty;
	}
	
	
	public void increaseQta() 
	{
		if(qty < product.getStock())
		{
			qty++;
		}
	}
	
	public void decreaseQta() 
	{
		if(qty>0) 
		{
			qty--;
		}
	}
	
	@Override
	public String toString() {
		return "CartProduct [product=" + product + ", qty=" + qty + "]";
	}
}
