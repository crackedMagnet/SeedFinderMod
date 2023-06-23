/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.biome;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.registry.*;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;
import net.minecraft.world.gen.structure.Structures;

/**
 *
 * @author matthewferguson
 */
public class ReferenceData {
     
     public static final Set<RegistryKey<Biome>> validVillageBiomes=new HashSet<>();
     public static final Set<RegistryKey<Biome>> validJavaVillageBiomes=new HashSet<>();
     public static final Set<RegistryKey<Biome>> validShipwreakBeachedBiomes=new HashSet<>();
     public static final Set<RegistryKey<Biome>> validShipwreakBiomes=new HashSet<>();
     public static final Set<RegistryKey<Biome>> validBuriedTreasureBiomes=new HashSet<>();
     public static final Set<RegistryKey<Biome>> validIglooBiomes=new HashSet<>();
     public static final Set<RegistryKey<Biome>> validDesertTempleBiomes=new HashSet<>();
     public static final Set<RegistryKey<Biome>> validJungleTempleBiomes=new HashSet<>();
     public static final Set<RegistryKey<Biome>> validWitchHutBiomes=new HashSet<>();
     public static final Set<RegistryKey<Biome>> validDeepOceanBiomes=new HashSet<>();
     public static final Set<RegistryKey<Biome>> validOceanBiomes=new HashSet<>();
     public static final Set<RegistryKey<Biome>> validOceanRuinBiomes=new HashSet<>();
     public static final Set<RegistryKey<Biome>> validSpawns=new HashSet<>();
     public static final Set<RegistryKey<Biome>> validWoodlandMansionBiomes=new HashSet<>();
     public static final Set<RegistryKey<Biome>> validPilagerOutpostBiomes=new HashSet<>();
     public static final Set<RegistryKey<Biome>> validJavaStrongholdBiomes=new HashSet<>();
     public static final Set<RegistryKey<Biome>> forestBiomes=new HashSet<>();
      public static final Set<RegistryKey<Biome>> validTrailRuinsBiomes=new HashSet<>();
     public static final Map<RegistryKey<Structure>, Set<RegistryKey<Biome>>> structureBiomes=new HashMap<>();

