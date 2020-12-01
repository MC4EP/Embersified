package p455w0rd.embersified.utils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import p455w0rd.embersified.blocks.BlockEmitter;
import p455w0rd.embersified.init.ModConfig;
import teamroots.embers.api.capabilities.EmbersCapabilities;
import teamroots.embers.api.power.IEmberCapability;

import javax.annotation.Nonnull;

/**
 * @author p455w0rd
 * @author Polyacov_Yury
 */
public class EnergyConverter implements IEnergyStorage {

    private final IEmberCapability embersCap;
    private final TileEntity tile;
    private final boolean receiver;

    public EnergyConverter(@Nonnull TileEntity tile, IEmberCapability embersCap, boolean canReceive) {
        this.tile = tile;
        this.embersCap = embersCap;
        receiver = canReceive;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive()) {
            return 0;
        }
        int amountInEmbersCap = (int) embersCap.getEmber();
        int spaceLeftInEmbersCap = (int) embersCap.getEmberCapacity() - amountInEmbersCap;
        if (spaceLeftInEmbersCap <= 0) {
            return 0;
        }
        int leftOver = 0;
        double tryToPush = embersCap.addAmount(maxReceive / ModConfig.Options.mulitiplier, false);
        if (tryToPush > spaceLeftInEmbersCap) {
            leftOver = (int) tryToPush - spaceLeftInEmbersCap;
        }
        if (!simulate) {
            embersCap.addAmount(maxReceive / ModConfig.Options.mulitiplier, true);
            tile.markDirty();
        }
        return (int) (maxReceive - (leftOver * ModConfig.Options.mulitiplier));
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract()) {
            return 0;
        }
        int amountInEmbersCap = (int) embersCap.getEmber();
        if (amountInEmbersCap <= 0) {
            return 0;
        }
        int leftOver = 0;
        double tryToPull = embersCap.removeAmount(maxExtract / ModConfig.Options.mulitiplier, false);
        if (tryToPull > amountInEmbersCap) {
            leftOver = (int) tryToPull - amountInEmbersCap;
        }
        if (!simulate) {
            embersCap.removeAmount(maxExtract / ModConfig.Options.mulitiplier, true);
            tile.markDirty();
        }
        return (int) (maxExtract - (leftOver * ModConfig.Options.mulitiplier));
    }

    public void pushEnergy(int ember_transfer_rate) {
        BlockPos pos = tile.getPos();
        EnumFacing facing = tile.getWorld().getBlockState(pos).getValue(BlockEmitter.facing);
        TileEntity attachedTile = tile.getWorld().getTileEntity(pos.offset(facing.getOpposite()));
        if (attachedTile == null || embersCap.getEmber() <= 0)
            return;
        if (attachedTile.hasCapability(EmbersCapabilities.EMBER_CAPABILITY, facing)
                || !ModConfig.Options.embersEnergyCanGenerateForgeEnergy
                || !attachedTile.hasCapability(CapabilityEnergy.ENERGY, facing))
            return;
        IEnergyStorage energyCap = attachedTile.getCapability(CapabilityEnergy.ENERGY, facing);
        if (energyCap == null
                || !energyCap.canReceive()
                || energyCap.getEnergyStored() >= energyCap.getMaxEnergyStored())
            return;
        final double stored = embersCap.getEmber() * ModConfig.Options.mulitiplier;
        if (stored < 1.0D) {  // gets stuck at 0.00999....
            embersCap.setEmber(0.0D);
            return;
        }
        final int value = (int) Math.min(
                ember_transfer_rate * ModConfig.Options.mulitiplier,
                stored);
        int added = energyCap.receiveEnergy(value, true);
        if (added > 0) {
            energyCap.receiveEnergy(value, false);
            embersCap.removeAmount(added / ModConfig.Options.mulitiplier, true);
            if (!tile.getWorld().isRemote)
                attachedTile.markDirty();
        }
    }

    @Override
    public int getEnergyStored() {
        return (int) (embersCap.getEmber() * ModConfig.Options.mulitiplier);
    }

    @Override
    public int getMaxEnergyStored() {
        return (int) (embersCap.getEmberCapacity() * ModConfig.Options.mulitiplier);
    }

    @Override
    public boolean canExtract() {
        return !receiver;
    }

    @Override
    public boolean canReceive() {
        return receiver;
    }
}
