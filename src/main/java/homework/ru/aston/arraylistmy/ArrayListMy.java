package homework.ru.aston.arraylistmy;

import java.util.Arrays;
import java.util.Collection;

public class ArrayListMy<E> {
	private static final int INIT_ARRAY_SIZE = 10;

	private Object[] array = new Object[INIT_ARRAY_SIZE];
	private int size = 0;

	public void add(E element) {
		if (size >= array.length) {
			increaseArrayLength(2 * array.length);
		}
		array[size] = element;
		++size;
	}

	public void add(int index, E element) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
		}
		if (size >= array.length) {
			increaseArrayLength(2 * array.length);
		}
		if (index < size) {
			System.arraycopy(array, index, array, index + 1, size - index);
		}
		array[index] = element;
		++size;
	}

	public void addAll(Collection<? extends E> c) {
		if (c == null || c.isEmpty()) {
			return;
		}
		if (size + c.size() > array.length) {
			int newSize = Math.max(2 * array.length, size + c.size());
			increaseArrayLength(newSize);
		}
		for (E element : c) {
			array[size] = element;
			++size;
		}
	}

	public void clear() {
		array = new Object[INIT_ARRAY_SIZE];
		size = 0;
	}

	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
		}
		return (E) array[index];
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
		}
		if (index < size - 1) {
			System.arraycopy(array, index + 1, array, index, size - index - 1);
		}
		array[size - 1] = null;
		--size;
	}

	public void remove(Object obj) {
		if (obj == null) {
			for (int i = 0; i < size; ++i) {
				if (array[i] == null) {
					remove(i);
					break;
				}
			}
		} else {
			for (int i = 0; i < size; ++i) {
				if (obj.equals(array[i])) {
					remove(i);
					break;
				}
			}
		}
	}

	public int size() {
		return size;
	}

	public void mergeSort() {
		if (size <= 1) {
			return;
		}
		Comparable<E>[] array2 = new Comparable[size];
		for (int i = 0; i < size; ++i) {
			array2[i] = (Comparable<E>) array[i];
		}
		array = mergeSortMy(array2);
	}

	private Comparable<E>[] mergeSortMy(Comparable<E>[] arr) {
		if (arr.length <= 1) {
			return arr;
		}
		Comparable<E>[] array1 = new Comparable[arr.length / 2];
		Comparable<E>[] array2 = new Comparable[arr.length - array1.length];
		System.arraycopy(arr, 0, array1, 0, array1.length);
		System.arraycopy(arr, array1.length, array2, 0, array2.length);
		return mergeArrays(mergeSortMy(array1), mergeSortMy(array2));
	}

	private Comparable<E>[] mergeArrays(Comparable<E>[] array1, Comparable<E>[] array2) {
		Comparable<E>[] result = new Comparable[array1.length + array2.length];
		int index1 = 0;
		int index2 = 0;
		int index = 0;
		while (true) {
			if (index1 >= array1.length) {
				System.arraycopy(array2, index2, result, index, array2.length - index2);
				break;
			}
			if (index2 >= array2.length) {
				System.arraycopy(array1, index1, result, index, array1.length - index1);
				break;
			}
			Comparable v1 = array1[index1];
			Comparable v2 = array2[index2];
			if (v1.compareTo(v2) < 0) {
				result[index] = v1;
				++index1;
			} else {
				result[index] = v2;
				++index2;
			}
			++index;
		}
		return result;
	}

	private void increaseArrayLength(int newSize) {
		Object[] array2 = new Object[newSize];
		System.arraycopy(array, 0, array2, 0, size);
		array = array2;
	}

	@Override
	public String toString() {
		return "size=" + size + " array=" + Arrays.toString(array);
	}
}
