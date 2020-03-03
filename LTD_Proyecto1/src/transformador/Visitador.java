package transformador;

import java.util.LinkedList;
import java.util.List;

import iter2rec.transformation.loop.Do;
import iter2rec.transformation.loop.Loop;
import iter2rec.transformation.loop.While;
import iter2rec.transformation.variable.LoopVariables;
import iter2rec.transformation.variable.Variable;
import japa.parser.ast.Node;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.ArrayAccessExpr;
import japa.parser.ast.expr.ArrayCreationExpr;
import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.CastExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.DoStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.WhileStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.Type;
import japa.parser.ast.visitor.ModifierVisitorAdapter;


public class Visitador extends ModifierVisitorAdapter<Object>
{
	/********************************************************/
	/********************** Atributos ***********************/
	/********************************************************/
	
	// Usamos un contador para numerar los métodos que creemos
	int contador=1;  
	// Variable usada para conocer la lista de métodos visitados
	LinkedList<MethodDeclaration> previousMethodDeclarations = new LinkedList<MethodDeclaration>();
	// Variable usada para saber cuál es el último método visitado (el que estoy visitando ahora)
	MethodDeclaration methodDeclaration;
	// Variable usada para conocer la lista de clases visitadas
	LinkedList<ClassOrInterfaceDeclaration> previousClassDeclarations = new LinkedList<ClassOrInterfaceDeclaration>();
	// Variable usada para saber cuál es la última clase visitada (la que estoy visitando ahora)	
	ClassOrInterfaceDeclaration classDeclaration;

	/********************************************************/
	/*********************** Metodos ************************/
	/********************************************************/

	// Visitador de clases
	// Este visitador no hace nada, simplemente registra en una lista las clases que se van visitando
	public Node visit(ClassOrInterfaceDeclaration classDeclaration, Object args)
	{
		this.previousClassDeclarations.add(classDeclaration);
		this.classDeclaration = classDeclaration;
		Node newClassDeclaration = super.visit(classDeclaration, args);
		this.previousClassDeclarations.removeLast();
		this.classDeclaration = this.previousClassDeclarations.isEmpty() ? null : this.previousClassDeclarations.getLast();
		
		return newClassDeclaration;
	}
	// Visitador de métodos
	// Este visitador no hace nada, simplemente registra en una lista los méetodos que se van visitando	
	public Node visit(MethodDeclaration methodDeclaration, Object args)
	{
		this.previousMethodDeclarations.add(methodDeclaration);
		this.methodDeclaration = methodDeclaration;
		Node newMethodDeclaration = super.visit(methodDeclaration, args);
		this.previousMethodDeclarations.removeLast();
		this.methodDeclaration = this.previousMethodDeclarations.isEmpty() ? null : this.previousMethodDeclarations.getLast();

		return newMethodDeclaration;
	}
	
	/**
	 * Visitador de sentencias DO WHILE
	 */
	public Node visit(DoStmt doStmt, Object args) 
	{
		BlockStmt blockStmt = new BlockStmt();
		List<Statement> statements = new LinkedList<Statement>();
		statements.add(doStmt.getBody());
		statements.add(oldWhile(doStmt,args));

		blockStmt.setStmts(statements);

		return blockStmt;
	}
	
