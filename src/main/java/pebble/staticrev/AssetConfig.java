package pebble.staticrev;

import pebble.util.RevManifestFileParser;

import java.util.HashMap;
import java.util.Map;

public class AssetConfig {

    private final String basePath;
    private final Map<String, String> assetRevMap;
    private final String assetsHost;

    public AssetConfig(String basePath, Map<String, String> assetRevMap) {
        this("", basePath, assetRevMap);
    }

    public AssetConfig(String assetsHost, String basePath, Map<String, String> revMap) {
        this.assetsHost = (assetsHost != null ? assetsHost : "");
        this.basePath = basePath;
        this.assetRevMap = revMap;
    }

    public AssetConfig(String basePath, String manifestFile) {
        this(basePath, RevManifestFileParser.parse(manifestFile));
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
}
