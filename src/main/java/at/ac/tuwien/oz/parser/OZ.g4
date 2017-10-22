grammar OZ;

@header{
package at.ac.tuwien.oz.parser;
}

program 
	: definition+
	;

definition
	:	localDefinition		# localDef
	|	classDefinition		# classDef
	;


// a class definition has a strict structure as defined below, however each part
// is optional
classDefinition
	: CLASS ID formalParameters? LCURLY 
		visibilityList?
		inheritedClassList?
		localDefinitionList?
		state?
		initialState?
		operationList?
	  RCURLY
	;
	
// lists all the features of a class that shall be visible from outside
// visibility enables read/write accessibility
visibilityList
	:	VISIBLE LPARA feature (COMMA feature)* RPARA
	;

// a feature can be any of the attributes or operations of a class or a reference to INIT
feature
	:	INIT
	|	ID
	;


// lists all the classes to be inherited
inheritedClassList
	:	INHERITS LCURLY
			(classDes SEMI)+
		RCURLY
	;
	
// a class descriptor is the class name optionally followed by generic parameters
// and/or renaming parameters
classDes
	:	ID genActuals? renaming ?
//	|	ID
//	TODO renaming implementieren.
//	|	ID genActuals renaming
//	|	ID renaming
	;

localDefinitionList
	: localDefinition+
	;
	
localDefinition
	: givenTypeDefinition
	| axiomaticDefinition
    | abbreviationDefinition
    | freeTypeDefinition
	;

// ID on the left hand side is used as an abbreviation of the expression on the right hand side
abbreviationDefinition
	: ID formalParameters? ABBRDEF expression
	;
	
// given type definitions are similar to classes that have no inner structure, i.e. they have
// no operations or attributes
givenTypeDefinition
	: LBRACK ID (COMMA ID)* RBRACK
	;

// defines a new type with a selected set of values
// free type definitions are somewhat similar to enumerations as used in other languages
freeTypeDefinition
	: ID FTDEF ID ('|' ID)*
	;

// define constant "variables" and functions
axiomaticDefinition
	: CONST LCURLY
		declarationList? 
		predicateList?
	  RCURLY
	;

// defines attributes and functions of the class
// predicates define the restrictions that must hold
// attributes can be primary and secondary
// secondary variables depend on primary variable and can be expressed in terms of 
// functions dependent on one or more primary variables
state
	: STATE LCURLY 
		primary 
		( DELTA LCURLY 
		  secondary 
		RCURLY )? 
	        predicateList? 
	  RCURLY
    | STATE LCURLY 
      	DELTA LCURLY
        	secondary
        RCURLY
        predicateList?
      RCURLY
    | STATE LCURLY 
        predicateList
      RCURLY
    | STATE LCURLY
      RCURLY
	;

primary
	:	declarationList
	;
		
secondary
	:	declarationList
	;

// defines a state that must hold on object initiation
initialState
	:	INIT LCURLY
			predicateList
		RCURLY
	;

operationList
	:	(operationSchemaDef|operationExpressionDef)+
	;

operationSchemaDef
	:	operationName=ID LCURLY
			deltalist?
			declarationList?
			predicateList?
		RCURLY
	;
	
deltalist
	:	DELTA LPARA ID (COMMA ID)* RPARA
	;
	
operationExpressionDef
	:	operationName=ID ISDEF operationExpression SEMI?
	;

operationExpression
	: 	DSEQ schemaText DOT operationExpression		# distributedSequentialComposition   // 1
	|	DNCH schemaText DOT operationExpression		# distributedNonDeterminisiticChoice // 1
	|   DAND schemaText DOT operationExpression		# distributedConjunction 			 // 1
	|   opExpression								# simpleOperationExpression 		 // 2
	;

opExpression
	:	
		opExprAtom						   				# operationExpressionAtom		// 1
