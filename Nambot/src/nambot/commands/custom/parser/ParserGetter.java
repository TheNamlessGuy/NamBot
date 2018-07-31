package nambot.commands.custom.parser;

import nambot.commands.custom.TokenType;
import nambot.commands.custom.Tokenizer.Token;
import nambot.commands.custom.nodes.ArrayAccessNode;
import nambot.commands.custom.nodes.BooleanExpressionNode;
import nambot.commands.custom.nodes.ConstantNode;
import nambot.commands.custom.nodes.ExprsNode;
import nambot.commands.custom.nodes.GetIDNode;
import nambot.commands.custom.nodes.GetNameNode;
import nambot.commands.custom.nodes.GetVarNode;
import nambot.commands.custom.nodes.IfNode;
import nambot.commands.custom.nodes.LengthNode;
import nambot.commands.custom.nodes.LoopNode;
import nambot.commands.custom.nodes.LowerNode;
import nambot.commands.custom.nodes.Node;
import nambot.commands.custom.nodes.NumberLoopNode;
import nambot.commands.custom.nodes.OutputNode;
import nambot.commands.custom.nodes.ParamListNode;
import nambot.commands.custom.nodes.RandomNode;
import nambot.commands.custom.nodes.RandomNumberNode;
import nambot.commands.custom.nodes.ReplaceNode;
import nambot.commands.custom.nodes.SetVarNode;

public class ParserGetter {
	private Parser p;

	public class Retval {
		Node value;
		int consumed;

		public Retval(Node node, int consumed) {
			this.value = node;
			this.consumed = consumed;
		}
	}

	public ParserGetter(Parser parser) {
		p = parser;
	}

	public Retval exprs(int i) {
		int offset = 0;
		Retval rv = null;

		if ((rv = expr(i)) == null) {
			return null;
		}
		Retval rv1 = rv;
		offset += rv.consumed;

		if ((rv = expSep(i + offset)) == null) {
			return new Retval(new ExprsNode(rv1.value), rv1.consumed);
		}
		offset += rv.consumed;

		if ((rv = exprs(i + offset)) == null) {
			return new Retval(new ExprsNode(rv1.value), rv1.consumed);
		}
		offset += rv.consumed;

		ExprsNode node = (ExprsNode) rv.value;
		node.add(0, rv1.value);
		return new Retval(node, offset);
	}

	private Retval expr(int i) {
		Retval rv = null;
		if ((rv = arrayaccess(i)) != null) {
			return rv;
		} else if ((rv = setvar(i)) != null) {
			return rv;
		} else if ((rv = getvar(i)) != null) {
			return rv;
		} else if ((rv = strConst(i)) != null) {
			return rv;
		} else if ((rv = whitespace(i)) != null) {
			return rv;
		} else if ((rv = funccall(i)) != null) {
			return rv;
		}
		return null;
	}

	private Retval funccall(int i) {
		Retval rv = null;
		if ((rv = ifstmt(i)) != null) {
			return rv;
		} else if ((rv = loop(i)) != null) {
			return rv;
		} else if ((rv = numberloop(i)) != null) {
			return rv;
		} else if ((rv = replace(i)) != null) {
			return rv;
		} else if ((rv = lower(i)) != null) {
			return rv;
		} else if ((rv = getname(i)) != null) {
			return rv;
		} else if ((rv = getid(i)) != null) {
			return rv;
		} else if ((rv = random(i)) != null) {
			return rv;
		} else if ((rv = randomvalue(i)) != null) {
			return rv;
		} else if ((rv = randomnumber(i)) != null) {
			return rv;
		} else if ((rv = length(i)) != null) {
			return rv;
		}
		return null;
	}

	private Retval value(int i) {
		Retval rv = null;
		if ((rv = arrayaccess(i)) != null) {
			return rv;
		} else if ((rv = valueNotArray(i)) != null) {
			return rv;
		}
		return null;
	}

