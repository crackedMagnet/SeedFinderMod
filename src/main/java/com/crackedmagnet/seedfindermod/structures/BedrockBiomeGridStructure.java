/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.structures;

import com.crackedmagnet.seedfindermod.biome.QuickBiomeSource;
import com.crackedmagnet.seedfindermod.rng.BedrockRandom;
import java.util.Set;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;

/**
 *
 * @author matthewferguson
 */
public class BedrockBiomeGridStructure extends BiomeGridStructure{

    
    int spacing;
    int separation;
    int salt;
    int checkRange;
    boolean linear;
    BedrockRandom rand=new BedrockRandom();
    RegistryEntry<Structure> structureEntry;

    public BedrockBiomeGridStructure(RegistryEntry<Structure> structureEntry, Set<RegistryKey<Biome>> biomes,  int spacing, int separation, int salt, boolean linear, int checkRange) {
        super(null, biomes);
        this.structureEntry=structureEntry;
        this.separation=separation;
        this.spacing = spacing;
        this.gridSize=spacing;
        this.salt = salt;
        
        this.linear = linear;
        this.checkRange = checkRange;
    }

    @Override
    public boolean isBedrock() {
        return true;
    }

    public RegistryEntry<Structure> getStructureEntry() {
        return structureEntry;
    }
    
    
    
    @Override
    public boolean isValid(long seed, QuickBiomeSource biomeSource, ChunkPos chunk) {
        if(checkRange==0) return true;
        /*int blockx=chunk.x*16+8;
        int blockz=chunk.z*16+8;
        for(int x=blockx-checkRange;x<=blockx+checkRange;x+=4)
            for(int z=blockz-checkRange;z<=blockz+checkRange;z+=4)
            {
                Biome surfaceBiome = biomeSource.getSurfaceBiome(x, z);
                if(!validBiomes.contains(surfaceBiome)) return false;
            }
        return true;*/
        return biomeCheck(biomeSource, chunk, validBiomes, checkRange);
    }
    public boolean biomeCheck(QuickBiomeSource biomeSource, ChunkPos chunk, Set<RegistryKey<Biome>> biomes, int range) {
        int blockx=chunk.x*16+8;
        int blockz=chunk.z*16+8;
        for(int x=blockx-range;x<=blockx+range;x+=4)
            for(int z=blockz-range;z<=blockz+range;z+=4)
            {
                RegistryKey<Biome> surfaceBiome = biomeSource.getSurfaceBiome(x, z);
                if(!biomes.contains(surfaceBiome)) return false;
            }
        return true;
    }

    @Override
    public ChunkPos getChunk(long seed, ChunkPos reference) {
        return getPotentialFeatureChunk(seed, reference.x, reference.z);
    }

     public final ChunkPos getPotentialFeatureChunk(long seed, int chunkx, int chunkz) {
     
      int gridx = Math.floorDiv(chunkx, spacing);
      int gridz = Math.floorDiv(chunkz, spacing);
      
      rand.setLargeFeatureWithSalt(seed, gridx, gridz, salt);
      int offsetx;
      int offsetz;
   
      if (linear) {
         offsetx = rand.nextInt(spacing - separation);
         offsetz = rand.nextInt(spacing - separation);
      } else {
         offsetx = (rand.nextInt(spacing - separation) + rand.nextInt(spacing - separation)) / 2;
         offsetz = (rand.nextInt(spacing - separation) + rand.nextInt(spacing - separation)) / 2;
      }

  
      return new ChunkPos(gridx * spacing + offsetx, gridz * spacing + offsetz);
   }
}
