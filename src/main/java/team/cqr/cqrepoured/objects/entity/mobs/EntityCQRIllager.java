package team.cqr.cqrepoured.objects.entity.mobs;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.AbstractIllager.IllagerArmPose;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.cqr.cqrepoured.factions.EDefaultFaction;
import team.cqr.cqrepoured.objects.entity.ECQREntityArmPoses;
import team.cqr.cqrepoured.objects.entity.bases.AbstractEntityCQR;
import team.cqr.cqrepoured.util.CQRConfig;
import team.cqr.cqrepoured.util.IRangedWeapon;

public class EntityCQRIllager extends AbstractEntityCQR {

	private static final DataParameter<Boolean> IS_AGGRESSIVE = EntityDataManager.<Boolean>createKey(EntityCQRIllager.class, DataSerializers.BOOLEAN);

	public EntityCQRIllager(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void entityInit() {
		super.entityInit();

		this.dataManager.register(IS_AGGRESSIVE, false);
	}

	@Override
	public void onEntityUpdate() {
		if (!this.world.isRemote) {
			if (this.getAttackTarget() != null && !this.dataManager.get(IS_AGGRESSIVE)) {
				this.dataManager.set(IS_AGGRESSIVE, true);
				this.setArmPose(ECQREntityArmPoses.HOLDING_ITEM);
			} else if (this.getAttackTarget() == null) {
				this.dataManager.set(IS_AGGRESSIVE, false);
				this.setArmPose(ECQREntityArmPoses.NONE);
			}
		}
		super.onEntityUpdate();
	}

	@Override
	public float getBaseHealth() {
		return CQRConfig.baseHealths.Illager;
	}

	@Override
	public EDefaultFaction getDefaultFaction() {
		return EDefaultFaction.ILLAGERS;
	}

	@Override
	protected ResourceLocation getLootTable() {
		return LootTableList.ENTITIES_VINDICATION_ILLAGER;
	}

	@Override
	public int getTextureCount() {
		return 2;
	}

	public boolean isAggressive() {
		if (!this.world.isRemote) {
			return this.getAttackTarget() != null;
		}
		return this.dataManager.get(IS_AGGRESSIVE);
	}

	@SideOnly(Side.CLIENT)
	public IllagerArmPose getIllagerArmPose() {
		if (this.isAggressive()) {
			if (this.isSpellCharging() && this.isSpellAnimated()) {
				return IllagerArmPose.SPELLCASTING;
			}

			Item active = this.getActiveItemStack().getItem();
			if (active instanceof IRangedWeapon || active instanceof ItemBow) {
				return IllagerArmPose.BOW_AND_ARROW;
			}
			return IllagerArmPose.ATTACKING;
		}
		return IllagerArmPose.CROSSED;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ILLAGER;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.VINDICATION_ILLAGER_AMBIENT;
	}

	@Override
	protected SoundEvent getDefaultHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_VINDICATION_ILLAGER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ILLAGER_DEATH;
	}

}
