package com.seedfinding.mcnoise;


import com.seedfinding.mcnoise.perlin.OctavePerlinNoiseSampler;
import com.seedfinding.mcnoise.simplex.OctaveSimplexNoiseSampler;
import com.seedfinding.mcnoise.simplex.SimplexNoiseSampler;
import com.seedfinding.mcseed.rand.JRand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoiseTest {
	@Test
	@DisplayName("Test Perlin noise")
	public void testPerlin() {
		JRand rand = new JRand(1L);
		OctavePerlinNoiseSampler perlinNoiseSampler = new OctavePerlinNoiseSampler(rand, 1);
		assertEquals(0.10709059654197703, perlinNoiseSampler.sample(0, 0, 0));
	}

	@Test
	@DisplayName("Test Perlin Octaves noise")
	public void testPerlinOctaves() {
		JRand rand = new JRand(1L);
		OctavePerlinNoiseSampler perlinNoiseSampler = new OctavePerlinNoiseSampler(rand, Arrays.stream(new int[] {1, 2}));
		double score = 0.0D;
		int bound = 100;
		for(int x = 0; x < bound; x++) {
			for(int y = 0; y < bound; y++) {
				for(int z = 0; z < bound; z++) {
					score += perlinNoiseSampler.sample(x, y, z);
				}
			}
		}
		assertEquals(2.5123135162530326, score);
	}


	@Test
	@DisplayName("Test Simplex noise")
	public void testSimplex() {
		JRand rand = new JRand(12);
		SimplexNoiseSampler simplexNoiseSampler = new SimplexNoiseSampler(rand);
		assertEquals(0.8331228771221665, simplexNoiseSampler.sample2D(0.5, 100));
		assertEquals(-0.047980544000000055, simplexNoiseSampler.sample3D(0.5, 0.6, 100.0));
	}

	@Test
	@DisplayName("Test Simplex Octaves noise")
	public void testSimplexOctaves() {
		JRand rand = new JRand(1L);
		OctaveSimplexNoiseSampler octaveSimplexNoiseSampler = new OctaveSimplexNoiseSampler(rand, 2);
		double score = 0.0D;
		int bound = 100;
		for(int x = 0; x < bound; x++) {
			for(int y = 0; y < bound; y++) {
				score += octaveSimplexNoiseSampler.sample(x, y);
			}
		}
		assertEquals(99.12262841409274, score); // this is unvalidated behavior but non regressive at least
	}

}
