package teamport.moonmod.world;

import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.provider.BiomeProvider;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.type.WorldTypeOverworld;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.wind.WindManagerGeneric;
import teamport.moonmod.block.MMBlocks;

public class WorldTypeMoon extends WorldTypeOverworld implements ISpace {

	public WorldTypeMoon(String languageKey) {
		super(languageKey, Weather.overworldClear, new WindManagerGeneric(), SeasonConfig.builder()
			.withSingleSeason(Seasons.NULL)
			.build());
	}

	@Override
	public int getMinY() {
		return 0;
	}

	@Override
	public int getMaxY() {
		return 127;
	}

	@Override
	public int getOceanY() {
		return 0;
	}

	@Override
	public BiomeProvider createBiomeProvider(World world) {
		return new BiomeProviderMoon(world.getRandomSeed(), this);
	}

	@Override
	public float getCloudHeight() {
		return -64;
	}

	@Override
	public ChunkGenerator createChunkGenerator(World world) {
		return new ChunkGeneratorMoon(world);
	}

	@Override
	public boolean hasAurora() {
		return false;
	}

	@Override
	public boolean isValidSpawn(World world, int x, int y, int z) {
		return world.getBlockId(x, y -1, z) == MMBlocks.regolith.id;
	}

	@Override
	public boolean mayRespawn() {
		return false;
	}

	@Override
	public int getDayNightCycleLengthTicks() {
		return 24000;
	}

	@Override
	public float[] getSunriseColor(float timeOfDay, float partialTick) {
		return new float[] {0.0f, 0.0f, 0.0f, 0.0f};
	}

	@Override
	public int getSkyDarken(World world, long tick, float partialTick) {
		int subtracted;
		float f1 = this.getCelestialAngle(world, tick, partialTick);
		float f2 = 1.0f - (MathHelper.cos(f1 * 3.141593f * 2.0f) * 2.0f + 0.5f);
		if (f2 < 0.0f) {
			f2 = 0.0f;
		}
		if (f2 > 1.0f) {
			f2 = 1.0f;
		}
		float weatherOffset = 0.0f;
		if (world.getCurrentWeather() != null) {
			weatherOffset = (float)world.getCurrentWeather().subtractLightLevel * world.weatherManager.getWeatherIntensity() * world.weatherManager.getWeatherPower();
		}
		if ((subtracted = (int)(f2 * (11.0f - weatherOffset) + weatherOffset)) > 8) {
			subtracted = 12;
		}
		return subtracted;
	}

	@Override
	public float getCelestialAngle(World world, long tick, float partialTick) {
		return 0.5F;
	}


	@Override
	public Vec3d getFogColor(World world, double x, double y, double z, float celestialAngle, float partialTick) {
	    float timeOfDay = MathHelper.clamp(MathHelper.cos(celestialAngle * 3.1415927F * 2.0F) * 2.0F + 0.5F, 0.0F, 1.0F);
		int i = 0;
		float f2 = MathHelper.cos(timeOfDay * 3.141593F * 2.0F) * 2.0F + 0.5F;
		if(f2 < 0.0F)
		{
			f2 = 0.0F;
		}
		if(f2 > 1.0F)
		{
			f2 = 1.0F;
		}
		float f3 = (float)(i >> 16 & 0xff) / 255F;
		float f4 = (float)(i >> 8 & 0xff) / 255F;
		float f5 = (float)(i & 0xff) / 255F;
		f3 *= f2 * 0.94F + 0.06F;
		f4 *= f2 * 0.94F + 0.06F;
		f5 *= f2 * 0.91F + 0.09F;
		return Vec3d.createVector(f3, f4, f5);
	}

	@Override
	public float getGravityScalar() {
		return 0.22F;
	}

	@Override
	public boolean suffocate() {
		return true;
	}

	@Override
	public String getCelestialMoonTexture() {
		return "/terrain/earth.png";
	}

	@Override
	public String getCelestialSunTexture() {
		return "/terrain/sun.png";
	}

	@Override
	public float getStarBrightness(World world) {
		return 1.0F;
	}
}
