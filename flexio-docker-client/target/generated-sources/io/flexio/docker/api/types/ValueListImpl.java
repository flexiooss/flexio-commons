package io.flexio.docker.api.types;

import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

class ValueListImpl<E> extends ArrayList<E> implements ValueList<E> {
  @SuppressWarnings("unchecked")
  public ValueListImpl(E... elements) {
    super(Arrays.asList(elements));
  }

  @SuppressWarnings("unchecked")
  public ValueListImpl(Collection<E> elements) {
    super(elements);
  }

  public Stream<E> stream() {
    return super.stream();
  }
}
