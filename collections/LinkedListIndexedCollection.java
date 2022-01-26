package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * This class represents a model of linked list indexed collection.
 * 
 * @author Anamarija
 *
 */
public class LinkedListIndexedCollection<T> implements List<T> {

	/**
	 * This class represents a node in the linked list with pointers to next and
	 * previous node and additional reference for value storage.
	 * 
	 * @author Anamarija
	 *
	 */
	private static class ListNode<T> {
		private ListNode<T> next;
		private ListNode<T> previous;
		private T data;

	}

	private int size = 0;
	private ListNode<T> first;
	private ListNode<T> last;
	private long modificationCount = 0;

	/**
	 * Deafault constructor that creates an empty collection with first=last=null.
	 */
	public LinkedListIndexedCollection() {
		this.first = this.last = null;
	}

	/**
	 * Constructor that creates a new collection and copies all elements from the
	 * given collection.
	 * 
	 * @param other is a reference to another collection whose elements are copied
	 *              into this collection
	 */
	public LinkedListIndexedCollection(Collection<? extends T> other) {
		this.addAll(other);
	}

	/**
	 * Adds the given object into this collection at the end of collection.
	 * 
	 * @param value of the object that is added in this collection
	 * @throws NullPointerException is the given object is null
	 */
	@Override
	public void add(T value) {
		if (value == null)
			throw new NullPointerException("The given value can't be null.");

		ListNode<T> novi = new ListNode<>();
		novi.data = value;
		novi.next = null;
		novi.previous = last;

		if (last == null) {
			first = novi;
		} else {
			last.next = novi;
		}
		last = novi;
		size++;
		modificationCount++;
	}

	/**
	 * Removes all elements from the collection.
	 */
	@Override
	public void clear() {
		this.first = this.last = null;
		size = 0;
		modificationCount = 0;
	}

	/**
	 * Checks if the list contains a given value, as determined by equals method.
	 * 
	 * @param value of the object that is tested
	 * @return true only if the collection contains given value
	 */
	@Override
	public boolean contains(Object value) {
		ListNode<T> cvor = first;
		while (cvor != null) {
			if (cvor.data.equals(value))
				return true;
			cvor = cvor.next;
		}
		return false;
	}

	/**
	 * Returns the object that is stored in linked list at position index.
	 * 
	 * @param index of the object that is returned
	 * @return the element at the given position in this collection
	 * @throws IndexOutOfBoundsException if the index is out of range (index < 0 ||
	 *                                   index > size - 1)
	 */
	public T get(int index) {
		if (index < 0 || index > this.size - 1)
			throw new IndexOutOfBoundsException("Index is out of range (index < 0 || index > size - 1).");
		ListNode<T> cvor;
		if (index < size / 2) {
			cvor = first;
			while (index-- > 0)
				cvor = cvor.next;
		} else {
			cvor = last;
			while (++index < size)
				cvor = cvor.previous;
		}
		return cvor.data;
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
		if (!this.contains(value))
			return -1;
		int index = 0;
		ListNode<T> cvor = first;
		while (cvor != null) {
			if (value.equals(cvor.data))
				return index;
			index++;
			cvor = cvor.next;
		}
		return -1;
	}

	/**
	 * Inserts the given value at the given position in linked-list.
	 * 
	 * @param value    of the object to be inserted
	 * @param position at which the object should be inserted
	 * @throws NullPointerException      when the given value is null
	 * @throws IndexOutOfBoundsException if the position is out of range (position <
	 *                                   0 || position > size)
	 */
	public void insert(T value, int position) {
		if (value == null)
			throw new NullPointerException("The given value can+t be null.");
		if (position < 0 || position > this.size)
			throw new IndexOutOfBoundsException("Position is out of range (position <0 || position > size).");
		ListNode<T> novi = new ListNode<>();
		novi.data = value;

		if (position > size - 1) {
			this.add(value);
		} else {

			ListNode<T> desni = first;
			for (int i = 0; i < position; i++) {
				desni = desni.next;
			}
			novi.next = desni;
			novi.previous = desni.previous;
			if (desni.previous == null) {
				first = novi;
			} else {
				desni.previous.next = novi;
			}
			desni.previous = novi;
			size++;
			modificationCount++;
		}
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
		Object value = get(index);
		remove(value);
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
		if (size == 1) {
			first = last = null;
			size--;
			return true;
		} else {
			ListNode<T> cvor = first;
			while (cvor != null) {
				if (value.equals(cvor.data)) {

					if (cvor == first) {
						first = cvor.next;
						cvor.next.previous = null;

					} else if (cvor == last) {
						last = cvor.previous;
						cvor.previous.next = null;

					} else {
						cvor.next.previous = cvor.previous;
						cvor.previous.next = cvor.next;
					}
					size--;
					modificationCount++;
					return true;
				}
				cvor = cvor.next;
			}
		}
		return false;
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
	 * Allocates new array with size equals to the size of this collections and
	 * fills it with collection content.
	 * 
	 * @return the newly created array that contains all elements from this
	 *         collection
	 */
	@Override
	public Object[] toArray() {

		Object[] array = new Object[size];
		int i = 0;
		ListNode<T> cvor = first;
		while (cvor != null) {
			array[i] = cvor.data;
			i++;
			cvor = cvor.next;
		}
		return array;
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
	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	public <T> T[] toArray(T[] a) {
		if (a.length < size) {
			int i = 0;
			T[] result = a;
			for (ListNode<T> k = (ListNode<T>) first; k != null; k = k.next) {
				result[i++] = k.data;
			}
			return result;
		}
		if (a.length > size)
			a[size] = null;

		return a;
	}

	/**
	 * Creates the elements getter implemented specifically for
	 * LinkedListIndexedCollection.
	 *
	 * @return new elements getter
	 */
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ListEG(this);
	}

	/**
	 * This class represents an implementation of ElementsGetter for
	 * LinkedListIndexedCollection.
	 */
	private class ListEG implements ElementsGetter<T> {

		private ListNode<T> cvor;

		private long savedModificationCount;

		/**
		 * Constructor of ElementsGetter for a chosen LinkedListIndexedCollection.
		 *
		 * @param col is an LinkedListIndexedCollection that the ElementsGetter is
		 *            working with.
		 */
		@SuppressWarnings("unchecked")
		public ListEG(LinkedListIndexedCollection<? extends T> col) {
			cvor = (ListNode<T>) col.first;
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
			return cvor != null;
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
			T o = cvor.data;
			cvor = cvor.next;
			return o;
		}
	}

}
