/*
 * Copyright (c) bdew, 2013
 * https://github.com/bdew/gendustry
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * https://raw.github.com/bdew/gendustry/master/MMPL-1.0.txt
 */

package net.bdew.gendustry.nei

import net.bdew.lib.data.base.{DataSlot, TileDataSlots}

/**
 * This is a fake TileEntity, used to show widgets (that require dataslots) on NEI recipes
 */
object FakeDataSlotsTile extends TileDataSlots {
  override def dataSlotChanged(slot: DataSlot) = {}
}
