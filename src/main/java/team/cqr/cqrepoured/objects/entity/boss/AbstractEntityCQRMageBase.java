package team.cqr.cqrepoured.objects.entity.boss;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import team.cqr.cqrepoured.init.CQRItems;
import team.cqr.cqrepoured.objects.entity.bases.AbstractEntityCQRBoss;

public abstract class AbstractEntityCQRMageBase extends AbstractEntityCQRBoss {

	private static final DataParameter<Boolean> IDENTITY_HIDDEN = EntityDataManager.<Boolean>createKey(AbstractEntityCQRMageBase.class, DataSerializers.BOOLEAN);

	public AbstractEntityCQRMageBase(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void entityInit() {
		super.entityInit();

		this.dataManager.register(IDENTITY_HIDDEN, true);
	}

	public void revealIdentity() {
		this.dataManager.set(IDENTITY_HIDDEN, false);
		this.bossInfoServer.setName(this.getDisplayName());
	}

	public boolean isIdentityHidden() {
		return this.dataManager.get(IDENTITY_HIDDEN);
	}

	@Override
	protected void damageEntity(DamageSource damageSrc, float damageAmount) {
		super.damageEntity(damageSrc, damageAmount);

		if (!this.world.isRemote && (this.getHealth() / this.getMaxHealth()) < 0.83F) {
			this.revealIdentity();
		}
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(CQRItems.STAFF_VAMPIRIC, 1));
		return super.onInitialSpawn(difficulty, livingdata);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setBoolean("identityHidden", this.isIdentityHidden());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if (!compound.getBoolean("identityHidden")) {
			this.revealIdentity();
		}
	}

	@Override
	public ITextComponent getDisplayName() {
		if (this.isIdentityHidden()) {
			return new TextComponentString("???");
		}
		return super.getDisplayName();
	}

	@Override
	public boolean canIgniteTorch() {
		return false;
	}

}
