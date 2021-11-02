package com.seedfinding.mcnoise.noise;


import kaptainwutax.mcutils.util.data.Pair;
import com.seedfinding.mcnoise.perlin.OctavePerlinNoiseSampler;
import kaptainwutax.seedutils.rand.JRand;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DoublePerlinNoiseSampler {
	private final double amplitude;
	private final OctavePerlinNoiseSampler firstSampler;
	private final OctavePerlinNoiseSampler secondSampler;

	public DoublePerlinNoiseSampler(JRand rand, IntStream octaves) {
		this(rand, octaves.boxed().collect(Collectors.toList()));
	}

	public DoublePerlinNoiseSampler(JRand rand, Pair<Integer, List<Double>> octavesParams) {
		this.firstSampler = new OctavePerlinNoiseSampler(rand, octavesParams);
		this.secondSampler = new OctavePerlinNoiseSampler(rand, octavesParams);
		int minNbOctaves = Integer.MAX_VALUE;
		int maxNbOctaves = Integer.MIN_VALUE;
		for(int idx = 0; idx < octavesParams.getSecond().size(); idx++) {
			double d0 = octavesParams.getSecond().get(idx);
			if(d0 != 0.0D) {
				minNbOctaves = Math.min(minNbOctaves, idx);
				maxNbOctaves = Math.max(maxNbOctaves, idx);
			}
		}
		this.amplitude = 0.16666666666666666D / createAmplitude(maxNbOctaves - minNbOctaves);
	}

	public DoublePerlinNoiseSampler(JRand rand, List<Integer> octaves) {
		this.firstSampler = new OctavePerlinNoiseSampler(rand, octaves);
		this.secondSampler = new OctavePerlinNoiseSampler(rand, octaves);
		int minNbOctave = octaves.stream().min(Integer::compareTo).orElse(0);
		int maxNbOctave = octaves.stream().max(Integer::compareTo).orElse(0);
		this.amplitude = 0.16666666666666666D / createAmplitude(maxNbOctave - minNbOctave);
	}

	private static double createAmplitude(int octaves) {
		return 0.1D * (1.0D + 1.0D / (double)(octaves + 1));
	}

	public double sample(double x, double y, double z) {
		double d = x * 1.0181268882175227D;
		double e = y * 1.0181268882175227D;
		double f = z * 1.0181268882175227D;
		return (this.firstSampler.sample(x, y, z) + this.secondSampler.sample(d, e, f)) * this.amplitude;
	}
}

