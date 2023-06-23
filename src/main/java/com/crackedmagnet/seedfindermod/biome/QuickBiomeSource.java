/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.biome;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;

import net.minecraft.structure.StructureSet;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.biome.source.MultiNoiseBiomeSourceParameterLists;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class QuickBiomeSource {
 
    QuickSampler quickSampler;
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");
    
    private static final double[] temperatureBoundries=new double[]{-1.0,-0.45,-0.15,0.2,0.55,1.0};
    private static final double[] humidityBoundries=new double[]{-1.0,-0.35,-0.1,0.1,0.3,1.0};
    private static final double[] erosionBoundries=new double[]{-1.0,-0.78,-0.375,-0.2225,0.05,0.45,0.55,1.0};
    private static final double[] continentalnessBoundries=new double[]{-1.2,-1.05,-0.455,-0.19,-0.11,0.03,0.3,0.55,1.0};
    private static final double[] weirdnessBoundries=new double[]{-1.0,-0.9333333,-0.766666,-0.56666666,-0.4,-0.2666666,-0.05,0.05,0.266666,0.4,0.56666,0.766666,0.933333,1.0};

    private static final RegistryKey<Biome>[][][][][] biomeArray=new RegistryKey[temperatureBoundries.length-1][humidityBoundries.length-1][continentalnessBoundries.length-1][erosionBoundries.length-1][weirdnessBoundries.length-1];

    public QuickBiomeSource(QuickSampler quickSampler) {
        this.quickSampler = quickSampler;
    }
    
    
    
    public static void bootstrap(RegistryEntryLookup.RegistryLookup registryLookup)
    {
        LOGGER.info("QuickBiomeSource bootstrap()");
        RegistryEntryLookup<MultiNoiseBiomeSourceParameterList> noiseParmetersRegistry = registryLookup.getOrThrow(RegistryKeys.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST);

        RegistryEntry.Reference<MultiNoiseBiomeSourceParameterList> overworldParametersEntry = noiseParmetersRegistry.getOrThrow(MultiNoiseBiomeSourceParameterLists.OVERWORLD);
        MultiNoiseBiomeSource biomeSource=MultiNoiseBiomeSource.create(overworldParametersEntry);

        //yes i know this is awful but i'm doing it anyway.
        for(int t=0;t<temperatureBoundries.length-1;t++)
        {
            long tv=(long)((temperatureBoundries[t]+temperatureBoundries[t+1])*5000d); // same as average * 10000;
            for(int h=0;h<humidityBoundries.length-1;h++)
            {
                long hv=(long)((humidityBoundries[h]+humidityBoundries[h+1])*5000d);
                for(int e=0;e<erosionBoundries.length-1;e++)
                {
                    long ev=(long)((erosionBoundries[e]+erosionBoundries[e+1])*5000d);
                    for(int c=0;c<continentalnessBoundries.length-1;c++)
                    {
                        long cv=(long)((continentalnessBoundries[c]+continentalnessBoundries[c+1])*5000d);
                        for(int w=0;w<weirdnessBoundries.length-1;w++)
                        {
                            long wv=(long)((weirdnessBoundries[w]+weirdnessBoundries[w+1])*5000d);
                            MultiNoiseUtil.NoiseValuePoint point = new MultiNoiseUtil.NoiseValuePoint(tv, hv, cv, ev, 0,wv); //depth of 0 aka surface
                            RegistryEntry<Biome> biome = biomeSource.getBiomeAtPoint(point);
                            
                            //biomeArray[t][h][c][e][w]=biome.value();
                            biomeArray[t][h][c][e][w]=biome.getKey().orElseThrow();
                        }
                    }
                }
            }
        }
    }
    
    public RegistryKey<Biome> getSurfaceBiome(int bx,int bz)
    {
        SampleTarget sample = quickSampler.sampleNoDepth(bx>>2, bz>>2); //block pos to quart pos
        int t=findIdx(sample.temperature(), temperatureBoundries);
        int h=findIdx(sample.humidity(), humidityBoundries);
        int e=findIdx(sample.erosion(), erosionBoundries);
        int c=findIdx(sample.continentalness(), continentalnessBoundries);
        int w=findIdx(sample.weirdness(), weirdnessBoundries);

        return biomeArray[t][h][c][e][w];
    }
    
    private int findIdx(double value, double[] boundries) //hopefully the compiler is smart enough to inline this as its going to get called alot.
    {
        int idx=1;
        while(idx<boundries.length&&boundries[idx]<value) idx++;
        if(idx>=boundries.length) return boundries.length-2;
        return idx-1;
    }
    
    public Map<RegistryKey<Biome>,Integer> getBiomeCounts(int bx, int bz, int range)
    {
        return getBiomeCounts(bx, bz, range, 50);
    }
    
    public Map<RegistryKey<Biome>,Integer> getBiomeCounts(int bx, int bz, int range, int increment)
    {
        int rangesquare=range*range;
        Map<RegistryKey<Biome>,Integer> output=new HashMap<>();
        for(int x=-range;x<range;x+=increment)
            for(int z=-range;z<range;z+=increment)
            {
                int distsquare=x*x+z*z;
                if(distsquare>rangesquare) continue;
                RegistryKey<Biome> surfaceBiome = getSurfaceBiome(bx+x, bz+z);
                Integer count = output.getOrDefault(surfaceBiome, 0);
                count++;
                output.put(surfaceBiome, count);
            }
        
        return output;
    }
    
}
