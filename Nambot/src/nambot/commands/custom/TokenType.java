package nambot.commands.custom;

public enum TokenType {
	EOF, ERROR,

	OUTPUT, FUNCTION_START, FUNCTION_END, EXPRESSION_SEPARATOR, PARAM_SEPARATOR,

	CHARACCESS, ARRAYACCESS, VAR, ASSIGNMENT,

	CONSTANT_STRING,

	CALL_START, CALL_END,

	LOWER, REPLACE, LOOP, IF, P_COMPARISON, N_COMPARISON,

	GETNAME, GETNICKNAME, GETID, RANDOM,

	WHITESPACE;
}