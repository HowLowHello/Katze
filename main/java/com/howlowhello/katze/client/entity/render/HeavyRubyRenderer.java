package com.howlowhello.katze.client.entity.render;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.client.entity.model.HeavyRubyModel;
import com.howlowhello.katze.entities.HeavyRubyEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class HeavyRubyRenderer extends MobRenderer<HeavyRubyEntity, HeavyRubyModel<HeavyRubyEntity>> {

    /** 指定材质的路径*/
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Katze.MOD_ID, "textures/entity/heavy_ruby.png");

    public HeavyRubyRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new com.howlowhello.katze.client.entity.model.HeavyRubyModel<>(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(HeavyRubyEntity entity) {
        return TEXTURE;
    }{
    }
}
