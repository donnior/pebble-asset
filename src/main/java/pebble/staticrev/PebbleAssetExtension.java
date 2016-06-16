package pebble.staticrev;

import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.tokenParser.TokenParser;
import pebble.ext.CSSTagInclude;
import pebble.ext.ImageTagInclude;
import pebble.ext.JavaScriptTagInclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PebbleAssetExtension extends AbstractExtension {

    Map<String, Function> functions = new HashMap<>();
    List<TokenParser> tokenParsers = new ArrayList<>();

    public PebbleAssetExtension(AssetConfig config) {
        functions.put("requireAsset", new RequireAssetFunction(config));

        tokenParsers.add(new JavaScriptTagInclude());
        tokenParsers.add(new CSSTagInclude());
        tokenParsers.add(new ImageTagInclude());
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
