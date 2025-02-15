package net.seasonal.kinetic;

import net.fabricmc.api.ClientModInitializer;
import net.seasonal.kinetic.Kinetic;

public class KineticClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Kinetic.LOGGER.info("KineticClient.java Initialized!");
    }
}
