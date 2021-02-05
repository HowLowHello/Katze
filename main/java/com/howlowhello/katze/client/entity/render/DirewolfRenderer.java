package com.howlowhello.katze.client.entity.render;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.client.entity.model.DirewolfModel;
import com.howlowhello.katze.entities.DirewolfEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class DirewolfRenderer extends MobRenderer<DirewolfEntity, DirewolfModel<DirewolfEntity>> {

    /** 指定材质的路径*/
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Katze.MOD_ID, "textures/entity/direwolf.png");

    public DirewolfRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new com.howlowhello.katze.client.entity.model.DirewolfModel<>(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(DirewolfEntity entity) {
        return TEXTURE;
    }{
    }
}
