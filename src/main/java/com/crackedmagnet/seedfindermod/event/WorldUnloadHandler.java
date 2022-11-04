/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.event;

import com.crackedmagnet.seedfindermod.Settings;

import com.crackedmagnet.seedfindermod.search.SearchThreadHolder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class WorldUnloadHandler implements ServerWorldEvents.Unload{
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");
    @Override
    public void onWorldUnload(MinecraftServer ms, ServerWorld sw) {
        if(Settings.modEnabled)
        {
            LOGGER.debug("WorldUnloadHandler.onWorldUnload()");

            SearchThreadHolder.stopAndClear();
            Settings.bedrock=false;
        }
    }
    
    
}
