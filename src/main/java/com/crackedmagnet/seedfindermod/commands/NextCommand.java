/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.commands;

import com.crackedmagnet.seedfindermod.CustomWorldPreset;
import com.crackedmagnet.seedfindermod.SeedFinderRegistries;
import com.crackedmagnet.seedfindermod.SeedFinderUtils;
import com.crackedmagnet.seedfindermod.SeedHolder;
import com.crackedmagnet.seedfindermod.SeedListHolder;
import com.crackedmagnet.seedfindermod.Settings;
import com.crackedmagnet.seedfindermod.search.ChatSearchProgressHandler;
import com.crackedmagnet.seedfindermod.search.FileSeedProvider;
import com.crackedmagnet.seedfindermod.search.LinearSeedProvider;
import com.crackedmagnet.seedfindermod.search.NextSeedProvider;
import com.crackedmagnet.seedfindermod.search.SearchThread;
import com.crackedmagnet.seedfindermod.search.SearchThreadHolder;
import com.crackedmagnet.seedfindermod.search.SeedSearch;
import com.crackedmagnet.seedfindermod.search.ViewWorldSearchResultHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.IOException;
import java.util.List;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class NextCommand implements Command<ServerCommandSource> {
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");
    private boolean bedrock;

    public NextCommand(boolean bedrock) {
        this.bedrock = bedrock;
    }
     
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        LOGGER.debug("LoadSeedCommand.run()");
        if(!CustomWorldPreset.isSeedFinderWorld(context)) return -1;

        Settings.bedrock=bedrock;
        SearchThread currentSearchThread = SearchThreadHolder.getCurrentSearchThread();
        SeedSearch seedSearch = SeedFinderRegistries.DEFAULT_SEED_SEARCH.value();
        ViewWorldSearchResultHandler handler=new ViewWorldSearchResultHandler(context.getSource().getPlayer());
        ChatSearchProgressHandler searchProgressHandler=new ChatSearchProgressHandler(context.getSource().getPlayer());
        List<Long> currentSeedList = SeedListHolder.getCurrentSeedList();
        if(currentSearchThread==null) 
        {
            NextSeedProvider nextSeedProvider; 
            if(currentSeedList!=null) 
            {
                LOGGER.info("Starting search using seed file, count = " + Integer.toString(currentSeedList.size()));
                nextSeedProvider=new FileSeedProvider(currentSeedList);
            }
            else 
            {
                LOGGER.info("Starting search at seed "+Long.toString(SeedHolder.getCurrentSeed()));
                nextSeedProvider = new LinearSeedProvider(SeedHolder.getCurrentSeed(), SeedHolder.getCurrentSeed()>>>48);
            }
            LOGGER.info("About to create search thread");
            currentSearchThread=new SearchThread(seedSearch, nextSeedProvider , handler,searchProgressHandler);
            LOGGER.info("Search Thread Created");

            SearchThreadHolder.setCurrentSearchThread(currentSearchThread);
            LOGGER.info("Current Search Thread Set");
        }
        else LOGGER.info("Using existing search provider "+Long.toString(currentSearchThread.getCurrentSeed()));


        Util.getMainWorkerExecutor().submit(currentSearchThread);
        LOGGER.info("Thread Submitted");
        return 1;
    }

    
}
