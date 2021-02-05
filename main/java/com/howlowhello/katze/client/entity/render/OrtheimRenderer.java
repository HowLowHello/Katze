package com.howlowhello.katze.client.entity.render;

import com.howlowhello.katze.Katze;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.ResourceLocation;

public class OrtheimRenderer extends ZombieRenderer {

    /** 指定材质的路径*/


    protected static final ResourceLocation TEXTURE = new ResourceLocation(Katze.MOD_ID, "textures/entity/ortheim.png");

    public OrtheimRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(ZombieEntity entity) {
        return TEXTURE;
    }
}
