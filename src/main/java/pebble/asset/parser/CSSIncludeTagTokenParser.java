package pebble.asset.parser;

import com.mitchellbosecke.pebble.node.expression.Expression;
import com.mitchellbosecke.pebble.node.expression.MapExpression;
import pebble.asset.RevAssetPathResolver;
import pebble.asset.node.AbstractAssetTagNode;
import pebble.asset.node.CssAssetTageNode;

public class CSSIncludeTagTokenParser extends AbstractAssetTagTokenParser {

    public CSSIncludeTagTokenParser(RevAssetPathResolver resolver) {
        super(resolver);
    }

    @Override
    public String getTag() {
        return "cssInclude";
    }

    @Override
    AbstractAssetTagNode tagNode(int lineNumber, Expression<?> expression, MapExpression mapExpression, RevAssetPathResolver resolver) {
        return new CssAssetTageNode(lineNumber, expression, mapExpression, resolver);
    }
}
