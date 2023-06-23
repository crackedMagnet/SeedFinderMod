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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class SeedFileCommand implements Command<ServerCommandSource> {
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");
   
    boolean clear;

    public SeedFileCommand(boolean clear) {
        this.clear = clear;
    }
    
     
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        LOGGER.debug("SeedFileCommand.run()");
        if(!CustomWorldPreset.isSeedFinderWorld(context)) return -1;
        
        if(clear) 
        {
            LOGGER.info("clearing seed file");
            SeedListHolder.clear();
            return 0;
        }

        String filename=null;
        if(SeedFinderUtils.hasArgument(context, "seed_list_filename")) filename=context.getArgument("seed_list_filename", String.class);
        
        try {
            LOGGER.info("Loading seed file: "+filename);
            SeedListHolder.load(filename);
        } catch (IOException ex) {
            LOGGER.error("Error loading seed file: "+filename,ex);
            context.getSource().getPlayer().sendMessage(Text.literal("Error loading seed file: "+ex.getMessage()));
        }
        List<Long> currentSeedList = SeedListHolder.getCurrentSeedList();
        if(currentSeedList==null) return -1;
        
        context.getSource().getPlayer().sendMessage(Text.literal("Seed file loaded, seed count = "+Integer.toString(currentSeedList.size())));
    
        return currentSeedList.size();
    }

    
}
