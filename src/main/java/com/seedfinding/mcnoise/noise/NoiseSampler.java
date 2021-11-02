package com.seedfinding.mcnoise.noise;

import com.seedfinding.mcseed.lcg.LCG;

public interface NoiseSampler {
	LCG SKIP_262 = LCG.JAVA.combine(262);

	double sample(double x, double y, double d, double e);
}
