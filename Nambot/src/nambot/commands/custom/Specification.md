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
`args$0`
```
will print the first argument sent to the program. That is to say, appending `$` and a number to the end of a variable name will get you the array values.  
Note that it always splits on whitespace, unless it is a character access, in which case it splits up the entire string character for character.  
Array indexes can come from anywhere and doesn't have to be hardcoded values. For example, the following is acceptable:
```
`args$args~$"4"`
```
Which, for the input "p4 p1", will result in "p4".

Arrays are always zero-indexed.

### Character access
Character access is a very specialized syntax which can only be used in 3 situations: on the first parameter of a loop, on a variable in an array access, on the parameter to the "random value" function, or on the parameter to the "length" function:
```
`l(args~,char,char;char)`
`args~$0`
`rv(args~)`
`len(args~)`
```
The above loop will, for the input "abc", produce "aabbcc".  
The above array access, for the input "abc", produce "a".  
The "random value" call will, for the input "abc", produce either "a", "b" or "c".
The "length" call will, for the input "abc", produce 3.

### Function calls
Function calls are for more advanced things in the language.  
A function call is built up like so:
```
FUNCTION_NAME(PARAMETERS)
```
Users can not specify custom functions, and can only use the functions that are built in to the language.

## Reserved symbols
`$` is used to for array access. 
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
The if statement is very simple. It starts the function call with a boolean operation, followed by one or several expressions which will be executed if the boolean operation is true.  
After that segment there is room for an optional "else"-expression list, which will be executed if the boolean operation is false.

#### Structure
```
if(VALUE COMPARATOR VALUE,EXPRESSIONS)
if(VALUE COMPARATOR VALUE,EXPRESSIONS,EXPRESSIONS)
```

#### Example usage
```
if(args$0 == "hello","goodbye")
if(A!=B,B,A;B)
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
l(args$0,a,"<";a;">")
l(args$0~,a,"<";a;">")
l(args~$0,a,"<";a;">")
l(args~$0~,a,"<";a;">")
```

### Number loop
The number loop loops from 0 to its first parameter, with each number being set to the variable specified in the second parameter. Each iteration, the third parameter (the expression list) gets evaluated.

#### Structure
```
ln(VALUE,VARIABLE,EXPRESSIONS)
```

#### Example usage
```
ln("10",i,i;i; )
ln(rn("5","10"),i,"output ")
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
gn(mention$0)
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
gnn(mention$0)
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
gi(mention$0)
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
ra(caller,mentions$0)
```

### Random value
Like Random, but instead of choosing between its parameters, it splits the value in its singular parameter.  
Can be used with characcess.

#### Structure
```
rv(VALUE)
```

#### Example usage
```
https://i.imgur.com/`rv("6j3yOTH JG75ivQ")`.jpg
rv(args~)
```

### Random number
Like Random, but instead of choosing between its parameters, it randomly generates a number between the two parameters (inclusive).

#### Structure
```
rn(VALUE, VALUE)
```

#### Example usage
```
rn("0","10")
rn(A,B)
```

### Random line from URL
Like Random, but instead of choosing between its parameters, it accepts an URL linking to a raw text file, and then randomly selects a line from that file.

#### Structure
```
rlu(VALUE)
```

#### Example usage
```
rlu("https://pastebin.com/raw/hF2aR41G")
```

### Length
Outputs the length of the parameter, either in words (separated by space), or if characcess in characters.

#### Structure
```
len(VALUE)
len(VALUE~)
```

#### Example usage
```
len(args)
len(args~)
len("ABC")
```