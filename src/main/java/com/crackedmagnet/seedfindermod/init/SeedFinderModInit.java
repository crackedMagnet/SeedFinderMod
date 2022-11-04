/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.init;

import com.crackedmagnet.seedfindermod.biome.QuickBiomeSource;
import com.crackedmagnet.seedfindermod.commands.StructureTypeArgument;
import com.crackedmagnet.seedfindermod.event.CommandRegistrationHandler;
import com.crackedmagnet.seedfindermod.event.PlayerChangeWorldsHandler;
import com.crackedmagnet.seedfindermod.event.ServerShutdownHandler;
import com.crackedmagnet.seedfindermod.event.ServerStartHandler;
import com.crackedmagnet.seedfindermod.event.WorldUnloadHandler;
import com.crackedmagnet.seedfindermod.structures.StructureFinders;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class SeedFinderModInit implements ModInitializer{
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");

    @Override
    public void onInitialize() {
        LOGGER.info("Initialising Seed Finder Mod...");
        LOGGER.debug("CommandRegistrationHandler.register()");
        ArgumentTypeRegistry.registerArgumentType(new Identifier("seedfindermod", "structuretype"), StructureTypeArgument.class, ConstantArgumentSerializer.of(StructureTypeArgument::new));
        
        StructureFinders.bootstrap();
        QuickBiomeSource.bootstrap();
        CommandRegistrationCallback.EVENT.register(new CommandRegistrationHandler());
        
        ServerWorldEvents.UNLOAD.register(new WorldUnloadHandler());
        ServerLifecycleEvents.SERVER_STOPPING.register(new ServerShutdownHandler());
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(new PlayerChangeWorldsHandler()); 
        
        ServerLifecycleEvents.SERVER_STARTED.register(new ServerStartHandler());
    }
    
}
