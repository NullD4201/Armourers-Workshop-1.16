package moe.plushie.armourers_workshop.common.init.items;

import moe.plushie.armourers_workshop.ArmourersWorkshop;
import moe.plushie.armourers_workshop.common.lib.EnumGuiId;
import moe.plushie.armourers_workshop.common.lib.LibItemNames;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemGuideBook extends AbstractModItem {

    public ItemGuideBook() {
        super(LibItemNames.GUIDE_BOOK);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (worldIn.isRemote) {
            playerIn.openGui(ArmourersWorkshop.getInstance(), EnumGuiId.GUIDE_BOOK.ordinal(), worldIn, 0, 0, 0);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
