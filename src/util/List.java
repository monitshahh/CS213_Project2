package util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Stores objects in a generic array that grows by four when full.
 *
 * @param <E> type of object stored in the list
 * @author Monit Shah
 */
public class List<E> implements Iterable<E> {

    /** Initial array capacity and amount added when growing. */
    private static final int CAPACITY = 4;

    /** Value returned when an object is absent. */
    private static final int NOT_FOUND = -1;

    /** Array containing the stored objects. */
    private E[] objects;

    /** Number of objects currently stored. */
    private int size;

    /**
     * Creates an empty list with an initial capacity of four.
     */
    public List() {
        objects = (E[]) new Object[CAPACITY];
        size = 0;
    }

    /**
     * Finds the first occurrence of the specified object.
     *
     * @param e object to find
     * @return first matching index, or NOT_FOUND if absent
     */
    private int find(E e) {
        for (int index = 0; index < size; index++) {
            if (objects[index] == null) {
                if (e == null) {
                    return index;
                }
            } else if (objects[index].equals(e)) {
                return index;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Increases the array capacity by four.
     */
    private void grow() {
        E[] expandedObjects =
                (E[]) new Object[objects.length + CAPACITY];

        for (int index = 0; index < size; index++) {
            expandedObjects[index] = objects[index];
        }

        objects = expandedObjects;
    }

    /**
     * Determines whether the specified object is present.
     *
     * @param e object to find
     * @return true if the object is present; false otherwise
     */
    public boolean contains(E e) {
        return find(e) != NOT_FOUND;
    }

    /**
     * Appends the specified object to the end of the list.
     *
     * @param e object to add
     */
    public void add(E e) {
        if (size == objects.length) {
            grow();
        }

        objects[size] = e;
        size++;
    }

    /**
     * Removes the first occurrence of the specified object.
     * Does nothing if the object is not there.
     *
     * @param e object to remove
     */
    public void remove(E e) {
        int objectIndex = find(e);

        if (objectIndex == NOT_FOUND) {
            return;
        }

        for (int index = objectIndex; index < size - 1; index++) {
            objects[index] = objects[index + 1];
        }

        size--;
        objects[size] = null;
    }

    /**
     * Determines whether the list contains no objects.
     *
     * @return true if the list is empty; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of objects stored in the list.
     *
     * @return number of stored objects
     */
    public int size() {
        return size;
    }

    /**
     * Creates an iterator for traversing the stored objects.
     *
     * @return iterator starting at the first object
     */
    @Override
    public Iterator<E> iterator() {
        return new ListIterator<E>();
    }

    /**
     * Returns the object at the specified index.
     *
     * @param index position of the object
     * @return object at the specified position
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Invalid index: " + index);
        }

        return objects[index];
    }

    /**
     * Replaces the object at the specified index.
     *
     * @param index position to replace
     * @param e replacement object
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Invalid index: " + index);
        }

        objects[index] = e;
    }

    /**
     * Returns the index of the specified object.
     *
     * @param e object to find
     * @return first matching index, or NOT_FOUND if absent
     */
    public int indexOf(E e) {
        return find(e);
    }

    /**
     * Traverses the objects stored in the enclosing list.
     *
     * @param <E> type of object returned by the iterator
     * @author Monit Shah
     */
    private class ListIterator<E> implements Iterator<E> {
        /** Index of the next object to return. */
        int current = 0;

        /**
         * Determines whether another object is available.
         *
         * @return true if another object is available
         */
        @Override
        public boolean hasNext() {
            return current < size;
        }

        /**
         * Returns the next object and advances the iterator.
         *
         * @return next object in the list
         * @throws NoSuchElementException if no objects remain
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException(
                        "No more objects in the list.");
            }

            E nextObject = (E) objects[current];
            current++;
            return nextObject;
        }
    }
}