/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.commands;


import com.crackedmagnet.seedfindermod.CustomWorldPreset;
import com.crackedmagnet.seedfindermod.SeedFinderRegistries;
import com.crackedmagnet.seedfindermod.SeedFinderUtils;
import static com.crackedmagnet.seedfindermod.commands.CriteriaResetCommand.LOGGER;
import com.crackedmagnet.seedfindermod.search.SeedSearch;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WritableBookItem;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class LoadSeedCommand implements Command<ServerCommandSource>{
    
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");
    public LoadSeedCommand() {
        

    }
    
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        LOGGER.debug("LoadSeedCommand.run()");
        try {
            if(!CustomWorldPreset.isSeedFinderWorld(context)) return -1;
            boolean hasSeed=SeedFinderUtils.hasArgument(context, "seed");
            Long seed;
            if(hasSeed) seed = context.getArgument("seed", Long.class);
            else
            {
                //attempt to get seed from book the player is holding
                ServerPlayerEntity player = context.getSource().getPlayer();
                ItemStack mainHandStack = player.getMainHandStack();
                Item item = mainHandStack.getItem();
                if((item instanceof WritableBookItem) || item instanceof WrittenBookItem )
                {
                    Text name = mainHandStack.getName();
                    if(name!=null)
                    {
                        try
                        {
                            seed=Long.parseLong(name.asTruncatedString(32));
                            NbtCompound seedSearchNbt = mainHandStack.getSubNbt("seedSearch");
                            if(seedSearchNbt!=null)
                            {
                                SeedSearch seedSearch = SeedFinderRegistries.DEFAULT_SEED_SEARCH.value();
                                seedSearch.setFromNbt(seedSearchNbt);
                                LOGGER.info("Loading search criteria from book");
                            }
                        }
                        catch(NumberFormatException formatException)
                        {
                            player.sendMessage(Text.literal("Seed book name is not a seed number."));
                            return -1;
                        }
                            
                    }
                    else 
                    {
                        player.sendMessage(Text.literal("Seed book missing seed in name."));
                        return -1;
                    }
                        
                }
                else
                {
                    player.sendMessage(Text.literal("You must specify a seed or be holding a seed book."));
                    return -1;
                }
            }
            LOGGER.info("Loading seed "+seed.toString());
       
            MinecraftServer server = context.getSource().getServer();
       
            ServerWorld addWorld = server.addWorld(seed);
            context.getSource().getPlayer().moveToWorld(addWorld);
            
       
        } catch (Exception ex) {
            LOGGER.error("LoadSeedCommand.run() Error", ex);
        }    
        
        return 0;        
    }
    
  
}
