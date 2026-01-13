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
    : REPEAT INT ':' NEWLINE+ (statement NEWLINE*)* END
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

ACTIVE: 'activ';

AND: 'and';
OR: 'or';
NOT: 'not';

INT     : DIGIT+;
NUMBER  : DIGIT+ ([.,] DIGIT+)? ;

REPEAT  : 'repeat';
IF: 'if';
ELSE: 'else';

NEWLINE : ('\r'? '\n')+ ;
WS      : [ \t\r]+ -> skip ;
COMMENT : '#' ~[\r\n]* -> skip;

ID      : (CHAR | '_')(CHAR | DIGIT | '_')*;

fragment CHAR   : [a-zA-Z];
fragment DIGIT : [0-9] ;
