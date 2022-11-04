/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.biome;

/**
 *
 * @author matthewferguson
 */
public record SampleTarget(double temperature, double humidity, double continentalness, double erosion, double weirdness, double depth) {
    @Override
    public String toString()
    {
        return "t: "+Double.toString(temperature)+"h: "+Double.toString(humidity)+"c: "+Double.toString(continentalness)+"e: "+Double.toString(erosion)+"w: "+Double.toString(weirdness)+"d: "+Double.toString(depth);
        
    }
}
