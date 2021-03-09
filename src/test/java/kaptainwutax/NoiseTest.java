package kaptainwutax;

import kaptainwutax.noiseutils.perlin.OctavePerlinNoiseSampler;
import kaptainwutax.seedutils.mc.ChunkRand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoiseTest {
    @Test
    @DisplayName("Test Perlin noise")
    public void testHeight() {
        ChunkRand rand=new ChunkRand();
        OctavePerlinNoiseSampler perlinNoiseSampler = new OctavePerlinNoiseSampler(rand, 16);
        assertEquals(perlinNoiseSampler.sample(0,0,0),-1830.633256896282);
    }
}
