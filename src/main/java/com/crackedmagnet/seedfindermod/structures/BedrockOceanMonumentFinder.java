/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.structures;

import com.crackedmagnet.seedfindermod.biome.QuickBiomeSource;
import com.crackedmagnet.seedfindermod.biome.ReferenceData;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.Structures;

/**
 *
 * @author matthewferguson
 */
public class BedrockOceanMonumentFinder extends BedrockBiomeGridStructure{

    public BedrockOceanMonumentFinder() {
        super(Structures.MONUMENT, ReferenceData.validDeepOceanBiomes, 32, 5, 10387313, false, 16);
    }

    @Override
    public boolean isValid(long seed, QuickBiomeSource biomeSource, ChunkPos chunk) {
        boolean deepOceanCheck=super.isValid(seed, biomeSource, chunk);
        if(!deepOceanCheck) return false;
         
        return biomeCheck(biomeSource, chunk, ReferenceData.validOceanBiomes, 29);
        
    }
    
    
    
}
