/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.commands;

import com.crackedmagnet.seedfindermod.CustomWorldPreset;
import com.crackedmagnet.seedfindermod.SeedFinderRegistries;
import com.crackedmagnet.seedfindermod.Settings;
import static com.crackedmagnet.seedfindermod.commands.NextBedrockCommand.LOGGER;
import com.crackedmagnet.seedfindermod.search.ChatSearchProgressHandler;
import com.crackedmagnet.seedfindermod.search.SearchThread;
import com.crackedmagnet.seedfindermod.search.SearchThreadHolder;
import com.crackedmagnet.seedfindermod.search.SeedSearch;
import com.crackedmagnet.seedfindermod.search.ViewWorldSearchResultHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class NextCommand implements Command<ServerCommandSource> {
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        LOGGER.debug("NextCommand.run()");
        if(!CustomWorldPreset.isSeedFinderWorld(context)) return -1;
        Settings.bedrock=false;
        SearchThread currentSearchThread = SearchThreadHolder.getCurrentSearchThread();
        SeedSearch seedSearch = SeedFinderRegistries.DEFAULT_SEED_SEARCH.value();
        ViewWorldSearchResultHandler handler=new ViewWorldSearchResultHandler(context.getSource().getPlayer());
        ChatSearchProgressHandler searchProgressHandler=new ChatSearchProgressHandler(context.getSource().getPlayer());
        if(currentSearchThread==null) 
        {
            currentSearchThread=new SearchThread(seedSearch, handler,searchProgressHandler);
            
            SearchThreadHolder.setCurrentSearchThread(currentSearchThread);
        }
        LOGGER.info("Starting/continuing search at seed "+Long.toString(currentSearchThread.getCurrentSeed()));
        Util.getMainWorkerExecutor().submit(currentSearchThread);
        
        return 1;
    }

    
}
