// Mathematical expressions.

package com.formula.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.formula.entity.Matrix;

/**
 * A mathematical expression, built out of literal numbers, variables,
 * arithmetic and relational operators, and elementary functions. It can be
 * evaluated to get its value given its variables' current values. The operator
 * names are from java.lang.Math where possible.
 */
public abstract class Expr {

	/**
	 * Calculate the expression's value.
	 * 
	 * @return the value given the current variable values
	 */
	public abstract Object value();
	
	

	/** Binary operator: addition */
	public static final int ADD = 0;
	/** Binary operator: subtraction */
	public static final int SUB = 1;
	/** Binary operator: multiplication */
	public static final int MUL = 2;
	/** Binary operator: division */
	public static final int DIV = 3;
	/** Binary operator: exponentiation */
	public static final int POW = 4;
	/** Binary operator: arctangent */
	public static final int ATAN2 = 5;
	/** Binary operator: maximum */
	public static final int MAX = 6;
	/** Binary operator: minimum */
	public static final int MIN = 7;
	/** Binary operator: less than */
	public static final int LT = 8;
	/** Binary operator: less or equal */
	public static final int LE = 9;
	/** Binary operator: equality */
	public static final int EQ = 10;
	/** Binary operator: inequality */
	public static final int NE = 11;
	/** Binary operator: greater or equal */
	public static final int GE = 12;
	/** Binary operator: greater than */
	public static final int GT = 13;
	/** Binary operator: logical and */
	public static final int AND = 14;
	/** Binary operator: logical or */
	public static final int OR = 15;

	/** Unary operator: absolute value */
	public static final int ABS = 100;
	/** Unary operator: arccosine */
	public static final int ACOS = 101;
	/** Unary operator: arcsine */
	public static final int ASIN = 102;
	/** Unary operator: arctangent */
	public static final int ATAN = 103;
	/** Unary operator: ceiling */
	public static final int CEIL = 104;
	/** Unary operator: cosine */
	public static final int COS = 105;
	/** Unary operator: e to the x */
	public static final int EXP = 106;
	/** Unary operator: floor */
	public static final int FLOOR = 107;
	/** Unary operator: natural log */
	public static final int LOG = 108;
	/** Unary operator: negation */
	public static final int NEG = 109;
	/** Unary operator: rounding */
	public static final int ROUND = 110;
	/** Unary operator: sine */
	public static final int SIN = 111;
	/** Unary operator: square root */
	public static final int SQRT = 112;
	/** Unary operator: tangent */
	public static final int TAN = 113;
	/** Aggregate operator: average */
	public static final int AVERAGE = 114;
	/** Aggregate operator: maximum */
	public static final int MAXIMUM = 115;
	/** Aggregate operator: minimum */
	public static final int MINIMUM = 116;
	/** Aggregate operator: count */
	public static final int COUNT = 117;
	/** Aggregate operator: sum */
	public static final int SUM = 118;
	/** Interpolation operator: SingleLevelInterpolation */
	public static final int SINGLELEVELINTERPOLATION  = 119;
	/** Interpolation operator: TwoLevelInterpolation */
	public static final int TWOLEVELINTERPOLATION  = 120;
	/** Interpolation operator: ThreeLevelInterpolation */
	public static final int THREELEVELINTERPOLATION  = 121;
	/** Interpolation operator: FourLevelInterpolation */
	public static final int FOURLEVELINTERPOLATION  = 122;
	

	/**
	 * Make a literal expression.
	 * 
	 * @param v
	 *            the constant value of the expression
	 * @return an expression whose value is always v
	 */
	public static Expr makeLiteral(double v) {
		return new LiteralExpr(v);
	}

	/**
	 * Make an expression that applies a unary operator to an operand.
	 * 
	 * @param rator
	 *            a code for a unary operator
	 * @param rand
	 *            operand
	 * @return an expression meaning rator(rand)
	 */
	public static Expr makeApp1(int rator, Expr rand) {
		Expr app = new UnaryExpr(rator, rand);
		return rand instanceof LiteralExpr ? new LiteralExpr((double)app.value()) : app;
	}
	
