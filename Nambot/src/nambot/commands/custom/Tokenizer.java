package nambot.commands.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
	private static class TokenMatcher {
		public final Pattern pattern;
		public final TokenType type;

		public TokenMatcher(Pattern pattern, TokenType type) {
			this.pattern = pattern;
			this.type = type;
		}
	}

	public static class Token {
		public final String value;
		public final TokenType type;

		public Token(String value, TokenType type) {
			this.value = value;
			this.type = type;
		}

		public String toString() {
			return type + (value == null ? "" : " '" + value + "'");
		}
	}

	private static List<TokenMatcher> matchers;

	public static List<Token> tokenize(String contents) {
		List<Token> tokens = new ArrayList<>();

		StringBuilder output = new StringBuilder();
		boolean isOutput = true;
		while (!contents.equals("")) {
			if (isOutput && contents.startsWith("`")) {
				isOutput = false;
				if (output.length() > 0) {
					tokens.add(new Token(output.toString(), TokenType.OUTPUT));
					output.setLength(0);
				}
				tokens.add(new Token(null, TokenType.FUNCTION_START));
				contents = contents.substring(1);
				continue;
			} else if (!isOutput && contents.startsWith("`")) {
				tokens.add(new Token(null, TokenType.FUNCTION_END));
				contents = contents.substring(1);
				isOutput = true;
				continue;
			}

			if (isOutput) {
				output.append(contents.charAt(0));
				contents = contents.substring(1);
				continue;
			}

			boolean matchFound = false;

			for (TokenMatcher tm : matchers) {
				Matcher m = tm.pattern.matcher(contents);
				if (m.find()) {
					matchFound = true;
					tokens.add(new Token(m.group(), tm.type));
					contents = m.replaceFirst("");
					break;
				}
			}

			if (!matchFound) {
				tokens.add(new Token(contents, TokenType.ERROR));
				break;
			}
		}

		if (output.length() > 0 && (tokens.size() == 0 || tokens.get(tokens.size() - 1).type != TokenType.ERROR)) {
			tokens.add(new Token(output.toString(), TokenType.OUTPUT));
		}

		tokens.add(new Token(null, TokenType.EOF));
		return tokens;
	}

	static {
		matchers = new ArrayList<>();

		matchers.add(createTokenMatcher(";", TokenType.EXPRESSION_SEPARATOR));
		matchers.add(createTokenMatcher(",", TokenType.PARAM_SEPARATOR));
		matchers.add(createTokenMatcher("\\(", TokenType.CALL_START));
		matchers.add(createTokenMatcher("\\)", TokenType.CALL_END));
		matchers.add(createTokenMatcher("low", TokenType.LOWER));
		matchers.add(createTokenMatcher("if", TokenType.IF));
		matchers.add(createTokenMatcher("l", TokenType.LOOP));
		matchers.add(createTokenMatcher("ra", TokenType.RANDOM));
		matchers.add(createTokenMatcher("r", TokenType.REPLACE));
		matchers.add(createTokenMatcher("gnn", TokenType.GETNICKNAME));
		matchers.add(createTokenMatcher("gn", TokenType.GETNAME));
		matchers.add(createTokenMatcher("gi", TokenType.GETID));
		matchers.add(createTokenMatcher("==", TokenType.P_COMPARISON));
		matchers.add(createTokenMatcher("!=", TokenType.N_COMPARISON));
		matchers.add(createTokenMatcher("=", TokenType.ASSIGNMENT));
		matchers.add(createTokenMatcher("([a-zA-Z]+[0-9]+|[a-zA-Z]+~[0-9]+)", TokenType.ARRAYACCESS));
		matchers.add(createTokenMatcher("[a-zA-Z]+~", TokenType.CHARACCESS));
		matchers.add(createTokenMatcher("[a-zA-Z]+", TokenType.VAR));
		matchers.add(createTokenMatcher("\"[^\"]+\"", TokenType.CONSTANT_STRING));
		matchers.add(createTokenMatcher("\\s+", TokenType.WHITESPACE));
	}

	private static TokenMatcher createTokenMatcher(String pattern, TokenType type) {
		return new TokenMatcher(Pattern.compile("^" + pattern), type);
	}
}