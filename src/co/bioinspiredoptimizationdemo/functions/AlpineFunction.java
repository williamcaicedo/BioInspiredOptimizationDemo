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
public class AlpineFunction implements ICostFunction{

    public double evaluate(List<Double> inputs) throws Exception {
        return Math.abs(inputs.get(0)*Math.sin(inputs.get(0))+0.1*inputs.get(0)) +Math.abs(inputs.get(1)*Math.sin(inputs.get(1))+0.1*inputs.get(1));
    }
    public String toString() {
    	return "AlpineFunction";
    }

}
