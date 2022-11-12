/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 *
 * @author matthewferguson
 */
public class Combinations {
    /**
     * Takes a list of objects and applies a function to every combination
     * and returns a list of the results. If the function returns null the
     * result is not added to the output list.
     * @param <T> The type of object the function takes a list of
     * @param <S> The type of the output of the function
     * @param list The list of objects that all combinations will be derived from
     * @param function The function that is applied to each combination of inputs
     * @return list of non null outputs from the function
     */
    public static <T,S> List<S> applyCombinations(List<T> list, Function<List<T>,S> function)
    {
        List<S> output=new ArrayList<>();
        int size=list.size();
        int combinations=(int) Math.pow(2, size);
        for(int mask=0;mask<combinations;mask++)
        {
            List<T> include=new ArrayList<>();
            for(int i=0;i<size;i++)
            {
                if((mask&(1<<i))>0) //basically if the bit is on, include the item
                    include.add(list.get(i));
            }
            S result=function.apply(include);
            if(result!=null) 
                output.add(result); 
        }
        return output;
    }
}
