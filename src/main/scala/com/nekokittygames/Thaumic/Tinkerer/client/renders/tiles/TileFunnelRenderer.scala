package com.nekokittygames.Thaumic.Tinkerer.client.renders.tiles

import com.nekokittygames.Thaumic.Tinkerer.common.tiles.TileFunnel
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.texture.{TextureMap, TextureAtlasSprite}
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11
import thaumcraft.api.aspects.Aspect
import thaumcraft.api.blocks.BlocksTC
import thaumcraft.client.lib.RenderBlocks
import thaumcraft.client.renderers.models.block.ModelJar
import thaumcraft.client.renderers.tile.TileJarRenderer
import thaumcraft.common.blocks.devices.BlockJarItem

/**
 * Created by Katrina on 20/05/2015.
 */
class TileFunnelRenderer extends TileEntitySpecialRenderer{


  var jarRenderer:TileJarRenderer=new TileJarRenderer()
  final val TEX_LABEL:ResourceLocation  = new ResourceLocation("thaumcraft", "textures/models/label.png");
  final val TEX_BRINE:ResourceLocation  = new ResourceLocation("thaumcraft", "textures/models/jarbrine.png");
  override def renderTileEntityAt(te: TileEntity, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int): Unit =
  {
    val funnel:TileFunnel=te.asInstanceOf[TileFunnel]
    if(funnel.inventory!=null && funnel.inventory.getItem!=null && funnel.inventory.getItem.asInstanceOf[BlockJarItem].getAspects(funnel.inventory).size()>0) {
      GL11.glPushMatrix();
      //GL11.glTranslated(x + 0.5, y + 0.365, z + 0.5);
      val amount:Int=funnel.inventory.getItem.asInstanceOf[BlockJarItem].getAspects(funnel.inventory).getAmount(funnel.inventory.getItem.asInstanceOf[BlockJarItem].getAspects(funnel.inventory).getAspects()(0))
      val aspect:Aspect=funnel.inventory.getItem.asInstanceOf[BlockJarItem].getAspects(funnel.inventory).getAspects()(0)
      renderTCJar(x.toFloat,y.toFloat,z.toFloat,amount,aspect)

      GL11.glPopMatrix()
    }
  }




  def renderTCJar(x:Float,y:Float,z:Float,amount:Int,aspect:Aspect):Unit=
  {
    GL11.glPushMatrix();
    GL11.glDisable(2884);
    GL11.glTranslatef(x + 0.5F, y + 0.1F, z + 0.5F);
    GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glDisable(2896);
      if(amount > 0) {
        this.renderLiquid(x, y, z, amount,aspect);
      }

      GL11.glEnable(2896);

    GL11.glEnable(2884);
    GL11.glPopMatrix();
}
  def renderLiquid(x: Float, y: Float, z: Float, amount: Int, aspect: Aspect) =
  {
    GL11.glPushMatrix();
    GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
    val renderBlocks:RenderBlocks = new RenderBlocks();
    GL11.glDisable(2896);
    val level:Float = amount / 64 * 0.625F;
    val t:Tessellator = Tessellator.getInstance();
    renderBlocks.setRenderBounds(0.25D, 0.0625D, 0.25D, 0.75D, 0.0625D + level, 0.75D);
    t.getWorldRenderer().startDrawingQuads();
    if(aspect != null) {
      t.getWorldRenderer().setColorOpaque_I(aspect.getColor());
    }

    t.getWorldRenderer().setBrightness(200);
    val icon:TextureAtlasSprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("thaumcraft:blocks/animatedglow");
    this.bindTexture(TextureMap.locationBlocksTexture);
    renderBlocks.renderFaceYNeg(BlocksTC.jar, -0.5D, 0.0D, -0.5D, icon);
    renderBlocks.renderFaceYPos(BlocksTC.jar, -0.5D, 0.0D, -0.5D, icon);
    renderBlocks.renderFaceZNeg(BlocksTC.jar, -0.5D, 0.0D, -0.5D, icon);
    renderBlocks.renderFaceZPos(BlocksTC.jar, -0.5D, 0.0D, -0.5D, icon);
    renderBlocks.renderFaceXNeg(BlocksTC.jar, -0.5D, 0.0D, -0.5D, icon);
    renderBlocks.renderFaceXPos(BlocksTC.jar, -0.5D, 0.0D, -0.5D, icon);
    t.draw();
    GL11.glEnable(2896);
    GL11.glPopMatrix();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
  }

}
