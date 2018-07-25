package nambot.commands.user;

import static nambot.globals.Vars.random;

import nambot.main.Send;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Zalgo {
	public static void cmd_zalgo(MessageReceivedEvent e, String param) {
		if (param.equals("")) {
			Help.sendCommandUser(e.getChannel(), "zalgo");
			return;
		}

		short fuckup = 1;
		if (param.startsWith("--")) {
			if (param.startsWith("--min")) {
				fuckup = 0;
				param = param.substring(5).trim();
			} else if (param.startsWith("--mid")) {
				fuckup = 1;
				param = param.substring(5).trim();
			} else if (param.startsWith("--max")) {
				fuckup = 2;
				param = param.substring(5).trim();
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < param.length(); ++i) {
			sb.append(param.charAt(i));

			int numUp = 0;
			int numMid = 0;
			int numDown = 0;
			if (fuckup == 0) { // min
				numUp = random.nextInt(8);
				numMid = random.nextInt(2);
				numDown = random.nextInt(8);
			} else if (fuckup == 1) { // mid
				numUp = random.nextInt(16) / 2 + 1;
				numMid = random.nextInt(6) / 2;
				numDown = random.nextInt(16) / 2 + 1;
			} else if (fuckup == 2) { // max
				numUp = random.nextInt(64) / 4 + 3;
				numMid = random.nextInt(16) / 4 + 1;
				numDown = random.nextInt(64) / 4 + 3;
			}

			for (int j = 0; j < numUp; ++j) {
				sb.append(zalgoUp[random.nextInt(zalgoUp.length)]);
			}
			for (int j = 0; j < numMid; ++j) {
				sb.append(zalgoMid[random.nextInt(zalgoMid.length)]);
			}
			for (int j = 0; j < numDown; ++j) {
				sb.append(zalgoDown[random.nextInt(zalgoDown.length)]);
			}
		}

		Send.text(e.getChannel(), sb.toString());
	}

	private static final char[] zalgoUp = { '\u030d', '\u030e', '\u0304', '\u0305', '\u033f', '\u0311', '\u0306', '\u0310', '\u0352', '\u0357', '\u0351',
			'\u0307', '\u0308', '\u030a', '\u0342', '\u0343', '\u0344', '\u034a', '\u034b', '\u034c', '\u0303', '\u0302', '\u030c', '\u0350', '\u0300',
			'\u0301', '\u030b', '\u030f', '\u0312', '\u0313', '\u0314', '\u033d', '\u0309', '\u0363', '\u0364', '\u0365', '\u0366', '\u0367', '\u0368',
			'\u0369', '\u036a', '\u036b', '\u036c', '\u036d', '\u036e', '\u036f', '\u033e', '\u035b', '\u0346', '\u031a' };

	private static final char[] zalgoMid = { '\u0315', '\u031b', '\u0340', '\u0341', '\u0358', '\u0321', '\u0322', '\u0327', '\u0328', '\u0334', '\u0335',
			'\u0336', '\u034f', '\u035c', '\u035d', '\u035e', '\u035f', '\u0360', '\u0362', '\u0338', '\u0337', '\u0361', '\u0489' };

	private static final char[] zalgoDown = { '\u0316', '\u0317', '\u0318', '\u0319', '\u031c', '\u031d', '\u031e', '\u031f', '\u0320', '\u0324', '\u0325',
			'\u0326', '\u0329', '\u032a', '\u032b', '\u032c', '\u032d', '\u032e', '\u032f', '\u0330', '\u0331', '\u0332', '\u0333', '\u0339', '\u033a',
			'\u033b', '\u033c', '\u0345', '\u0347', '\u0348', '\u0349', '\u034d', '\u034e', '\u0353', '\u0354', '\u0355', '\u0356', '\u0359', '\u035a',
			'\u0323' };
}
