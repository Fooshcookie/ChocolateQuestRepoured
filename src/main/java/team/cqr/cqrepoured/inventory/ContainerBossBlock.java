package team.cqr.cqrepoured.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import team.cqr.cqrepoured.objects.items.ItemSoulBottle;
import team.cqr.cqrepoured.tileentity.TileEntityBoss;

public class ContainerBossBlock extends Container {

	public ContainerBossBlock(InventoryPlayer playerInv, TileEntityBoss tileentity) {
		IItemHandler inventory = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 50 + i * 18));
			}
		}

		for (int k = 0; k < 9; k++) {
			this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 108));
		}

		this.addSlotToContainer(new SlotItemHandler(inventory, 0, 80, 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ItemSoulBottle && stack.hasTagCompound() && stack.getTagCompound().hasKey("EntityIn");
			}
		});
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.isCreative();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			ItemStack itemstack = itemstack1.copy();

			if (index > 35) {
				if (this.mergeItemStack(itemstack1, 0, 36, false)) {
					return itemstack;
				}
			} else if (this.mergeItemStack(itemstack1, 36, this.inventorySlots.size(), false)) {
				return itemstack;
			} else if (index > 26) {
				if (this.mergeItemStack(itemstack1, 0, 27, false)) {
					return itemstack;
				}
			} else {
				if (this.mergeItemStack(itemstack1, 27, 36, false)) {
					return itemstack;
				}
			}
		}

		return ItemStack.EMPTY;
	}

}
