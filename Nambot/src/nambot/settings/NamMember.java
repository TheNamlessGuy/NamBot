package nambot.settings;

import static nambot.globals.Vars.nambot;
import static nambot.globals.Vars.random;

import java.awt.Color;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;

public class NamMember {
	public static final long LEVELUP_LEAST_TIME_DIFF = 120000;

	public long EXP;
	public long EXP_lastPost;
	public int level;
	public int EXP_threshold;

	public int inventorySize;

	public ArrayList<String> inventory;

	public User user;

	private void setEXPThreshold() {
		EXP_threshold = 10 * (int) Math.pow(2, (level - 1));
	}

	public void update() {
		long now = System.currentTimeMillis();
		if ((now - EXP_lastPost) >= LEVELUP_LEAST_TIME_DIFF) {
			EXP_lastPost = now;
			addEXP(random.nextInt(5) + 5);
		}
	}

	public boolean addItem(String item, int amount) {
		if (inventory.size() + amount > inventorySize) {
			return false;
		}

		for (int i = 0; i < amount; ++i) {
			inventory.add(item);
		}
		return true;
	}

	public boolean removeItem(String item, int amount) {
		if (item == null) {
			inventory.clear();
			return true;
		}

		if (!inventory.contains(item)) {
			return false;
		}

		for (int removed = 0; removed < amount && inventory.contains(item); ++removed) {
			inventory.remove(item);
		}
		return true;
	}

	public long getAvailableEXP() {
		int originalLevel = level;

		long retval = EXP;
		while (level >= 0) {
			--level;
			setEXPThreshold();
			retval += EXP_threshold;
		}
		level = originalLevel;
		setEXPThreshold();

		return retval;
	}

	public boolean removeEXP(int amount) {
		long originalEXP = EXP;
		int originalLevel = level;

		EXP -= amount;
		while (EXP < 0 && level >= 0) {
			--level;
			setEXPThreshold();
			EXP += EXP_threshold;
		}

		if (level < 0) {
			EXP = originalEXP;
			level = originalLevel;
			setEXPThreshold();
			return false;
		}
		return true;
	}

	public void addEXP(int amount) {
		EXP += amount;
		while (EXP >= EXP_threshold) {
			levelup();
		}
	}

	private void levelup() {
		EXP -= EXP_threshold;
		++level;
		setEXPThreshold();
	}

	public NamMember(User u) {
		EXP = 0;
		EXP_lastPost = 0;
		level = 0;
		setEXPThreshold();

		inventory = new ArrayList<>();
		inventorySize = 10;

		user = u;
	}

	public NamMember(String ID, JSONObject o) {
		EXP = o.getLong("EXP");
		level = o.getInt("level");
		EXP_lastPost = 0;
		setEXPThreshold();

		inventory = new ArrayList<>();
		JSONArray inv = o.getJSONArray("inventory");
		for (int i = 0; i < inv.length(); ++i) {
			inventory.add(inv.getString(i));
		}
		inventorySize = o.getInt("inventorySize");

		user = nambot.getUserById(ID);
	}

	public JSONObject save() {
		JSONObject o = new JSONObject();

		o.put("level", level);
		o.put("EXP", EXP);
		o.put("inventory", inventory);
		o.put("inventorySize", inventorySize);

		return o;
	}

	public String getName() {
		return user.getName();
	}

	public Color getColor(Guild g) {
		return g.getMemberById(user.getId()).getColor();
	}

	public String getID() {
		return user.getId();
	}
}