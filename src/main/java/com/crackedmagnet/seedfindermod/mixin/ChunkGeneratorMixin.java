/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.mixin;

import com.crackedmagnet.seedfindermod.SeedFinderRegistries;
import com.crackedmagnet.seedfindermod.Settings;
import com.crackedmagnet.seedfindermod.structures.BedrockStructureSets;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;
import net.minecraft.world.gen.structure.Structure;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author matthewferguson
 */
@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
    BedrockStructureSets bedrockPlacement=null;
 
    @Shadow
    protected  Registry<StructureSet> structureSetRegistry;
  
    /*
    This Mixin overrides the structure sets with bedrock ones if the seed is
    being loaded with /findseed next_bedrock
    */
    
     @Inject(at = @At("RETURN"), method = "streamStructureSets", cancellable = true)
     protected void streamStructureSets(CallbackInfoReturnable<Stream<RegistryEntry<StructureSet>>> cir) {
         if(Settings.modEnabled&&Settings.bedrock) //this needs to be done better
         {
             if(bedrockPlacement==null)
             {
                 bedrockPlacement=new BedrockStructureSets(structureSetRegistry);
             }

             cir.setReturnValue(SeedFinderRegistries.BEDROCK_STRUCTURE_SETS_REGISTRY.streamEntries().map(RegistryEntry::upcast));
         }
     }
    
}
