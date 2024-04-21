package com.ch;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import com.ch.math.Vector3f;
import com.ch.voxel.World;

/**
 * in this project is responsible for handling the rendering of a 3D scene using the
 * Java OpenGL API. It sets up the graphics context, loads a shader and a texture,
 * and initializes a world object. The loop method updates the camera position and
 * renderes the scene using the World object. The update method processes input events
 * and updates the camera position, while the render method renders the scene using
 * the Shader class. Finally, the exit method provides an exit status when the program
 * is closed.
 */
public class Main {
	
 /**
  * initializes display and graphics libraries, enters an infinite loop for rendering
  * and event handling, and exits with a successful return value of 0.
  * 
  * @param args program's command-line arguments passed to the `main` function by the
  * operating system or user.
  * 
  * 	- `args`: an array of `String` values representing command-line arguments passed
  * to the program. The length of this array is provided by the `main` method caller.
  * 	- Each element in `args`: a single `String` value representing a command-line
  * argument. These values can be used as input for the program, or they can be ignored
  * if appropriate.
  */
	public static void main(String[] args) {
		
		initDisplay();
		initGL();
		loop();
		exit(0);
		
	}
	
	private static Model m;
	private static Shader s;
	private static Texture t;
	private static Camera3D c;
//	private static Chunk[][][] ch;
	private static World w;
	
 /**
  * sets up a display mode with resolution 1920x1080, creates a GL context with forward
  * compatibility and core profile enabled, and enables vsync. It also prints the GL
  * version string using `GL11.glGetString()`.
  */
	private static void initDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(1920, 1080));
			Display.create(new PixelFormat(), new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true));
			Display.setVSyncEnabled(true);
			System.out.println(GL11.glGetString(GL11.GL_VERSION));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
 /**
  * initializes OpenGL components such as clear color, grabbing mouse events, enabling
  * depth test and cull face, loading shader, texture, and vertex data.
  */
	private static void initGL() {
		
		GL11.glClearColor(0.1f, 0.7f, 1f, 1);
		
		Mouse.setGrabbed(true);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		c = new Camera3D(70, 16.f/9, .03f, 1000);
		
		s = Shader.loadShader("res/shaders/default");
		
		t = new Texture("res/textures/block0.png");
		
		float[] vertices = {
			-.5f, -.5f, 0,
			-.5f,  .5f, 0,
			 .5f,  .5f, 0,
			 .5f, -.5f, 0,
			
		};
		int[] indices = {
				0, 1, 2, 0, 2, 3
		};
//		ch = new Chunk[4][4][4];
//		for (int i = 0; i < 4; i++)
//			for (int j = 0; j < 4; j++)
//				for (int k = 0; k < 4; k++) {
//					ch[i][j][k] = new Chunk(i, j, k);
//					ch[i][j][k].updateBlocks();
//					ch[i][j][k].genModel();
//				}
		w = new World();
		//m = c.genModel();//Model.load(vertices, indices);
		
		c.getTransform().setPos(new Vector3f(0, 0, 0));
		
	}
	
 /**
  * continuously loops while a close request is not received and the escape key is not
  * pressed. It updates the display title, clears the color and depth buffers, and
  * renders the scene using the `render()` method.
  */
	private static void loop() {
		
		Timer.init();
		
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			
			Timer.update();
			
			Display.setTitle("" + Timer.getFPS() + 
					/* "   " + c.getTransform().getPos().toString() +*/ "   " 
					+ ((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()) / 1048576) + " of " + (Runtime.getRuntime().maxMemory() / 1048576));
			
			update(Timer.getDelta());
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			render();
			
			Display.update();
			
		}
		
	}
	
 /**
  * updates the position of an object `w` based on input provided by a `c` object and
  * a time interval `dt`.
  * 
  * @param dt time step or simulation speedup used to update the game objects' positions
  * and states during the rendering process.
  */
	private static void update(float dt) {
		c.processInput(dt, 5, .3f);
		w.updatePos(c.getTransform().getPos().getX(), c.getTransform().getPos().getY(), c.getTransform().getPos().getZ());
	}

 /**
  * renders a 3D model using a scene graph and uniform colors. It enables and disables
  * attributess, binds the scene graph, and draws the model using the Model's draw()
  * method.
  */
	private static void render() {
		
//		Model.enableAttribs();
		
		s.bind();
//		for (int i = 0; i < 4; i++)
//			for (int j = 0; j < 4; j++)
//				for (int k = 0; k < 4; k++) {
//					float r = (4 - i) / 4f;
//					float g = j / 4f;
//					float b = k / 4f;
//					s.uniformf("color", r, g, b);
//					s.unifromMat4("MVP", (c.getViewProjection().mul(ch[i][j][k].getModelMatrix())));
//					ch[i][j][k].getModel().draw();
//				}
		
		w.render(s, c);
		
//		Model.disableAttribs();
	}
	
 /**
  * terminates the current Java process with a specific exit status, which can be used
  * to indicate the reason for the termination.
  * 
  * @param status value to be passed to the `System.exit()` method, which terminates
  * the application and returns control to the operating system.
  */
	private static void exit(int status) {
		System.exit(status);
	}
}
