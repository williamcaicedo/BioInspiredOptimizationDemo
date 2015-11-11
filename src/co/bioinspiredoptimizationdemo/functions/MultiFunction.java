package co.bioinspiredoptimizationdemo.functions;

import java.util.List;

import co.mechanism.core.ICostFunction;

public class MultiFunction implements ICostFunction {

	@Override
	public double evaluate(List<Double> inputs) throws Exception {
		// TODO Auto-generated method stub
		return -1*(inputs.get(0)*Math.sin(4*Math.PI*inputs.get(0)) - inputs.get(1)*Math.sin(4*Math.PI*inputs.get(1)+Math.PI)
		+1);
	}

	@Override
	public String toString() {
		return "MultiFunction";
	}

}
