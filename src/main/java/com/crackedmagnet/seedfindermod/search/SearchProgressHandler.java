/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.crackedmagnet.seedfindermod.search;

import java.util.List;
import net.minecraft.util.Pair;
import net.minecraft.util.math.ChunkPos;

/**
 *
 * @author matthewferguson
 */
public interface SearchProgressHandler {
    public void updateProgress(long current48bit, long count48bit, long millis);
    public void foundSeed(long seed, List<Pair<String, ChunkPos>> structures);
    public void sendMessage(String message);
}
