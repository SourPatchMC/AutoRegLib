package vazkii.arl.interf;

import net.minecraft.client.color.item.ItemColor;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public interface IItemColorProvider {

	@ClientOnly
	public ItemColor getItemColor();

}