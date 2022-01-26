package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class represents a scattered table of addressing that allows the storage
 * of ordered pairs
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * 
	 * Examples of this class have a member variable key in which they remember the
	 * passed key, the member variable value in which they remember the associated
	 * value, and the member variable next which points to the next instance of the
	 * TableEntry class <K, V> located in the same table slot
	 *
	 * @param <K> the key type
	 * @param <V> the value type
	 */
	public static class TableEntry<K, V> {

		private K key;
		private V value;
		TableEntry<K, V> next;

		/**
		 * Gets the key.
		 *
		 * @return the key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets the value.
		 *
		 * @param value the new value
		 */
		public void setValue(V value) {
			this.value = value;
		}

	}

	private TableEntry<K, V>[] table;
	private int size = 0;
	private long modificationCount = 0;
	final static int CONSTANTCAPACITY = 16;

	/**
	 * Instantiates a new simple hashtable with a constant capacity.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {

		table = new TableEntry[CONSTANTCAPACITY];

	}

	/**
	 * Instantiates a new simple hashtable with a given capacity.
	 *
	 * @param capacity the capacity
	 * @throws IllegalArgumentException if (capacity < 1)
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException();
		while (!(capacity != 0 && ((capacity & (capacity - 1)) == 0))) {
			capacity++;
		}
		table = new TableEntry[capacity];

	}

	/**
	 * Puts the TableEntry of key and value to the end of the list for the assigned
	 * slot. If the key already exists, it replaces the old value with the given new
	 * value.
	 *
	 * @param key   the key
	 * @param value the value
	 * @return the old value if the key already exists or null if it is newly added
	 * @throws NullPointerException if the value of the key is null
	 */
	public V put(K key, V value) {
		if (key == null)
			throw new NullPointerException();

		if (checkFill()) {
			rehash();
			modificationCount++;
		}

		int index = getHash(key);
		TableEntry<K, V> item = new TableEntry<K, V>();
		item.key = key;
		item.value = value;
		item.next = null;

		if (table[index] == null) {
			table[index] = item;
			size++;
			modificationCount++;

		} else {
			TableEntry<K, V> curr = table[index];

			while (curr != null) {
				if (curr.key.equals(key)) {
					curr.setValue(value);
					return value;
				}
				if (curr.next == null) {
					curr.next = item;
					size++;
					modificationCount++;
				}
				curr = curr.next;
			}
		}
		return null;
	}

	/**
	 * Gets the value for the given key.
	 *
	 * @param key the key
	 * @return the value for the given key or null if the key doesn't exists in the
	 *         table
	 */
	public V get(Object key) {
		int index = getHash(key);
		TableEntry<K, V> curr = table[index];
		while (curr != null) {
			if (curr.key.equals(key))
				return curr.getValue();
			curr = curr.next;
		}
		return null;
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Contains key.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean containsKey(Object key) {
		if (key == null)
			throw new NullPointerException();
		int index = getHash(key);
		TableEntry<K, V> curr = table[index];
		while (curr != null) {
			if (curr.key.equals(key))
				return true;
			curr = curr.next;
		}
		return false;
	}

	/**
	 * Contains value.
	 *
	 * @param value the value
	 * @return true, if successful
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> curr = table[i];
			while (curr != null) {
				if (curr.value.equals(value))
					return true;
				curr = curr.next;
			}
		}
		return false;
	}

	/**
	 * Removes the TableEntry with for a given key from the table, if one exists,
	 * and returns it to the caller.
	 *
	 * @param key the key
	 * @return the value for the given key if it exists
	 */
	public V remove(Object key) {
		if (!this.containsKey(key))
			return null;
		int index = getHash(key);
		if (table[index] == null) {
			return null;
		} else {
			TableEntry<K, V> curr = table[index];
			TableEntry<K, V> before = null;
			while (curr != null) {
				if (curr.key.equals(key)) {
					if (before != null) {
						before.next = curr.next;
					} else {
						table[index] = curr.next;
					}
					size--;
					modificationCount++;
					return curr.value;

				}
				before = curr;
				curr = curr.next;
			}
		}
		return null;

	}

	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		if (size == 0)
			return "";
		String str = "[";
		for (int i = 0; i < table.length; i++) {
			str += "polje: " + i + "\n";
			TableEntry<K, V> curr = table[i];
			while (curr != null) {
				str += curr.getKey() + "=" + curr.getValue() + ", ";
				curr = curr.next;
			}
		}
		str = str.substring(0, str.length() - 2);
		str += "]";
		return str;

	}

	/**
	 * To array.
	 *
	 * @return the table entry[]
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K, V>[] toArray() {
		TableEntry<K, V>[] a = new TableEntry[this.size()];
		int index = 0;
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> curr = table[i];
			while (curr != null) {
				a[index++] = curr;
				curr = curr.next;
			}
		}
		return a;
	}

	/**
	 * Clear.
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++)
			table[i] = null;
		size = 0;
		modificationCount++;
	}

	/**
	 * Return the index of the slot for the given key.
	 *
	 * @param key the key
	 * @return the index of the slot
	 */
	private int getHash(Object key) {
		int hash = Math.abs(key.hashCode());
		int index = hash % table.length;
		return index;
	}

	/**
	 * Check the table occupancy as the ratio of the content of the variable size
	 * and the size of the internal field of references.
	 *
	 * @return true, if table occupancy is greater or equal from 75% of the number
	 *         of slots
	 */
	private boolean checkFill() {
		if ((size / table.length) >= (0.75 * table.length))
			return true;
		return false;

	}

	/**
	 * Create a new table with double its size.
	 *
	 * @return the v
	 */
	@SuppressWarnings("unchecked")
	private V rehash() {
		TableEntry<K, V>[] pom = this.toArray();
		table = new TableEntry[table.length * 2];
		for (TableEntry<K, V> el : pom) {
			if (el == null)
				break;
			int index = getHash(el.key);
			if (table[index] == null) {
				table[index] = el;

			} else {
				TableEntry<K, V> curr = table[index];

				while (curr != null) {
					if (curr.key.equals(el.key)) {
						curr.setValue(el.value);
						return el.value;
					}
					if (curr.next == null) {
						curr.next = el;

					}
					curr = curr.next;
				}
			}
		}
		return null;
	}

	/**
	 * Iterator.
	 *
	 * @return the iterator
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl(this);
	}

	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		TableEntry<K, V> curr, returned;
		int numberOfElem = 0;
		int index = 0;
		private long savedModificationCount;
		boolean returnFlag = true;

		/**
		 * Instantiates a new iterator for the SimpleHashtable.
		 *
		 * @param hashtable is a reference to SimpleHashtable
		 */
		public IteratorImpl(SimpleHashtable<K, V> hashtable) {
			curr = table[0];
			savedModificationCount = hashtable.modificationCount;
		}

		/**
		 * Check modification.
		 */
		private void checkModification() {
			if (modificationCount != savedModificationCount)
				throw new ConcurrentModificationException();

		}

		/**
		 * Checks if the next TableEntry exists
		 *
		 * @return true, if the next TableEntry exists
		 */
		public boolean hasNext() {
			checkModification();
			return numberOfElem < size;
		}

		/**
		 * Return the next TableEntry form the hashtable.
		 *
		 * @return the next TableEntry
		 */
		public SimpleHashtable.TableEntry<K, V> next() {
			checkModification();
			if (numberOfElem >= size)
				throw new NoSuchElementException();
			numberOfElem++;
			curr = returned;
			while (curr == null) {
				if (index >= table.length)
					return null;
				curr = table[index++];
			}
			returned = curr.next;
			return curr;

		}

		/**
		 * Removes the TableEntry that was last returned.
		 */
		public void remove() {
			if (!returnFlag)
				throw new IllegalStateException();
			checkModification();
			savedModificationCount++;
			SimpleHashtable.this.remove(curr.key);
			returnFlag = false;
		}
	}

}
