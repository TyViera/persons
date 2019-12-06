package pe.ty.persons.expose.web.filter;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageLengthController {

  @PostMapping(value = "/", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Integer anyData(@RequestBody String body) {
    return body.length();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MaxPayloadSizeFilter.TooLongPayloadException.class)
  public void handleMaxPayloadSizeError() {
  }

}