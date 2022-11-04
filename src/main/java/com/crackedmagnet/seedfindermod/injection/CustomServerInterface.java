/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.injection;

import net.minecraft.server.world.ServerWorld;

/**
 *
 * @author matthewferguson
 */
public interface CustomServerInterface {
   public default ServerWorld addWorld(long seed)
    {
        return null;
    }
}
