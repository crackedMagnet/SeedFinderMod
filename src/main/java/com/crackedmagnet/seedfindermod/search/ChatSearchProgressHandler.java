/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.search;

import java.util.List;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

/**
 *
 * @author matthewferguson
 */
public class ChatSearchProgressHandler implements SearchProgressHandler{

    ServerPlayerEntity player;

    public ChatSearchProgressHandler(ServerPlayerEntity player) {
        this.player = player;
    }
    
    @Override
    public void updateProgress(long current48bit, long count48bit, long millis) {
         
        double currentSeedPerSec=((double)count48bit/(double)millis)*1000d;
                        
        String message="Current seed: "+Long.toString(current48bit)+" Rate: "+Long.toString(Math.round(currentSeedPerSec))+ " seeds/sec";
        player.sendMessage(Text.literal(message));
    }

    @Override
    public void foundSeed(long seed, List<Pair<String, ChunkPos>> structures) {
        StringBuilder sb=new StringBuilder();
        sb.append("Found seed: ");
        sb.append(seed);
        for(Pair<String, ChunkPos> pair:structures)
        {
            sb.append("\n");
            BlockPos blockPos = pair.getRight().getBlockPos(0, 200, 0);
            sb.append(pair.getLeft());
            sb.append(" at ");
            sb.append(blockPos.getX());
            sb.append(" ");
            sb.append(blockPos.getZ());
        }
        player.sendMessage(Text.literal(sb.toString()));
        
    }
    
}
