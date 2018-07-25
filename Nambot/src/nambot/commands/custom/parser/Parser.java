package nambot.commands.custom.parser;

import java.util.ArrayList;
import java.util.List;

import nambot.commands.custom.TokenType;
import nambot.commands.custom.Tokenizer;
import nambot.commands.custom.Tokenizer.Token;
import nambot.commands.custom.nodes.ErrorNode;
import nambot.commands.custom.nodes.Node;
import nambot.commands.custom.nodes.TopNode;

public class Parser {
	private List<Token> tokens;

	List<Node> nodes;
	boolean isFunc;

	ParserGetter getter;
	ParserGetter.Retval rv;

	public TopNode parse(String function) {
		tokens = Tokenizer.tokenize(function);
		nodes = new ArrayList<>();
		isFunc = false;

		getter = new ParserGetter(this);
		rv = null;

		while (tokens.size() > 0) {
			topNode();
			if (peek(0).type == TokenType.EOF) {
				break;
			}
		}

		return new TopNode(nodes);
	}

	private void topNode() {
		if (isFunc) {
			if ((rv = getter.funcEnd(0)) != null) {
				isFunc = false;
				next(rv.consumed);
				return;
			} else if ((rv = getter.exprs(0)) != null) {
				addAndNext();
				return;
			}
		} else {
			if ((rv = getter.output(0)) != null) {
				addAndNext();
				return;
			} else if ((rv = getter.funcStart(0)) != null) {
				isFunc = true;
				next(rv.consumed);
				return;
			}
		}

		printStackTrace();
	}

	private void addAndNext() {
		nodes.add(rv.value);
		next(rv.consumed);
	}

	private void next(int i) {
		for (int n = 0; n < i; ++n) {
			tokens.remove(0);
		}
	}

	Token peek(int i) {
		if (i >= tokens.size()) {
			return new Token(null, TokenType.EOF);
		}
		return tokens.get(i);
	}

	private void printStackTrace() {
		StringBuilder sb = new StringBuilder();
		sb.append("Parser error:\n");
		for (Token t : tokens) {
			sb.append(t.toString()).append("\n");
		}
		tokens.clear();
		error(sb.toString());
	}

	private void error(String msg) {
		nodes.clear();
		nodes.add(new ErrorNode(msg));
	}
}