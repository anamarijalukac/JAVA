package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

// TODO: Auto-generated Javadoc
/**
 * This class represents a model of array indexed collection.
 *
 * @author Anamarija
 * @param <T> the generic type
 */
public class ArrayIndexedCollection<T> implements List<T> {

	/** The size. */
	private int size = 0;

	/** The capacity. */
	private int capacity = 0;

	/** The elements. */
	private T[] elements;

	/** The modification count. */
	private long modificationCount = 0;

	/** The Constant CONSTANTCAPACITY. */
	final static int CONSTANTCAPACITY = 16;

	/**
	 * Constructor that creates a collection of given size and preallocates the
	 * <code>elements</code> array of that size.
	 * 
	 * @param initialCapacity sets the capacity to the given value
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		this.capacity = initialCapacity;
		if (initialCapacity < 1)
			throw new IllegalArgumentException("The value of initialCapacity is < 1.");
		this.elements = (T[]) new Object[initialCapacity];
	}

	/**
	 * Deafault constructor that creates an empty collection with capacity set to
	 * CONSTANTCAPACITY.
	 */
	public ArrayIndexedCollection() {
		this(CONSTANTCAPACITY);
	}

	/**
	 * Constructor that preallocates the <code>elements</code> array of the given
	 * size and copies all elements from given collection into this newly
	 * constructed collecction.
	 * 
	 * @param other           is a reference to another collection whose elements
	 *                        are copied into this collection
	 * @param initialCapacity sets the capacity to the given value
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<? extends T> other, int initialCapacity) {
		if (initialCapacity < 1)
			throw new IllegalArgumentException("The value of initialCapacity is < 1.");
		if (other == null)
			throw new NullPointerException("The given collection other can't be null.");

		if (initialCapacity < other.size()) {
			this.elements = (T[]) new Object[other.size()];
			this.capacity = other.size();
		} else {
			this.elements = (T[]) new Object[initialCapacity];
			this.capacity = initialCapacity;
		}

		this.addAll(other);

		this.size = other.size();
	}

	/**
	 * Constructor that preallocates the <code>elements</code> array to size
	 * CONSTANTCAPACITY and copies all elements from given collection into this
	 * newly constructed collection.
	 * 
	 * @param other is a reference to another collection whose elements are copied
	 *              into this collection
	 */
	public ArrayIndexedCollection(Collection<? extends T> other) {
		this(other, CONSTANTCAPACITY);
	}

	/**
	 * Allocates new array with size equals to the size of this collections and
	 * fills it with collection content.
	 * 
	 * @return the newly created array that contains all elements from this
	 *         collection
	 */
	@Override
	public Object[] toArray() {

		Object[] array = new Object[this.size];

		for (int i = 0; i < this.size; i++) {
			array[i] = this.elements[i];
		}

		return array;
	}

