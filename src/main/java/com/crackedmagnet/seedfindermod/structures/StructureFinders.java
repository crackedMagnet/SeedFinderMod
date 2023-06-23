/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.structures;

import com.crackedmagnet.seedfindermod.SeedFinderRegistries;
import static com.crackedmagnet.seedfindermod.SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY;
import com.crackedmagnet.seedfindermod.biome.ReferenceData;
import com.mojang.serialization.Lifecycle;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureSetKeys;
import net.minecraft.structure.StructureSets;
import net.minecraft.util.Identifier;
import net.minecraft.registry.MutableRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;
import net.minecraft.world.gen.structure.Structures;
import org.slf4j.LoggerFactory;


/**
 *
 * @author matthewferguson
 */
public class StructureFinders {
    public static  RegistryEntry<GridStructure> JAVA_VILLAGE;
    public static  RegistryEntry<GridStructure> JAVA_RUINED_PORTAL;
    public static  RegistryEntry<GridStructure> JAVA_WOODLAND_MANSION;
    public static  RegistryEntry<GridStructure> JAVA_DESERT_TEMPLE;
    public static  RegistryEntry<GridStructure> JAVA_WITCH_HUT;
    public static  RegistryEntry<GridStructure> JAVA_JUNGLE_TEMPLE;
    public static  RegistryEntry<GridStructure> JAVA_IGLOO;
    public static  RegistryEntry<GridStructure> JAVA_PILAGER_OUTPOST;
    public static  RegistryEntry<GridStructure> JAVA_OCEAN_MONUMENT;
    
