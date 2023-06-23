/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod;

import com.crackedmagnet.seedfindermod.search.SeedSearch;
import com.crackedmagnet.seedfindermod.structures.GridStructure;
import com.mojang.serialization.Lifecycle;
import java.util.function.Function;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.Identifier;

/**
 *
 * @author matthewferguson
 */
public class SeedFinderRegistries {
    public static final Registry<SeedSearch> SEED_SEARCH_REGISTRY; 
    public static final RegistryEntry<SeedSearch> DEFAULT_SEED_SEARCH;
    public static final Registry<GridStructure> STRUCTURE_FINDER_REGISTRY;
    public static final RegistryKey<Registry<GridStructure>> STRUCTURE_FINDER_REGISTRY_KEY;
    
    public static final Registry<StructureSet> BEDROCK_STRUCTURE_SETS_REGISTRY;
    public static final RegistryKey<Registry<StructureSet>> BEDROCK_STRUCTURE_SETS_REGISTRY_KEY;

  
    //BuiltinRegistries.add(BuiltinRegistries.WORLD_PRESET, , )
    static
    {
        
        SEED_SEARCH_REGISTRY=new SimpleRegistry<SeedSearch>(RegistryKey.ofRegistry(Identifier.of("seedfindermod", "seed_search_registry")), Lifecycle.stable());
        //DEFAULT_SEED_SEARCH = BuiltinRegistries.add(SEED_SEARCH_REGISTRY, "seedfindermod:current_seed_search", new SeedSearch());


        DEFAULT_SEED_SEARCH = RegistryEntry.of(Registry.register(SEED_SEARCH_REGISTRY, "seedfindermod:current_seed_search", new SeedSearch()));

        STRUCTURE_FINDER_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier("seedfindermod:structure_finder_keys"));

        STRUCTURE_FINDER_REGISTRY=new SimpleRegistry<GridStructure>(STRUCTURE_FINDER_REGISTRY_KEY, Lifecycle.stable());

        //STRUCTURE_FINDER_REGISTRY = (Registry<GridStructure>) DynamicRegistryManager.createSimpleRegistry(STRUCTURE_FINDER_REGISTRY_KEY);
        
        BEDROCK_STRUCTURE_SETS_REGISTRY_KEY=RegistryKey.ofRegistry(new Identifier("seedfindermod:bedrock_structure_sets_registry"));
        BEDROCK_STRUCTURE_SETS_REGISTRY=new SimpleRegistry<StructureSet>(BEDROCK_STRUCTURE_SETS_REGISTRY_KEY, Lifecycle.stable());

        //BEDROCK_STRUCTURE_SETS_REGISTRY =(Registry<StructureSet>) DynamicRegistryManager.createSimpleRegistry(BEDROCK_STRUCTURE_SETS_REGISTRY_KEY);
        
        //STRUCTURE_FINDER_REGISTRY = new SimpleRegistry<GridStructure>(STRUCTURE_FINDER_REGISTRY_KEY, Lifecycle.stable(), (Function)null);
        //RegistryKey.ofRegistry(new Identifier(registryId) worldGenStructure

    }
    
}
