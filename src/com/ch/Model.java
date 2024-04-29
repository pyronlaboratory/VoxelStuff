package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * in the provided code is a Java class that handles rendering of 3D models using the
 * OpenGL API. It provides a way to load a 3D model from a set of vertices and indices,
 * create a Vertex Array Object (VAO), store the indices, store the vertex data,
 * unbind the VAO, and return a `Model` object representing the loaded model. The
 * class also includes methods for creating and binding a VAO, as well as storing
 * index data in an element array buffer.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * binds a vertex array object, enables vertex attributes for position and texture
	 * coord, and calls `glDrawElements` to render triangles.
	 */
	public void draw() {
		GL30.glBindVertexArray(vao);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, size);
		GL11.glDrawElements(GL11.GL_TRIANGLES, size, GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * enables vertex attributes 0 and 1 in the OpenGL context.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * disables vertex attributes 0 and 1 using `glDisableVertexAttribArray`.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * returns the value of the `vao` field.
	 * 
	 * @returns an integer value representing the VAO.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * returns the current size of an object's storage.
	 * 
	 * @returns the value of the `size` field.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * loads data into a model object from an array of vertices and an array of indices.
	 * 
	 * @param vertices 3D model's geometry data, which is stored in an array of floating-point
	 * values and passed to the `storeData()` method for storage in the Vertex Array
	 * Object (VAO).
	 * 
	 * 	- `float[] vertices`: An array of floating-point values representing 3D vertices.
	 * 	- `int[] indices`: An array of integer values representing the triangle indices.
	 * 	- `int vao`: A variable storing the Vulkan handle for the vertex array object
	 * (VAO) created during the function execution.
	 * 	- `int v_count`: The number of triangles represented by the `indices` array.
	 * 
	 * @param indices 3D model's vertices array in the graphics hardware, which is stored
	 * and bound to a Vertex Array Object (VAO) for efficient rendering.
	 * 
	 * 	- `indices`: An integer array representing the indices of the vertices in the model.
	 * 	- `v_count`: The number of vertices in the model, which can be obtained by calling
	 * `indices.length`.
	 * 
	 * @returns a `Model` object containing the loaded data.
	 * 
	 * 	- The return type is `Model`, which represents a 3D model loaded from the given
	 * vertices and indices.
	 * 	- The `vao` field contains the Vulkan object handle for the model, used for binding
	 * the model to the GPU.
	 * 	- The `v_count` field contains the number of vertices in the model.
	 * 
	 * Overall, the `load` function loads a 3D model from the given vertices and indices
	 * and returns the loaded model handle for further processing or rendering.
	 */
	public static Model load(float[] vertices, int[] indices) {
		int vao = createVAO();
		storeIndices(indices);
		storeData(0, vertices);
		unbindVAO();
		int v_count = indices.length;
		return new Model(vao, v_count);
	}
	
	/**
	 * generates a new vertex array object (Vao) and binds it to the current context,
	 * allowing for manipulation of vertices within the context.
	 * 
	 * @returns an integer value representing a unique vertex array object (VAO) handle.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * stores an array of floats into a vertex buffer object (VBO) and sets up vertex
	 * attributers to draw the data in a specific format.
	 * 
	 * @param attrib 3D vertex attribute that contains the data to be stored in the VBO,
	 * specifying the buffer index and the format of the data.
	 * 
	 * @param data 3D data to be stored in a vertex buffer object (VBO).
	 * 
	 * 	- `data` is a `float[]` array.
	 * 	- Its size is determined by the value of `Util.createFlippedBuffer(data)`, which
	 * is 5 * 4 in this case.
	 * 	- The elements of the array are stored in two vertex attributes, `attrib` and
	 * `attrib + 1`, respectively.
	 * 	- Each attribute has a value of 3 * 4 bytes for the first element, and 2 * 4 bytes
	 * for the second element.
	 */
	private static void storeData(int attrib, float[] data) {
		int vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Util.createFlippedBuffer(data), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attrib, 3, GL11.GL_FLOAT, false, 5 * 4,     0);
		GL20.glVertexAttribPointer(attrib + 1, 2, GL11.GL_FLOAT, false, 5 * 4, 3 * 4);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * generates a new buffer object using the `glGenBuffers` method, binds it with
	 * `glBindBuffer`, and then uses `glBufferData` to store an array of indices in the
	 * buffer.
	 * 
	 * @param indices 3D vertices' indices in the element array buffer to be bound and stored.
	 * 
	 * 	- `indices`: an array of integers that represents the indices of vertices in a
	 * 3D mesh.
	 * 	- `GL15`: a class that provides methods for managing graphics buffers and vertex
	 * data.
	 * 	- `glGenBuffers()`: a method that creates a new buffer object.
	 * 	- `glBindBuffer()`: a method that binds a buffer object to the current rendering
	 * context.
	 * 	- `glBufferData()`: a method that fills a buffer with data, in this case, the
	 * indices of a 3D mesh.
	 * 	- `Util`: an unknown class that provides a method for creating a flipped buffer.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * disables the vertex array object (VAO) bound to handle rendering more efficiently
	 * by the GPU.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
