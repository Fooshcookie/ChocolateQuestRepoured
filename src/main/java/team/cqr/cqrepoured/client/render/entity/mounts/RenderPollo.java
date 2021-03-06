package team.cqr.cqrepoured.client.render.entity.mounts;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import team.cqr.cqrepoured.client.models.entities.ModelCQRPollo;
import team.cqr.cqrepoured.objects.mounts.EntityPollo;
import team.cqr.cqrepoured.util.Reference;

public class RenderPollo extends RenderLiving<EntityPollo> {

	static ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/mounts/pollo.png");

	public RenderPollo(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelCQRPollo(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPollo entity) {
		return TEXTURE;
	}

}
