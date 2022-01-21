package moe.plushie.armourers_workshop.utils;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;

public final class PlayerUtils {

    private PlayerUtils() {
        throw new IllegalAccessError("Utility class.");
    }

    public static boolean gameProfilesMatch(GameProfile profile1, GameProfile profile2) {
        if (profile1 == null) {
            return false;
        }
        if (profile2 == null) {
            return false;
        }

        if (profile1.getId() != null && profile2.getId() != null) {
            if (profile1.getId().equals(profile2.getId())) {
                return true;
            }
        }

        if (profile1.getName() != null && profile2.getName() != null) {
            if (profile1.getName().equals(profile2.getName())) {
                return true;
            }
        }

        return false;
    }

    public static Direction getDirection(int x, int y, int z, PlayerEntity player) {
        return Direction.byIndex(getOrientation(x, y, z, player));
    }

    public static int getOrientation(int x, int y, int z, LivingEntity entity) {
        if (MathHelper.abs((float) entity.posX - x) < 2.0F && MathHelper.abs((float) entity.posZ - z) < 2.0F) {
            double d0 = entity.posY + entity.getEyeHeight() - entity.getYOffset();

            if (d0 - y > 2.0D) {
                return 1;
            }
            if (y - d0 > 0.0D) {
                return 0;
            }
        }

        int l = MathHelper.floor(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        return l == 0 ? 3 : (l == 1 ? 4 : (l == 2 ? 2 : (l == 3 ? 5 : 0)));
    }

    public static Direction getDirectionSide(PlayerEntity player) {
        return Direction.byIndex(getOrientationSide(player));
    }

    public static int getOrientationSide(LivingEntity entity) {
        int l = MathHelper.floor(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        return l == 0 ? 3 : (l == 1 ? 4 : (l == 2 ? 2 : (l == 3 ? 5 : 0)));
    }

    public static void giveItem(PlayerEntity player, ItemStack stack) {
        if (stack == null) {
            return;
        }
        if (!player.inventory.addItemStackToInventory(stack)) {
            if (!player.getEntityWorld().isRemote) {
                UtilItems.spawnItemInWorld(player.getEntityWorld(), player.posX, player.posY, player.posZ, stack);
            }
        }
    }
}
