/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.search;

import com.crackedmagnet.seedfindermod.SeedFinderRegistries;
import com.crackedmagnet.seedfindermod.SeedFinderUtils;
import java.util.List;
import java.util.function.UnaryOperator;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class ViewWorldSearchResultHandler extends SearchResultHandler{
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");

    ServerPlayerEntity player;

    public ViewWorldSearchResultHandler(ServerPlayerEntity player) {
        this.player = player;
    }
    
    @Override
    public  void handleResult(long seed64, List<Pair<String, ChunkPos>> structures) {            
        LOGGER.debug("ViewWorldSearchResultHandler.handleResult() enter seed: "+Long.toString(seed64));
        if(LOGGER.isDebugEnabled()) SeedFinderUtils.logChunkStructures(structures);//SeedFinderUtils.logChunkMatches(result);
        try
        {
            
            MinecraftServer server = player.getServer();
            AddWorldTask addWorldTask = new AddWorldTask(seed64);
            PlayerInventory inventory = player.getInventory();
           
            int emptySlot = inventory.getEmptySlot();
            ItemStack book=createBook(seed64, structures);
            if(emptySlot>=0)
            {
                inventory.insertStack(emptySlot, book);
                inventory.swapSlotWithHotbar(emptySlot);
                
            }
            else inventory.offerOrDrop(book);
            server.executeSync(addWorldTask);

        }
        catch(Exception ex)
        {
            LOGGER.error("ViewWorldSearchResultHandler.handleResult() Error",ex);
        }
        LOGGER.debug("ViewWorldSearchResultHandler.handleResult() exit seed: "+Long.toString(seed64));
    }
    
    private ItemStack createBook(long seed64, List<Pair<String, ChunkPos>> structures)
    {
        StringBuilder sb=new StringBuilder();
        sb.append(seed64);
        sb.append("\n\n");
        for(Pair<String, ChunkPos> structure:structures)
        {
            sb.append(structure.getLeft());
            sb.append("\n");
            BlockPos bp= structure.getRight().getStartPos();
            sb.append("/tp ");
            sb.append(bp.getX());
            sb.append(" ~ ");
            sb.append(bp.getZ());
            sb.append("\n\n");
        }
        
        SeedSearch seedSearch = SeedFinderRegistries.DEFAULT_SEED_SEARCH.value();
  
        ItemStack stack=new ItemStack(Items.WRITABLE_BOOK);
        UnaryOperator<String> op=UnaryOperator.identity();
        NbtList pages=new NbtList();
        NbtString seedPage=NbtString.of(op.apply(sb.toString()));
        NbtString criteriaPage=NbtString.of(op.apply(seedSearch.getBookCriteriaString()));
        pages.add(seedPage);
        pages.add(criteriaPage);
        stack.setSubNbt("pages", pages);
        stack.setSubNbt("title", NbtString.of(Long.toString(seed64)));
        stack.setSubNbt("author", NbtString.of("Seed Finder Mod"));
        stack.setCustomName(Text.literal(Long.toString(seed64)));
        return stack;
    }
    
    public class AddWorldTask implements Runnable
    {
        long seed;

        public AddWorldTask(long seed) {
            this.seed = seed;
        }
        

        @Override
        public void run() {
            MinecraftServer server = player.getServer();
            ServerWorld addWorld = server.addWorld(seed);
            player.moveToWorld(addWorld);
            addWorld.setWeather(0, 0, false, false);
        }
        
    }
    
}
