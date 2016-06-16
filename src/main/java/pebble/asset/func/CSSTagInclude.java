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

public class CSSTagInclude extends AbstractTokenParser {
    @Override
    public String getTag() {
        return "cssInclude";
    }

    @Override
    public RenderableNode parse(Token token, Parser parser) throws ParserException {
        TokenStream stream = parser.getStream();
        int lineNumber = token.getLineNumber();

        stream.next();

        Expression<?> expression = parser.getExpressionParser().parseExpression();
//        stream.next();

        Token current = stream.current();
        MapExpression mapExpression = null;

        stream.expect(Token.Type.EXECUTE_END);

        String value = null;
        try {
            value = expression.evaluate(null, null).toString();
        } catch (PebbleException e) {
            e.printStackTrace();
        }

        return new TextNode("<link href=\"" + value  +"\" rel=\"stylesheet\"/>", lineNumber);
    }
}
