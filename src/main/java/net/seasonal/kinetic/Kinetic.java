package net.seasonal.kinetic;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Kinetic implements ModInitializer {
	public static final String MOD_ID = "kinetic";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Kinetic.java Initialized!");
	}
}
