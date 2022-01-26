package hr.fer.oprpp1.custom.collections;

/**
 * The interface ElementsGetter is a type of iterator for the given collection.
 *
 * @param <T> the generic type
 */
public interface ElementsGetter<T> {

	public boolean hasNextElement();

	public T getNextElement();

	default void processRemaining(Processor<? super T> p) {
		while (hasNextElement())
			p.process(getNextElement());

	}

}
