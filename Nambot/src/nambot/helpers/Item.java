package nambot.helpers;

import java.lang.reflect.Method;

public class Item {
	public final String name;
	public final int price;
	public final Method method;
	public final String description;

	public Item(String name, int price, Method method, String description) {
		this.name = name;
		this.price = price;
		this.method = method;
		this.description = description;
	}
}