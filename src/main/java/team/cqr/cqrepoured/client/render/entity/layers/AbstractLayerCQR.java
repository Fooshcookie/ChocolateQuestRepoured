package team.cqr.cqrepoured.client.render.entity.layers;

import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import team.cqr.cqrepoured.client.render.entity.RenderCQREntity;
import team.cqr.cqrepoured.objects.entity.bases.AbstractEntityCQR;

public abstract class AbstractLayerCQR<T extends AbstractEntityCQR & IAnimatable> extends GeoLayerRenderer<T> {

	protected final RenderCQREntity<?> entityRenderer;

	//TODO: Adapt the base cqr renderer
	public AbstractLayerCQR(RenderCQREntity<?> livingEntityRendererIn) {
		this.entityRenderer = livingEntityRendererIn;
	}

	
	@Override
	public void render(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		
	}
}
