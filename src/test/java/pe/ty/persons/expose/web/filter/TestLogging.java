package pe.ty.persons.expose.web.filter;

import ch.qos.logback.classic.Level;
import java.util.Arrays;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.codec.StringDecoder;
import org.springframework.http.MediaType;
import org.springframework.http.codec.DecoderHttpMessageReader;
import org.springframework.http.codec.EncoderHttpMessageWriter;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.reactive.accept.FixedContentTypeResolver;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.handler.FilteringWebHandler;

@Slf4j
public class TestLogging {

  private static final String TEST_STRING = "TEST";
  private static final String EXPECTED_LOG_RESULT_REQUEST = "[POST] 'http://127.0.0.1:9999/?name=xyz' from null";
  private static final String EXPECTED_LOG_RESULT_REQUEST_DEBUG =
      "[POST] 'http://127.0.0.1:9999/?name=xyz' from null\n" +
          "WebTestClient-Request-Id=[1]\n" +
          "Content-Type=[text/plain;charset=UTF-8]\n" +
          "Content-Length=[4]\n" +
          "[\nTEST\n]";

  private static final String EXPECTED_LOG_RESULT_RESPONSE = "Response [200 OK] for [POST] 'http://127.0.0.1:9999/?name=xyz' from null";
  private static final String EXPECTED_LOG_RESULT_RESPONSE_DEBUG =
      "Response [200 OK] for [POST] 'http://127.0.0.1:9999/?name=xyz' from null\n" +
          "Content-Type=[application/json;charset=UTF-8]\n" +
          "Content-Length=[1]\n" +
          "[\n4\n]";

  @Test
  public void testDebug() throws Exception {

    Logger log = Mockito.mock(Logger.class);
    Mockito.when(log.isDebugEnabled()).thenReturn(Boolean.TRUE);
    Mockito.when(log.isInfoEnabled()).thenReturn(Boolean.TRUE);

    DispatcherHandler dispatcherHandler = buildWebHandler();
    final WebTestClient testClient = WebTestClient.bindToWebHandler(new FilteringWebHandler(
        dispatcherHandler,
        Arrays.asList(new RequestLoggingWebFilter(log), new ResponseLoggingWebFilter(log))
    )).configureClient().baseUrl("http://127.0.0.1:9999/").build();

    testClient.post().uri("/?name=xyz")
        .body(BodyInserters.fromObject(TEST_STRING))
        .exchange()
        .expectStatus().isOk()
        .expectBody().json(String.valueOf(TEST_STRING.length()));

    Mockito.verify(log, Mockito.atLeastOnce()).debug(EXPECTED_LOG_RESULT_REQUEST_DEBUG);
    Mockito.verify(log, Mockito.times(1)).debug(EXPECTED_LOG_RESULT_RESPONSE_DEBUG);

  }

  @Before
  public void setUp() throws Exception {
    ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory
        .getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
    rootLogger.setLevel(Level.ALL);
  }

  @Test
  public void testInfo() throws Exception {
    Logger log = Mockito.mock(Logger.class);
    Mockito.when(log.isDebugEnabled()).thenReturn(Boolean.FALSE);
    Mockito.when(log.isInfoEnabled()).thenReturn(Boolean.TRUE);

    DispatcherHandler dispatcherHandler = buildWebHandler();
    final WebTestClient testClient = WebTestClient.bindToWebHandler(new FilteringWebHandler(
        dispatcherHandler,
        Arrays.asList(new RequestLoggingWebFilter(log), new ResponseLoggingWebFilter(log))
    )).configureClient().baseUrl("http://127.0.0.1:9999/")
        .build();

    testClient.post().uri("/?name=xyz")
        .body(BodyInserters.fromObject(TEST_STRING))
        .exchange()
        .expectStatus().isOk()
        .expectBody().json(String.valueOf(TEST_STRING.length()));

    Mockito.verify(log, Mockito.atLeastOnce()).info(EXPECTED_LOG_RESULT_REQUEST);
    Mockito.verify(log, Mockito.times(1)).info(EXPECTED_LOG_RESULT_RESPONSE);

  }

  private DispatcherHandler buildWebHandler() throws Exception {
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

    // handler mapping
    RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
    beanFactory.registerSingleton("handlerMapping", handlerMapping);

    // handler adapter
    RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
    handlerAdapter.setMessageReaders(
        Collections.singletonList(
            new DecoderHttpMessageReader<>(
                StringDecoder.allMimeTypes(false)
            )
        )
    );
    beanFactory.registerSingleton("handlerAdapter", handlerAdapter);

    // HandlerResultHandler
    ResponseBodyResultHandler responseResultHandler = new ResponseBodyResultHandler(
        Collections.singletonList(
            new EncoderHttpMessageWriter<>(new Jackson2JsonEncoder())
        ),
        new FixedContentTypeResolver(MediaType.APPLICATION_JSON_UTF8)
    );

    beanFactory.registerSingleton("responseHandler", responseResultHandler);

    // controller
    beanFactory.registerSingleton("testHandler", new MessageLengthController());

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        beanFactory);

    applicationContext.refresh();

    handlerAdapter.setApplicationContext(applicationContext);
    handlerAdapter.afterPropertiesSet();

    handlerMapping.setApplicationContext(applicationContext);
    handlerMapping.afterPropertiesSet();

    return new DispatcherHandler(applicationContext);
  }

}
