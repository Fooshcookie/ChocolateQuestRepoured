package team.cqr.cqrepoured.util.handlers;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import team.cqr.cqrepoured.CQRMain;
import team.cqr.cqrepoured.crafting.RecipeArmorDyableBreathing;
import team.cqr.cqrepoured.crafting.RecipeArmorDyableRainbow;
import team.cqr.cqrepoured.crafting.RecipeCrownAttach;
import team.cqr.cqrepoured.crafting.RecipeCrownDetach;
import team.cqr.cqrepoured.crafting.RecipesArmorDyes;
import team.cqr.cqrepoured.customtextures.TextureSetManager;
import team.cqr.cqrepoured.factions.FactionRegistry;
import team.cqr.cqrepoured.init.CQRItems;
import team.cqr.cqrepoured.objects.entity.bases.AbstractEntityCQR;
import team.cqr.cqrepoured.objects.entity.bases.AbstractEntityCQRBoss;
import team.cqr.cqrepoured.structuregen.DungeonDataManager;
import team.cqr.cqrepoured.structuregen.lootchests.LootTableLoader;
import team.cqr.cqrepoured.util.CQRConfig;
import team.cqr.cqrepoured.util.Reference;

@EventBusSubscriber
public class EventsHandler {

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onLootTableLoadPre(LootTableLoadEvent event) {
		if (event.getName().getNamespace().equals(Reference.MODID)) {
			try {
				event.setTable(LootTableLoader.fillLootTable(event.getName(), event.getTable()));
			} catch (Exception e) {
				CQRMain.logger.error("Unable to fill loot table {}", event.getName());
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onLootTableLoadPost(LootTableLoadEvent event) {
		LootTableLoader.freezeLootTable();
	}

	@SubscribeEvent
	public static void onDefense(LivingAttackEvent event) {
		boolean tep = false;

		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			Entity attacker = event.getSource().getTrueSource();
			@SuppressWarnings("unused")
			float amount = event.getAmount();
			World world = player.world;

			if (player.getActiveItemStack().getItem() != CQRItems.SHIELD_WALKER_KING || player.getHeldItemMainhand().getItem() != CQRItems.SWORD_WALKER || player.getRidingEntity() != null || attacker == null) {
				return;
			}

			double d = attacker.posX + (attacker.world.rand.nextDouble() - 0.5D) * 4.0D;
			double d1 = attacker.posY;
			double d2 = attacker.posZ + (attacker.world.rand.nextDouble() - 0.5D) * 4.0D;

			@SuppressWarnings("unused")
			double d3 = player.posX;
			@SuppressWarnings("unused")
			double d4 = player.posY;
			@SuppressWarnings("unused")
			double d5 = player.posZ;

			int i = MathHelper.floor(d);
			int j = MathHelper.floor(d1);
			int k = MathHelper.floor(d2);

			BlockPos ep = new BlockPos(i, j, k);
			BlockPos ep1 = new BlockPos(i, j + 1, k);

			if (world.getCollisionBoxes(player, player.getEntityBoundingBox()).size() == 0 && !world.containsAnyLiquid(attacker.getEntityBoundingBox()) && player.isActiveItemStackBlocking() && player.getDistanceSq(attacker) >= 25.0D) {
				if (world.getBlockState(ep).getBlock().isPassable(world, ep) && world.getBlockState(ep1).getBlock().isPassable(world, ep1)) {
					tep = true;
				} else {
					tep = false;
					if (!world.isRemote) {
						((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_LARGE, player.posX, player.posY + player.height * 0.5D, player.posZ, 12, 0.25D, 0.25D, 0.25D, 0.0D);
					}
				}
			}

			if (tep) {
				if (world.getBlockState(ep).getBlock().isPassable(world, ep) && world.getBlockState(ep1).getBlock().isPassable(world, ep1)) {
					if (player instanceof EntityPlayerMP) {
						EntityPlayerMP playerMP = (EntityPlayerMP) player;

						playerMP.connection.setPlayerLocation(d, d1, d2, playerMP.rotationYaw, playerMP.rotationPitch);
						if (!world.isRemote) {
							((WorldServer) world).spawnParticle(EnumParticleTypes.PORTAL, player.posX, player.posY + player.height * 0.5D, player.posZ, 12, 0.25D, 0.25D, 0.25D, 0.0D);
						}
						world.playSound(null, d, d1, d2, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.MASTER, 1.0F, 1.0F);
					}
					event.setCanceled(true);
					tep = false;
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event) {
		Random rand = new Random();
		Entity entity = event.getEntity();
		NBTTagCompound tag = entity.getEntityData();

		if (tag.hasKey("Items")) {
			NBTTagList itemList = tag.getTagList("Items", Constants.NBT.TAG_COMPOUND);

			if (itemList == null) {
				return;
			}

			for (int i = 0; i < itemList.tagCount(); i++) {
				NBTTagCompound entry = itemList.getCompoundTagAt(i);
				ItemStack stack = new ItemStack(entry);

				if (stack != null) {
					if (!entity.world.isRemote) {
						entity.world.spawnEntity(new EntityItem(entity.world, entity.posX + rand.nextDouble(), entity.posY, entity.posZ + rand.nextDouble(), stack));
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onWorldLoad(WorldEvent.Load e) {
		DungeonDataManager.handleWorldLoad(e.getWorld());

		if (!e.getWorld().isRemote && e.getWorld().provider.getDimension() == 0) {
			LootTableLoader.registerCustomLootTables((WorldServer) e.getWorld());
		}
	}

	@SubscribeEvent
	public static void onWorldCreateSpawnpoint(WorldEvent.CreateSpawnPosition e) {
		DungeonDataManager.handleWorldLoad(e.getWorld());
	}

	@SubscribeEvent
	public static void onWorldSave(WorldEvent.Save e) {
		DungeonDataManager.handleWorldSave(e.getWorld());
		FactionRegistry.instance().saveAllReputationData(false);
	}

	@SubscribeEvent
	public static void onRecipeRegister(RegistryEvent.Register<IRecipe> event) {
		event.getRegistry().register(new RecipesArmorDyes().setRegistryName(Reference.MODID, "armor_coloring"));
		event.getRegistry().register(new RecipeArmorDyableRainbow());
		event.getRegistry().register(new RecipeArmorDyableBreathing());
		event.getRegistry().register(new RecipeCrownAttach());
		event.getRegistry().register(new RecipeCrownDetach());
		// event.getRegistry().register(new RecipeDynamicCrown().setRegistryName(Reference.MODID, "dynamic_king_crown"));
	}

	@SubscribeEvent
	public static void onWorldUnload(WorldEvent.Unload e) {
		if (!e.getWorld().isRemote) {
			DungeonDataManager.handleWorldUnload(e.getWorld());
		}
	}

	@SubscribeEvent
	public static void onPlayerLogin(PlayerLoggedInEvent event) {
		// Send packets with ct's to player
		if (FMLCommonHandler.instance().getSide().isServer() || !CQRMain.proxy.isOwnerOfIntegratedServer(event.player)) {
			TextureSetManager.sendTexturesToClient((EntityPlayerMP) event.player);

			FactionRegistry.instance().handlePlayerLogin((EntityPlayerMP) event.player);
		}

	}

	@SubscribeEvent
	public static void onPlayerLogout(PlayerLoggedOutEvent event) {
		if (FMLCommonHandler.instance().getSide().isServer() || !CQRMain.proxy.isOwnerOfIntegratedServer(event.player)) {
			FactionRegistry.instance().handlePlayerLogout((EntityPlayerMP) event.player);
		}
	}

	@SubscribeEvent
	public static void onAttackEntityEvent(AttackEntityEvent event) {
		if (CQRConfig.mobs.blockCancelledByAxe) {
			EntityPlayer player = event.getEntityPlayer();
			World world = player.world;

			if (!world.isRemote && event.getTarget() instanceof AbstractEntityCQR) {
				AbstractEntityCQR targetCQR = (AbstractEntityCQR) event.getTarget();

				if (targetCQR.canBlockDamageSource(DamageSource.causePlayerDamage(player)) && player.getHeldItemMainhand().getItem() instanceof ItemAxe && player.getCooledAttackStrength(0) >= 0.9F) {
					targetCQR.setLastTimeHitByAxeWhileBlocking(targetCQR.ticksExisted);
				}
			}
		}
	}

	@SubscribeEvent
	public static void sayNoToCowardlyPlacingLavaAgainstBosses(FillBucketEvent event) {
		if (CQRConfig.bosses.antiCowardMode && !event.getEntityPlayer().isCreative()) {
			BlockPos pos = new BlockPos(event.getEntityPlayer());
			int radius = CQRConfig.bosses.antiCowardRadius;
			AxisAlignedBB aabb = new AxisAlignedBB(pos.add(-radius, -radius / 2, -radius), pos.add(radius, radius / 2, radius));
			event.setCanceled(!event.getWorld().getEntitiesWithinAABB(AbstractEntityCQRBoss.class, aabb).isEmpty());
		}
	}

}
