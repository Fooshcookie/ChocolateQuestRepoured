package team.cqr.cqrepoured.network.server.packet;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import team.cqr.cqrepoured.objects.items.ItemDungeonPlacer.ClientDungeon;
import team.cqr.cqrepoured.structuregen.dungeons.DungeonBase;

public class SPacketDungeonSync implements IMessage {

	private List<DungeonBase> dungeons;
	private List<ClientDungeon> fakeDungeonSet;

	public SPacketDungeonSync() {

	}

	public SPacketDungeonSync(List<DungeonBase> dungeons) {
		this.dungeons = dungeons;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int dungeonCount = buf.readByte();
		this.fakeDungeonSet = new ArrayList<>(dungeonCount);
		for (int i = 0; i < dungeonCount; i++) {
			String name = ByteBufUtils.readUTF8String(buf);
			int iconID = buf.readByte();
			int dependencyCount = buf.readByte();
			String[] dependencies = new String[dependencyCount];
			for (int j = 0; j < dependencyCount; j++) {
				dependencies[j] = ByteBufUtils.readUTF8String(buf);
			}

			this.fakeDungeonSet.add(new ClientDungeon(name, iconID, dependencies));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(this.dungeons.size());
		for (DungeonBase dungeon : this.dungeons) {
			ByteBufUtils.writeUTF8String(buf, dungeon.getDungeonName());
			buf.writeByte(dungeon.getIconID());
			buf.writeByte(dungeon.getModDependencies().length);
			for (String dependency : dungeon.getModDependencies()) {
				ByteBufUtils.writeUTF8String(buf, dependency);
			}
		}
	}

	public List<ClientDungeon> getFakeDungeonList() {
		return this.fakeDungeonSet;
	}

}
