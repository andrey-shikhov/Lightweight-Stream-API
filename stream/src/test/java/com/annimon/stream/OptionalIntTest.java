package com.annimon.stream;

import com.annimon.stream.function.Supplier;
import com.annimon.stream.function.IntConsumer;
import com.annimon.stream.function.IntSupplier;
import static com.annimon.stream.test.hamcrest.OptionalIntMatcher.isEmpty;
import static com.annimon.stream.test.hamcrest.OptionalIntMatcher.isPresent;
import org.junit.Test;

import java.util.NoSuchElementException;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link OptionalInt}
 */
public class OptionalIntTest {

    @Test
    public void testGetWithPresentValue() {
        int value = OptionalInt.of(10).getAsInt();
        assertEquals(10, value);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetOnEmptyOptional() {
        OptionalInt.empty().getAsInt();
    }

    @Test
    public void testIsPresent() {
        assertThat(OptionalInt.of(10), isPresent());
    }

    @Test
    public void testIsPresentOnEmptyOptional() {
        assertThat(OptionalInt.empty(), isEmpty());
    }

    @Test
    public void testIfPresent() {
        OptionalInt.empty().ifPresent(new IntConsumer() {
            @Override
            public void accept(int value) {
                throw new IllegalStateException();
            }
        });

        OptionalInt.of(15).ifPresent(new IntConsumer() {
            @Override
            public void accept(int value) {
                assertEquals(15, value);
            }
        });
    }

    @Test
    public void testStream() {
        long count = OptionalInt.of(10).stream().count();
        assertThat(count, is(1L));
    }

    @Test
    public void testStreamOnEmptyOptional() {
        long count = OptionalInt.empty().stream().count();
        assertThat(count, is(0L));
    }

    @Test
    public void testOr() {
        int value = OptionalInt.of(42).or(new Supplier<OptionalInt>() {
            @Override
            public OptionalInt get() {
                return OptionalInt.of(19);
            }
        }).getAsInt();
        assertEquals(42, value);
    }

    @Test
    public void testOrOnEmptyOptional() {
        int value = OptionalInt.empty().or(new Supplier<OptionalInt>() {
            @Override
            public OptionalInt get() {
                return OptionalInt.of(19);
            }
        }).getAsInt();
        assertEquals(19, value);
    }

    @Test
    public void testOrOnEmptyOptionalAndEmptySupplierOptional() {
        final OptionalInt optional = OptionalInt.empty().or(new Supplier<OptionalInt>() {
            @Override
            public OptionalInt get() {
                return OptionalInt.empty();
            }
        });
        assertThat(optional, isEmpty());
    }

    @Test
    public void testOrElse() {
        assertEquals(17, OptionalInt.empty().orElse(17));
        assertEquals(17, OptionalInt.of(17).orElse(0));
    }

    @Test
    public void testOrElseGet() {
        assertEquals(21, OptionalInt.empty().orElseGet(new IntSupplier() {
            @Override
            public int getAsInt() {
                return 21;
            }
        }));

        assertEquals(21, OptionalInt.of(21).orElseGet(new IntSupplier() {
            @Override
            public int getAsInt() {
                throw new IllegalStateException();
            }
        }));
    }

    @Test
    public void testOrElseThrow() {
        try {
            assertEquals(25, OptionalInt.of(25).orElseThrow(new Supplier<NoSuchElementException>() {
                @Override
                public NoSuchElementException get() {
                    throw new IllegalStateException();
                }
            }));
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testOrElseThrow2() {
        assertEquals(25, OptionalInt.empty().orElseThrow(new Supplier<NoSuchElementException>() {
            @Override
            public NoSuchElementException get() {
                return new NoSuchElementException();
            }
        }));
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEquals() {
        assertEquals(OptionalInt.empty(), OptionalInt.empty());
        assertFalse(OptionalInt.empty().equals(Optional.empty()));

        assertEquals(OptionalInt.of(42), OptionalInt.of(42));

        assertFalse(OptionalInt.of(41).equals(OptionalInt.of(42)));
        assertFalse(OptionalInt.of(0).equals(OptionalInt.empty()));
    }

    @Test
    public void testHashCode() {
        assertEquals(OptionalInt.empty().hashCode(), 0);
        assertEquals(31, OptionalInt.of(31).hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(OptionalInt.empty().toString(), "OptionalInt.empty");
        assertEquals(OptionalInt.of(42).toString(), "OptionalInt[42]");
    }

}
