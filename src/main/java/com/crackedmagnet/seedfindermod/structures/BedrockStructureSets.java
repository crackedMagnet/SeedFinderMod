/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.structures;

import com.crackedmagnet.seedfindermod.SeedFinderRegistries;
import com.mojang.serialization.Lifecycle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureSetKeys;
import net.minecraft.structure.StructureSets;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;
import net.minecraft.world.gen.structure.Structure;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class BedrockStructureSets {

    public  BedrockStructureSets(RegistryEntryLookup<StructureSet> structureSetRegistry)
    {
        /*
        This is pretty awkward as I was having issues with the structure entry 
        references in the the structure sets call referencing different isntances
        than the world gen was using.  I now realise that was due to the 
        dynamic registry and this should all be reworked to use that as a 
        reference point.
        */

        if(BEDROCK_VILLAGES==null) BEDROCK_VILLAGES=init(structureSetRegistry,StructureSetKeys.VILLAGES,BedrockStructureSetKeys.BEDROCK_VILLAGES, new BedrockGridStructurePlacement(34, 8, false, 10387312));
        if(BEDROCK_RUINED_PORTALS==null) BEDROCK_RUINED_PORTALS=init(structureSetRegistry,StructureSetKeys.RUINED_PORTALS,BedrockStructureSetKeys.BEDROCK_RUINED_PORTALS, new BedrockGridStructurePlacement(40, 15, true, 40552231));
        if(BEDROCK_WOODLAND_MANSIONS==null) BEDROCK_WOODLAND_MANSIONS=init(structureSetRegistry,StructureSetKeys.WOODLAND_MANSIONS,BedrockStructureSetKeys.BEDROCK_WOODLAND_MANSIONS,new BedrockGridStructurePlacement(80, 20, false, 10387319));
        if(BEDROCK_TEMPLES==null) BEDROCK_TEMPLES=init(structureSetRegistry,List.of(StructureSetKeys.DESERT_PYRAMIDS,StructureSetKeys.JUNGLE_TEMPLES,StructureSetKeys.SWAMP_HUTS,StructureSetKeys.IGLOOS),BedrockStructureSetKeys.BEDROCK_TEMPLES, new BedrockGridStructurePlacement(32, 8, true, 14357617));
        if(BEDROCK_PILAGER_OUTPOSTS==null) BEDROCK_PILAGER_OUTPOSTS=init(structureSetRegistry,StructureSetKeys.PILLAGER_OUTPOSTS,BedrockStructureSetKeys.BEDROCK_PILAGER_OUTPOSTS,new BedrockGridStructurePlacement(80, 24, false, 165745296));
        if(BEDROCK_OCEAN_MONUMENTS==null) BEDROCK_OCEAN_MONUMENTS=init(structureSetRegistry,StructureSetKeys.OCEAN_MONUMENTS,BedrockStructureSetKeys.BEDROCK_OCEAN_MONUMENTS,new BedrockGridStructurePlacement(32, 5, false, 10387313));
        if(BEDROCK_TRAIL_RUINS==null) BEDROCK_TRAIL_RUINS=init(structureSetRegistry,StructureSetKeys.TRAIL_RUINS,BedrockStructureSetKeys.BEDROCK_TRAIL_RUINS,new BedrockGridStructurePlacement(34, 8, false, 83469867));

    }
 
    public static  RegistryEntry<StructureSet> BEDROCK_VILLAGES=null;
    public static  RegistryEntry<StructureSet> BEDROCK_RUINED_PORTALS=null;
    public static  RegistryEntry<StructureSet> BEDROCK_WOODLAND_MANSIONS=null;
    public static  RegistryEntry<StructureSet> BEDROCK_TEMPLES=null;
    public static  RegistryEntry<StructureSet> BEDROCK_PILAGER_OUTPOSTS=null;
    public static  RegistryEntry<StructureSet> BEDROCK_OCEAN_MONUMENTS=null;

    public static  RegistryEntry<StructureSet> BEDROCK_TRAIL_RUINS=null;
    
    static RegistryEntry<StructureSet> register(RegistryKey<StructureSet> key, StructureSet structureSet) { 
       
        RegistryEntry entry = ((MutableRegistry)SeedFinderRegistries.BEDROCK_STRUCTURE_SETS_REGISTRY).add(key, structureSet, Lifecycle.stable());
        
        List<StructureSet.WeightedEntry> structures = structureSet.structures();
        for(StructureSet.WeightedEntry weightedEntry:structures)
        {
            RegistryEntry<Structure> structure = weightedEntry.structure();
            LoggerFactory.getLogger("seedfindermod").info("BedrockStructureSets structure instance: "+structure.getKey().orElseThrow().getValue().getPath()+" "+structure.value().toString()); 
        }
        
        return entry;
    }
  
    private RegistryEntry<StructureSet> init(RegistryEntryLookup<StructureSet> structureSetRegistry, RegistryKey<StructureSet> origEntry, RegistryKey<StructureSet> newKey, StructurePlacement structurePlacement)
    {
        return init(structureSetRegistry, List.of(origEntry), newKey, structurePlacement);
    }
    
    private RegistryEntry<StructureSet> init(RegistryEntryLookup<StructureSet> structureSetRegistry, List<RegistryKey<StructureSet>> origEntryList, RegistryKey<StructureSet> newKey, StructurePlacement structurePlacement)
    {

        List<StructureSet.WeightedEntry> entries=new ArrayList<>();
        for(RegistryKey<StructureSet> origEntry:origEntryList)
        {

            StructureSet structureSet = structureSetRegistry.getOrThrow(origEntry).value();
            entries.addAll(structureSet.structures());
        }
        return register(newKey, new StructureSet(entries, structurePlacement));
        
    }    
}
