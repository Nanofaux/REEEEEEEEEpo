package me.yiffed.veinminer.events

import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class BreakEvent(player: Player, block: Block) : Event() {
    override fun getHandlers(): HandlerList {
        TODO("Not yet implemented")
    }

}