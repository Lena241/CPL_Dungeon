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
    ;

moveStmt
    : GEHEN '(' ')'
    ;

rotateStmt
    : DREHEN '(' direction ')'
    ;

direction
    : LINKS
    | RECHTS
    ;

// ---------- Lexer rules ----------

GEHEN   : 'gehen';
DREHEN  : 'drehen';
LINKS   : 'links';
RECHTS  : 'rechts';

NEWLINE : ('\r'? '\n')+ ;
WS      : [ \t\r]+ -> skip ;
COMMENT : '#' ~[\r\n]* -> skip;
