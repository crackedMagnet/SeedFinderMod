/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.commands;

import com.crackedmagnet.seedfindermod.CustomWorldPreset;
import com.crackedmagnet.seedfindermod.SeedFinderRegistries;
import com.crackedmagnet.seedfindermod.SeedFinderUtils;
import com.crackedmagnet.seedfindermod.search.SeedSearch;
import com.crackedmagnet.seedfindermod.criteria.StructureClause;
import com.crackedmagnet.seedfindermod.search.SearchThreadHolder;
import com.crackedmagnet.seedfindermod.structures.GridStructure;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.registry.RegistryKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class CriteriaAddCommand implements Command<ServerCommandSource>{
 public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if(!CustomWorldPreset.isSeedFinderWorld(context)) return -1;

        LOGGER.debug("CriteriaAddCommand.run()");
        SeedSearch seedSearch = SeedFinderRegistries.DEFAULT_SEED_SEARCH.value();
        SearchThreadHolder.stopAndClear();
        int result=-1;
        try
        {
            synchronized (seedSearch) {

                RegistryKey<GridStructure> structureKey = context.getArgument("structure_type", RegistryKey.class);

                Double maxDistance = context.getArgument("max_distance", Double.class);
                if(SeedFinderUtils.hasArgument(context, "criteria_idx"))
                {
                    Integer criteriaIdx=context.getArgument("criteria_idx", Integer.class);
                    result=seedSearch.addCriteria(new StructureClause(structureKey, criteriaIdx, maxDistance));
                }
                else result=seedSearch.addCriteria(new StructureClause(structureKey, maxDistance));

            }
            ServerCommandSource source = context.getSource();
            source.getPlayer().sendMessage(Text.literal("Criteria Added.  Current Criteria:\n"+seedSearch.getCriteriaString()));
            LOGGER.debug("added criteria");
        }
         catch(Exception ex)
         {
             LOGGER.error("CriteriaAddCommand.run() Error",ex);
         }
        
        return result;
    }
    
}
