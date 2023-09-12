package it.italiandudes.dnd_visualizer.utils;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import org.jetbrains.annotations.Nullable;

public final class DiscordRichPresenceManager {

    // Classes
    public static final class States {
        public static final String MENU = "Nel Menu";
        public static final String IN_GAME = "In Gioco";
    }

    // Attributes
    public static final String APPLICATION_ID = "1151163095352344617";
    public static final String APPLICATION_IMAGE = "dnd_visualizer";
    private static DiscordRichPresence presence = null;
    private static String characterName = null;
    private static String level = null;

    // Rich Presence Initializer
    private static void initializeRichPresence() {
        DiscordRPC lib = DiscordRPC.INSTANCE;
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        lib.Discord_Initialize(APPLICATION_ID, handlers, true, null);
        presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.largeImageKey = APPLICATION_IMAGE;
        lib.Discord_UpdatePresence(presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    //noinspection BusyWait
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }, "RPC-Callback-Handler").start();
    }
    public static void updateRichPresenceState(@Nullable final String state) {
        if (presence == null) initializeRichPresence();
        presence.state = state;
        DiscordRPC.INSTANCE.Discord_UpdatePresence(presence);
    }
    public static void updateRichPresenceDetails() {
        if (presence == null) initializeRichPresence();
        StringBuilder detailsBuilder = new StringBuilder();
        if (characterName == null || characterName.replace(" ", "").isEmpty()) characterName = null;
        if (characterName != null) detailsBuilder.append(characterName);
        if (level == null || level.replace(" ", "").isEmpty()) level = null;
        if (characterName != null && level != null) detailsBuilder.append(" - ").append(level);
        presence.details = detailsBuilder.toString();
        DiscordRPC.INSTANCE.Discord_UpdatePresence(presence);
    }
    public static void setCharacterName(@Nullable final String characterName) {
        DiscordRichPresenceManager.characterName = characterName;
        updateRichPresenceDetails();
    }
    public static void setLevel(@Nullable final String level) {
        DiscordRichPresenceManager.level = level;
        updateRichPresenceDetails();
    }
    public static void shutdownRichPresence() {
        DiscordRPC.INSTANCE.Discord_Shutdown();
    }
}
