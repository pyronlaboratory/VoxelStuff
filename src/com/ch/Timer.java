package com.ch;

import org.lwjgl.Sys;

/**
 * is a Java class that provides functions for calculating and tracking frame rate
 * (FPS) and time. The class has several public static methods: init(), calculateDelta(),
 * updateFPS(), getDelta(), getFPS(), and update(). These methods are used to initialize
 * the frame rate counter, calculate the delta time between frames, update the frame
 * rate counter, retrieve the current delta value, retrieve the current FPS value,
 * and update the time variable respectively. The class also has several instance
 * variables: fps, lastFPS, lastFrame, time, and delta.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

 /**
  * calculates the seconds since the epoch (January 1, 1970, 00:00:00 UTC) based on
  * the current system time and timer resolution.
  * 
  * @returns a long value representing the current time in milliseconds, calculated
  * from the system's timer resolution.
  */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

 /**
  * initializes a variable `lastFPS` with the current time taken to execute a certain
  * task.
  */
	public static void init() {
		lastFPS = getTimeS();
	}

 /**
  * calculates the time difference between two points, measured in milliseconds, and
  * returns the result as a float value.
  * 
  * @returns a floating-point value representing the time difference between two frames.
  */
	private static float calculateDelta() {
		long time = getTimeS();
		float delta = (int) (time - lastFrame);
		lastFrame = getTimeS();
		return delta;
	}

 /**
  * updates the frame rate by incrementing a counter every second, storing the current
  * frame rate in a variable, and resetting the counter to zero when it reaches a
  * certain threshold.
  */
	private static void updateFPS() {
		if (getTimeS() - lastFPS > 1000) {
			currentFPS = fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

 /**
  * returns the value of the `delta` field.
  * 
  * @returns a floating-point value representing the delta.
  */
	public static float getDelta() {
		return delta;
	}

 /**
  * returns the current frames per second (FPS) metric for the application.
  * 
  * @returns the current frames per second (FPS) measurement.
  */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * retrieves the value of the `time` variable, which is a floating-point number
     * representing the current time.
     * 
     * @returns a floating-point representation of the current time.
     */
    public static float getTime() {
        return time;
    }

    /**
     * updates FPS, calculates and sets a delta value, and adds it to a time variable.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
