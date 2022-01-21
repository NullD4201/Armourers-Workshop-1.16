package moe.plushie.armourers_workshop.common.inventory;

import moe.plushie.armourers_workshop.common.inventory.slot.SlotSkinTemplate;
import moe.plushie.armourers_workshop.common.tileentities.TileEntityHologramProjector;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHologramProjector extends ModTileContainer<TileEntityHologramProjector> {

    public ContainerHologramProjector(PlayerInventory invPlayer, TileEntityHologramProjector tileEntity) {
        super(invPlayer, tileEntity);
        addPlayerSlots(8, 142);
        addSlotToContainer(new SlotSkinTemplate(tileEntity, 0, 8, 110));
    }

    @Override
    protected ItemStack transferStackFromPlayer(PlayerEntity playerIn, int index) {
        Slot slot = getSlot(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            ItemStack result = stack.copy();

            // Moving from player to tile entity.
            if (!this.mergeItemStack(stack, 36, 37, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            slot.onTake(playerIn, stack);

            return result;
        }
        return ItemStack.EMPTY;
    }
}
