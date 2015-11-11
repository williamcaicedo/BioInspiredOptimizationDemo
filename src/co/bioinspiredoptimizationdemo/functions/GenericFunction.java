package co.bioinspiredoptimizationdemo.functions;

import java.util.List;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import co.mechanism.core.ICostFunction;

public class GenericFunction implements ICostFunction{
	
	private String f;
	public GenericFunction(String f) {
		this.f = f;
	}

	@Override
	public double evaluate(List<Double> inputs) {
		// TODO Auto-generated method stub
		String fx = f.replaceAll("x", inputs.get(0).toString());
		fx = fx.replaceAll("y", inputs.get(1).toString());
		Evaluator e = new Evaluator();
		try {
			return Double.parseDouble(e.evaluate(fx));
		} catch (EvaluationException e1) {
			fx = f.replaceAll("x","0");
			fx = fx.replaceAll("y","0");
			try {
			return Double.parseDouble(e.evaluate(fx));
			}catch(Exception e2) {return Double.NaN;}
		}
	}
	
	public String toString() {
    	return "GenericFunction";
    }

}
