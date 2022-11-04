/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.commands;

import com.crackedmagnet.seedfindermod.CustomWorldPreset;
import com.crackedmagnet.seedfindermod.SeedFinderRegistries;
import com.crackedmagnet.seedfindermod.criteria.BiomeClause;
import com.crackedmagnet.seedfindermod.criteria.BiomeProportionClause;
import com.crackedmagnet.seedfindermod.search.SeedSearch;
import com.crackedmagnet.seedfindermod.search.SearchThreadHolder;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.command.argument.RegistryPredicateArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class CriteriaAddBiomeCommand implements Command<ServerCommandSource>{
 public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if(!CustomWorldPreset.isSeedFinderWorld(context)) return -1;
     context.getSource().getWorld().getChunkManager().getChunkGenerator();
     

        LOGGER.debug("CriteriaAddBiomeCommand.run()");
        SeedSearch seedSearch = SeedFinderRegistries.DEFAULT_SEED_SEARCH.value();
        SearchThreadHolder.stopAndClear();
        int result=-1;
        try
        {
            synchronized (seedSearch) {


                RegistryPredicateArgumentType.RegistryPredicate<Biome> biomePredicate=context.getArgument("biome",RegistryPredicateArgumentType.RegistryPredicate.class);

                Set<RegistryKey<Biome>> biomes=new HashSet<>();

                ServerWorld world = context.getSource().getWorld();
                BiomeSource biomeSource = world.getChunkManager().getChunkGenerator().getBiomeSource();

                Set<RegistryEntry<Biome>> entrySet = biomeSource.getBiomes().stream().filter(biomePredicate).collect(Collectors.toUnmodifiableSet());
         
                entrySet.forEach((entry) -> { 
                    //biomes.add(BuiltinRegistries.BIOME.get(entry.getKey().get().getValue()));
                    
                    biomes.add(entry.getKey().orElseThrow()); 
                });
               
                List<ParsedCommandNode<ServerCommandSource>> nodes = context.getNodes();
                boolean hasPercent=false;
                //this is messy, there must be a better way.
                for(ParsedCommandNode<ServerCommandSource> node:nodes)
                {
                    String name = node.getNode().getName();
                    if(name.equals("percent")) 
                    {
                        hasPercent=true;
                        break;
                    }
                }
                if(hasPercent)
                {
                    Integer percent=context.getArgument("percent", Integer.class);
                    
                    result=seedSearch.addCriteria(new BiomeProportionClause(biomes,biomePredicate.asString(),percent));
                }
                else result=seedSearch.addCriteria(new BiomeClause(biomes,biomePredicate.asString()));


            }
            
            ServerCommandSource source = context.getSource();
            
            source.getPlayer().sendMessage(Text.literal("Criteria Added.  Current Criteria:\n"+seedSearch.getCriteriaString()));
            LOGGER.debug("added criteria");
        }
         catch(Exception ex)
         {
             LOGGER.error("CriteriaAddBiomeCommand.run() Error",ex);
         }
        
        return result;
    }
    
}