//	|	opExprAtom renaming DIFFERENCE LPARA id (COMMA id)* RPARA 		// TODO 2operation expression atoms with optionally renaming and/or hiding blocks
//	|	opExprAtom renaming												// TODO 2
//	|	opExprAtom DIFFERENCE LPARA id (COMMA id)* RPARA renaming?		// TODO 2
	|	left=opExpression AND right=opExpression		# operationConjunction 		// 3 operation conjunction
	|	left=opExpression APAR right=opExpression		# associativeParallelComposition // 4 associative form of parallel composition
	|	left=opExpression PAR right=opExpression		# parallelComposition		// 5 parallel composition
	|	left=opExpression NCH right=opExpression		# nonDeterministicChoice	// 6 non-deterministic choice operation
	|	left=opExpression SEQ_OP right=opExpression	    # sequentialComposition		// 7 sequential composition
	|	left=opExpression DOT right=opExpression		# scopeEnrichment 			// 8 scope enrichment: variables defined on the left hand side are also available on the right hand side
	;

opExprAtom
	:	LBRACK (DELTA LPARA ID ( COMMA ID)* RPARA)? declarationList? ('|'? predicateList)? RBRACK	# anonymousOperationSchemaDef
	|	caller? opName=id																			# operationPromotion
		//(featureOrFunctionCall ATTR_CALL)? opName=id												
		// TODO (expression '.')? opName=ID // could be extended to support all expressions
	|	LPARA operationExpression RPARA																# parenthesizedOperationExpression
	;  
	
caller
	: (id ATTR_CALL)+
	;
	
// similar to anonymousOperationSchemaDef except that there is no means of changing object attributes.
schemaText
	:	schemaDeclarationList ( '|' predicate) ?
	;
	
schemaDeclarationList
	:	declaration (SEMI declaration)* SEMI?
	;
	
renaming
	:	LBRACK renamePair (COMMA renamePair)* RBRACK
	;	

renamePair
	:	id DIV id
	;

	
declarationList
	: (declaration SEMI)+
	;

declaration
	: declarationNameList COLON expression
	;

declarationNameList
	: id (COMMA id)*
	;

predicateList
	: predicate (SEMI predicate)* SEMI?				# predicates
	;

predicate
	: FORALL schemaText DOT predicate				# forall		
	| EXISTS schemaText DOT predicate				# exists		
	| EXISTS_1 schemaText DOT predicate				# existsOne		
//	| LET letDefs DOT predicate						# letDefinition	// TODO - not in mapping
	| simplePredicate								# simple		
	;


simplePredicate
	:	left=simplePredicate CONJ right=simplePredicate		# conjunction	
	|	left=simplePredicate OR right=simplePredicate		# disjunction	
	|	left=simplePredicate IMPL right=simplePredicate		# implication	
	|	left=simplePredicate EQUIV right=simplePredicate	# equivalence	
	|	predicateAtom										# atom	
	;

predicateAtom
	:	NOT predicateAtom										# negation 	
	|	id ATTR_CALL INIT										# initcall
	|	TRUE													# true	
	|	FALSE													# false	
	|	left=expression underlinedId right=expression 			# referenceBinaryRelation
	|	expression												# booleanExpression	
	|	LPARA predicate RPARA									# parenthesizedPredicate
	;
	
