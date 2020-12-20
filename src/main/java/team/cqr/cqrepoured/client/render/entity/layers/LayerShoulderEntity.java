package team.cqr.cqrepoured.client.render.entity.layers;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelParrot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.IAnimatable;
import team.cqr.cqrepoured.client.render.entity.RenderCQREntity;
import team.cqr.cqrepoured.client.render.entity.RenderPirateParrot;
import team.cqr.cqrepoured.objects.entity.bases.AbstractEntityCQR;
import team.cqr.cqrepoured.objects.entity.boss.EntityCQRPirateParrot;

public class LayerShoulderEntity<T extends AbstractEntityCQR & IAnimatable> extends AbstractLayerCQR<T> {

	protected RenderLivingBase<? extends EntityLivingBase> leftRenderer;
	private ModelBase leftModel;
	private ResourceLocation leftResource;
	private UUID leftUniqueId;
	private Class<?> leftEntityClass;
	protected RenderLivingBase<? extends EntityLivingBase> rightRenderer;

	public LayerShoulderEntity(RenderCQREntity<?> livingEntityRendererIn) {
		super(livingEntityRendererIn);
	}

	@Override
	public void doRenderLayer(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (entitylivingbaseIn.getLeftShoulderEntity() != null) {
			GlStateManager.enableRescaleNormal();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			NBTTagCompound nbttagcompound = entitylivingbaseIn.getLeftShoulderEntity();

			if (!nbttagcompound.isEmpty()) {
				LayerShoulderEntity<T>.DataHolder layerentityonshoulder$dataholder = this.renderEntityOnShoulder(entitylivingbaseIn, this.leftUniqueId, nbttagcompound, this.leftRenderer, this.leftModel, this.leftResource, this.leftEntityClass, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch,
						scale, true);
				this.leftUniqueId = layerentityonshoulder$dataholder.entityId;
				this.leftRenderer = layerentityonshoulder$dataholder.renderer;
				this.leftResource = layerentityonshoulder$dataholder.textureLocation;
				this.leftModel = layerentityonshoulder$dataholder.model;
				this.leftEntityClass = layerentityonshoulder$dataholder.clazz;
			}

			GlStateManager.disableRescaleNormal();
		}
	}

	private LayerShoulderEntity<T>.DataHolder renderEntityOnShoulder(T p_192864_1_, @Nullable UUID p_192864_2_, NBTTagCompound p_192864_3_, RenderLivingBase<? extends EntityLivingBase> p_192864_4_, ModelBase p_192864_5_, ResourceLocation p_192864_6_, Class<?> p_192864_7_, float p_192864_8_,
			float p_192864_9_, float p_192864_10_, float p_192864_11_, float p_192864_12_, float p_192864_13_, float p_192864_14_, boolean p_192864_15_) {
		if (p_192864_2_ == null || !p_192864_2_.equals(p_192864_3_.getUniqueId("UUID"))) {
			p_192864_2_ = p_192864_3_.getUniqueId("UUID");
			p_192864_7_ = EntityList.getClassFromName(p_192864_3_.getString("id"));

			if (p_192864_7_ == EntityCQRPirateParrot.class) {
				p_192864_4_ = new RenderPirateParrot(this.entityRenderer.getRenderManager());
				p_192864_5_ = new ModelParrot();
				p_192864_6_ = RenderPirateParrot.TEXTURE;
			}
		}

		p_192864_4_.bindTexture(p_192864_6_);
		GlStateManager.pushMatrix();
		float f = p_192864_1_.isSneaking() ? -1.3F : -1.5F;
		float f1 = p_192864_15_ ? 0.4F : -0.4F;
		GlStateManager.translate(f1, f, 0.0F);

		if (p_192864_7_ == EntityCQRPirateParrot.class) {
			p_192864_11_ = 0.0F;
		}

		p_192864_5_.setLivingAnimations(p_192864_1_, p_192864_8_, p_192864_9_, p_192864_10_);
		p_192864_5_.setRotationAngles(p_192864_8_, p_192864_9_, p_192864_11_, p_192864_12_, p_192864_13_, p_192864_14_, p_192864_1_);
		p_192864_5_.render(p_192864_1_, p_192864_8_, p_192864_9_, p_192864_11_, p_192864_12_, p_192864_13_, p_192864_14_);
		GlStateManager.popMatrix();
		return new LayerShoulderEntity.DataHolder(p_192864_2_, p_192864_4_, p_192864_5_, p_192864_6_, p_192864_7_);
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	class DataHolder {
		public UUID entityId;
		public RenderLivingBase<? extends EntityLivingBase> renderer;
		public ModelBase model;
		public ResourceLocation textureLocation;
		public Class<?> clazz;

		public DataHolder(UUID p_i47463_2_, RenderLivingBase<? extends EntityLivingBase> p_i47463_3_, ModelBase p_i47463_4_, ResourceLocation p_i47463_5_, Class<?> p_i47463_6_) {
			this.entityId = p_i47463_2_;
			this.renderer = p_i47463_3_;
			this.model = p_i47463_4_;
			this.textureLocation = p_i47463_5_;
			this.clazz = p_i47463_6_;
		}
	}

}
