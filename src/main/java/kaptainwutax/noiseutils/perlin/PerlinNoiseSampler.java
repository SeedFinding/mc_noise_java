package kaptainwutax.noiseutils.perlin;

import kaptainwutax.noiseutils.noise.Noise;
import kaptainwutax.seedutils.rand.JRand;

import static kaptainwutax.noiseutils.utils.MathHelper.*;

public class PerlinNoiseSampler extends Noise {

	public PerlinNoiseSampler(JRand rand) {
		super(rand);
	}

	public double sample(double x, double y, double z, double yAmplification, double minY) {
		double[] perms = sampleEx(x, y, z, yAmplification, minY);
		return lerp3(perms[0], perms[1], perms[2], perms[3], perms[4], perms[5], perms[6], perms[7], perms[8], perms[9], perms[10]);
	}

	public double[] sampleEx(double x, double y, double z, double yAmplification, double minY) {
		double offsetX = x + this.originX;
		double offsetY = y + this.originY;
		double offsetZ = z + this.originZ;
		// this could be done with modf
		int sectionX = floor(offsetX);
		int sectionY = floor(offsetY);
		int sectionZ = floor(offsetZ);
		double localX = offsetX - (double) sectionX;
		double localY = offsetY - (double) sectionY;
		double localZ = offsetZ - (double) sectionZ;

		double fadeLocalX = smoothStep(localX);
		double fadeLocalY = smoothStep(localY);
		double fadeLocalZ = smoothStep(localZ);

		// this is useful for 1.16+
		if (yAmplification != 0.0D) {
			double yFloor = Math.min(minY, localY);
			localY -= (double) floor(yFloor / yAmplification) * yAmplification;
		}
		return this.samplePermutations(sectionX, sectionY, sectionZ, localX, localY, localZ, fadeLocalX, fadeLocalY, fadeLocalZ);
	}

	public double[] samplePermutations(int sectionX, int sectionY, int sectionZ, double localX, double localY, double localZ, double fadeLocalX, double fadeLocalY, double fadeLocalZ) {
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

		return new double[] {fadeLocalX, fadeLocalY, fadeLocalZ, x1, x2, x3, x4, x5, x6, x7, x8};
	}

}
