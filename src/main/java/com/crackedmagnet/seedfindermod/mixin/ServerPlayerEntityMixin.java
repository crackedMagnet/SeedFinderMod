/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.mixin;

import com.crackedmagnet.seedfindermod.Settings;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author matthewferguson
 */
@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    
    /*
        When the player is moved from one overworld world to another overworld 
        world the teleport target logic returns null causing error in the entity
        tracking because the player is leaving chunks it didn't know they entered
    */
    @Inject(at = @At("RETURN"), method = "getTeleportTarget", cancellable = true)
    protected void getTeleportTarget(ServerWorld destination, CallbackInfoReturnable<TeleportTarget> cir)
    { 
        if(Settings.modEnabled)
        {
            RegistryKey<World> registryKey = destination.getRegistryKey();

            if(registryKey.getValue().getPath().startsWith("seed_"))
            {
                Vec3d vec3d = new Vec3d(0, 256, 0);
                cir.setReturnValue(new TeleportTarget(vec3d, Vec3d.ZERO, 90.0F, 0.0F));
            }
        }
    }
    
}
