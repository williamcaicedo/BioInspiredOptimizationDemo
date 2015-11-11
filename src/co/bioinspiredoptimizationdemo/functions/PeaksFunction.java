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
public class PeaksFunction implements ICostFunction{

    public double evaluate(List<Double> inputs) throws Exception {
        double x1 = inputs.get(0);
        double x2 = inputs.get(1);
        return 3*Math.pow(1-x1,2)*Math.exp(-(x1*x1+Math.pow(x2+1,2)))
                - 10*((x1/5) - Math.pow(x1,3)-Math.pow(x2,5))*Math.exp(-(x1*x1+x2*x2))
                -(1/3)*Math.exp(-(Math.pow(x1+1,2)+x2*x2));
    }
    
    public String toString() {
    	return "PeaksFunction";
    }

}
