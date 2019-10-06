/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

//import java.math.double;
import java.math.MathContext;
import java.util.ArrayList;

/**
 *
 * @author Ralph McDougall 27/12/2017
 */
public class Vector {
    public ArrayList<Double> v = new ArrayList<Double>();
    
    public Vector(double x, double y)
    {
        v = new ArrayList<Double>();
        v.add(x);
        v.add(y);
    }
    
    public void add(Vector f)
    {
        //System.out.println(f.v);
        this.v.set(0, this.v.get(0) + (f.v.get(0)));
        this.v.set(1, this.v.get(1) + (f.v.get(1)));
    }
    
}
