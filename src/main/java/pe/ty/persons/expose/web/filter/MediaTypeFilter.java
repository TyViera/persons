package pe.ty.persons.expose.web.filter;

import org.springframework.http.MediaType;

public interface MediaTypeFilter {

  default boolean logged(MediaType mediaType) {
    return true;
  }

}
