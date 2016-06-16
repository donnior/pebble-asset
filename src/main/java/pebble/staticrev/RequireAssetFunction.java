package pebble.staticrev;

import com.mitchellbosecke.pebble.extension.Function;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RequireAssetFunction implements Function {

    private final Map resourceMap;
    private final String basePath;
    private final String assetsHost;

    public RequireAssetFunction(AssetConfig config) {
        this.assetsHost = config.getAssetsHost();
        this.basePath = config.getBasePath();
        this.resourceMap = config.getResourceMap();
    }

    @Override
    public Object execute(Map<String, Object> map) {
        String resourceName = map.get("name").toString();
        String path = path(resourceName);
        return this.assetsHost + "/" + this.resourceMap.getOrDefault(path, path);
    }

    @Override
    public List<String> getArgumentNames() {
        return Arrays.asList("name");
    }

    private String path(String resource) {
        return this.basePath + "/" + resource;
    }

}
