/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.structures;

import com.crackedmagnet.seedfindermod.biome.QuickBiomeSource;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;

/**
 *
 * @author matthewferguson
 */
public class BiomeGridStructure extends GridStructure{

    public BiomeGridStructure(RegistryEntry<StructureSet> structureSetEntry, Set<RegistryKey<Biome>> biomes) {
        super(structureSetEntry);
        validBiomes.addAll(biomes);
    }

    Set<RegistryKey<Biome>> validBiomes=new HashSet<>();
    @Override
    public boolean isValid(long seed, QuickBiomeSource biomeSource, ChunkPos chunk) {
        RegistryKey<Biome> surfaceBiome = biomeSource.getSurfaceBiome(chunk.x*16, chunk.z*16);
        return validBiomes.contains(surfaceBiome);
        
    }
    

    
}
