/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.structures;

import com.crackedmagnet.seedfindermod.biome.QuickBiomeSource;
import com.crackedmagnet.seedfindermod.biome.ReferenceData;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;
import net.minecraft.world.gen.structure.Structures;

/**
 *
 * @author matthewferguson
 */
public class BedrockOceanMonumentFinder extends BedrockBiomeGridStructure{

    private static final RegistryEntry.Reference<Structure> structure = structureRegistry.getOrThrow(StructureKeys.MONUMENT);
    public BedrockOceanMonumentFinder() {
        super(structure, ReferenceData.validDeepOceanBiomes, 32, 5, 10387313, false, 16);
    }

    @Override
    public boolean isValid(long seed, QuickBiomeSource biomeSource, ChunkPos chunk) {
        boolean deepOceanCheck=super.isValid(seed, biomeSource, chunk);
        if(!deepOceanCheck) return false;
         
        return biomeCheck(biomeSource, chunk, ReferenceData.validOceanBiomes, 29);
        
    }
    
    
    
}
