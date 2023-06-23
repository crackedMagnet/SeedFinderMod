/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.structures;

import com.crackedmagnet.seedfindermod.biome.QuickBiomeSource;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;




/**
 *
 * @author matthewferguson
 */
public  class GridStructure implements StructureFinder{
   StructureSet structureSet;
   Integer gridSize=null;
 

    public GridStructure(RegistryEntry<StructureSet> structureSetEntry) {
        if(structureSetEntry!=null)
            structureSet=structureSetEntry.value();
        else structureSet=null;
    }
    

   
   @Override
   public ChunkPos getChunk(long seed, ChunkPos reference)
   {
       StructurePlacement placement = structureSet.placement();
       if(placement instanceof RandomSpreadStructurePlacement rssp)
       {
           ChunkPos startChunk = rssp.getStartChunk(seed, reference.x, reference.z);
           return startChunk;
    
       }
       
       return new ChunkPos(placement.getLocatePos(reference));
   }
   
    public int getGridSize()
    {
        if(gridSize!=null) return gridSize;
        StructurePlacement placement = structureSet.placement();
        if(placement instanceof RandomSpreadStructurePlacement rssp)
        {
            gridSize=rssp.getSpacing();
        }
        else gridSize=0; //this is an issue need to check other types of placement.
        
        return gridSize;
        
    }
    
    /**
     * Checks biome for structure.  This method is intended to be overridden
     * @param biomeSource
     * @param chunk
     * @return true if the biomeCheck passes.
     */
   @Override
    public boolean isValid(long seed, QuickBiomeSource biomeSource, ChunkPos chunk)
    {
        return true; 
    }

    @Override
    public Set<ChunkPos> getChunksInRange(long seed, ChunkPos center, double maxBlockDistance) {
        double sqaureChunkMaxDist=(maxBlockDistance/16.0)*(maxBlockDistance/16.0);   
        double chunkRange=Math.ceil(maxBlockDistance/16.0);
        int localGridSize=this.getGridSize();
        int gridRange=(int)Math.ceil(chunkRange/(double) this.getGridSize());
        Set<ChunkPos> output=new HashSet<>();
        for(int x=-gridRange;x<=gridRange;x++)
            for(int z=-gridRange;z<=gridRange;z++)
            {
                ChunkPos cp=this.getChunk(seed, new ChunkPos(center.x+x*localGridSize, center.z+z*localGridSize));
                if(cp==null) continue;
                if(squareDist(center, cp)<=sqaureChunkMaxDist)
                    output.add(cp);
            }
        return output;
    }
    
     
    public Set<ChunkPos> getChunksInSquareRange(long seed, ChunkPos center, int chunkRange) {
        int localGridSize=this.getGridSize();
        int gridRange=(int)Math.ceil(chunkRange/(double) this.getGridSize());
        Set<ChunkPos> output=new HashSet<>();
        for(int x=-gridRange;x<=gridRange;x++)
            for(int z=-gridRange;z<=gridRange;z++)
            {
                ChunkPos cp=this.getChunk(seed, new ChunkPos(center.x+x*localGridSize, center.z+z*localGridSize));
                if(cp==null) continue;
                if(inSquareRange(center, cp, chunkRange)) output.add(cp);
            }
        return output;
    }
    
    private double squareDist(ChunkPos cp1, ChunkPos cp2)
    {
        int diffx=cp1.x-cp2.x;
        int diffz=cp1.z-cp2.z;
        return diffx*diffx+diffz*diffz;
    }
    
    private boolean inSquareRange(ChunkPos center, ChunkPos chunkPos, int chunkRange)
    {
        //probably a better way to do this.
        if(chunkPos.x<center.x-chunkRange) return false;
        if(chunkPos.z<center.z-chunkRange) return false;
        if(chunkPos.x>center.x+chunkRange) return false;
        if(chunkPos.z>center.z+chunkRange) return false;
        return true;
    }
     
     public boolean isBedrock()
     {
         return false;
     }
    
}