	private Retval valueNotArray(int i) {
		Retval rv = null;
		if ((rv = strConst(i)) != null) {
			return rv;
		} else if ((rv = getvar(i)) != null) {
			return rv;
		} else if ((rv = getname(i)) != null) {
			return rv;
		} else if ((rv = getid(i)) != null) {
			return rv;
		} else if ((rv = replace(i)) != null) {
			return rv;
		} else if ((rv = lower(i)) != null) {
			return rv;
		} else if ((rv = whitespace(i)) != null) {
			return rv;
		} else if ((rv = random(i)) != null) {
			return rv;
		} else if ((rv = randomvalue(i)) != null) {
			return rv;
		} else if ((rv = randomnumber(i)) != null) {
			return rv;
		} else if ((rv = length(i)) != null) {
			return rv;
		}
		return null;
	}

	private Retval paramlist(int i) {
		int offset = 0;
		Retval rv = null;

		if ((rv = value(i)) == null) {
			return null;
		}
		Retval rv1 = rv;
		offset += rv.consumed;

		if ((rv = paramSep(i + offset)) == null) {
			return new Retval(new ParamListNode(rv1.value), rv1.consumed);
		}
		offset += rv.consumed;

		if ((rv = paramlist(i + offset)) == null) {
			return new Retval(new ParamListNode(rv1.value), rv1.consumed);
		}
		offset += rv.consumed;

		ParamListNode node = (ParamListNode) rv.value;
		node.add(0, rv1.value);
		return new Retval(node, offset);
	}

