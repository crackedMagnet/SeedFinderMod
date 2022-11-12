/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.structures;

import com.crackedmagnet.seedfindermod.biome.QuickBiomeSource;
import com.crackedmagnet.seedfindermod.biome.ReferenceData;
import java.util.Set;
import net.minecraft.structure.StructureSets;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;

/**
 *
 * @author matthewferguson
 */
public class JavaPillagerOutpostFinder extends BiomeGridStructure {

    public JavaPillagerOutpostFinder() {
        super(StructureSets.PILLAGER_OUTPOSTS, ReferenceData.validPilagerOutpostBiomes);
    }

    
    
     
    private static final StructurePlacement.FrequencyReductionMethod FREQUENCY_REDUCTION = StructurePlacement.FrequencyReductionMethod.LEGACY_TYPE_1;

    @Override
    public ChunkPos getChunk(long seed, ChunkPos reference) {
        
        ChunkPos cp=super.getChunk(seed, reference); 
        if(!FREQUENCY_REDUCTION.shouldGenerate(seed, 165745296, cp.x, cp.z, 0.2f)) return null;
        return cp;
    }

    
    @Override
    public boolean isValid(long seed, QuickBiomeSource biomeSource, ChunkPos chunk) {
        boolean parentIsValid=super.isValid(seed, biomeSource, chunk);
        if(!parentIsValid) return false;
        GridStructure villageFinder = StructureFinders.JAVA_VILLAGE.value();
        Set<ChunkPos> possibleVillagesInRange = villageFinder.getChunksInSquareRange(seed, chunk, 10);
        for(ChunkPos villageChunk:possibleVillagesInRange)
        {
            if(villageFinder.isValid(seed, biomeSource, villageChunk)) return false;
        }
        return true;
    }


    
    
    
}
