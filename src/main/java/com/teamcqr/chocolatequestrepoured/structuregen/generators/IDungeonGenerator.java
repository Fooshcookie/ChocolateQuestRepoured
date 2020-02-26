package com.teamcqr.chocolatequestrepoured.structuregen.generators;

import java.util.ArrayList;
import java.util.List;

import com.teamcqr.chocolatequestrepoured.structuregen.DungeonGenerationHandler;
import com.teamcqr.chocolatequestrepoured.structuregen.IStructure;
import com.teamcqr.chocolatequestrepoured.structuregen.Structure;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

/**
 * Copyright (c) 29.04.2019
 * Developed by DerToaster98
 * GitHub: https://github.com/DerToaster98
 */
public interface IDungeonGenerator {

	void preProcess(World world, Chunk chunk, int x, int y, int z, List<List<? extends IStructure>> lists);

	void buildStructure(World world, Chunk chunk, int x, int y, int z, List<List<? extends IStructure>> lists);

	void postProcess(World world, Chunk chunk, int x, int y, int z, List<List<? extends IStructure>> lists);

	void fillChests(World world, Chunk chunk, int x, int y, int z, List<List<? extends IStructure>> lists);

	void placeSpawners(World world, Chunk chunk, int x, int y, int z, List<List<? extends IStructure>> lists);

	void placeCoverBlocks(World world, Chunk chunk, int x, int y, int z, List<List<? extends IStructure>> lists);

	default void generate(World world, Chunk chunk, int x, int y, int z) {
		/*
		 * int median = 0;
		 * int cant = 0;
		 * for(int iX = 0; iX < x; iX++) {
		 * for(int iZ = 0; iZ < z; iZ++) {
		 * int height = world.getTopSolidOrLiquidBlock(new BlockPos(iX, 0, iZ)).getY();
		 * median += height;
		 * cant++;
		 * }
		 * }
		 * y = median /cant;
		 */
		if (world.isRemote) {
			return;
		}

		Structure structure = new Structure(world);
		List<List<? extends IStructure>> lists = new ArrayList<>();

		this.preProcess(world, chunk, x, y, z, lists);
		this.buildStructure(world, chunk, x, y, z, lists);
		this.postProcess(world, chunk, x, y, z, lists);
		this.fillChests(world, chunk, x, y, z, lists);
		this.placeSpawners(world, chunk, x, y, z, lists);
		this.placeCoverBlocks(world, chunk, x, y, z, lists);

		for (List<? extends IStructure> list : lists) {
			structure.addList(list);
		}
		DungeonGenerationHandler.addStructure(world, structure);
	}

}