	private Retval length(int i) {
		int offset = 0;
		Retval rv = null;
		boolean characcess = false;

		if (p.peek(i).type != TokenType.LENGTH) {
			return null;
		}
		++offset;

		if ((rv = callStart(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = value(i + offset)) == null) {
			return null;
		}
		Node val = rv.value;
		offset += rv.consumed;

		if ((rv = characcess(i + offset)) != null) {
			characcess = true;
			offset += rv.consumed;
		}

		if ((rv = callEnd(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		return new Retval(new LengthNode(val, characcess), offset);
	}

	private Retval random(int i) {
		int offset = 0;
		Retval rv = null;

		if (p.peek(i).type != TokenType.RANDOM) {
			return null;
		}
		++offset;

		if ((rv = callStart(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = paramlist(i + offset)) == null) {
			return null;
		}
		Node val = rv.value;
		offset += rv.consumed;

		if ((rv = callEnd(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		return new Retval(new RandomNode(val, false), offset);
	}

	private Retval randomvalue(int i) {
		int offset = 0;
		Retval rv = null;
		boolean characcess = false;

		if (p.peek(i).type != TokenType.RANDOMVALUE) {
			return null;
		}
		++offset;

		if ((rv = callStart(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = value(i + offset)) == null) {
			return null;
		}
		Node val = rv.value;
		offset += rv.consumed;

		if ((rv = characcess(i + offset)) != null) {
			characcess = true;
			offset += rv.consumed;
		}

		if ((rv = callEnd(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		return new Retval(new RandomNode(val, characcess), offset);
	}

	private Retval randomnumber(int i) {
		int offset = 0;
		Retval rv = null;

		if (p.peek(i).type != TokenType.RANDOMNUMBER) {
			return null;
		}
		++offset;

		if ((rv = callStart(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = value(i + offset)) == null) {
			return null;
		}
		Node lval = rv.value;
		offset += rv.consumed;

		if ((rv = paramSep(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = value(i + offset)) == null) {
			return null;
		}
		Node rval = rv.value;
		offset += rv.consumed;

		if ((rv = callEnd(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		return new Retval(new RandomNumberNode(lval, rval), offset);
	}

	private Retval getname(int i) {
		int offset = 0;
		Retval rv = null;

		if (!(p.peek(i).type == TokenType.GETNAME || p.peek(i).type == TokenType.GETNICKNAME)) {
			return null;
		}
		++offset;

		if ((rv = callStart(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = value(i + offset)) == null) {
			return null;
		}
		Node val = rv.value;
		offset += rv.consumed;

		if ((rv = callEnd(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		return new Retval(new GetNameNode(val, p.peek(i).type == TokenType.GETNICKNAME), offset);
	}

	private Retval getid(int i) {
		int offset = 0;
		Retval rv = null;

		if (p.peek(i).type != TokenType.GETID) {
			return null;
		}
		++offset;

		if ((rv = callStart(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = value(i + offset)) == null) {
			return null;
		}
		Node val = rv.value;
		offset += rv.consumed;

		if ((rv = callEnd(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		return new Retval(new GetIDNode(val), offset);
	}

	private Retval lower(int i) {
		int offset = 0;
		Retval rv = null;

		if (p.peek(i).type != TokenType.LOWER) {
			return null;
		}
		++offset;

		if ((rv = callStart(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = value(i + offset)) == null) {
			return null;
		}
		Node val = rv.value;
		offset += rv.consumed;

		if ((rv = callEnd(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		return new Retval(new LowerNode(val), offset);
	}

	private Retval replace(int i) {
		int offset = 0;
		Retval rv = null;

		if (p.peek(i).type != TokenType.REPLACE) {
			return null;
		}
		++offset;

		if ((rv = callStart(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = value(i + offset)) == null) {
			return null;
		}
		Node val = rv.value;
		offset += rv.consumed;

		if ((rv = paramSep(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = value(i + offset)) == null) {
			return null;
		}
		Node toReplace = rv.value;
		offset += rv.consumed;

		if ((rv = paramSep(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = value(i + offset)) == null) {
			return null;
		}
		Node replaceWith = rv.value;
		offset += rv.consumed;

		if ((rv = callEnd(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		return new Retval(new ReplaceNode(val, toReplace, replaceWith), offset);
	}

	private Retval numberloop(int i) {
		int offset = 0;
		Retval rv = null;

		if (p.peek(i).type != TokenType.NUMBERLOOP) {
			return null;
		}
		++offset;

		if ((rv = callStart(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = value(i + offset)) == null) {
			return null;
		}
		Node var = rv.value;
		offset += rv.consumed;

		if ((rv = paramSep(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = var(i + offset)) == null) {
			return null;
		}
		Node lvar = rv.value;
		offset += rv.consumed;

		if ((rv = paramSep(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = exprs(i + offset)) == null) {
			return null;
		}
		Node exprs = rv.value;
		offset += rv.consumed;

		if ((rv = callEnd(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		return new Retval(new NumberLoopNode(var, lvar, exprs), offset);
	}

	private Retval loop(int i) {
		int offset = 0;
		Retval rv = null;
		boolean characcess = false;

		if (p.peek(i).type != TokenType.LOOP) {
			return null;
		}
		++offset;

		if ((rv = callStart(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = value(i + offset)) == null) {
			return null;
		}
		Node var = rv.value;
		offset += rv.consumed;

		if ((rv = characcess(i + offset)) != null) {
			characcess = true;
			offset += rv.consumed;
		}

		if ((rv = paramSep(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = var(i + offset)) == null) {
			return null;
		}
		Node lvar = rv.value;
		offset += rv.consumed;

		if ((rv = paramSep(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = exprs(i + offset)) == null) {
			return null;
		}
		Node exprs = rv.value;
		offset += rv.consumed;

		if ((rv = callEnd(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		return new Retval(new LoopNode(var, lvar, exprs, characcess), offset);
	}

	private Retval ifstmt(int i) {
		int offset = 0;
		Retval rv = null;

		if (p.peek(i).type != TokenType.IF) {
			return null;
		}
		++offset;

		if ((rv = callStart(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = booleanExpression(i + offset)) == null) {
			return null;
		}
		Node stmt = rv.value;
		offset += rv.consumed;

		if ((rv = paramSep(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = exprs(i + offset)) == null) {
			return null;
		}
		Node ifNode = rv.value;
		offset += rv.consumed;

		rv = callEnd(i + offset);
		if (rv != null) {
			offset += rv.consumed;
			return new Retval(new IfNode(stmt, ifNode, null), offset);
		}

		if ((rv = paramSep(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = exprs(i + offset)) == null) {
			return null;
		}
		Node elseNode = rv.value;
		offset += rv.consumed;

		if ((rv = callEnd(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		return new Retval(new IfNode(stmt, ifNode, elseNode), offset);
	}

	private Retval booleanExpression(int i) {
		int offset = 0;
		Retval rv = null;

		if ((rv = value(i)) == null) {
			return null;
		}
		Node ln = rv.value;
		offset += rv.consumed;

		if ((rv = comparator(i + offset)) == null) {
			return null;
		}
		Node comparator = rv.value;
		offset += rv.consumed;

		if ((rv = value(i + offset)) == null) {
			return null;
		}
		Node rn = rv.value;
		offset += rv.consumed;

		return new Retval(new BooleanExpressionNode(ln, comparator, rn), offset);
	}

	private Retval setvar(int i) {
		int offset = 0;
		Retval rv = null;

		if ((rv = var(i)) == null) {
			return null;
		}
		Node name = rv.value;
		offset += rv.consumed;

		if ((rv = assign(i + offset)) == null) {
			return null;
		}
		offset += rv.consumed;

		if ((rv = value(i + offset)) == null) {
			return null;
		}
		Node value = rv.value;
		offset += rv.consumed;

		return new Retval(new SetVarNode(name, value), offset);
	}

	private Retval getvar(int i) {
		Retval rv = null;
		if ((rv = var(i)) != null) {
			return new Retval(new GetVarNode(rv.value), 1);
		}
		return null;
	}

	private Retval comparator(int i) {
		Retval rv = null;
		if ((rv = pcomp(i)) != null) {
			return rv;
		} else if ((rv = ncomp(i)) != null) {
			return rv;
		}
		return null;
	}

	private Retval arrayaccess(int i) {
		int offset = 0;
		Retval rv = null;
		boolean characcess = false;

		if ((rv = valueNotArray(i)) == null) {
			return null;
		}
		Node val = rv.value;
		offset += rv.consumed;

		if ((rv = characcess(i + offset)) != null) {
			characcess = true;
			offset += rv.consumed;
		}

		Token t = p.peek(i + offset);
		if (t.type != TokenType.ARRAYACCESS) {
			return null;
		}
		++offset;

		Node access = null;
		if (t.value.length() > 1) {
			access = new ConstantNode(t.value.substring(1));
		} else {
			if ((rv = value(i + offset)) == null) {
				return null;
			}
			access = rv.value;
			offset += rv.consumed;
		}

		return new Retval(new ArrayAccessNode(val, access, characcess), offset);
	}

	private Retval characcess(int i) {
		if (p.peek(i).type == TokenType.CHARACCESS) {
			return new Retval(null, 1);
		}
		return null;
	}

	private Retval strConst(int i) {
		if (p.peek(i).type == TokenType.CONSTANT_STRING) {
			return new Retval(new ConstantNode(p.peek(i).value), 1);
		}
		return null;
	}

	private Retval whitespace(int i) {
		if (p.peek(i).type == TokenType.WHITESPACE) {
			return new Retval(new ConstantNode(p.peek(i).value), 1);
		}
		return null;
	}

	private Retval var(int i) {
		if (p.peek(i).type == TokenType.VAR) {
			return new Retval(new ConstantNode(p.peek(i).value), 1);
		}
		return null;
	}

	private Retval pcomp(int i) {
		if (p.peek(i).type == TokenType.P_COMPARISON) {
			return new Retval(new ConstantNode("=="), 1);
		}
		return null;
	}

	private Retval ncomp(int i) {
		if (p.peek(i).type == TokenType.N_COMPARISON) {
			return new Retval(new ConstantNode("!="), 1);
		}
		return null;
	}

	private Retval assign(int i) {
		if (p.peek(i).type == TokenType.ASSIGNMENT) {
			return new Retval(null, 1);
		}
		return null;
	}

	private Retval callStart(int i) {
		if (p.peek(i).type == TokenType.CALL_START) {
			return new Retval(null, 1);
		}
		return null;
	}

	private Retval callEnd(int i) {
		if (p.peek(i).type == TokenType.CALL_END) {
			return new Retval(null, 1);
		}
		return null;
	}

	private Retval expSep(int i) {
		if (p.peek(i).type == TokenType.EXPRESSION_SEPARATOR) {
			return new Retval(null, 1);
		}
		return null;
	}

	private Retval paramSep(int i) {
		if (p.peek(i).type == TokenType.PARAM_SEPARATOR) {
			return new Retval(null, 1);
		}
		return null;
	}

	public Retval output(int i) {
		if (p.peek(i).type == TokenType.OUTPUT) {
			return new Retval(new OutputNode(p.peek(i).value), 1);
		}
		return null;
	}

	public Retval funcStart(int i) {
		if (p.peek(i).type == TokenType.FUNCTION_START) {
			return new Retval(null, 1);
		}
		return null;
	}

	public Retval funcEnd(int i) {
		if (p.peek(i).type == TokenType.FUNCTION_END) {
			return new Retval(null, 1);
		}
		return null;
	}
}