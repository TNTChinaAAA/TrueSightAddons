/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.labymod.addons.truesight.v1_8_9;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class BlockUtils {

  /**
   * Get block from [blockPos]
   */
  public static Block getBlock(BlockPos blockPos) {
    return getState(blockPos) != null ? getState(blockPos).getBlock() : null;
  }

  /**
   * Get material from [blockPos]
   */
  public static Material getMaterial(BlockPos blockPos) {
    return getState(blockPos) != null ? getState(blockPos).getBlock().getMaterial() : null;
  }

  /**
   * Check [blockPos] is replaceable
   */
  public static boolean isReplaceable(BlockPos blockPos) {
    Material material = getMaterial(blockPos);
    return material != null && material.isReplaceable();
  }

  /**
   * Get state from [blockPos]
   */
  public static IBlockState getState(BlockPos blockPos) {
    Minecraft mc = Minecraft.getMinecraft();
    return mc.theWorld != null ? mc.theWorld.getBlockState(blockPos) : null;
  }

  /**
   * Check if [blockPos] is clickable
   */
  public static boolean canBeClicked(BlockPos blockPos) {
    Minecraft mc = Minecraft.getMinecraft();
    IBlockState state = getState(blockPos);
    if (state == null)
      return false;
    Block block = state.getBlock();

    return block.canCollideCheck(state, false) && mc.theWorld.getWorldBorder().contains(blockPos)
        && !block.getMaterial().isReplaceable() && !block.hasTileEntity()
        && isFullBlock(blockPos, state, true)
        && mc.theWorld.loadedEntityList.stream().noneMatch(
        entity -> entity instanceof EntityFallingBlock && entity.getPosition().equals(blockPos))
        && !(block instanceof BlockContainer) && !(block instanceof BlockWorkbench);
  }

  /**
   * Get block name by [id]
   */
  public static String getBlockName(int id) {
    return Block.getBlockById(id).getLocalizedName();
  }

  /**
   * Check if block is full block
   */
  public static boolean isFullBlock(BlockPos blockPos, IBlockState blockState,
      boolean supportSlabs) {
    Minecraft mc = Minecraft.getMinecraft();

    IBlockState state = blockState != null ? blockState : getState(blockPos);
    if (state == null)
      return false;

    AxisAlignedBB box = state.getBlock().getCollisionBoundingBox(mc.theWorld, blockPos, state);
    if (box == null)
      return false;

    // The slab will only return true if it's placed at a level that can be placed like any normal full block
    return box.maxX - box.minX == 1.0 && (box.maxY - box.minY == 1.0
        || supportSlabs && box.maxY % 1.0 == 0.0) && box.maxZ - box.minZ == 1.0;
  }

  public static boolean isFullBlock(Block block) {
    if (block instanceof BlockSoulSand)
      return false;
    if (block instanceof BlockGlass || block instanceof BlockStainedGlass)
      return true;

    // Many translucent or non-full blocks have blockBounds set to 1.0
    return block.isFullBlock() && block.isBlockNormalCube()
        && block.getBlockBoundsMaxX() == 1.0 && block.getBlockBoundsMaxY() == 1.0
        && block.getBlockBoundsMaxZ() == 1.0;
  }

  /**
   * Get distance to center of [blockPos]
   */
  public static double getCenterDistance(BlockPos blockPos) {
    Minecraft mc = Minecraft.getMinecraft();
    return mc.thePlayer.getDistance(blockPos.getX() + 0.5, blockPos.getY() + 0.5,
        blockPos.getZ() + 0.5);
  }

  /**
   * Search a limited amount [maxBlocksLimit] of specific blocks [targetBlocks] around the player in a specific [radius].
   * If [targetBlocks] is null it searches every block
   **/
  public static Map<BlockPos, Block> searchBlocks(int radius, Set<Block> targetBlocks, int maxBlocksLimit) {
    Minecraft mc = Minecraft.getMinecraft();

    Map<BlockPos, Block> blocks = new HashMap<>();

    if (mc.thePlayer == null) return blocks;

    for (int x = radius; x >= -radius + 1; x--) {
      for (int y = radius; y >= -radius + 1; y--) {
        for (int z = radius; z >= -radius + 1; z--) {
          if (blocks.size() >= maxBlocksLimit) {
            return blocks;
          }

          BlockPos blockPos = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);
          Block block = getBlock(blockPos);
          if (block == null) continue;

          if (targetBlocks == null || targetBlocks.contains(block)) {
            blocks.put(blockPos, block);
          }
        }
      }
    }

    return blocks;
  }
}