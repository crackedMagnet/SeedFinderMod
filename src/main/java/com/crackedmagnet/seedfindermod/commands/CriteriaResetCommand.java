/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.commands;

import com.crackedmagnet.seedfindermod.CustomWorldPreset;
import com.crackedmagnet.seedfindermod.SeedFinderRegistries;
import static com.crackedmagnet.seedfindermod.commands.CriteriaRemoveCommand.LOGGER;
import com.crackedmagnet.seedfindermod.search.SearchThreadHolder;
import com.crackedmagnet.seedfindermod.search.SeedSearch;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class CriteriaResetCommand implements Command<ServerCommandSource>{
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        LOGGER.debug("CriteriaResetCommand.run()");
        if(!CustomWorldPreset.isSeedFinderWorld(context)) return -1;
        SeedSearch seedSearch = SeedFinderRegistries.DEFAULT_SEED_SEARCH.value();
        SearchThreadHolder.stopAndClear();
        seedSearch.resetCriteria();
        context.getSource().getPlayer().sendMessage(Text.literal("Search Criteria Cleared"));
        return 0;
    }
    
}
