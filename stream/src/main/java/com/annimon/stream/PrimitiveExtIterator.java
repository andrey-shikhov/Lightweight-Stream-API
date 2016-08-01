package com.annimon.stream;

import java.util.NoSuchElementException;

/**
 * Extended PrimitiveIterator for common purposes.
 */
public final class PrimitiveExtIterator {

    private PrimitiveExtIterator() { }

    public static abstract class OfInt extends PrimitiveIterator.OfInt {

        protected int next;
        protected boolean hasNext, isInit;

        @Override
        public boolean hasNext() {
            if (!isInit) {
                nextIteration();
                isInit = true;
            }
            return hasNext;
        }

        @Override
        public int nextInt() {
            if (!hasNext) {
                throw new NoSuchElementException();
            }
            final int result = next;
            nextIteration();
            return result;
        }

        protected abstract void nextIteration();
    }

}
