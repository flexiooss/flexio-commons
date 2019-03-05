package io.flexio.commons.graylog.api.types;

import java.lang.Iterable;
import java.lang.Object;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Stream;

public interface ValueList<E> extends Iterable<E> {
  boolean contains(Object o);

  boolean containsAll(Collection<?> c);

  E get(int index);

  int size();

  int indexOf(Object o);

  boolean isEmpty();

  <T> T[] toArray(T[] a);

  Object[] toArray();

  Stream<E> stream();

  class Builder<E> {
    private final LinkedList<E> delegate = new LinkedList<>();

    public ValueList<E> build() {
      return new ValueListImpl<>(this.delegate);
    }

    public Builder with(E... elements) {
      if(elements != null) {this.delegate.addAll(Arrays.asList(elements));};
      return this;
    }

    public Builder with(Collection<E> elements) {
      if(elements != null) {this.delegate.addAll(elements);};
      return this;
    }
  }
}
