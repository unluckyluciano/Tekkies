package tekkies.Model;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Sneaker {
	
	private int sneaker_id = 0;
	private String description = "";
	private BigDecimal price = new BigDecimal(0);
	private int stock = 0;
	private String brand = "";
	private String type = "";
	private String color = "";
	private int size = 0;
	
	public int getSneakerId()
	{
		return sneaker_id;
	}
	public void setSneakerId(int sneaker_id) 
	{
		this.sneaker_id = sneaker_id;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public BigDecimal getPrice() 
	{
		return price;
	}
	
	public void getPrice(BigDecimal price) 
	{
		this.price = price;
	}
	
	public int getStock()
	{
		return stock;
	}
	
	public void setStock(int stock)
	{
		this.stock = stock;
	}
	
	public String getBrand()
	{
		return brand;
	}
	
	public void setBrand(String brand)
	{
		this.brand = brand;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getColor()
	{
		return color;
	}
	
	public void setColor(String color)
	{
		this.color = color;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + sneaker_id;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sneaker other = (Sneaker) obj;
		if (sneaker_id != other.sneaker_id)
			return false;
		if (type == null) 
		{
			if (other.type != null)
				return false;
		} 
		else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Sneaker [id=" + sneaker_id + ", brand=" + brand + ", type=" + type + ", color=" + color + ", size=" + size + ", price="  + price + "]";
	}
}
