package com.thealgorithms.datastructures.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A thread-safe queue implementation using a linked list with synchronized methods.
 * This implementation uses the synchronized keyword to ensure thread safety.
 *
 * @param <T> the type of elements held in this queue
 */
public final class ThreadSafeQueue<T> implements Iterable<T> {

    /**
     * Node class representing each element in the queue.
     */
    private static final class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> front;
    private Node<T> rear;
    private int size;

    /**
     * Initializes an empty ThreadSafeQueue.
     */
    public ThreadSafeQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue is empty, otherwise false
     */
    public synchronized boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the queue.
     *
     * @return the number of elements in the queue
     */
    public synchronized int size() {
        return size;
    }

    /**
     * Adds an element to the rear of the queue.
     *
     * @param data the element to insert
     * @throws IllegalArgumentException if data is null
     */
    public synchronized void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot enqueue null data");
        }

        Node<T> newNode = new Node<>(data);

        if (isEmpty()) {
            front = newNode;
        } else {
            rear.next = newNode;
        }
        rear = newNode;
        size++;
    }

    /**
     * Removes and returns the element at the front of the queue.
     *
     * @return the element at the front of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public synchronized T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        T retValue = front.data;
        front = front.next;
        size--;

        if (isEmpty()) {
            rear = null;
        }

        return retValue;
    }

    /**
     * Returns the element at the front of the queue without removing it.
     *
     * @return the element at the front of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public synchronized T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return front.data;
    }

    /**
     * Returns an iterator over the elements in the queue.
     *
     * @return an iterator over the elements in the queue
     */
    @Override
    public synchronized Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> current = front;

            @Override
            public synchronized boolean hasNext() {
                return current != null;
            }

            @Override
            public synchronized T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    /**
     * Clears all elements from the queue.
     */
    public synchronized void clear() {
        front = null;
        rear = null;
        size = 0;
    }

    /**
     * Returns a string representation of the queue.
     *
     * @return a string representation of the queue
     */
    @Override
    public synchronized String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        Node<T> current = front;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append(']');
        return sb.toString();
    }
}
