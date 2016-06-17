package pebble.asset.func;

import com.mitchellbosecke.pebble.error.ParserException;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.lexer.Token;
import com.mitchellbosecke.pebble.lexer.TokenStream;
import com.mitchellbosecke.pebble.node.RenderableNode;
import com.mitchellbosecke.pebble.node.TextNode;
import com.mitchellbosecke.pebble.node.expression.Expression;
import com.mitchellbosecke.pebble.node.expression.MapExpression;
import com.mitchellbosecke.pebble.parser.Parser;
import com.mitchellbosecke.pebble.tokenParser.AbstractTokenParser;
import pebble.asset.impl.RevAssetPathResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JavaScriptIncludeTag extends AbstractAssetTag {

    public JavaScriptIncludeTag(RevAssetPathResolver resolver) {
        super(resolver);
    }

    @Override
    public String getTag() {
        return "javascriptInclude";
    }

    @Override
    public RenderableNode parse(Token token, Parser parser) throws ParserException {
        TokenStream stream = parser.getStream();
        int lineNumber = token.getLineNumber();

        stream.next();

        Expression<?> expression = parser.getExpressionParser().parseExpression();

        Token current = stream.current();
        MapExpression mapExpression = null;

        stream.expect(Token.Type.EXECUTE_END);

        Object value = null;
        try {
            value = expression.evaluate(null, null);
            return nodeForValues(value, lineNumber);
        } catch (PebbleException e) {
            e.printStackTrace();
        }
        return new TextNode("", lineNumber);
    }

    @Override
    public TextNode singleTextNodeForAsset(String assetPath, int lineNumber) {
        return new TextNode("<script src=\"" + assetPath  +"\"></script>\n", lineNumber);
    }

}
