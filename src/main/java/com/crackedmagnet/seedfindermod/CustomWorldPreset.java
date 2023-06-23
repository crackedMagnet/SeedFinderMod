/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod;

import com.mojang.brigadier.context.CommandContext;
import java.util.OptionalLong;

import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
//import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
//import net.minecraft.util.registry.BuiltinRegistries;
//import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.structure.Structure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class CustomWorldPreset {
    
    public static final Identifier SEED_FINDER_ID = new Identifier("seedfindermod:seed_finder_dimension");
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");


    /**
     * Checks if the chunk generator used is the instance used in the seed finder world preset
     * This is a protection to prevent people from messing up their 
     * existing worlds by adding extra dimensions and then complaining to me
     * about it.
     * @param context
     * @return 
     */
    public static boolean isSeedFinderWorld(CommandContext<ServerCommandSource> context)
    {
        if(!Settings.modEnabled)
        {
            context.getSource().getPlayer().sendMessage(Text.literal("/findseed can only be used on worlds created with the \"Seed Finder\" world type!\n\nCreate a new world and set the world type to \"Seed Finder\" under \"More World Options...\""));
            return false;
        }
        return true;
    }
    
    public static boolean isSeedFinderWorld(ServerWorld world)
    {

        Identifier worldDimId = world.getRegistryKey().getValue();
        LOGGER.info(worldDimId.toString());
        return worldDimId.equals(SEED_FINDER_ID);

    }
}
