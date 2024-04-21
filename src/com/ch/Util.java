package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

/**
 * is a collection of utility methods for working with various data types, including
 * integers, floats, bytes, and matrices. The class provides methods for creating
 * buffers from these data types, flipping buffers, and removing empty strings. It
 * also offers methods for converting integer arrays to int arrays, floating arrays
 * to float arrays, and matrix4f values to floating point buffers.
 */
public class Util {
	
 /**
  * creates a new float buffer with the specified size using the `BufferUtils` class.
  * 
  * @param size number of float values to be stored in the resulting FloatBuffer.
  * 
  * @returns a `FloatBuffer` object with the specified size.
  * 
  * The function returns a `FloatBuffer`.
  * A `FloatBuffer` is an object that provides direct access to a contiguous block of
  * memory cells representing floating-point numbers without incurring a full copy of
  * the data. It allows efficient manipulation and modification of the data in the buffer.
  * It has a size attribute indicating the number of elements in the buffer.
  */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

 /**
  * creates an `IntBuffer` instance with the specified size using `BufferUtils`.
  * 
  * @param size integer capacity of the IntBuffer that will be created by the function.
  * 
  * @returns an IntBuffer instance representing a buffer of integers with the specified
  * size.
  * 
  * The function returns an `IntBuffer` object, which is a view of a portion of memory
  * that can be used to store and manipulate integer values.
  * The `IntBuffer` object is created using the `BufferUtils` class, which provides a
  * range of methods for manipulating buffers.
  * The size parameter passed to the function determines the number of integers that
  * can be stored in the buffer.
  */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

 /**
  * creates a new `ByteBuffer` instance with a specified size. The returned buffer can
  * be used for reading or writing binary data.
  * 
  * @param size integer size of the ByteBuffer to be created, which determines the
  * capacity of the buffer and its ability to hold data.
  * 
  * @returns a non-null `ByteBuffer` object of the specified size.
  * 
  * 	- The output is a `ByteBuffer` instance that can be used to store binary data.
  * 	- The size of the buffer is determined by the `size` parameter passed into the function.
  * 	- The buffer is created using the `BufferUtils` class, which provides utility
  * methods for working with buffers in Java.
  */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

 /**
  * creates an `IntBuffer` object from an array of integers and flips it to create a
  * view of the array in reverse order.
  * 
  * @returns an flipped IntBuffer containing the input values.
  * 
  * 	- The `IntBuffer` object that is returned represents a view of the underlying
  * memory, where the low-order byte values of the input array are stored in successive
  * memory locations.
  * 	- The `flip()` method is called on the buffer after it has been created and
  * populated with the input values, which reverses the endianness of the data.
  * 	- The returned buffer is guaranteed to be a valid view of the input array for as
  * long as the array is not modified or its elements are not reused.
  */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
 /**
  * creates a new `FloatBuffer` instance from an array of floating-point values and
  * flips it to create a new view of the data.
  * 
  * @returns a flipped FloatBuffer containing the provided values.
  * 
  * 	- `FloatBuffer buffer`: The buffer that contains the flipped float values.
  * 	- `values.length`: The number of float values stored in the buffer.
  * 	- `put(values)`: The method used to store the float values in the buffer.
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
  * filters a given array of strings, retaining only those that are not empty. The
  * resulting array is returned as a new String array.
  * 
  * @param data 0-length array of strings that the function operates on and whose
  * non-empty elements are collected and returned in the output array.
  * 
  * 	- `data` is an array of strings.
  * 	- It has a fixed length of `data.length`.
  * 	- Each element in the array is a string.
  * 	- The strings may contain any valid characters according to the Java character set.
  * 
  * @returns an array of non-empty strings.
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
  * takes an `Integer` array as input and returns a new `int[]` array with the same elements.
  * 
  * @param data array of integers that will be converted to an integer array using the
  * `toIntArray()` method.
  * 
  * 1/ The `Integer[] data` parameter is an array of integers that can be manipulated
  * in this function.
  * 2/ The function creates a new `int[] result` with the same length as the original
  * `data`.
  * 3/ The function loops through each element of the `data` array and assigns its
  * value to the corresponding position in the `result` array.
  * 4/ Finally, the function returns the `result` array, which contains the deserialized
  * values from the input `data` array.
  * 
  * @returns an integer array containing the values of the input `Integer[]` array.
  */
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
 /**
  * takes a list of integers and returns an integer array with the same size as the
  * input list, containing the corresponding integers from the list.
  * 
  * @param data List of integers that is converted to an integer array by the
  * `toIntArray()` method.
  * 
  * 	- `data` is a list of integers.
  * 	- `data.size()` returns the number of elements in the list.
  * 	- The `for` loop iterates over the elements of the list and assigns each element
  * to its corresponding position in the resulting integer array.
  * 
  * @returns an integer array containing the elements of the provided list.
  */
	public static int[] toIntArray(List<Integer> data) {
		int[] result = new int[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
	
 /**
  * converts a `Float` array into a `float` array, copying the values of the original
  * array to the new array.
  * 
  * @param data 1D array of `Float` values that will be converted into an array of
  * `float` values.
  * 
  * 	- `data`: An instance of the `Float` array class, representing an array of
  * floating-point numbers.
  * 	- `length`: A field of type `int`, indicating the number of elements in the `data`
  * array.
  * 
  * @returns an array of floats containing the same values as the input `data` array.
  */
	public static float[] toFloatArray(Float[] data) {
		float[] result = new float[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
 /**
  * converts a given list of `Float` values into an array of `float` values, returning
  * the converted array.
  * 
  * @param data list of floats that will be converted to an array of floats by the
  * `toFloatArray()` method.
  * 
  * 	- `List<Float>` represents a collection of floating-point numbers.
  * 	- `data.size()` returns the number of elements in the list.
  * 	- `data.get(i)` retrieves the `i`-th element from the list and assigns it to the
  * corresponding position in the output array `result`.
  * 
  * @returns an array of `float` values containing the elements of the input `List<Float>`.
  */
	public static float[] toFloatArray(List<Float> data) {
		float[] result = new float[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
}
