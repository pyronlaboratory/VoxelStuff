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
 * is a Java class that allows for the creation and management of shaders for use in
 * graphics rendering applications. It provides methods for binding shaders to programs,
 * setting uniforms and matrices, and loading shaders from files. The class also
 * includes utility methods for working with shader source code.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
 /**
  * glues a program to a named buffer object, making it accessible for drawing.
  */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
 /**
  * returns the value of a field called `program`.
  * 
  * @returns an integer representing the program value.
  */
	public int getProgram() {
		return this.program;
	}
	
 /**
  * takes a string `name` and an array of floats `vals`. It switches on the length of
  * the array to call the appropriate GL20 method for uniform float setting: `glUniform1f`,
  * `glUniform2f`, `glUniform3f`, or `glUniform4f`.
  * 
  * @param name name of the uniform location for which the values are being set.
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
  * sets a 4x4 uniform matrix using the `glUniformMatrix4` method provided by Android's
  * GL20 class. It takes two parameters: a string representing the location of the
  * uniform and a `Matrix4f` object containing the matrix data.
  * 
  * @param name 0-based index of the uniform location where the matrix will be stored
  * in the GPU's memory.
  * 
  * @param mat 4x4 matrix to be uniformed.
  * 
  * 	- `name`: The name of the uniform variable, which is used to store the matrix's
  * values in the shader program.
  * 	- `mat`: A `Matrix4f` object representing a 4x4 homogeneous transformation matrix.
  * Its properties include its linear data, which is stored in a flipped buffer for
  * efficient access in the shader program.
  */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
 /**
  * retrieves the location of a uniform in a program using the `GL20` class and method
  * `glGetUniformLocation`.
  * 
  * @param name 0-based index of a uniform location to retrieve within the current
  * program as returned by `GL20.glGetUniformLocation()`.
  * 
  * @returns an integer representing the location of a uniform in a program.
  */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
 /**
  * loads a shader program from a file and creates a new shader object representing it.
  * 
  * @param filename name of the shader file to be loaded.
  * 
  * @returns a `Shader` object representing a compiled shader program.
  * 
  * 	- The function returns a `Shader` object, which represents a shader program that
  * can be used to render graphics in a 3D graphics pipeline.
  * 	- The `Shader` object contains information about the shader program, including
  * its name and the names of its vertex and fragment shaders.
  * 	- The `Shader` object is immutable, meaning that once it is created, its properties
  * cannot be changed.
  * 	- The function creates a new program object using the `GL20.glCreateProgram()`
  * method, and then loads the vertex and fragment shaders into the program using the
  * `loadShader` methods.
  * 	- The function validates the program after loading the shaders to ensure that it
  * is correctly structured and can be used for rendering.
  */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
 /**
  * creates a new shader object in the GPU, sets its source code using the
  * `GL20.glShaderSource()` method, compiles it using the `GL20.glCompileShader()`
  * method, and attaches it to a program object.
  * 
  * @param target type of shader (either vertex or fragment) that the function is
  * creating or modifying.
  * 
  * @param src shader source code to be compiled and executed by the `GL20.glCompileShader()`
  * method.
  * 
  * @param program 3D graphics program that the loaded shader will be attached to and
  * used by.
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
  * validates a program object and checks its linking and validation status. If any
  * errors are found, it prints the error message and exits the program.
  * 
  * @param program 3D graphics program to be linked and validated.
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
  * reads the contents of a specified file as a string and returns it.
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
