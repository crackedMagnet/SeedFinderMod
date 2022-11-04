/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.search;

/**
 *
 * @author matthewferguson
 */
public class SearchThreadHolder {
    private static SearchThread currentSearchThread;

    public static SearchThread getCurrentSearchThread() {
        return currentSearchThread;
    }

    public static void setCurrentSearchThread(SearchThread searchThread) {
        SearchThreadHolder.currentSearchThread = searchThread;
    }
    
    public static void stopAndClear()
    {
        if(currentSearchThread!=null) currentSearchThread.stop();
        currentSearchThread=null;
    }

    
}
