package pebble.asset.impl;

import java.util.HashMap;
import java.util.Map;

public class AssetConfig implements RevAssetPathResolver {

    private final String basePath;
    private final Map<String, String> assetRevMap;
    private final String assetsHost;

    public AssetConfig(String basePath, Map<String, String> assetRevMap) {
        this("", basePath, assetRevMap);
    }

    public AssetConfig(String assetsHost, String basePath, Map<String, String> revMap) {
        this.assetsHost = (assetsHost != null ? assetsHost : "");
        this.basePath = basePath;
        this.assetRevMap = (revMap != null ? revMap : new HashMap<>());
    }

    public AssetConfig(String basePath) {
        this(basePath, new HashMap<String, String>());
    }

    public Map<String, String> getAssetRevMap() {
        return assetRevMap;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getAssetsHost() {
        return assetsHost;
    }

    public String resolvePath(String assetName) {
        String path = path(assetName);
        return this.getAssetsHost() + "/" + this.getAssetRevMap().getOrDefault(path, path);
    }

    private String path(String resource) {
        if (this.getBasePath() == null || this.getBasePath().equals("")) {
            return resource;
        }
        return this.getBasePath() + "/" + resource;
    }
}
