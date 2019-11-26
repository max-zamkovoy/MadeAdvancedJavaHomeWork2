package ru.mail;

import java.util.*;
import java.util.stream.Collectors;

class MadeSimpleMap<K, V> implements SimpleMap<K, V> {

    private static final int DEFAULT_CAPACITY = 10;

    private Node<K, V>[] table;

    private static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> next;
        private Node<K, V> previous;

        Node(K key, V value, Node<K, V> next, Node<K, V> previous) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.previous = previous;
        }

        K getKey() {
            return key;
        }

        V getValue() {
            return value;
        }
    }

    public MadeSimpleMap() {
        this(DEFAULT_CAPACITY);
    }

    public MadeSimpleMap(int capacity) {
        table = new Node[capacity];
    }

    @Override
    public V put(K key, V value) {
        int hash = hash(key);
        Node<K, V> oldNode = findTargetOrLastNode(key);
        V oldValue = null;
        if (isSameKey(oldNode, key)) {
            oldValue = oldNode.value;
            oldNode.value = value;
        } else {
            Node<K, V> newNode = new Node<K, V>(key, value, null, oldNode);
            if (oldNode == null) {
                table[hash] = newNode;
            } else {
                oldNode.next = newNode;
            }
        }
        return oldValue;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = findTargetNode(key);
        return node != null ? node.value : null;
    }

    @Override
    public V remove(K key) {
        int hash = hash(key);
        Node<K, V> node = findTargetNode(key);
        if (node == null) {
            throw new NoSuchElementException();
        }
        Node<K, V> previous = node.previous;
        Node<K, V> next = node.next;
        if (previous == null) {
            table[hash] = next;
        } else {
            previous.next = next;
        }
        if (next != null) {
            next.previous = previous;
        }

        return node.value;
    }

    @Override
    public boolean contains(K key) {
        return keySet().contains(key);
    }

    @Override
    public int size() {
        return entries().size();
    }

    @Override
    public Set<K> keySet() {
        return entries().stream().map(Node::getKey).collect(Collectors.toSet());
    }

    @Override
    public Collection<V> values() {
        return entries().stream().map(Node::getValue).collect(Collectors.toList());
    }

    private Collection<Node<K, V>> entries() {
        Collection<Node<K, V>> entries = new ArrayList<>();
        for (Node<K, V> node : table) {
            while (node != null) {
                entries.add(node);
                node = node.next;
            }
        }
        return entries;
    }

    private Node<K, V> findTargetNode(K key) {
        Node<K, V> current = findTargetOrLastNode(key);
        return isSameKey(current, key) ? current : null;
    }

    private Node<K, V> findTargetOrLastNode(K key) {
        int hash = hash(key);
        Node<K, V> current = table[hash];
        if (current != null) {
            while (!isSameKey(current, key) && current.next != null) {
                current = current.next;
            }
        }
        return current;
    }

    private boolean isSameKey(Node<K, V> node, K key) {
        return node != null && node.key.equals(key);
    }

    private int hash(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return Math.abs(key.hashCode()) % table.length;
    }
}