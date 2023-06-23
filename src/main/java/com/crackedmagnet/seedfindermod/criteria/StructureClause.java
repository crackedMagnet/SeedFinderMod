/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.criteria;

import com.crackedmagnet.seedfindermod.SeedFinderRegistries;
import com.crackedmagnet.seedfindermod.biome.QuickBiomeSource;
import com.crackedmagnet.seedfindermod.structures.BedrockBiomeGridStructure;
import com.crackedmagnet.seedfindermod.structures.GridStructure;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.structure.Structure;


/**
 *
 * @author matthewferguson
 */
public class StructureClause {

    GridStructure gridStructure;
    double maxDistance;
    Integer fromClauseIdx;
    RegistryKey<GridStructure> structureFinderKey; 
    
  


    public StructureClause(RegistryKey<GridStructure> structureFinderKey, double maxDistance) 
    {
        this.gridStructure=SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY.get(structureFinderKey);
        this.maxDistance = maxDistance;
        this.fromClauseIdx=null;
        this.structureFinderKey=structureFinderKey;
      
    }
    
    public StructureClause(RegistryKey<GridStructure> structureFinderKey, Integer fromClauseIdx, double maxDistance) 
    {
        gridStructure=SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY.get(structureFinderKey);
        this.maxDistance = maxDistance;
        this.fromClauseIdx=fromClauseIdx;
        this.structureFinderKey=structureFinderKey;
    }
    
    private StructureClause(NbtCompound nbt)
    {
        this.gridStructure=SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY.get(new Identifier(nbt.getString("structureFinderKey")));
        maxDistance=nbt.getDouble("maxDistance");
        if(nbt.contains("fromClauseIdx"))  fromClauseIdx=nbt.getInt("fromClauseIdx");
        else fromClauseIdx=null;        
        this.structureFinderKey=SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY.getKey(gridStructure).orElseThrow();
    }
    
    public boolean isValid(long seed, QuickBiomeSource quickBiomeSource, ChunkPos cp)
    {
        return gridStructure.isValid(seed, quickBiomeSource, cp);
    }
    
    public RegistryEntry<Structure> getBedrockStructureEntry()
    {
        if(!gridStructure.isBedrock()) return null;
        if(gridStructure instanceof BedrockBiomeGridStructure bedrockGridStructure)
        {
            return bedrockGridStructure.getStructureEntry();
        }
        else return null;
    }
 
    public List<ChunkPos> getMatches(long seed, List<List<ChunkPos>> previousCriteriaResults)
    {
        Set<ChunkPos> matches=new HashSet<>();
        if(fromClauseIdx==null) matches.addAll(gridStructure.getChunksInRange(seed, ChunkPos.ORIGIN, this.maxDistance));// addMatches(seed, matches, ChunkPos.ORIGIN);
        else
        {
            List<ChunkPos> prevResults=previousCriteriaResults.get(fromClauseIdx);
            for(ChunkPos cp:prevResults)
            {
                matches.addAll(gridStructure.getChunksInRange(seed, cp, this.maxDistance));
            }
        }

        return new ArrayList<>(matches);
    }
    
    public void decreaseFromClauseIdx()
    {
        if(fromClauseIdx!=null) fromClauseIdx--;
    }

    @Override
    public String toString() {
        if(fromClauseIdx==null) return structureFinderKey.getValue().getPath()+" within "+Double.toString(maxDistance)+" blocks of spawn";
        return structureFinderKey.getValue().getPath()+" within "+Double.toString(maxDistance)+" blocks of criteria idx "+fromClauseIdx.toString();
    }
    
    public String getStructureName()
    {
        return structureFinderKey.getValue().getPath();
    }
    
    
    public  NbtCompound writeNbt(NbtCompound nbt)
    {
        nbt.putString("structureFinderKey", structureFinderKey.getValue().toString());
        nbt.putDouble("maxDistance", maxDistance);
        if(fromClauseIdx!=null) nbt.putInt("fromClauseIdx", fromClauseIdx);
        return nbt;
    }
    
    public static StructureClause fromNbt(NbtCompound nbt)
    {
        return new StructureClause(nbt);
    }
    
}
