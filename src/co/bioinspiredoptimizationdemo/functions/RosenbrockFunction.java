package co.bioinspiredoptimizationdemo.functions;

import java.util.List;

import co.mechanism.core.ICostFunction;

public class RosenbrockFunction implements ICostFunction {

	@Override
	public double evaluate(List<Double> inputs) throws Exception {
		// TODO Auto-generated method stub
		return Math.pow(1-inputs.get(0), 2) + 100*Math.pow(inputs.get(1)-Math.pow(inputs.get(0), 2), 2);
	}

	@Override
	public String toString() {
		return "RosenbrockFunction";
	}
	
}
