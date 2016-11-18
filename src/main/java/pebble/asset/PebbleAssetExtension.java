package pebble.asset;

import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.tokenParser.TokenParser;
import pebble.asset.parser.CSSIncludeTagTokenParser;
import pebble.asset.parser.ImageIncludeTagTokenParser;
import pebble.asset.parser.JavaScriptIncludeTagTokenParser;
import pebble.asset.func.AssetFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PebbleAssetExtension extends AbstractExtension {

    private Map<String, Function> functions = new HashMap<>();
    private List<TokenParser> tokenParsers = new ArrayList<>();

    public PebbleAssetExtension(AssetConfig config) {
        functions.put("asset", new AssetFunction(config));

        tokenParsers.add(new JavaScriptIncludeTagTokenParser(config));
        tokenParsers.add(new CSSIncludeTagTokenParser(config));
        tokenParsers.add(new ImageIncludeTagTokenParser(config));
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
