/*
 * Copyright (c) bdew, 2013 - 2014
 * https://github.com/bdew/gendustry
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.gendustry.machines.liquifier

import net.bdew.gendustry.apiimpl.TileWorker
import net.bdew.gendustry.config.Fluids
import net.bdew.gendustry.fluids.ProteinSources
import net.bdew.gendustry.machines.FluidPusher
import net.bdew.gendustry.power.TilePowered
import net.bdew.lib.block.TileKeepData
import net.bdew.lib.covers.TileCoverable
import net.bdew.lib.data._
import net.bdew.lib.data.base.UpdateKind
import net.bdew.lib.power.TileBaseProcessor
import net.bdew.lib.tile.ExposeTank
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraftforge.fluids._

class TileLiquifier extends TileBaseProcessor with TileWorker with TilePowered with ExposeTank with TileCoverable with TileKeepData with FluidPusher {
  lazy val cfg = MachineLiquifier

  val tank = DataSlotTankRestricted("tank", this, cfg.tankSize, Fluids.protein).setUpdate(UpdateKind.GUI, UpdateKind.SAVE)
  val output = DataSlotInt("output", this).setUpdate(UpdateKind.SAVE)

  object slots {
    val inMeat = 0
  }

  def getSizeInventory = 1

  def getTankFromDirection(dir: EnumFacing): IFluidTank = tank

  def isWorking = output > 0
  def tryStart(): Boolean = {
    if (getStackInSlot(slots.inMeat) != null) {
      output := ProteinSources.getValue(getStackInSlot(0))
      decrStackSize(slots.inMeat, 1)
      return true
    } else return false
  }

  def tryFinish(): Boolean = {
    if (tank.fill(output, false) == output.value) {
      tank.fill(output, true)
      output := -1
      return true
    } else return false
  }

  allowSided = true
  override def isItemValidForSlot(slot: Int, stack: ItemStack): Boolean = ProteinSources.getValue(stack) > 0
  override def canExtractItem(slot: Int, item: ItemStack, side: EnumFacing): Boolean = false

  override def fill(from: EnumFacing, resource: FluidStack, doFill: Boolean) = 0
  override def canFill(from: EnumFacing, fluid: Fluid) = false

  override def isValidCover(side: EnumFacing, cover: ItemStack) = true
}