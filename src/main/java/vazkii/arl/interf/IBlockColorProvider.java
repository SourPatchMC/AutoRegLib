package vazkii.arl.interf;

import net.minecraft.client.color.block.BlockColor;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public interface IBlockColorProvider extends IItemColorProvider {

	@ClientOnly
	public BlockColor getBlockColor();

}