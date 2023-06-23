/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.search;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matthewferguson
 */
public class FileSeedProvider implements NextSeedProvider{
    List<Long> seedList=new ArrayList<>();
    Long lastBiomeSeed=null;
    int idx=0;
    
    public static final Logger LOGGER = LoggerFactory.getLogger("seedfindermod");

    public FileSeedProvider(List<Long> seedList) {        
        this.seedList.addAll(seedList);
    }

    @Override
    public Long nextStructureSeed() {
        if(idx>=seedList.size())  return null;
        lastBiomeSeed=null;
        Long seed=seedList.get(idx);
        idx++;
        return seed;
    }

    @Override
    public Long nextBiomeSeed() {
        if(lastBiomeSeed!=null) return null; //its already asked for the biomes for this seed
        if(idx==0) return null;
        Long seed=seedList.get(idx-1);
        lastBiomeSeed=seed;
        return seed;
    }

}
