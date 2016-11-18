package pebble.asset.parser;

import com.mitchellbosecke.pebble.error.ParserException;
import com.mitchellbosecke.pebble.lexer.Token;
import com.mitchellbosecke.pebble.lexer.TokenStream;
import com.mitchellbosecke.pebble.node.RenderableNode;
import com.mitchellbosecke.pebble.node.expression.Expression;
import com.mitchellbosecke.pebble.node.expression.MapExpression;
import com.mitchellbosecke.pebble.parser.Parser;
import com.mitchellbosecke.pebble.tokenParser.AbstractTokenParser;
import pebble.asset.RevAssetPathResolver;
import pebble.asset.node.AbstractAssetTagNode;

public abstract class AbstractAssetTagTokenParser extends AbstractTokenParser {

    private RevAssetPathResolver resolver;

    public AbstractAssetTagTokenParser(){}

    public AbstractAssetTagTokenParser(RevAssetPathResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public RenderableNode parse(Token token, Parser parser) throws ParserException {
        TokenStream stream = parser.getStream();
        int lineNumber = token.getLineNumber();

        stream.next();   //skip over the 'javascriptInclude' token

        Expression<?> expression = parser.getExpressionParser().parseExpression();

        Token current = stream.current();
        MapExpression mapExpression = null;

        if (current.getType().equals(Token.Type.NAME) && current.getValue().equals("props")) {

            // Skip over 'props'
            stream.next();

            Expression<?> parsedExpression = parser.getExpressionParser().parseExpression();

            if (parsedExpression instanceof MapExpression) {
                mapExpression = (MapExpression) parsedExpression;
            } else {
                throw new ParserException(null, String.format("Unexpected expression '%1s'.", parsedExpression
                        .getClass().getCanonicalName()), token.getLineNumber(), stream.getFilename());
            }

        }


        stream.expect(Token.Type.EXECUTE_END);

        return tagNode(lineNumber, expression, mapExpression, this.resolver);
    }

    protected String assertPath(String assertName) {
        return this.resolver.resolvePath(assertName);
    }

    abstract AbstractAssetTagNode tagNode(int lineNumber, Expression<?> expression, MapExpression mapExpression,
                                          RevAssetPathResolver resolver);

}
