package com.ch.voxel;

/**
 * represents a three-dimensional block with various properties, including position
 * (x, y, z) and flags for different states (ft, bk, tp, bt, lt, rt).
 * Fields:
 * 	- z (int): represents the block's depth in the voxel world.
 * 	- rt (boolean): in the Block class represents whether the block is right-clickable.
 */
public class Block {
	
	public int x, y, z;
	public boolean ft, bk, tp, bt, lt, rt;
	
	public Block(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.ft = false;
		this.bk = false;
		this.tp = false;
		this.bt = false;
		this.lt = false;
		this.rt = false;
	}

}
