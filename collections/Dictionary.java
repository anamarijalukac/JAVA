package hr.fer.oprpp1.custom.collections;

/**
 * This class represents a kind of map which for ecery given key, remembers its
 * given value.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class Dictionary<K, V> {

	/**
	 * This class represents an arranged pair of key and value.
	 */
	class Pair {
		private K key;
		private V value;

		/**
		 * Instantiates a new pair.
		 *
		 * @param key   the key
		 * @param value the value
		 * @throws NullPointerException when the value of key is null
		 */
		public Pair(K key, V value) {
			if (key == null)
				throw new NullPointerException();
			this.key = key;
			this.value = value;
		}

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

	ArrayIndexedCollection<Pair> a = new ArrayIndexedCollection<>();

	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return a.isEmpty();
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return a.size();

	}

	/**
	 * Clear.
	 */
	public void clear() {
		a.clear();
	}

	/**
	 * Puts new value for a given in the dictionary. If the given key already
	 * exists, it replaces the old value with new value.
	 *
	 * @param key   the key
	 * @param value the value
	 * @return old value for the given key and null if the old value doesn't exists
	 *         in the dictionary
	 */
	public V put(K key, V value) {
		Pair pair = new Pair(key, value);
		ElementsGetter<Pair> getter = a.createElementsGetter();
		while (getter.hasNextElement()) {
			Pair old = getter.getNextElement();
			if (old.getKey().equals(key)) {
				V oldvalue = old.getValue();
				old.setValue(value);
				return oldvalue;
			}
		}
		a.add(pair);
		return null;

	}

	/**
	 * Gets the value for the given key.
	 *
	 * @param key the key
	 * @return the value of the given key or null if the key doesn't exists in the
	 *         dictionary
	 */
	public V get(Object key) {
		ElementsGetter<Pair> getter = a.createElementsGetter();
		while (getter.hasNextElement()) {
			Pair pair = getter.getNextElement();
			if (pair.getKey().equals(key)) {
				return pair.getValue();
			}
		}
		return null;

	}

	/**
	 * Removes the pair of the given key.
	 *
	 * @param key the key
	 * @return the value of the removed pair or null if the pair doesn't exists in
	 *         the dictionary
	 */
	public V remove(K key) {
		ElementsGetter<Pair> getter = a.createElementsGetter();
		while (getter.hasNextElement()) {
			Pair old = getter.getNextElement();
			if (old.getKey().equals(key)) {
				a.remove(old);
				return old.getValue();
			}
		}
		return null;

	}

}