	public static Expr makeApp3(int rator, Expr rand) {
		Expr app = new AggregateExpr(rator, rand);
		return rand instanceof LiteralExpr ? new LiteralExpr((double)app.value()) : app;
	}
	
	public static Expr makeApp4(int rator, Expr rand1, Expr matrixId) {
		Expr app = new InterpolationExpr(rator,rand1,null,null,null, matrixId); 
		return rand1 instanceof LiteralExpr && matrixId instanceof LiteralExpr  ? new LiteralExpr((double)app.value()) : app;
	}
	
	public static Expr makeApp5(int rator, Expr rand1,Expr rand2, Expr matrixId) {
		Expr app = new InterpolationExpr(rator,rand1,rand2,null,null, matrixId); 
		return rand1 instanceof LiteralExpr && rand2 instanceof LiteralExpr && matrixId instanceof LiteralExpr  ? new LiteralExpr((double)app.value()) : app;
	}
	public static Expr makeApp6(int rator, Expr rand1,Expr rand2,Expr rand3, Expr matrixId) {
		Expr app = new InterpolationExpr(rator,rand1,rand2,rand3,null, matrixId); 
		return rand1 instanceof LiteralExpr && rand2 instanceof LiteralExpr && rand3 instanceof LiteralExpr && matrixId instanceof LiteralExpr  ? new LiteralExpr((double)app.value()) : app;
	}
	public static Expr makeApp7(int rator, Expr rand1,Expr rand2,Expr rand3, Expr rand4,Expr matrixId) {
		Expr app = new InterpolationExpr(rator,rand1,rand2,rand3,rand4, matrixId); 
		return rand1 instanceof LiteralExpr && rand2 instanceof LiteralExpr && rand3 instanceof LiteralExpr && rand4 instanceof LiteralExpr && matrixId instanceof LiteralExpr  ? new LiteralExpr((double)app.value()) : app;
	}
	
	/**
	 * Make an expression that applies a binary operator to two operands.
	 * 
	 * @param rator
	 *            a code for a binary operator
	 * @param rand0
	 *            left operand
	 * @param rand1
	 *            right operand
	 * @return an expression meaning rator(rand0, rand1)
	 */
	public static Expr makeApp2(int rator, Expr rand0, Expr rand1) {
		Expr app = new BinaryExpr(rator, rand0, rand1);
		return rand0 instanceof LiteralExpr && rand1 instanceof LiteralExpr ? new LiteralExpr((double)app.value()) : app;
	}

	/**
	 * Make a conditional expression.
	 * 
	 * @param test
	 *            `if' part
	 * @param consequent
	 *            `then' part
	 * @param alternative
	 *            `else' part
	 * @return an expression meaning `if test, then consequent, else
	 *         alternative'
	 */
	public static Expr makeIfThenElse(Expr test, Expr consequent, Expr alternative) {
		Expr cond = new ConditionalExpr(test, consequent, alternative);
		if (test instanceof LiteralExpr)
			return (double)test.value() != 0 ? consequent : alternative;
		else
			return cond;
	}

	@Override
	public String toString() {
		return "Expr [value()=" + value() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}

// These classes are all private to this module because we could
// plausibly want to do it in a completely different way, such as a
// stack machine.

class LiteralExpr extends Expr {
	double v;

	LiteralExpr(double v) {
		this.v = v;
	}

	public Object value() {
		return v;
	}
}

class AggregateExpr extends Expr {
	int rator;
	Expr rand;

	AggregateExpr(int rator, Expr rand) {
		this.rator = rator;
		this.rand = rand;
	}

	public Object value() {

		if (rand.value() instanceof String) {
			String part = (String) rand.value();
			String[] parts = part.split(",");
			double[] arg = new double[parts.length];
			for (int i = 0; parts.length > i; i++) {
				arg[i] = Double.parseDouble(parts[i]);
			}

			switch (rator) {
			case AVERAGE:
				return average(arg);
			case MAXIMUM:
				return maximum(arg);
			case MINIMUM:
				return minimum(arg);
			case COUNT:
				return (double) arg.length;
			case SUM:
				return sum(arg);
			default:
				throw new RuntimeException("BUG: bad rator for AggregateExpr");
			}
		} else {
			return (Object) rand.value();
		}
	}
	
