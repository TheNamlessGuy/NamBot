package nambot.helpers;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class DiscordTransformers {
	/* Get as mention */
	public static String getAsMention(MessageChannel mc) {
		return "<#" + mc.getId() + ">";
	}

	public static String getAsMention(Member m) {
		return m.getAsMention();
	}

	public static String getAsMention(User u) {
		return u.getAsMention();
	}

	/* Name indicators */
	public static String getNameIndicator(Member m) {
		StringBuilder sb = new StringBuilder();
		if (m.getNickname() != null) {
			sb.append(m.getNickname()).append(" (");
		}
		sb.append(m.getUser().getName() + "#" + m.getUser().getDiscriminator());
		if (m.getNickname() != null) {
			sb.append(')');
		}
		return sb.toString();
	}

	/* Time strings */
	public static String getTimeString(OffsetDateTime then) {
		return getTimeString(OffsetDateTime.now(), then);
	}

	public static String getTimeString(OffsetDateTime now, OffsetDateTime then) {
		String retval = getTimeFormattedString(then) + " (";

		String timePassed = getTimePassedString(now, then);
		if (timePassed == null)
			retval += "today";
		else
			retval += timePassed + " ago";

		retval += ")";
		return retval;
	}

	public static String getTimeFormattedString(OffsetDateTime t) {
		return t.format(DateTimeFormatter.RFC_1123_DATE_TIME);
	}

	public static String getTimePassedString(OffsetDateTime then) {
		return getTimePassedString(OffsetDateTime.now(), then);
	}

	public static String getTimePassedString(OffsetDateTime now, OffsetDateTime then) {
		String retval = "";
		long years = then.until(now, ChronoUnit.YEARS);
		then = then.plusYears(years);
		long months = then.until(now, ChronoUnit.MONTHS);
		then = then.plusMonths(months);
		long days = then.until(now, ChronoUnit.DAYS);

		if (years != 0)
			retval += Long.toString(years) + ((years == 1) ? " year, " : " years, ");
		if (months != 0)
			retval += Long.toString(months) + ((months == 1) ? " month, " : " months, ");
		if (days != 0)
			retval += Long.toString(days) + ((days == 1) ? " day" : " days");

		if (retval.endsWith(", "))
			retval = retval.substring(0, retval.length() - 2);

		return (retval.equals("")) ? null : retval;
	}
}