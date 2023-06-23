/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;

/**
 *
 * @author matthewferguson
 */
public class SpawnerMetric {

    public static List<Pair<BlockPos,String>> getSpawners(ServerWorld world, BlockPos center, int chunkRange, SpawnerPredicate predicate)
    {
        ChunkPos centercp=new ChunkPos(center);
        ServerChunkManager chunkManager = world.getChunkManager();
        List<Pair<BlockPos,String>> output=new ArrayList<>();
        for(int x=-chunkRange;x<chunkRange;x++)
            for(int z=-chunkRange;z<chunkRange;z++)
            {
                Chunk chunk = chunkManager.getChunk(centercp.x+x, centercp.z+z, ChunkStatus.FULL, false);
                if(chunk==null) continue;
                Set<BlockPos> blockEntityPositions = chunk.getBlockEntityPositions();
                for(BlockPos bp:blockEntityPositions)
                {
                    Optional<MobSpawnerBlockEntity> blockEntityOptional = chunk.getBlockEntity(bp, BlockEntityType.MOB_SPAWNER);
                    if(blockEntityOptional.isEmpty()) continue;
                    MobSpawnerBlockEntity spawner=blockEntityOptional.get();
                    MobSpawnerLogic logic = spawner.getLogic();
                    Entity entity = logic.getRenderedEntity(world);
                    Identifier id = Registry.ENTITY_TYPE.getId(entity.getType());
                    if(!predicate.matches(entity.getType())) continue;
                    output.add(new Pair<>(bp,id.getPath()));
                }
            }
        return output;
    }
    
    public static List<List<Pair<BlockPos,String>>> getSpawnerGroups(ServerWorld world, BlockPos center, int chunkRange, SpawnerPredicate predicate, int minGroupSize)
    {
        List<Pair<BlockPos, String>> spawners = getSpawners(world, center, chunkRange, predicate);
        Map<BlockPos,Pair<BlockPos,String>> posMap=new HashMap<>();
        for(Pair<BlockPos, String> spawner:spawners)
        {
            posMap.put(spawner.getLeft(), spawner);
        }
        
        Map<BlockPos,Set<BlockPos>> inRangeMap=new HashMap<>();
        for(int i=0;i<spawners.size()-1;i++)
        {
            BlockPos bp1=spawners.get(i).getLeft();
            Set<BlockPos> inRange=new HashSet<>();
            for(int j=i+1;j<spawners.size();j++)
            {
                BlockPos bp2=spawners.get(j).getLeft();
                double dist = Math.sqrt(bp1.getSquaredDistance(bp2));
                if(dist<=30) inRange.add(bp2); //could be 32 in theory but that could get tricky to find as a spawn spot.
            }
            if(!inRange.isEmpty()) inRangeMap.put(bp1, inRange);
        }
        
        List<List<Pair<BlockPos,String>>> output=new ArrayList<>();
        
        for(Pair<BlockPos, String> spawner:spawners)
        {
            BlockPos bp=spawner.getLeft();
            Set<BlockPos> inRange = inRangeMap.get(bp);
            if(inRange==null) continue;
 
            List<Pair<BlockPos,String>> inRangeList=new ArrayList<>();
            inRangeList.add(posMap.get(bp));
            for(BlockPos bp2:inRange)
                inRangeList.add(posMap.get(bp2));
       
            List<List<Pair<BlockPos,String>>> results=Combinations.applyCombinations(inRangeList, SpawnerMetric::checkGroup);

            output.addAll(results);

        }
        if(minGroupSize==1) //adding single spawners if required
        {
            for(Pair<BlockPos, String> spawner:spawners)
            {
                output.add(List.of(spawner));
            }
        }
        return dedupe(output,minGroupSize);
    }
   
