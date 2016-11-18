package pebble.asset.node;

import com.mitchellbosecke.pebble.node.expression.Expression;
import com.mitchellbosecke.pebble.node.expression.MapExpression;
import pebble.asset.RevAssetPathResolver;

public class CssAssetTageNode extends AbstractAssetTagNode {
    public CssAssetTageNode(int lineNumber, Expression<?> sourcesExpression, MapExpression mapExpression, RevAssetPathResolver resolver) {
        super(lineNumber, sourcesExpression, mapExpression, resolver);
    }

    @Override
    public String tagName() {
        return "link";
    }
}
