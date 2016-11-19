package pebble.asset.node;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.node.expression.Expression;
import com.mitchellbosecke.pebble.node.expression.MapExpression;
import pebble.asset.RevAssetPathResolver;

import java.io.IOException;
import java.io.Writer;

public class JavascriptAssetTagNode extends AbstractAssetTagNode {

    private static String[] minimizedAttributes = new String[]{"async"};

    public JavascriptAssetTagNode(int lineNumber, Expression<?> sourcesExpression, MapExpression mapExpression, RevAssetPathResolver resolver) {
        super(lineNumber, sourcesExpression, mapExpression, resolver);
    }


    @Override
    public String tagName() {
        return "script";
    }
}
