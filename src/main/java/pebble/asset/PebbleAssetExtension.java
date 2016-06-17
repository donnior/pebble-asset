package pebble.asset;

import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.tokenParser.TokenParser;
import pebble.asset.func.CSSIncludeTag;
import pebble.asset.func.ImageIncludeTag;
import pebble.asset.func.JavaScriptIncludeTag;
import pebble.asset.func.RequireAssetFunction;
import pebble.asset.impl.AssetConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PebbleAssetExtension extends AbstractExtension {

    private Map<String, Function> functions = new HashMap<>();
    private List<TokenParser> tokenParsers = new ArrayList<>();

    public PebbleAssetExtension(AssetConfig config) {
        functions.put("requireAsset", new RequireAssetFunction(config));

        tokenParsers.add(new JavaScriptIncludeTag(config));
        tokenParsers.add(new CSSIncludeTag(config));
        tokenParsers.add(new ImageIncludeTag(config));
    }

    @Override
    public Map<String, Function> getFunctions() {
        return functions;
    }

    @Override
    public List<TokenParser> getTokenParsers() {
        return this.tokenParsers;
    }
}
