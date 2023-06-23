/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod;

import com.crackedmagnet.seedfindermod.search.SearchThreadHolder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matthewferguson
 */
public class SeedListHolder {
    private static List<Long> currentSeedList=null;

    public static List<Long> getCurrentSeedList() {
        return currentSeedList;
    }

    public static void load(String filename) throws IOException
    {
        currentSeedList=new ArrayList<>();
        try
        {
            try (FileReader fr = new FileReader(filename);BufferedReader br = new BufferedReader(fr)) {
                br.lines().forEach((line) -> {
                    String[] split = line.split("\\s");
                    if(split.length>0)
                    {
                        try
                        {
                            currentSeedList.add(Long.valueOf(split[0]));
                        }
                        catch(NumberFormatException ex)
                        {
                            //just ignore the ones that don't match.
                        }

                    }
                });
            }
        }
        catch(IOException ex)
        {
            currentSeedList=null; //clear seed list before throwing.
            throw ex;
        }
    }
    
    public static void clear()
    {
        currentSeedList=null;
        SearchThreadHolder.stopAndClear();
    }
    
    
}
