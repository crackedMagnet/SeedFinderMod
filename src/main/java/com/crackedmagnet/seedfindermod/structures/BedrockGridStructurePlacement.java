/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.structures;

import com.crackedmagnet.seedfindermod.rng.BedrockRandom;
import java.util.Optional;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;
import net.minecraft.world.gen.chunk.placement.StructurePlacementCalculator;
import net.minecraft.world.gen.chunk.placement.StructurePlacementType;
import net.minecraft.world.gen.noise.NoiseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class BedrockGridStructurePlacement extends StructurePlacement{

    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");
    int spacing;
    int separation;
    boolean linear;
    
    BedrockGridStructurePlacement(int spacing,int separation,boolean linear,int salt)
    {
        super(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.DEFAULT, 1.0F, salt, Optional.empty());
    
        this.spacing=spacing;
        this.separation=separation;
        this.linear=linear;
    }


    @Override
    protected boolean isStartChunk(StructurePlacementCalculator calculator, int chunkX, int chunkZ) {
        long seed = calculator.getStructureSeed();
        ChunkPos featureChunk = getPotentialFeatureChunk(seed, chunkX, chunkZ);
        boolean output = featureChunk.x == chunkX && featureChunk.z == chunkZ;
        return output;
    }
    
    
    @Override
    public StructurePlacementType<?> getType() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public ChunkPos getPotentialFeatureChunk(long seed, int chunkx, int chunkz) {
     
      int gridx = Math.floorDiv(chunkx, spacing);
      int gridz = Math.floorDiv(chunkz, spacing);
      long featureSeed = (long)gridx * 341873128712L + (long)gridz * 132897987541L + seed + (long)this.getSalt();
      BedrockRandom rand=new BedrockRandom((int) featureSeed);
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
