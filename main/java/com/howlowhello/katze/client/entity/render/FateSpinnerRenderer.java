package com.howlowhello.katze.client.entity.render;

import com.howlowhello.katze.Katze;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.util.ResourceLocation;

public class FateSpinnerRenderer extends EndermanRenderer {

    /** 指定材质的路径*/
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Katze.MOD_ID, "textures/entity/fate_spinner.png");

    public FateSpinnerRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(EndermanEntity entity) {
        return TEXTURE;
    }

}
