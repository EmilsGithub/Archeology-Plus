package net.emilsg.archeologyplus.config;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class APConfig {
    public static final String FILE_VERSION = "2.1.0";
    public static final String DO_CERTAIN_DEATH = "do_certain_death";
    public static final String CERTAIN_DEATH_DURATION = "certain_death_duration";
    public static final String DO_CERTAIN_DEATH_PARTICLES = "do_certain_death_particles";
    public static final String SPIKES_POISON_IF_DIFFICULTY_HARD = "spikes_poison_if_difficulty_hard";
    private static final Logger LOGGER = LoggerFactory.getLogger(APConfig.class);
    private static final String fileName = ArcheologyPlus.MOD_ID + ".properties";
    private static APConfig instance;
    private final Map<String, ConfigEntry<?>> configs;
    private final File configFile;
    public APConfig(String configFilePath) {
        this.configFile = new File(configFilePath);
        this.configs = new HashMap<>();
        initializeConfigs();
        loadOrCreateConfig();
    }

    public static void init() {
        LOGGER.info("Initializing Clutter Config.");
    }

    public static APConfig getInstance() {
        if (instance == null) {
            instance = new APConfig(FabricLoader.getInstance().getConfigDir().resolve(fileName).toString());
        }
        return instance;
    }

    public void initializeConfigs() {
        configs.put(DO_CERTAIN_DEATH, new ConfigEntry<>(true, "Apply Certain Death when entering applicable Structures."));
        configs.put(CERTAIN_DEATH_DURATION, new ConfigEntry<>(120, "The duration of Certain Death when applied by entering applicable Structures."));
        configs.put(DO_CERTAIN_DEATH_PARTICLES, new ConfigEntry<>(true, "Spawn Ash Particles instead of Potion Swirls when Entities are affected by Certain Death."));

        configs.put(SPIKES_POISON_IF_DIFFICULTY_HARD, new ConfigEntry<>(true, "Spikes apply Poison if the World's Difficulty is set to Hard."));

    }

    public boolean isConfigVersionCurrent() {
        if (getCurrentFileVersion() != null) {
            return getCurrentFileVersion().equals("# File Version: " + FILE_VERSION);
        }
        return false;
    }

    public String getCurrentFileVersion() {
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("# File Version:")) {
                    return line;
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error reading config file for version check.", e);
        }
        return null;
    }

    public String getFileVersionNumber(String versionLine) {
        return versionLine.replace("# File Version: ", "");
    }

    private void loadOrCreateConfig() {
        if (configFile.exists()) {
            loadConfig();
        } else {
            LOGGER.info("Clutter config file is missing, generating default one.");
            createDefaultConfig();
        }
    }

    private void loadConfig() {
        Properties props = new Properties();

        try (FileReader reader = new FileReader(configFile)) {
            props.load(reader);

            for (Map.Entry<String, ConfigEntry<?>> entry : configs.entrySet()) {
                String key = entry.getKey();
                ConfigEntry<?> configEntry = entry.getValue();

                if (props.containsKey(key)) {

                    updateConfigEntry(key, configEntry, props.getProperty(key));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMissingConfigsAndUpdateVersion() {
        loadConfig();

        StringBuilder newFileContent = new StringBuilder();
        boolean versionMismatch = true;
        boolean changesMade = false;

        try (FileReader reader = new FileReader(configFile)) {
            try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.startsWith("# File Version:")) {
                        if (line.equals("# File Version: " + FILE_VERSION)) {
                            versionMismatch = false;
                        } else {
                            line = "# File Version: " + FILE_VERSION;
                            changesMade = true;
                        }
                    }
                    newFileContent.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            LOGGER.error("Failed to read existing configuration for version check.", e);
        }

        for (Map.Entry<String, ConfigEntry<?>> entry : configs.entrySet()) {
            if (!newFileContent.toString().contains(entry.getKey() + "=")) {
                ConfigEntry<?> configEntry = entry.getValue();
                String key = entry.getKey();
                String value = configEntry.getValue().toString();
                String comment = configEntry.getComment();

                if (comment != null && !comment.isEmpty()) {
                    newFileContent.append("\n# ").append(comment).append(" Default value: ").append(value);
                }
                newFileContent.append("\n").append(key).append("=").append(value).append("\n");
                changesMade = true;
            }
        }

        if (changesMade) {
            try (FileWriter writer = new FileWriter(configFile, false)) {
                writer.write(newFileContent.toString());
                LOGGER.info("Configuration file updated with missing entries and/or version update.");
            } catch (IOException e) {
                LOGGER.error("Failed to update configuration file.", e);
            }
        } else {
            LOGGER.info("Configuration file up-to-date. No changes made.");
        }
    }


    @SuppressWarnings("unchecked")
    private <T> void updateConfigEntry(String key, ConfigEntry<T> configEntry, String value) {
        T convertedValue = (T) convertStringToType(key, value, configEntry.getValue().getClass(), configEntry);
        configEntry.setValue(convertedValue);
    }

    private <T> Object convertStringToType(String key, String value, Class<?> type, ConfigEntry<T> configEntry) {
        try {
            if (Boolean.class.isAssignableFrom(type)) {
                return type.cast(Boolean.parseBoolean(value));
            }
            if (Integer.class.isAssignableFrom(type)) {
                return type.cast(Integer.parseInt(value));
            }
            if (Float.class.isAssignableFrom(type)) {
                return type.cast(Float.parseFloat(value));
            }
            if (Double.class.isAssignableFrom(type)) {
                return type.cast(Double.parseDouble(value));
            }
        } catch (Exception e) {
            LOGGER.warn("Error converting config value for key '" + key + "' with value '" + value + "'. Please check your" + fileName + " file.");
            return configEntry.getDefaultValue();
        }
        throw new IllegalArgumentException("Unsupported type for configuration key '" + key + "': " + type);
    }

    public void createDefaultConfig() {
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write("# File Version: " + APConfig.FILE_VERSION + "\n\n");

            for (Map.Entry<String, ConfigEntry<?>> entry : configs.entrySet()) {
                String key = entry.getKey();
                ConfigEntry<?> configEntry = entry.getValue();
                String value = configEntry.getValue().toString();
                String comment = configEntry.getComment();

                if (comment != null && !comment.isEmpty()) {
                    writer.write("# " + comment + " Default value: " + value + "\n");
                }
                writer.write(key + "=" + value + "\n\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T> void resetConfigEntryValue(ConfigEntry<T> configEntry) {
        configEntry.setValue(configEntry.getDefaultValue());
    }

    public void resetConfig() {
        for (Map.Entry<String, ConfigEntry<?>> entry : configs.entrySet()) {
            resetConfigEntryValue(entry.getValue());
        }
        createDefaultConfig();
        LOGGER.info("Configuration reset to default values.");
    }

    @SuppressWarnings("unchecked")
    public <T> T getOrDefault(String key, T defaultValue) {
        ConfigEntry<?> entry = configs.get(key);
        if (entry == null) {
            return defaultValue;
        }

        try {
            return (T) entry.getValue();
        } catch (ClassCastException e) {
            LOGGER.warn("Incorrect type for config key '" + key + "': Expected " + defaultValue.getClass().getSimpleName() + ", found " + entry.getValue().getClass().getSimpleName());
            return defaultValue;
        }
    }

    private static class ConfigEntry<T> {
        private final T defaultValue;
        private final String comment;
        private T value;

        public ConfigEntry(T defaultValue, String comment) {
            this.defaultValue = defaultValue;
            this.comment = comment;
            this.value = defaultValue;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public T getDefaultValue() {
            return defaultValue;
        }

        public String getComment() {
            return comment;
        }
    }
}
