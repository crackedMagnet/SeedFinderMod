/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.commands;

import com.crackedmagnet.seedfindermod.CustomWorldPreset;
import com.crackedmagnet.seedfindermod.SeedFinderRegistries;
import static com.crackedmagnet.seedfindermod.commands.CriteriaAddCommand.LOGGER;
import com.crackedmagnet.seedfindermod.search.SeedSearch;
import com.crackedmagnet.seedfindermod.criteria.StructureClause;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class CriteriaListCommand implements Command<ServerCommandSource>{
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        LOGGER.debug("CriteriaListCommand.run()");
        if(!CustomWorldPreset.isSeedFinderWorld(context)) return -1;
        List<StructureClause> criteriaList=null;
        try
        {
            SeedSearch seedSearch = SeedFinderRegistries.DEFAULT_SEED_SEARCH.value();

            ServerCommandSource source = context.getSource();
            source.getPlayer().sendMessage(Text.literal(seedSearch.getCriteriaString()));
        }
        catch(Exception ex)
        {
            LOGGER.error("CriteriaListCommand.run() Error", ex);
            
        }
        return criteriaList==null?-1:criteriaList.size();
    }
    
}
