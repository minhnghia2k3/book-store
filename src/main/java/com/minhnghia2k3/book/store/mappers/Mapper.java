package com.minhnghia2k3.book.store.mappers;

public interface Mapper<A, B> {
    B toMapper(A a);
    A fromMapper(B b);
}
