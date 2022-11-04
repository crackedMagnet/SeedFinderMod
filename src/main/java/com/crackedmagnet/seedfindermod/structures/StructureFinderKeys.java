/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.structures;

import com.crackedmagnet.seedfindermod.SeedFinderRegistries;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;

/**
 *
 * @author matthewferguson
 */
public class StructureFinderKeys {
    public static final RegistryKey<GridStructure> JAVA_VILLAGE = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:java_village"));
    public static final RegistryKey<GridStructure> JAVA_RUINED_PORTAL = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:java_ruined_portal"));
    public static final RegistryKey<GridStructure> JAVA_WOODLAND_MANSION = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:java_woodland_mansion"));
    public static final RegistryKey<GridStructure> JAVA_DESERT_TEMPLE = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:java_desert_temple"));
    public static final RegistryKey<GridStructure> JAVA_WITCH_HUT = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:java_witch_hut"));
    public static final RegistryKey<GridStructure> JAVA_JUNGLE_TEMPLE = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:java_jungle_temple"));
    public static final RegistryKey<GridStructure> JAVA_IGLOO = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:java_igloo"));
    public static final RegistryKey<GridStructure> JAVA_PILAGER_OUTPOST = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:java_pilager_outpost"));
    public static final RegistryKey<GridStructure> JAVA_OCEAN_MONUMENT = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:java_ocean_monument"));
    
    public static final RegistryKey<GridStructure> BEDROCK_VILLAGE = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:bedrock_village"));
    public static final RegistryKey<GridStructure> BEDROCK_RUINED_PORTAL = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:bedrock_ruined_portal"));
    public static final RegistryKey<GridStructure> BEDROCK_WOODLAND_MANSION = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:bedrock_woodland_mansion"));
    public static final RegistryKey<GridStructure> BEDROCK_DESERT_TEMPLE = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:bedrock_desert_temple"));
    public static final RegistryKey<GridStructure> BEDROCK_WITCH_HUT = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:bedrock_witch_hut"));
    public static final RegistryKey<GridStructure> BEDROCK_JUNGLE_TEMPLE = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:bedrock_jungle_temple"));
    public static final RegistryKey<GridStructure> BEDROCK_IGLOO = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:bedrock_igloo"));
    public static final RegistryKey<GridStructure> BEDROCK_PILAGER_OUTPOST = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:bedrock_pilager_outpost"));
    public static final RegistryKey<GridStructure> BEDROCK_OCEAN_MONUMENT = RegistryKey.of(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY_KEY, new Identifier("seedfindermod:bedrock_ocean_monument"));;
  
    
    /* private static RegistryKey<StructureSet> of(String id) {
      return RegistryKey.of(Registry.STRUCTURE_SET_KEY, new Identifier(id));
   }*/
     /*private static RegistryKey<Structure> of(String id) {
      return RegistryKey.of(Registry.STRUCTURE_KEY, new Identifier(id));
   }*/
      /*   static RegistryEntry<GridStructure> register(RegistryKey<GridStructure> key, GridStructure gridStructure) {
            return BuiltinRegistries.add(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY, (RegistryKey)key, gridStructure);
        }*/
}
