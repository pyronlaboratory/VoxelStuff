package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

/**
 * provides various methods for manipulating and transforming data in different
 * formats, including buffers, integers, floats, and strings. These methods include
 * creating and manipulating buffers, converting between integer and float arrays,
 * and removing empty strings from an array. Additionally, the class provides a method
 * to flip a buffer and another to create a matrix4f from a FloatBuffer.
 */
public class Util {
	
 /**
  * generates a `FloatBuffer` object of specified size, using the `BufferUtils` class.
  * 
  * @param size capacity of the `FloatBuffer` to be created, which determines the
  * number of elements that can be stored in the buffer.
  * 
  * @returns a `FloatBuffer` object of the specified size, which can be used to store
  * and manipulate floating-point data.
  * 
  * 	- The returned buffer is of type `FloatBuffer`.
  * 	- The size of the buffer is specified by the `size` parameter passed to the function.
  * 	- The buffer contains a contiguous block of floating-point values with a specific
  * size.
  */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

 /**
  * creates an integer buffer with the specified size using `BufferUtils`. The returned
  * buffer can be used for storing and manipulating integer values.
  * 
  * @param size integer capacity of the IntBuffer created by the `createIntBuffer()`
  * method.
  * 
  * @returns an `IntBuffer` object of the specified size.
  * 
  * 	- The IntBuffer object is created using the `BufferUtils` class.
  * 	- The size parameter passed to the function determines the capacity of the buffer.
  * 	- The returned IntBuffer has a fixed capacity and cannot be resized or modified
  * once it is created.
  * 	- The buffer can be accessed and manipulated using the standard methods provided
  * by the `IntBuffer` interface.
  */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

 /**
  * creates a new instance of `java.nio.Buffer` with the specified size.
  * 
  * @param size buffer size, which is used to create a new ByteBuffer object with the
  * specified capacity.
  * 
  * @returns a non-null `ByteBuffer` instance with the specified size.
  * 
  * 	- The function returns a `ByteBuffer` instance that represents a contiguous block
  * of memory capable of holding binary data.
  * 	- The size parameter specifies the capacity of the buffer, which determines how
  * much data can be stored in it.
  * 	- The buffer is created using `BufferUtils`, a utility class provided by the Java
  * platform for working with buffers and other memory-related primitives.
  */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

 /**
  * creates an `IntBuffer` from a list of integers, stores them in the buffer, and
  * flips the buffer to create a new buffer with the values in the original order.
  * 
  * @returns an IntBuffer containing the flipped values.
  * 
  * 	- The function creates an `IntBuffer` object named `buffer`.
  * 	- The `buffer` object is filled with the input array `values`.
  * 	- The `buffer` object is flipped using the `flip()` method.
  * 
  * The resulting `IntBuffer` object has the following properties:
  * 
  * 	- It contains the same sequence of integers as the input array `values`.
  * 	- It is in a flipped state, meaning that the order of the integers in the buffer
  * is reversed compared to the original input array.
  */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
 /**
  * creates a new FloatBuffer by copying the given array of floats into it, and then
  * flipping the buffer for efficient access.
  * 
  * @returns a flipped FloatBuffer containing the provided values.
  * 
  * The function creates a new `FloatBuffer` object by calling the `createFloatBuffer`
  * method and passing in the length of the input array as an argument.
  * 
  * The `buffer.put(values)` statement copies the elements of the input array into the
  * buffer, starting at the beginning of the buffer.
  * 
  * The `buffer.flip()` statement flips the buffer, making its position pointer point
  * to the end of the buffer instead of the beginning. This allows for efficient reading
  * of the buffer's contents in a particular order.
  * 
  * Overall, the `createFlippedBuffer` function creates a new `FloatBuffer` object
  * that is initialized with the input array values and is flipped for efficient access.
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
  * filters a string array by removing any empty strings, resulting in a new array
  * with non-empty strings.
  * 
  * @param data 2D array of strings that needs to be processed and transformed into a
  * new 1D array without empty strings.
  * 
  * The method takes an array of `String`s as input, denoted by `data`.
  * 
  * The type of `data` is not explicitly specified; however, considering the context
  * of the code snippet, it can be inferred to be of type `String[]`.
  * 
  * Within the function, `data` undergoes processing and its elements are filtered
  * based on a predetermined condition - if the element is not an empty string.
  * 
  * The filtered elements are then collected in an instance of `ArrayList`, denoted
  * by `result`. The `ArrayList` is created with no arguments, indicating that it
  * should be initialized with a default capacity.
  * 
  * Subsequently, the elements of `result` are copied into an array of `String`s,
  * denoted by `res`, using the `toArray()` method.
  * 
  * The `res` array is returned as output from the function.
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
  * converts an `Integer[]` array to an `int[]` array, retaining the original values.
  * 
  * @param data array of integers that is converted into an integer array by the function.
  * 
  * 	- `Integer[] data`: This is an array of integers that represents the original
  * data to be converted into an integer array.
  * 	- `int[] result`: The resulting integer array that stores the converted values
  * from `data`.
  * 
  * @returns an integer array with the same length as the input `data` array, containing
  * the original values of the elements.
  */
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
 /**
  * converts a list of integers to an integer array, creating a new array with the
  * same size as the list and containing the corresponding integers from the list.
  * 
  * @param data List of integers that are converted into an integer array by the
  * `toIntArray()` method.
  * 
  * 1/ `data` is a `List<Integer>` containing a collection of integers.
  * 2/ The size of the list, denoted by `data.size()`, can be used to determine the
  * number of elements in the list.
  * 3/ Each element in the list can be accessed and retrieved using its index or
  * position in the list, as denoted by `data.get(i)`.
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
  * converts a `Float[]` array to an equivalent `float[]` array with the same length,
  * copying each value from the input array to the output array.
  * 
  * @param data Float array that is to be converted into a float array.
  * 
  * 	- The type of `data` is `Float[]`, indicating that it is an array of floating-point
  * numbers.
  * 	- The length of `data` is specified by its `length` attribute, which is a valid
  * integer value.
  * 	- Each element in the array is represented by a separate `Float` object, which
  * can be accessed and manipulated individually through the array's indices (zero-based).
  * 
  * @returns an array of floating-point values equivalent to the input `data`.
  */
	public static float[] toFloatArray(Float[] data) {
		float[] result = new float[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
 /**
  * converts a list of floating-point numbers to an array of the same size, copying
  * each number from the input list to the output array.
  * 
  * @param data list of Float values that will be converted into an array of Floats
  * by the function.
  * 
  * 	- The input `data` is of type `List<Float>`, indicating that it is an array-like
  * data structure containing floating-point numbers.
  * 	- The size of `data` can be retrieved using its `size()` method, which returns
  * the number of elements in the list.
  * 	- Each element of `data` is accessed using its index, represented by the variable
  * `i`, which ranges from 0 to `data.size() - 1`.
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
