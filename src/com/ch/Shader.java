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
 * in the provided file is a Java class that allows for the creation and manipulation
 * of shaders for use in a 3D graphics pipeline. It provides methods for binding the
 * shader to a program, uniform floating-point values, and matrix transformations,
 * as well as loading shaders from files. The class also includes utility methods for
 * getting location information and validating the shader program.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glUseProgram to activate a previously created program object, indicating that it
	 * should be used for subsequent GPU operations.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * retrieves an integer value representing a program.
	 * 
	 * @returns an integer representation of the program.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * sets a uniform float value for a given name and length of input values passed as
	 * an array.
	 * 
	 * @param name name of the uniform to be set.
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
	 * unifies a 4x4 matrix onto the GPU using the `glUniformMatrix4` method from the
	 * Android GL20 library, passing the matrix data in a flipped buffer.
	 * 
	 * @param name name of the uniform location to which the matrix will be assigned.
	 * 
	 * @param mat 4x4 floating-point matrix to be uniformed.
	 * 
	 * 	- `name`: A string parameter representing the name of the uniform location where
	 * the matrix will be stored.
	 * 	- `mat`: An instance of `Matrix4f` representing a 4x4 floating-point matrix. This
	 * object contains the matrix data in its linear buffer, which is passed to
	 * `glUniformMatrix4` for storage at the specified location.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * retrieves the location of a uniform in a GPU program using the `GL20.glGetUniformLocation()`
	 * method.
	 * 
	 * @param name 0-based index of the uniform location to retrieve in the OpenGL program.
	 * 
	 * @returns an integer representing the location of a uniform in a graphics program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader program from a given filename, consisting of a vertex and fragment
	 * shader. It creates a new program with the loaded shaders and validates it before
	 * returning a new Shader object representing the program.
	 * 
	 * @param filename file containing the vertex and fragment shaders to be loaded into
	 * the program.
	 * 
	 * @returns a `Shader` object that represents a shader program created by combining
	 * a vertex shader and a fragment shader.
	 * 
	 * 	- The output is a `Shader` object that represents a shader program created by
	 * combining a vertex shader and a fragment shader using the `GL20.glCreateProgram()`
	 * method.
	 * 	- The shader program is validated using the `validateProgram()` method to ensure
	 * it is properly linked and functional.
	 * 	- The shader program is returned as a new instance of the `Shader` class, which
	 * provides methods for accessing and manipulating the shader's attributes and uniforms.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a shader program and loads a shader source code into it. It compiles the
	 * shader and attaches it to the program. If compilation fails, an error message is
	 * printed and the program exits.
	 * 
	 * @param target type of shader to be created, with values of 0 for vertex shaders
	 * and 1 for fragment shaders.
	 * 
	 * @param src source code of the shader to be compiled.
	 * 
	 * @param program 3D graphics program to which the loaded shader will be attached.
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
	 * validates a program object by checking its link and validation statuses, and prints
	 * any error messages if they occur.
	 * 
	 * @param program 3D graphics program to be validated and linked with the GL API.
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
	 * reads the contents of a specified file and returns its text as a string.
	 * 
	 * @param file path to a file that contains the text to be read.
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
