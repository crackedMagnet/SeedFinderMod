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
public class HelpCommand implements Command<ServerCommandSource>{
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        LOGGER.debug("HelpCommand.run()");
        if(!CustomWorldPreset.isSeedFinderWorld(context)) return -1;
        String filthyHardcodedHelpText=
                """
                Seed Finder Mod find seeds that meet your criteria and 
                teleports you into those worlds.
                
                --- Search Criteria Examples ---
                Adding criteria for a village start <50 blocks from 0,0:
                /findseed criteria add structure java_village 50 spawn
                
                Adding criteria for a woodland mansion <150 blocks from 
                previous structure criteria location:
                /findseed criteria add structure java_woodland_mansion 150 criteriaIdx 0
                
                Adding criteria for badlands biome somewhere within 500 blocks
                /findseed criteria add biome minecraft:badlands
                
                Adding criteria for 20% jungle (jungle, sparse or bamboo)
                /findseed criteria add biome #minecraft:is_jungle 20
                
                Removing criteria
                /findseed criteria remove biome 0
                /findseed criteria remove structure 1
                
                List current criteria
                /findseed criteria list
                
                Clear criteria 
                /findseed criteria clear
                
                Note: Changing criteria will stop any search in progress.
                
                --- Running Search ---
                Find next seed that meets the criteria and move the player 
                into that world with java structure locations
                /findseed next
                
                Find next seed that meets the criteria and move the player 
                into that world with bedrock structure locations (actual 
                structures will generate differently in bedrock but will 
                be in the same locations)
                /findseed next_bedrock
                
                --- Other Function(s) ---
                Create a new overworld dimension with a specfic seed and
                move the player into that world. (java structures)
                /findseed load 69420
                
                Create or load an overworld dimension for the seed book you
                are holding in your main hand.
                /findseed load
                
                --- Tips / Notes ---
                Spawn is currently assumed to be 0,0.  For some searches
                spawns far for 0,0 are possible.
                
                It is possible to set impossible criteria, like having an outpost
                within 100 blocks of a village.  It is recommended that you add 
                criteria gradually to refine the type of seed you want.
                
                If you want an island seed try set large amount of ocean eg:
                /findseed criteria add biome #minecraft:is_ocean 80
                
                Each time you are teleported into a world you are given a
                book which contains the seed, the locations of the structures
                in the search criteria and details about the criteria used in
                the search.  You can use "/findseed load" while holding one
                of these books to revisit a world.  It is recommended to put
                seeds on your "short list" by putting them in an ender chest.
                You can then reload them later and decide which one you want
                to use.
                
                Each overworld is saved as a different dimension with the
                seed number in its name.  You can use "Open world folder" 
                in the world edit screen to see all the seeds.  These seed_ 
                folders can be deleted to save space.
                
                The reason for requiring the "Seed Finder" world type is to
                protect people from messing up the worlds they play in.  Don't
                just start playing in the seed finder world.  Create a new 
                world with the seed you found.
                
                *** The help doesn't fit on the screen *** 
                       Type / then scroll up chat
                """;
        
        context.getSource().getPlayer().sendMessage(Text.literal(filthyHardcodedHelpText));
        
        return 1;
    }
    
}
