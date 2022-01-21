package moe.plushie.armourers_workshop.client.model.skin;

import net.minecraft.entity.LivingEntity;
import org.lwjgl.opengl.GL11;

import moe.plushie.armourers_workshop.api.common.IExtraColours;
import moe.plushie.armourers_workshop.api.common.skin.data.ISkinDye;
import moe.plushie.armourers_workshop.api.common.skin.type.ISkinPartType;
import moe.plushie.armourers_workshop.client.model.ModelMannequin;
import moe.plushie.armourers_workshop.client.render.ModRenderHelper;
import moe.plushie.armourers_workshop.client.render.SkinPartRenderData;
import moe.plushie.armourers_workshop.client.render.SkinPartRenderer;
import moe.plushie.armourers_workshop.client.render.SkinRenderData;
import moe.plushie.armourers_workshop.common.skin.data.Skin;
import moe.plushie.armourers_workshop.common.skin.type.SkinTypeRegistry;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ModelTypeHelper extends BipedModel implements IEquipmentModel {

    public Skin npcSkinData = null;
    public ISkinDye npcDyeData = null;
    protected boolean slim;

    protected static float SCALE = 0.0625F;

    protected void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    protected void setRotation(ModelRenderer targetModel, ModelRenderer sourceModel) {
        targetModel.rotateAngleX = sourceModel.rotateAngleX;
        targetModel.rotateAngleY = sourceModel.rotateAngleY;
        targetModel.rotateAngleZ = sourceModel.rotateAngleZ;
    }

    @Override
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
        super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
        this.isRiding = false;
        this.isSneak = false;
        // this.aimedBow = false;
        this.isChild = false;
        this.slim = false;
        // this.heldItemRight = 0;
        bipedLeftLeg.rotateAngleZ = 0F;
        bipedRightLeg.rotateAngleZ = 0F;
        bipedHead.rotateAngleZ = 0F;
        bipedHeadwear.rotateAngleZ = 0F;
    }

    @Override
    public void render(Entity entity, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
        if (npcSkinData != null) {
            this.isRiding = false;
            this.isSneak = false;
            // this.aimedBow = false;
            this.isChild = false;
            this.slim = false;
            // this.heldItemRight = 0;
            if (entity instanceof LivingEntity) {
                /*
                 * if (((EntityLivingBase)entity).getHeldItem() != null) { this.heldItemRight =
                 * 1; }
                 */
                if (((LivingEntity) entity).isRiding()) {
                    this.isRiding = true;
                }
                if (((LivingEntity) entity).isSneaking()) {
                    this.isSneak = true;
                }
                if (((LivingEntity) entity).isChild()) {
                    this.isChild = true;
                }
            }
            bipedLeftLeg.rotateAngleZ = 0F;
            bipedRightLeg.rotateAngleZ = 0F;
            bipedHead.rotateAngleZ = 0F;
            bipedHeadwear.rotateAngleZ = 0F;

            super.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, entity);

            GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
            GL11.glEnable(GL11.GL_CULL_FACE);
            ModRenderHelper.enableAlphaBlend();
            render(entity, npcSkinData, false, npcDyeData, null, false, 0, true);
            ModRenderHelper.disableAlphaBlend();
            GL11.glPopAttrib();

            npcSkinData = null;
            npcDyeData = null;
        }
    }

    @Override
    public void render(Entity entity, Skin skin, BipedModel BipedModel, boolean showSkinPaint, ISkinDye skinDye, IExtraColours extraColours, boolean itemRender, double distance, boolean doLodLoading) {
        setRotationFromBipedModel(BipedModel);
        render(entity, skin, showSkinPaint, skinDye, extraColours, itemRender, distance, doLodLoading);
    }

    @Override
    public void render(Entity entity, Skin skin, BipedModel BipedModel, SkinRenderData renderData) {
        setRotationFromBipedModel(BipedModel);
        render(entity, skin, renderData);
    }

    @Override
    public void render(Entity entity, Skin skin, float limb1, float limb2, float limb3, float headY, float headX) {
        setRotationAngles(limb1, limb2, limb3, headY, headX, SCALE, entity);
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_CULL_FACE);
        ModRenderHelper.enableAlphaBlend();
        render(entity, skin, false, null, null, false, 0, true);
        ModRenderHelper.disableAlphaBlend();
        GL11.glPopAttrib();
    }

    public abstract void render(Entity entity, Skin skin, boolean showSkinPaint, ISkinDye skinDye, IExtraColours extraColours, boolean itemRender, double distance, boolean doLodLoading);

    public abstract void render(Entity entity, Skin skin, SkinRenderData renderData);

    protected void setRotationFromBipedModel(BipedModel BipedModel) {
        this.isRiding = false;
        this.isSneak = false;
        // this.aimedBow = false;
        // this.heldItemRight = 0;
        this.slim = false;
        if (BipedModel == null) {
            setRotation(bipedHead, 0F, 0F, 0F);
            setRotation(bipedBody, 0F, 0F, 0F);
            setRotation(bipedLeftArm, 0F, (float) Math.toRadians(-1F), (float) Math.toRadians(-5F));
            setRotation(bipedRightArm, 0F, (float) Math.toRadians(1F), (float) Math.toRadians(5F));
            setRotation(bipedLeftLeg, 0F, 0F, 0F);
            setRotation(bipedRightLeg, 0F, 0F, 0F);
            isChild = false;
        } else {
            if (BipedModel instanceof ModelPlayer) {
                ModelPlayer modelPlayer = (ModelPlayer) BipedModel;
                this.slim = modelPlayer.bipedLeftArm.rotationPointY == 2.5F;
            }
            setRotation(bipedHead, BipedModel.bipedHead);
            setRotation(bipedBody, BipedModel.bipedBody);
            setRotation(bipedLeftArm, BipedModel.bipedLeftArm);
            setRotation(bipedRightArm, BipedModel.bipedRightArm);
            setRotation(bipedLeftLeg, BipedModel.bipedLeftLeg);
            setRotation(bipedRightLeg, BipedModel.bipedRightLeg);
            isChild = BipedModel.isChild;
            if (BipedModel instanceof ModelMannequin) {
                this.slim = ((ModelMannequin) BipedModel).isSlim();
            }
        }
    }

    protected void renderPart(SkinPartRenderData partRenderData) {
        SkinPartRenderer.INSTANCE.renderPart(partRenderData);
    }

    protected boolean skinHasHead(Skin skin) {
        if (skin.getSkinType() == SkinTypeRegistry.skinHead) {
            return true;
        }
        if (skin.getSkinType() == SkinTypeRegistry.skinOutfit) {
            ISkinPartType headPart = SkinTypeRegistry.skinHead.getSkinParts().get(0);
            for (int i = 0; i < skin.getPartCount(); i++) {
                if (skin.getSubParts().get(i).getPartType() == headPart) {
                    return true;
                }
            }
        }
        return false;
    }
}
