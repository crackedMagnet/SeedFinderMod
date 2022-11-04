package com.crackedmagnet.seedfindermod.biome;

import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.Xoroshiro128PlusPlusRandom;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.gen.noise.NoiseParametersKeys;

/**
 *
 * @author matthewferguson
 */
public class QuickSampler {

    DoublePerlinNoiseSampler temperatureNoise=null;
    DoublePerlinNoiseSampler humidityNoise=null;
    DoublePerlinNoiseSampler continentalnessNoise=null;
    DoublePerlinNoiseSampler erosionNoise=null;
    DoublePerlinNoiseSampler weirdnessNoise=null;
    DoublePerlinNoiseSampler offsetNoise=null;
    
    private static final SeedHash temperatureHash=new SeedHash("minecraft:temperature");
    private static final SeedHash vegetationHash=new SeedHash("minecraft:vegetation");
    private static final SeedHash continentalnessHash=new SeedHash("minecraft:continentalness");
    private static final SeedHash erosionHash=new SeedHash("minecraft:erosion");
    private static final SeedHash ridgeHash=new SeedHash("minecraft:ridge");
    private static final SeedHash offsetHash=new SeedHash("minecraft:offset");

    private static final DoublePerlinNoiseSampler.NoiseParameters temperatureParams = BuiltinRegistries.NOISE_PARAMETERS.get(NoiseParametersKeys.TEMPERATURE);
    private static final DoublePerlinNoiseSampler.NoiseParameters vegetationParams = BuiltinRegistries.NOISE_PARAMETERS.get(NoiseParametersKeys.VEGETATION);
    private static final DoublePerlinNoiseSampler.NoiseParameters continentalnessParams = BuiltinRegistries.NOISE_PARAMETERS.get(NoiseParametersKeys.CONTINENTALNESS);
    private static final DoublePerlinNoiseSampler.NoiseParameters erosionParams = BuiltinRegistries.NOISE_PARAMETERS.get(NoiseParametersKeys.EROSION);
    private static final DoublePerlinNoiseSampler.NoiseParameters ridgeParams = BuiltinRegistries.NOISE_PARAMETERS.get(NoiseParametersKeys.RIDGE);
    private static final DoublePerlinNoiseSampler.NoiseParameters offsetParams = BuiltinRegistries.NOISE_PARAMETERS.get(NoiseParametersKeys.OFFSET);
    
    Xoroshiro128PlusPlusRandom rng=new Xoroshiro128PlusPlusRandom(0);
    
    public QuickSampler() {
      
    }
    
    private Xoroshiro128PlusPlusRandom rngFromHash(long forklo, long forkhi, SeedHash seedHash)
    {
        return new Xoroshiro128PlusPlusRandom(forklo^seedHash.hashlo, forkhi^seedHash.hashhi);
    }
    
    public void reInit(long seed)
    {
        rng.setSeed(seed);
        long forklo=rng.nextLong();
        long forkhi=rng.nextLong();

        temperatureNoise=DoublePerlinNoiseSampler.create(rngFromHash(forklo,forkhi,temperatureHash), temperatureParams);
        humidityNoise = DoublePerlinNoiseSampler.create(rngFromHash(forklo,forkhi,vegetationHash), vegetationParams);
        continentalnessNoise=DoublePerlinNoiseSampler.create(rngFromHash(forklo,forkhi,continentalnessHash), continentalnessParams);
        erosionNoise =DoublePerlinNoiseSampler.create(rngFromHash(forklo,forkhi,erosionHash), erosionParams);
        weirdnessNoise= DoublePerlinNoiseSampler.create(rngFromHash(forklo,forkhi,ridgeHash), ridgeParams);
        offsetNoise =  DoublePerlinNoiseSampler.create(rngFromHash(forklo,forkhi,offsetHash), offsetParams);
    }
    
    public SampleTarget sampleNoDepth(int qx,  int qz) {
      double qxd=qx;
      double qzd=qz;
      double offx = (double)qx + (this.offsetNoise.sample(qxd,0d,qzd)*4d); 
      double offz = (double)qz + (this.offsetNoise.sample(qzd,qxd,0d)*4d);
      
      double y=0; 
      double temperature = temperatureNoise.sample(offx, y, offz);
      double humidity = humidityNoise.sample(offx, y, offz);
      double continentalness = continentalnessNoise.sample(offx, 0.0D, offz);
      double erosion = erosionNoise.sample(offx, 0.0D, offz);
      double weridness = weirdnessNoise.sample(offx, 0.0D, offz);
      return new SampleTarget(temperature, humidity, continentalness, erosion, weridness, 0d);
   }

}
