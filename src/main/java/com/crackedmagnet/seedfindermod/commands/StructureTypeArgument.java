/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.commands;

import com.crackedmagnet.seedfindermod.SeedFinderRegistries;
import com.crackedmagnet.seedfindermod.structures.GridStructure;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class StructureTypeArgument implements ArgumentType<RegistryKey<GridStructure>>{
    
public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");
    @Override
    public RegistryKey<GridStructure> parse(StringReader reader) throws CommandSyntaxException {

        int argBeginning = reader.getCursor(); 

        while(reader.canRead() && Identifier.isCharValid(reader.peek())) {
         reader.skip();
        }

        String str = reader.getString().substring(argBeginning, reader.getCursor());
        try {
            
             GridStructure structure = SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY.get(new Identifier("seedfindermod:"+str));
            Optional<RegistryKey<GridStructure>> key = SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY.getKey(structure);
            return key.get(); 
        } catch (Exception ex) {
            throw new SimpleCommandExceptionType(Text.literal(ex.getMessage())).createWithContext(reader);
        }

    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
     
        Set<Identifier> ids = SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY.getIds();
        List<String> idPaths=new ArrayList<>();
        ids.forEach((t) -> {idPaths.add(t.getPath());});
        return CommandSource.suggestMatching(idPaths, builder);
     
    }

    @Override
    public Collection<String> getExamples() {
        List<String> output=new ArrayList<>();
        Set<Identifier> ids = SeedFinderRegistries.STRUCTURE_FINDER_REGISTRY.getIds();
        for(Identifier id:ids)
        {
            output.add(id.getPath());
        }
      
       return output;
    }
    
}
