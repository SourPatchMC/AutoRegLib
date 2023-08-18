package vazkii.arl.interf;

import java.util.function.Consumer;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public interface ICreativeExtras {

	default boolean canAddToCreativeTab(CreativeModeTab tab) {
		return true;
	}

	void addCreativeModeExtras(CreativeModeTab tab, Consumer<ItemStack> items);

}
