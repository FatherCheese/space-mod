package teamport.moonmod.mixin;

import net.minecraft.core.world.World;
import net.minecraft.core.world.type.WorldType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.moonmod.world.ISpace;

@Mixin(value = World.class, remap = false)
public abstract class WorldMixin {

	@Shadow
	public abstract WorldType getWorldType();

	@Inject(method = "getStarBrightness", at = @At("TAIL"), cancellable = true)
	private void moonMod_starBrightness(float partialTick, CallbackInfoReturnable<Float> cir) {
		if (getWorldType() instanceof ISpace)
			cir.setReturnValue(1.0F);
	}
}
