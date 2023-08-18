package vazkii.arl.quilt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import vazkii.arl.util.CreativeTabHandler;

@Mixin(CreativeModeTab.class)
public abstract class CreativeModeTabMixin {

	@Inject(method = "fillItemList", at = @At("HEAD"))
	private void arl$injectItems(NonNullList<ItemStack> items, CallbackInfo ci) {
		CreativeTabHandler.buildContents((CreativeModeTab) (Object) this, items::add);
	}

}
