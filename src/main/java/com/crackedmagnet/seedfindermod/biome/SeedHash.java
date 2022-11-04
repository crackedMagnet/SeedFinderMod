/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crackedmagnet.seedfindermod.biome;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Longs;

/**
 *
 * @author matthewferguson
 */
@SuppressWarnings("deprecation")
public class SeedHash {
    public final long hashlo;
    public final long hashhi;

    public SeedHash(long hashlo, long hashhi) {
        this.hashlo = hashlo;
        this.hashhi = hashhi;
    }
    public SeedHash(String str)
    {
        
        byte[] hashBytes = Hashing.md5().hashString(str, Charsets.UTF_8).asBytes();
        hashlo = Longs.fromBytes(hashBytes[0], hashBytes[1], hashBytes[2], hashBytes[3], hashBytes[4], hashBytes[5], hashBytes[6], hashBytes[7]);
        hashhi = Longs.fromBytes(hashBytes[8], hashBytes[9], hashBytes[10], hashBytes[11], hashBytes[12], hashBytes[13], hashBytes[14], hashBytes[15]);
       
        
    }
}