	public double average(double[] arg) {
		double avg = 0.0;
		for(int i=0; arg.length>i; i++){
			avg = avg+arg[i];
		}
		avg =avg/arg.length;
		return avg;
	}
	
	public double maximum(double[] arg) {
		double max = arg[0];
		for(int i=0; arg.length>i; i++){
			max = max < arg[i] ? arg[i] : max;
		}
		return max;
	}
	
	public double minimum(double[] arg) {
		double min = arg[0];
		for(int i=0; arg.length>i; i++){
			min = min < arg[i] ? min : arg[i];
		}
		return min;
	}
	
	public double sum(double[] arg) {
		double sum = 0.0;
		for(int i=0; arg.length>i; i++){
			sum = sum+arg[i];
		}
		return sum;
	}
}

class InterpolationExpr extends Expr {
	int rator;
	Expr rand1;
	Expr rand2;
	Expr rand3;
	Expr rand4;
	Expr matrixid;

	InterpolationExpr(int rator,Expr rand1,Expr rand2,Expr rand3,Expr rand4,Expr rand5) {
		this.rator = rator;
		this.rand1 = rand1;
		this.rand2 = rand2;
		this.rand3 = rand3;
		this.rand4 = rand4;
		this.matrixid = rand5;
	}

	public Object value() {
			
			double columnOne = (double) rand1.value();
			double columnTwo = 0.0;
			double columnThree = 0.0;
			double columnFour = 0.0;
			if(rand2!=null){
				columnTwo = (double) rand2.value();
			}
			if(rand3!=null){
				columnThree = (double) rand3.value();
			}
			if(rand4!=null){
				columnFour = (double) rand4.value();
			}
			double matrixId = (double) matrixid.value();
			
			List<Matrix> matrixList  = getMatrix(matrixId);
			
			switch (rator) {
			case SINGLELEVELINTERPOLATION:
				return singleLevelInterpolation(columnOne,matrixId, matrixList);
			case TWOLEVELINTERPOLATION:
				return twoLevelInterpolation(columnOne,columnTwo,matrixId,matrixList);
			case THREELEVELINTERPOLATION:
				return threeLevelInterpolation(columnOne,columnTwo,columnThree,matrixId,matrixList);
			case FOURLEVELINTERPOLATION:
				return fourLevelInterpolation(columnOne,columnTwo,columnThree,columnFour,matrixId,matrixList);
			default:
				throw new RuntimeException("BUG: bad rator for InterplationExpr");
			}
		
	}
	
	
	public double singleLevelInterpolation(double columnOne,double matrixId, List<Matrix> matrixList) {
		return 0.0111;
	}
	
	public double twoLevelInterpolation(double columnOne,double columnTwo,double matrixId, List<Matrix> matrixList) {
		return 0.0222;
	}
	
	public double threeLevelInterpolation(double columnOne,double columnTwo,double columnThree,double matrixId,List<Matrix> matrixList) {
		return 0.0333;
	}
	public double fourLevelInterpolation(double columnOne,double columnTwo,double columnThree,double columnFour,double matrixId,List<Matrix> matrixList) {
		return 0.0444;
	}
	
	public double twoLevelInterpolation(double columnOne,double matrixId) {
		return 0.011111;
	}
	public List<Matrix> getMatrix(double matrixid) {
		
		Matrix matrix = new Matrix();
		List<Matrix> matrixList = new ArrayList<Matrix>(); 
		EntityManagerFactory factoryOne = Persistence.createEntityManagerFactory("Application");
		EntityManager entitymanagerOne = factoryOne.createEntityManager();
		try {

			float matrixId = (float)matrixid ;
			Query query = entitymanagerOne.createQuery("select m from Matrix m where m.matrixId = :matrixId").setParameter("matrixId", matrixId); 
			List results = query.getResultList();
		
			for (Object object : results) {
				matrix = (Matrix) object;
				matrixList.add(matrix);
			}
			return matrixList;

		} catch (Exception e) {
			System.out.println("inside class: Expr.java,  method: getMatrix, Exception message :: ");
			return null;
		}
	}
}

class UnaryExpr extends Expr {
	int rator;
	Expr rand;

