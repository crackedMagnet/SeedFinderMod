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
import net.minecraft.structure.StructureSets;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.structure.Structures;
import org.slf4j.LoggerFactory;


/**
 *
 * @author matthewferguson
 */
public class StructureFinders {
    public static final RegistryEntry<GridStructure> JAVA_VILLAGE;
    public static final RegistryEntry<GridStructure> JAVA_RUINED_PORTAL;
    public static final RegistryEntry<GridStructure> JAVA_WOODLAND_MANSION;
    public static final RegistryEntry<GridStructure> JAVA_DESERT_TEMPLE;
    public static final RegistryEntry<GridStructure> JAVA_WITCH_HUT;
    public static final RegistryEntry<GridStructure> JAVA_JUNGLE_TEMPLE;
    public static final RegistryEntry<GridStructure> JAVA_IGLOO;
    public static final RegistryEntry<GridStructure> JAVA_PILAGER_OUTPOST;
    public static final RegistryEntry<GridStructure> JAVA_OCEAN_MONUMENT;
    
    public static final RegistryEntry<GridStructure> BEDROCK_VILLAGE;
    public static final RegistryEntry<GridStructure> BEDROCK_RUINED_PORTAL;
    public static final RegistryEntry<GridStructure> BEDROCK_WOODLAND_MANSION;
    public static final RegistryEntry<GridStructure> BEDROCK_DESERT_TEMPLE;
    public static final RegistryEntry<GridStructure> BEDROCK_WITCH_HUT;
    public static final RegistryEntry<GridStructure> BEDROCK_JUNGLE_TEMPLE;
    public static final RegistryEntry<GridStructure> BEDROCK_IGLOO;
    public static final RegistryEntry<GridStructure> BEDROCK_PILAGER_OUTPOST;
    public static final RegistryEntry<GridStructure> BEDROCK_OCEAN_MONUMENT;
    
  
    public static RegistryEntry<GridStructure> bootstrap()
    {
        return JAVA_DESERT_TEMPLE;
    }
    
            
  
    
    //public static final GridStructure DESERT_PYRAMID=register(StructureSets.DESERT_PYRAMIDS);
   // private static final Map<Structure,GridStructure> structureMap=new HashMap<>();
    /*public static GridStructure getGridStructure(Structure structure)
    {
        return structureMap.get(structure);
    }*/
    
