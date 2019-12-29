package com.teamcqr.chocolatequestrepoured.objects.items.staves;

import com.teamcqr.chocolatequestrepoured.util.IRangedWeapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemStaffWind extends Item  implements IRangedWeapon{

	public ItemStaffWind() {
		setMaxDamage(2048);
		setMaxStackSize(1);
	}

	@Override
	public void shoot(World worldIn, EntityLivingBase shooter, Entity target, EnumHand handIn) {
		// TODO Auto-generated method stub
		
	}

}