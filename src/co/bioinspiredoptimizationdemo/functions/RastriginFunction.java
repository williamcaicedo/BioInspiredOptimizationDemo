/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.bioinspiredoptimizationdemo.functions;

import java.util.List;

import co.mechanism.core.ICostFunction;


/**
 *
 * @author william
 */
public class RastriginFunction implements ICostFunction{

    public double evaluate(List<Double> inputs) throws Exception {
        return 20 + Math.pow(inputs.get(0),2) + Math.pow(inputs.get(1),2) - 10*(Math.cos(2*Math.PI*inputs.get(0))+Math.cos(2*Math.PI*inputs.get(1)));
    }

    public String toString() {
    	return "RastriginFunction";
    }
}
