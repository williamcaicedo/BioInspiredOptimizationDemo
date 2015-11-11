package co.bioinspiredoptimizationdemo.functions;

import java.util.List;

import co.mechanism.core.ICostFunction;

public class Levy13Function implements ICostFunction {

	@Override
	public double evaluate(List<Double> inputs) throws Exception {
		// TODO Auto-generated method stub
		return Math.pow(Math.sin(3*Math.PI*inputs.get(0)), 2) 
				+ Math.pow(inputs.get(0) - 1, 2)*(1 + Math.pow(Math.sin(3*Math.PI*inputs.get(1)), 2))
				+Math.pow((inputs.get(1) - 1), 2)*(1 + Math.pow(Math.sin(2*Math.PI*inputs.get(1)), 2));
	}
	
	public String toString() {
    	return "Levy13Function";
    }

}
