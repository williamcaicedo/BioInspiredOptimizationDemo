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
public class ParaboloidFunction implements ICostFunction{

    public double evaluate(List<Double> inputs) throws Exception {
        return Math.pow(inputs.get(0),2)+(Math.pow(inputs.get(1),2));
    }
    public String toString() {
    	return "ParaboloidFunction";
    }

}
