package co.bioinspiredoptimizationdemo.functions;

import java.util.List;

import co.mechanism.core.ICostFunction;

public class SchafferFunction implements ICostFunction {

	@Override
	public double evaluate(List<Double> inputs) throws Exception {
		// TODO Auto-generated method stub
		Double x = inputs.get(0);
		Double y = inputs.get(1);
		return (0.5 + (Math.pow(Math.sin(Math.sqrt(x*x + y*y)),2) - 0.5)/(1 + 0.001*(x*x+y*y)));
	}

	@Override
	public String toString() {
		return "SchafferFunction";
	}

}
