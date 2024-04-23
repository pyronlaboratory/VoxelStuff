package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * in the provided code is used to handle vertex and index buffer objects (VBOs and
 * IBOs) for rendering 3D models. It provides methods for binding and unbinding VAOs,
 * enabling and disabling vertex attributaries, and storing data in VBOs and IBOs.
 * The class also provides a method for loading a model from a set of vertices and indices.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
 /**
  * binds a vertex array object (VAO), enables vertex attribution arrays for position
  * and texture coordinates, draws a set of triangles using either `glDrawArrays` or
  * `glDrawElements`, and then disables the vertex attribution arrays and unbinds the
  * VAO.
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
  * enables vertex attribute arrays for both attributes 0 and 1 in the OpenGL context.
  */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
 /**
  * disables both vertex attribute arrays.
  */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
 /**
  * returns the value of a variable `vao`.
  * 
  * @returns an integer value representing the `vao` variable.
  */
	public int getVAO() {
		return vao;
	}
	
 /**
  * returns the value of a field named `size`.
  * 
  * @returns the value of the `size` field.
  */
	public int getSize() {
		return size;
	}
	
 /**
  * loads a 3D model from a set of vertices and indices stored in arrays, creates a
  * Vertex Array Object (VAO), stores the indices, stores the vertex data, unbinds the
  * VAO, and returns a `Model` object representing the loaded model.
  * 
  * @param vertices 3D geometric data for the model, which is stored in an array of
  * floating-point values and provided to the `Model` constructor for creation and rendering.
  * 
  * 	- `float[] vertices`: An array of floating-point numbers representing 3D vertex
  * positions.
  * 	- `int[] indices`: An array of integers representing the triangle Indices in the
  * mesh.
  * 
  * @param indices 3D model's index data, which is used to bind the vertex array object
  * (VAO) and store the indices of the vertices in the model.
  * 
  * 	- `indices`: An integer array representing the indices of the vertices in the
  * model. Its length is equal to the number of vertices in the model.
  * 
  * @returns a `Model` object containing the loaded vertices and indices.
  * 
  * 	- The returned object is of type `Model`, which represents a 3D model in the program.
  * 	- The `vao` field stores the handle to the Vertex Array Object (VAO) used for
  * rendering the model.
  * 	- The `v_count` field stores the number of vertices in the model.
  * 
  * Therefore, the output returned by the `load` function is a `Model` object that
  * contains the VAO handle and the number of vertices in the model.
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
  * creates a Vertex Array Object (VAO) and binds it to the current GL context, allowing
  * for efficient management of vertex data.
  * 
  * @returns an integer value representing a valid vertex array object (VBO).
  */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
 /**
  * binds a vertex buffer object (VBO) and sets up an array of floating-point data to
  * be rendered by the graphics processing unit (GPU).
  * 
  * @param attrib 3D vertex attribute index that stores the data for the current buffer.
  * 
  * @param data 3D array of floating-point values that will be stored in the Vertex
  * Buffer Object (VBO).
  * 
  * 	- `data` is an array of 4 floats, which are stored in a VAO (VerteX Array Object)
  * using `GL15.glBufferData()`.
  * 	- The data is serialized and deserialized using `Util.createFlippedBuffer()`,
  * which is a utility method that creates a buffer object from the input data.
  * 	- The buffer is created with the `GL_STATIC_DRAW` binding mode, indicating that
  * the data will be drawn in the same way each time the buffer is bound.
  * 	- Two VAO attributes are created and set using `GL20.glVertexAttribPointer()`,
  * one for each of the two arrays in `data`. The first attribute is for 3 floats,
  * while the second attribute is for 2 floats.
  * 	- The `false` argument in `GL11.glBufferData()` indicates that the data will be
  * copied into the buffer without any transformation or other modifications.
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
  * generates a new buffer for storing index data, binds it, and then transfers the
  * index data to the buffer using `GL_STATIC_DRAW`.
  * 
  * @param indices 3D vertex positions of a shape to be stored in an element array
  * buffer for rendering.
  * 
  * 	- `indices`: A serialized array of integers, representing the indices of vertices
  * in a 3D model.
  * 	- `ibo`: The buffer object handle generated by `GL15.glGenBuffers()` for storing
  * the vertex indices.
  * 	- `GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo)`: Binds the specified
  * buffer object to the element array buffer slot.
  * 	- `GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices),
  * GL15.GL_STATIC_DRAW)`: Stores the vertex indices in the bound element array buffer
  * using the `Util.createFlippedBuffer()` method to create a flipped copy of the input
  * array. The `GL15.glBufferData()` function sets the data for the element array
  * buffer, with the type set to `GL_STATIC_DRAW`.
  */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
 /**
  * terminates the binding of a Vertex Array Object (VAO) by calling `glBindVertexArray(0)`.
  */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
