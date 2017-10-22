// Generated from at/ac/tuwien/oz/parser/OZ.g4 by ANTLR 4.7

package at.ac.tuwien.oz.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class OZParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, DECORATION=3, CLASS=4, CONST=5, VISIBLE=6, INHERITS=7, 
		INIT=8, STATE=9, DELTA=10, FORALL=11, EXISTS=12, EXISTS_1=13, IF=14, THEN=15, 
		ELSE=16, SELF=17, POWER=18, POWER1=19, ID_REL=20, FINITE=21, FINITE1=22, 
		SEQ=23, SEQ1=24, ISEQ=25, BAG=26, EMPTYSET=27, EMPTYBAG=28, RELATION=29, 
		PART_FUNC=30, TOT_FUNC=31, PART_INJ=32, TOT_INJ=33, PART_SUR=34, TOT_SUR=35, 
		BIJEC=36, F_PART_FUNC=37, F_PART_INJ=38, MAPLET=39, RAN=40, DOM=41, EQUALS=42, 
		NEQUALS=43, LT=44, LTE=45, GT=46, GTE=47, MULT=48, DIV=49, INT_DIV=50, 
		MOD=51, PLUS=52, MINUS=53, ELEM=54, NELEM=55, SUBSET=56, STR_SUBSET=57, 
		RAN_AR=58, DOM_AR=59, RAN_RESTR=60, DOM_RESTR=61, PREFIX=62, SUFFIX=63, 
		IN_SEQ=64, IN_BAG=65, SUBBAG=66, MULTIPLICITY=67, SCALING=68, BAG_UNION=69, 
		BAG_DIFFERENCE=70, ITEMS=71, UNION=72, DIFFERENCE=73, INTERSECT=74, OVERRIDE=75, 
		COUNT=76, CONCATENATE=77, RANGE=78, TAIL=79, HEAD=80, LAST=81, FRONT=82, 
		REV=83, EXTRACT=84, FILTER=85, MIN=86, MAX=87, SUM=88, SUCC=89, ISDEF=90, 
		FTDEF=91, ABBRDEF=92, DSEQ=93, DNCH=94, DAND=95, DOT=96, SEQ_OP=97, NCH=98, 
		APAR=99, PAR=100, AND=101, NOT=102, EQUIV=103, IMPL=104, OR=105, CONJ=106, 
		TRUE=107, FALSE=108, CLASS_HIER=109, OBJ_CONTAIN=110, LCURLY=111, RCURLY=112, 
		LPARA=113, RPARA=114, LLBRACK=115, RRBRACK=116, LBRACK=117, RBRACK=118, 
		SEMI=119, COMMA=120, COLON=121, PNAT=122, NAT=123, INTEGER=124, BOOL=125, 
		REAL=126, CHAR=127, ID=128, PRIME=129, QUEST=130, EXCL=131, ATTR_CALL=132, 
		UNDERLINE=133, INT=134, FLOAT=135, COMMENT=136, SL_COMMENT=137, WS=138;
	public static final int
		RULE_program = 0, RULE_definition = 1, RULE_classDefinition = 2, RULE_visibilityList = 3, 
		RULE_feature = 4, RULE_inheritedClassList = 5, RULE_classDes = 6, RULE_localDefinitionList = 7, 
		RULE_localDefinition = 8, RULE_abbreviationDefinition = 9, RULE_givenTypeDefinition = 10, 
		RULE_freeTypeDefinition = 11, RULE_axiomaticDefinition = 12, RULE_state = 13, 
		RULE_primary = 14, RULE_secondary = 15, RULE_initialState = 16, RULE_operationList = 17, 
		RULE_operationSchemaDef = 18, RULE_deltalist = 19, RULE_operationExpressionDef = 20, 
		RULE_operationExpression = 21, RULE_opExpression = 22, RULE_opExprAtom = 23, 
		RULE_caller = 24, RULE_schemaText = 25, RULE_schemaDeclarationList = 26, 
		RULE_renaming = 27, RULE_renamePair = 28, RULE_declarationList = 29, RULE_declaration = 30, 
		RULE_declarationNameList = 31, RULE_predicateList = 32, RULE_predicate = 33, 
		RULE_simplePredicate = 34, RULE_predicateAtom = 35, RULE_expression = 36, 
		RULE_genActuals = 37, RULE_formalParameters = 38, RULE_powerSetOp = 39, 
		RULE_infixRelationOp = 40, RULE_infixComparisonOp = 41, RULE_setOp = 42, 
		RULE_prefix = 43, RULE_postfix = 44, RULE_featureOrFunctionCall = 45, 
		RULE_idOrNumber = 46, RULE_underlinedId = 47, RULE_id = 48, RULE_number = 49, 
		RULE_predefinedType = 50;
	public static final String[] ruleNames = {
		"program", "definition", "classDefinition", "visibilityList", "feature", 
		"inheritedClassList", "classDes", "localDefinitionList", "localDefinition", 
		"abbreviationDefinition", "givenTypeDefinition", "freeTypeDefinition", 
		"axiomaticDefinition", "state", "primary", "secondary", "initialState", 
		"operationList", "operationSchemaDef", "deltalist", "operationExpressionDef", 
		"operationExpression", "opExpression", "opExprAtom", "caller", "schemaText", 
		"schemaDeclarationList", "renaming", "renamePair", "declarationList", 
		"declaration", "declarationNameList", "predicateList", "predicate", "simplePredicate", 
		"predicateAtom", "expression", "genActuals", "formalParameters", "powerSetOp", 
		"infixRelationOp", "infixComparisonOp", "setOp", "prefix", "postfix", 
		"featureOrFunctionCall", "idOrNumber", "underlinedId", "id", "number", 
		"predefinedType"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'|'", "'><'", null, "'class'", "'const'", "'visible'", "'inherits'", 
		"'INIT'", "'state'", "'delta'", "'forall'", "'exists'", "'exists1'", "'if'", 
		"'then'", "'else'", "'self'", "'!P'", "'!P1'", "'id'", "'!F'", "'!F1'", 
		"'seq'", "'seq1'", "'iseq'", "'bag'", "'{}'", "'|[]|'", "'<-->'", "'-|->'", 
		"'--->'", "'>-|->'", "'>--->'", "'-|->>'", "'--->>'", "'>-->>'", "'-||->'", 
		"'>-||->'", "'|->'", "'ran'", "'dom'", "'='", "'~='", "'<'", "'<='", "'>'", 
		"'>='", "'*'", "'/'", "'div'", "'mod'", "'+'", "'-'", "'in'", "'~in'", 
		"'<<='", "'<<'", "'||>'", "'<||'", "'|>'", "'<|'", "'prefix'", "'suffix'", 
		"'inseq'", "'inbag'", "'subbag'", "'(#)'", "'(><)'", "'|+|'", "'|-|'", 
		"'items'", "'++'", "'\\'", "'**'", "'>O<'", "'#'", "'+^+'", "'..'", "'tail'", 
		"'head'", "'last'", "'front'", "'rev'", "'extract'", "'filter'", "'min'", 
		"'max'", "'sum'", "'succ'", "'^='", "'::='", "'=='", "'(0/9)'", "'([])'", 
		"'(&&)'", "'@'", "'0/9'", "'[]'", "'||!'", "'||'", "'&&'", "'~'", "'<=>'", 
		"'=>'", "'or'", "'and'", null, null, "'|v'", "'((C))'", "'{'", "'}'", 
		"'('", "')'", "'|['", "']|'", "'['", "']'", "';'", "','", "':'", "'!N1'", 
		"'!N'", "'!Z'", "'!B'", "'!R'", "'!C'", null, "'''", "'?'", "'!'", "'.'", 
		"'_'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "DECORATION", "CLASS", "CONST", "VISIBLE", "INHERITS", 
		"INIT", "STATE", "DELTA", "FORALL", "EXISTS", "EXISTS_1", "IF", "THEN", 
		"ELSE", "SELF", "POWER", "POWER1", "ID_REL", "FINITE", "FINITE1", "SEQ", 
		"SEQ1", "ISEQ", "BAG", "EMPTYSET", "EMPTYBAG", "RELATION", "PART_FUNC", 
		"TOT_FUNC", "PART_INJ", "TOT_INJ", "PART_SUR", "TOT_SUR", "BIJEC", "F_PART_FUNC", 
		"F_PART_INJ", "MAPLET", "RAN", "DOM", "EQUALS", "NEQUALS", "LT", "LTE", 
		"GT", "GTE", "MULT", "DIV", "INT_DIV", "MOD", "PLUS", "MINUS", "ELEM", 
		"NELEM", "SUBSET", "STR_SUBSET", "RAN_AR", "DOM_AR", "RAN_RESTR", "DOM_RESTR", 
		"PREFIX", "SUFFIX", "IN_SEQ", "IN_BAG", "SUBBAG", "MULTIPLICITY", "SCALING", 
		"BAG_UNION", "BAG_DIFFERENCE", "ITEMS", "UNION", "DIFFERENCE", "INTERSECT", 
		"OVERRIDE", "COUNT", "CONCATENATE", "RANGE", "TAIL", "HEAD", "LAST", "FRONT", 
		"REV", "EXTRACT", "FILTER", "MIN", "MAX", "SUM", "SUCC", "ISDEF", "FTDEF", 
		"ABBRDEF", "DSEQ", "DNCH", "DAND", "DOT", "SEQ_OP", "NCH", "APAR", "PAR", 
		"AND", "NOT", "EQUIV", "IMPL", "OR", "CONJ", "TRUE", "FALSE", "CLASS_HIER", 
		"OBJ_CONTAIN", "LCURLY", "RCURLY", "LPARA", "RPARA", "LLBRACK", "RRBRACK", 
		"LBRACK", "RBRACK", "SEMI", "COMMA", "COLON", "PNAT", "NAT", "INTEGER", 
		"BOOL", "REAL", "CHAR", "ID", "PRIME", "QUEST", "EXCL", "ATTR_CALL", "UNDERLINE", 
		"INT", "FLOAT", "COMMENT", "SL_COMMENT", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "OZ.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public OZParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public List<DefinitionContext> definition() {
			return getRuleContexts(DefinitionContext.class);
		}
		public DefinitionContext definition(int i) {
			return getRuleContext(DefinitionContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(102);
				definition();
				}
				}
				setState(105); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CLASS || _la==CONST || _la==LBRACK || _la==ID );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefinitionContext extends ParserRuleContext {
		public DefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_definition; }
	 
		public DefinitionContext() { }
		public void copyFrom(DefinitionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LocalDefContext extends DefinitionContext {
		public LocalDefinitionContext localDefinition() {
			return getRuleContext(LocalDefinitionContext.class,0);
		}
		public LocalDefContext(DefinitionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterLocalDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitLocalDef(this);
		}
	}
	public static class ClassDefContext extends DefinitionContext {
		public ClassDefinitionContext classDefinition() {
			return getRuleContext(ClassDefinitionContext.class,0);
		}
		public ClassDefContext(DefinitionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterClassDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitClassDef(this);
		}
	}

	public final DefinitionContext definition() throws RecognitionException {
		DefinitionContext _localctx = new DefinitionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_definition);
		try {
			setState(109);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CONST:
			case LBRACK:
			case ID:
				_localctx = new LocalDefContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(107);
				localDefinition();
				}
				break;
			case CLASS:
				_localctx = new ClassDefContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(108);
				classDefinition();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDefinitionContext extends ParserRuleContext {
		public TerminalNode CLASS() { return getToken(OZParser.CLASS, 0); }
		public TerminalNode ID() { return getToken(OZParser.ID, 0); }
		public TerminalNode LCURLY() { return getToken(OZParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(OZParser.RCURLY, 0); }
		public FormalParametersContext formalParameters() {
			return getRuleContext(FormalParametersContext.class,0);
		}
		public VisibilityListContext visibilityList() {
			return getRuleContext(VisibilityListContext.class,0);
		}
		public InheritedClassListContext inheritedClassList() {
			return getRuleContext(InheritedClassListContext.class,0);
		}
		public LocalDefinitionListContext localDefinitionList() {
			return getRuleContext(LocalDefinitionListContext.class,0);
		}
		public StateContext state() {
			return getRuleContext(StateContext.class,0);
		}
		public InitialStateContext initialState() {
			return getRuleContext(InitialStateContext.class,0);
		}
		public OperationListContext operationList() {
			return getRuleContext(OperationListContext.class,0);
		}
		public ClassDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterClassDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitClassDefinition(this);
		}
	}

	public final ClassDefinitionContext classDefinition() throws RecognitionException {
		ClassDefinitionContext _localctx = new ClassDefinitionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_classDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			match(CLASS);
			setState(112);
			match(ID);
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACK) {
				{
				setState(113);
				formalParameters();
				}
			}

			setState(116);
			match(LCURLY);
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==VISIBLE) {
				{
				setState(117);
				visibilityList();
				}
			}

			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==INHERITS) {
				{
				setState(120);
				inheritedClassList();
				}
			}

			setState(124);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(123);
				localDefinitionList();
				}
				break;
			}
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STATE) {
				{
				setState(126);
				state();
				}
			}

			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==INIT) {
				{
				setState(129);
				initialState();
				}
			}

			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(132);
				operationList();
				}
			}

			setState(135);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VisibilityListContext extends ParserRuleContext {
		public TerminalNode VISIBLE() { return getToken(OZParser.VISIBLE, 0); }
		public TerminalNode LPARA() { return getToken(OZParser.LPARA, 0); }
		public List<FeatureContext> feature() {
			return getRuleContexts(FeatureContext.class);
		}
		public FeatureContext feature(int i) {
			return getRuleContext(FeatureContext.class,i);
		}
		public TerminalNode RPARA() { return getToken(OZParser.RPARA, 0); }
		public List<TerminalNode> COMMA() { return getTokens(OZParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(OZParser.COMMA, i);
		}
		public VisibilityListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_visibilityList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterVisibilityList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitVisibilityList(this);
		}
	}

	public final VisibilityListContext visibilityList() throws RecognitionException {
		VisibilityListContext _localctx = new VisibilityListContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_visibilityList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			match(VISIBLE);
			setState(138);
			match(LPARA);
			setState(139);
			feature();
			setState(144);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(140);
				match(COMMA);
				setState(141);
				feature();
				}
				}
				setState(146);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(147);
			match(RPARA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FeatureContext extends ParserRuleContext {
		public TerminalNode INIT() { return getToken(OZParser.INIT, 0); }
		public TerminalNode ID() { return getToken(OZParser.ID, 0); }
		public FeatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_feature; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterFeature(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitFeature(this);
		}
	}

	public final FeatureContext feature() throws RecognitionException {
		FeatureContext _localctx = new FeatureContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_feature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			_la = _input.LA(1);
			if ( !(_la==INIT || _la==ID) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InheritedClassListContext extends ParserRuleContext {
		public TerminalNode INHERITS() { return getToken(OZParser.INHERITS, 0); }
		public TerminalNode LCURLY() { return getToken(OZParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(OZParser.RCURLY, 0); }
		public List<ClassDesContext> classDes() {
			return getRuleContexts(ClassDesContext.class);
		}
		public ClassDesContext classDes(int i) {
			return getRuleContext(ClassDesContext.class,i);
		}
		public List<TerminalNode> SEMI() { return getTokens(OZParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(OZParser.SEMI, i);
		}
		public InheritedClassListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inheritedClassList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterInheritedClassList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitInheritedClassList(this);
		}
	}

	public final InheritedClassListContext inheritedClassList() throws RecognitionException {
		InheritedClassListContext _localctx = new InheritedClassListContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_inheritedClassList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			match(INHERITS);
			setState(152);
			match(LCURLY);
			setState(156); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(153);
				classDes();
				setState(154);
				match(SEMI);
				}
				}
				setState(158); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ID );
			setState(160);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDesContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(OZParser.ID, 0); }
		public GenActualsContext genActuals() {
			return getRuleContext(GenActualsContext.class,0);
		}
		public RenamingContext renaming() {
			return getRuleContext(RenamingContext.class,0);
		}
		public ClassDesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDes; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterClassDes(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitClassDes(this);
		}
	}

	public final ClassDesContext classDes() throws RecognitionException {
		ClassDesContext _localctx = new ClassDesContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_classDes);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			match(ID);
			setState(164);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(163);
				genActuals();
				}
				break;
			}
			setState(167);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACK) {
				{
				setState(166);
				renaming();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LocalDefinitionListContext extends ParserRuleContext {
		public List<LocalDefinitionContext> localDefinition() {
			return getRuleContexts(LocalDefinitionContext.class);
		}
		public LocalDefinitionContext localDefinition(int i) {
			return getRuleContext(LocalDefinitionContext.class,i);
		}
		public LocalDefinitionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_localDefinitionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterLocalDefinitionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitLocalDefinitionList(this);
		}
	}

	public final LocalDefinitionListContext localDefinitionList() throws RecognitionException {
		LocalDefinitionListContext _localctx = new LocalDefinitionListContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_localDefinitionList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(170); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(169);
					localDefinition();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(172); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LocalDefinitionContext extends ParserRuleContext {
		public GivenTypeDefinitionContext givenTypeDefinition() {
			return getRuleContext(GivenTypeDefinitionContext.class,0);
		}
		public AxiomaticDefinitionContext axiomaticDefinition() {
			return getRuleContext(AxiomaticDefinitionContext.class,0);
		}
		public AbbreviationDefinitionContext abbreviationDefinition() {
			return getRuleContext(AbbreviationDefinitionContext.class,0);
		}
		public FreeTypeDefinitionContext freeTypeDefinition() {
			return getRuleContext(FreeTypeDefinitionContext.class,0);
		}
		public LocalDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_localDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterLocalDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitLocalDefinition(this);
		}
	}

	public final LocalDefinitionContext localDefinition() throws RecognitionException {
		LocalDefinitionContext _localctx = new LocalDefinitionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_localDefinition);
		try {
			setState(178);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(174);
				givenTypeDefinition();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(175);
				axiomaticDefinition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(176);
				abbreviationDefinition();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(177);
				freeTypeDefinition();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AbbreviationDefinitionContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(OZParser.ID, 0); }
		public TerminalNode ABBRDEF() { return getToken(OZParser.ABBRDEF, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FormalParametersContext formalParameters() {
			return getRuleContext(FormalParametersContext.class,0);
		}
		public AbbreviationDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_abbreviationDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterAbbreviationDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitAbbreviationDefinition(this);
		}
	}

	public final AbbreviationDefinitionContext abbreviationDefinition() throws RecognitionException {
		AbbreviationDefinitionContext _localctx = new AbbreviationDefinitionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_abbreviationDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			match(ID);
			setState(182);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACK) {
				{
				setState(181);
				formalParameters();
				}
			}

			setState(184);
			match(ABBRDEF);
			setState(185);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GivenTypeDefinitionContext extends ParserRuleContext {
		public TerminalNode LBRACK() { return getToken(OZParser.LBRACK, 0); }
		public List<TerminalNode> ID() { return getTokens(OZParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(OZParser.ID, i);
		}
		public TerminalNode RBRACK() { return getToken(OZParser.RBRACK, 0); }
		public List<TerminalNode> COMMA() { return getTokens(OZParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(OZParser.COMMA, i);
		}
		public GivenTypeDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_givenTypeDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterGivenTypeDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitGivenTypeDefinition(this);
		}
	}

	public final GivenTypeDefinitionContext givenTypeDefinition() throws RecognitionException {
		GivenTypeDefinitionContext _localctx = new GivenTypeDefinitionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_givenTypeDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			match(LBRACK);
			setState(188);
			match(ID);
			setState(193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(189);
				match(COMMA);
				setState(190);
				match(ID);
				}
				}
				setState(195);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(196);
			match(RBRACK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FreeTypeDefinitionContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(OZParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(OZParser.ID, i);
		}
		public TerminalNode FTDEF() { return getToken(OZParser.FTDEF, 0); }
		public FreeTypeDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_freeTypeDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterFreeTypeDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitFreeTypeDefinition(this);
		}
	}

	public final FreeTypeDefinitionContext freeTypeDefinition() throws RecognitionException {
		FreeTypeDefinitionContext _localctx = new FreeTypeDefinitionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_freeTypeDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			match(ID);
			setState(199);
			match(FTDEF);
			setState(200);
			match(ID);
			setState(205);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(201);
				match(T__0);
				setState(202);
				match(ID);
				}
				}
				setState(207);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AxiomaticDefinitionContext extends ParserRuleContext {
		public TerminalNode CONST() { return getToken(OZParser.CONST, 0); }
		public TerminalNode LCURLY() { return getToken(OZParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(OZParser.RCURLY, 0); }
		public DeclarationListContext declarationList() {
			return getRuleContext(DeclarationListContext.class,0);
		}
		public PredicateListContext predicateList() {
			return getRuleContext(PredicateListContext.class,0);
		}
		public AxiomaticDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_axiomaticDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterAxiomaticDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitAxiomaticDefinition(this);
		}
	}

	public final AxiomaticDefinitionContext axiomaticDefinition() throws RecognitionException {
		AxiomaticDefinitionContext _localctx = new AxiomaticDefinitionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_axiomaticDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208);
			match(CONST);
			setState(209);
			match(LCURLY);
			setState(211);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(210);
				declarationList();
				}
				break;
			}
			setState(214);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 11)) & ~0x3f) == 0 && ((1L << (_la - 11)) & ((1L << (FORALL - 11)) | (1L << (EXISTS - 11)) | (1L << (EXISTS_1 - 11)) | (1L << (SELF - 11)) | (1L << (POWER - 11)) | (1L << (POWER1 - 11)) | (1L << (FINITE - 11)) | (1L << (FINITE1 - 11)) | (1L << (SEQ - 11)) | (1L << (SEQ1 - 11)) | (1L << (ISEQ - 11)) | (1L << (BAG - 11)) | (1L << (EMPTYSET - 11)) | (1L << (EMPTYBAG - 11)) | (1L << (RAN - 11)) | (1L << (DOM - 11)) | (1L << (MINUS - 11)) | (1L << (ITEMS - 11)) | (1L << (UNION - 11)) | (1L << (INTERSECT - 11)))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (COUNT - 76)) | (1L << (CONCATENATE - 76)) | (1L << (TAIL - 76)) | (1L << (HEAD - 76)) | (1L << (LAST - 76)) | (1L << (FRONT - 76)) | (1L << (REV - 76)) | (1L << (MIN - 76)) | (1L << (MAX - 76)) | (1L << (SUM - 76)) | (1L << (SUCC - 76)) | (1L << (NCH - 76)) | (1L << (NOT - 76)) | (1L << (TRUE - 76)) | (1L << (FALSE - 76)) | (1L << (CLASS_HIER - 76)) | (1L << (LCURLY - 76)) | (1L << (LPARA - 76)) | (1L << (LLBRACK - 76)) | (1L << (LBRACK - 76)) | (1L << (PNAT - 76)) | (1L << (NAT - 76)) | (1L << (INTEGER - 76)) | (1L << (BOOL - 76)) | (1L << (REAL - 76)) | (1L << (CHAR - 76)) | (1L << (ID - 76)) | (1L << (INT - 76)) | (1L << (FLOAT - 76)))) != 0)) {
				{
				setState(213);
				predicateList();
				}
			}

			setState(216);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StateContext extends ParserRuleContext {
		public TerminalNode STATE() { return getToken(OZParser.STATE, 0); }
		public List<TerminalNode> LCURLY() { return getTokens(OZParser.LCURLY); }
		public TerminalNode LCURLY(int i) {
			return getToken(OZParser.LCURLY, i);
		}
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public List<TerminalNode> RCURLY() { return getTokens(OZParser.RCURLY); }
		public TerminalNode RCURLY(int i) {
			return getToken(OZParser.RCURLY, i);
		}
		public TerminalNode DELTA() { return getToken(OZParser.DELTA, 0); }
		public SecondaryContext secondary() {
			return getRuleContext(SecondaryContext.class,0);
		}
		public PredicateListContext predicateList() {
			return getRuleContext(PredicateListContext.class,0);
		}
		public StateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_state; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterState(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitState(this);
		}
	}

	public final StateContext state() throws RecognitionException {
		StateContext _localctx = new StateContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_state);
		int _la;
		try {
			setState(252);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(218);
				match(STATE);
				setState(219);
				match(LCURLY);
				setState(220);
				primary();
				setState(226);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==DELTA) {
					{
					setState(221);
					match(DELTA);
					setState(222);
					match(LCURLY);
					setState(223);
					secondary();
					setState(224);
					match(RCURLY);
					}
				}

				setState(229);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 11)) & ~0x3f) == 0 && ((1L << (_la - 11)) & ((1L << (FORALL - 11)) | (1L << (EXISTS - 11)) | (1L << (EXISTS_1 - 11)) | (1L << (SELF - 11)) | (1L << (POWER - 11)) | (1L << (POWER1 - 11)) | (1L << (FINITE - 11)) | (1L << (FINITE1 - 11)) | (1L << (SEQ - 11)) | (1L << (SEQ1 - 11)) | (1L << (ISEQ - 11)) | (1L << (BAG - 11)) | (1L << (EMPTYSET - 11)) | (1L << (EMPTYBAG - 11)) | (1L << (RAN - 11)) | (1L << (DOM - 11)) | (1L << (MINUS - 11)) | (1L << (ITEMS - 11)) | (1L << (UNION - 11)) | (1L << (INTERSECT - 11)))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (COUNT - 76)) | (1L << (CONCATENATE - 76)) | (1L << (TAIL - 76)) | (1L << (HEAD - 76)) | (1L << (LAST - 76)) | (1L << (FRONT - 76)) | (1L << (REV - 76)) | (1L << (MIN - 76)) | (1L << (MAX - 76)) | (1L << (SUM - 76)) | (1L << (SUCC - 76)) | (1L << (NCH - 76)) | (1L << (NOT - 76)) | (1L << (TRUE - 76)) | (1L << (FALSE - 76)) | (1L << (CLASS_HIER - 76)) | (1L << (LCURLY - 76)) | (1L << (LPARA - 76)) | (1L << (LLBRACK - 76)) | (1L << (LBRACK - 76)) | (1L << (PNAT - 76)) | (1L << (NAT - 76)) | (1L << (INTEGER - 76)) | (1L << (BOOL - 76)) | (1L << (REAL - 76)) | (1L << (CHAR - 76)) | (1L << (ID - 76)) | (1L << (INT - 76)) | (1L << (FLOAT - 76)))) != 0)) {
					{
					setState(228);
					predicateList();
					}
				}

				setState(231);
				match(RCURLY);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(233);
				match(STATE);
				setState(234);
				match(LCURLY);
				setState(235);
				match(DELTA);
				setState(236);
				match(LCURLY);
				setState(237);
				secondary();
				setState(238);
				match(RCURLY);
				setState(240);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 11)) & ~0x3f) == 0 && ((1L << (_la - 11)) & ((1L << (FORALL - 11)) | (1L << (EXISTS - 11)) | (1L << (EXISTS_1 - 11)) | (1L << (SELF - 11)) | (1L << (POWER - 11)) | (1L << (POWER1 - 11)) | (1L << (FINITE - 11)) | (1L << (FINITE1 - 11)) | (1L << (SEQ - 11)) | (1L << (SEQ1 - 11)) | (1L << (ISEQ - 11)) | (1L << (BAG - 11)) | (1L << (EMPTYSET - 11)) | (1L << (EMPTYBAG - 11)) | (1L << (RAN - 11)) | (1L << (DOM - 11)) | (1L << (MINUS - 11)) | (1L << (ITEMS - 11)) | (1L << (UNION - 11)) | (1L << (INTERSECT - 11)))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (COUNT - 76)) | (1L << (CONCATENATE - 76)) | (1L << (TAIL - 76)) | (1L << (HEAD - 76)) | (1L << (LAST - 76)) | (1L << (FRONT - 76)) | (1L << (REV - 76)) | (1L << (MIN - 76)) | (1L << (MAX - 76)) | (1L << (SUM - 76)) | (1L << (SUCC - 76)) | (1L << (NCH - 76)) | (1L << (NOT - 76)) | (1L << (TRUE - 76)) | (1L << (FALSE - 76)) | (1L << (CLASS_HIER - 76)) | (1L << (LCURLY - 76)) | (1L << (LPARA - 76)) | (1L << (LLBRACK - 76)) | (1L << (LBRACK - 76)) | (1L << (PNAT - 76)) | (1L << (NAT - 76)) | (1L << (INTEGER - 76)) | (1L << (BOOL - 76)) | (1L << (REAL - 76)) | (1L << (CHAR - 76)) | (1L << (ID - 76)) | (1L << (INT - 76)) | (1L << (FLOAT - 76)))) != 0)) {
					{
					setState(239);
					predicateList();
					}
				}

				setState(242);
				match(RCURLY);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(244);
				match(STATE);
				setState(245);
				match(LCURLY);
				setState(246);
				predicateList();
				setState(247);
				match(RCURLY);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(249);
				match(STATE);
				setState(250);
				match(LCURLY);
				setState(251);
				match(RCURLY);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryContext extends ParserRuleContext {
		public DeclarationListContext declarationList() {
			return getRuleContext(DeclarationListContext.class,0);
		}
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitPrimary(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_primary);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			declarationList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SecondaryContext extends ParserRuleContext {
		public DeclarationListContext declarationList() {
			return getRuleContext(DeclarationListContext.class,0);
		}
		public SecondaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_secondary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterSecondary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitSecondary(this);
		}
	}

	public final SecondaryContext secondary() throws RecognitionException {
		SecondaryContext _localctx = new SecondaryContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_secondary);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			declarationList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitialStateContext extends ParserRuleContext {
		public TerminalNode INIT() { return getToken(OZParser.INIT, 0); }
		public TerminalNode LCURLY() { return getToken(OZParser.LCURLY, 0); }
		public PredicateListContext predicateList() {
			return getRuleContext(PredicateListContext.class,0);
		}
		public TerminalNode RCURLY() { return getToken(OZParser.RCURLY, 0); }
		public InitialStateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initialState; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterInitialState(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitInitialState(this);
		}
	}

	public final InitialStateContext initialState() throws RecognitionException {
		InitialStateContext _localctx = new InitialStateContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_initialState);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(258);
			match(INIT);
			setState(259);
			match(LCURLY);
			setState(260);
			predicateList();
			setState(261);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperationListContext extends ParserRuleContext {
		public List<OperationSchemaDefContext> operationSchemaDef() {
			return getRuleContexts(OperationSchemaDefContext.class);
		}
		public OperationSchemaDefContext operationSchemaDef(int i) {
			return getRuleContext(OperationSchemaDefContext.class,i);
		}
		public List<OperationExpressionDefContext> operationExpressionDef() {
			return getRuleContexts(OperationExpressionDefContext.class);
		}
		public OperationExpressionDefContext operationExpressionDef(int i) {
			return getRuleContext(OperationExpressionDefContext.class,i);
		}
		public OperationListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operationList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterOperationList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitOperationList(this);
		}
	}

	public final OperationListContext operationList() throws RecognitionException {
		OperationListContext _localctx = new OperationListContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_operationList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(265); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(265);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
				case 1:
					{
					setState(263);
					operationSchemaDef();
					}
					break;
				case 2:
					{
					setState(264);
					operationExpressionDef();
					}
					break;
				}
				}
				setState(267); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ID );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperationSchemaDefContext extends ParserRuleContext {
		public Token operationName;
		public TerminalNode LCURLY() { return getToken(OZParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(OZParser.RCURLY, 0); }
		public TerminalNode ID() { return getToken(OZParser.ID, 0); }
		public DeltalistContext deltalist() {
			return getRuleContext(DeltalistContext.class,0);
		}
		public DeclarationListContext declarationList() {
			return getRuleContext(DeclarationListContext.class,0);
		}
		public PredicateListContext predicateList() {
			return getRuleContext(PredicateListContext.class,0);
		}
		public OperationSchemaDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operationSchemaDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterOperationSchemaDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitOperationSchemaDef(this);
		}
	}

	public final OperationSchemaDefContext operationSchemaDef() throws RecognitionException {
		OperationSchemaDefContext _localctx = new OperationSchemaDefContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_operationSchemaDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(269);
			((OperationSchemaDefContext)_localctx).operationName = match(ID);
			setState(270);
			match(LCURLY);
			setState(272);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DELTA) {
				{
				setState(271);
				deltalist();
				}
			}

			setState(275);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				{
				setState(274);
				declarationList();
				}
				break;
			}
			setState(278);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 11)) & ~0x3f) == 0 && ((1L << (_la - 11)) & ((1L << (FORALL - 11)) | (1L << (EXISTS - 11)) | (1L << (EXISTS_1 - 11)) | (1L << (SELF - 11)) | (1L << (POWER - 11)) | (1L << (POWER1 - 11)) | (1L << (FINITE - 11)) | (1L << (FINITE1 - 11)) | (1L << (SEQ - 11)) | (1L << (SEQ1 - 11)) | (1L << (ISEQ - 11)) | (1L << (BAG - 11)) | (1L << (EMPTYSET - 11)) | (1L << (EMPTYBAG - 11)) | (1L << (RAN - 11)) | (1L << (DOM - 11)) | (1L << (MINUS - 11)) | (1L << (ITEMS - 11)) | (1L << (UNION - 11)) | (1L << (INTERSECT - 11)))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (COUNT - 76)) | (1L << (CONCATENATE - 76)) | (1L << (TAIL - 76)) | (1L << (HEAD - 76)) | (1L << (LAST - 76)) | (1L << (FRONT - 76)) | (1L << (REV - 76)) | (1L << (MIN - 76)) | (1L << (MAX - 76)) | (1L << (SUM - 76)) | (1L << (SUCC - 76)) | (1L << (NCH - 76)) | (1L << (NOT - 76)) | (1L << (TRUE - 76)) | (1L << (FALSE - 76)) | (1L << (CLASS_HIER - 76)) | (1L << (LCURLY - 76)) | (1L << (LPARA - 76)) | (1L << (LLBRACK - 76)) | (1L << (LBRACK - 76)) | (1L << (PNAT - 76)) | (1L << (NAT - 76)) | (1L << (INTEGER - 76)) | (1L << (BOOL - 76)) | (1L << (REAL - 76)) | (1L << (CHAR - 76)) | (1L << (ID - 76)) | (1L << (INT - 76)) | (1L << (FLOAT - 76)))) != 0)) {
				{
				setState(277);
				predicateList();
				}
			}

			setState(280);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeltalistContext extends ParserRuleContext {
		public TerminalNode DELTA() { return getToken(OZParser.DELTA, 0); }
		public TerminalNode LPARA() { return getToken(OZParser.LPARA, 0); }
		public List<TerminalNode> ID() { return getTokens(OZParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(OZParser.ID, i);
		}
		public TerminalNode RPARA() { return getToken(OZParser.RPARA, 0); }
		public List<TerminalNode> COMMA() { return getTokens(OZParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(OZParser.COMMA, i);
		}
		public DeltalistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deltalist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterDeltalist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitDeltalist(this);
		}
	}

	public final DeltalistContext deltalist() throws RecognitionException {
		DeltalistContext _localctx = new DeltalistContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_deltalist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			match(DELTA);
			setState(283);
			match(LPARA);
			setState(284);
			match(ID);
			setState(289);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(285);
				match(COMMA);
				setState(286);
				match(ID);
				}
				}
				setState(291);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(292);
			match(RPARA);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperationExpressionDefContext extends ParserRuleContext {
		public Token operationName;
		public TerminalNode ISDEF() { return getToken(OZParser.ISDEF, 0); }
		public OperationExpressionContext operationExpression() {
			return getRuleContext(OperationExpressionContext.class,0);
		}
		public TerminalNode ID() { return getToken(OZParser.ID, 0); }
		public TerminalNode SEMI() { return getToken(OZParser.SEMI, 0); }
		public OperationExpressionDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operationExpressionDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterOperationExpressionDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitOperationExpressionDef(this);
		}
	}

	public final OperationExpressionDefContext operationExpressionDef() throws RecognitionException {
		OperationExpressionDefContext _localctx = new OperationExpressionDefContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_operationExpressionDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(294);
			((OperationExpressionDefContext)_localctx).operationName = match(ID);
			setState(295);
			match(ISDEF);
			setState(296);
			operationExpression();
			setState(298);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(297);
				match(SEMI);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperationExpressionContext extends ParserRuleContext {
		public OperationExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operationExpression; }
	 
		public OperationExpressionContext() { }
		public void copyFrom(OperationExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class DistributedNonDeterminisiticChoiceContext extends OperationExpressionContext {
		public TerminalNode DNCH() { return getToken(OZParser.DNCH, 0); }
		public SchemaTextContext schemaText() {
			return getRuleContext(SchemaTextContext.class,0);
		}
		public TerminalNode DOT() { return getToken(OZParser.DOT, 0); }
		public OperationExpressionContext operationExpression() {
			return getRuleContext(OperationExpressionContext.class,0);
		}
		public DistributedNonDeterminisiticChoiceContext(OperationExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterDistributedNonDeterminisiticChoice(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitDistributedNonDeterminisiticChoice(this);
		}
	}
	public static class DistributedSequentialCompositionContext extends OperationExpressionContext {
		public TerminalNode DSEQ() { return getToken(OZParser.DSEQ, 0); }
		public SchemaTextContext schemaText() {
			return getRuleContext(SchemaTextContext.class,0);
		}
		public TerminalNode DOT() { return getToken(OZParser.DOT, 0); }
		public OperationExpressionContext operationExpression() {
			return getRuleContext(OperationExpressionContext.class,0);
		}
		public DistributedSequentialCompositionContext(OperationExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterDistributedSequentialComposition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitDistributedSequentialComposition(this);
		}
	}
	public static class DistributedConjunctionContext extends OperationExpressionContext {
		public TerminalNode DAND() { return getToken(OZParser.DAND, 0); }
		public SchemaTextContext schemaText() {
			return getRuleContext(SchemaTextContext.class,0);
		}
		public TerminalNode DOT() { return getToken(OZParser.DOT, 0); }
		public OperationExpressionContext operationExpression() {
			return getRuleContext(OperationExpressionContext.class,0);
		}
		public DistributedConjunctionContext(OperationExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterDistributedConjunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitDistributedConjunction(this);
		}
	}
	public static class SimpleOperationExpressionContext extends OperationExpressionContext {
		public OpExpressionContext opExpression() {
			return getRuleContext(OpExpressionContext.class,0);
		}
		public SimpleOperationExpressionContext(OperationExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterSimpleOperationExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitSimpleOperationExpression(this);
		}
	}

	public final OperationExpressionContext operationExpression() throws RecognitionException {
		OperationExpressionContext _localctx = new OperationExpressionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_operationExpression);
		try {
			setState(316);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DSEQ:
				_localctx = new DistributedSequentialCompositionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(300);
				match(DSEQ);
				setState(301);
				schemaText();
				setState(302);
				match(DOT);
				setState(303);
				operationExpression();
				}
				break;
			case DNCH:
				_localctx = new DistributedNonDeterminisiticChoiceContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(305);
				match(DNCH);
				setState(306);
				schemaText();
				setState(307);
				match(DOT);
				setState(308);
				operationExpression();
				}
				break;
			case DAND:
				_localctx = new DistributedConjunctionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(310);
				match(DAND);
				setState(311);
				schemaText();
				setState(312);
				match(DOT);
				setState(313);
				operationExpression();
				}
				break;
			case LPARA:
			case LBRACK:
			case ID:
				_localctx = new SimpleOperationExpressionContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(315);
				opExpression(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OpExpressionContext extends ParserRuleContext {
		public OpExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_opExpression; }
	 
		public OpExpressionContext() { }
		public void copyFrom(OpExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class OperationExpressionAtomContext extends OpExpressionContext {
		public OpExprAtomContext opExprAtom() {
			return getRuleContext(OpExprAtomContext.class,0);
		}
		public OperationExpressionAtomContext(OpExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterOperationExpressionAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitOperationExpressionAtom(this);
		}
	}
	public static class NonDeterministicChoiceContext extends OpExpressionContext {
		public OpExpressionContext left;
		public OpExpressionContext right;
		public TerminalNode NCH() { return getToken(OZParser.NCH, 0); }
		public List<OpExpressionContext> opExpression() {
			return getRuleContexts(OpExpressionContext.class);
		}
		public OpExpressionContext opExpression(int i) {
			return getRuleContext(OpExpressionContext.class,i);
		}
		public NonDeterministicChoiceContext(OpExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterNonDeterministicChoice(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitNonDeterministicChoice(this);
		}
	}
	public static class OperationConjunctionContext extends OpExpressionContext {
		public OpExpressionContext left;
		public OpExpressionContext right;
		public TerminalNode AND() { return getToken(OZParser.AND, 0); }
		public List<OpExpressionContext> opExpression() {
			return getRuleContexts(OpExpressionContext.class);
		}
		public OpExpressionContext opExpression(int i) {
			return getRuleContext(OpExpressionContext.class,i);
		}
		public OperationConjunctionContext(OpExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterOperationConjunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitOperationConjunction(this);
		}
	}
	public static class ParallelCompositionContext extends OpExpressionContext {
		public OpExpressionContext left;
		public OpExpressionContext right;
		public TerminalNode PAR() { return getToken(OZParser.PAR, 0); }
		public List<OpExpressionContext> opExpression() {
			return getRuleContexts(OpExpressionContext.class);
		}
		public OpExpressionContext opExpression(int i) {
			return getRuleContext(OpExpressionContext.class,i);
		}
		public ParallelCompositionContext(OpExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterParallelComposition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitParallelComposition(this);
		}
	}
	public static class AssociativeParallelCompositionContext extends OpExpressionContext {
		public OpExpressionContext left;
		public OpExpressionContext right;
		public TerminalNode APAR() { return getToken(OZParser.APAR, 0); }
		public List<OpExpressionContext> opExpression() {
			return getRuleContexts(OpExpressionContext.class);
		}
		public OpExpressionContext opExpression(int i) {
			return getRuleContext(OpExpressionContext.class,i);
		}
		public AssociativeParallelCompositionContext(OpExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterAssociativeParallelComposition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitAssociativeParallelComposition(this);
		}
	}
	public static class ScopeEnrichmentContext extends OpExpressionContext {
		public OpExpressionContext left;
		public OpExpressionContext right;
		public TerminalNode DOT() { return getToken(OZParser.DOT, 0); }
		public List<OpExpressionContext> opExpression() {
			return getRuleContexts(OpExpressionContext.class);
		}
		public OpExpressionContext opExpression(int i) {
			return getRuleContext(OpExpressionContext.class,i);
		}
		public ScopeEnrichmentContext(OpExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterScopeEnrichment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitScopeEnrichment(this);
		}
	}
	public static class SequentialCompositionContext extends OpExpressionContext {
		public OpExpressionContext left;
		public OpExpressionContext right;
		public TerminalNode SEQ_OP() { return getToken(OZParser.SEQ_OP, 0); }
		public List<OpExpressionContext> opExpression() {
			return getRuleContexts(OpExpressionContext.class);
		}
		public OpExpressionContext opExpression(int i) {
			return getRuleContext(OpExpressionContext.class,i);
		}
		public SequentialCompositionContext(OpExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterSequentialComposition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitSequentialComposition(this);
		}
	}

	public final OpExpressionContext opExpression() throws RecognitionException {
		return opExpression(0);
	}

	private OpExpressionContext opExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		OpExpressionContext _localctx = new OpExpressionContext(_ctx, _parentState);
		OpExpressionContext _prevctx = _localctx;
		int _startState = 44;
		enterRecursionRule(_localctx, 44, RULE_opExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new OperationExpressionAtomContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(319);
			opExprAtom();
			}
			_ctx.stop = _input.LT(-1);
			setState(341);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(339);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
					case 1:
						{
						_localctx = new OperationConjunctionContext(new OpExpressionContext(_parentctx, _parentState));
						((OperationConjunctionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_opExpression);
						setState(321);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(322);
						match(AND);
						setState(323);
						((OperationConjunctionContext)_localctx).right = opExpression(7);
						}
						break;
					case 2:
						{
						_localctx = new AssociativeParallelCompositionContext(new OpExpressionContext(_parentctx, _parentState));
						((AssociativeParallelCompositionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_opExpression);
						setState(324);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(325);
						match(APAR);
						setState(326);
						((AssociativeParallelCompositionContext)_localctx).right = opExpression(6);
						}
						break;
					case 3:
						{
						_localctx = new ParallelCompositionContext(new OpExpressionContext(_parentctx, _parentState));
						((ParallelCompositionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_opExpression);
						setState(327);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(328);
						match(PAR);
						setState(329);
						((ParallelCompositionContext)_localctx).right = opExpression(5);
						}
						break;
					case 4:
						{
						_localctx = new NonDeterministicChoiceContext(new OpExpressionContext(_parentctx, _parentState));
						((NonDeterministicChoiceContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_opExpression);
						setState(330);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(331);
						match(NCH);
						setState(332);
						((NonDeterministicChoiceContext)_localctx).right = opExpression(4);
						}
						break;
					case 5:
						{
						_localctx = new SequentialCompositionContext(new OpExpressionContext(_parentctx, _parentState));
						((SequentialCompositionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_opExpression);
						setState(333);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(334);
						match(SEQ_OP);
						setState(335);
						((SequentialCompositionContext)_localctx).right = opExpression(3);
						}
						break;
					case 6:
						{
						_localctx = new ScopeEnrichmentContext(new OpExpressionContext(_parentctx, _parentState));
						((ScopeEnrichmentContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_opExpression);
						setState(336);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(337);
						match(DOT);
						setState(338);
						((ScopeEnrichmentContext)_localctx).right = opExpression(2);
						}
						break;
					}
					} 
				}
				setState(343);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class OpExprAtomContext extends ParserRuleContext {
		public OpExprAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_opExprAtom; }
	 
		public OpExprAtomContext() { }
		public void copyFrom(OpExprAtomContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AnonymousOperationSchemaDefContext extends OpExprAtomContext {
		public TerminalNode LBRACK() { return getToken(OZParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(OZParser.RBRACK, 0); }
		public TerminalNode DELTA() { return getToken(OZParser.DELTA, 0); }
		public TerminalNode LPARA() { return getToken(OZParser.LPARA, 0); }
		public List<TerminalNode> ID() { return getTokens(OZParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(OZParser.ID, i);
		}
		public TerminalNode RPARA() { return getToken(OZParser.RPARA, 0); }
		public DeclarationListContext declarationList() {
			return getRuleContext(DeclarationListContext.class,0);
		}
		public PredicateListContext predicateList() {
			return getRuleContext(PredicateListContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(OZParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(OZParser.COMMA, i);
		}
		public AnonymousOperationSchemaDefContext(OpExprAtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterAnonymousOperationSchemaDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitAnonymousOperationSchemaDef(this);
		}
	}
	public static class OperationPromotionContext extends OpExprAtomContext {
		public IdContext opName;
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public CallerContext caller() {
			return getRuleContext(CallerContext.class,0);
		}
		public OperationPromotionContext(OpExprAtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterOperationPromotion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitOperationPromotion(this);
		}
	}
	public static class ParenthesizedOperationExpressionContext extends OpExprAtomContext {
		public TerminalNode LPARA() { return getToken(OZParser.LPARA, 0); }
		public OperationExpressionContext operationExpression() {
			return getRuleContext(OperationExpressionContext.class,0);
		}
		public TerminalNode RPARA() { return getToken(OZParser.RPARA, 0); }
		public ParenthesizedOperationExpressionContext(OpExprAtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterParenthesizedOperationExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitParenthesizedOperationExpression(this);
		}
	}

	public final OpExprAtomContext opExprAtom() throws RecognitionException {
		OpExprAtomContext _localctx = new OpExprAtomContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_opExprAtom);
		int _la;
		try {
			setState(376);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACK:
				_localctx = new AnonymousOperationSchemaDefContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(344);
				match(LBRACK);
				setState(356);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==DELTA) {
					{
					setState(345);
					match(DELTA);
					setState(346);
					match(LPARA);
					setState(347);
					match(ID);
					setState(352);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(348);
						match(COMMA);
						setState(349);
						match(ID);
						}
						}
						setState(354);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(355);
					match(RPARA);
					}
				}

				setState(359);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
				case 1:
					{
					setState(358);
					declarationList();
					}
					break;
				}
				setState(365);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << FORALL) | (1L << EXISTS) | (1L << EXISTS_1) | (1L << SELF) | (1L << POWER) | (1L << POWER1) | (1L << FINITE) | (1L << FINITE1) | (1L << SEQ) | (1L << SEQ1) | (1L << ISEQ) | (1L << BAG) | (1L << EMPTYSET) | (1L << EMPTYBAG) | (1L << RAN) | (1L << DOM) | (1L << MINUS))) != 0) || ((((_la - 71)) & ~0x3f) == 0 && ((1L << (_la - 71)) & ((1L << (ITEMS - 71)) | (1L << (UNION - 71)) | (1L << (INTERSECT - 71)) | (1L << (COUNT - 71)) | (1L << (CONCATENATE - 71)) | (1L << (TAIL - 71)) | (1L << (HEAD - 71)) | (1L << (LAST - 71)) | (1L << (FRONT - 71)) | (1L << (REV - 71)) | (1L << (MIN - 71)) | (1L << (MAX - 71)) | (1L << (SUM - 71)) | (1L << (SUCC - 71)) | (1L << (NCH - 71)) | (1L << (NOT - 71)) | (1L << (TRUE - 71)) | (1L << (FALSE - 71)) | (1L << (CLASS_HIER - 71)) | (1L << (LCURLY - 71)) | (1L << (LPARA - 71)) | (1L << (LLBRACK - 71)) | (1L << (LBRACK - 71)) | (1L << (PNAT - 71)) | (1L << (NAT - 71)) | (1L << (INTEGER - 71)) | (1L << (BOOL - 71)) | (1L << (REAL - 71)) | (1L << (CHAR - 71)) | (1L << (ID - 71)) | (1L << (INT - 71)))) != 0) || _la==FLOAT) {
					{
					setState(362);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==T__0) {
						{
						setState(361);
						match(T__0);
						}
					}

					setState(364);
					predicateList();
					}
				}

				setState(367);
				match(RBRACK);
				}
				break;
			case ID:
				_localctx = new OperationPromotionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(369);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
				case 1:
					{
					setState(368);
					caller();
					}
					break;
				}
				setState(371);
				((OperationPromotionContext)_localctx).opName = id();
				}
				break;
			case LPARA:
				_localctx = new ParenthesizedOperationExpressionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(372);
				match(LPARA);
				setState(373);
				operationExpression();
				setState(374);
				match(RPARA);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CallerContext extends ParserRuleContext {
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public List<TerminalNode> ATTR_CALL() { return getTokens(OZParser.ATTR_CALL); }
		public TerminalNode ATTR_CALL(int i) {
			return getToken(OZParser.ATTR_CALL, i);
		}
		public CallerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caller; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterCaller(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitCaller(this);
		}
	}

	public final CallerContext caller() throws RecognitionException {
		CallerContext _localctx = new CallerContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_caller);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(381); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(378);
					id();
					setState(379);
					match(ATTR_CALL);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(383); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SchemaTextContext extends ParserRuleContext {
		public SchemaDeclarationListContext schemaDeclarationList() {
			return getRuleContext(SchemaDeclarationListContext.class,0);
		}
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public SchemaTextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_schemaText; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterSchemaText(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitSchemaText(this);
		}
	}

	public final SchemaTextContext schemaText() throws RecognitionException {
		SchemaTextContext _localctx = new SchemaTextContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_schemaText);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(385);
			schemaDeclarationList();
			setState(388);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(386);
				match(T__0);
				setState(387);
				predicate();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SchemaDeclarationListContext extends ParserRuleContext {
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public List<TerminalNode> SEMI() { return getTokens(OZParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(OZParser.SEMI, i);
		}
		public SchemaDeclarationListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_schemaDeclarationList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterSchemaDeclarationList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitSchemaDeclarationList(this);
		}
	}

	public final SchemaDeclarationListContext schemaDeclarationList() throws RecognitionException {
		SchemaDeclarationListContext _localctx = new SchemaDeclarationListContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_schemaDeclarationList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(390);
			declaration();
			setState(395);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(391);
					match(SEMI);
					setState(392);
					declaration();
					}
					} 
				}
				setState(397);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
			}
			setState(399);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(398);
				match(SEMI);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RenamingContext extends ParserRuleContext {
		public TerminalNode LBRACK() { return getToken(OZParser.LBRACK, 0); }
		public List<RenamePairContext> renamePair() {
			return getRuleContexts(RenamePairContext.class);
		}
		public RenamePairContext renamePair(int i) {
			return getRuleContext(RenamePairContext.class,i);
		}
		public TerminalNode RBRACK() { return getToken(OZParser.RBRACK, 0); }
		public List<TerminalNode> COMMA() { return getTokens(OZParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(OZParser.COMMA, i);
		}
		public RenamingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_renaming; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterRenaming(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitRenaming(this);
		}
	}

	public final RenamingContext renaming() throws RecognitionException {
		RenamingContext _localctx = new RenamingContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_renaming);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(401);
			match(LBRACK);
			setState(402);
			renamePair();
			setState(407);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(403);
				match(COMMA);
				setState(404);
				renamePair();
				}
				}
				setState(409);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(410);
			match(RBRACK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RenamePairContext extends ParserRuleContext {
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public TerminalNode DIV() { return getToken(OZParser.DIV, 0); }
		public RenamePairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_renamePair; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterRenamePair(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitRenamePair(this);
		}
	}

	public final RenamePairContext renamePair() throws RecognitionException {
		RenamePairContext _localctx = new RenamePairContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_renamePair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(412);
			id();
			setState(413);
			match(DIV);
			setState(414);
			id();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationListContext extends ParserRuleContext {
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public List<TerminalNode> SEMI() { return getTokens(OZParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(OZParser.SEMI, i);
		}
		public DeclarationListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarationList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterDeclarationList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitDeclarationList(this);
		}
	}

	public final DeclarationListContext declarationList() throws RecognitionException {
		DeclarationListContext _localctx = new DeclarationListContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_declarationList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(419); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(416);
					declaration();
					setState(417);
					match(SEMI);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(421); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public DeclarationNameListContext declarationNameList() {
			return getRuleContext(DeclarationNameListContext.class,0);
		}
		public TerminalNode COLON() { return getToken(OZParser.COLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitDeclaration(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(423);
			declarationNameList();
			setState(424);
			match(COLON);
			setState(425);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationNameListContext extends ParserRuleContext {
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(OZParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(OZParser.COMMA, i);
		}
		public DeclarationNameListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarationNameList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterDeclarationNameList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitDeclarationNameList(this);
		}
	}

	public final DeclarationNameListContext declarationNameList() throws RecognitionException {
		DeclarationNameListContext _localctx = new DeclarationNameListContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_declarationNameList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(427);
			id();
			setState(432);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(428);
				match(COMMA);
				setState(429);
				id();
				}
				}
				setState(434);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PredicateListContext extends ParserRuleContext {
		public PredicateListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicateList; }
	 
		public PredicateListContext() { }
		public void copyFrom(PredicateListContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PredicatesContext extends PredicateListContext {
		public List<PredicateContext> predicate() {
			return getRuleContexts(PredicateContext.class);
		}
		public PredicateContext predicate(int i) {
			return getRuleContext(PredicateContext.class,i);
		}
		public List<TerminalNode> SEMI() { return getTokens(OZParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(OZParser.SEMI, i);
		}
		public PredicatesContext(PredicateListContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterPredicates(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitPredicates(this);
		}
	}

	public final PredicateListContext predicateList() throws RecognitionException {
		PredicateListContext _localctx = new PredicateListContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_predicateList);
		int _la;
		try {
			int _alt;
			_localctx = new PredicatesContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(435);
			predicate();
			setState(440);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(436);
					match(SEMI);
					setState(437);
					predicate();
					}
					} 
				}
				setState(442);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
			}
			setState(444);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(443);
				match(SEMI);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PredicateContext extends ParserRuleContext {
		public PredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicate; }
	 
		public PredicateContext() { }
		public void copyFrom(PredicateContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ForallContext extends PredicateContext {
		public TerminalNode FORALL() { return getToken(OZParser.FORALL, 0); }
		public SchemaTextContext schemaText() {
			return getRuleContext(SchemaTextContext.class,0);
		}
		public TerminalNode DOT() { return getToken(OZParser.DOT, 0); }
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public ForallContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterForall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitForall(this);
		}
	}
	public static class ExistsContext extends PredicateContext {
		public TerminalNode EXISTS() { return getToken(OZParser.EXISTS, 0); }
		public SchemaTextContext schemaText() {
			return getRuleContext(SchemaTextContext.class,0);
		}
		public TerminalNode DOT() { return getToken(OZParser.DOT, 0); }
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public ExistsContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterExists(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitExists(this);
		}
	}
	public static class ExistsOneContext extends PredicateContext {
		public TerminalNode EXISTS_1() { return getToken(OZParser.EXISTS_1, 0); }
		public SchemaTextContext schemaText() {
			return getRuleContext(SchemaTextContext.class,0);
		}
		public TerminalNode DOT() { return getToken(OZParser.DOT, 0); }
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public ExistsOneContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterExistsOne(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitExistsOne(this);
		}
	}
	public static class SimpleContext extends PredicateContext {
		public SimplePredicateContext simplePredicate() {
			return getRuleContext(SimplePredicateContext.class,0);
		}
		public SimpleContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterSimple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitSimple(this);
		}
	}

	public final PredicateContext predicate() throws RecognitionException {
		PredicateContext _localctx = new PredicateContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_predicate);
		try {
			setState(462);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FORALL:
				_localctx = new ForallContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(446);
				match(FORALL);
				setState(447);
				schemaText();
				setState(448);
				match(DOT);
				setState(449);
				predicate();
				}
				break;
			case EXISTS:
				_localctx = new ExistsContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(451);
				match(EXISTS);
				setState(452);
				schemaText();
				setState(453);
				match(DOT);
				setState(454);
				predicate();
				}
				break;
			case EXISTS_1:
				_localctx = new ExistsOneContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(456);
				match(EXISTS_1);
				setState(457);
				schemaText();
				setState(458);
				match(DOT);
				setState(459);
				predicate();
				}
				break;
			case SELF:
			case POWER:
			case POWER1:
			case FINITE:
			case FINITE1:
			case SEQ:
			case SEQ1:
			case ISEQ:
			case BAG:
			case EMPTYSET:
			case EMPTYBAG:
			case RAN:
			case DOM:
			case MINUS:
			case ITEMS:
			case UNION:
			case INTERSECT:
			case COUNT:
			case CONCATENATE:
			case TAIL:
			case HEAD:
			case LAST:
			case FRONT:
			case REV:
			case MIN:
			case MAX:
			case SUM:
			case SUCC:
			case NCH:
			case NOT:
			case TRUE:
			case FALSE:
			case CLASS_HIER:
			case LCURLY:
			case LPARA:
			case LLBRACK:
			case LBRACK:
			case PNAT:
			case NAT:
			case INTEGER:
			case BOOL:
			case REAL:
			case CHAR:
			case ID:
			case INT:
			case FLOAT:
				_localctx = new SimpleContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(461);
				simplePredicate(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimplePredicateContext extends ParserRuleContext {
		public SimplePredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simplePredicate; }
	 
		public SimplePredicateContext() { }
		public void copyFrom(SimplePredicateContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class EquivalenceContext extends SimplePredicateContext {
		public SimplePredicateContext left;
		public SimplePredicateContext right;
		public TerminalNode EQUIV() { return getToken(OZParser.EQUIV, 0); }
		public List<SimplePredicateContext> simplePredicate() {
			return getRuleContexts(SimplePredicateContext.class);
		}
		public SimplePredicateContext simplePredicate(int i) {
			return getRuleContext(SimplePredicateContext.class,i);
		}
		public EquivalenceContext(SimplePredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterEquivalence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitEquivalence(this);
		}
	}
	public static class ConjunctionContext extends SimplePredicateContext {
		public SimplePredicateContext left;
		public SimplePredicateContext right;
		public TerminalNode CONJ() { return getToken(OZParser.CONJ, 0); }
		public List<SimplePredicateContext> simplePredicate() {
			return getRuleContexts(SimplePredicateContext.class);
		}
		public SimplePredicateContext simplePredicate(int i) {
			return getRuleContext(SimplePredicateContext.class,i);
		}
		public ConjunctionContext(SimplePredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterConjunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitConjunction(this);
		}
	}
	public static class DisjunctionContext extends SimplePredicateContext {
		public SimplePredicateContext left;
		public SimplePredicateContext right;
		public TerminalNode OR() { return getToken(OZParser.OR, 0); }
		public List<SimplePredicateContext> simplePredicate() {
			return getRuleContexts(SimplePredicateContext.class);
		}
		public SimplePredicateContext simplePredicate(int i) {
			return getRuleContext(SimplePredicateContext.class,i);
		}
		public DisjunctionContext(SimplePredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterDisjunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitDisjunction(this);
		}
	}
	public static class ImplicationContext extends SimplePredicateContext {
		public SimplePredicateContext left;
		public SimplePredicateContext right;
		public TerminalNode IMPL() { return getToken(OZParser.IMPL, 0); }
		public List<SimplePredicateContext> simplePredicate() {
			return getRuleContexts(SimplePredicateContext.class);
		}
		public SimplePredicateContext simplePredicate(int i) {
			return getRuleContext(SimplePredicateContext.class,i);
		}
		public ImplicationContext(SimplePredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterImplication(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitImplication(this);
		}
	}
	public static class AtomContext extends SimplePredicateContext {
		public PredicateAtomContext predicateAtom() {
			return getRuleContext(PredicateAtomContext.class,0);
		}
		public AtomContext(SimplePredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitAtom(this);
		}
	}

	public final SimplePredicateContext simplePredicate() throws RecognitionException {
		return simplePredicate(0);
	}

	private SimplePredicateContext simplePredicate(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		SimplePredicateContext _localctx = new SimplePredicateContext(_ctx, _parentState);
		SimplePredicateContext _prevctx = _localctx;
		int _startState = 68;
		enterRecursionRule(_localctx, 68, RULE_simplePredicate, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new AtomContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(465);
			predicateAtom();
			}
			_ctx.stop = _input.LT(-1);
			setState(481);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(479);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
					case 1:
						{
						_localctx = new ConjunctionContext(new SimplePredicateContext(_parentctx, _parentState));
						((ConjunctionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_simplePredicate);
						setState(467);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(468);
						match(CONJ);
						setState(469);
						((ConjunctionContext)_localctx).right = simplePredicate(6);
						}
						break;
					case 2:
						{
						_localctx = new DisjunctionContext(new SimplePredicateContext(_parentctx, _parentState));
						((DisjunctionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_simplePredicate);
						setState(470);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(471);
						match(OR);
						setState(472);
						((DisjunctionContext)_localctx).right = simplePredicate(5);
						}
						break;
					case 3:
						{
						_localctx = new ImplicationContext(new SimplePredicateContext(_parentctx, _parentState));
						((ImplicationContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_simplePredicate);
						setState(473);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(474);
						match(IMPL);
						setState(475);
						((ImplicationContext)_localctx).right = simplePredicate(4);
						}
						break;
					case 4:
						{
						_localctx = new EquivalenceContext(new SimplePredicateContext(_parentctx, _parentState));
						((EquivalenceContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_simplePredicate);
						setState(476);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(477);
						match(EQUIV);
						setState(478);
						((EquivalenceContext)_localctx).right = simplePredicate(3);
						}
						break;
					}
					} 
				}
				setState(483);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PredicateAtomContext extends ParserRuleContext {
		public PredicateAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicateAtom; }
	 
		public PredicateAtomContext() { }
		public void copyFrom(PredicateAtomContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NegationContext extends PredicateAtomContext {
		public TerminalNode NOT() { return getToken(OZParser.NOT, 0); }
		public PredicateAtomContext predicateAtom() {
			return getRuleContext(PredicateAtomContext.class,0);
		}
		public NegationContext(PredicateAtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterNegation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitNegation(this);
		}
	}
	public static class ReferenceBinaryRelationContext extends PredicateAtomContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public UnderlinedIdContext underlinedId() {
			return getRuleContext(UnderlinedIdContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ReferenceBinaryRelationContext(PredicateAtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterReferenceBinaryRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitReferenceBinaryRelation(this);
		}
	}
	public static class InitcallContext extends PredicateAtomContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode ATTR_CALL() { return getToken(OZParser.ATTR_CALL, 0); }
		public TerminalNode INIT() { return getToken(OZParser.INIT, 0); }
		public InitcallContext(PredicateAtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterInitcall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitInitcall(this);
		}
	}
	public static class TrueContext extends PredicateAtomContext {
		public TerminalNode TRUE() { return getToken(OZParser.TRUE, 0); }
		public TrueContext(PredicateAtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterTrue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitTrue(this);
		}
	}
	public static class FalseContext extends PredicateAtomContext {
		public TerminalNode FALSE() { return getToken(OZParser.FALSE, 0); }
		public FalseContext(PredicateAtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterFalse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitFalse(this);
		}
	}
	public static class ParenthesizedPredicateContext extends PredicateAtomContext {
		public TerminalNode LPARA() { return getToken(OZParser.LPARA, 0); }
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public TerminalNode RPARA() { return getToken(OZParser.RPARA, 0); }
		public ParenthesizedPredicateContext(PredicateAtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterParenthesizedPredicate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitParenthesizedPredicate(this);
		}
	}
	public static class BooleanExpressionContext extends PredicateAtomContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BooleanExpressionContext(PredicateAtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterBooleanExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitBooleanExpression(this);
		}
	}

	public final PredicateAtomContext predicateAtom() throws RecognitionException {
		PredicateAtomContext _localctx = new PredicateAtomContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_predicateAtom);
		try {
			setState(501);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				_localctx = new NegationContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(484);
				match(NOT);
				setState(485);
				predicateAtom();
				}
				break;
			case 2:
				_localctx = new InitcallContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(486);
				id();
				setState(487);
				match(ATTR_CALL);
				setState(488);
				match(INIT);
				}
				break;
			case 3:
				_localctx = new TrueContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(490);
				match(TRUE);
				}
				break;
			case 4:
				_localctx = new FalseContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(491);
				match(FALSE);
				}
				break;
			case 5:
				_localctx = new ReferenceBinaryRelationContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(492);
				((ReferenceBinaryRelationContext)_localctx).left = expression(0);
				setState(493);
				underlinedId();
				setState(494);
				((ReferenceBinaryRelationContext)_localctx).right = expression(0);
				}
				break;
			case 6:
				_localctx = new BooleanExpressionContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(496);
				expression(0);
				}
				break;
			case 7:
				_localctx = new ParenthesizedPredicateContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(497);
				match(LPARA);
				setState(498);
				predicate();
				setState(499);
				match(RPARA);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class EmptySetContext extends ExpressionContext {
		public TerminalNode EMPTYSET() { return getToken(OZParser.EMPTYSET, 0); }
		public EmptySetContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterEmptySet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitEmptySet(this);
		}
	}
	public static class AdditiveExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public Token op;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(OZParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(OZParser.MINUS, 0); }
		public AdditiveExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterAdditiveExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitAdditiveExpression(this);
		}
	}
	public static class CollectionOperationContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public SetOpContext setOp() {
			return getRuleContext(SetOpContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public CollectionOperationContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterCollectionOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitCollectionOperation(this);
		}
	}
	public static class PowerSetExpressionContext extends ExpressionContext {
		public PowerSetOpContext powerSetOp() {
			return getRuleContext(PowerSetOpContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PowerSetExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterPowerSetExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitPowerSetExpression(this);
		}
	}
	public static class MinMaxOfExpressionContext extends ExpressionContext {
		public Token op;
		public ExpressionContext e;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode MIN() { return getToken(OZParser.MIN, 0); }
		public TerminalNode MAX() { return getToken(OZParser.MAX, 0); }
		public MinMaxOfExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterMinMaxOfExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitMinMaxOfExpression(this);
		}
	}
	public static class BagContext extends ExpressionContext {
		public TerminalNode LLBRACK() { return getToken(OZParser.LLBRACK, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RRBRACK() { return getToken(OZParser.RRBRACK, 0); }
		public List<TerminalNode> COMMA() { return getTokens(OZParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(OZParser.COMMA, i);
		}
		public BagContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterBag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitBag(this);
		}
	}
	public static class SchemaSumContext extends ExpressionContext {
		public TerminalNode SUM() { return getToken(OZParser.SUM, 0); }
		public SchemaTextContext schemaText() {
			return getRuleContext(SchemaTextContext.class,0);
		}
		public TerminalNode DOT() { return getToken(OZParser.DOT, 0); }
		public FeatureOrFunctionCallContext featureOrFunctionCall() {
			return getRuleContext(FeatureOrFunctionCallContext.class,0);
		}
		public SchemaSumContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterSchemaSum(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitSchemaSum(this);
		}
	}
	public static class CartesianContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public CartesianContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterCartesian(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitCartesian(this);
		}
	}
	public static class GenericClassReferenceContext extends ExpressionContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public GenActualsContext genActuals() {
			return getRuleContext(GenActualsContext.class,0);
		}
		public GenericClassReferenceContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterGenericClassReference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitGenericClassReference(this);
		}
	}
	public static class TupleContext extends ExpressionContext {
		public TerminalNode LPARA() { return getToken(OZParser.LPARA, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RPARA() { return getToken(OZParser.RPARA, 0); }
		public List<TerminalNode> COMMA() { return getTokens(OZParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(OZParser.COMMA, i);
		}
		public TupleContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterTuple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitTuple(this);
		}
	}
	public static class PrefixExpressionContext extends ExpressionContext {
		public PrefixContext prefix() {
			return getRuleContext(PrefixContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PrefixExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterPrefixExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitPrefixExpression(this);
		}
	}
	public static class ParenthesizedExpressionContext extends ExpressionContext {
		public TerminalNode LPARA() { return getToken(OZParser.LPARA, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPARA() { return getToken(OZParser.RPARA, 0); }
		public ParenthesizedExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterParenthesizedExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitParenthesizedExpression(this);
		}
	}
	public static class PredefinedTypeExpressionContext extends ExpressionContext {
		public PredefinedTypeContext p;
		public PredefinedTypeContext predefinedType() {
			return getRuleContext(PredefinedTypeContext.class,0);
		}
		public PredefinedTypeExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterPredefinedTypeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitPredefinedTypeExpression(this);
		}
	}
	public static class SetAbstractionContext extends ExpressionContext {
		public TerminalNode LCURLY() { return getToken(OZParser.LCURLY, 0); }
		public SchemaTextContext schemaText() {
			return getRuleContext(SchemaTextContext.class,0);
		}
		public TerminalNode RCURLY() { return getToken(OZParser.RCURLY, 0); }
		public TerminalNode DOT() { return getToken(OZParser.DOT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SetAbstractionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterSetAbstraction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitSetAbstraction(this);
		}
	}
	public static class PolymorphicExpressionContext extends ExpressionContext {
		public TerminalNode CLASS_HIER() { return getToken(OZParser.CLASS_HIER, 0); }
		public TerminalNode ID() { return getToken(OZParser.ID, 0); }
		public GenActualsContext genActuals() {
			return getRuleContext(GenActualsContext.class,0);
		}
		public PolymorphicExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterPolymorphicExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitPolymorphicExpression(this);
		}
	}
	public static class PostfixExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PostfixContext postfix() {
			return getRuleContext(PostfixContext.class,0);
		}
		public PostfixExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterPostfixExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitPostfixExpression(this);
		}
	}
	public static class GeneralizedOperationContext extends ExpressionContext {
		public Token op;
		public TerminalNode LPARA() { return getToken(OZParser.LPARA, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPARA() { return getToken(OZParser.RPARA, 0); }
		public TerminalNode UNION() { return getToken(OZParser.UNION, 0); }
		public TerminalNode INTERSECT() { return getToken(OZParser.INTERSECT, 0); }
		public TerminalNode CONCATENATE() { return getToken(OZParser.CONCATENATE, 0); }
		public GeneralizedOperationContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterGeneralizedOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitGeneralizedOperation(this);
		}
	}
	public static class BinaryRelationContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode RELATION() { return getToken(OZParser.RELATION, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BinaryRelationContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterBinaryRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitBinaryRelation(this);
		}
	}
	public static class SetContext extends ExpressionContext {
		public TerminalNode LCURLY() { return getToken(OZParser.LCURLY, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RCURLY() { return getToken(OZParser.RCURLY, 0); }
		public List<TerminalNode> COMMA() { return getTokens(OZParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(OZParser.COMMA, i);
		}
		public SetContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitSet(this);
		}
	}
	public static class EmptySequenceContext extends ExpressionContext {
		public TerminalNode NCH() { return getToken(OZParser.NCH, 0); }
		public EmptySequenceContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterEmptySequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitEmptySequence(this);
		}
	}
	public static class FormalClassReferenceContext extends ExpressionContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public FormalParametersContext formalParameters() {
			return getRuleContext(FormalParametersContext.class,0);
		}
		public FormalClassReferenceContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterFormalClassReference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitFormalClassReference(this);
		}
	}
	public static class InfixComparisonOperationContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public InfixComparisonOpContext infixComparisonOp() {
			return getRuleContext(InfixComparisonOpContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public InfixComparisonOperationContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterInfixComparisonOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitInfixComparisonOperation(this);
		}
	}
	public static class TernaryRelationContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext middle;
		public ExpressionContext right;
		public List<TerminalNode> RELATION() { return getTokens(OZParser.RELATION); }
		public TerminalNode RELATION(int i) {
			return getToken(OZParser.RELATION, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TernaryRelationContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterTernaryRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitTernaryRelation(this);
		}
	}
	public static class MultiplicativeExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public Token op;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode MULT() { return getToken(OZParser.MULT, 0); }
		public TerminalNode DIV() { return getToken(OZParser.DIV, 0); }
		public TerminalNode INT_DIV() { return getToken(OZParser.INT_DIV, 0); }
		public TerminalNode MOD() { return getToken(OZParser.MOD, 0); }
		public MultiplicativeExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterMultiplicativeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitMultiplicativeExpression(this);
		}
	}
	public static class EmptyBagContext extends ExpressionContext {
		public TerminalNode EMPTYBAG() { return getToken(OZParser.EMPTYBAG, 0); }
		public EmptyBagContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterEmptyBag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitEmptyBag(this);
		}
	}
	public static class CallContext extends ExpressionContext {
		public FeatureOrFunctionCallContext featureOrFunctionCall() {
			return getRuleContext(FeatureOrFunctionCallContext.class,0);
		}
		public CallContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitCall(this);
		}
	}
	public static class SequenceContext extends ExpressionContext {
		public TerminalNode LBRACK() { return getToken(OZParser.LBRACK, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RBRACK() { return getToken(OZParser.RBRACK, 0); }
		public List<TerminalNode> COMMA() { return getTokens(OZParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(OZParser.COMMA, i);
		}
		public SequenceContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterSequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitSequence(this);
		}
	}
	public static class InfixRelationOperationContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public InfixRelationOpContext infixRelationOp() {
			return getRuleContext(InfixRelationOpContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public InfixRelationOperationContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterInfixRelationOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitInfixRelationOperation(this);
		}
	}
	public static class SelfContext extends ExpressionContext {
		public TerminalNode SELF() { return getToken(OZParser.SELF, 0); }
		public SelfContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterSelf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitSelf(this);
		}
	}
	public static class RangeExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode RANGE() { return getToken(OZParser.RANGE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public RangeExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterRangeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitRangeExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 72;
		enterRecursionRule(_localctx, 72, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(594);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,60,_ctx) ) {
			case 1:
				{
				_localctx = new PowerSetExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(504);
				powerSetOp();
				setState(505);
				expression(29);
				}
				break;
			case 2:
				{
				_localctx = new PrefixExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(507);
				prefix();
				setState(508);
				expression(28);
				}
				break;
			case 3:
				{
				_localctx = new GeneralizedOperationContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(510);
				((GeneralizedOperationContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (UNION - 72)) | (1L << (INTERSECT - 72)) | (1L << (CONCATENATE - 72)))) != 0)) ) {
					((GeneralizedOperationContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(511);
				match(LPARA);
				setState(512);
				expression(0);
				setState(513);
				match(RPARA);
				}
				break;
			case 4:
				{
				_localctx = new PolymorphicExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(515);
				match(CLASS_HIER);
				setState(516);
				match(ID);
				setState(518);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
				case 1:
					{
					setState(517);
					genActuals();
					}
					break;
				}
				}
				break;
			case 5:
				{
				_localctx = new PredefinedTypeExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(520);
				((PredefinedTypeExpressionContext)_localctx).p = predefinedType();
				}
				break;
			case 6:
				{
				_localctx = new SchemaSumContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(521);
				match(SUM);
				setState(522);
				schemaText();
				setState(523);
				match(DOT);
				setState(524);
				featureOrFunctionCall();
				}
				break;
			case 7:
				{
				_localctx = new MinMaxOfExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(526);
				((MinMaxOfExpressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==MIN || _la==MAX) ) {
					((MinMaxOfExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(527);
				((MinMaxOfExpressionContext)_localctx).e = expression(14);
				}
				break;
			case 8:
				{
				_localctx = new FormalClassReferenceContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(528);
				id();
				setState(529);
				formalParameters();
				}
				break;
			case 9:
				{
				_localctx = new GenericClassReferenceContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(531);
				id();
				setState(532);
				genActuals();
				}
				break;
			case 10:
				{
				_localctx = new SetAbstractionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(534);
				match(LCURLY);
				setState(535);
				schemaText();
				setState(538);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==DOT) {
					{
					setState(536);
					match(DOT);
					setState(537);
					expression(0);
					}
				}

				setState(540);
				match(RCURLY);
				}
				break;
			case 11:
				{
				_localctx = new TupleContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(542);
				match(LPARA);
				setState(543);
				expression(0);
				setState(546); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(544);
					match(COMMA);
					setState(545);
					expression(0);
					}
					}
					setState(548); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==COMMA );
				setState(550);
				match(RPARA);
				}
				break;
			case 12:
				{
				_localctx = new CallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(552);
				featureOrFunctionCall();
				}
				break;
			case 13:
				{
				_localctx = new SetContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(553);
				match(LCURLY);
				setState(554);
				expression(0);
				setState(559);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(555);
					match(COMMA);
					setState(556);
					expression(0);
					}
					}
					setState(561);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(562);
				match(RCURLY);
				}
				break;
			case 14:
				{
				_localctx = new BagContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(564);
				match(LLBRACK);
				setState(565);
				expression(0);
				setState(570);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(566);
					match(COMMA);
					setState(567);
					expression(0);
					}
					}
					setState(572);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(573);
				match(RRBRACK);
				}
				break;
			case 15:
				{
				_localctx = new SequenceContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(575);
				match(LBRACK);
				setState(576);
				expression(0);
				setState(581);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(577);
					match(COMMA);
					setState(578);
					expression(0);
					}
					}
					setState(583);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(584);
				match(RBRACK);
				}
				break;
			case 16:
				{
				_localctx = new EmptySetContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(586);
				match(EMPTYSET);
				}
				break;
			case 17:
				{
				_localctx = new EmptyBagContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(587);
				match(EMPTYBAG);
				}
				break;
			case 18:
				{
				_localctx = new EmptySequenceContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(588);
				match(NCH);
				}
				break;
			case 19:
				{
				_localctx = new SelfContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(589);
				match(SELF);
				}
				break;
			case 20:
				{
				_localctx = new ParenthesizedExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(590);
				match(LPARA);
				setState(591);
				expression(0);
				setState(592);
				match(RPARA);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(633);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,62,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(631);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,61,_ctx) ) {
					case 1:
						{
						_localctx = new CartesianContext(new ExpressionContext(_parentctx, _parentState));
						((CartesianContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(596);
						if (!(precpred(_ctx, 30))) throw new FailedPredicateException(this, "precpred(_ctx, 30)");
						setState(597);
						match(T__1);
						setState(598);
						((CartesianContext)_localctx).right = expression(31);
						}
						break;
					case 2:
						{
						_localctx = new MultiplicativeExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((MultiplicativeExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(599);
						if (!(precpred(_ctx, 27))) throw new FailedPredicateException(this, "precpred(_ctx, 27)");
						setState(600);
						((MultiplicativeExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MULT) | (1L << DIV) | (1L << INT_DIV) | (1L << MOD))) != 0)) ) {
							((MultiplicativeExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(601);
						((MultiplicativeExpressionContext)_localctx).right = expression(28);
						}
						break;
					case 3:
						{
						_localctx = new AdditiveExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((AdditiveExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(602);
						if (!(precpred(_ctx, 26))) throw new FailedPredicateException(this, "precpred(_ctx, 26)");
						setState(603);
						((AdditiveExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((AdditiveExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(604);
						((AdditiveExpressionContext)_localctx).right = expression(27);
						}
						break;
					case 4:
						{
						_localctx = new RangeExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((RangeExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(605);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(606);
						match(RANGE);
						setState(607);
						((RangeExpressionContext)_localctx).right = expression(26);
						}
						break;
					case 5:
						{
						_localctx = new CollectionOperationContext(new ExpressionContext(_parentctx, _parentState));
						((CollectionOperationContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(608);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(609);
						setOp();
						setState(610);
						((CollectionOperationContext)_localctx).right = expression(25);
						}
						break;
					case 6:
						{
						_localctx = new InfixComparisonOperationContext(new ExpressionContext(_parentctx, _parentState));
						((InfixComparisonOperationContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(612);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(613);
						infixComparisonOp();
						setState(614);
						((InfixComparisonOperationContext)_localctx).right = expression(24);
						}
						break;
					case 7:
						{
						_localctx = new InfixRelationOperationContext(new ExpressionContext(_parentctx, _parentState));
						((InfixRelationOperationContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(616);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(617);
						infixRelationOp();
						setState(618);
						((InfixRelationOperationContext)_localctx).right = expression(23);
						}
						break;
					case 8:
						{
						_localctx = new TernaryRelationContext(new ExpressionContext(_parentctx, _parentState));
						((TernaryRelationContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(620);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(621);
						match(RELATION);
						setState(622);
						((TernaryRelationContext)_localctx).middle = expression(0);
						setState(623);
						match(RELATION);
						setState(624);
						((TernaryRelationContext)_localctx).right = expression(22);
						}
						break;
					case 9:
						{
						_localctx = new BinaryRelationContext(new ExpressionContext(_parentctx, _parentState));
						((BinaryRelationContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(626);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(627);
						match(RELATION);
						setState(628);
						((BinaryRelationContext)_localctx).right = expression(21);
						}
						break;
					case 10:
						{
						_localctx = new PostfixExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(629);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(630);
						postfix();
						}
						break;
					}
					} 
				}
				setState(635);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,62,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class GenActualsContext extends ParserRuleContext {
		public TerminalNode LBRACK() { return getToken(OZParser.LBRACK, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RBRACK() { return getToken(OZParser.RBRACK, 0); }
		public List<TerminalNode> COMMA() { return getTokens(OZParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(OZParser.COMMA, i);
		}
		public GenActualsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_genActuals; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterGenActuals(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitGenActuals(this);
		}
	}

	public final GenActualsContext genActuals() throws RecognitionException {
		GenActualsContext _localctx = new GenActualsContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_genActuals);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(636);
			match(LBRACK);
			setState(637);
			expression(0);
			setState(642);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(638);
				match(COMMA);
				setState(639);
				expression(0);
				}
				}
				setState(644);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(645);
			match(RBRACK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormalParametersContext extends ParserRuleContext {
		public TerminalNode LBRACK() { return getToken(OZParser.LBRACK, 0); }
		public List<TerminalNode> ID() { return getTokens(OZParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(OZParser.ID, i);
		}
		public TerminalNode RBRACK() { return getToken(OZParser.RBRACK, 0); }
		public List<TerminalNode> COMMA() { return getTokens(OZParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(OZParser.COMMA, i);
		}
		public FormalParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterFormalParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitFormalParameters(this);
		}
	}

	public final FormalParametersContext formalParameters() throws RecognitionException {
		FormalParametersContext _localctx = new FormalParametersContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_formalParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(647);
			match(LBRACK);
			setState(648);
			match(ID);
			setState(653);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(649);
				match(COMMA);
				setState(650);
				match(ID);
				}
				}
				setState(655);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(656);
			match(RBRACK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PowerSetOpContext extends ParserRuleContext {
		public Token op;
		public TerminalNode POWER() { return getToken(OZParser.POWER, 0); }
		public TerminalNode POWER1() { return getToken(OZParser.POWER1, 0); }
		public TerminalNode FINITE() { return getToken(OZParser.FINITE, 0); }
		public TerminalNode FINITE1() { return getToken(OZParser.FINITE1, 0); }
		public TerminalNode SEQ() { return getToken(OZParser.SEQ, 0); }
		public TerminalNode SEQ1() { return getToken(OZParser.SEQ1, 0); }
		public TerminalNode ISEQ() { return getToken(OZParser.ISEQ, 0); }
		public TerminalNode BAG() { return getToken(OZParser.BAG, 0); }
		public PowerSetOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_powerSetOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterPowerSetOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitPowerSetOp(this);
		}
	}

	public final PowerSetOpContext powerSetOp() throws RecognitionException {
		PowerSetOpContext _localctx = new PowerSetOpContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_powerSetOp);
		try {
			setState(666);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case POWER:
				enterOuterAlt(_localctx, 1);
				{
				setState(658);
				((PowerSetOpContext)_localctx).op = match(POWER);
				}
				break;
			case POWER1:
				enterOuterAlt(_localctx, 2);
				{
				setState(659);
				((PowerSetOpContext)_localctx).op = match(POWER1);
				}
				break;
			case FINITE:
				enterOuterAlt(_localctx, 3);
				{
				setState(660);
				((PowerSetOpContext)_localctx).op = match(FINITE);
				}
				break;
			case FINITE1:
				enterOuterAlt(_localctx, 4);
				{
				setState(661);
				((PowerSetOpContext)_localctx).op = match(FINITE1);
				}
				break;
			case SEQ:
				enterOuterAlt(_localctx, 5);
				{
				setState(662);
				((PowerSetOpContext)_localctx).op = match(SEQ);
				}
				break;
			case SEQ1:
				enterOuterAlt(_localctx, 6);
				{
				setState(663);
				((PowerSetOpContext)_localctx).op = match(SEQ1);
				}
				break;
			case ISEQ:
				enterOuterAlt(_localctx, 7);
				{
				setState(664);
				((PowerSetOpContext)_localctx).op = match(ISEQ);
				}
				break;
			case BAG:
				enterOuterAlt(_localctx, 8);
				{
				setState(665);
				((PowerSetOpContext)_localctx).op = match(BAG);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InfixRelationOpContext extends ParserRuleContext {
		public Token op;
		public TerminalNode PART_FUNC() { return getToken(OZParser.PART_FUNC, 0); }
		public TerminalNode TOT_FUNC() { return getToken(OZParser.TOT_FUNC, 0); }
		public TerminalNode PART_INJ() { return getToken(OZParser.PART_INJ, 0); }
		public TerminalNode TOT_INJ() { return getToken(OZParser.TOT_INJ, 0); }
		public TerminalNode PART_SUR() { return getToken(OZParser.PART_SUR, 0); }
		public TerminalNode TOT_SUR() { return getToken(OZParser.TOT_SUR, 0); }
		public TerminalNode BIJEC() { return getToken(OZParser.BIJEC, 0); }
		public TerminalNode MAPLET() { return getToken(OZParser.MAPLET, 0); }
		public TerminalNode F_PART_FUNC() { return getToken(OZParser.F_PART_FUNC, 0); }
		public TerminalNode F_PART_INJ() { return getToken(OZParser.F_PART_INJ, 0); }
		public TerminalNode DOM_RESTR() { return getToken(OZParser.DOM_RESTR, 0); }
		public TerminalNode RAN_RESTR() { return getToken(OZParser.RAN_RESTR, 0); }
		public TerminalNode DOM_AR() { return getToken(OZParser.DOM_AR, 0); }
		public TerminalNode RAN_AR() { return getToken(OZParser.RAN_AR, 0); }
		public InfixRelationOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_infixRelationOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterInfixRelationOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitInfixRelationOp(this);
		}
	}

	public final InfixRelationOpContext infixRelationOp() throws RecognitionException {
		InfixRelationOpContext _localctx = new InfixRelationOpContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_infixRelationOp);
		try {
			setState(682);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PART_FUNC:
				enterOuterAlt(_localctx, 1);
				{
				setState(668);
				((InfixRelationOpContext)_localctx).op = match(PART_FUNC);
				}
				break;
			case TOT_FUNC:
				enterOuterAlt(_localctx, 2);
				{
				setState(669);
				((InfixRelationOpContext)_localctx).op = match(TOT_FUNC);
				}
				break;
			case PART_INJ:
				enterOuterAlt(_localctx, 3);
				{
				setState(670);
				((InfixRelationOpContext)_localctx).op = match(PART_INJ);
				}
				break;
			case TOT_INJ:
				enterOuterAlt(_localctx, 4);
				{
				setState(671);
				((InfixRelationOpContext)_localctx).op = match(TOT_INJ);
				}
				break;
			case PART_SUR:
				enterOuterAlt(_localctx, 5);
				{
				setState(672);
				((InfixRelationOpContext)_localctx).op = match(PART_SUR);
				}
				break;
			case TOT_SUR:
				enterOuterAlt(_localctx, 6);
				{
				setState(673);
				((InfixRelationOpContext)_localctx).op = match(TOT_SUR);
				}
				break;
			case BIJEC:
				enterOuterAlt(_localctx, 7);
				{
				setState(674);
				((InfixRelationOpContext)_localctx).op = match(BIJEC);
				}
				break;
			case MAPLET:
				enterOuterAlt(_localctx, 8);
				{
				setState(675);
				((InfixRelationOpContext)_localctx).op = match(MAPLET);
				}
				break;
			case F_PART_FUNC:
				enterOuterAlt(_localctx, 9);
				{
				setState(676);
				((InfixRelationOpContext)_localctx).op = match(F_PART_FUNC);
				}
				break;
			case F_PART_INJ:
				enterOuterAlt(_localctx, 10);
				{
				setState(677);
				((InfixRelationOpContext)_localctx).op = match(F_PART_INJ);
				}
				break;
			case DOM_RESTR:
				enterOuterAlt(_localctx, 11);
				{
				setState(678);
				((InfixRelationOpContext)_localctx).op = match(DOM_RESTR);
				}
				break;
			case RAN_RESTR:
				enterOuterAlt(_localctx, 12);
				{
				setState(679);
				((InfixRelationOpContext)_localctx).op = match(RAN_RESTR);
				}
				break;
			case DOM_AR:
				enterOuterAlt(_localctx, 13);
				{
				setState(680);
				((InfixRelationOpContext)_localctx).op = match(DOM_AR);
				}
				break;
			case RAN_AR:
				enterOuterAlt(_localctx, 14);
				{
				setState(681);
				((InfixRelationOpContext)_localctx).op = match(RAN_AR);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InfixComparisonOpContext extends ParserRuleContext {
		public Token op;
		public TerminalNode EQUALS() { return getToken(OZParser.EQUALS, 0); }
		public TerminalNode NEQUALS() { return getToken(OZParser.NEQUALS, 0); }
		public TerminalNode ELEM() { return getToken(OZParser.ELEM, 0); }
		public TerminalNode NELEM() { return getToken(OZParser.NELEM, 0); }
		public TerminalNode SUBSET() { return getToken(OZParser.SUBSET, 0); }
		public TerminalNode STR_SUBSET() { return getToken(OZParser.STR_SUBSET, 0); }
		public TerminalNode LT() { return getToken(OZParser.LT, 0); }
		public TerminalNode LTE() { return getToken(OZParser.LTE, 0); }
		public TerminalNode GT() { return getToken(OZParser.GT, 0); }
		public TerminalNode GTE() { return getToken(OZParser.GTE, 0); }
		public TerminalNode PREFIX() { return getToken(OZParser.PREFIX, 0); }
		public TerminalNode SUFFIX() { return getToken(OZParser.SUFFIX, 0); }
		public TerminalNode IN_SEQ() { return getToken(OZParser.IN_SEQ, 0); }
		public TerminalNode IN_BAG() { return getToken(OZParser.IN_BAG, 0); }
		public TerminalNode SUBBAG() { return getToken(OZParser.SUBBAG, 0); }
		public InfixComparisonOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_infixComparisonOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterInfixComparisonOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitInfixComparisonOp(this);
		}
	}

	public final InfixComparisonOpContext infixComparisonOp() throws RecognitionException {
		InfixComparisonOpContext _localctx = new InfixComparisonOpContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_infixComparisonOp);
		try {
			setState(699);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EQUALS:
				enterOuterAlt(_localctx, 1);
				{
				setState(684);
				((InfixComparisonOpContext)_localctx).op = match(EQUALS);
				}
				break;
			case NEQUALS:
				enterOuterAlt(_localctx, 2);
				{
				setState(685);
				((InfixComparisonOpContext)_localctx).op = match(NEQUALS);
				}
				break;
			case ELEM:
				enterOuterAlt(_localctx, 3);
				{
				setState(686);
				((InfixComparisonOpContext)_localctx).op = match(ELEM);
				}
				break;
			case NELEM:
				enterOuterAlt(_localctx, 4);
				{
				setState(687);
				((InfixComparisonOpContext)_localctx).op = match(NELEM);
				}
				break;
			case SUBSET:
				enterOuterAlt(_localctx, 5);
				{
				setState(688);
				((InfixComparisonOpContext)_localctx).op = match(SUBSET);
				}
				break;
			case STR_SUBSET:
				enterOuterAlt(_localctx, 6);
				{
				setState(689);
				((InfixComparisonOpContext)_localctx).op = match(STR_SUBSET);
				}
				break;
			case LT:
				enterOuterAlt(_localctx, 7);
				{
				setState(690);
				((InfixComparisonOpContext)_localctx).op = match(LT);
				}
				break;
			case LTE:
				enterOuterAlt(_localctx, 8);
				{
				setState(691);
				((InfixComparisonOpContext)_localctx).op = match(LTE);
				}
				break;
			case GT:
				enterOuterAlt(_localctx, 9);
				{
				setState(692);
				((InfixComparisonOpContext)_localctx).op = match(GT);
				}
				break;
			case GTE:
				enterOuterAlt(_localctx, 10);
				{
				setState(693);
				((InfixComparisonOpContext)_localctx).op = match(GTE);
				}
				break;
			case PREFIX:
				enterOuterAlt(_localctx, 11);
				{
				setState(694);
				((InfixComparisonOpContext)_localctx).op = match(PREFIX);
				}
				break;
			case SUFFIX:
				enterOuterAlt(_localctx, 12);
				{
				setState(695);
				((InfixComparisonOpContext)_localctx).op = match(SUFFIX);
				}
				break;
			case IN_SEQ:
				enterOuterAlt(_localctx, 13);
				{
				setState(696);
				((InfixComparisonOpContext)_localctx).op = match(IN_SEQ);
				}
				break;
			case IN_BAG:
				enterOuterAlt(_localctx, 14);
				{
				setState(697);
				((InfixComparisonOpContext)_localctx).op = match(IN_BAG);
				}
				break;
			case SUBBAG:
				enterOuterAlt(_localctx, 15);
				{
				setState(698);
				((InfixComparisonOpContext)_localctx).op = match(SUBBAG);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetOpContext extends ParserRuleContext {
		public Token op;
		public TerminalNode UNION() { return getToken(OZParser.UNION, 0); }
		public TerminalNode DIFFERENCE() { return getToken(OZParser.DIFFERENCE, 0); }
		public TerminalNode INTERSECT() { return getToken(OZParser.INTERSECT, 0); }
		public TerminalNode CONCATENATE() { return getToken(OZParser.CONCATENATE, 0); }
		public TerminalNode OVERRIDE() { return getToken(OZParser.OVERRIDE, 0); }
		public TerminalNode EXTRACT() { return getToken(OZParser.EXTRACT, 0); }
		public TerminalNode FILTER() { return getToken(OZParser.FILTER, 0); }
		public TerminalNode MULTIPLICITY() { return getToken(OZParser.MULTIPLICITY, 0); }
		public TerminalNode SCALING() { return getToken(OZParser.SCALING, 0); }
		public TerminalNode BAG_UNION() { return getToken(OZParser.BAG_UNION, 0); }
		public TerminalNode BAG_DIFFERENCE() { return getToken(OZParser.BAG_DIFFERENCE, 0); }
		public SetOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterSetOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitSetOp(this);
		}
	}

	public final SetOpContext setOp() throws RecognitionException {
		SetOpContext _localctx = new SetOpContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_setOp);
		try {
			setState(712);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case UNION:
				enterOuterAlt(_localctx, 1);
				{
				setState(701);
				((SetOpContext)_localctx).op = match(UNION);
				}
				break;
			case DIFFERENCE:
				enterOuterAlt(_localctx, 2);
				{
				setState(702);
				((SetOpContext)_localctx).op = match(DIFFERENCE);
				}
				break;
			case INTERSECT:
				enterOuterAlt(_localctx, 3);
				{
				setState(703);
				((SetOpContext)_localctx).op = match(INTERSECT);
				}
				break;
			case CONCATENATE:
				enterOuterAlt(_localctx, 4);
				{
				setState(704);
				((SetOpContext)_localctx).op = match(CONCATENATE);
				}
				break;
			case OVERRIDE:
				enterOuterAlt(_localctx, 5);
				{
				setState(705);
				((SetOpContext)_localctx).op = match(OVERRIDE);
				}
				break;
			case EXTRACT:
				enterOuterAlt(_localctx, 6);
				{
				setState(706);
				((SetOpContext)_localctx).op = match(EXTRACT);
				}
				break;
			case FILTER:
				enterOuterAlt(_localctx, 7);
				{
				setState(707);
				((SetOpContext)_localctx).op = match(FILTER);
				}
				break;
			case MULTIPLICITY:
				enterOuterAlt(_localctx, 8);
				{
				setState(708);
				((SetOpContext)_localctx).op = match(MULTIPLICITY);
				}
				break;
			case SCALING:
				enterOuterAlt(_localctx, 9);
				{
				setState(709);
				((SetOpContext)_localctx).op = match(SCALING);
				}
				break;
			case BAG_UNION:
				enterOuterAlt(_localctx, 10);
				{
				setState(710);
				((SetOpContext)_localctx).op = match(BAG_UNION);
				}
				break;
			case BAG_DIFFERENCE:
				enterOuterAlt(_localctx, 11);
				{
				setState(711);
				((SetOpContext)_localctx).op = match(BAG_DIFFERENCE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrefixContext extends ParserRuleContext {
		public Token op;
		public TerminalNode MINUS() { return getToken(OZParser.MINUS, 0); }
		public TerminalNode COUNT() { return getToken(OZParser.COUNT, 0); }
		public TerminalNode RAN() { return getToken(OZParser.RAN, 0); }
		public TerminalNode DOM() { return getToken(OZParser.DOM, 0); }
		public TerminalNode TAIL() { return getToken(OZParser.TAIL, 0); }
		public TerminalNode HEAD() { return getToken(OZParser.HEAD, 0); }
		public TerminalNode REV() { return getToken(OZParser.REV, 0); }
		public TerminalNode LAST() { return getToken(OZParser.LAST, 0); }
		public TerminalNode FRONT() { return getToken(OZParser.FRONT, 0); }
		public TerminalNode ITEMS() { return getToken(OZParser.ITEMS, 0); }
		public PrefixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterPrefix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitPrefix(this);
		}
	}

	public final PrefixContext prefix() throws RecognitionException {
		PrefixContext _localctx = new PrefixContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_prefix);
		try {
			setState(724);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case MINUS:
				enterOuterAlt(_localctx, 1);
				{
				setState(714);
				((PrefixContext)_localctx).op = match(MINUS);
				}
				break;
			case COUNT:
				enterOuterAlt(_localctx, 2);
				{
				setState(715);
				((PrefixContext)_localctx).op = match(COUNT);
				}
				break;
			case RAN:
				enterOuterAlt(_localctx, 3);
				{
				setState(716);
				((PrefixContext)_localctx).op = match(RAN);
				}
				break;
			case DOM:
				enterOuterAlt(_localctx, 4);
				{
				setState(717);
				((PrefixContext)_localctx).op = match(DOM);
				}
				break;
			case TAIL:
				enterOuterAlt(_localctx, 5);
				{
				setState(718);
				((PrefixContext)_localctx).op = match(TAIL);
				}
				break;
			case HEAD:
				enterOuterAlt(_localctx, 6);
				{
				setState(719);
				((PrefixContext)_localctx).op = match(HEAD);
				}
				break;
			case REV:
				enterOuterAlt(_localctx, 7);
				{
				setState(720);
				((PrefixContext)_localctx).op = match(REV);
				}
				break;
			case LAST:
				enterOuterAlt(_localctx, 8);
				{
				setState(721);
				((PrefixContext)_localctx).op = match(LAST);
				}
				break;
			case FRONT:
				enterOuterAlt(_localctx, 9);
				{
				setState(722);
				((PrefixContext)_localctx).op = match(FRONT);
				}
				break;
			case ITEMS:
				enterOuterAlt(_localctx, 10);
				{
				setState(723);
				((PrefixContext)_localctx).op = match(ITEMS);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PostfixContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(OZParser.NOT, 0); }
		public PostfixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterPostfix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitPostfix(this);
		}
	}

	public final PostfixContext postfix() throws RecognitionException {
		PostfixContext _localctx = new PostfixContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_postfix);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(726);
			_la = _input.LA(1);
			if ( !(((((_la - 48)) & ~0x3f) == 0 && ((1L << (_la - 48)) & ((1L << (MULT - 48)) | (1L << (PLUS - 48)) | (1L << (NOT - 48)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FeatureOrFunctionCallContext extends ParserRuleContext {
		public FeatureOrFunctionCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_featureOrFunctionCall; }
	 
		public FeatureOrFunctionCallContext() { }
		public void copyFrom(FeatureOrFunctionCallContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AttributeCallContext extends FeatureOrFunctionCallContext {
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public List<TerminalNode> ATTR_CALL() { return getTokens(OZParser.ATTR_CALL); }
		public TerminalNode ATTR_CALL(int i) {
			return getToken(OZParser.ATTR_CALL, i);
		}
		public AttributeCallContext(FeatureOrFunctionCallContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterAttributeCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitAttributeCall(this);
		}
	}
	public static class SuccessorContext extends FeatureOrFunctionCallContext {
		public TerminalNode SUCC() { return getToken(OZParser.SUCC, 0); }
		public TerminalNode LPARA() { return getToken(OZParser.LPARA, 0); }
		public IdOrNumberContext idOrNumber() {
			return getRuleContext(IdOrNumberContext.class,0);
		}
		public TerminalNode RPARA() { return getToken(OZParser.RPARA, 0); }
		public SuccessorContext(FeatureOrFunctionCallContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterSuccessor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitSuccessor(this);
		}
	}
	public static class FunctionReferenceContext extends FeatureOrFunctionCallContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode LPARA() { return getToken(OZParser.LPARA, 0); }
		public List<FeatureOrFunctionCallContext> featureOrFunctionCall() {
			return getRuleContexts(FeatureOrFunctionCallContext.class);
		}
		public FeatureOrFunctionCallContext featureOrFunctionCall(int i) {
			return getRuleContext(FeatureOrFunctionCallContext.class,i);
		}
		public TerminalNode RPARA() { return getToken(OZParser.RPARA, 0); }
		public List<TerminalNode> COMMA() { return getTokens(OZParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(OZParser.COMMA, i);
		}
		public FunctionReferenceContext(FeatureOrFunctionCallContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterFunctionReference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitFunctionReference(this);
		}
	}
	public static class FeatureOrNumberContext extends FeatureOrFunctionCallContext {
		public IdOrNumberContext idOrNumber() {
			return getRuleContext(IdOrNumberContext.class,0);
		}
		public FeatureOrNumberContext(FeatureOrFunctionCallContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterFeatureOrNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitFeatureOrNumber(this);
		}
	}

	public final FeatureOrFunctionCallContext featureOrFunctionCall() throws RecognitionException {
		FeatureOrFunctionCallContext _localctx = new FeatureOrFunctionCallContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_featureOrFunctionCall);
		int _la;
		try {
			int _alt;
			setState(753);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,72,_ctx) ) {
			case 1:
				_localctx = new AttributeCallContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(728);
				id();
				setState(731); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(729);
						match(ATTR_CALL);
						setState(730);
						id();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(733); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,70,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			case 2:
				_localctx = new SuccessorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(735);
				match(SUCC);
				setState(736);
				match(LPARA);
				setState(737);
				idOrNumber();
				setState(738);
				match(RPARA);
				}
				break;
			case 3:
				_localctx = new FunctionReferenceContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(740);
				id();
				setState(741);
				match(LPARA);
				setState(742);
				featureOrFunctionCall();
				setState(747);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(743);
					match(COMMA);
					setState(744);
					featureOrFunctionCall();
					}
					}
					setState(749);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(750);
				match(RPARA);
				}
				break;
			case 4:
				_localctx = new FeatureOrNumberContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(752);
				idOrNumber();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdOrNumberContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public IdOrNumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idOrNumber; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterIdOrNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitIdOrNumber(this);
		}
	}

	public final IdOrNumberContext idOrNumber() throws RecognitionException {
		IdOrNumberContext _localctx = new IdOrNumberContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_idOrNumber);
		try {
			setState(757);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(755);
				id();
				}
				break;
			case INT:
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(756);
				number();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnderlinedIdContext extends ParserRuleContext {
		public List<TerminalNode> UNDERLINE() { return getTokens(OZParser.UNDERLINE); }
		public TerminalNode UNDERLINE(int i) {
			return getToken(OZParser.UNDERLINE, i);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public UnderlinedIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_underlinedId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterUnderlinedId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitUnderlinedId(this);
		}
	}

	public final UnderlinedIdContext underlinedId() throws RecognitionException {
		UnderlinedIdContext _localctx = new UnderlinedIdContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_underlinedId);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(759);
			match(UNDERLINE);
			setState(760);
			id();
			setState(761);
			match(UNDERLINE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(OZParser.ID, 0); }
		public TerminalNode DECORATION() { return getToken(OZParser.DECORATION, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitId(this);
		}
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_id);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(763);
			match(ID);
			setState(765);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,74,_ctx) ) {
			case 1:
				{
				setState(764);
				match(DECORATION);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public Token type;
		public TerminalNode INT() { return getToken(OZParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(OZParser.FLOAT, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitNumber(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_number);
		try {
			setState(769);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(767);
				((NumberContext)_localctx).type = match(INT);
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(768);
				((NumberContext)_localctx).type = match(FLOAT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PredefinedTypeContext extends ParserRuleContext {
		public Token op;
		public TerminalNode NAT() { return getToken(OZParser.NAT, 0); }
		public TerminalNode PNAT() { return getToken(OZParser.PNAT, 0); }
		public TerminalNode INTEGER() { return getToken(OZParser.INTEGER, 0); }
		public TerminalNode BOOL() { return getToken(OZParser.BOOL, 0); }
		public TerminalNode REAL() { return getToken(OZParser.REAL, 0); }
		public TerminalNode CHAR() { return getToken(OZParser.CHAR, 0); }
		public PredefinedTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predefinedType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).enterPredefinedType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OZListener ) ((OZListener)listener).exitPredefinedType(this);
		}
	}

	public final PredefinedTypeContext predefinedType() throws RecognitionException {
		PredefinedTypeContext _localctx = new PredefinedTypeContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_predefinedType);
		try {
			setState(777);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAT:
				enterOuterAlt(_localctx, 1);
				{
				setState(771);
				((PredefinedTypeContext)_localctx).op = match(NAT);
				}
				break;
			case PNAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(772);
				((PredefinedTypeContext)_localctx).op = match(PNAT);
				}
				break;
			case INTEGER:
				enterOuterAlt(_localctx, 3);
				{
				setState(773);
				((PredefinedTypeContext)_localctx).op = match(INTEGER);
				}
				break;
			case BOOL:
				enterOuterAlt(_localctx, 4);
				{
				setState(774);
				((PredefinedTypeContext)_localctx).op = match(BOOL);
				}
				break;
			case REAL:
				enterOuterAlt(_localctx, 5);
				{
				setState(775);
				((PredefinedTypeContext)_localctx).op = match(REAL);
				}
				break;
			case CHAR:
				enterOuterAlt(_localctx, 6);
				{
				setState(776);
				((PredefinedTypeContext)_localctx).op = match(CHAR);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 22:
			return opExpression_sempred((OpExpressionContext)_localctx, predIndex);
		case 34:
			return simplePredicate_sempred((SimplePredicateContext)_localctx, predIndex);
		case 36:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean opExpression_sempred(OpExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 6);
		case 1:
			return precpred(_ctx, 5);
		case 2:
			return precpred(_ctx, 4);
		case 3:
			return precpred(_ctx, 3);
		case 4:
			return precpred(_ctx, 2);
		case 5:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean simplePredicate_sempred(SimplePredicateContext _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return precpred(_ctx, 5);
		case 7:
			return precpred(_ctx, 4);
		case 8:
			return precpred(_ctx, 3);
		case 9:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 10:
			return precpred(_ctx, 30);
		case 11:
			return precpred(_ctx, 27);
		case 12:
			return precpred(_ctx, 26);
		case 13:
			return precpred(_ctx, 25);
		case 14:
			return precpred(_ctx, 24);
		case 15:
			return precpred(_ctx, 23);
		case 16:
			return precpred(_ctx, 22);
		case 17:
			return precpred(_ctx, 21);
		case 18:
			return precpred(_ctx, 20);
		case 19:
			return precpred(_ctx, 18);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u008c\u030e\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\3\2\6\2j\n\2\r\2\16\2k\3\3\3\3\5\3p\n\3\3\4\3\4\3\4\5\4u\n\4\3\4\3"+
		"\4\5\4y\n\4\3\4\5\4|\n\4\3\4\5\4\177\n\4\3\4\5\4\u0082\n\4\3\4\5\4\u0085"+
		"\n\4\3\4\5\4\u0088\n\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\7\5\u0091\n\5\f\5\16"+
		"\5\u0094\13\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\6\7\u009f\n\7\r\7\16"+
		"\7\u00a0\3\7\3\7\3\b\3\b\5\b\u00a7\n\b\3\b\5\b\u00aa\n\b\3\t\6\t\u00ad"+
		"\n\t\r\t\16\t\u00ae\3\n\3\n\3\n\3\n\5\n\u00b5\n\n\3\13\3\13\5\13\u00b9"+
		"\n\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\7\f\u00c2\n\f\f\f\16\f\u00c5\13\f"+
		"\3\f\3\f\3\r\3\r\3\r\3\r\3\r\7\r\u00ce\n\r\f\r\16\r\u00d1\13\r\3\16\3"+
		"\16\3\16\5\16\u00d6\n\16\3\16\5\16\u00d9\n\16\3\16\3\16\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\5\17\u00e5\n\17\3\17\5\17\u00e8\n\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00f3\n\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00ff\n\17\3\20\3\20\3\21\3\21"+
		"\3\22\3\22\3\22\3\22\3\22\3\23\3\23\6\23\u010c\n\23\r\23\16\23\u010d\3"+
		"\24\3\24\3\24\5\24\u0113\n\24\3\24\5\24\u0116\n\24\3\24\5\24\u0119\n\24"+
		"\3\24\3\24\3\25\3\25\3\25\3\25\3\25\7\25\u0122\n\25\f\25\16\25\u0125\13"+
		"\25\3\25\3\25\3\26\3\26\3\26\3\26\5\26\u012d\n\26\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u013f"+
		"\n\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\7\30\u0156\n\30\f\30\16\30\u0159"+
		"\13\30\3\31\3\31\3\31\3\31\3\31\3\31\7\31\u0161\n\31\f\31\16\31\u0164"+
		"\13\31\3\31\5\31\u0167\n\31\3\31\5\31\u016a\n\31\3\31\5\31\u016d\n\31"+
		"\3\31\5\31\u0170\n\31\3\31\3\31\5\31\u0174\n\31\3\31\3\31\3\31\3\31\3"+
		"\31\5\31\u017b\n\31\3\32\3\32\3\32\6\32\u0180\n\32\r\32\16\32\u0181\3"+
		"\33\3\33\3\33\5\33\u0187\n\33\3\34\3\34\3\34\7\34\u018c\n\34\f\34\16\34"+
		"\u018f\13\34\3\34\5\34\u0192\n\34\3\35\3\35\3\35\3\35\7\35\u0198\n\35"+
		"\f\35\16\35\u019b\13\35\3\35\3\35\3\36\3\36\3\36\3\36\3\37\3\37\3\37\6"+
		"\37\u01a6\n\37\r\37\16\37\u01a7\3 \3 \3 \3 \3!\3!\3!\7!\u01b1\n!\f!\16"+
		"!\u01b4\13!\3\"\3\"\3\"\7\"\u01b9\n\"\f\"\16\"\u01bc\13\"\3\"\5\"\u01bf"+
		"\n\"\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\5#\u01d1\n#\3$\3"+
		"$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\7$\u01e2\n$\f$\16$\u01e5\13$"+
		"\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\5%\u01f8\n%\3&\3&"+
		"\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\5&\u0209\n&\3&\3&\3&\3&\3&\3&"+
		"\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\5&\u021d\n&\3&\3&\3&\3&\3&\3&\6&"+
		"\u0225\n&\r&\16&\u0226\3&\3&\3&\3&\3&\3&\3&\7&\u0230\n&\f&\16&\u0233\13"+
		"&\3&\3&\3&\3&\3&\3&\7&\u023b\n&\f&\16&\u023e\13&\3&\3&\3&\3&\3&\3&\7&"+
		"\u0246\n&\f&\16&\u0249\13&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\5&\u0255\n&\3"+
		"&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3"+
		"&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\7&\u027a\n&\f&\16&\u027d\13&\3\'\3"+
		"\'\3\'\3\'\7\'\u0283\n\'\f\'\16\'\u0286\13\'\3\'\3\'\3(\3(\3(\3(\7(\u028e"+
		"\n(\f(\16(\u0291\13(\3(\3(\3)\3)\3)\3)\3)\3)\3)\3)\5)\u029d\n)\3*\3*\3"+
		"*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\5*\u02ad\n*\3+\3+\3+\3+\3+\3+\3+\3"+
		"+\3+\3+\3+\3+\3+\3+\3+\5+\u02be\n+\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\5"+
		",\u02cb\n,\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\5-\u02d7\n-\3.\3.\3/\3/\3/\6"+
		"/\u02de\n/\r/\16/\u02df\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\7/\u02ec\n/\f/\16"+
		"/\u02ef\13/\3/\3/\3/\5/\u02f4\n/\3\60\3\60\5\60\u02f8\n\60\3\61\3\61\3"+
		"\61\3\61\3\62\3\62\5\62\u0300\n\62\3\63\3\63\5\63\u0304\n\63\3\64\3\64"+
		"\3\64\3\64\3\64\3\64\5\64\u030c\n\64\3\64\2\5.FJ\65\2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdf\2"+
		"\b\4\2\n\n\u0082\u0082\5\2JJLLOO\3\2XY\3\2\62\65\3\2\66\67\5\2\62\62\66"+
		"\66hh\2\u038b\2i\3\2\2\2\4o\3\2\2\2\6q\3\2\2\2\b\u008b\3\2\2\2\n\u0097"+
		"\3\2\2\2\f\u0099\3\2\2\2\16\u00a4\3\2\2\2\20\u00ac\3\2\2\2\22\u00b4\3"+
		"\2\2\2\24\u00b6\3\2\2\2\26\u00bd\3\2\2\2\30\u00c8\3\2\2\2\32\u00d2\3\2"+
		"\2\2\34\u00fe\3\2\2\2\36\u0100\3\2\2\2 \u0102\3\2\2\2\"\u0104\3\2\2\2"+
		"$\u010b\3\2\2\2&\u010f\3\2\2\2(\u011c\3\2\2\2*\u0128\3\2\2\2,\u013e\3"+
		"\2\2\2.\u0140\3\2\2\2\60\u017a\3\2\2\2\62\u017f\3\2\2\2\64\u0183\3\2\2"+
		"\2\66\u0188\3\2\2\28\u0193\3\2\2\2:\u019e\3\2\2\2<\u01a5\3\2\2\2>\u01a9"+
		"\3\2\2\2@\u01ad\3\2\2\2B\u01b5\3\2\2\2D\u01d0\3\2\2\2F\u01d2\3\2\2\2H"+
		"\u01f7\3\2\2\2J\u0254\3\2\2\2L\u027e\3\2\2\2N\u0289\3\2\2\2P\u029c\3\2"+
		"\2\2R\u02ac\3\2\2\2T\u02bd\3\2\2\2V\u02ca\3\2\2\2X\u02d6\3\2\2\2Z\u02d8"+
		"\3\2\2\2\\\u02f3\3\2\2\2^\u02f7\3\2\2\2`\u02f9\3\2\2\2b\u02fd\3\2\2\2"+
		"d\u0303\3\2\2\2f\u030b\3\2\2\2hj\5\4\3\2ih\3\2\2\2jk\3\2\2\2ki\3\2\2\2"+
		"kl\3\2\2\2l\3\3\2\2\2mp\5\22\n\2np\5\6\4\2om\3\2\2\2on\3\2\2\2p\5\3\2"+
		"\2\2qr\7\6\2\2rt\7\u0082\2\2su\5N(\2ts\3\2\2\2tu\3\2\2\2uv\3\2\2\2vx\7"+
		"q\2\2wy\5\b\5\2xw\3\2\2\2xy\3\2\2\2y{\3\2\2\2z|\5\f\7\2{z\3\2\2\2{|\3"+
		"\2\2\2|~\3\2\2\2}\177\5\20\t\2~}\3\2\2\2~\177\3\2\2\2\177\u0081\3\2\2"+
		"\2\u0080\u0082\5\34\17\2\u0081\u0080\3\2\2\2\u0081\u0082\3\2\2\2\u0082"+
		"\u0084\3\2\2\2\u0083\u0085\5\"\22\2\u0084\u0083\3\2\2\2\u0084\u0085\3"+
		"\2\2\2\u0085\u0087\3\2\2\2\u0086\u0088\5$\23\2\u0087\u0086\3\2\2\2\u0087"+
		"\u0088\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u008a\7r\2\2\u008a\7\3\2\2\2"+
		"\u008b\u008c\7\b\2\2\u008c\u008d\7s\2\2\u008d\u0092\5\n\6\2\u008e\u008f"+
		"\7z\2\2\u008f\u0091\5\n\6\2\u0090\u008e\3\2\2\2\u0091\u0094\3\2\2\2\u0092"+
		"\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0095\3\2\2\2\u0094\u0092\3\2"+
		"\2\2\u0095\u0096\7t\2\2\u0096\t\3\2\2\2\u0097\u0098\t\2\2\2\u0098\13\3"+
		"\2\2\2\u0099\u009a\7\t\2\2\u009a\u009e\7q\2\2\u009b\u009c\5\16\b\2\u009c"+
		"\u009d\7y\2\2\u009d\u009f\3\2\2\2\u009e\u009b\3\2\2\2\u009f\u00a0\3\2"+
		"\2\2\u00a0\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2"+
		"\u00a3\7r\2\2\u00a3\r\3\2\2\2\u00a4\u00a6\7\u0082\2\2\u00a5\u00a7\5L\'"+
		"\2\u00a6\u00a5\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00a9\3\2\2\2\u00a8\u00aa"+
		"\58\35\2\u00a9\u00a8\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\17\3\2\2\2\u00ab"+
		"\u00ad\5\22\n\2\u00ac\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00ac\3"+
		"\2\2\2\u00ae\u00af\3\2\2\2\u00af\21\3\2\2\2\u00b0\u00b5\5\26\f\2\u00b1"+
		"\u00b5\5\32\16\2\u00b2\u00b5\5\24\13\2\u00b3\u00b5\5\30\r\2\u00b4\u00b0"+
		"\3\2\2\2\u00b4\u00b1\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b4\u00b3\3\2\2\2\u00b5"+
		"\23\3\2\2\2\u00b6\u00b8\7\u0082\2\2\u00b7\u00b9\5N(\2\u00b8\u00b7\3\2"+
		"\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00bb\7^\2\2\u00bb"+
		"\u00bc\5J&\2\u00bc\25\3\2\2\2\u00bd\u00be\7w\2\2\u00be\u00c3\7\u0082\2"+
		"\2\u00bf\u00c0\7z\2\2\u00c0\u00c2\7\u0082\2\2\u00c1\u00bf\3\2\2\2\u00c2"+
		"\u00c5\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c6\3\2"+
		"\2\2\u00c5\u00c3\3\2\2\2\u00c6\u00c7\7x\2\2\u00c7\27\3\2\2\2\u00c8\u00c9"+
		"\7\u0082\2\2\u00c9\u00ca\7]\2\2\u00ca\u00cf\7\u0082\2\2\u00cb\u00cc\7"+
		"\3\2\2\u00cc\u00ce\7\u0082\2\2\u00cd\u00cb\3\2\2\2\u00ce\u00d1\3\2\2\2"+
		"\u00cf\u00cd\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\31\3\2\2\2\u00d1\u00cf"+
		"\3\2\2\2\u00d2\u00d3\7\7\2\2\u00d3\u00d5\7q\2\2\u00d4\u00d6\5<\37\2\u00d5"+
		"\u00d4\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6\u00d8\3\2\2\2\u00d7\u00d9\5B"+
		"\"\2\u00d8\u00d7\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00da\3\2\2\2\u00da"+
		"\u00db\7r\2\2\u00db\33\3\2\2\2\u00dc\u00dd\7\13\2\2\u00dd\u00de\7q\2\2"+
		"\u00de\u00e4\5\36\20\2\u00df\u00e0\7\f\2\2\u00e0\u00e1\7q\2\2\u00e1\u00e2"+
		"\5 \21\2\u00e2\u00e3\7r\2\2\u00e3\u00e5\3\2\2\2\u00e4\u00df\3\2\2\2\u00e4"+
		"\u00e5\3\2\2\2\u00e5\u00e7\3\2\2\2\u00e6\u00e8\5B\"\2\u00e7\u00e6\3\2"+
		"\2\2\u00e7\u00e8\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00ea\7r\2\2\u00ea"+
		"\u00ff\3\2\2\2\u00eb\u00ec\7\13\2\2\u00ec\u00ed\7q\2\2\u00ed\u00ee\7\f"+
		"\2\2\u00ee\u00ef\7q\2\2\u00ef\u00f0\5 \21\2\u00f0\u00f2\7r\2\2\u00f1\u00f3"+
		"\5B\"\2\u00f2\u00f1\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4"+
		"\u00f5\7r\2\2\u00f5\u00ff\3\2\2\2\u00f6\u00f7\7\13\2\2\u00f7\u00f8\7q"+
		"\2\2\u00f8\u00f9\5B\"\2\u00f9\u00fa\7r\2\2\u00fa\u00ff\3\2\2\2\u00fb\u00fc"+
		"\7\13\2\2\u00fc\u00fd\7q\2\2\u00fd\u00ff\7r\2\2\u00fe\u00dc\3\2\2\2\u00fe"+
		"\u00eb\3\2\2\2\u00fe\u00f6\3\2\2\2\u00fe\u00fb\3\2\2\2\u00ff\35\3\2\2"+
		"\2\u0100\u0101\5<\37\2\u0101\37\3\2\2\2\u0102\u0103\5<\37\2\u0103!\3\2"+
		"\2\2\u0104\u0105\7\n\2\2\u0105\u0106\7q\2\2\u0106\u0107\5B\"\2\u0107\u0108"+
		"\7r\2\2\u0108#\3\2\2\2\u0109\u010c\5&\24\2\u010a\u010c\5*\26\2\u010b\u0109"+
		"\3\2\2\2\u010b\u010a\3\2\2\2\u010c\u010d\3\2\2\2\u010d\u010b\3\2\2\2\u010d"+
		"\u010e\3\2\2\2\u010e%\3\2\2\2\u010f\u0110\7\u0082\2\2\u0110\u0112\7q\2"+
		"\2\u0111\u0113\5(\25\2\u0112\u0111\3\2\2\2\u0112\u0113\3\2\2\2\u0113\u0115"+
		"\3\2\2\2\u0114\u0116\5<\37\2\u0115\u0114\3\2\2\2\u0115\u0116\3\2\2\2\u0116"+
		"\u0118\3\2\2\2\u0117\u0119\5B\"\2\u0118\u0117\3\2\2\2\u0118\u0119\3\2"+
		"\2\2\u0119\u011a\3\2\2\2\u011a\u011b\7r\2\2\u011b\'\3\2\2\2\u011c\u011d"+
		"\7\f\2\2\u011d\u011e\7s\2\2\u011e\u0123\7\u0082\2\2\u011f\u0120\7z\2\2"+
		"\u0120\u0122\7\u0082\2\2\u0121\u011f\3\2\2\2\u0122\u0125\3\2\2\2\u0123"+
		"\u0121\3\2\2\2\u0123\u0124\3\2\2\2\u0124\u0126\3\2\2\2\u0125\u0123\3\2"+
		"\2\2\u0126\u0127\7t\2\2\u0127)\3\2\2\2\u0128\u0129\7\u0082\2\2\u0129\u012a"+
		"\7\\\2\2\u012a\u012c\5,\27\2\u012b\u012d\7y\2\2\u012c\u012b\3\2\2\2\u012c"+
		"\u012d\3\2\2\2\u012d+\3\2\2\2\u012e\u012f\7_\2\2\u012f\u0130\5\64\33\2"+
		"\u0130\u0131\7b\2\2\u0131\u0132\5,\27\2\u0132\u013f\3\2\2\2\u0133\u0134"+
		"\7`\2\2\u0134\u0135\5\64\33\2\u0135\u0136\7b\2\2\u0136\u0137\5,\27\2\u0137"+
		"\u013f\3\2\2\2\u0138\u0139\7a\2\2\u0139\u013a\5\64\33\2\u013a\u013b\7"+
		"b\2\2\u013b\u013c\5,\27\2\u013c\u013f\3\2\2\2\u013d\u013f\5.\30\2\u013e"+
		"\u012e\3\2\2\2\u013e\u0133\3\2\2\2\u013e\u0138\3\2\2\2\u013e\u013d\3\2"+
		"\2\2\u013f-\3\2\2\2\u0140\u0141\b\30\1\2\u0141\u0142\5\60\31\2\u0142\u0157"+
		"\3\2\2\2\u0143\u0144\f\b\2\2\u0144\u0145\7g\2\2\u0145\u0156\5.\30\t\u0146"+
		"\u0147\f\7\2\2\u0147\u0148\7e\2\2\u0148\u0156\5.\30\b\u0149\u014a\f\6"+
		"\2\2\u014a\u014b\7f\2\2\u014b\u0156\5.\30\7\u014c\u014d\f\5\2\2\u014d"+
		"\u014e\7d\2\2\u014e\u0156\5.\30\6\u014f\u0150\f\4\2\2\u0150\u0151\7c\2"+
		"\2\u0151\u0156\5.\30\5\u0152\u0153\f\3\2\2\u0153\u0154\7b\2\2\u0154\u0156"+
		"\5.\30\4\u0155\u0143\3\2\2\2\u0155\u0146\3\2\2\2\u0155\u0149\3\2\2\2\u0155"+
		"\u014c\3\2\2\2\u0155\u014f\3\2\2\2\u0155\u0152\3\2\2\2\u0156\u0159\3\2"+
		"\2\2\u0157\u0155\3\2\2\2\u0157\u0158\3\2\2\2\u0158/\3\2\2\2\u0159\u0157"+
		"\3\2\2\2\u015a\u0166\7w\2\2\u015b\u015c\7\f\2\2\u015c\u015d\7s\2\2\u015d"+
		"\u0162\7\u0082\2\2\u015e\u015f\7z\2\2\u015f\u0161\7\u0082\2\2\u0160\u015e"+
		"\3\2\2\2\u0161\u0164\3\2\2\2\u0162\u0160\3\2\2\2\u0162\u0163\3\2\2\2\u0163"+
		"\u0165\3\2\2\2\u0164\u0162\3\2\2\2\u0165\u0167\7t\2\2\u0166\u015b\3\2"+
		"\2\2\u0166\u0167\3\2\2\2\u0167\u0169\3\2\2\2\u0168\u016a\5<\37\2\u0169"+
		"\u0168\3\2\2\2\u0169\u016a\3\2\2\2\u016a\u016f\3\2\2\2\u016b\u016d\7\3"+
		"\2\2\u016c\u016b\3\2\2\2\u016c\u016d\3\2\2\2\u016d\u016e\3\2\2\2\u016e"+
		"\u0170\5B\"\2\u016f\u016c\3\2\2\2\u016f\u0170\3\2\2\2\u0170\u0171\3\2"+
		"\2\2\u0171\u017b\7x\2\2\u0172\u0174\5\62\32\2\u0173\u0172\3\2\2\2\u0173"+
		"\u0174\3\2\2\2\u0174\u0175\3\2\2\2\u0175\u017b\5b\62\2\u0176\u0177\7s"+
		"\2\2\u0177\u0178\5,\27\2\u0178\u0179\7t\2\2\u0179\u017b\3\2\2\2\u017a"+
		"\u015a\3\2\2\2\u017a\u0173\3\2\2\2\u017a\u0176\3\2\2\2\u017b\61\3\2\2"+
		"\2\u017c\u017d\5b\62\2\u017d\u017e\7\u0086\2\2\u017e\u0180\3\2\2\2\u017f"+
		"\u017c\3\2\2\2\u0180\u0181\3\2\2\2\u0181\u017f\3\2\2\2\u0181\u0182\3\2"+
		"\2\2\u0182\63\3\2\2\2\u0183\u0186\5\66\34\2\u0184\u0185\7\3\2\2\u0185"+
		"\u0187\5D#\2\u0186\u0184\3\2\2\2\u0186\u0187\3\2\2\2\u0187\65\3\2\2\2"+
		"\u0188\u018d\5> \2\u0189\u018a\7y\2\2\u018a\u018c\5> \2\u018b\u0189\3"+
		"\2\2\2\u018c\u018f\3\2\2\2\u018d\u018b\3\2\2\2\u018d\u018e\3\2\2\2\u018e"+
		"\u0191\3\2\2\2\u018f\u018d\3\2\2\2\u0190\u0192\7y\2\2\u0191\u0190\3\2"+
		"\2\2\u0191\u0192\3\2\2\2\u0192\67\3\2\2\2\u0193\u0194\7w\2\2\u0194\u0199"+
		"\5:\36\2\u0195\u0196\7z\2\2\u0196\u0198\5:\36\2\u0197\u0195\3\2\2\2\u0198"+
		"\u019b\3\2\2\2\u0199\u0197\3\2\2\2\u0199\u019a\3\2\2\2\u019a\u019c\3\2"+
		"\2\2\u019b\u0199\3\2\2\2\u019c\u019d\7x\2\2\u019d9\3\2\2\2\u019e\u019f"+
		"\5b\62\2\u019f\u01a0\7\63\2\2\u01a0\u01a1\5b\62\2\u01a1;\3\2\2\2\u01a2"+
		"\u01a3\5> \2\u01a3\u01a4\7y\2\2\u01a4\u01a6\3\2\2\2\u01a5\u01a2\3\2\2"+
		"\2\u01a6\u01a7\3\2\2\2\u01a7\u01a5\3\2\2\2\u01a7\u01a8\3\2\2\2\u01a8="+
		"\3\2\2\2\u01a9\u01aa\5@!\2\u01aa\u01ab\7{\2\2\u01ab\u01ac\5J&\2\u01ac"+
		"?\3\2\2\2\u01ad\u01b2\5b\62\2\u01ae\u01af\7z\2\2\u01af\u01b1\5b\62\2\u01b0"+
		"\u01ae\3\2\2\2\u01b1\u01b4\3\2\2\2\u01b2\u01b0\3\2\2\2\u01b2\u01b3\3\2"+
		"\2\2\u01b3A\3\2\2\2\u01b4\u01b2\3\2\2\2\u01b5\u01ba\5D#\2\u01b6\u01b7"+
		"\7y\2\2\u01b7\u01b9\5D#\2\u01b8\u01b6\3\2\2\2\u01b9\u01bc\3\2\2\2\u01ba"+
		"\u01b8\3\2\2\2\u01ba\u01bb\3\2\2\2\u01bb\u01be\3\2\2\2\u01bc\u01ba\3\2"+
		"\2\2\u01bd\u01bf\7y\2\2\u01be\u01bd\3\2\2\2\u01be\u01bf\3\2\2\2\u01bf"+
		"C\3\2\2\2\u01c0\u01c1\7\r\2\2\u01c1\u01c2\5\64\33\2\u01c2\u01c3\7b\2\2"+
		"\u01c3\u01c4\5D#\2\u01c4\u01d1\3\2\2\2\u01c5\u01c6\7\16\2\2\u01c6\u01c7"+
		"\5\64\33\2\u01c7\u01c8\7b\2\2\u01c8\u01c9\5D#\2\u01c9\u01d1\3\2\2\2\u01ca"+
		"\u01cb\7\17\2\2\u01cb\u01cc\5\64\33\2\u01cc\u01cd\7b\2\2\u01cd\u01ce\5"+
		"D#\2\u01ce\u01d1\3\2\2\2\u01cf\u01d1\5F$\2\u01d0\u01c0\3\2\2\2\u01d0\u01c5"+
		"\3\2\2\2\u01d0\u01ca\3\2\2\2\u01d0\u01cf\3\2\2\2\u01d1E\3\2\2\2\u01d2"+
		"\u01d3\b$\1\2\u01d3\u01d4\5H%\2\u01d4\u01e3\3\2\2\2\u01d5\u01d6\f\7\2"+
		"\2\u01d6\u01d7\7l\2\2\u01d7\u01e2\5F$\b\u01d8\u01d9\f\6\2\2\u01d9\u01da"+
		"\7k\2\2\u01da\u01e2\5F$\7\u01db\u01dc\f\5\2\2\u01dc\u01dd\7j\2\2\u01dd"+
		"\u01e2\5F$\6\u01de\u01df\f\4\2\2\u01df\u01e0\7i\2\2\u01e0\u01e2\5F$\5"+
		"\u01e1\u01d5\3\2\2\2\u01e1\u01d8\3\2\2\2\u01e1\u01db\3\2\2\2\u01e1\u01de"+
		"\3\2\2\2\u01e2\u01e5\3\2\2\2\u01e3\u01e1\3\2\2\2\u01e3\u01e4\3\2\2\2\u01e4"+
		"G\3\2\2\2\u01e5\u01e3\3\2\2\2\u01e6\u01e7\7h\2\2\u01e7\u01f8\5H%\2\u01e8"+
		"\u01e9\5b\62\2\u01e9\u01ea\7\u0086\2\2\u01ea\u01eb\7\n\2\2\u01eb\u01f8"+
		"\3\2\2\2\u01ec\u01f8\7m\2\2\u01ed\u01f8\7n\2\2\u01ee\u01ef\5J&\2\u01ef"+
		"\u01f0\5`\61\2\u01f0\u01f1\5J&\2\u01f1\u01f8\3\2\2\2\u01f2\u01f8\5J&\2"+
		"\u01f3\u01f4\7s\2\2\u01f4\u01f5\5D#\2\u01f5\u01f6\7t\2\2\u01f6\u01f8\3"+
		"\2\2\2\u01f7\u01e6\3\2\2\2\u01f7\u01e8\3\2\2\2\u01f7\u01ec\3\2\2\2\u01f7"+
		"\u01ed\3\2\2\2\u01f7\u01ee\3\2\2\2\u01f7\u01f2\3\2\2\2\u01f7\u01f3\3\2"+
		"\2\2\u01f8I\3\2\2\2\u01f9\u01fa\b&\1\2\u01fa\u01fb\5P)\2\u01fb\u01fc\5"+
		"J&\37\u01fc\u0255\3\2\2\2\u01fd\u01fe\5X-\2\u01fe\u01ff\5J&\36\u01ff\u0255"+
		"\3\2\2\2\u0200\u0201\t\3\2\2\u0201\u0202\7s\2\2\u0202\u0203\5J&\2\u0203"+
		"\u0204\7t\2\2\u0204\u0255\3\2\2\2\u0205\u0206\7o\2\2\u0206\u0208\7\u0082"+
		"\2\2\u0207\u0209\5L\'\2\u0208\u0207\3\2\2\2\u0208\u0209\3\2\2\2\u0209"+
		"\u0255\3\2\2\2\u020a\u0255\5f\64\2\u020b\u020c\7Z\2\2\u020c\u020d\5\64"+
		"\33\2\u020d\u020e\7b\2\2\u020e\u020f\5\\/\2\u020f\u0255\3\2\2\2\u0210"+
		"\u0211\t\4\2\2\u0211\u0255\5J&\20\u0212\u0213\5b\62\2\u0213\u0214\5N("+
		"\2\u0214\u0255\3\2\2\2\u0215\u0216\5b\62\2\u0216\u0217\5L\'\2\u0217\u0255"+
		"\3\2\2\2\u0218\u0219\7q\2\2\u0219\u021c\5\64\33\2\u021a\u021b\7b\2\2\u021b"+
		"\u021d\5J&\2\u021c\u021a\3\2\2\2\u021c\u021d\3\2\2\2\u021d\u021e\3\2\2"+
		"\2\u021e\u021f\7r\2\2\u021f\u0255\3\2\2\2\u0220\u0221\7s\2\2\u0221\u0224"+
		"\5J&\2\u0222\u0223\7z\2\2\u0223\u0225\5J&\2\u0224\u0222\3\2\2\2\u0225"+
		"\u0226\3\2\2\2\u0226\u0224\3\2\2\2\u0226\u0227\3\2\2\2\u0227\u0228\3\2"+
		"\2\2\u0228\u0229\7t\2\2\u0229\u0255\3\2\2\2\u022a\u0255\5\\/\2\u022b\u022c"+
		"\7q\2\2\u022c\u0231\5J&\2\u022d\u022e\7z\2\2\u022e\u0230\5J&\2\u022f\u022d"+
		"\3\2\2\2\u0230\u0233\3\2\2\2\u0231\u022f\3\2\2\2\u0231\u0232\3\2\2\2\u0232"+
		"\u0234\3\2\2\2\u0233\u0231\3\2\2\2\u0234\u0235\7r\2\2\u0235\u0255\3\2"+
		"\2\2\u0236\u0237\7u\2\2\u0237\u023c\5J&\2\u0238\u0239\7z\2\2\u0239\u023b"+
		"\5J&\2\u023a\u0238\3\2\2\2\u023b\u023e\3\2\2\2\u023c\u023a\3\2\2\2\u023c"+
		"\u023d\3\2\2\2\u023d\u023f\3\2\2\2\u023e\u023c\3\2\2\2\u023f\u0240\7v"+
		"\2\2\u0240\u0255\3\2\2\2\u0241\u0242\7w\2\2\u0242\u0247\5J&\2\u0243\u0244"+
		"\7z\2\2\u0244\u0246\5J&\2\u0245\u0243\3\2\2\2\u0246\u0249\3\2\2\2\u0247"+
		"\u0245\3\2\2\2\u0247\u0248\3\2\2\2\u0248\u024a\3\2\2\2\u0249\u0247\3\2"+
		"\2\2\u024a\u024b\7x\2\2\u024b\u0255\3\2\2\2\u024c\u0255\7\35\2\2\u024d"+
		"\u0255\7\36\2\2\u024e\u0255\7d\2\2\u024f\u0255\7\23\2\2\u0250\u0251\7"+
		"s\2\2\u0251\u0252\5J&\2\u0252\u0253\7t\2\2\u0253\u0255\3\2\2\2\u0254\u01f9"+
		"\3\2\2\2\u0254\u01fd\3\2\2\2\u0254\u0200\3\2\2\2\u0254\u0205\3\2\2\2\u0254"+
		"\u020a\3\2\2\2\u0254\u020b\3\2\2\2\u0254\u0210\3\2\2\2\u0254\u0212\3\2"+
		"\2\2\u0254\u0215\3\2\2\2\u0254\u0218\3\2\2\2\u0254\u0220\3\2\2\2\u0254"+
		"\u022a\3\2\2\2\u0254\u022b\3\2\2\2\u0254\u0236\3\2\2\2\u0254\u0241\3\2"+
		"\2\2\u0254\u024c\3\2\2\2\u0254\u024d\3\2\2\2\u0254\u024e\3\2\2\2\u0254"+
		"\u024f\3\2\2\2\u0254\u0250\3\2\2\2\u0255\u027b\3\2\2\2\u0256\u0257\f "+
		"\2\2\u0257\u0258\7\4\2\2\u0258\u027a\5J&!\u0259\u025a\f\35\2\2\u025a\u025b"+
		"\t\5\2\2\u025b\u027a\5J&\36\u025c\u025d\f\34\2\2\u025d\u025e\t\6\2\2\u025e"+
		"\u027a\5J&\35\u025f\u0260\f\33\2\2\u0260\u0261\7P\2\2\u0261\u027a\5J&"+
		"\34\u0262\u0263\f\32\2\2\u0263\u0264\5V,\2\u0264\u0265\5J&\33\u0265\u027a"+
		"\3\2\2\2\u0266\u0267\f\31\2\2\u0267\u0268\5T+\2\u0268\u0269\5J&\32\u0269"+
		"\u027a\3\2\2\2\u026a\u026b\f\30\2\2\u026b\u026c\5R*\2\u026c\u026d\5J&"+
		"\31\u026d\u027a\3\2\2\2\u026e\u026f\f\27\2\2\u026f\u0270\7\37\2\2\u0270"+
		"\u0271\5J&\2\u0271\u0272\7\37\2\2\u0272\u0273\5J&\30\u0273\u027a\3\2\2"+
		"\2\u0274\u0275\f\26\2\2\u0275\u0276\7\37\2\2\u0276\u027a\5J&\27\u0277"+
		"\u0278\f\24\2\2\u0278\u027a\5Z.\2\u0279\u0256\3\2\2\2\u0279\u0259\3\2"+
		"\2\2\u0279\u025c\3\2\2\2\u0279\u025f\3\2\2\2\u0279\u0262\3\2\2\2\u0279"+
		"\u0266\3\2\2\2\u0279\u026a\3\2\2\2\u0279\u026e\3\2\2\2\u0279\u0274\3\2"+
		"\2\2\u0279\u0277\3\2\2\2\u027a\u027d\3\2\2\2\u027b\u0279\3\2\2\2\u027b"+
		"\u027c\3\2\2\2\u027cK\3\2\2\2\u027d\u027b\3\2\2\2\u027e\u027f\7w\2\2\u027f"+
		"\u0284\5J&\2\u0280\u0281\7z\2\2\u0281\u0283\5J&\2\u0282\u0280\3\2\2\2"+
		"\u0283\u0286\3\2\2\2\u0284\u0282\3\2\2\2\u0284\u0285\3\2\2\2\u0285\u0287"+
		"\3\2\2\2\u0286\u0284\3\2\2\2\u0287\u0288\7x\2\2\u0288M\3\2\2\2\u0289\u028a"+
		"\7w\2\2\u028a\u028f\7\u0082\2\2\u028b\u028c\7z\2\2\u028c\u028e\7\u0082"+
		"\2\2\u028d\u028b\3\2\2\2\u028e\u0291\3\2\2\2\u028f\u028d\3\2\2\2\u028f"+
		"\u0290\3\2\2\2\u0290\u0292\3\2\2\2\u0291\u028f\3\2\2\2\u0292\u0293\7x"+
		"\2\2\u0293O\3\2\2\2\u0294\u029d\7\24\2\2\u0295\u029d\7\25\2\2\u0296\u029d"+
		"\7\27\2\2\u0297\u029d\7\30\2\2\u0298\u029d\7\31\2\2\u0299\u029d\7\32\2"+
		"\2\u029a\u029d\7\33\2\2\u029b\u029d\7\34\2\2\u029c\u0294\3\2\2\2\u029c"+
		"\u0295\3\2\2\2\u029c\u0296\3\2\2\2\u029c\u0297\3\2\2\2\u029c\u0298\3\2"+
		"\2\2\u029c\u0299\3\2\2\2\u029c\u029a\3\2\2\2\u029c\u029b\3\2\2\2\u029d"+
		"Q\3\2\2\2\u029e\u02ad\7 \2\2\u029f\u02ad\7!\2\2\u02a0\u02ad\7\"\2\2\u02a1"+
		"\u02ad\7#\2\2\u02a2\u02ad\7$\2\2\u02a3\u02ad\7%\2\2\u02a4\u02ad\7&\2\2"+
		"\u02a5\u02ad\7)\2\2\u02a6\u02ad\7\'\2\2\u02a7\u02ad\7(\2\2\u02a8\u02ad"+
		"\7?\2\2\u02a9\u02ad\7>\2\2\u02aa\u02ad\7=\2\2\u02ab\u02ad\7<\2\2\u02ac"+
		"\u029e\3\2\2\2\u02ac\u029f\3\2\2\2\u02ac\u02a0\3\2\2\2\u02ac\u02a1\3\2"+
		"\2\2\u02ac\u02a2\3\2\2\2\u02ac\u02a3\3\2\2\2\u02ac\u02a4\3\2\2\2\u02ac"+
		"\u02a5\3\2\2\2\u02ac\u02a6\3\2\2\2\u02ac\u02a7\3\2\2\2\u02ac\u02a8\3\2"+
		"\2\2\u02ac\u02a9\3\2\2\2\u02ac\u02aa\3\2\2\2\u02ac\u02ab\3\2\2\2\u02ad"+
		"S\3\2\2\2\u02ae\u02be\7,\2\2\u02af\u02be\7-\2\2\u02b0\u02be\78\2\2\u02b1"+
		"\u02be\79\2\2\u02b2\u02be\7:\2\2\u02b3\u02be\7;\2\2\u02b4\u02be\7.\2\2"+
		"\u02b5\u02be\7/\2\2\u02b6\u02be\7\60\2\2\u02b7\u02be\7\61\2\2\u02b8\u02be"+
		"\7@\2\2\u02b9\u02be\7A\2\2\u02ba\u02be\7B\2\2\u02bb\u02be\7C\2\2\u02bc"+
		"\u02be\7D\2\2\u02bd\u02ae\3\2\2\2\u02bd\u02af\3\2\2\2\u02bd\u02b0\3\2"+
		"\2\2\u02bd\u02b1\3\2\2\2\u02bd\u02b2\3\2\2\2\u02bd\u02b3\3\2\2\2\u02bd"+
		"\u02b4\3\2\2\2\u02bd\u02b5\3\2\2\2\u02bd\u02b6\3\2\2\2\u02bd\u02b7\3\2"+
		"\2\2\u02bd\u02b8\3\2\2\2\u02bd\u02b9\3\2\2\2\u02bd\u02ba\3\2\2\2\u02bd"+
		"\u02bb\3\2\2\2\u02bd\u02bc\3\2\2\2\u02beU\3\2\2\2\u02bf\u02cb\7J\2\2\u02c0"+
		"\u02cb\7K\2\2\u02c1\u02cb\7L\2\2\u02c2\u02cb\7O\2\2\u02c3\u02cb\7M\2\2"+
		"\u02c4\u02cb\7V\2\2\u02c5\u02cb\7W\2\2\u02c6\u02cb\7E\2\2\u02c7\u02cb"+
		"\7F\2\2\u02c8\u02cb\7G\2\2\u02c9\u02cb\7H\2\2\u02ca\u02bf\3\2\2\2\u02ca"+
		"\u02c0\3\2\2\2\u02ca\u02c1\3\2\2\2\u02ca\u02c2\3\2\2\2\u02ca\u02c3\3\2"+
		"\2\2\u02ca\u02c4\3\2\2\2\u02ca\u02c5\3\2\2\2\u02ca\u02c6\3\2\2\2\u02ca"+
		"\u02c7\3\2\2\2\u02ca\u02c8\3\2\2\2\u02ca\u02c9\3\2\2\2\u02cbW\3\2\2\2"+
		"\u02cc\u02d7\7\67\2\2\u02cd\u02d7\7N\2\2\u02ce\u02d7\7*\2\2\u02cf\u02d7"+
		"\7+\2\2\u02d0\u02d7\7Q\2\2\u02d1\u02d7\7R\2\2\u02d2\u02d7\7U\2\2\u02d3"+
		"\u02d7\7S\2\2\u02d4\u02d7\7T\2\2\u02d5\u02d7\7I\2\2\u02d6\u02cc\3\2\2"+
		"\2\u02d6\u02cd\3\2\2\2\u02d6\u02ce\3\2\2\2\u02d6\u02cf\3\2\2\2\u02d6\u02d0"+
		"\3\2\2\2\u02d6\u02d1\3\2\2\2\u02d6\u02d2\3\2\2\2\u02d6\u02d3\3\2\2\2\u02d6"+
		"\u02d4\3\2\2\2\u02d6\u02d5\3\2\2\2\u02d7Y\3\2\2\2\u02d8\u02d9\t\7\2\2"+
		"\u02d9[\3\2\2\2\u02da\u02dd\5b\62\2\u02db\u02dc\7\u0086\2\2\u02dc\u02de"+
		"\5b\62\2\u02dd\u02db\3\2\2\2\u02de\u02df\3\2\2\2\u02df\u02dd\3\2\2\2\u02df"+
		"\u02e0\3\2\2\2\u02e0\u02f4\3\2\2\2\u02e1\u02e2\7[\2\2\u02e2\u02e3\7s\2"+
		"\2\u02e3\u02e4\5^\60\2\u02e4\u02e5\7t\2\2\u02e5\u02f4\3\2\2\2\u02e6\u02e7"+
		"\5b\62\2\u02e7\u02e8\7s\2\2\u02e8\u02ed\5\\/\2\u02e9\u02ea\7z\2\2\u02ea"+
		"\u02ec\5\\/\2\u02eb\u02e9\3\2\2\2\u02ec\u02ef\3\2\2\2\u02ed\u02eb\3\2"+
		"\2\2\u02ed\u02ee\3\2\2\2\u02ee\u02f0\3\2\2\2\u02ef\u02ed\3\2\2\2\u02f0"+
		"\u02f1\7t\2\2\u02f1\u02f4\3\2\2\2\u02f2\u02f4\5^\60\2\u02f3\u02da\3\2"+
		"\2\2\u02f3\u02e1\3\2\2\2\u02f3\u02e6\3\2\2\2\u02f3\u02f2\3\2\2\2\u02f4"+
		"]\3\2\2\2\u02f5\u02f8\5b\62\2\u02f6\u02f8\5d\63\2\u02f7\u02f5\3\2\2\2"+
		"\u02f7\u02f6\3\2\2\2\u02f8_\3\2\2\2\u02f9\u02fa\7\u0087\2\2\u02fa\u02fb"+
		"\5b\62\2\u02fb\u02fc\7\u0087\2\2\u02fca\3\2\2\2\u02fd\u02ff\7\u0082\2"+
		"\2\u02fe\u0300\7\5\2\2\u02ff\u02fe\3\2\2\2\u02ff\u0300\3\2\2\2\u0300c"+
		"\3\2\2\2\u0301\u0304\7\u0088\2\2\u0302\u0304\7\u0089\2\2\u0303\u0301\3"+
		"\2\2\2\u0303\u0302\3\2\2\2\u0304e\3\2\2\2\u0305\u030c\7}\2\2\u0306\u030c"+
		"\7|\2\2\u0307\u030c\7~\2\2\u0308\u030c\7\177\2\2\u0309\u030c\7\u0080\2"+
		"\2\u030a\u030c\7\u0081\2\2\u030b\u0305\3\2\2\2\u030b\u0306\3\2\2\2\u030b"+
		"\u0307\3\2\2\2\u030b\u0308\3\2\2\2\u030b\u0309\3\2\2\2\u030b\u030a\3\2"+
		"\2\2\u030cg\3\2\2\2Okotx{~\u0081\u0084\u0087\u0092\u00a0\u00a6\u00a9\u00ae"+
		"\u00b4\u00b8\u00c3\u00cf\u00d5\u00d8\u00e4\u00e7\u00f2\u00fe\u010b\u010d"+
		"\u0112\u0115\u0118\u0123\u012c\u013e\u0155\u0157\u0162\u0166\u0169\u016c"+
		"\u016f\u0173\u017a\u0181\u0186\u018d\u0191\u0199\u01a7\u01b2\u01ba\u01be"+
		"\u01d0\u01e1\u01e3\u01f7\u0208\u021c\u0226\u0231\u023c\u0247\u0254\u0279"+
		"\u027b\u0284\u028f\u029c\u02ac\u02bd\u02ca\u02d6\u02df\u02ed\u02f3\u02f7"+
		"\u02ff\u0303\u030b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}