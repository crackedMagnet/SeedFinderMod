/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.criteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.world.biome.Biome;

/**
 *
 * @author matthewferguson
 */
public class BiomeClause {
    
    Set<RegistryKey<Biome>> biomes;

    String description;
    public BiomeClause(Set<RegistryKey<Biome>> biomes, String description) {
        this.biomes = biomes;
        this.description=description;
    }

    protected BiomeClause(NbtCompound nbt)
    {
        this.biomes=new HashSet<>();
        this.description=nbt.getString("description");
        NbtList biomeNbtList=(NbtList) nbt.get("biomes");
        for(int i=0;i<biomeNbtList.size();i++)
        {
            RegistryKey<Biome> biomeKey = RegistryKey.of(RegistryKeys.BIOME, new Identifier(biomeNbtList.getString(i)));
            biomes.add(biomeKey);
        }
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
    
    public NbtCompound writeNbt(NbtCompound nbt)
    {
        nbt.putString("description", description);
        NbtList biomeList=new NbtList();
        List<RegistryKey<Biome>> biomeKeys=new ArrayList<>(biomes);
        for(int i=0;i<biomeKeys.size();i++)
        {
            Identifier identifier=biomeKeys.get(i).getValue();
            biomeList.add(i, NbtString.of(identifier.toString()));
        }
        nbt.put("biomes", biomeList);
        return nbt;
    }
    
    public static BiomeClause fromNbt(NbtCompound nbt)
    {
        return new BiomeClause(nbt);
    }
    
    
    
    
    
    
    
}