expression
	: left=expression '><' right=expression									# cartesian
	| powerSetOp expression													# powerSetExpression		
	| prefix expression														# prefixExpression
	| left=expression op=(MULT | DIV | INT_DIV | MOD) right=expression		# multiplicativeExpression	
	| left=expression op=(PLUS | MINUS ) right=expression					# additiveExpression		
	| left=expression RANGE right=expression								# rangeExpression			
	| left=expression setOp right=expression								# collectionOperation		
	| left=expression infixComparisonOp right=expression					# infixComparisonOperation	
	| left=expression infixRelationOp right=expression						# infixRelationOperation	
	| left=expression RELATION middle=expression RELATION right=expression	# ternaryRelation			
	| left=expression RELATION right=expression								# binaryRelation			
	| op=(UNION | INTERSECT | CONCATENATE) LPARA expression RPARA			# generalizedOperation
	| expression postfix													# postfixExpression			// TODO - not in mapping
	| CLASS_HIER ID genActuals?												# polymorphicExpression
	| p=predefinedType														# predefinedTypeExpression	
	// chapter 8, polygon
 	| SUM schemaText DOT featureOrFunctionCall								# schemaSum					// TODO
	// allows to calculate standard functions on the elements of a set.
	// applicable only if a total order is defined for the element type (int, nat, real, char) is no problem
	// will not be applicable in this implementation for any other set type.
	| op=(MIN | MAX) e=expression											# minMaxOfExpression		
	// will normally be used in expressions on the right hand side of a declaration like
	// queue : Queue[T] where queue is a generic class and T is a generic parameter.
	// queue1 : Queue[Queue[Item]] where Queue is a generic class
	|	id formalParameters													# formalClassReference
	|	id genActuals														# genericClassReference
	|	LCURLY schemaText (DOT expression)? RCURLY							# setAbstraction			// TODO
	|   LPARA expression (COMMA expression)+ RPARA							# tuple
	// feature or function Calls
	// comprises: ids, numbers, calls to function id(..) and function-to-function-calls id(id(..))...
	|	featureOrFunctionCall												# call
	|	LCURLY expression (COMMA expression)* RCURLY						# set
	|	LLBRACK expression (COMMA expression)* RRBRACK						# bag
	|   LBRACK expression (COMMA expression)* RBRACK						# sequence
	|	EMPTYSET															# emptySet
	|	EMPTYBAG															# emptyBag
	|   NCH																	# emptySequence				// same symbol used for empty sequence and non-deterministic choice
	|	SELF																# self
	|	LPARA expression RPARA												# parenthesizedExpression
	;
	
genActuals
	:	LBRACK expression (COMMA expression)* RBRACK
	;

// formal parameters are used as generic parameters for a class, such generic types
// may also be used in declarations to define a variable to have a generic type
formalParameters
	:	LBRACK ID (COMMA ID)* RBRACK
	;	

powerSetOp
	:	op=POWER
	|	op=POWER1
//	|	op=ID_REL		// TODO - not in mapping
	|	op=FINITE
	|	op=FINITE1
	|	op=SEQ
	|	op=SEQ1
	|	op=ISEQ
	|	op=BAG
	;

infixRelationOp
	:	op=PART_FUNC
	|	op=TOT_FUNC
	|	op=PART_INJ
	|	op=TOT_INJ
	|	op=PART_SUR
	|	op=TOT_SUR
	|	op=BIJEC
	|	op=MAPLET
	|	op=F_PART_FUNC
	|	op=F_PART_INJ
	|	op=DOM_RESTR	// domain restriction
	|	op=RAN_RESTR	// range restriction
	|	op=DOM_AR		// domain anti-restriction
	|	op=RAN_AR		// range anti-restriction
	;

infixComparisonOp
	:	op=EQUALS		// equals
	|	op=NEQUALS		// equals not
	|	op=ELEM			// is element in
	|	op=NELEM		// is not element in
	|	op=SUBSET		// is subset of
	|	op=STR_SUBSET	// is a strict subset
	|	op=LT			// less than
	|	op=LTE			// less than or equal
	|	op=GT			// greater than
	|	op=GTE			// greater than or equal
	|	op=PREFIX		// prefix relation a is prefix of sequence b
	|	op=SUFFIX		// suffix relation a is suffix of sequence b
	|	op=IN_SEQ		// segment relation a is part of sequence b
	|	op=IN_BAG		// bag membership
	|	op=SUBBAG		// sub-bag relation
	;

setOp
	:	op=UNION
	|	op=DIFFERENCE
	|	op=INTERSECT
	| 	op=CONCATENATE
	|	op=OVERRIDE
	|	op=EXTRACT
	|	op=FILTER
	|	op=MULTIPLICITY 		// multiplicity in a bag
	|	op=SCALING				// bag scaling
	|	op=BAG_UNION
	|	op=BAG_DIFFERENCE
	;
	
