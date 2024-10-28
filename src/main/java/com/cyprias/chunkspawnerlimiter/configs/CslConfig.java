package com.cyprias.chunkspawnerlimiter.configs;

import com.cyprias.chunkspawnerlimiter.ChunkSpawnerLimiter;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CslConfig extends ConfigFile<ChunkSpawnerLimiter> {

    private boolean metrics;
    /* Properties */
    private boolean debugMessages;
    private boolean checkChunkLoad;
    private boolean checkChunkUnload;
    private boolean activeInspections;
    private boolean watchCreatureSpawns;
    private boolean watchVehicleCreate;
    private int checkSurroundingChunks;
    private int inspectionFrequency;
    private boolean notifyPlayers;
    private boolean preserveNamedEntities;
    private boolean preserveRaidEntities;
    private List<String> ignoreMetadata;
    private boolean killInsteadOfRemove;

    /* Messages */
    private String removedEntities;
    private String reloadedConfig;

    private String maxAmountBlocks;
    private String maxAmountBlocksTitle;
    private String maxAmountBlocksSubtitle;

    public CslConfig(final @NotNull ChunkSpawnerLimiter plugin) {
        super(plugin, "", "config.yml", "");
        saveDefaultConfig();
    }

    @Override
    public void initValues() {
        String propertiesPath = "properties.";
        final ConfigurationSection propertiesSection = config.getConfigurationSection("properties");

        if (propertiesSection == null) {
            throw new NullPointerException("Your properties section is missing! Disabling plugin.");
        }

        this.debugMessages =  propertiesSection.getBoolean("debug-messages", false);
        this.checkChunkLoad = propertiesSection.getBoolean( "check-chunk-load", false);
        this.checkChunkUnload = propertiesSection.getBoolean( "check-chunk-unload", false);
        this.activeInspections = propertiesSection.getBoolean( "active-inspections", true);
        this.watchCreatureSpawns = propertiesSection.getBoolean( "watch-creature-spawns", true);
        this.watchVehicleCreate = propertiesSection.getBoolean( "watch-vehicle-create-event", true);
        this.checkSurroundingChunks = propertiesSection.getInt("check-surrounding-chunks", 1);
        this.inspectionFrequency = propertiesSection.getInt("inspection-frequency", 300);
        this.notifyPlayers = propertiesSection.getBoolean( "notify-players", false);
        this.preserveNamedEntities = propertiesSection.getBoolean( "preserve-named-entities", true);
        this.preserveRaidEntities = propertiesSection.getBoolean( "preserve-raid-entities", true);
        this.ignoreMetadata = propertiesSection.getStringList(propertiesPath + "ignore-metadata");
        this.killInsteadOfRemove = propertiesSection.getBoolean( "kill-instead-of-remove", false);

        String messagesPath = "messages.";
        this.removedEntities = config.getString(messagesPath + "removedEntities");
        this.reloadedConfig = config.getString(messagesPath + "reloadedConfig", "&cReloaded csl config.");
        this.maxAmountBlocks = config.getString(messagesPath + "maxAmountBlocks", "&6Cannot place more &4{material}&6. Max amount per chunk &2{amount}.");
        this.maxAmountBlocksTitle = config.getString(messagesPath + "maxAmountBlocksTitle", "&6Cannot place more &4{material}&6.");
        this.maxAmountBlocksSubtitle = config.getString(messagesPath + "maxAmountBlocksSubtitle", "&6Max amount per chunk &2{amount}.");
        this.metrics = config.getBoolean("metrics", true);
    }

    public boolean metrics() {
        return metrics;
    }

    public int getEntityLimit(String entityType) {
        return config.getInt("entities." + entityType);
    }

    public boolean isSpawnReason(String reason) {
        return config.getBoolean("spawn-reasons." + reason);
    }

    public boolean contains(String property) {
        return config.contains(property);
    }

    public ConfigurationSection getSpawnReasons() {
        return config.getConfigurationSection("spawn-reasons");
    }

    public ConfigurationSection getEntityLimits() {
        return config.getConfigurationSection("entities");
    }

    public boolean isWorldAllowed(final String worldName) {
        final List<String> worldNames = getWorldNames();
        if (getWorldsMode() == WorldsMode.EXCLUDED) {
            return !worldNames.contains(worldName);
        }
        // INCLUDED
        return worldNames.contains(worldName);
    }

    public boolean isWorldNotAllowed(final String worldName) {
        return !isWorldAllowed(worldName);
    }

    public List<String> getWorldNames() {
        return config.getStringList("worlds.worlds");
    }

    public WorldsMode getWorldsMode() {
        final String mode = config.getString("worlds.mode", "excluded");
        if (mode == null) {
            return WorldsMode.EXCLUDED;
        }

        return WorldsMode.valueOf(mode.toUpperCase());
    }


    public boolean isDebugMessages() {
        return debugMessages;
    }

    public boolean isCheckChunkLoad() {
        return checkChunkLoad;
    }

    public boolean isCheckChunkUnload() {
        return checkChunkUnload;
    }

    public boolean isActiveInspections() {
        return activeInspections;
    }

    public boolean isWatchCreatureSpawns() {
        return watchCreatureSpawns;
    }

    public boolean isWatchVehicleCreate() {
        return watchVehicleCreate;
    }

    public int getCheckSurroundingChunks() {
        return checkSurroundingChunks;
    }

    public int getInspectionFrequency() {
        return inspectionFrequency;
    }

    public boolean isNotifyPlayers() {
        return notifyPlayers;
    }

    public boolean isPreserveNamedEntities() {
        return preserveNamedEntities;
    }

    public boolean isPreserveRaidEntities() {
        return preserveRaidEntities;
    }

    public List<String> getIgnoreMetadata() {
        return ignoreMetadata;
    }

    public boolean isKillInsteadOfRemove() {
        return killInsteadOfRemove;
    }

    public String getRemovedEntities() {
        return removedEntities;
    }

    public String getReloadedConfig() {
        return reloadedConfig;
    }

    public String getMaxAmountBlocks() {
        return maxAmountBlocks;
    }

    public String getMaxAmountBlocksTitle() {
        return maxAmountBlocksTitle;
    }

    public String getMaxAmountBlocksSubtitle() {
        return maxAmountBlocksSubtitle;
    }

    public enum WorldsMode {
        INCLUDED,
        EXCLUDED
    }
}
