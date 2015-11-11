package co.bioinspiredoptimizationdemo.functions;

import java.util.List;

import co.mechanism.core.ICostFunction;

public class EggholderFunction implements ICostFunction {

	@Override
	public double evaluate(List<Double> inputs) throws Exception {
		// TODO Auto-generated method stub
		return -(inputs.get(1) + 47)*Math.sin(Math.sqrt(Math.abs(inputs.get(1) + inputs.get(0)/2 + 47)))
				- inputs.get(0)*Math.sin(Math.sqrt(Math.abs(inputs.get(0) - (inputs.get(1) + 47))));
	}

}