    public static  RegistryEntry<GridStructure> BEDROCK_VILLAGE;
    public static  RegistryEntry<GridStructure> BEDROCK_RUINED_PORTAL;
    public static  RegistryEntry<GridStructure> BEDROCK_WOODLAND_MANSION;
    public static  RegistryEntry<GridStructure> BEDROCK_DESERT_TEMPLE;
    public static  RegistryEntry<GridStructure> BEDROCK_WITCH_HUT;
    public static  RegistryEntry<GridStructure> BEDROCK_JUNGLE_TEMPLE;
    public static  RegistryEntry<GridStructure> BEDROCK_IGLOO;
    public static  RegistryEntry<GridStructure> BEDROCK_PILAGER_OUTPOST;
    public static  RegistryEntry<GridStructure> BEDROCK_TRAIL_RUINS;
    public static  RegistryEntry<GridStructure> BEDROCK_OCEAN_MONUMENT;
    
  
    public static RegistryEntry<GridStructure> bootstrap(RegistryEntryLookup.RegistryLookup registryLookup )
    {
        RegistryEntryLookup<StructureSet> structureSetRegistry = registryLookup.getOrThrow(RegistryKeys.STRUCTURE_SET);
        RegistryEntryLookup<Structure> structureRegistry = registryLookup.getOrThrow(RegistryKeys.STRUCTURE);


        JAVA_VILLAGE = register(StructureFinderKeys.JAVA_VILLAGE,new BiomeGridStructure(structureSetRegistry.getOrThrow(StructureSetKeys.VILLAGES), ReferenceData.validJavaVillageBiomes));
        JAVA_RUINED_PORTAL = register(StructureFinderKeys.JAVA_RUINED_PORTAL,new GridStructure(structureSetRegistry.getOrThrow(StructureSetKeys.RUINED_PORTALS)));
        JAVA_WOODLAND_MANSION = register(StructureFinderKeys.JAVA_WOODLAND_MANSION,new BiomeGridStructure(structureSetRegistry.getOrThrow(StructureSetKeys.WOODLAND_MANSIONS), ReferenceData.validWoodlandMansionBiomes));
        JAVA_DESERT_TEMPLE = register(StructureFinderKeys.JAVA_DESERT_TEMPLE,new BiomeGridStructure(structureSetRegistry.getOrThrow(StructureSetKeys.DESERT_PYRAMIDS), ReferenceData.validDesertTempleBiomes));
        JAVA_WITCH_HUT = register(StructureFinderKeys.JAVA_WITCH_HUT,new BiomeGridStructure(structureSetRegistry.getOrThrow(StructureSetKeys.SWAMP_HUTS), ReferenceData.validWitchHutBiomes));
        JAVA_JUNGLE_TEMPLE = register(StructureFinderKeys.JAVA_JUNGLE_TEMPLE,new BiomeGridStructure(structureSetRegistry.getOrThrow(StructureSetKeys.JUNGLE_TEMPLES), ReferenceData.validJungleTempleBiomes));
        JAVA_IGLOO = register(StructureFinderKeys.JAVA_IGLOO,new BiomeGridStructure(structureSetRegistry.getOrThrow(StructureSetKeys.IGLOOS), ReferenceData.validIglooBiomes));
        JAVA_PILAGER_OUTPOST= register(StructureFinderKeys.JAVA_PILAGER_OUTPOST,new JavaPillagerOutpostFinder());
        JAVA_OCEAN_MONUMENT = register(StructureFinderKeys.JAVA_OCEAN_MONUMENT,new BiomeGridStructure(structureSetRegistry.getOrThrow(StructureSetKeys.OCEAN_MONUMENTS), ReferenceData.validDeepOceanBiomes));
        JAVA_OCEAN_MONUMENT = register(StructureFinderKeys.JAVA_TRAIL_RUINS,new BiomeGridStructure(structureSetRegistry.getOrThrow(StructureSetKeys.TRAIL_RUINS), ReferenceData.validTrailRuinsBiomes));

        BEDROCK_VILLAGE = register(StructureFinderKeys.BEDROCK_VILLAGE, new BedrockBiomeGridStructure(structureRegistry.getOrThrow(StructureKeys.VILLAGE_PLAINS), ReferenceData.validVillageBiomes, 34, 8, 10387312, false, 2));
        BEDROCK_RUINED_PORTAL = register(StructureFinderKeys.BEDROCK_RUINED_PORTAL,new BedrockBiomeGridStructure(structureRegistry.getOrThrow(StructureKeys.RUINED_PORTAL), new HashSet<>(), 40, 15, 40552231, true, 0));
        BEDROCK_WOODLAND_MANSION = register(StructureFinderKeys.BEDROCK_WOODLAND_MANSION,new BedrockBiomeGridStructure(structureRegistry.getOrThrow(StructureKeys.MANSION), ReferenceData.validWoodlandMansionBiomes, 80, 20, 10387319, false, 32));
        BEDROCK_DESERT_TEMPLE = register(StructureFinderKeys.BEDROCK_DESERT_TEMPLE,new BedrockBiomeGridStructure(structureRegistry.getOrThrow(StructureKeys.DESERT_PYRAMID), ReferenceData.validDesertTempleBiomes, 32, 8, 14357617, true, 2));
        BEDROCK_WITCH_HUT = register(StructureFinderKeys.BEDROCK_WITCH_HUT,new BedrockBiomeGridStructure(structureRegistry.getOrThrow(StructureKeys.SWAMP_HUT), ReferenceData.validWitchHutBiomes, 32, 8, 14357617, true, 2));
        BEDROCK_JUNGLE_TEMPLE = register(StructureFinderKeys.BEDROCK_JUNGLE_TEMPLE,new BedrockBiomeGridStructure(structureRegistry.getOrThrow(StructureKeys.JUNGLE_PYRAMID), ReferenceData.validJungleTempleBiomes, 32, 8, 14357617, true, 2));
        BEDROCK_IGLOO = register(StructureFinderKeys.BEDROCK_IGLOO,new BedrockBiomeGridStructure(structureRegistry.getOrThrow(StructureKeys.IGLOO), ReferenceData.validIglooBiomes, 32, 8, 14357617, true, 2));
        BEDROCK_PILAGER_OUTPOST = register(StructureFinderKeys.BEDROCK_PILAGER_OUTPOST,new BedrockBiomeGridStructure(structureRegistry.getOrThrow(StructureKeys.PILLAGER_OUTPOST),ReferenceData.validPilagerOutpostBiomes, 80, 24, 165745296, false, 2));
        BEDROCK_OCEAN_MONUMENT = register(StructureFinderKeys.BEDROCK_OCEAN_MONUMENT,new BedrockOceanMonumentFinder());
        BEDROCK_TRAIL_RUINS = register(StructureFinderKeys.BEDROCK_TRAIL_RUINS,new BedrockBiomeGridStructure(structureRegistry.getOrThrow(StructureKeys.TRAIL_RUINS),ReferenceData.validTrailRuinsBiomes, 34, 8, 83469867, false, 2));
        return JAVA_DESERT_TEMPLE;
    }

    static RegistryEntry<GridStructure> register(RegistryKey<GridStructure> key, GridStructure gridStructure) {
      return add(STRUCTURE_FINDER_REGISTRY, key, gridStructure);
      //return BuiltinRegistries.add(SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY, (RegistryKey)key, gridStructure);
   }
    
    private static RegistryEntry<GridStructure> add(Registry<GridStructure> registry, RegistryKey<GridStructure> key, GridStructure object) {
        RegistryEntry entry = ((MutableRegistry)registry).add(key, object, Lifecycle.stable());
        
        Set<Identifier> ids = SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY.getIds();
        LoggerFactory.getLogger("seedfindermod").debug("StructureFinders registered "+key.toString()+" new reg size = "+Integer.toString(ids.size()));
      return entry;
   }
    
    

   
    
    
}
