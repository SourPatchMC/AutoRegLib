package vazkii.arl.util;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.mojang.datafixers.util.Pair;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import vazkii.arl.AutoRegLib;
import vazkii.arl.interf.IBlockColorProvider;
import vazkii.arl.interf.IBlockItemProvider;
import vazkii.arl.interf.IItemColorProvider;
import vazkii.arl.interf.IItemPropertiesFiller;

public final class RegistryHelper {

	// Quilt
	private static ModData data;
	
	private static final Queue<Pair<Item, IItemColorProvider>> itemColors = new ArrayDeque<>();
	private static final Queue<Pair<Block, IBlockColorProvider>> blockColors = new ArrayDeque<>();

	private static final Map<Object, ResourceLocation> internalNames = new HashMap<>();

	// Quilt
	private static ResourceLocation id(String resloc) {
		return new ResourceLocation(data.modid, resloc);
	}

	// Quilt
	public static void setup(String modid) {
		data = new ModData(modid);
	}
	
	public static <T> ResourceLocation getRegistryName(T obj, Registry<T> registry) {
		if(internalNames.containsKey(obj))
			return getInternalName(obj);
		
		return registry.getKey(obj);
	}
	
	public static void setInternalName(Object obj, ResourceLocation name) {
		internalNames.put(obj, name);
	}
	
	public static ResourceLocation getInternalName(Object obj) {
		return internalNames.get(obj);
	}

	public static void registerBlock(Block block, String resloc) {
		registerBlock(block, resloc, true);
	}

	public static void registerBlock(Block block, String resloc, boolean hasBlockItem) {
		register(block, resloc, Registry.BLOCK);

		if(hasBlockItem) {
			data.register(Registry.ITEM, () -> data.createItemBlock(block));
		}

		if(block instanceof IBlockColorProvider)
			blockColors.add(Pair.of(block, (IBlockColorProvider) block));
	}

	public static void registerItem(Item item, String resloc) {
		register(item, resloc, Registry.ITEM);

		if(item instanceof IItemColorProvider)
			itemColors.add(Pair.of(item, (IItemColorProvider) item));
	}
	
	public static <T> void register(T obj, String resloc, Registry<T> registry) {
		if(obj == null)
			throw new IllegalArgumentException("Can't register null object.");

		setInternalName(obj, id(resloc));
		data.register(registry, () -> obj);
	}

	public static void setCreativeTab(ItemLike itemlike, CreativeModeTab group) {
		ResourceLocation res = getInternalName(itemlike);
		if(res == null)
			throw new IllegalArgumentException("Can't set the creative tab for an ItemLike without a registry name yet");

		CreativeTabHandler.itemsPerCreativeTab.put(group, itemlike);
	}

	public static void submitBlockColors(BiConsumer<BlockColor, Block> consumer) {
		blockColors.forEach(p -> consumer.accept(p.getSecond().getBlockColor(), p.getFirst()));
		blockColors.clear();
	}

	public static void submitItemColors(BiConsumer<ItemColor, Item> consumer) {
		itemColors.forEach(p -> consumer.accept(p.getSecond().getItemColor(), p.getFirst()));
		itemColors.clear();
	}

	private static record ModData(String modid) {

		private <T> void register(Registry<T> registry, Supplier<T> supplier) {
			T entry = (T) supplier.get();
			ResourceLocation name = getInternalName(entry);
			Registry.register(registry, name, entry);
			AutoRegLib.LOGGER.debug("Registering to " + registry.key().registry() + " - " + name);;
		}

		private Item createItemBlock(Block block) {
			Item.Properties props = new Item.Properties();
			ResourceLocation registryName = getInternalName(block);

			if(block instanceof IItemPropertiesFiller)
				((IItemPropertiesFiller) block).fillItemProperties(props);

			BlockItem blockitem;
			if(block instanceof IBlockItemProvider)
				blockitem = ((IBlockItemProvider) block).provideItemBlock(block, props);
			else blockitem = new BlockItem(block, props);

			if(block instanceof IItemColorProvider)
				itemColors.add(Pair.of(blockitem, (IItemColorProvider) block));

			setInternalName(blockitem, registryName);
			return blockitem;
		}

	}

}
