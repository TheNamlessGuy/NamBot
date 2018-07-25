# NamBot language
The custom command language used by NamBot is a very simple language.  
The biggest quirk it has is probably that it considers all values to be Strings.

## Getting started
### Hello world
If you just want to print a value in the language, it's fairly simple. All you have to do is write the output!
```
Hello World
```

### Code blocks
Just printing a value isn't really code in the language. To actually have something execute, you will have to enclose it in backticks:
```
`args`
```
Anything that produces an output between two backticks is sent to the output. For example, the program above will just print the value of the args passed to the program.

### Expressions
"Expressions" is a collective word for everything you can do in the language. Function calls, variable assignment, constants, you name it.  
Expressions are only executed if they are in code blocks.  
Expressions are separated by the symbol `;`. This isn't always necessary, but it is always recommended.  
It should be noted that whitespace counts as its own expression, and can therefore not be used in function calls or similar. It does however print itself, so the following is fully valid:
```
`"ABC";     ;"DEF"`
```
Output:
```
ABC     DEF
```

### Array access
If you want to only access the first parameter (for example), you have to use array access. This is fortunately very easy:
```
`args0`
```
will print the first argument sent to the program. That is to say, appending a number to the end of a variable name will get you the array values.  
Note that it always splits on whitespace, unless it is a character access, in which case it splits up the entire string character for character.

Arrays are always zero-indexed.

### Character access
Character access is a very specialized syntax which can only be used in 2 ways: either as the first parameter to a loop, or as the variable name to an array:
```
`l(args~,char,char;char)`
`args~0`
```
The above loop will, for the input "abc", produce "aabbcc".  
The above array access, for the input "abc", produce "a".

### Function calls
Function calls are for more advanced things in the language.  
A function call is built up like so:
```
FUNCTION_NAME(PARAMETERS)
```
Users can not specify custom functions, and can only use the functions that are built in to the language.

## Reserved symbols
`;` is used to separate expressions.  
`,` is used to separate parameters to functions.  
`(` denotes a function call start.  
`)` denotes a function call end.  
`low` is for the lower function.  
`if` is for the if statement.  
`l` is for the loop statement.  
`r` is for the replace statement.  
`==` is used in comparison statements.  
`!=` is used in comparison statements.  
`=` is used in assignments.  
`~` denotes character access of variables.  
Generally, all lower case and symbol combinations can be considered reserved. Only use upper case variable names for custom variables.

## Default variables
`args` are the args passed to the program.  
`mentions` is a list of all the user mentions that were passed as args.  
`caller` is a mention of the caller of the program.

## Built in functions
### if
The if statement is very simple. It starts the function call with a boolean operation, followed by one or several expressions (separated by `;`) which will be executed if the boolean operation is true.  
After that segment there is room for an optional "else"-expression list, which will be executed if the boolean operation is false.

#### Structure
```
if(VALUE COMPARATOR VALUE,EXPRESSIONS)
if(VALUE COMPARATOR VALUE,EXPRESSIONS,EXPRESSIONS)
```

#### Example usage
```
if(args0 == "hello","goodbye")
if(A!=B,B,A)
```

### Loop
The loop statement starts with a value (constants, variable name, character access variable name, etc.), followed by the loop variables name, and finally the expression list is added.  
The expressions in the list are executed as long as there are values to assign to the loop variable.

The starting value is split by space if it is not a character access.

#### Structure
```
l(VALUE,VARIABLE,EXPRESSIONS)
```

#### Example usage
```
l(args,arg,arg; ;arg)
l(args~,char,char;char)
```

### Replace
The replace statement starts with a value which will have its contents replaced. This is followed by a value which details what should be replaced, and finally a second value detailing what the second value should be replaced with.

Replace is largely a direct translation to the [replaceAll](https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#replaceAll(java.lang.String,%20java.lang.String)) function in the String class in Java, meaning all tricks that work with it work in the replace statement as well (such as [this](https://stackoverflow.com/a/37734685))

#### Structure
```
r(VALUE,VALUE,VALUE)
```

#### Example usage
```
r(args,"([a-zA-Z])",":regional_indicator_$1:")
r("ABC",A,B)
```

### Lower
Returns the value of the parameter as lower case.

#### Structure
```
low(VALUE)
```

#### Example usage
```
low(args)
low("ABC")
```

### Get name
Gets the name of a mention. Note that the mention has to be of the format `<@[SNOWFLAKE]>` or `<@![SNOWFLAKE]>`, and not `@[USER]`.

#### Structure
```
gn(VALUE)
```

#### Example usage
```
gn(caller)
gn(mention0)
```

### Get nickname
Gets the nickname of a mention. Note that the mention has to be of the format `<@[SNOWFLAKE]>` or `<@![SNOWFLAKE]>`, and not `@[USER]`.

#### Structure
```
gnn(VALUE)
```

#### Example usage
```
gnn(caller)
gnn(mention0)
```

### Get ID
Gets the snowflake of a mention. Note that the mention has to be of the format `<@[SNOWFLAKE]>` or `<@![SNOWFLAKE]>`, and not `@[USER]`.

#### Structure
```
gi(VALUE)
```

#### Example usage
```
gi(caller)
gi(mention0)
```

### Random
Randomly choses one of its parameters to display/return.

#### Structure
```
ra(VALUE,VALUE,VALUE)
ra(VALUE)
```

#### Example usage
```
https://i.imgur.com/`ra("6j3yOTH","JG75ivQ")`.jpg
ra(caller,mention0)
```