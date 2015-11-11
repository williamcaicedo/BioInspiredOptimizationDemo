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
public class GriewankFunction implements ICostFunction{

    public double evaluate(List<Double> inputs) throws Exception {
        return 1+((Math.pow((inputs.get(0)-100), 2)+Math.pow((inputs.get(1)-100), 2))/4000)
                -Math.cos((inputs.get(0)-100)/Math.sqrt(2))*Math.cos((inputs.get(1)-100)/Math.sqrt(2));
    }
    
    public String toString() {
    	return "GriewankFunction";
    }

}
