/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.search;


import com.crackedmagnet.seedfindermod.SeedHolder;
import com.crackedmagnet.seedfindermod.biome.QuickBiomeSource;
import com.crackedmagnet.seedfindermod.biome.QuickSampler;
import com.crackedmagnet.seedfindermod.criteria.StructureClause;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import net.minecraft.util.Pair;
import net.minecraft.util.math.ChunkPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class SearchThread implements Runnable{
    SeedSearch seedSearch;
    SearchResultHandler searchResultHandler;

    boolean isRunning48=false;
    boolean isRunning64=false;
    Calendar lastCheckin;
    long sinceCheckinCount=0;
    double currentSeedPerSec=0;
    NextSeedProvider nextSeedProvider;
    QuickSampler qs=new QuickSampler();
    QuickBiomeSource quickBiomeSource=new QuickBiomeSource(qs);
    SearchProgressHandler searchProgressHandler;
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");

    long last48Bit;
    
    public SearchThread(SeedSearch seedSearch, NextSeedProvider nextSeedProvider, SearchResultHandler searchResultHandler) {
        this.seedSearch=seedSearch;
        this.searchResultHandler=searchResultHandler;
        this.nextSeedProvider=nextSeedProvider;
        searchProgressHandler=null;
    }
    
     public SearchThread(SeedSearch seedSearch, NextSeedProvider nextSeedProvider,SearchResultHandler searchResultHandler, SearchProgressHandler searchProgressHandler) {
        this.seedSearch=seedSearch;
        this.searchResultHandler=searchResultHandler;
        this.nextSeedProvider=nextSeedProvider;
        this.searchProgressHandler=searchProgressHandler;
    }
    
    
            
    

    @Override
    public void run() {
        try
        {
            List<StructureClause> criteria = seedSearch.listCriteria();
            lastCheckin=Calendar.getInstance();
            LOGGER.info("SearchThread.run() start");
            isRunning48=true;

            while(isRunning48)
            {
                Long current48Bit=nextSeedProvider.nextStructureSeed();
                if(current48Bit==null)
                {
                    isRunning48=false;
                    searchProgressHandler.sendMessage("Seed List Exhausted"); //Should probably have some sort of seed result instead of the long.
                    break;
                }
                last48Bit=current48Bit;
                sinceCheckinCount++;
                List<List<ChunkPos>> match = seedSearch.findMatch(current48Bit);
                if(match!=null)
                {
                    LOGGER.info("SearchThread: Structure Match Found Starting Biomes "+Long.toString(current48Bit));

                   // long upper16=0;
                    isRunning64=true;
                    while(isRunning64) //Math.pow(2,16)
                    {
                        sinceCheckinCount++;
                        
                        Long current64Bit=nextSeedProvider.nextBiomeSeed();
                        if(current64Bit==null)
                        {
                            isRunning64=false;
                            break;
                        }
                        
                        if((current64Bit&0xFF000000000000l)==0) reportProgress();
             
                        qs.reInit(current64Bit); //reinitialises the noise generators with a new seed;
                        boolean seedRejected=false;
                        
                        List<Pair<String,ChunkPos>> structures=new ArrayList<>();
                        for(int i=0;i<match.size();i++)
                        {
                            int existsCount=0;
                            StructureClause sc=criteria.get(i);
                            
                            List<ChunkPos> chunkOptions=match.get(i);
                            for(ChunkPos cp:chunkOptions)
                            {
                                if(sc.isValid(current64Bit, quickBiomeSource, cp))
                                {
                                    existsCount++;
                                    structures.add(new Pair<>(sc.getStructureName(),cp));
                                }
                            }
                            if(existsCount==0)
                            {
                                seedRejected=true;
                                break;
                            }
                            
                        }
                        if(!seedRejected)
                        {
                            if(seedSearch.meetsBiomeCriteria(quickBiomeSource))
                            {
                                searchProgressHandler.foundSeed(current64Bit, structures);
                                searchResultHandler.handleResult(current64Bit, structures);
                                isRunning64=false;
                                isRunning48=false;
                            }
                        }
                        //upper16++;
                    }
                    LOGGER.info("SearchThread: Structure Match Found Finished Biomes "+Long.toString(current48Bit));

                }

                if((current48Bit&0xFFFF)==0)
                {
                    reportProgress();
                }

            }
            LOGGER.info("SearchThread.run() exit");
        }
        catch(Exception ex)
        {
            LOGGER.error("SearchThread.run() Error",ex);
        }
    }
    
    private void reportProgress()
    {
        Calendar currentTime=Calendar.getInstance();
        long diff=currentTime.getTimeInMillis()-lastCheckin.getTimeInMillis();
        if(diff>5000)
        {
            currentSeedPerSec=((double)sinceCheckinCount/(double)diff)*1000d;

            LOGGER.debug("SearchThread: "+Long.toString(last48Bit)+" "+Long.toString(sinceCheckinCount)+" "+Long.toString(Math.round(currentSeedPerSec)));
            if(searchProgressHandler!=null)
                searchProgressHandler.updateProgress(last48Bit, sinceCheckinCount, diff);
            lastCheckin=currentTime;
            sinceCheckinCount=0;

        }
    }
    
    public long getCurrentSeed()
    {
        return last48Bit;
    }
    
    public double getCurrentSeedsPerSec()
    {
        return currentSeedPerSec;
    }
    
    public void stop()
    {
        isRunning64=false;
        isRunning48=false;
    }

}
