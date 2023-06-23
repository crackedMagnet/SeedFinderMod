/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.mixin;
import com.crackedmagnet.seedfindermod.injection.CustomServerInterface;
import com.crackedmagnet.seedfindermod.SeedHolder;
import com.google.common.collect.ImmutableList;
import java.util.Map;
import java.util.concurrent.Executor;

import net.minecraft.registry.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.RandomSequencesState;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
/**
 *
 * @author matthewferguson
 */
@Mixin(MinecraftServer.class)
public abstract class ServerMixin implements CustomServerInterface{
    @Shadow
    Map<RegistryKey<World>, ServerWorld> worlds;

    @Shadow
    SaveProperties saveProperties;
    
    @Shadow
    Executor workerExecutor;
    
    @Shadow
    LevelStorage.Session session;
    
    @Shadow
    WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory;

    @Shadow public abstract DynamicRegistryManager.Immutable getRegistryManager();

    @Override
    public ServerWorld addWorld(long seed) {

        Registry<DimensionOptions> dimensionOptionsRegistry = this.getRegistryManager().get(RegistryKeys.DIMENSION);

        SeedHolder.setCurrentSeed(seed);
        
        ServerWorldProperties serverWorldProperties = this.saveProperties.getMainWorldProperties();
        GeneratorOptions generatorOptions= this.saveProperties.getGeneratorOptions();

        Identifier seedIdentifier = new Identifier("seedfindermod","seed_"+Long.toString(seed));

        //This registry key is not "used" however if its not created palette errors start appearing.  I'm not entirely sure why.
        RegistryKey<DimensionOptions> seedDimOptionsKey = RegistryKey.of(RegistryKeys.DIMENSION, seedIdentifier);

        DimensionOptions overworldDimensionOptions = dimensionOptionsRegistry.getOrThrow(DimensionOptions.OVERWORLD);

        WorldGenerationProgressListener worldGenerationProgressListener = worldGenerationProgressListenerFactory.create(11);

        RegistryKey<World> worldKey = RegistryKey.of(RegistryKeys.WORLD, seedIdentifier);

        ServerWorld serverWorld2 = new ServerWorld((MinecraftServer)(Object)this, this.workerExecutor, this.session, serverWorldProperties, worldKey, (DimensionOptions)overworldDimensionOptions, worldGenerationProgressListener, false, BiomeAccess.hashSeed(seed), ImmutableList.of(), false, new RandomSequencesState(seed));

        this.worlds.put(worldKey, serverWorld2);
        serverWorld2.getChunkManager().getChunkGenerator().getSpawnHeight(serverWorld2);

        return serverWorld2;
    }

}
