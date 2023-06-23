/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.event;

import com.crackedmagnet.seedfindermod.commands.CriteriaAddBiomeCommand;
import com.crackedmagnet.seedfindermod.commands.CriteriaAddCommand;
import com.crackedmagnet.seedfindermod.commands.CriteriaListCommand;
import com.crackedmagnet.seedfindermod.commands.CriteriaRemoveBiomeCommand;
import com.crackedmagnet.seedfindermod.commands.CriteriaRemoveCommand;
import com.crackedmagnet.seedfindermod.commands.CriteriaResetCommand;
import com.crackedmagnet.seedfindermod.commands.HelpCommand;
import com.crackedmagnet.seedfindermod.commands.InfoBiomesCommand;
import com.crackedmagnet.seedfindermod.commands.InfoSpawnersCommand;
import com.crackedmagnet.seedfindermod.commands.LoadSeedCommand;
import com.crackedmagnet.seedfindermod.commands.NextCommand;
import com.crackedmagnet.seedfindermod.commands.SeedFileCommand;
import com.crackedmagnet.seedfindermod.commands.SpawnerMobPredicateArgumentType;
import com.crackedmagnet.seedfindermod.commands.StructureTypeArgument;
import com.crackedmagnet.seedfindermod.structures.GridStructure;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.RegistryPredicateArgumentType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class CommandRegistrationHandler implements CommandRegistrationCallback {
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");

    @Override
    public void register(CommandDispatcher<ServerCommandSource> cd, CommandRegistryAccess cra, CommandManager.RegistrationEnvironment re) {
            LOGGER.debug("CommandRegistrationHandler.register()");
            DoubleArgumentType distanceArgumentType=DoubleArgumentType.doubleArg(0, 2000);
            IntegerArgumentType criteriaIdxArgumentType=IntegerArgumentType.integer();
 
            RequiredArgumentBuilder<ServerCommandSource, RegistryKey<GridStructure>> structureTypeBuilder = CommandManager.argument("structure_type", new StructureTypeArgument());
            
            
                    
            LiteralArgumentBuilder<ServerCommandSource> addBuilder = 
                    CommandManager.literal("add")
                            .then(CommandManager.literal("structure")
                                    .then(structureTypeBuilder
                                            .then(CommandManager.argument("max_distance", distanceArgumentType)
                                                .then(CommandManager.literal("spawn").executes(new CriteriaAddCommand()))
                                                .then(CommandManager.literal("criteriaIdx")
                                                        .then(CommandManager.argument("criteria_idx", criteriaIdxArgumentType)
                                                                .executes(new CriteriaAddCommand())
                                                        )
                                                    )
                                            )
                                    )
                            )
                            .then(CommandManager.literal("biome")
                                    .then(CommandManager.argument("biome", RegistryPredicateArgumentType.registryPredicate(RegistryKeys.BIOME))
                                    
                                               .executes(new CriteriaAddBiomeCommand())
                                       .then(CommandManager.argument("percent", IntegerArgumentType.integer(1, 100))
                                               .executes(new CriteriaAddBiomeCommand())))
                            );
            
            LiteralArgumentBuilder<ServerCommandSource> listBuilder=CommandManager.literal("list").executes(new CriteriaListCommand());
            LiteralArgumentBuilder<ServerCommandSource> seedFileBuilder=CommandManager.literal("seed_file")
                .then(CommandManager.literal("clear").executes(new SeedFileCommand(true)))
                .then(CommandManager.literal("load").then(CommandManager.argument("seed_list_filename", StringArgumentType.string()).executes(new SeedFileCommand(false))));
            LiteralArgumentBuilder<ServerCommandSource> nextBuilder=CommandManager.literal("next").executes(new NextCommand(false));
            LiteralArgumentBuilder<ServerCommandSource> nextBedrockBuilder=CommandManager.literal("next_bedrock").executes(new NextCommand(true));
            LiteralArgumentBuilder<ServerCommandSource> helpBuilder=CommandManager.literal("help").executes(new HelpCommand());
            LiteralArgumentBuilder<ServerCommandSource> resetBuilder=CommandManager.literal("clear").executes(new CriteriaResetCommand());
            LiteralArgumentBuilder<ServerCommandSource> removeBuilder=
                    CommandManager.literal("remove")
                            .then(CommandManager.literal("structure")
                                    .then(CommandManager.argument("criteria_idx", criteriaIdxArgumentType)
                                            .executes(new CriteriaRemoveCommand())
                                    )
                            )
                            .then(CommandManager.literal("biome")
                                    .then(CommandManager.argument("criteria_idx", criteriaIdxArgumentType)
                                            .executes(new CriteriaRemoveBiomeCommand())
                                    )
                            );
            
            
            
            
            LiteralArgumentBuilder<ServerCommandSource> spawnersBuilder=CommandManager.literal("spawners").executes(new InfoSpawnersCommand())
                    .then(CommandManager.argument("spawner_type",SpawnerMobPredicateArgumentType.spawnerMobPredicate()).executes(new InfoSpawnersCommand())
                        .then(CommandManager.argument("min_group_size", IntegerArgumentType.integer()).executes(new InfoSpawnersCommand()))
                    
                    );
            
            LiteralArgumentBuilder<ServerCommandSource> biomesBuilder=CommandManager.literal("biomes").executes(new InfoBiomesCommand())
                    .then(CommandManager.argument("distance", IntegerArgumentType.integer()).executes(new InfoBiomesCommand()));
            

            LiteralArgumentBuilder<ServerCommandSource> findSeedBuilder = CommandManager.literal("findseed")
                    .then(nextBuilder)
                    .then(nextBedrockBuilder)
                    .then(seedFileBuilder)
                    .then(helpBuilder)
                    .then(CommandManager.literal("info")
                            .then(spawnersBuilder)
                            .then(biomesBuilder)
                    )
                    .then(CommandManager.literal("criteria")  
                        .then(listBuilder)
                        .then(addBuilder)
                        .then(removeBuilder)
                        .then(resetBuilder)
                    )
                    .then(CommandManager.literal("load").executes(new LoadSeedCommand()))
                    .then(CommandManager.literal("load").then(CommandManager.argument("seed",LongArgumentType.longArg()).executes(new LoadSeedCommand())));
            
            cd.register(findSeedBuilder);
    }
    
}
