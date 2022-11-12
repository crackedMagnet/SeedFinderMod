/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.commands;

import com.crackedmagnet.seedfindermod.stats.SpawnerMetric;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EnumArgumentType;
import net.minecraft.util.StringIdentifiable;


/**
 *
 * @author matthewferguson
 */
public class SpawnerMobPredicateArgumentType implements ArgumentType<SpawnerMetric.SpawnerPredicate>{

    List<String> values=Stream.of(SpawnerMetric.SpawnerPredicate.values()).map((t) -> {return t.name().toLowerCase();}).toList();
    
    public static SpawnerMobPredicateArgumentType spawnerMobPredicate()
    {
        return new SpawnerMobPredicateArgumentType();
    }

    @Override
    public SpawnerMetric.SpawnerPredicate parse(StringReader reader) throws CommandSyntaxException {
        String str = reader.readUnquotedString();
        return SpawnerMetric.SpawnerPredicate.valueOf(str.toUpperCase());
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return CommandSource.suggestMatching(values, builder);
    }

    @Override
    public Collection<String> getExamples() {
        return values;
    }

}
