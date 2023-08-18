package vazkii.arl.util;

import java.util.function.Consumer;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import vazkii.arl.interf.ICreativeExtras;

public class CreativeTabHandler {

	protected static Multimap<CreativeModeTab, ItemLike> itemsPerCreativeTab = HashMultimap.create();

	public static void buildContents(CreativeModeTab tab, Consumer<ItemStack> items) {
		for(ItemLike il : itemsPerCreativeTab.get(tab)) {
			Item item = il.asItem();
			
			if(item instanceof ICreativeExtras extras && !extras.canAddToCreativeTab(tab))
				continue;
			
			items.accept(item.getDefaultInstance());
			
			if(item instanceof ICreativeExtras extras)
				extras.addCreativeModeExtras(tab, items);
		}
	}

}
