package pebble.staticrev;

import pebble.util.RevManifestFileParser;

import java.util.HashMap;
import java.util.Map;

public class AssetConfig {

    private final String basePath;
    private final Map<String, String> resourceMap;
    private final String assetsHost;

    public AssetConfig(String basePath, Map<String, String> resourceMap) {
        this("", basePath, resourceMap);
    }

    public AssetConfig(String assetsHost, String basePath, Map<String, String> resourceMap) {
        this.assetsHost = (assetsHost != null ? assetsHost : "");
        this.basePath = basePath;
        this.resourceMap = resourceMap;
    }

    public AssetConfig(String basePath, String manifestFile) {
        this(basePath, RevManifestFileParser.parse(manifestFile));
    }

    public AssetConfig(String basePath) {
        this(basePath, new HashMap<String, String>());
    }

    public Map<String, String> getResourceMap() {
        return resourceMap;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getAssetsHost() {
        return assetsHost;
    }
}