   static
     {


        validSpawns.add(BiomeKeys.PLAINS);
        validSpawns.add(BiomeKeys.FOREST);
        validSpawns.add(BiomeKeys.TAIGA);
        validSpawns.add(BiomeKeys.JUNGLE);

        validVillageBiomes.add(BiomeKeys.PLAINS);
        validVillageBiomes.add(BiomeKeys.SUNFLOWER_PLAINS);
        validVillageBiomes.add(BiomeKeys.TAIGA);
        validVillageBiomes.add(BiomeKeys.SNOWY_TAIGA);
        validVillageBiomes.add(BiomeKeys.DESERT);
        validVillageBiomes.add(BiomeKeys.SAVANNA);
        validVillageBiomes.add(BiomeKeys.SNOWY_PLAINS);
        validVillageBiomes.add(BiomeKeys.MEADOW);

        validPilagerOutpostBiomes.addAll(validVillageBiomes);
        validPilagerOutpostBiomes.add(BiomeKeys.STONY_PEAKS);
        validPilagerOutpostBiomes.add(BiomeKeys.FROZEN_PEAKS);
        validPilagerOutpostBiomes.add(BiomeKeys.JAGGED_PEAKS);
        validPilagerOutpostBiomes.add(BiomeKeys.SNOWY_SLOPES);
        validPilagerOutpostBiomes.add(BiomeKeys.GROVE);
        structureBiomes.put(StructureKeys.PILLAGER_OUTPOST, validPilagerOutpostBiomes);

        validShipwreakBeachedBiomes.add(BiomeKeys.SNOWY_BEACH);
        validShipwreakBeachedBiomes.add(BiomeKeys.BEACH);
        structureBiomes.put(StructureKeys.SHIPWRECK_BEACHED, validShipwreakBeachedBiomes);
        
        validShipwreakBiomes.add(BiomeKeys.OCEAN);
        validShipwreakBiomes.add(BiomeKeys.DEEP_OCEAN);
        validShipwreakBiomes.add(BiomeKeys.COLD_OCEAN);
        validShipwreakBiomes.add(BiomeKeys.DEEP_COLD_OCEAN);
        validShipwreakBiomes.add(BiomeKeys.LUKEWARM_OCEAN);
        validShipwreakBiomes.add(BiomeKeys.DEEP_LUKEWARM_OCEAN);
        validShipwreakBiomes.add(BiomeKeys.FROZEN_OCEAN);
        validShipwreakBiomes.add(BiomeKeys.DEEP_FROZEN_OCEAN);
        validShipwreakBiomes.add(BiomeKeys.WARM_OCEAN);
        structureBiomes.put(StructureKeys.SHIPWRECK, validShipwreakBiomes);

        validBuriedTreasureBiomes.add(BiomeKeys.SNOWY_BEACH);
        validBuriedTreasureBiomes.add(BiomeKeys.BEACH);
        validBuriedTreasureBiomes.add(BiomeKeys.STONY_SHORE);
        structureBiomes.put(StructureKeys.BURIED_TREASURE, validBuriedTreasureBiomes);
       
        
        validIglooBiomes.add(BiomeKeys.SNOWY_TAIGA);
        validIglooBiomes.add(BiomeKeys.SNOWY_PLAINS);
        structureBiomes.put(StructureKeys.IGLOO, validIglooBiomes);
        
        validDesertTempleBiomes.add(BiomeKeys.DESERT);
        structureBiomes.put(StructureKeys.DESERT_PYRAMID, validDesertTempleBiomes);
        validJungleTempleBiomes.add(BiomeKeys.JUNGLE);
        structureBiomes.put(StructureKeys.JUNGLE_PYRAMID, validJungleTempleBiomes);
        validWitchHutBiomes.add(BiomeKeys.SWAMP);
        structureBiomes.put(StructureKeys.SWAMP_HUT, validWitchHutBiomes);

        validDeepOceanBiomes.add(BiomeKeys.DEEP_COLD_OCEAN);
        validDeepOceanBiomes.add(BiomeKeys.DEEP_FROZEN_OCEAN);
        validDeepOceanBiomes.add(BiomeKeys.DEEP_LUKEWARM_OCEAN);
        validDeepOceanBiomes.add(BiomeKeys.DEEP_OCEAN);
        structureBiomes.put(StructureKeys.MONUMENT, validDeepOceanBiomes);
        
        validOceanBiomes.addAll(validDeepOceanBiomes);
        validOceanBiomes.add(BiomeKeys.OCEAN);
        validOceanBiomes.add(BiomeKeys.FROZEN_OCEAN);
        validOceanBiomes.add(BiomeKeys.WARM_OCEAN);
        validOceanBiomes.add(BiomeKeys.OCEAN);
        validOceanBiomes.add(BiomeKeys.LUKEWARM_OCEAN);
        validOceanBiomes.add(BiomeKeys.COLD_OCEAN);

        validWoodlandMansionBiomes.add(BiomeKeys.DARK_FOREST);
        structureBiomes.put(StructureKeys.MANSION, validWoodlandMansionBiomes);

        validJavaStrongholdBiomes.add(BiomeKeys.PLAINS);
        validJavaStrongholdBiomes.add(BiomeKeys.DESERT);
        validJavaStrongholdBiomes.add(BiomeKeys.WINDSWEPT_HILLS);
        validJavaStrongholdBiomes.add(BiomeKeys.FOREST);
        validJavaStrongholdBiomes.add(BiomeKeys.TAIGA);
        validJavaStrongholdBiomes.add(BiomeKeys.SNOWY_PLAINS);
        validJavaStrongholdBiomes.add(BiomeKeys.MUSHROOM_FIELDS);
        validJavaStrongholdBiomes.add(BiomeKeys.WINDSWEPT_FOREST);
        validJavaStrongholdBiomes.add(BiomeKeys.JUNGLE);
        validJavaStrongholdBiomes.add(BiomeKeys.SPARSE_JUNGLE);
        validJavaStrongholdBiomes.add(BiomeKeys.BIRCH_FOREST);
        validJavaStrongholdBiomes.add(BiomeKeys.DARK_FOREST);
        validJavaStrongholdBiomes.add(BiomeKeys.SNOWY_TAIGA);
        validJavaStrongholdBiomes.add(BiomeKeys.OLD_GROWTH_PINE_TAIGA);
        validJavaStrongholdBiomes.add(BiomeKeys.SAVANNA);
        validJavaStrongholdBiomes.add(BiomeKeys.SAVANNA_PLATEAU);
        validJavaStrongholdBiomes.add(BiomeKeys.BADLANDS);
        validJavaStrongholdBiomes.add(BiomeKeys.WOODED_BADLANDS);
        validJavaStrongholdBiomes.add(BiomeKeys.SUNFLOWER_PLAINS);
        validJavaStrongholdBiomes.add(BiomeKeys.WINDSWEPT_GRAVELLY_HILLS);
        validJavaStrongholdBiomes.add(BiomeKeys.FLOWER_FOREST);
        validJavaStrongholdBiomes.add(BiomeKeys.ICE_SPIKES);
        validJavaStrongholdBiomes.add(BiomeKeys.OLD_GROWTH_BIRCH_FOREST);
        validJavaStrongholdBiomes.add(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA);
        validJavaStrongholdBiomes.add(BiomeKeys.WINDSWEPT_SAVANNA);
        validJavaStrongholdBiomes.add(BiomeKeys.ERODED_BADLANDS);
        validJavaStrongholdBiomes.add(BiomeKeys.BAMBOO_JUNGLE);
        validJavaStrongholdBiomes.add(BiomeKeys.DRIPSTONE_CAVES);
        validJavaStrongholdBiomes.add(BiomeKeys.LUSH_CAVES);
        validJavaStrongholdBiomes.add(BiomeKeys.MEADOW);
        validJavaStrongholdBiomes.add(BiomeKeys.GROVE);
        validJavaStrongholdBiomes.add(BiomeKeys.SNOWY_SLOPES);
        validJavaStrongholdBiomes.add(BiomeKeys.FROZEN_PEAKS);
        validJavaStrongholdBiomes.add(BiomeKeys.JAGGED_PEAKS);
        validJavaStrongholdBiomes.add(BiomeKeys.STONY_PEAKS);
        
        forestBiomes.add(BiomeKeys.BAMBOO_JUNGLE);
        forestBiomes.add(BiomeKeys.JUNGLE);
        forestBiomes.add(BiomeKeys.BIRCH_FOREST);
        forestBiomes.add(BiomeKeys.OLD_GROWTH_PINE_TAIGA);
        forestBiomes.add(BiomeKeys.OLD_GROWTH_BIRCH_FOREST);
        forestBiomes.add(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA);
        forestBiomes.add(BiomeKeys.FOREST);
        forestBiomes.add(BiomeKeys.DARK_FOREST);
        forestBiomes.add(BiomeKeys.TAIGA);
        forestBiomes.add(BiomeKeys.SNOWY_TAIGA);
        forestBiomes.add(BiomeKeys.WOODED_BADLANDS);
        forestBiomes.add(BiomeKeys.WINDSWEPT_FOREST);

        validJavaVillageBiomes.add(BiomeKeys.PLAINS);
        validJavaVillageBiomes.add(BiomeKeys.TAIGA);
        validJavaVillageBiomes.add(BiomeKeys.DESERT);
        validJavaVillageBiomes.add(BiomeKeys.SAVANNA);
        validJavaVillageBiomes.add(BiomeKeys.SNOWY_PLAINS);
        validJavaVillageBiomes.add(BiomeKeys.MEADOW);

        validTrailRuinsBiomes.add(BiomeKeys.TAIGA);
        validTrailRuinsBiomes.add(BiomeKeys.SNOWY_TAIGA);
        validTrailRuinsBiomes.add(BiomeKeys.OLD_GROWTH_PINE_TAIGA);
        validTrailRuinsBiomes.add(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA);
        validTrailRuinsBiomes.add(BiomeKeys.OLD_GROWTH_BIRCH_FOREST);
        validTrailRuinsBiomes.add(BiomeKeys.JUNGLE);


     }
  
     
}