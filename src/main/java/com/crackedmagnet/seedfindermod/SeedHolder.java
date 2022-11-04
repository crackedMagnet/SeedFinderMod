/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod;

/**
 *
 * @author matthewferguson
 */
public class SeedHolder {
    private static Long currentSeed=null;
    private static Long origSeed=null;
    public static void setCurrentSeed(long seed)
    {
        currentSeed=seed;
    }
    public static Long getCurrentSeed()
    {
        return currentSeed;
    }

    public static Long getOrigSeed() {
        return origSeed;
    }

    public static void setOrigSeed(Long origSeed) {
        SeedHolder.origSeed = origSeed;
    }
    
    
}
