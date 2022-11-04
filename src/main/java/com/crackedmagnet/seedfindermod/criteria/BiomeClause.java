/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.criteria;

import java.util.Map;
import java.util.Set;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

/**
 *
 * @author matthewferguson
 */
public class BiomeClause {
    //proportion
    //includes
    Set<RegistryKey<Biome>> biomes;

    String description;
    public BiomeClause(Set<RegistryKey<Biome>> biomes, String description) {
        this.biomes = biomes;
        this.description=description;
    }

    
    
    public boolean matches(Map<RegistryKey<Biome>,Integer> biomeCounts)
    {
        int totalCount=0;
        for(RegistryKey<Biome> biome:biomes)
            totalCount+=biomeCounts.getOrDefault(biome, 0);

        return totalCount>0;
    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public String toString() {
        return getDescription()+" exists with 500 blocks";
    }
    
    
    
    
    
    
    
    
}
