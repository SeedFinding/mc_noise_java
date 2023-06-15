[![Build Status](https://jenkins.seedfinding.com/buildStatus/icon?job=mc_java%2Fmc_noise_java)](https://jenkins.seedfinding.com/job/mc_java/job/mc_noise_java/)

# Minecraft Noise

An efficient library to simulate Perlin and Simplex Noise as used in Minecraft.

## Usage

We don't recommend using that lib directly if you don't know how Noise generation works, please prefer using TerrainUtils

```java
JRand rand = new JRand(1L);
OctaveSimplexNoiseSampler simplexSampler = new OctaveSimplexNoiseSampler(rand, 2);
double value=simplexSampler.sample(0, 0);
```

```java
JRand rand = new JRand(1L);
OctavePerlinNoiseSampler perlinSampler = new OctavePerlinNoiseSampler(rand, Arrays.stream(new int[] {1, 2}));
double value=perlinSampler.sample(0, 0, 0);
```

## Legal mentions
Licensed under MIT

Maintained by Neil and KaptainWutax.

NOT OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG.
