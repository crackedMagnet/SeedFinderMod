/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.event;

import com.crackedmagnet.seedfindermod.Settings;
import com.mojang.datafixers.util.Either;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents.AfterPlayerChange;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class PlayerChangeWorldsHandler implements AfterPlayerChange{
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");

    @Override
    public void afterChangeWorld(ServerPlayerEntity player, ServerWorld origin, ServerWorld destination) {
        if(Settings.modEnabled)
        {
            LOGGER.debug("PlayerChangeWorldsHandler.afterChangeWorld()");
            BlockPos spawnPos = destination.getChunkManager().getNoiseConfig().getMultiNoiseSampler().findBestSpawnPosition();
            ChunkPos spawnChunk=new ChunkPos(spawnPos);

            ServerChunkManager chunkManager = destination.getChunkManager();

            CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> chunkFuture = chunkManager.getChunkFutureSyncOnMainThread(spawnChunk.x, spawnChunk.z, ChunkStatus.FULL, true);

            TeleportTask teleportTask = new TeleportTask(chunkFuture, destination, player, spawnPos);

            Util.getMainWorkerExecutor().submit(teleportTask);
        }
 
    }
    
    public class TeleportTask implements Runnable
    {
        CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> chunkFuture;
        ServerWorld destination;
        ServerPlayerEntity player;
        BlockPos spawnPos;

        public TeleportTask(CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> chunkFuture, ServerWorld destination, ServerPlayerEntity player, BlockPos spawnPos) {
            this.chunkFuture = chunkFuture;
            this.destination = destination;
            this.player = player;
            this.spawnPos = spawnPos;
        }

        @Override
        public void run() {
            LOGGER.debug("TeleportTask.run()");
            try {
                
                Either<Chunk, ChunkHolder.Unloaded> result = chunkFuture.get(); //wait till complete
                Optional<Chunk> chunkResult = result.left();
                if(!chunkResult.isPresent())
                {
                    LOGGER.error("TeleportTask.run() Chunk not ready");
                    return;
                }
                int y = destination.getTopY(Heightmap.Type.WORLD_SURFACE, spawnPos.getX(), spawnPos.getZ());
                player.teleport(spawnPos.getX(), y, spawnPos.getZ());
          
               
                
            } catch (InterruptedException | ExecutionException ex) {
                LOGGER.error("TeleportTask.run() Error", ex);
            }
        }
        
    }
    
}