prefix
	:	op=MINUS
	|	op=COUNT
	|	op=RAN
	|	op=DOM
	|	op=TAIL
	|	op=HEAD
	|	op=REV
	|	op=LAST
	|	op=FRONT
	|	op=ITEMS
	;
	
postfix
	:	NOT // relation inversion				// TODO - not in mapping
	|	'*'	// reflexive-transitive closure		// TODO - not in mapping
	|	'+'	// transitive closure				// TODO - not in mapping
	;


featureOrFunctionCall
	:	id (ATTR_CALL id)+														# attributeCall
	|	SUCC LPARA idOrNumber RPARA												# successor
	|   id LPARA featureOrFunctionCall (COMMA featureOrFunctionCall)* RPARA		# functionReference
	|	idOrNumber																# featureOrNumber
	;

idOrNumber
	:	id
	|	number
	;

underlinedId
	:	UNDERLINE id UNDERLINE
	;

id
	:	ID DECORATION?
	;

number
	:	type=INT
	|	type=FLOAT
	;
	
predefinedType
	: op=NAT
	| op=PNAT
	| op=INTEGER
	| op=BOOL
	| op=REAL
	| op=CHAR
	;	

DECORATION
	:	PRIME
	|	QUEST
	|	EXCL
	;

CLASS		: 	'class';
CONST		: 	'const';
VISIBLE		:	'visible';
INHERITS	:	'inherits';
INIT		:	'INIT';
STATE		:	'state';
DELTA		:	'delta';
FORALL		:	'forall';
EXISTS		:	'exists';
EXISTS_1	:	'exists1';
IF			:	'if';
THEN		:	'then';
ELSE		:	'else';
SELF		:	'self';

// power set expression operators
POWER 	:	'!P';
POWER1 	:	'!P1';	// non empty power-set
ID_REL 	:	'id';	// identity relation
FINITE 	:	'!F';	// finite set
FINITE1 :	'!F1';	// finite non-empty set

SEQ		:	'seq';	// sequence
SEQ1	:	'seq1';	// non-empty sequence
ISEQ	:	'iseq';	// injective sequence, no values appear twice
BAG		:	'bag';	// bag
EMPTYSET	:	'{}';	// empty set
EMPTYBAG	:	'|[]|';	// empty bag

// relation and function symbols
RELATION	:	'<-->';		// relation
PART_FUNC	:	'-|->';		// partial function
TOT_FUNC	:	'--->';		// total function
PART_INJ	:	'>-|->';	// partial injection
TOT_INJ		:	'>--->';	// total injection
PART_SUR	:	'-|->>';	// partial surjection
TOT_SUR		:	'--->>';	// total surjection
BIJEC		:	'>-->>';	// bijection
F_PART_FUNC	:	'-||->';	// finite partial function
F_PART_INJ	:	'>-||->';	// finite partial injection
MAPLET		:	'|->';		// maplet

// operations on relations/functions
RAN		:	'ran';		// Range
DOM		:	'dom';		// Domain

// comparison
EQUALS	:	'=';		// equals
NEQUALS	:	'~='; 		// equals not
LT		:	'<';		// less than
LTE		:	'<=';		// less than or equal
GT		:	'>';		// greater than
GTE		:	'>=';		// greater than or equal

// arithmetic operators
MULT	:	'*';
DIV		:	'/';
INT_DIV	:	'div';		// integer division
MOD		:	'mod';
PLUS	:	'+';
MINUS	:	'-';

// set comparison
ELEM		:	'in';		// is element in
NELEM		:	'~in';		// is not element in
SUBSET		:	'<<=';		// is subset of
STR_SUBSET 	:	'<<';	// is a strict subset

// set restriction
RAN_AR		:	'||>';
DOM_AR		:	'<||';
RAN_RESTR	:	'|>';
DOM_RESTR	:	'<|';