    /**
     * Ensures smaller groups of spawners aren't included if they are all already in a larger group
     * @param results list of spawner groups, largest first;
     * @param minGroupSize groups of spawners smaller than this won't be returned;
     * @return 
     */
    private static  List<List<Pair<BlockPos,String>>> dedupe(List<List<Pair<BlockPos,String>>> results, int minGroupSize)
    {
        List<List<Pair<BlockPos,String>>> output=new ArrayList<>();
        int maxCount=0;
        for(List<Pair<BlockPos,String>> result:results)
            if(maxCount<result.size()) maxCount=result.size();
        for(int i=maxCount;i>=minGroupSize;i--) //add the largest ones first so they are there when the smaller ones get checked.
        {
            for(List<Pair<BlockPos,String>> result:results)
            {
                if(result.size()!=i) continue;
                if(!contains(output, result)) output.add(result);
            }
        }
        return output;
    }
    
    private static boolean contains(List<List<Pair<BlockPos,String>>> list, List<Pair<BlockPos,String>> item)
    {
        for(List<Pair<BlockPos,String>> existing:list)
        {
            if(existing.containsAll(item)) return true;
        }
        return false;
    }
    
    private static double distance(BlockPos bp1, BlockPos bp2)
    {
        int xdiff=bp1.getX()-bp2.getX();
        int ydiff=bp1.getY()-bp2.getY();
        int zdiff=bp1.getZ()-bp2.getZ();
        return Math.sqrt(xdiff*xdiff+ydiff*ydiff+zdiff*zdiff);
    }
    
    private static List<Pair<BlockPos,String>> checkGroup(List<Pair<BlockPos,String>> spawners)
    {
        int size=spawners.size();
        if(size<2) return null;
   
        Box intersection=null;
        for(int i=0;i<size-1;i++)
        {
            Pair<BlockPos,String> spawner1=spawners.get(i);
            BlockPos bp1=spawner1.getLeft();
            Vec3d pos=new Vec3d(bp1.getX(), bp1.getY(), bp1.getZ());
            Box box=Box.of(pos.add(0.5,0.5,0.5), 30,30,30);
     
            if(intersection!=null&&!intersection.intersects(box)) 
            {
                return null;
            } //there is no overlap they can't all be in range
            intersection = (intersection==null) ? box : intersection.intersection(box); 
            
        }
        if(intersection==null) 
        {
            return null;
        } //shouldn't happen but keeps ide happy.
        // now we have an intersection box that represents the limits of what could be in range.  
        // go through all block positions until we find one that works.
        // this is really ugly but its all i've got right now.
     
        int minx=(int) Math.floor(intersection.minX);
        int miny=(int) Math.floor(intersection.minY);
        int minz=(int) Math.floor(intersection.minZ);
        int maxx=(int) Math.ceil(intersection.maxX);
        int maxy=(int) Math.ceil(intersection.maxY);
        int maxz=(int) Math.ceil(intersection.maxZ);
        
        //attempt to short cut by trying the middle first;
        if(allInRange(spawners, new BlockPos((maxx-minx)/2, (maxy-miny)/2, (maxz-minz)/2))) return spawners;
        for(int x=minx;x<=maxx;x++)
            for(int y=miny;y<=maxy;y++)
                for(int z=minz;z<=maxz;z++)
                {
                    if(allInRange(spawners, new BlockPos(x, y, z))) return spawners;
                }
  
        return null;
    }
    

    
    private static boolean allInRange(List<Pair<BlockPos,String>> spawners, BlockPos pos)
    {
        for(Pair<BlockPos,String> spawner:spawners)
        {
            double dist=distance(spawner.getLeft(), pos);
            if(dist>15) return false;
        }
        return true;
    }
    
    public enum SpawnerPredicate 
    {
        ALL((t) -> {return true;}),
        ZOMBIE_ONLY((t) -> {return t.equals(EntityType.ZOMBIE);}),
        SPIDER_ONLY((t) -> {return t.equals(EntityType.SPIDER);}),
        SKELETON_ONLY((t) -> {return t.equals(EntityType.SKELETON);}),
        CAVE_SPIDER_ONLY((t) -> {return t.equals(EntityType.CAVE_SPIDER);}),
        NOT_CAVE_SPIDER((t) -> {return !t.equals(EntityType.CAVE_SPIDER);});
        Function<EntityType, Boolean> predicate;
        SpawnerPredicate(Function<EntityType, Boolean> predicate)
        {
            this.predicate=predicate;
        }
        
        public boolean matches(EntityType entityType)
        {
            return this.predicate.apply(entityType);
        }
    }
    
}
