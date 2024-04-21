package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an extension of the Camera Class and provides additional functionality for a
 * 3D camera. It takes in parameters for field of view, aspect ratio, near and far
 * z-coordinates, and calculates the projection matrix accordingly. The class also
 * includes methods for adjusting to the viewport size and processing input from the
 * mouse and keyboard.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * calculates a projection matrix based on the provided `CameraStruct` data and returns
	 * it as a `Matrix4f` object.
	 * 
	 * @param data 3D camera's projection matrix as a Matrix4f object, which is then
	 * assigned to the function's return value.
	 * 
	 * 	- `projection`: This is the projection matrix that is being calculated and returned
	 * by the function. It is a 4x4 matrix.
	 * 	- `getAsMatrix4()`: This is the method called on the `data` object to retrieve
	 * the projection matrix as a 4x4 matrix.
	 * 
	 * @returns a Matrix4f object representing the projection matrix.
	 * 
	 * 	- The output is a `Matrix4f` object representing the perspective projection matrix.
	 * 	- The matrix contains 16 elements that determine the shape and size of the projected
	 * image, including the position, rotation, and scaling of the camera in the world
	 * coordinate system.
	 * 	- The matrix is constructed using the `getAsMatrix4()` method of the `CameraStruct`
	 * class, which retrieves the projection matrix from the `data` parameter.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * adjusts the camera's projection and view matrices to fit within a specified viewport
	 * size, based on the aspect ratio of the viewport.
	 * 
	 * @param width 2D viewport width of the display device, which is used to calculate
	 * the appropriate projection and view matrices for rendering the 3D scene.
	 * 
	 * @param height 2D viewport size of the renderer and is used to calculate the
	 * appropriate projection matrix for rendering 3D objects within the viewport.
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
	 * is a subclass of CameraStruct that represents a 3D camera. It has additional fields
	 * for fov, aspect, zNear, and zFar, which are used to calculate the camera's projection
	 * matrix. The class also provides a method to get the camera's perspective matrix
	 * as a Matrix4f object.
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
		 * returns a `Matrix4f` object representing a perspective projection matrix with field
		 * of view (fov), aspect ratio, near and far distances.
		 * 
		 * @returns a matrix representation of a perspective projection, initialized with the
		 * specified field of view, aspect ratio, near and far z-values.
		 * 
		 * 	- The `Matrix4f` object represents a 4x4 matrix that contains the perspective
		 * projection transformation.
		 * 	- The `fov`, `aspect`, `zNear`, and `zFar` parameters are used to initialize the
		 * matrix with a perspective projection.
		 * 	- The matrix is initialized using the `initPerspective` method of the `Matrix4f`
		 * class, which sets the matrix's elements based on the provided values.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * processes input events from the mouse and keyboard, adjusting the position and
	 * rotation of an object based on user input. It also multiplies the speed of movement
	 * by a factor determined by the L shift key.
	 * 
	 * @param dt time step or elapsed time since the last iteration of the function, which
	 * is used to compute and apply the movement speed modulation based on the pressed keys.
	 * 
	 * @param speed 3D movement speed of the object being controlled, which is multiplied
	 * by the time increment `dt` to determine the total distance moved.
	 * 
	 * @param sens sensitivity of the object's rotation to mouse input, and it affects
	 * the amount of rotation applied to the object when the user moves the mouse cursor.
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
	 * takes a direction vector `dir` and a scaling factor `amt`, and moves the position
	 * of an entity by applying the given translation to its current position.
	 * 
	 * @param dir 3D direction to move the object in the specified amount.
	 * 
	 * 	- `dir` is a `Vector3f` object representing a 3D vector with x, y, and z components.
	 * 	- The `mul()` method is used to multiply the `dir` vector by a scalar value `amt`,
	 * which represents the amount of movement in the specified direction.
	 * 
	 * The function then uses the `setPos()` method of the transform object to move the
	 * position of the element represented by the `getTransform()` method by adding the
	 * multiplied `dir` vector to the existing position.
	 * 
	 * @param amt amount of movement along the specified direction, which is added to the
	 * current position of the transform.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
