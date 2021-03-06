package team.cqr.cqrepoured.network.server.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SPacketItemStackSync implements IMessage {

	private int entityId;
	private int slotIndex;
	private ItemStack stack;

	public SPacketItemStackSync() {

	}

	public SPacketItemStackSync(int entityId, int slotIndex, ItemStack stack) {
		this.entityId = entityId;
		this.slotIndex = slotIndex;
		this.stack = stack;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityId = buf.readInt();
		this.slotIndex = buf.readInt();
		this.stack = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.entityId);
		buf.writeInt(this.slotIndex);
		ByteBufUtils.writeItemStack(buf, this.stack);
	}

	public int getEntityId() {
		return this.entityId;
	}

	public int getSlotIndex() {
		return this.slotIndex;
	}

	public ItemStack getStack() {
		return this.stack;
	}

}
