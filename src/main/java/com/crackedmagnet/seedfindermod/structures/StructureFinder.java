/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.crackedmagnet.seedfindermod.structures;

import com.crackedmagnet.seedfindermod.biome.QuickBiomeSource;
import java.util.Set;
import net.minecraft.util.math.ChunkPos;

/**
 *
 * @author matthewferguson
 */
public interface StructureFinder {
    public ChunkPos getChunk(long seed, ChunkPos reference);
    public boolean isValid(long seed, QuickBiomeSource biomeSource, ChunkPos chunk);
    public Set<ChunkPos> getChunksInRange(long seed, ChunkPos center, double maxBlockDistance);
}
