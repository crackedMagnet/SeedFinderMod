/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.event;

import com.crackedmagnet.seedfindermod.SeedFinderRegistries;
import com.crackedmagnet.seedfindermod.Settings;
import static com.crackedmagnet.seedfindermod.commands.CriteriaAddCommand.LOGGER;
import com.crackedmagnet.seedfindermod.search.SearchThreadHolder;
import com.crackedmagnet.seedfindermod.search.SeedSearch;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

/**
 *
 * @author matthewferguson
 */
public class ServerShutdownHandler implements ServerLifecycleEvents.ServerStopping{

    @Override
    public void onServerStopping(MinecraftServer ms) {
        LOGGER.debug("ServerShutdownHandler.onServerStopping()");
        SeedSearch seedSearch = SeedFinderRegistries.DEFAULT_SEED_SEARCH.value();
        
        //any running search need to be stopped
        SearchThreadHolder.stopAndClear();
        
        //clear criteria so its not still there when another world is loaded.
        seedSearch.resetCriteria();
        
        //reset settings so they don't stick around next world load.
        Settings.bedrock=false;
        Settings.modEnabled=false;
        
    }
    
}
