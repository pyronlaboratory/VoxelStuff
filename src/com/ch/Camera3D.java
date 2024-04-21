package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an extension of the Camera Class that adds additional functionality for
 * manipulating the camera's position and orientation in a 3D environment. It takes
 * in various parameters such as field of view, aspect ratio, near and far distances,
 * and adjusts its projection matrix accordingly. The class also includes methods for
 * processing input from the keyboard and mouse, and for moving the camera based on
 * those inputs.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

 /**
  * calculates a 4x4 matrix representing the camera's projection transformation, based
  * on the provided `CameraStruct` data.
  * 
  * @param data 3D camera data that contains information necessary to calculate the
  * projection matrix.
  * 
  * 1/ `getAsMatrix4()` - This method returns a `Matrix4f` object containing the
  * camera's projection matrix in column-major order.
  * 2/ `projection` - The variable `projection` is assigned the result of calling `getAsMatrix4()`.
  * 
  * @returns a Matrix4f object containing the projection matrix.
  * 
  * 1/ The output is a `Matrix4f` object, which represents a 4D array of floating-point
  * values that define a linear transformation matrix in homogeneous coordinate space.
  * 2/ The matrix contains elements that correspond to the projection parameters defined
  * by the `CameraStruct` data passed into the function. These elements include the
  * focal length, principal point, and other parameters used to project a 3D scene
  * onto a 2D image plane.
  * 3/ The matrix can be used to perform various geometric transformations on 3D points
  * and vectors, such as perspective projection, zooming, and shearing.
  */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

 /**
  * adjusts the camera's projection and view matrices to fit within a specified width
  * and height boundary. It updates the aspect ratio of the camera, calculates the
  * projection matrix, and sets the view matrix.
  * 
  * @param width 2D viewport width.
  * 
  * @param height 2D viewport dimensions, which are used to calculate the camera's
  * projection and view matrices and to set the `GL11.glViewport()` call.
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
  * is a subclass of the CameraStruct class, with additional fields for fov, aspect,
  * zNear, and zFar. The getAsMatrix4() method returns a Matrix4f object initialized
  * with a perspective projection matrix based on the provided values.
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
   * returns a `Matrix4f` object initialized with a perspective projection matrix based
   * on input values for field of view (`fov`), aspect ratio, near and far distances.
   * 
   * @returns a matrix representation of a perspective projection, where the field of
   * view (fov), aspect ratio, near and far distances are provided as input.
   * 
   * The method returns a `Matrix4f` object representing a 4x4 homogeneous transformation
   * matrix, which is used in computer graphics to perform perspective projection.
   * 
   * The `fov` parameter represents the field of view angle in radians, while `aspect`
   * is the aspect ratio of the viewport. The `zNear` and `zFar` parameters represent
   * the near and far clipping planes, respectively.
   * 
   * The returned matrix is initialized with these values to create a perspective
   * projection matrix that can be used for rendering 3D scenes from various viewpoints.
   */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

 /**
  * processes mouse input and keyboard input to move a transform in 3D space based on
  * sensitivity and shift key modifier.
  * 
  * @param dt time step of the simulation, which is multiplied with the speed to
  * determine the amount of movement in the update loop.
  * 
  * @param speed 3D movement speed of the object being manipulated by the function,
  * which is multiplied by the time interval `dt` to determine the total distance
  * traveled during that time.
  * 
  * @param sens sensitivity of the object's movement to mouse input, and it affects
  * the amount of rotation applied to the object's transform based on the mouse position.
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
  * moves the object by a specified distance along a given direction, applying the
  * transformation to the object's position.
  * 
  * @param dir 3D direction in which the object should be moved, with its magnitude
  * determined by the `amt` parameter.
  * 
  * 	- `dir`: A Vector3f object representing the direction of movement in 3D space.
  * It has three components - x, y, and z, which represent the amount of movement in
  * each dimension.
  * 
  * @param amt amount of movement to be applied to the object's position, as measured
  * along the specified direction.
  */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
