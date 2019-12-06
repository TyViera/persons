package pe.ty.persons.expose.web.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
public class ResponseLoggingWebFilter implements WebFilter {

  private static final MediaTypeFilter DEFAULT_FILTER = new MediaTypeFilter() {
  };
  private static final LogMessageFormatter DEFAULT_LOG_MESSAGE_FORMATTER = new LoggingServerHttpResponseDecorator.DefaultLogMessageFormatter();

  @Getter
  @Setter
  private MediaTypeFilter mediaTypeFilter;

  @Getter
  @Setter
  private LogMessageFormatter responseMessageFormatter;

  private final Logger logger;

  public ResponseLoggingWebFilter() {
    this(log);
  }

  public ResponseLoggingWebFilter(Logger logger) {
    this(logger, DEFAULT_FILTER);
  }

  public ResponseLoggingWebFilter(Logger logger, MediaTypeFilter mediaTypeFilter) {
    this.logger = logger;
    this.mediaTypeFilter = mediaTypeFilter;
    this.responseMessageFormatter = DEFAULT_LOG_MESSAGE_FORMATTER;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    return chain.filter(decorate(exchange));
  }

  private ServerWebExchange decorate(ServerWebExchange exchange) {

    final ServerHttpResponse decoratedResponse = new LoggingServerHttpResponseDecorator(
        exchange.getResponse(),
        exchange.getRequest(),
        logger,
        mediaTypeFilter,
        new LoggingServerHttpResponseDecorator.DefaultLogMessageFormatter()
    );

    return new ServerWebExchangeDecorator(exchange) {

      @Override
      public ServerHttpResponse getResponse() {
        return decoratedResponse;
      }

    };
  }

}