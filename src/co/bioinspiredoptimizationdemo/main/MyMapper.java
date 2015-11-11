/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.bioinspiredoptimizationdemo.main;

import java.util.ArrayList;
import java.util.List;

import org.jzy3d.plot3d.builder.Mapper;

import co.mechanism.core.ICostFunction;
//import net.masagroup.jzy3d.plot3d.builder.Mapper;




/**
 *
 * @author william
 */
public class MyMapper extends Mapper{

    ICostFunction function;

    public MyMapper(ICostFunction f) {
        this.function = f;
    }



    @Override
    public double f(double x, double y) {
        List<Double> l = new ArrayList<Double>();
        l.add(x);
        l.add(y);
        try {
            return function.evaluate(l);
        } catch (Exception ex) {
           ex.printStackTrace();
        }
        return 0;
    }

}
