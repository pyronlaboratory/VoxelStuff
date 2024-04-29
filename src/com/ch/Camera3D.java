package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an extension of the Matrix4f class and provides methods for creating and modifying
 * perspective projection matrices. It also includes methods for processing input
 * events from the mouse and keyboard, adjusting the position and rotation of an
 * object based on user input. Additionally, it has a move() method that moves the
 * position of an entity by applying a translation to its current position in a 3D space.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * calculates and returns a Matrix4f object representing the projection matrix as
	 * defined by the input `CameraStruct` data.
	 * 
	 * @param data 3D camera data required to calculate the perspective projection matrix.
	 * 
	 * 1/ `getAsMatrix4()` - This method returns a `Matrix4f` object representing the
	 * projection matrix as defined by the `data` variable.
	 * 
	 * @returns a Matrix4f object representing the projection matrix.
	 * 
	 * The `Matrix4f` object returned by the function represents the projection matrix
	 * for the given camera data. Specifically, it encodes the intrinsic and extrinsic
	 * parameters of the camera in a 4x4 homogeneous transformation matrix. The matrix
	 * elements represent the distortion coefficients, aspect ratio, and other camera-specific
	 * properties that are used to project 3D points from the world coordinates into image
	 * coordinates.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * adjusts the camera's projection and view matrices to fit within the given viewport
	 * dimensions, and sets the viewport size to the specified width and height.
	 * 
	 * @param width 2D viewport width for which the `adjustToViewport()` method is called.
	 * 
	 * @param height 2D viewport size in pixels and is used to calculate the aspect ratio
	 * of the 3D scene and to set the viewport dimensions in the GL11 glViewport method.
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
	 * is a custom class that extends the CameraStruct class and provides additional
	 * functionality for representing a camera in a 3D environment. It takes in the field
	 * of view (fov), aspect ratio, near distance (zNear), and far distance (zFar) as
	 * parameters in its constructor, and uses these values to initialize a matrix
	 * representation of a perspective projection. This matrix can then be used to transform
	 * 3D points and positions using the `getAsMatrix4()` method. Additionally, the class
	 * provides methods for processing input events from the mouse and keyboard, which
	 * can be used to adjust the position and rotation of an object based on user input.
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
		 * Initializes a matrix that represents a perspective projection, with the specified
		 * field of view (fov), aspect ratio, near and far distances.
		 * 
		 * @returns a matrix representing a perspective projection, with values computed based
		 * on the provided fov, aspect, zNear, and zFar parameters.
		 * 
		 * 	- The return value is a `Matrix4f` object representing a 4x4 matrix, which contains
		 * the perspective projection parameters in its elements.
		 * 	- The matrix's entries are determined by the input parameters `fov`, `aspect`,
		 * `zNear`, and `zFar`. Specifically, the elements of the matrix are:
		 * 	+ `m00` = 2 / (tan(fov/2) * zNear)
		 * 	+ `m11` = 1 / (tan(fov/2) * zNear)
		 * 	+ `m20` = 0
		 * 	+ `m21` = 0
		 * 	+ `m30` = 0
		 * 	+ `m31` = -2 / (zFar - zNear)
		 * 	- The matrix is used to transform 3D points from the object space to the image
		 * space, applying the perspective projection.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * rotates and moves a transform based on mouse input, keyboard input, and time. It
	 * also scales the speed based on the LShift key.
	 * 
	 * @param dt time step of the simulation, which determines the amount of movement
	 * performed by the entity.
	 * 
	 * @param speed 3D movement speed of the object being controlled by the code, and its
	 * value is multiplied by the time interval `dt` to determine the total distance moved
	 * during each frame.
	 * 
	 * @param sens sensitivity of the object's rotation to the mouse input, and it affects
	 * how quickly the object rotates in response to mouse movements.
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
	 * moves an object's position by a specified distance along a given direction, applying
	 * the transformation to the object's position.
	 * 
	 * @param dir 3D direction in which the object should be moved, with the movement
	 * amount determined by the `amt` parameter.
	 * 
	 * 	- `dir` is a `Vector3f` instance representing a 3D direction vector.
	 * 	- `amt` is an instance of a floating-point number indicating the distance to move
	 * along the specified direction.
	 * 
	 * @param amt amount of movement along the specified direction, which is added to the
	 * current position of the transform.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
