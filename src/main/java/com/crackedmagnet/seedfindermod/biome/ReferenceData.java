/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.biome;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.structure.Structure;
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
     public static final Map<Structure, Set<RegistryKey<Biome>>> structureBiomes=new HashMap<>();

     static
     {

        validSpawns.add(BiomeKeys.PLAINS);
        //validSpawns.add(BiomeKeys.JUNGLE_HILLS);
        validSpawns.add(BiomeKeys.FOREST);
        //validSpawns.add(BiomeKeys.DARK_FOREST);
        //validSpawns.add(BiomeKeys.DARK_FOREST_HILLS);
        validSpawns.add(BiomeKeys.TAIGA);
        //validSpawns.add(BiomeKeys.TAIGA_HILLS);
        validSpawns.add(BiomeKeys.JUNGLE);
        //validSpawns.add(BiomeKeys.WOODED_HILLS);
        
        //validSpawns.add(BiomeKeys.SAVANNA);
        //validSpawns.add(BiomeKeys.SAVANNA_PLATEAU);
        
        
        //validVillageBiomeKeys.add(BiomeKeys.DESERT_HILLS);
        validVillageBiomes.add(BiomeKeys.PLAINS);
        validVillageBiomes.add(BiomeKeys.SUNFLOWER_PLAINS);
        validVillageBiomes.add(BiomeKeys.TAIGA);
        validVillageBiomes.add(BiomeKeys.SNOWY_TAIGA);
        //validVillageBiomeKeys.add(BiomeKeys.TAIGA_HILLS);
        //validVillageBiomeKeys.add(BiomeKeys.SNOWY_TAIGA_HILLS);
        validVillageBiomes.add(BiomeKeys.DESERT);
        validVillageBiomes.add(BiomeKeys.SAVANNA);
        //validVillageBiomeKeys.add(BiomeKeys.SAVANNA_PLATEAU);
        validVillageBiomes.add(BiomeKeys.SNOWY_PLAINS);
        validVillageBiomes.add(BiomeKeys.MEADOW);
        //validVillageBiomeKeys.add(BiomeKeys.OLD_GROWTH_PINE_TAIGA);
        //validVillageBiomeKeys.add(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA);
        //validVillageBiomeKeys.add(BiomeKeys.SAVANNA_PLATEAU);
        //validVillageBiomeKeys.add(BiomeKeys.WINDSWEPT_SAVANNA);
        
        //[ab.plains, ab.sunflowerPlains, ab.savanna, ab.snowyTundra, ab.taiga, ab.taigaHills, ab.snowyTaigaHills, ab.desert]
        validPilagerOutpostBiomes.addAll(validVillageBiomes);
        validPilagerOutpostBiomes.add(BiomeKeys.STONY_PEAKS);
        validPilagerOutpostBiomes.add(BiomeKeys.FROZEN_PEAKS);
        validPilagerOutpostBiomes.add(BiomeKeys.JAGGED_PEAKS);
        validPilagerOutpostBiomes.add(BiomeKeys.SNOWY_SLOPES);
        validPilagerOutpostBiomes.add(BiomeKeys.GROVE);
        structureBiomes.put(Structures.PILLAGER_OUTPOST.value(), validPilagerOutpostBiomes);
        
      
        
        validShipwreakBeachedBiomes.add(BiomeKeys.SNOWY_BEACH);
        validShipwreakBeachedBiomes.add(BiomeKeys.BEACH);
        structureBiomes.put(Structures.SHIPWRECK_BEACHED.value(), validShipwreakBeachedBiomes);
        
        //validShipwreakBiomeKeys.add(BiomeKeys.SNOWY_BEACH);
        //validShipwreakBiomeKeys.add(BiomeKeys.BEACH);
        
        validShipwreakBiomes.add(BiomeKeys.OCEAN);
        validShipwreakBiomes.add(BiomeKeys.DEEP_OCEAN);
        validShipwreakBiomes.add(BiomeKeys.COLD_OCEAN);
        validShipwreakBiomes.add(BiomeKeys.DEEP_COLD_OCEAN);
        validShipwreakBiomes.add(BiomeKeys.LUKEWARM_OCEAN);
        validShipwreakBiomes.add(BiomeKeys.DEEP_LUKEWARM_OCEAN);
        validShipwreakBiomes.add(BiomeKeys.FROZEN_OCEAN);
        validShipwreakBiomes.add(BiomeKeys.DEEP_FROZEN_OCEAN);
        validShipwreakBiomes.add(BiomeKeys.WARM_OCEAN);
        structureBiomes.put(Structures.SHIPWRECK.value(), validShipwreakBiomes);
        
        //validShipwreakBeachedBiomeKeys.add(BiomeKeys.BEACH);
        
        //allowedBiomeKeys: [U.beach, U.snowyBeach, U.legacyMushroomFieldsShore, U.ocean, U.deepOcean, U.coldOcean, 
        //U.deepColdOcean, U.lukeWarmOcean, U.deepLukewarmOcean, U.frozenOcean, U.deepFrozenOcean, U.warmOcean],
       // validShipwreakBeachedBiomeKeys.add(BiomeKeys.MUSHROOM_FIELD_SHORE);
      
        validBuriedTreasureBiomes.add(BiomeKeys.SNOWY_BEACH);
        validBuriedTreasureBiomes.add(BiomeKeys.BEACH);
        //validBuriedTreasureBiomeKeys.add(BiomeKeys.MUSHROOM_FIELD_SHORE);
        validBuriedTreasureBiomes.add(BiomeKeys.STONY_SHORE);
        structureBiomes.put(Structures.BURIED_TREASURE.value(), validBuriedTreasureBiomes);
       
        
        validIglooBiomes.add(BiomeKeys.SNOWY_TAIGA);
        validIglooBiomes.add(BiomeKeys.SNOWY_PLAINS);
        structureBiomes.put(Structures.IGLOO.value(), validIglooBiomes);
        
        validDesertTempleBiomes.add(BiomeKeys.DESERT);
        structureBiomes.put(Structures.DESERT_PYRAMID.value(), validDesertTempleBiomes);
        //validDesertTempleBiomeKeys.add(BiomeKeys.DESERT_HILLS);
        validJungleTempleBiomes.add(BiomeKeys.JUNGLE);
        structureBiomes.put(Structures.JUNGLE_PYRAMID.value(), validJungleTempleBiomes);
        //validJungleTempleBiomeKeys.add(BiomeKeys.JUNGLE_HILLS);
        validWitchHutBiomes.add(BiomeKeys.SWAMP);
        structureBiomes.put(Structures.SWAMP_HUT.value(), validWitchHutBiomes);
        //validWitchHutBiomeKeys.add(BiomeKeys.SWAMP_HILLS);
        
        validDeepOceanBiomes.add(BiomeKeys.DEEP_COLD_OCEAN);
        validDeepOceanBiomes.add(BiomeKeys.DEEP_FROZEN_OCEAN);
        validDeepOceanBiomes.add(BiomeKeys.DEEP_LUKEWARM_OCEAN);
        validDeepOceanBiomes.add(BiomeKeys.DEEP_OCEAN);
        //validDeepOceanBiomeKeys.add(BiomeKeys.DEEP_WARM_OCEAN);
        structureBiomes.put(Structures.MONUMENT.value(), validDeepOceanBiomes);
        
        validOceanBiomes.addAll(validDeepOceanBiomes);
        validOceanBiomes.add(BiomeKeys.OCEAN);
        validOceanBiomes.add(BiomeKeys.FROZEN_OCEAN);
        validOceanBiomes.add(BiomeKeys.WARM_OCEAN);
        validOceanBiomes.add(BiomeKeys.OCEAN);
        validOceanBiomes.add(BiomeKeys.LUKEWARM_OCEAN);
        validOceanBiomes.add(BiomeKeys.COLD_OCEAN);
        
        //validOceanBiomes.add(BiomeKeys.RIVER);
        //validOceanBiomes.add(BiomeKeys.FROZEN_RIVER);
        
        
        validWoodlandMansionBiomes.add(BiomeKeys.DARK_FOREST);
        structureBiomes.put(Structures.MANSION.value(), validWoodlandMansionBiomes);
        
        //validWoodlandMansionBiomeKeys.add(BiomeKeys.DARK_FOREST_HILLS);
        //validWoodlandMansionBiomeKeys.add(BiomeKeys.MUSHROOM_FIELDS);
        //pd = [X.deepColdOcean, X.deepFrozenOcean, X.deepLukewarmOcean, X.deepOcean, X.deepWarmOcean], 
        //qd = [X.ocean, X.frozenOcean, X.deepOcean, X.warmOcean, X.lukeWarmOcean, X.coldOcean, X.deepWarmOcean, X.deepLukewarmOcean, X.deepColdOcean, X.deepFrozenOcean, X.river, X.frozenRiver]
        //allowedBiomeKeys: [U.beach, U.snowyBeach, U.legacyMushroomFieldsShore, U.ocean, U.deepOcean, U.coldOcean, U.deepColdOcean, U.lukeWarmOcean, U.deepLukewarmOcean, U.frozenOcean, U.deepFrozenOcean, U.warmOcean],
        
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
        //validJavaVillageBiomeKeys.add(BiomeKeys.SUNFLOWER_PLAINS);
        validJavaVillageBiomes.add(BiomeKeys.TAIGA);
        //validVillageBiomeKeys.add(BiomeKeys.SNOWY_TAIGA);
        //validVillageBiomeKeys.add(BiomeKeys.TAIGA_HILLS);
        //validVillageBiomeKeys.add(BiomeKeys.SNOWY_TAIGA_HILLS);
        validJavaVillageBiomes.add(BiomeKeys.DESERT);
        validJavaVillageBiomes.add(BiomeKeys.SAVANNA);
        //validVillageBiomeKeys.add(BiomeKeys.SAVANNA_PLATEAU);
        validJavaVillageBiomes.add(BiomeKeys.SNOWY_PLAINS);
        validJavaVillageBiomes.add(BiomeKeys.MEADOW);
        
        
        //structureBiomes.put(Structures.VILLAGE_DESERT.value(), validJavaVillageBiomes);
   
     
     

      
        
     }
  
     
}