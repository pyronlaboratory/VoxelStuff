package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is responsible for managing camera functionality in a 3D environment. It provides
 * methods to create and manipulate matrices for perspective projection, as well as
 * handling input from the mouse and keyboard to rotate and move objects. Additionally,
 * it has a processInput method that applies movements based on time and keyboard
 * input, while also scaling speed based on the LShift key.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Takes a `CameraStruct` object as input and returns the projection matrix in Matrix4f
	 * format.
	 * 
	 * @param data 3D camera's transformation matrix as a `Matrix4f` object, which is
	 * then assigned to the function's return value, a `Matrix4f` object named `projection`.
	 * 
	 * @returns a Matrix4f object containing the projection matrix.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts the camera's projection and view matrices to match the specified viewport
	 * size, ensuring proper aspect ratio and viewport positioning.
	 * 
	 * @param width 2D viewport width.
	 * 
	 * @param height 2D viewport size of the camera's view, which is used to calculate
	 * the appropriate projection and view matrices for rendering the 3D scene in the
	 * correct aspect ratio.
	 */
	@Override
	public void adjustToViewport(int width, int height) {
		((CameraStruct3D) this.values).aspect = (float) width / height;
		calculateProjectionMatrix(values);
		try {
			calculateViewMatrix();
		} catch (NullPointerException e) {
		}
		GL11.glViewport(0, 0, width, height);
	}

	/**
	 * Defines a class for representing a 3D camera's perspective projection. It has
	 * parameters such as Field of View (fov), Aspect Ratio, Near Distance (zNear), and
	 * Far Distance (zFar). The class provides a method to initialize a matrix representing
	 * the perspective projection, which is used to transform 3D points from the object
	 * space to the image space.
	 */
	protected class CameraStruct3D extends CameraStruct {

		public float fov, aspect, zNear, zFar;

		public CameraStruct3D(float fov, float aspect, float zNear, float zFar) {
			this.fov = fov;
			this.aspect = aspect;
			this.zNear = zNear;
			this.zFar = zFar;
		}

		/**
		 * Generates a 4x4 matrix representing a perspective projection, with fields for field
		 * of view (fov), aspect ratio, near and far distances.
		 * 
		 * @returns a Matrix4f object representing a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Processes mouse and keyboard input to move an object based on its rotation and
	 * position. It rotates the object based on the mouse drag and applies linear motion
	 * based on keyboard inputs.
	 * 
	 * @param dt time interval for which the code is executed, and it is used to calculate
	 * the movement of the object based on the keyboard inputs.
	 * 
	 * @param speed 3D movement speed of the object being controlled by the program, and
	 * it is multiplied by the time elapsed (represented by `dt`) to determine the total
	 * distance traveled during the frame.
	 * 
	 * @param sens sensitivity of the character's movement, which determines how much the
	 * character will move in response to the user's input.
	 */
	public void processInput(float dt, float speed, float sens) {

		float dx = Mouse.getDX();
		float dy = Mouse.getDY();
		float roty = (float)Math.toRadians(dx * sens);
		getTransform().rotate(new Vector3f(0, 1, 0), (float) roty);
		getTransform().rotate(getTransform().getRot().getRight(), (float) -Math.toRadians(dy * sens));
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			speed *= 10;
		
		float movAmt = speed * dt;

		if (Keyboard.isKeyDown(Keyboard.KEY_W))
			move(getTransform().getRot().getForward(), movAmt);
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
			move(getTransform().getRot().getForward(), -movAmt);
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
			move(getTransform().getRot().getLeft(), movAmt);
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
			move(getTransform().getRot().getRight(), movAmt);
		
	}

	/**
	 * Moves the object by a specified amount in a given direction, using the transform's
	 * position property to update the new position.
	 * 
	 * @param dir 3D direction to move the object in the specified amount of time, as
	 * measured by the `amt` parameter.
	 * 
	 * @param amt amount of movement along the specified direction, which is added to the
	 * current position of the object represented by the `getTransform()` method.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