	/**
	 * Visitador de sentencias WHILE
	 */	
	public Node visit(WhileStmt whileStmt, Object args){
		
		return oldWhile(whileStmt, args);
	}
	
	
	public IfStmt oldWhile(Statement stmt, Object args)
	{
		/**************************/
		/******** LLAMADOR ********/
		/**************************/		
		// Creamos un objeto Loop que sirve para examinar bucles
		Loop loop = null;
		if(stmt instanceof WhileStmt) {
			loop = new While(null, null, stmt);
		}else {
			loop = new Do(null, null, stmt);
		}
		
		// El objeto Loop nos calcula la lista de variables declaradas en el método y usadas en el bucle (la intersección)
		List<Variable> variables = loop.getUsedVariables(methodDeclaration);
		// Creamos un objeto LoopVariables que sirve para convertir la lista de variables en lista de argumentos y parámetros
		LoopVariables loopVariables = new LoopVariables(variables);
		// El objeto LoopVariables nos calcula la lista de argumentos del método 
		List<Expression> arguments = loopVariables.getArgs();
		
		//Object[] result = this.metodo_x()
		MethodCallExpr methodCall = new MethodCallExpr();
		methodCall.setName("metodo_"+contador);
		methodCall.setArgs(arguments);
		
		//Object[] result = this.metodo_x()
		ClassOrInterfaceType objType = new ClassOrInterfaceType();
		objType.setName("Object");
		
		//Object[] result = this.metodo_x()
		ReferenceType refType = new ReferenceType();
		refType.setArrayCount(1);
		refType.setType(objType);
		
		//Object[] result = this.metodo_x()
		NameExpr resultExpr = new NameExpr("result");
		List<VariableDeclarator> vars = new LinkedList<VariableDeclarator>();
		VariableDeclarator var = new VariableDeclarator();
		VariableDeclaratorId varId = new VariableDeclaratorId();
		varId.setName(resultExpr.getName());
		vars.add(var);
		var.setId(varId);
		var.setInit(methodCall);
		
		//Object[] result = this.metodo_x()
		VariableDeclarationExpr varDecExpr = new VariableDeclarationExpr();
		varDecExpr.setType(refType);
		varDecExpr.setVars(vars);
		
		//Object[] result = this.metodo_x()
		ExpressionStmt exprStmt = new ExpressionStmt();
		exprStmt.setExpression(varDecExpr);
		
		//NUEVO IF
		IfStmt newIf = new IfStmt();
		Expression condition = null;
		if(stmt instanceof DoStmt) {
			DoStmt doStmt = (DoStmt) stmt;
			condition = doStmt.getCondition();
		} else {
			WhileStmt whileStmt = (WhileStmt) stmt;
			condition = whileStmt.getCondition();
		}
		newIf.setCondition(condition);
		
		//NUEVO IF
		BlockStmt bloqueIf = new BlockStmt();

		//Lista de statements del nuevo IF
		List<Statement> cuerpoIf = new LinkedList<Statement>();
		cuerpoIf.add(exprStmt);
		
		//NUEVO IF
		for (int i = 0; i < variables.size(); i++) {
					
			ArrayAccessExpr expr = new ArrayAccessExpr();
			expr.setName(new NameExpr("result"));
			expr.setIndex(new IntegerLiteralExpr(i+""));
			
			CastExpr cast = new CastExpr();
			cast.setType(getWrapper(variables.get(i).getType()));
			cast.setExpr(expr);
			
			cuerpoIf.add(new ExpressionStmt(variables.get(i).getAssignationExpr(cast)));	
		}
		
		bloqueIf.setStmts(cuerpoIf);
		newIf.setThenStmt(bloqueIf);
		
		/**************************/
		/********* METODO *********/
		/**************************/
		MethodDeclaration methodDeclaration2 = new MethodDeclaration();
		methodDeclaration2.setName("metodo_"+contador);
		methodDeclaration2.setType(refType);
		methodDeclaration2.setModifiers(methodDeclaration.getModifiers());
		methodDeclaration2.setParameters(loopVariables.getParameters());
		
		//Return statement IF
		ReturnStmt returnIf = new ReturnStmt();
		MethodCallExpr methodExpression = new MethodCallExpr();
		methodExpression.setName("metodo_"+contador);
		methodExpression.setArgs(arguments);
		returnIf.setExpr(methodExpression);
		
		//Creamos el If recursivo 
		IfStmt ifRecursivo = new IfStmt();
		ifRecursivo.setCondition(condition);
		ifRecursivo.setThenStmt(returnIf); //TODO
		
		//Return Stmt Method
		ReturnStmt returnMetodo = new ReturnStmt();
		ArrayInitializerExpr aie = new ArrayInitializerExpr();
		aie.setValues(arguments);
		ArrayCreationExpr ace = new ArrayCreationExpr();
		ace.setType(objType);
		ace.setArrayCount(1);
		ace.setInitializer(aie);
		returnMetodo.setExpr(ace);
		
		//Bloque global del mÈtodo que contiene la lista de statements
		BlockStmt cuerpoMetodo = new BlockStmt();
		
		//Creamos una lista de statements y la rellenamos
		List<Statement> cuerpoMetodoStmts = new LinkedList<Statement>();
		if(stmt instanceof WhileStmt) {
			WhileStmt whileStmt = (WhileStmt) stmt;
			cuerpoMetodoStmts.add(whileStmt.getBody()); 
		} else {
			DoStmt doStmt = (DoStmt) stmt;
			cuerpoMetodoStmts.add(doStmt.getBody()); 
			
		}		
		cuerpoMetodoStmts.add(ifRecursivo);
		cuerpoMetodoStmts.add(returnMetodo);
		
		cuerpoMetodo.setStmts(cuerpoMetodoStmts);
		methodDeclaration2.setBody(cuerpoMetodo);
		
		//AÒadimos el nuevo metodo a la clase
		this.classDeclaration.getMembers().add(methodDeclaration2);
		
		//Aumentamos el contador para el nombre de los metodos
		contador++;

		return newIf;
	}

	
	// Dada un tipo, 
	// Si es un tipo primitivo, devuelve el wrapper correspondiente 
	// Si es un tipo no primitivo, lo devuelve
	private Type getWrapper(Type type)
	{
		if (!(type instanceof PrimitiveType))
			return type;
			
		PrimitiveType primitiveType = (PrimitiveType) type;
		String primitiveName = primitiveType.getType().name();
		String wrapperName = primitiveName;

		if (wrapperName.equals("Int"))
			wrapperName = "Integer";
		else if (wrapperName.equals("Char"))
			wrapperName = "Character";

		return new ClassOrInterfaceType(wrapperName);
	}
	// Dada una sentencia, 
	// Si es una única instrucción, devuelve un bloque equivalente 
	// Si es un bloque, lo devuelve
	private BlockStmt blockWrapper(Statement statement)
	{
		if (statement instanceof BlockStmt)
			return (BlockStmt) statement;

		BlockStmt block = new BlockStmt();
		List<Statement> blockStmts = new LinkedList<Statement>();
		blockStmts.add(statement);

		block.setStmts(blockStmts);

		return block;
	}
}