PREFIX		:	'prefix';	// prefix relation a is prefix of sequence b
SUFFIX		:	'suffix';	// suffix relation a is suffix of sequence b
IN_SEQ		:	'inseq';	// segment relation a is part of sequence b

IN_BAG		:	'inbag';	// bag membership
SUBBAG		:	'subbag';	// sub-bag relation
MULTIPLICITY
	:	'(#)';
SCALING		:	'(><)';
BAG_UNION
	:	'|+|';
BAG_DIFFERENCE
	:	'|-|';
ITEMS
	:	'items';
	
// set operations
UNION 		:	'++'; 	// set union or bag union
DIFFERENCE	:	'\\';	// set difference or bag difference
INTERSECT	:	'**';	// set intersection
OVERRIDE	:	'>O<';	// overriding operator
COUNT		:	'#';	// unary set operation count - number of elements in the set
CONCATENATE
	:	'+^+';			// sequence concatenation

RANGE		:	'..';	// range operation, creates a set containing values from left to right operand

// sequence operations
TAIL	:	'tail';		// tail of a sequence
HEAD	:	'head';		// head of a sequence
LAST	:	'last';		// last elem of a sequence
FRONT	:	'front';	// front elements of a sequence (all elements except last)
REV		:	'rev';		// reverse of a sequence
EXTRACT
	:	'extract';		// extraction
FILTER
	:	'filter';		// filtering

// sequence or set operations
MIN	:	'min';
MAX	:	'max';
SUM	:	'sum';

// operations on numbers
SUCC:	 'succ';


// local definition symbols
ISDEF		:	'^=';	// for operationExpressionDef
FTDEF 		:	'::=';	// for freeTypeDefinition
ABBRDEF		:	'==';	// for abbreviationDefinitions
	

// operation expression symbols
DSEQ	:	'(0/9)';	// distributed sequential composition
DNCH	:	'([])';		// distributed non-deterministic choice
DAND	:	'(&&)';		// distributed conjunction

DOT		:	'@';		// scope enrichment and used for quantified expressions
SEQ_OP	:	'0/9';		// sequential composition operator
NCH		:	'[]';		// non-deterministic choice, same symbol used for empty sequence
APAR	:	'||!';		// associative parallel composition
PAR		:	'||';		// parallel composition
AND		:	'&&';

// boolean operators
NOT		:	'~';
EQUIV	:	'<=>';
IMPL	:	'=>';
OR		:	'or';
CONJ	:	'and';

TRUE	: 	'true'|'TRUE';
FALSE	: 	'false'|'FALSE';
	

CLASS_HIER	:	'|v';		// class hierarchy to declare a type as a class hierarchy union type (could be type or any subtype)
OBJ_CONTAIN :	'((C))';

// parentheses, brackets...
LCURLY 	:	'{';
RCURLY 	:	'}';
LPARA	:	'(';
RPARA	:	')';
LLBRACK
	:	'|[';
RRBRACK
	:	']|';
LBRACK	:	'[';
RBRACK	:	']';

// statement separators...
SEMI: ';';
COMMA: ',';
COLON: ':';

// predefined data types
PNAT	:	'!N1';
NAT		:   '!N';
INTEGER	:	'!Z';
BOOL	:	'!B';
REAL	:	'!R';
CHAR	: 	'!C';

// identifiers may have underscores, but not in the beginning or the end
ID: ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_'('a'..'z'|'A'..'Z'|'0'..'9'))* ;

PRIME
	:	'\'';
QUEST
	:	'?';
EXCL:	'!';

ATTR_CALL
	:	'.';	
	
UNDERLINE
	:	'_';

// integers
INT		: ('1'..'9')('0'..'9')* | '0';
FLOAT	: ('0'..'9')+'.'('0'..'9')+;

COMMENT :	'/*' .*? '*/' 			-> channel(HIDDEN);
	
SL_COMMENT
		:	'//' .*? '\r'? '\n' 	-> channel(HIDDEN);

WS: (	' ' 
     | '\t' 
     | '\r' 
     | '\n'
     )+
     -> channel(HIDDEN)
   ;
