package p455w0rd.embersified.blocks.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import p455w0rd.embersified.blocks.BlockEmitter;
import p455w0rd.embersified.init.ModConfig.Options;
import p455w0rd.embersified.utils.EnergyConverter;
import teamroots.embers.api.capabilities.EmbersCapabilities;
import teamroots.embers.tileentity.TileEntityReceiver;

/**
 * @author p455w0rd
 * @author Polyacov_Yury
 */
public class TileReceiver extends TileEntityReceiver {
    public IEnergyStorage forgeCap = new EnergyConverter(this, capability, false);
    long ticksExisted = 0;

    @Override
    public void update() {
        ++ticksExisted;
        BlockPos pos = getPos();
        IBlockState state = getWorld().getBlockState(pos);
        EnumFacing facing = state.getValue(BlockEmitter.facing);
        TileEntity attachedTile = getWorld().getTileEntity(pos.offset(facing.getOpposite()));
        if (ticksExisted % 2 != 0 || attachedTile == null || capability.getEmber() <= 0)
            return;
        if (!attachedTile.hasCapability(EmbersCapabilities.EMBER_CAPABILITY, facing)
                && Options.embersEnergyCanGenerateForgeEnergy
                && attachedTile.hasCapability(CapabilityEnergy.ENERGY, facing)) {
            IEnergyStorage cap = attachedTile.getCapability(CapabilityEnergy.ENERGY, facing);
            if (cap != null && cap.canReceive() && cap.getEnergyStored() < cap.getMaxEnergyStored()) {
                int added = cap.receiveEnergy((int) Math.min(TRANSFER_RATE * Options.mulitiplier, capability.getEmber() * Options.mulitiplier), true);
                if (added > 0) {
                    cap.receiveEnergy((int) Math.min(TRANSFER_RATE * Options.mulitiplier, capability.getEmber() * Options.mulitiplier), false);
                    capability.removeAmount(added / Options.mulitiplier, true);
                }
                if (!getWorld().isRemote) {
                    attachedTile.markDirty();
                }
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (Options.embersEnergyCanGenerateForgeEnergy && capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (Options.embersEnergyCanGenerateForgeEnergy && capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(forgeCap);
        }
        return super.getCapability(capability, facing);
    }
}