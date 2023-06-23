/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.event;

import com.crackedmagnet.seedfindermod.CustomWorldPreset;
import com.crackedmagnet.seedfindermod.Settings;
import java.util.Iterator;

import com.crackedmagnet.seedfindermod.biome.QuickBiomeSource;
import com.crackedmagnet.seedfindermod.biome.QuickSampler;
import com.crackedmagnet.seedfindermod.structures.BedrockBiomeGridStructure;
import com.crackedmagnet.seedfindermod.structures.BedrockStructureSets;
import com.crackedmagnet.seedfindermod.structures.StructureFinders;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class ServerStartHandler implements ServerLifecycleEvents.ServerStarted{
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");
    @Override
    public void onServerStarted(MinecraftServer ms) {
        boolean hasSeedFinderWorld=false;
        Iterator<ServerWorld> worlds = ms.getWorlds().iterator();
        while(worlds.hasNext())
        {
            ServerWorld world = worlds.next();
            boolean seedFinderWorld = CustomWorldPreset.isSeedFinderWorld(world);
            if(seedFinderWorld)
            {
                hasSeedFinderWorld=true;
                break;
            }
        }
        
        Settings.modEnabled=hasSeedFinderWorld;
        
        if(hasSeedFinderWorld) {
            LOGGER.info("Seed Finder world type detected.  Enabling Seed Finder Mod");
            RegistryEntryLookup.RegistryLookup registryLookup = ms.getRegistryManager().createRegistryLookup();
            BedrockBiomeGridStructure.bootstrap(registryLookup);
            LOGGER.info("BedrockBiomeGridStructure Done");
            StructureFinders.bootstrap(registryLookup);
            LOGGER.info("StructureFinders Done");
            QuickSampler.bootstrap(registryLookup);
            LOGGER.info("QuickSampler Done");
            QuickBiomeSource.bootstrap(registryLookup);
            LOGGER.info("QuickBiomeSource Done");
            BedrockStructureSets bss=new BedrockStructureSets(registryLookup.getOrThrow(RegistryKeys.STRUCTURE_SET));
            LOGGER.info("BedrockStructureSets Done");

        }
        else LOGGER.info("Non Seed Finder world type detected.  Disabling Seed Finder Mod");
    }
    
}
