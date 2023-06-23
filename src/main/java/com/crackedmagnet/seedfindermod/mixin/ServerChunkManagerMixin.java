package com.crackedmagnet.seedfindermod.mixin;

import com.crackedmagnet.seedfindermod.SeedFinderRegistries;
import com.crackedmagnet.seedfindermod.Settings;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.placement.StructurePlacementCalculator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerChunkManager.class)
public abstract class ServerChunkManagerMixin {


    @Shadow public abstract ChunkGenerator getChunkGenerator();
    StructurePlacementCalculator bedrockStructurePlacementCalculator=null;

    @Inject(at = @At("RETURN"), method = "getStructurePlacementCalculator", cancellable = true)
    protected void getStructurePlacementCalculator(CallbackInfoReturnable<StructurePlacementCalculator> cir) {
        if(Settings.modEnabled&&Settings.bedrock) //this needs to be done better
        {
            if(bedrockStructurePlacementCalculator==null)
            {
                BiomeSource bs=this.getChunkGenerator().getBiomeSource();
                StructurePlacementCalculator javaStructurePlacementCalculator = cir.getReturnValue();
                bedrockStructurePlacementCalculator=StructurePlacementCalculator.create(javaStructurePlacementCalculator.getNoiseConfig(), javaStructurePlacementCalculator.getStructureSeed(), bs, SeedFinderRegistries.BEDROCK_STRUCTURE_SETS_REGISTRY.streamEntries().map(structureSetReference -> structureSetReference));
            }

            cir.setReturnValue(bedrockStructurePlacementCalculator);
        }
    }
}
