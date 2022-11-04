/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod;


import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedCommandNode;
import java.util.List;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Pair;
import net.minecraft.util.math.ChunkPos;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class SeedFinderUtils {
    public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger("seedfindermod");
    public static void logStackTrace()
    {
        
        LOGGER.info("LoadSeedCommand.run() Tread Name: "+Thread.currentThread().getName());
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb=new StringBuilder();
        for(StackTraceElement ste:stackTrace)
        {
            sb.append(ste.toString());
            sb.append("\n");
        }
        LOGGER.info(sb.toString());
        
    }
    
    public static void logChunkMatches(List<List<ChunkPos>> matches)
    {
        StringBuilder sb=new StringBuilder();
        sb.append("[\n");
        for(List<ChunkPos> chunks:matches)
        {
            sb.append("\t[");
            boolean first=false;
            for(ChunkPos cp:chunks)
            {
                if(!first) sb.append(",");
                sb.append(cp.toString());
                first=true;
            }
            sb.append("]");
        }
        sb.append("\n[");
        LOGGER.info(sb.toString());
    }
    public static void logChunkStructures(List<Pair<String, ChunkPos>> structures)
    {
        StringBuilder sb=new StringBuilder();
        sb.append("[\n");
        for(Pair<String, ChunkPos> structure:structures)
        {
            sb.append(structure.getLeft());
            sb.append(" ");
            sb.append(structure.getRight().toString());
        }
        sb.append("\n[");
        LOGGER.info(sb.toString());
    }
    
    public static boolean hasArgument(CommandContext<ServerCommandSource> context, String argName)
    {
        List<ParsedCommandNode<ServerCommandSource>> nodes = context.getNodes();
        for(ParsedCommandNode<ServerCommandSource> node:nodes)
        {
            String name = node.getNode().getName();
            if(name.equals(argName)) 
                return true;
        }
        return false;
    }
    
    
}
