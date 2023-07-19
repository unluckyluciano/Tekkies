package tekkies.Model;

import java.util.ArrayList;
import java.util.List;

public class Cart 
{
	private List<CartProduct> products;
	
	public Cart() 
	{
		products = new ArrayList<>();
	}
	
	public void addProduct(Sneaker product) 
	{
		addProduct(product, 1);
	}
	
	public void addProduct(Sneaker product, int qty) 
	{
		if(product.getStock() > 0) {
			
			if(product.getStock() < qty) 
			{
				qty = product.getStock();
			}
			
			boolean found = false;
			
			for(CartProduct x : products) 
			{
				if(x.getProduct().equals(product)) 
				{
					x.setQty(x.getQty() + qty);
					found = true;
					break;
				}
			}
			
			if(!found) 
			{
				CartProduct prod = new CartProduct();
				prod.setProduct(product);
				prod.setQty(qty);
				products.add(prod);                      
			}
		}
	}
	
	public void deleteProduct(Sneaker product) 
	{
		for(CartProduct x : products) 
		{
			if(x.getProduct().equals(product)) 
			{
				products.remove(x);
				break;
			}
		}
 	}
	
	public List<CartProduct> getProducts() 
	{
		return  products;	
	}
	
	public boolean isEmpty() 
	{
		return products.isEmpty();
	}
}
