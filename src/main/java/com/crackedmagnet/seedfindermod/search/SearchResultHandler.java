/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.search;

import java.util.List;
import net.minecraft.util.Pair;
import net.minecraft.util.math.ChunkPos;

/**
 *
 * @author matthewferguson
 */
public abstract class SearchResultHandler {
    public abstract void handleResult(long seed64, List<Pair<String, ChunkPos>> structures);
    
    
}
