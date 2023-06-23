/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.search;

/**
 *
 * @author matthewferguson
 */
public class LinearSeedProvider implements NextSeedProvider{

    long currentStructureSeed;
    long currentBiomeSeedIdx;
    private static final long MAX_STRUCTURE_SEED=0xFFFFFFFFFFFFl;
    private static final long MAX_BIOME_SEED_IDX=0xFFFF;
    

    public LinearSeedProvider(long currentStructureSeed, long currentBiomeSeedIdx) {
        this.currentStructureSeed = currentStructureSeed&MAX_STRUCTURE_SEED;
        this.currentBiomeSeedIdx = currentBiomeSeedIdx;
    }
    
    public LinearSeedProvider(long currentStructureSeed) {
        this.currentStructureSeed = currentStructureSeed;
        this.currentBiomeSeedIdx=0;
    }
    
    @Override
    public Long nextStructureSeed() {
        currentStructureSeed++;
        long seed=currentStructureSeed;
        if(currentStructureSeed>MAX_STRUCTURE_SEED) currentStructureSeed=0;
        currentBiomeSeedIdx=0; //reset on structure seed change.
        return seed;
    }

    @Override
    public Long nextBiomeSeed() {
        long idx=currentBiomeSeedIdx;
        currentBiomeSeedIdx++;
        if(currentBiomeSeedIdx>MAX_BIOME_SEED_IDX)
        {
            currentBiomeSeedIdx=0;
            return null;
        }
        return (idx<<48)|currentStructureSeed; 
    }
    
}
