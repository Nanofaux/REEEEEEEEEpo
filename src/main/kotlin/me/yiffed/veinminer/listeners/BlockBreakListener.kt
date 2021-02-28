package me.yiffed.veinminer.listeners

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakListener : Listener {
    private val picks = listOf(
        Material.DIAMOND_PICKAXE,
        Material.NETHERITE_PICKAXE,
    )

    private fun isOre(block: Block): Boolean {
        return block.type.toString().endsWith("_ORE")
    }

    private fun isTree(block: Block): Boolean {
        return block.type.toString().endsWith("_LOG")
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block
        val playerItem = event.player.inventory.itemInMainHand
        if (isOre(block) && picks.contains(playerItem.type) || isTree(block))
            veinMine(block)
    }

    private fun getNearbyBlocks(block: Block): MutableList<Block> {
        val type = block.type
        val location = block.location
        val world = block.world
        val nearby = listOf(-1, 1)
        val blocks = mutableListOf<Block>()

        val blockX = location.blockX
        val blockY = location.blockY
        val blockZ = location.blockZ

        for (x: Int in nearby) {
            val curBlock = world.getBlockAt(blockX + x, blockY, blockZ)
            if (curBlock.type == type) {
                blocks.add(curBlock)
            }
        }
        for (y: Int in nearby) {
            val curBlock = world.getBlockAt(blockX, blockY + y, blockZ)
            if (curBlock.type == type) {
                blocks.add(curBlock)
            }
        }
        for (z: Int in nearby) {
            val curBlock = world.getBlockAt(blockX, blockY, blockZ + z)
            if (curBlock.type == type) {
                blocks.add(curBlock)
            }
        }
        return blocks
    }

    private fun veinMine(block: Block) {
        var blocks = getNearbyBlocks(block)
        for (i in 0..6) {
            val nextBlocks = mutableListOf<Block>()
            for (curBlock: Block in blocks) {
                nextBlocks += getNearbyBlocks(curBlock)
                curBlock.breakNaturally()
            }
            nextBlocks.removeAll { it.type == Material.AIR }
            if (nextBlocks.size == 0)
                break
            blocks = nextBlocks
        }
    }

}