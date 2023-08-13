package it.italiandudes.dnd_visualizer.client.javafx.util;

import it.italiandudes.dnd_visualizer.client.javafx.Client;
import it.italiandudes.dnd_visualizer.client.javafx.JFXDefs;
import it.italiandudes.dnd_visualizer.utils.Defs;
import it.italiandudes.idl.common.Logger;
import javafx.scene.Scene;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class ThemeHandler {

    // Config Theme
    private static String configTheme = null;

    // Methods
    public static void setConfigTheme() {
        if (configTheme != null) return;
        if ((boolean) Client.getSettings().get("enableDarkMode")) {
            configTheme = Defs.Resources.get(JFXDefs.Resources.CSS.CSS_DARK_THEME).toExternalForm();
        } else {
            configTheme = Defs.Resources.get(JFXDefs.Resources.CSS.CSS_LIGHT_THEME).toExternalForm();
        }
        Logger.log(configTheme);
    }

    // Config Theme
    public static void loadConfigTheme(@NotNull final Scene scene) {
        scene.getStylesheets().add(configTheme);
    }

    // Light Theme
    public static void loadLightTheme(@NotNull final Scene scene) {
        scene.getStylesheets().add(Defs.Resources.get(JFXDefs.Resources.CSS.CSS_LIGHT_THEME).toExternalForm());
    }

    // Dark Theme
    public static void loadDarkTheme(@NotNull final Scene scene) {
        scene.getStylesheets().add(Defs.Resources.get(JFXDefs.Resources.CSS.CSS_DARK_THEME).toExternalForm());
    }

}
