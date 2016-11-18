package pebble.asset.func;

import com.mitchellbosecke.pebble.extension.Function;
import pebble.asset.RevAssetPathResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AssetFunction implements Function {

    private final RevAssetPathResolver resolver;

    public AssetFunction(RevAssetPathResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public Object execute(Map<String, Object> map) {
        String resourceName = map.get("name").toString();
        return this.resolver.resolvePath(resourceName);
    }

    @Override
    public List<String> getArgumentNames() {
        return Arrays.asList("name");
    }

}
