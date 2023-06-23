/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.stats;

import java.util.function.Function;

/**
 *
 * @author matthewferguson
 */
public class MinMaxStats<T> {
    //should probably generalise this so average, mode,  median etc can be used
    
    T minT;
    double minMetric=Double.POSITIVE_INFINITY;
    T maxT;
    double maxMetric=Double.NEGATIVE_INFINITY;
    Function<T,Double> metricFunction;

    public MinMaxStats(Function<T, Double> metricFunction) {
        this.metricFunction = metricFunction;
    }
    
    
    boolean submit(T t)
    {
        double metric=metricFunction.apply(t);
        boolean change=false;
        if(metric<minMetric)
        {
            minMetric=metric;
            minT=t;
            change=true;
        }
        if(metric>maxMetric)
        {
            maxMetric=metric;
            maxT=t;
            change=true;
        }
        return change;
    }

    public T getMinT() {
        return minT;
    }

    public double getMinMetric() {
        return minMetric;
    }

    public double getMaxMetric() {
        return maxMetric;
    }

    public T getMaxT() {
        return maxT;
    }
    
    
    

            
    
}
