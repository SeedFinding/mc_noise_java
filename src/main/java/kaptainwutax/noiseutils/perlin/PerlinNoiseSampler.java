package kaptainwutax.noiseutils.perlin;

import kaptainwutax.noiseutils.noise.Noise;
import kaptainwutax.seedutils.lcg.rand.JRand;

import static kaptainwutax.noiseutils.utils.MathHelper.*;

public class PerlinNoiseSampler extends Noise {

	public PerlinNoiseSampler(JRand rand) {
		super(rand);
	}

	public double sample(double x, double y, double z, double yAmplification, double minY) {
		double offsetX = x + this.originX;
		double offsetY = y + this.originY;
		double offsetZ = z + this.originZ;
		// this could be done with modf
		int intX = floor(offsetX);
		int intY = floor(offsetY);
		int intZ = floor(offsetZ);
		double fracX = offsetX - (double)intX;
		double fracY = offsetY - (double)intY;
		double fracZ = offsetZ - (double)intZ;
		double clampY=0.0D;
		if (yAmplification != 0.0D) {
			double yFloor = Math.min(minY, fracY);
			clampY = (double)floor(yFloor / yAmplification) * yAmplification;
		}
		return this.sample(intX, intY, intZ, fracX, fracY - clampY, fracZ, smoothStep(fracX), smoothStep(fracY), smoothStep(fracZ));
	}

	public double sample(int sectionX, int sectionY, int sectionZ, double localX, double localY, double localZ, double fadeLocalX, double fadeLocalY, double fadeLocalZ) {
		int pXY = this.lookup(sectionX) + sectionY;
		int pX1Y = this.lookup(sectionX + 1) + sectionY;

		int ppXYZ = this.lookup(pXY) + sectionZ;
		int ppX1YZ = this.lookup(pX1Y) + sectionZ;

		int ppXY1Z = this.lookup(pXY + 1) + sectionZ;
		int ppX1Y1Z = this.lookup(pX1Y + 1) + sectionZ;

		double x1 = grad(this.lookup(ppXYZ), localX, localY, localZ);
		double x2 = grad(this.lookup(ppX1YZ), localX - 1.0D, localY, localZ);
		double x3 = grad(this.lookup(ppXY1Z), localX, localY - 1.0D, localZ);
		double x4 = grad(this.lookup(ppX1Y1Z), localX - 1.0D, localY - 1.0D, localZ);
		double x5 = grad(this.lookup(ppXYZ + 1), localX, localY, localZ - 1.0D);
		double x6 = grad(this.lookup(ppX1YZ + 1), localX - 1.0D, localY, localZ - 1.0D);
		double x7 = grad(this.lookup(ppXY1Z + 1), localX, localY - 1.0D, localZ - 1.0D);
		double x8 = grad(this.lookup(ppX1Y1Z + 1), localX - 1.0D, localY - 1.0D, localZ - 1.0D);
		return lerp3(fadeLocalX, fadeLocalY, fadeLocalZ, x1, x2, x3, x4, x5, x6, x7, x8);
	}
}
