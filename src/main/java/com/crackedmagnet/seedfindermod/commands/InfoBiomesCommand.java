/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.commands;

import com.crackedmagnet.seedfindermod.SeedFinderUtils;
import com.crackedmagnet.seedfindermod.biome.QuickBiomeSource;
import com.crackedmagnet.seedfindermod.biome.QuickSampler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;

/**
 *
 * @author matthewferguson
 */
public class InfoBiomesCommand implements Command<ServerCommandSource>{

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        long seed=context.getSource().getWorld().getSeed();
        QuickSampler quickSampler=new QuickSampler();
        quickSampler.reInit(seed);
        QuickBiomeSource bs=new QuickBiomeSource(quickSampler);
        ServerPlayerEntity player = context.getSource().getPlayer();
        Vec3d pos = player.getPos();
        int range=500;
        if(SeedFinderUtils.hasArgument(context,"distance"))
            range=context.getArgument("distance", Integer.class);
        Map<RegistryKey<Biome>, Integer> biomeCounts = bs.getBiomeCounts((int)pos.x,(int) pos.z, range,4);
        List<Entry<RegistryKey<Biome>, Integer>> entryList=new ArrayList<>(biomeCounts.entrySet());
        entryList.sort((Entry<RegistryKey<Biome>, Integer> o1, Entry<RegistryKey<Biome>, Integer>  o2) -> {
            return o1.getValue().compareTo(o2.getValue()); 
        });
        
        int total=0;
        for(Entry<RegistryKey<Biome>, Integer> entry:entryList)
            total+=entry.getValue();
        
        StringBuilder sb=new StringBuilder();
        sb.append("Approx Biome Distribution (");
        sb.append(range);
        sb.append(" blocks)\n");
        for(Entry<RegistryKey<Biome>, Integer> entry:entryList)
        {
            double proportion=(double) entry.getValue()/(double) total;
            sb.append(entry.getKey().getValue().getPath());
            sb.append(" ");
            if(proportion>=0.01) sb.append(Math.round(proportion*100));
            else sb.append(String.format("%3.2f",proportion*100));
            
            sb.append("%\n");
        }
        player.sendMessage(Text.literal(sb.toString()));

        return entryList.size();
    }
}
