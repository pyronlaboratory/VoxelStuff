package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

/**
 * provides several methods for manipulating arrays of data, including buffer creation
 * and flipping, array removal, and type casting. It also includes a method for
 * creating a matrix from a float buffer.
 */
public class Util {
	
 /**
  * creates a new `FloatBuffer` object with the specified size.
  * 
  * @param size number of elements to be stored in the `FloatBuffer`.
  * 
  * @returns a `FloatBuffer` object of size `size`.
  * 
  * The output is a FloatBuffer object that represents a contiguous block of floating-point
  * data in memory.
  * The buffer has a specified size, which can be any positive integer value.
  * The buffer's capacity is equal to its size, and it can hold at most this number
  * of floats without reallocating memory.
  * The buffer's position is 0 by default, indicating that the first float in the
  * buffer is at position 0.
  */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

 /**
  * creates an `IntBuffer` object with the specified size using `BufferUtils`.
  * 
  * @param size number of elements to be stored in the IntBuffer.
  * 
  * @returns an IntBuffer object representing a contiguous block of integers with the
  * specified size.
  * 
  * The `IntBuffer` object created has a size property that represents the number of
  * elements in the buffer.
  * A capacity property that represents the maximum amount of memory that can be used
  * by the buffer.
  * Accessibility flags that indicate whether the buffer is read-only, write-only, or
  * readable and writable.
  * The buffer's position, which indicates where the next element can be found in the
  * buffer.
  */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

 /**
  * creates a new `ByteBuffer` instance with a specified size. It uses the `BufferUtils`
  * class to create the buffer, ensuring efficient memory management.
  * 
  * @param size maximum capacity of the ByteBuffer to be created, which determines the
  * amount of memory allocated for storage.
  * 
  * @returns a ByteBuffer object of the specified size.
  * 
  * The `createByteBuffer` function returns a `ByteBuffer` instance, which is a
  * lightweight, direct-access view of a portion of memory.
  * 
  * The `ByteBuffer` instance has an initial capacity determined by the parameter
  * `size`, and can be resized using the `put` method.
  * 
  * The `ByteBuffer` instance can be accessed and manipulated using various methods,
  * such as `get`, `getInt`, `getLong`, and `put`.
  */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

 /**
  * creates an `IntBuffer` instance from a list of integers, puts them into the buffer,
  * and flips it for efficient random access.
  * 
  * @returns an flipped `IntBuffer` containing the input `int` values.
  * 
  * 	- The buffer is an `IntBuffer`, indicating that it stores integer values.
  * 	- It is created by taking the input array `values` and creating an `IntBuffer`
  * from it using the `createIntBuffer` method.
  * 	- The `put` method is called on the buffer to write the input values to it, in
  * the order they were provided in the input array.
  * 	- After writing the values, the `flip` method is called on the buffer to flip it,
  * making its contents available for reading as integer values.
  */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
 /**
  * creates a new FloatBuffer object filled with the provided float values, then flips
  * the buffer to create a mirrored copy of the original data.
  * 
  * @returns a flipped FloatBuffer containing the input values.
  * 
  * 	- The `FloatBuffer` object returned is created with the specified length from the
  * input `float... values`.
  * 	- The `put` method is called on the buffer to store the input values.
  * 	- The `flip()` method is called after storing the values to flip the buffer's
  * position, effectively reversing its orientation for subsequent access.
  */
	public static FloatBuffer createFlippedBuffer(float... values) {
		FloatBuffer buffer = createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}

	/*
	public static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
		FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);

		for (int i = 0; i < vertices.length; i++) {
			buffer.put(vertices[i].getPos().getX());
			buffer.put(vertices[i].getPos().getY());
			buffer.put(vertices[i].getPos().getZ());
			buffer.put(vertices[i].getTexCoord().getX());
			buffer.put(vertices[i].getTexCoord().getY());
			buffer.put(vertices[i].getNormal().getX());
			buffer.put(vertices[i].getNormal().getY());
			buffer.put(vertices[i].getNormal().getZ());
			buffer.put(vertices[i].getTangent().getX());
			buffer.put(vertices[i].getTangent().getY());
			buffer.put(vertices[i].getTangent().getZ());
		}

		buffer.flip();

		return buffer;
	}
	*/

