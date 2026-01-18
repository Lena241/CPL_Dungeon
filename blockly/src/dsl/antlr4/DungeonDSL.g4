grammar DungeonDSL;

options { visitor = true; }

@header {
package dsl;
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

repeatStmt
    : REPEAT ID INRANGE '('range'):' NEWLINE+ (statement NEWLINE*)+ END
    ;

range
   :  INT
   ;

ifStmt
  : IF condition ':' NEWLINE+ block
    (ELSE IF condition ':' NEWLINE+ block)*
    (ELSE ':' NEWLINE+ block)?
    END
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
  ;

block
  : (statement NEWLINE*)+
  ;

switchStmt
    : SWITCH '(' VAR_NAME ')' ':' NEWLINE+ (caseStmt NEWLINE*)* (defaultStmt NEWLINE*)? END;

caseStmt
    : CASE caseValueStmt ':' NEWLINE+ (statement NEWLINE*)*;

caseValueStmt
    : VAR_NAME
    | INT;

defaultStmt
    : DEFAULT ':' NEWLINE+ (statement NEWLINE*)*;

setVariableStmt
    : VAR_NAME '=' expressionRoot;

expressionRoot
    : expressionFirstOrder;

expressionFirstOrder
    : expressionSecondOrder
    | expressionFirstOrder FIRST_ORDER_OPERATOR expressionFirstOrder;


expressionSecondOrder
    : expressionLeaf
    | expressionSecondOrder SECOND_ORDER_OPERATOR expressionSecondOrder
    ;


expressionLeaf
    : VAR_NAME
    | INT
    | '(' expressionRoot ')';

direction
    : VORNE
    | HINTER
    | HIER
    | LINKS
    | RECHTS
    ;

// ---------- Lexer rules ----------

GEHEN   : 'gehen';
DREHEN  : 'drehen';
AUFHEBEN : 'aufheben';
BENUTZEN : 'benutzen';
FEUERBALL : 'feuerball';
SCHIEBEN : 'schieben';
ZIEHEN : 'ziehen';
LINKS   : 'links';
RECHTS  : 'rechts';
VORNE   : 'vorne';
HINTER  : 'hinter';
HIER    : 'hier';
END     : 'end';
SWITCH  : 'switch';
CASE    : 'case';
DEFAULT : 'default';

VAR_NAME : [a-zA-Z]+[a-zA-Z0-9]*;
STRING  : '"' ~["]* '"';
BOOLEAN : 'true'
        | 'false';

ACTIVE: 'activ';

AND: 'and';
OR: 'or';
NOT: 'not';

INT     : DIGIT+;
NUMBER  : DIGIT+ ([.,] DIGIT+)? ;

REPEAT  : 'for';
INRANGE : 'in range';
IF: 'if';
ELSE: 'else';

NEWLINE : ('\r'? '\n')+ ;
WS      : [ \t\r]+ -> skip ;
COMMENT : '#' ~[\r\n]* -> skip;

ID      : (CHAR | '_')(CHAR | DIGIT | '_')*;

fragment CHAR   : [a-zA-Z];
fragment DIGIT : [0-9] ;

FIRST_ORDER_OPERATOR
    : '+'
    | '-';

SECOND_ORDER_OPERATOR
    : '*'
    | '/';
