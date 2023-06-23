/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.criteria;

import java.util.Map;
import java.util.Set;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;


/**
 *
 * @author matthewferguson
 */
public class BiomeProportionClause extends BiomeClause{

    int minPercent;

    public BiomeProportionClause(Set<RegistryKey<Biome>> biomes, String description, int minPercent) {
        super(biomes,description);
        this.minPercent = minPercent;
    }
    
    private BiomeProportionClause(NbtCompound nbt)
    {
        super(nbt);
        minPercent=nbt.getInt("minPercent");
    }
    
    
    @Override
    public boolean matches(Map<RegistryKey<Biome>, Integer> biomeCounts) {
        
        int total=0;
        for(Integer count:biomeCounts.values())
            total+=count;
        int count=0;
        for(RegistryKey<Biome> biome:biomes)
            count+=biomeCounts.getOrDefault(biome, 0);
        
        double biomePercent=(double) count/(double) total;
        biomePercent*=100d;
        return biomePercent>=minPercent;
    }

    @Override
    public String toString() {
        return getDescription()+" >="+Integer.toString(minPercent)+"% within 500 blocks";
    }

    public int getMinPercent() {
        return minPercent;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt=super.writeNbt(nbt);
        nbt.putInt("minPercent", minPercent);
        return nbt;
    }

    public static BiomeClause fromNbt(NbtCompound nbt) {
        return new BiomeProportionClause(nbt);
    }
    
    
    
    
    
    
    
}