	/*
	public static FloatBuffer createFlippedBuffer(Matrix4f value) {
		FloatBuffer buffer = createFloatBuffer(4 * 4);

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				buffer.put(value.get(i, j));

		buffer.flip();

		return buffer;
	}
	
	public static Matrix4f loatMat4(FloatBuffer vals) {
		
//		vals.flip();
		
		Matrix4f m = new Matrix4f();
		
		int index;
		for (index = 0; index < 16; index++)
			m.set(index % 4, index / 4, vals.get());
		
		return m;
	}
*/
 /**
  * removes all empty strings from an array of strings, returning a new array of
  * non-empty strings.
  * 
  * @param data 0 or more string arrays to be processed and returned as a new array
  * of non-empty strings.
  * 
  * 	- The array `data` is passed as an argument to the function, indicating that it
  * is a collection of strings.
  * 	- The length of the array `data` is specified using the variable `i`, which ranges
  * from 0 to `data.length - 1`.
  * 	- Each element in the array `data` is checked for emptiness using the method
  * `equals()`. If an element is empty, it is not included in the result.
  * 	- The resulting array `res` is created by calling the `toArray()` method on the
  * list of non-empty strings generated by the loop.
  * 
  * @returns an array of non-empty strings obtained by iterating over the input array
  * and adding each non-empty string to a list.
  */
	public static String[] removeEmptyStrings(String[] data) {
		ArrayList<String> result = new ArrayList<String>();

		for (int i = 0; i < data.length; i++)
			if (!data[i].equals(""))
				result.add(data[i]);

		String[] res = new String[result.size()];
		result.toArray(res);

		return res;
	}

 /**
  * converts an array of integers to an integer array with the same length, by simply
  * copying the elements of the original array.
  * 
  * @param data 1D array of integers that will be converted to an integer array through
  * the method.
  * 
  * 1/ The type of the array is specified as `Integer[]`.
  * 2/ The length of the array is determined by the size of the `data` array.
  * 3/ Each element in the `data` array is assigned to an equivalent integer value in
  * the output `result` array, using the `=` operator.
  * 
  * @returns an integer array with the same length as the input `data` array, containing
  * the corresponding integer values.
  */
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
 /**
  * converts a list of integers to an integer array with the same size as the list.
  * It uses a for loop to iterate over the list and copy each element into the resulting
  * array.
  * 
  * @param data list of integers that will be converted to an integer array by the
  * `toIntArray()` method.
  * 
  * 	- `data`: This is a `List` object that contains a collection of `Integer` objects.
  * 	- `size()`: This is an instance method of the `List` class that returns the number
  * of elements in the list.
  * 	- `get(int index)`: This is an instance method of the `List` class that returns
  * the element at a specific position in the list.
  * 
  * @returns an integer array of size equal to the number of elements in the input list.
  */
	public static int[] toIntArray(List<Integer> data) {
		int[] result = new int[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
	
 /**
  * takes a `Float[]` array as input and returns an equivalent `float[]` array with
  * the same elements.
  * 
  * @param data Float array that contains the values to be converted into a float array.
  * 
  * 	- `data` is an array of `Float`.
  * 
  * @returns an array of `float` values, with the same length as the input `data` array.
  */
	public static float[] toFloatArray(Float[] data) {
		float[] result = new float[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
 /**
  * takes a list of floating-point numbers and returns an array of the same size,
  * containing each number from the input list.
  * 
  * @param data list of floating-point numbers that will be converted to an array of
  * float values.
  * 
  * 	- `data`: A list containing floating-point numbers.
  * 
  * @returns an array of `float` values, each corresponding to a value in the input list.
  */
	public static float[] toFloatArray(List<Float> data) {
		float[] result = new float[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
}