	/**
	 * Returns the number of elements in this collection.
	 * 
	 * @return number of elements in this collection
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Adds the given object into this collection at the end of collection.
	 * 
	 * @param value of the object that is added in this collection
	 * @throws NullPointerException is the given object is null
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void add(T value) {
		if (value == null)
			throw new NullPointerException("The given value can't be null.");
		if (this.size >= this.capacity) {
			T[] novi = (T[]) new Object[capacity * 2];
			for (int i = 0; i < this.size; i++) {
				novi[i] = this.elements[i];
			}
			this.elements = novi;
			this.capacity *= 2;

		}
		this.elements[this.size] = value;
		this.size++;
		modificationCount++;
	}

	/**
	 * Checks if the list contains a given value, as determined by equals method.
	 * 
	 * @param value of the object that is tested
	 * @return true only if the collection contains given value
	 */
	@Override
	public boolean contains(Object value) {
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value))
				return true;
		}
		return false;
	}

	/**
	 * Checks if the collection contains a given value and removes the first
	 * occurrence of it.
	 * 
	 * @param value of the object that should be removed
	 * @return true if the collection contains given value
	 */
	@Override
	public boolean remove(Object value) {
		if (this.contains(value)) {
			for (int j = 0; j < this.size; j++) {
				if (this.elements[j].equals(value)) {

					for (int i = j; i < this.size - 1; i++) {
						this.elements[i] = this.elements[i + 1];
					}
				}
			}
			this.size--;
			modificationCount++;
			return true;
		}
		return false;
	}

	/**
	 * Removes all elements from the collection.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		this.size = 0;
		modificationCount = 0;
	}

	/**
	 * Returns the object that is stored in backing array at position index. *
	 * 
	 * @param index of the object that is returned
	 * @return the element at the given position in this collection
	 * @throws IndexOutOfBoundsException if the index is out of range (index < 0 ||
	 *                                   index > size - 1)
	 */
	public T get(int index) {
		if (index < 0 || index > this.size - 1)
			throw new IndexOutOfBoundsException("Index is out of range (index < 0 || index > size - 1).");
		return this.elements[index];
	}

	/**
	 * Inserts the given value at the given position in array.
	 * 
	 * @param value    of the object to be inserted
	 * @param position at which the object should be inserted
	 * @throws NullPointerException      when the given value is null
	 * @throws IndexOutOfBoundsException if the position is out of range (position <
	 *                                   0 || position > size)
	 */
	@SuppressWarnings("unchecked")
	public void insert(T value, int position) {
		if (value == null)
			throw new NullPointerException("The given value can+t be null.");
		if (position < 0 || position > this.size)
			throw new IndexOutOfBoundsException("Position is out of range (position < 0 || position > size).");

		if (this.size >= this.capacity) {
			T[] novi = (T[]) new Object[capacity * 2];
			for (int i = 0; i < this.size; i++) {
				novi[i] = this.elements[i];
			}
			this.elements = novi;
			this.capacity *= 2;
		}

		T pom = (T) new Object();
		for (int i = this.size; i >= position; i--) {
			pom = this.elements[i];
			this.elements[i + 1] = this.elements[i];
			this.elements[i] = pom;

		}
		this.elements[position] = value;
		this.size++;
		modificationCount++;
	}

	/**
	 * Removes element at specified index from collection.
	 * 
	 * @param index of the object that is removes
	 * @throws IndexOutOfBoundsException if the index is out of range (index < 0 ||
	 *                                   index > size - 1)
	 */
	public void remove(int index) {
		if (index < 0 || index > this.size - 1)
			throw new IndexOutOfBoundsException("Index is out of range (index <0 || index > size-1).");
		Object e = get(index);
		remove(e);

	}

	/**
	 * Searches the collection and returns the index of the first occurrence of the
	 * given value.
	 * 
	 * @param value of the object that is searched for
	 * @return the index of the first occurrence of the given value or -1 if the
	 *         <code>value</code> is not found
	 */
	public int indexOf(Object value) {
		if (value == null)
			return -1;
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Allocates new parameterized array with size equals to the size of this
	 * collections and fills it with collection content.
	 *
	 * @param <T> the generic type
	 * @param a   the a
	 * @return the newly created generic array that contains all elements from this
	 *         collection
	 */
	@SuppressWarnings({ "hiding", "unchecked" })
	@Override
	public <T> T[] toArray(T[] a) {

		T[] array = (T[]) new Object[size];
		for (int i = 0; i < size; i++) {
			array[i] = a[i];
		}

		return array;
	}

	/**
	 * Creates the elements getter implemented specifically for
	 * ArrayIndexedCollection.
	 *
	 * @return new elements getter
	 */
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ArrayEG(this);
	}

	/**
	 * This class represents an implementation of ElementsGetter for
	 * ArrayIndexedCollection.
	 */
	private class ArrayEG implements ElementsGetter<T> {

		private T[] polje;
		private int cnt = 0;
		private final long savedModificationCount;

		/**
		 * Constructor of ElementsGetter for a chosen ArrayIndexedCollection.
		 *
		 * @param col is an ArrayIndexedCollection that the ElementsGetter is working
		 *            with.
		 */
		public ArrayEG(ArrayIndexedCollection<? extends T> col) {
			polje = (T[]) col.elements;
			savedModificationCount = col.modificationCount;
		}

		/**
		 * Check modification.
		 */
		private void checkModification() {
			if (modificationCount != savedModificationCount)
				throw new ConcurrentModificationException();

		}

		/**
		 * Checks if next elements exists.
		 *
		 * @return true, if successful
		 */
		@Override
		public boolean hasNextElement() {
			checkModification();
			if (polje[cnt] != null)
				return true;
			return false;
		}

		/**
		 * Gets the next element.
		 *
		 * @return the next element
		 */
		@Override
		public T getNextElement() {
			checkModification();
			if (!hasNextElement())
				throw new NoSuchElementException("Nema vise elemenata!");
			return polje[cnt++];
		}

	}

}
