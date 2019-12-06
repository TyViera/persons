package pe.ty.persons.expose.web.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
public class RequestLoggingWebFilter implements WebFilter {

  private static final MediaTypeFilter DEFAULT_FILTER = new MediaTypeFilter() {
  };

  private static final LogMessageFormatter DEFAULT_LOG_MESSAGE_FORMATTER = new LoggingServerHttpRequestDecorator.DefaultLogMessageFormatter();

  @Getter
  @Setter
  private MediaTypeFilter mediaTypeFilter;

  @Getter
  @Setter
  private LogMessageFormatter logMessageFormatter;

  private final Logger logger;

  public RequestLoggingWebFilter() {
    this(log);
  }

  public RequestLoggingWebFilter(Logger logger) {
    this(logger, DEFAULT_FILTER);
  }

  public RequestLoggingWebFilter(Logger logger, MediaTypeFilter mediaTypeFilter) {
    this.logger = logger;
    this.mediaTypeFilter = mediaTypeFilter;
    this.logMessageFormatter = DEFAULT_LOG_MESSAGE_FORMATTER;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    return chain.filter(decorate(exchange));
  }

  private ServerWebExchange decorate(ServerWebExchange exchange) {

    final ServerHttpRequest decoratedRequest = new LoggingServerHttpRequestDecorator(
        exchange.getRequest(),
        exchange.getResponse(),
        logger,
        mediaTypeFilter,
        new LoggingServerHttpRequestDecorator.DefaultLogMessageFormatter()
    );

    return new ServerWebExchangeDecorator(exchange) {

      @Override
      public ServerHttpRequest getRequest() {
        return decoratedRequest;
      }

    };
  }

}
