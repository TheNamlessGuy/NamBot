package nambot.commands.custom.nodes;

import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.core.entities.Guild;

public abstract class Node {
	protected class RunInstance {
		public StringBuilder stdout;
		public Map<String, String> vars;
		public Guild guild;

		public RunInstance() {
			stdout = new StringBuilder();
			vars = new HashMap<>();
			guild = null;
		}
	}

	protected void run(RunInstance ri) {
		String value = getValue(ri);
		if (value != null) {
			ri.stdout.append(value);
		}
	}

	protected abstract String getValue(RunInstance ri);
}