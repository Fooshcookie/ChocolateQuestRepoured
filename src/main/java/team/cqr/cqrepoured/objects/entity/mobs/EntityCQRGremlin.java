package team.cqr.cqrepoured.objects.entity.mobs;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import team.cqr.cqrepoured.factions.EDefaultFaction;
import team.cqr.cqrepoured.init.CQRLoottables;
import team.cqr.cqrepoured.objects.entity.bases.AbstractEntityCQR;
import team.cqr.cqrepoured.util.CQRConfig;

public class EntityCQRGremlin extends AbstractEntityCQR {

	public EntityCQRGremlin(World worldIn) {
		super(worldIn);
	}

	@Override
	public float getBaseHealth() {
		return CQRConfig.baseHealths.Gremlin;
	}

	@Override
	public EDefaultFaction getDefaultFaction() {
		return EDefaultFaction.GREMLINS;
	}

	@Override
	protected ResourceLocation getLootTable() {
		return CQRLoottables.ENTITIES_GREMLIN;
	}

	@Override
	public boolean canMountEntity() {
		return false;
	}

	@Override
	public boolean isSitting() {
		return false;
	}

	@Override
	public boolean canOpenDoors() {
		return false;
	}

	@Override
	public boolean canIgniteTorch() {
		return false;
	}

	@Override
	public boolean canTameEntity() {
		return false;
	}

	@Override
	public float getEyeHeight() {
		return this.height * 0.7F;
	}

	@Override
	public float getDefaultWidth() {
		return 0.9F;
	}

	@Override
	public float getDefaultHeight() {
		return 1.2F;
	}

}
