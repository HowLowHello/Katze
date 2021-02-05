package com.howlowhello.katze.client.entity.render;

import com.howlowhello.katze.Katze;
import com.howlowhello.katze.client.entity.model.PlayerIsraDynameModel;
import com.howlowhello.katze.entities.PlayerIsraDynameEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class PlayerIsraDynameRenderer extends MobRenderer<PlayerIsraDynameEntity, PlayerIsraDynameModel<PlayerIsraDynameEntity>> {

    /** 指定材质的路径*/
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Katze.MOD_ID, "textures/entity/player_isra_dyname.png");

    public PlayerIsraDynameRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new com.howlowhello.katze.client.entity.model.PlayerIsraDynameModel<>(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(PlayerIsraDynameEntity entity) {
        return TEXTURE;
    }{
    }
}
