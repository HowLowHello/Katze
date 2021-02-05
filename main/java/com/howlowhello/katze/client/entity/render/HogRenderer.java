package com.howlowhello.katze.client.entity.render;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.client.entity.model.HogModel;
import com.howlowhello.katze.entities.HogEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class HogRenderer extends MobRenderer<HogEntity, HogModel<HogEntity>> {

    /** 指定材质的路径*/
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Katze.MOD_ID, "textures/entity/hog.png");

    public HogRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new com.howlowhello.katze.client.entity.model.HogModel<>(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(HogEntity entity) {
        return TEXTURE;
    }
}
