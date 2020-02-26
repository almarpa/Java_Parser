package transformador;

import java.util.LinkedList;
import java.util.List;

import japa.parser.ast.Node;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.BooleanLiteralExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.WhileStmt;
import japa.parser.ast.visitor.ModifierVisitorAdapter;

public class Visitador_2 extends ModifierVisitorAdapter<Object>
{
	
	// Reemplaza el cuerpo de los bucles "while" que solo tenga una instrucci—n,
	// por un bloque con esa œnica instrucci—n
	public Node visit(WhileStmt whileStmt, Object args)
	{
		
		// Cambiar las condiciones por true
		BooleanLiteralExpr condition = new BooleanLiteralExpr();
		condition.setValue(true);
		whileStmt.setCondition(condition);
		
		// Seguimos visitando el cuerpo del bucle
		return super.visit(whileStmt, args);
	}
}