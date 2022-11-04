/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.mixin;

import com.crackedmagnet.seedfindermod.SeedHolder;
import com.crackedmagnet.seedfindermod.Settings;
import net.minecraft.world.gen.GeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author matthewferguson
 */
@Mixin(GeneratorOptions.class)
public class GeneratorOptionsMixin {
    /*
    This Mixin is basically the core of being able to have different overworlds
    with different seeds.  When a world is instanated it refers to the server
    for the world seed so this allows that to be changed.
    
    There is a bit of a side effect when the orignal dimention is respawned into.
    The seed remains changed so the new chunks generate on a different seed.
    This should probably be addressed.
    */
    
    @Inject(at = @At("RETURN"), method = "getSeed", cancellable = true)
    protected void getSeed(CallbackInfoReturnable<Long> cir)
    {
        if(Settings.modEnabled)
        {
            Long currentSeed = SeedHolder.getCurrentSeed();
            if(currentSeed==null)
            {
                SeedHolder.setOrigSeed(cir.getReturnValue());
                SeedHolder.setCurrentSeed(cir.getReturnValue());
            }
            cir.setReturnValue(SeedHolder.getCurrentSeed());
        }
    } 
}
