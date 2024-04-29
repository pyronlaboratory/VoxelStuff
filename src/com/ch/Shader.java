package com.ch;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.opengl.GL20;

import com.ch.math.Matrix4f;

/**
 * in OpenGL provides a way to create and manage shaders for use in 3D graphics
 * programs. It offers methods for loading shader sources, creating shader programs,
 * validating program links, and accessing program attributes and uniforms. Additionally,
 * it allows for the creation of vertex and fragment shaders using the `loadShader()`
 * method.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glUseProgram(program) and activates a previously created OpenGL program.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * retrieves the value of a field named `program`.
	 * 
	 * @returns the value of the `program` field.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * updates a uniform value in a shader program based on the length of an array of
	 * floats passed as an argument. The function calls the `glUniformx` method of the
	 * OpenGL API with the location of the uniform and the corresponding float value from
	 * the array.
	 * 
	 * @param name name of the uniform location in the GL context, which is used to
	 * identify the location where the uniform values are to be stored.
	 */
	public void uniformf(String name, float ...vals) {
		switch (vals.length) {
		case 1:
			GL20.glUniform1f(getLoaction(name), vals[0]);
			break;
		case 2:
			GL20.glUniform2f(getLoaction(name), vals[0], vals[1]);
			break;
		case 3:
			GL20.glUniform3f(getLoaction(name), vals[0], vals[1], vals[2]);
			break;
		case 4:
			GL20.glUniform4f(getLoaction(name), vals[0], vals[1], vals[2], vals[3]);
			break;
		}
	}
	
	/**
	 * sets a 4x4 uniform matrix value to the specified location using the `glUniformMatrix4`
	 * method from the OpenGL API.
	 * 
	 * @param name 0-based array index of the uniform location where the matrix will be
	 * stored.
	 * 
	 * @param mat 4x4 matrix that will be passed as a uniform buffer to the OpenGL API
	 * through the `GL20.glUniformMatrix4()` method.
	 * 
	 * 	- `name`: A String representing the uniform name.
	 * 	- `mat`: A `Matrix4f` object that contains the matrix data in linear form.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * glances at the specified uniform location for a given program and name using the
	 * `GL20` class.
	 * 
	 * @param name name of an uniform in the program.
	 * 
	 * @returns an integer representing the uniform location in the program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader from a file and creates a program object, validating it after loading.
	 * 
	 * @param filename filename of the shader to be loaded.
	 * 
	 * @returns a newly created `Shader` object containing the loaded shader program.
	 * 
	 * 	- The returned output is an instance of the `Shader` class, which represents a
	 * shader program that has been loaded from a file.
	 * 	- The `Shader` object contains references to the vertex and fragment shaders that
	 * make up the shader program, as well as other information such as the shader's
	 * validation status and any attached textures or buffers.
	 * 	- The `Shader` class is immutable, meaning that once a shader has been loaded,
	 * its properties cannot be modified. However, the underlying vertex and fragment
	 * shaders can still be modified after they have been loaded.
	 * 	- The `loadShader` function creates two new programs, one for the vertex shader
	 * and one for the fragment shader, using the `GL20.glCreateProgram()` function. These
	 * programs are then combined to form the final shader program.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a new shader program and attaches it to an existing program, loading the
	 * source code from a string variable.
	 * 
	 * @param target type of shader to be created, with values ranging from 0 (vertex
	 * shader) to 3 (geometry shader), 7 (pixel shader), or 9 (fragment shader).
	 * 
	 * @param src source code of the shader to be compiled.
	 * 
	 * @param program 3D graphics program that the loaded shader will be attached to.
	 */
	private static void loadShader(int target, String src, int program) {
		int shader = GL20.glCreateShader(target);
		
		GL20.glShaderSource(shader, src);
		GL20.glCompileShader(shader);
		
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		
		GL20.glAttachShader(program, shader);
	}
	
	/**
	 * validates a program object and logs any errors if they occur.
	 * 
	 * @param program 3D graphics program to be validated and linked with the GPU.
	 */
	private static void validateProgram(int program) {
		GL20.glLinkProgram(program);
		
		if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
		
		GL20.glValidateProgram(program);
		
		if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
	}
	
	/**
	 * reads the contents of a given file as a string, handling potential exceptions
	 * during the read operation.
	 * 
	 * @param file file from which the text is to be read.
	 * 
	 * @returns a string representation of the contents of the specified file.
	 */
	private static String getText(String file) {
		String text = "";
		try {
			InputStream is = new FileInputStream(file);
			int ch;
			while ((ch = is.read()) != -1)
				text += (char) ch;
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return text;
	}

}
