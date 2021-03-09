package kaptainwutax.noiseutils.noise;

import kaptainwutax.seedutils.lcg.rand.JRand;

public class Noise {
    protected final byte[] permutations=new byte[256];
    public final double originX;
    public final double originY;
    public final double originZ;

    public Noise(JRand rand){
        this.originX = rand.nextDouble() * 256.0D;
        this.originY = rand.nextDouble() * 256.0D;
        this.originZ = rand.nextDouble() * 256.0D;

        for(int j = 0; j < 256; ++j) {
            this.permutations[j] = (byte)j;
        }

        for(int index = 0; index < 256; ++index) {
            int randomIndex = rand.nextInt(256 - index)+index;
            byte temp = this.permutations[index];
            this.permutations[index] = this.permutations[randomIndex];
            this.permutations[randomIndex] = temp;
        }
    }

    protected int lookup(int hash) {
        return this.permutations[hash & 0xFF] & 0xFF; // force byte to behave correctly and not wrapping up
    }
}
