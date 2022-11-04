/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.mixin;

import com.crackedmagnet.seedfindermod.CustomWorldPreset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.WorldPreset;
import net.minecraft.world.gen.WorldPresets;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author matthewferguson
 */
@Mixin(WorldPresets.Registrar.class)
public abstract class WorldPresetMixin {
    // defining our registry key. this key provides an Identifier for our preset, that we can use for our lang files and data elements.
    private static final RegistryKey<WorldPreset> SEED_FINDER_WORLD_KEY = RegistryKey.of(Registry.WORLD_PRESET_KEY, new Identifier("seedfindermod", "seed_finder_world"));
    private static final RegistryKey<WorldPreset> SEED_FINDER_OVERWORLD_KEY = RegistryKey.of(Registry.WORLD_PRESET_KEY, new Identifier("seedfindermod", "seed_finder_overworld"));
    @Shadow protected abstract RegistryEntry<WorldPreset> register(RegistryKey<WorldPreset> key, DimensionOptions dimensionOptions);
    @Shadow protected abstract DimensionOptions createOverworldOptions(ChunkGenerator chunkGenerator);
    @Shadow protected abstract DimensionOptions createOverworldOptions(BiomeSource biomeSource, RegistryEntry<ChunkGeneratorSettings> chunkGeneratorSettings);
 
    @Inject(method = "initAndGetDefault", at = @At("RETURN"))
    private void addPresets(CallbackInfoReturnable<RegistryEntry<WorldPreset>> cir) {
        // the register() method is shadowed from the target class
        MultiNoiseBiomeSource biomeSource=MultiNoiseBiomeSource.Preset.OVERWORLD.getBiomeSource(BuiltinRegistries.BIOME);
        
        DimensionOptions overworldDimensionOptions = this.createOverworldOptions(biomeSource, BuiltinRegistries.CHUNK_GENERATOR_SETTINGS.getEntry(ChunkGeneratorSettings.OVERWORLD).orElseThrow());
        //CustomWorldPreset.overworldDimensionOptions=overworldDimensionOptions;
        //RegistryEntryList<StructureSet> structureOverrides=RegistryEntryList.of(new ArrayList<>());
        
        //FlatChunkGeneratorLayer airLayer=new FlatChunkGeneratorLayer(63, Blocks.AIR);
        //FlatChunkGeneratorLayer bedrockLayer=new FlatChunkGeneratorLayer(1, Blocks.BEDROCK);
        //FlatChunkGeneratorConfig flatChunkGeneratorConfig = new FlatChunkGeneratorConfig(Optional.of(structureOverrides), BuiltinRegistries.BIOME).withLayers(List.of(airLayer,bedrockLayer), Optional.of(structureOverrides));
        
        //DimensionOptions flatDimensionOptions=createOverworldOptions(new FlatChunkGenerator(null, flatChunkGeneratorConfig));
        //CustomWorldPreset.overworldDimensionOptions=overworldDimensionOptions;
        //CustomWorldPreset.seedFinderWorldDimensionOptions=flatDimensionOptions;
        CustomWorldPreset.seedFinderWorldDimensionOptions=overworldDimensionOptions;
        DimensionOptions dimensionOptions = new DimensionOptions(CustomWorldPreset.SEED_FINDER_DIMENSION_TYPE, overworldDimensionOptions.getChunkGenerator());
        //DimensionOptions dimensionOptions = new DimensionOptions(CustomWorldPreset.SEED_FINDER_DIMENSION_TYPE, flatDimensionOptions.getChunkGenerator());
        this.register(SEED_FINDER_WORLD_KEY, dimensionOptions);
        //this.register(SEED_FINDER_OVERWORLD_KEY, overworldDimensionOptions);
    }
}