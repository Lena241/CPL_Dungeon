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
    | repeatStmt
    ;

moveStmt
    : GEHEN '(' ')'
    ;

rotateStmt
    : DREHEN '(' direction ')'
    ;

pickupStmt
    : ABHOLEN '(' ')'
    ;

repeatStmt
    : REPEAT INT ':' NEWLINE+ (statement NEWLINE*)* END
    ;

direction
    : LINKS
    | RECHTS
    ;

// ---------- Lexer rules ----------

GEHEN   : 'gehen';
DREHEN  : 'drehen';
ABHOLEN : 'abholen';
LINKS   : 'links';
RECHTS  : 'rechts';
END     : 'end';

fragment DIGIT : [0-9] ;
INT     : DIGIT+;
NUMBER  : DIGIT+ ([.,] DIGIT+)? ;

REPEAT  : 'repeat';

NEWLINE : ('\r'? '\n')+ ;
WS      : [ \t\r]+ -> skip ;
COMMENT : '#' ~[\r\n]* -> skip;
