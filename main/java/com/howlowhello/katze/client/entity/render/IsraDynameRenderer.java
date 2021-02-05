package com.howlowhello.katze.client.entity.render;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.client.entity.model.IsraDynameModel;
import com.howlowhello.katze.entities.IsraDynameEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class IsraDynameRenderer extends MobRenderer<IsraDynameEntity, IsraDynameModel<IsraDynameEntity>> {

    /** 指定材质的路径*/
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Katze.MOD_ID, "textures/entity/isra_dyname.png");

    public IsraDynameRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new com.howlowhello.katze.client.entity.model.IsraDynameModel<>(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(IsraDynameEntity entity) {
        return TEXTURE;
    }{
    }
}