   /* private static GridStructure register(RegistryEntry<StructureSet> structureSetEntry)
    {
        StructureSet structureSet = structureSetEntry.value();
        //StructurePlacement placement = structureSet.placement();
        GridStructure gridStructure = new GridStructure(structureSetEntry);
        List<StructureSet.WeightedEntry> structures = structureSet.structures();
        for(StructureSet.WeightedEntry entry:structures)
        {
            Structure structure = entry.structure().value();
            //StructureType<?> type = structure.getType();
            structureMap.put(structure, gridStructure);
        }
        return gridStructure;
    }
    private static void init()
    {
        Biome biome;
        Registry<StructureSet> structureSetRegistry = BuiltinRegistries.STRUCTURE_SET;
        Set<RegistryKey<StructureSet>> keys = structureSetRegistry.getKeys();
      
      //  Set<Map.Entry<RegistryKey<StructureSet>, StructureSet>> entries = structureSetRegistry.getEntrySet();
        for(RegistryKey<StructureSet> key:keys)
        {
            Optional<RegistryEntry<StructureSet>> entry = structureSetRegistry.getEntry(key);
            if(entry.isPresent()) register(entry.get());
        }
    }
    static
    {
        init();
        
    }*/
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
    
    
    static
    {
        JAVA_VILLAGE = register(StructureFinderKeys.JAVA_VILLAGE,new BiomeGridStructure(StructureSets.VILLAGES, ReferenceData.validJavaVillageBiomes));
        JAVA_RUINED_PORTAL = register(StructureFinderKeys.JAVA_RUINED_PORTAL,new GridStructure(StructureSets.RUINED_PORTALS));
        JAVA_WOODLAND_MANSION = register(StructureFinderKeys.JAVA_WOODLAND_MANSION,new BiomeGridStructure(StructureSets.WOODLAND_MANSIONS, ReferenceData.validWoodlandMansionBiomes));
        JAVA_DESERT_TEMPLE = register(StructureFinderKeys.JAVA_DESERT_TEMPLE,new BiomeGridStructure(StructureSets.DESERT_PYRAMIDS, ReferenceData.validDesertTempleBiomes));
        JAVA_WITCH_HUT = register(StructureFinderKeys.JAVA_WITCH_HUT,new BiomeGridStructure(StructureSets.SWAMP_HUTS, ReferenceData.validWitchHutBiomes));
        JAVA_JUNGLE_TEMPLE = register(StructureFinderKeys.JAVA_JUNGLE_TEMPLE,new BiomeGridStructure(StructureSets.JUNGLE_TEMPLES, ReferenceData.validJungleTempleBiomes));
        JAVA_IGLOO = register(StructureFinderKeys.JAVA_IGLOO,new BiomeGridStructure(StructureSets.IGLOOS, ReferenceData.validIglooBiomes));
        //JAVA_PILAGER_OUTPOST = register(StructureFinderKeys.JAVA_PILAGER_OUTPOST,new BiomeGridStructure(StructureSets.PILLAGER_OUTPOSTS, ReferenceData.validPilagerOutpostBiomes));
        JAVA_PILAGER_OUTPOST= register(StructureFinderKeys.JAVA_PILAGER_OUTPOST,new JavaPillagerOutpostFinder());
        JAVA_OCEAN_MONUMENT = register(StructureFinderKeys.JAVA_OCEAN_MONUMENT,new BiomeGridStructure(StructureSets.OCEAN_MONUMENTS, ReferenceData.validDeepOceanBiomes));
        
        BEDROCK_VILLAGE = register(StructureFinderKeys.BEDROCK_VILLAGE, new BedrockBiomeGridStructure(Structures.VILLAGE_PLAINS, ReferenceData.validVillageBiomes, 34, 8, 10387312, false, 2));
        BEDROCK_RUINED_PORTAL = register(StructureFinderKeys.BEDROCK_RUINED_PORTAL,new BedrockBiomeGridStructure(Structures.RUINED_PORTAL, new HashSet<>(), 40, 15, 40552231, true, 0));
        BEDROCK_WOODLAND_MANSION = register(StructureFinderKeys.BEDROCK_WOODLAND_MANSION,new BedrockBiomeGridStructure(Structures.MANSION, ReferenceData.validWoodlandMansionBiomes, 80, 20, 10387319, false, 32));
        BEDROCK_DESERT_TEMPLE = register(StructureFinderKeys.BEDROCK_DESERT_TEMPLE,new BedrockBiomeGridStructure(Structures.DESERT_PYRAMID, ReferenceData.validDesertTempleBiomes, 32, 8, 14357617, true, 2));
        BEDROCK_WITCH_HUT = register(StructureFinderKeys.BEDROCK_WITCH_HUT,new BedrockBiomeGridStructure(Structures.SWAMP_HUT, ReferenceData.validWitchHutBiomes, 32, 8, 14357617, true, 2));
        BEDROCK_JUNGLE_TEMPLE = register(StructureFinderKeys.BEDROCK_JUNGLE_TEMPLE,new BedrockBiomeGridStructure(Structures.JUNGLE_PYRAMID, ReferenceData.validJungleTempleBiomes, 32, 8, 14357617, true, 2));
        BEDROCK_IGLOO = register(StructureFinderKeys.BEDROCK_IGLOO,new BedrockBiomeGridStructure(Structures.IGLOO, ReferenceData.validIglooBiomes, 32, 8, 14357617, true, 2));
        BEDROCK_PILAGER_OUTPOST = register(StructureFinderKeys.BEDROCK_PILAGER_OUTPOST,new BedrockBiomeGridStructure(Structures.PILLAGER_OUTPOST,ReferenceData.validPilagerOutpostBiomes, 80, 24, 165745296, false, 2));
        BEDROCK_OCEAN_MONUMENT = register(StructureFinderKeys.BEDROCK_OCEAN_MONUMENT,new BedrockOceanMonumentFinder());
        /*
         
    public StructureConfig villageConfig=new StructureConfig(27, 10, 10387312,false);
    public StructureConfig villageConfigV118=new StructureConfig(34, 8, 10387312,false);
    public StructureConfig villageConfigV12=new StructureConfig(40, 12, 10387312,true);// will need to check this.  I think its linear separation.
    public StructureConfig buriedTreasureConfig=new StructureConfig(4, 2, 16842397,false); //liean seperation false
    public StructureConfig desertTempleConfig=new StructureConfig(32, 8, 14357617,true); 
    public StructureConfig shipwreakConfig=new StructureConfig(10, 5, 165745295,false); //not within 5 chunks of village blockpos +8 10 blocks need to be beach
    public StructureConfig shipwreakConfigV118=new StructureConfig(24, 4, 165745295,true); //not within 5 chunks of village blockpos +8 10 blocks need to be beach
    public StructureConfig pillagerOutpostConfig=new StructureConfig(80, 24, 165745296,false); 
    public StructureConfig fortressConfig=new StructureConfig(30, 4, 30084232,true);
    public StructureConfig ruinedPortalOverlandConfig=new StructureConfig(40, 15, 40552231,true);
    public StructureConfig ruinedPortalNetherConfig=new StructureConfig(25, 10, 40552231,true);
    public StructureConfig oceanMonumentConfig=new StructureConfig(32, 5, 10387313,false);
    //public StructureConfig oceanRuinConfig=new StructureConfig(12, 7, 14357621,true); //can not spawn near village
    public StructureConfig oceanRuinConfig=new StructureConfig(20, 8, 14357621,true); //can not spawn near village
    public StructureConfig witchHutConfig=new StructureConfig(32, 8, 14357617,true); 
    //public StructureConfig oceanRuinConfig=new StructureConfig(32, 8, 14357617,true); 
    public StructureConfig woodlandMansionConfig=new StructureConfig(80, 20, 10387319,false);  //linear seperation=false dark forrest and dark forrest hills only
    public StructureConfig iglooConfig=new StructureConfig(32, 8, 14357617,true);  //linear seperation=false dark forrest and dark forrest hills only
    //put(StructureFeature.ANCIENT_CITY, new StructureFeatureConfiguration(24, 8, 20083232))
    public StructureConfig ancientCityConfig=new StructureConfig(24, 8, 20083232,false);

        */
    }
   
    
    
}
