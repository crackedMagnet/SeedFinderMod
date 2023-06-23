package com.crackedmagnet.seedfindermod.search;

import com.crackedmagnet.seedfindermod.biome.QuickBiomeSource;
import com.crackedmagnet.seedfindermod.criteria.BiomeClause;
import com.crackedmagnet.seedfindermod.criteria.BiomeProportionClause;
import com.crackedmagnet.seedfindermod.criteria.StructureClause;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

/**
 *
 * @author matthewferguson
 */
public class SeedSearch {
    //will sort this out later
    List<StructureClause> structureCriteria=new ArrayList<>();
    List<BiomeClause> biomeCriteria=new ArrayList<>();

    //needs some sort of callback
    public void doSearch()
    {
        
        
    }
    
    public List<List<ChunkPos>> findMatch(long seed)
    {
        List<List<ChunkPos>> results=new ArrayList<>(structureCriteria.size());
        for(int i=0;i<structureCriteria.size();i++)
        {
            StructureClause clause=structureCriteria.get(i);
            List<ChunkPos> matches = clause.getMatches(seed, results);
            if(matches.isEmpty()) return null;
            results.add(i, matches);
        }
        return results;
    }
    
    public boolean meetsBiomeCriteria(QuickBiomeSource bs)
    {
        Map<RegistryKey<Biome>, Integer> biomeCounts = bs.getBiomeCounts(0, 0, 500);
        for(BiomeClause biomeClause:biomeCriteria)
        {
            boolean matches = biomeClause.matches(biomeCounts);
            if(!matches) return false;
        }
        return true;
    }

    public int addCriteria(BiomeClause clause)
    {
        biomeCriteria.add(clause);
        return biomeCriteria.size()-1;
    }
    public int addCriteria(StructureClause clause)
    {
        structureCriteria.add(clause);
        return structureCriteria.size()-1;
    }
    
    public int removeBiomeCriteria(int idx)
    {
        if(idx>=biomeCriteria.size()) return biomeCriteria.size();
        biomeCriteria.remove(idx);
        return biomeCriteria.size();
    }
    
    public int removeStructureCriteria(int idx)
    {
        if(idx>=structureCriteria.size()) return structureCriteria.size();
        for(int i=idx+1;i<structureCriteria.size();i++)
        {
            StructureClause structureClause = structureCriteria.get(idx);
            structureClause.decreaseFromClauseIdx();
        }
        structureCriteria.remove(idx);
        return structureCriteria.size();
    }
    
    public void resetCriteria()
    {
        structureCriteria=new ArrayList<>();
        biomeCriteria=new ArrayList<>();
    }
    
    public List<StructureClause> listCriteria()
    {
        return new ArrayList<>(structureCriteria);
    }
    
    public String getCriteriaString()
    {
        List<StructureClause> criteriaList = this.listCriteria();
        StringBuilder sb=new StringBuilder();
        sb.append("--- Structure Criteria ---\n");
        for(int i=0;i<criteriaList.size();i++)
        {
            StructureClause criteria=criteriaList.get(i);
            
            sb.append(i);
            sb.append(": ");
            sb.append(criteria.toString());
            sb.append("\n");
        }
        sb.append("\n----- Biome Criteria -----\n");
        for(int i=0;i<biomeCriteria.size();i++)
        {
            BiomeClause criteria=biomeCriteria.get(i);
            
            sb.append(i);
            sb.append(": ");
            sb.append(criteria.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public NbtCompound writeNbt(NbtCompound nbt)
    {
        NbtList structureCriteriaNbt=new NbtList();
        for(StructureClause structureClause:structureCriteria)
        {
            structureCriteriaNbt.add(structureClause.writeNbt(new NbtCompound()));
        }
        
        NbtList biomeCriteriaNbt=new NbtList();
        for(BiomeClause biomeClause:biomeCriteria)
        {
            biomeCriteriaNbt.add(biomeClause.writeNbt(new NbtCompound()));
        }
        
        nbt.put("structureCriteria", structureCriteriaNbt);
        nbt.put("biomeCriteria", biomeCriteriaNbt);
        
        return nbt;
    }
    
    public void setFromNbt(NbtCompound nbt)
    {
        structureCriteria=new ArrayList<>();
        NbtList structureCriteriaNbt=(NbtList) nbt.get("structureCriteria");
        
        for(int i=0;i<structureCriteriaNbt.size();i++)
        {
            NbtCompound structureClauseNbt = structureCriteriaNbt.getCompound(i);
            structureCriteria.add(StructureClause.fromNbt(structureClauseNbt));
        }
        
        biomeCriteria=new ArrayList<>();
        NbtList biomeCriteriaNbt=(NbtList) nbt.get("biomeCriteria");
        
        for(int i=0;i<biomeCriteriaNbt.size();i++)
        {
            NbtCompound biomeClauseNbt = biomeCriteriaNbt.getCompound(i);
            if(biomeClauseNbt.contains("minPercent"))
                biomeCriteria.add(BiomeProportionClause.fromNbt(biomeClauseNbt));
            else
                biomeCriteria.add(BiomeClause.fromNbt(biomeClauseNbt));
        }
    }
    
    public String getBookCriteriaString()
    {
        
        List<StructureClause> criteriaList = this.listCriteria();
        StringBuilder sb=new StringBuilder();
        sb.append("Structure Criteria:\n");
        for(int i=0;i<criteriaList.size();i++)
        {
            StructureClause criteria=criteriaList.get(i);
            
            sb.append(i);
            sb.append(": ");
            sb.append(criteria.toString().replace(" within ", " within\n"));
            sb.append("\n");
        }
        sb.append("\nBiome Criteria:\n");
        for(int i=0;i<biomeCriteria.size();i++)
        {
            BiomeClause criteria=biomeCriteria.get(i);
            
            sb.append(i);
            sb.append(": ");
            sb.append(criteria.getDescription().replace("minecraft:", ""));
            if(criteria instanceof BiomeProportionClause bpc)
            {
                sb.append(">=");
                sb.append(bpc.getMinPercent());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
}
