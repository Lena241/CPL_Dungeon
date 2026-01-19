grammar DungeonDSL;

options { visitor = true; }

@header {
package dsl.antlr4;
}

// ---------- Parser rules ----------

program
    : (statement NEWLINE*)* EOF
    ;

statement
    : moveStmt
    | rotateStmt
    | pickupStmt
    | useStmt
    | pushStmt
    | shootFireballStmt
    | pullStmt
    | repeatStmt
    | ifStmt
    | setVariableStmt
    | switchStmt
    | whileStmt
    ;

moveStmt
    : GEHEN '(' ')'
    ;

rotateStmt
    : DREHEN '(' direction ')'
    ;

pickupStmt
    : AUFHEBEN '(' ')'
    ;

useStmt
    : BENUTZEN '(' direction ')'
    ;

shootFireballStmt
    : FEUERBALL '(' ')'
    ;

pushStmt
    : SCHIEBEN '(' ')'
    ;

pullStmt
    :   ZIEHEN '(' ')'
    ;

ifStmt
  : IF condition ':' NEWLINE+ block
    (ELSE IF condition ':' NEWLINE+ block)*
    (ELSE ':' NEWLINE+ block)?
    END
  ;

repeatStmt
    : REPEAT ID INRANGE LPAREN range RPAREN COLON NEWLINE+ block END
    ;

whileStmt
    : WHILE condition ':' NEWLINE+ block END
    ;

range
   : INT
   | ID
   ;

expression
    : expressionRootStmt
    | condition
    ;

condition
  : orExpr
  ;

orExpr
  : andExpr (OR andExpr)*
  ;

andExpr
  : notExpr (AND notExpr)*
  ;

notExpr
  : NOT notExpr
  | predicate
  | '(' condition ')'
  ;

predicate
  : ACTIVE '(' direction ')'
  | WALL  '(' direction ')'
  | FLOOR '(' direction ')'
  | PIT   '(' direction ')'
  | BOOLEAN
  | ID
  | comparsion
  ;

comparsion
    : expressionRootStmt COMPARE_OPERATOR expressionRootStmt
    | condition COMPARE_OPERATOR condition;

block
  : (statement NEWLINE*)+
  ;

switchStmt
    : SWITCH '(' ID ')' ':' NEWLINE+ (caseStmt NEWLINE*)* (defaultStmt NEWLINE*)? END;

caseStmt
    : CASE caseValueStmt ':' NEWLINE+ (statement NEWLINE*)*;

caseValueStmt
    : ID
    | INT;

defaultStmt
    : DEFAULT ':' NEWLINE+ (statement NEWLINE*)*;

setVariableStmt
    : ID '=' expression;

expressionRootStmt
    : expressionFirstOrderStmt;

expressionFirstOrderStmt
    : expressionSecondOrderStmt
    | expressionFirstOrderStmt FIRST_ORDER_OPERATOR expressionFirstOrderStmt;


expressionSecondOrderStmt
    : expressionLeafStmt
    | expressionSecondOrderStmt SECOND_ORDER_OPERATOR expressionSecondOrderStmt
    ;


expressionLeafStmt
    : ID
    | INT
    | '(' expressionRootStmt ')';

direction
    : VORNE
    | HINTER
    | HIER
    | LINKS
    | RECHTS
    ;

// ---------- Lexer rules ----------

// --- Punctuation ---
LPAREN : '(';
RPAREN : ')';
COLON  : ':';
COMMA  : ',';

// --- Control Flow ---
IF: 'if';
ELSE: 'else';
END: 'end';
REPEAT  : 'for';
WHILE : 'while';

// --- Boolean logic ---
AND: 'and';
OR: 'or';
NOT: 'not';

// --- Switch / Case ---
SWITCH  : 'switch';
CASE    : 'case';
DEFAULT : 'default';

// --- Commands / DSL functions ---
GEHEN   : 'gehen';
DREHEN  : 'drehen';
AUFHEBEN : 'aufheben';
BENUTZEN : 'benutzen';
FEUERBALL : 'feuerball';
SCHIEBEN : 'schieben';
ZIEHEN : 'ziehen';
ACTIVE: 'active';
WALL  : 'wall';
FLOOR : 'floor';
PIT   : 'pit';


// --- Directions ---
LINKS   : 'links';
RECHTS  : 'rechts';
VORNE   : 'vorne';
HINTER  : 'hinter';
HIER    : 'hier';

// --- Literals ---
BOOLEAN : 'true'
        | 'false';
STRING  : '"' ~["]* '"';

// --- Identifiers ---
ID      : (CHAR | '_')(CHAR | DIGIT | '_')*;

// --- Numbers ---
INT     : DIGIT+;
NUMBER  : DIGIT+ ([.,] DIGIT+)? ;

// --- Special phrase token ---
INRANGE : 'in range';

// --- Whitespace / Comments ---
NEWLINE : ('\r'? '\n')+ ;
WS      : [ \t\r]+ -> skip ;
COMMENT : '#' ~[\r\n]* -> skip;

// --- Fragments ---
fragment CHAR   : [a-zA-Z];
fragment DIGIT : [0-9] ;

// --- Compare Operators ---
COMPARE_OPERATOR
    : '=='
    | '!='
    | '>='
    | '<='
    | '<'
    | '>'
    ;
// --- Calculation Operators ---
FIRST_ORDER_OPERATOR
    : '+'
    | '-';

SECOND_ORDER_OPERATOR
    : '*'
    | '/';

