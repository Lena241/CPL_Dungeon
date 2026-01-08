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
ABHOLEN : 'abholen';
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

fragment DIGIT : [0-9] ;
INT     : DIGIT+;
NUMBER  : DIGIT+ ([.,] DIGIT+)? ;

REPEAT  : 'repeat';

NEWLINE : ('\r'? '\n')+ ;
WS      : [ \t\r]+ -> skip ;
COMMENT : '#' ~[\r\n]* -> skip;
