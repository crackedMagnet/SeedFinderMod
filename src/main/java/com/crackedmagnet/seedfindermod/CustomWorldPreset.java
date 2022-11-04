/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod;

import com.mojang.brigadier.context.CommandContext;
import java.util.OptionalLong;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

/**
 *
 * @author matthewferguson
 */
public class CustomWorldPreset {
    
    public static DimensionOptions seedFinderWorldDimensionOptions;
    public static final Identifier SEED_FINDER_ID = new Identifier("seedfindermod:seed_finder_dimension_type");
    public static final RegistryEntry<DimensionType> SEED_FINDER_DIMENSION_TYPE = BuiltinRegistries.add(BuiltinRegistries.DIMENSION_TYPE, SEED_FINDER_ID,new DimensionType(OptionalLong.empty(), true, false, false, true, 1.0D, true, false, -64, 384, 384, BlockTags.INFINIBURN_OVERWORLD, SEED_FINDER_ID, 0.0F, new DimensionType.MonsterSettings(false, true, UniformIntProvider.create(0, 7), 0)));
    
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
        Identifier worldDimId = world.getDimensionKey().getValue();
        return worldDimId.equals(SEED_FINDER_DIMENSION_TYPE.getKey().orElseThrow().getValue());
    }
}
