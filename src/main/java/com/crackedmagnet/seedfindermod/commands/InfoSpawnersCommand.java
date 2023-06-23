/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.commands;

import com.crackedmagnet.seedfindermod.CustomWorldPreset;
import com.crackedmagnet.seedfindermod.SeedFinderUtils;
import static com.crackedmagnet.seedfindermod.commands.CriteriaListCommand.LOGGER;
import com.crackedmagnet.seedfindermod.stats.SpawnerMetric;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;

/**
 *
 * @author matthewferguson
 */
public class InfoSpawnersCommand implements Command<ServerCommandSource>{

    

    
    
    
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        LOGGER.debug("InfoSpawnersCommand.run()");
        if(!CustomWorldPreset.isSeedFinderWorld(context)) return -1;
        
        try
        {
            ServerWorld world = context.getSource().getWorld();
            BlockPos playerPos=new BlockPos(context.getSource().getPlayer().getPos());
            StringBuilder sb=new StringBuilder();

            SpawnerMetric.SpawnerPredicate predicate=SpawnerMetric.SpawnerPredicate.ALL;
            if(SeedFinderUtils.hasArgument(context, "spawner_type"))
            {
                predicate=context.getArgument("spawner_type", SpawnerMetric.SpawnerPredicate.class);
            }
            
            
            int minGroupSize=1;
            if(SeedFinderUtils.hasArgument(context, "min_group_size"))
            {
                minGroupSize=context.getArgument("min_group_size", Integer.class);
            }
            List<List<Pair<BlockPos, String>>> spawnerGroups = SpawnerMetric.getSpawnerGroups(world, playerPos, 20,predicate,minGroupSize);
            Collections.reverse(spawnerGroups); //So the largest ones are last, because they appear at the bottom of chat.
            int prevGroupSize=-1;
            for(List<Pair<BlockPos, String>> group:spawnerGroups)
            {
                if(group.size()!=prevGroupSize)
                {
                    prevGroupSize=group.size();
                    if(prevGroupSize>1)
                    {
                        sb.append("\n");
                        sb.append(prevGroupSize);
                        sb.append("x Spawner Groups:\n");
                    }
                    else 
                    {
                        sb.append("Single Spawners:\n\n");
                    }
                }
                if(prevGroupSize>1) sb.append("\n");
                for(Pair<BlockPos, String> spawner:group)
                {
                    sb.append(spawner.getLeft().toShortString());
                    sb.append(" ");
                    sb.append(spawner.getRight());
                    sb.append("\n");
                }
                
            }
            
            context.getSource().getPlayer().sendMessage(Text.literal(sb.toString()));
        }
        catch(Exception ex)
        {
            LOGGER.error("InfoSpawnersCommand.run() Error", ex);
            
        }
        return SINGLE_SUCCESS;
    }
    
}