	UnaryExpr(int rator, Expr rand) {
		this.rator = rator;
		this.rand = rand;
	}

	public Object value() {
		double arg = 0.0;
		if(rand.value() instanceof Double){
			arg = (double) rand.value();
		}
		switch (rator) {
		case ABS:
			return Math.abs(arg);
		case ACOS:
			return Math.acos(arg);
		case ASIN:
			return Math.asin(arg);
		case ATAN:
			return Math.atan(arg);
		case CEIL:
			return Math.ceil(arg);
		case COS:
			return Math.cos(arg);
		case EXP:
			return Math.exp(arg);
		case FLOOR:
			return Math.floor(arg);
		case LOG:
			return Math.log(arg);
		case NEG:
			return -arg;
		case ROUND:
			return Math.rint(arg);
		case SIN:
			return Math.sin(arg);
		case SQRT:
			return Math.sqrt(arg);
		case TAN:
			return Math.tan(arg);
		default:
			throw new RuntimeException("BUG: bad rator for UnaryExpr");
		}
	}
}

class BinaryExpr extends Expr {
	int rator;
	Expr rand0, rand1;

	BinaryExpr(int rator, Expr rand0, Expr rand1) {
		this.rator = rator;
		this.rand0 = rand0;
		this.rand1 = rand1;
	}

	public Object value() {
		
		if(rand0.value() instanceof Double  && rand1.value() instanceof Double){
		double arg0 = (double)rand0.value();
		double arg1 = (double)rand1.value();
		switch (rator) {
		case ADD:
			return arg0 + arg1;
		case SUB:
			return arg0 - arg1;
		case MUL:
			return arg0 * arg1;
		case DIV:
			return arg0 / arg1; // division by 0 has IEEE 754 behavior
		case POW:
			return Math.pow(arg0, arg1);
		case ATAN2:
			return Math.atan2(arg0, arg1);
		case MAX:
			return arg0 < arg1 ? arg1 : arg0;
		case MIN:
			return arg0 < arg1 ? arg0 : arg1;
		case LT:
			return arg0 < arg1 ? 1.0 : 0.0;
		case LE:
			return arg0 <= arg1 ? 1.0 : 0.0;
		case EQ:
			return arg0 == arg1 ? 1.0 : 0.0;
		case NE:
			return arg0 != arg1 ? 1.0 : 0.0;
		case GE:
			return arg0 >= arg1 ? 1.0 : 0.0;
		case GT:
			return arg0 > arg1 ? 1.0 : 0.0;
		case AND:
			return arg0 != 0 && arg1 != 0 ? 1.0 : 0.0;
		case OR:
			return arg0 != 0 || arg1 != 0 ? 1.0 : 0.0;
		default:
			throw new RuntimeException("BUG: bad rator for Double data type in BinaryExpr ");
		}
		}else if(rand0.value() instanceof Date  && rand1.value() instanceof Date){
			Date arg0 = (Date)rand0.value();
			Date arg1 = (Date)rand1.value();
			switch (rator) {
			case GT:
				return arg0.after(arg1)? 1.0 : 0.0;
			case LT:
				return arg0.before(arg1) ? 1.0 : 0.0;
			case EQ:
				return arg0.equals(arg1) ? 1.0 : 0.0;	
			default:
				throw new RuntimeException("BUG: bad rator for date datatype in BinaryExpr");
			}
		}else{
			throw new RuntimeException("BUG: bad rator in BinaryExpr");
		}
	}
}

class ConditionalExpr extends Expr {
	Expr test, consequent, alternative;

	ConditionalExpr(Expr test, Expr consequent, Expr alternative) {
		this.test = test;
		this.consequent = consequent;
		this.alternative = alternative;
	}

	public Object value() {
		return (double)test.value() != 0 ? consequent.value() : alternative.value();
	}
}
