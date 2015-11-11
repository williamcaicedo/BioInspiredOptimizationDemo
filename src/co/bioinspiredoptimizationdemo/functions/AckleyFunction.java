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
public class AckleyFunction implements ICostFunction{

    public double evaluate(List<Double> inputs) throws Exception {
        double x = inputs.get(0);
        double y = inputs.get(1);
        return 20+Math.exp(1) -20*Math.exp(-0.2*Math.sqrt(0.5*(x*x+ y*y)))
               -Math.exp(0.5*(Math.cos(2*Math.PI*x)+Math.cos(2*Math.PI*y)));

       //return -20*Math.exp(-0.2*Math.sqrt(0.5*(x*x+y*y)))-Math.exp(0.5*(Math.cos(2*Math.PI*x)+Math.cos(2*Math.PI*y)))+20+Math.exp(1);
    }
    public String toString() {
    	return "AckleyFunction";
    }

}
