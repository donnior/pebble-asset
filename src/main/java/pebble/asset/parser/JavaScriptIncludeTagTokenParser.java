package pebble.asset.parser;

import com.mitchellbosecke.pebble.node.expression.Expression;
import com.mitchellbosecke.pebble.node.expression.MapExpression;
import pebble.asset.RevAssetPathResolver;
import pebble.asset.node.AbstractAssetTagNode;
import pebble.asset.node.JavascriptAssetTagNode;

public class JavaScriptIncludeTagTokenParser extends AbstractAssetTagTokenParser {

    public JavaScriptIncludeTagTokenParser(RevAssetPathResolver resolver) {
        super(resolver);
    }

    @Override
    public String getTag() {
        return "javascriptInclude";
    }

    @Override
    AbstractAssetTagNode tagNode(int lineNumber, Expression<?> expression, MapExpression mapExpression, RevAssetPathResolver resolver) {
        return new JavascriptAssetTagNode(lineNumber, expression, mapExpression, resolver);
    }
}
