package org.embeddedt.protestversioning;

import net.fabricmc.api.ModInitializer;

import java.util.regex.Pattern;

public class Protestversioning implements ModInitializer {
    public static final Pattern RELEASE_VERSION = Pattern.compile("1\\.([0-9]+)(?:\\.([0-9]+))?");
    public static String ACTUAL_GAME_VERSION = "";

    @Override
    public void onInitialize() {

    }
}
