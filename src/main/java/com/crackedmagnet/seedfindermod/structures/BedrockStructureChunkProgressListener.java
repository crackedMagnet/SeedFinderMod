/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.Structure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class BedrockStructureChunkProgressListener implements WorldGenerationProgressListener{
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");
    ServerWorld serverWorld;
    Map<ChunkPos,List<StructureStart>> starts;
    


    public void setServerWorld(ServerWorld serverWorld, Map<ChunkPos, List<RegistryEntry<Structure>>> bedrockStructures) {
        this.serverWorld = serverWorld;
         this.starts = new HashMap<>();
        for(Map.Entry<ChunkPos, List< RegistryEntry<Structure> >> entry:bedrockStructures.entrySet())
        {
            ChunkPos cp=entry.getKey();

            List<RegistryEntry<Structure>> structureList = entry.getValue();
            for(RegistryEntry<Structure> structureEntry:structureList)
            {
                Structure structure = structureEntry.value();
                if(structure==null) continue;
                ChunkGenerator chunkGenerator = serverWorld.getChunkManager().getChunkGenerator();
                
                StructureStart structureStart = structure.createStructureStart(serverWorld.getRegistryManager(), chunkGenerator, chunkGenerator.getBiomeSource(), serverWorld.getChunkManager().getNoiseConfig(), serverWorld.getStructureTemplateManager(), serverWorld.getSeed(), cp, 0, serverWorld, (registryEntry) -> {return true;});
                BlockBox bb = structureStart.getBoundingBox();
                ChunkPos minChunk=new ChunkPos(new BlockPos(bb.getMinX(), 0, bb.getMinZ()));
                ChunkPos maxChunk=new ChunkPos(new BlockPos(bb.getMaxX(), 0, bb.getMaxZ()));
                for(int x=minChunk.x;x<=maxChunk.x;x++)
                    for(int z=minChunk.z;z<=maxChunk.z;z++)
                    {
                        ChunkPos chunkPos = new ChunkPos(x,z);
                        List<StructureStart> list = starts.computeIfAbsent(chunkPos, (t) -> {return new ArrayList<>();});
                        list.add(structureStart);
                    }
            }
        }
    }
    
    
    
    @Override
    public void start(ChunkPos spawnPos) {
     
    }

    @Override
    public void setChunkStatus(ChunkPos pos, ChunkStatus status) {
        if(status==ChunkStatus.FULL)
        {
           // ChunkGenerator chunkGenerator = serverWorld.getChunkManager().getChunkGenerator();
            BlockBox bb=new BlockBox(pos.x*16, -64, pos.z*16, pos.x*16+15, 320, pos.z*16+15);
            List<StructureStart> startList = starts.get(pos);
            if(startList==null) return;
            if(startList.isEmpty()) return;
            //LOGGER.info("registering structures for chunk "+pos.toString());
            PlacementTask placementTask = new PlacementTask(serverWorld, startList, pos);
            Util.getMainWorkerExecutor().submit(placementTask);
            //Chunk chunk = serverWorld.getChunk(pos.x, pos.z, ChunkStatus.STRUCTURE_REFERENCES, false);
            //CompletableFuture<Either<Chunk, ChunkHolder.Unloaded>> futureChunk = serverWorld.getChunkManager().getChunkFutureSyncOnMainThread(pos.x, pos.z, ChunkStatus.STRUCTURE_STARTS, false);
            /*for(StructureStart start:startList)
            {
            LOGGER.info("registering "+start.getStructure().getClass().getName()+" for chunk "+pos.toString());
            //chunk.setStructureStart(start.getStructure(), start);
            start.place(serverWorld, serverWorld.getStructureAccessor(), chunkGenerator, serverWorld.random, bb, pos);
            //chunk.addStructureReference(start, pos.toLong());   
            }*/
            /*futureChunk.whenComplete((chunkOrUnloaded, throwable) -> {
            if(throwable==null)
            {
            Optional<Chunk> left = chunkOrUnloaded.left();
            if(left.isPresent())
            {
            Chunk chunk=left.get();
            for(StructureStart start:startList)
            {
            LOGGER.info("registering "+start.getStructure().getClass().getName()+" for chunk "+pos.toString());
            chunk.setStructureStart(start.getStructure(), start);
            //start.place(serverWorld, serverWorld.getStructureAccessor(), chunkGenerator, serverWorld.random, bb, pos);
            //chunk.addStructureReference(start, pos.toLong());
            }
            }
            }
            });*/
        }
    }

    @Override
    public void start() {
        
    }

    @Override
    public void stop() {
        
    }
    
    public class PlacementTask implements Runnable
    {
        ServerWorld serverWorld;
        List<StructureStart> starts;
        ChunkPos cp;

        public PlacementTask(ServerWorld serverWorld, List<StructureStart> starts, ChunkPos cp) {
            this.serverWorld = serverWorld;
            this.starts = starts;
            this.cp = cp;
        }
        
        

        @Override
        public void run() {
          //  LOGGER.info("placing structures for chunk "+cp.toString());
            //Chunk chunk = serverWorld.getChunk(cp.x, cp.z, ChunkStatus.FULL, false);
            ChunkGenerator chunkGenerator = serverWorld.getChunkManager().getChunkGenerator();
            BlockBox bb=new BlockBox(cp.x*16, -64, cp.z*16, cp.x*16+15, 320, cp.z*16+15);
            for(StructureStart start:starts)
            {
            //    LOGGER.info("placing "+start.getStructure().getClass().getName()+" for chunk "+cp.toString());
                start.place(serverWorld, serverWorld.getStructureAccessor(), chunkGenerator, serverWorld.random, bb, cp);
                
            }
        }
        
    }
    
}
