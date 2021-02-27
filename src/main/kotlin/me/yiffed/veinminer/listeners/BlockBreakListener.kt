package me.yiffed.veinminer.listeners

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakListener : Listener {
    private val woodsUwu = listOf(
        Material.ACACIA_LOG,
        Material.BIRCH_LOG,
        Material.DARK_OAK_LOG,
        Material.JUNGLE_LOG,
        Material.OAK_LOG,
        Material.SPRUCE_LOG
    )
    private val ores = listOf(
        Material.COAL_ORE,
        Material.IRON_ORE,
        Material.GOLD_ORE,
        Material.DIAMOND_ORE,
        Material.EMERALD_ORE,
        Material.NETHER_QUARTZ_ORE,
        Material.LAPIS_ORE,
        Material.REDSTONE_ORE
    )
    private val picks = listOf(
        Material.DIAMOND_PICKAXE,
        Material.NETHERITE_PICKAXE,
    )

    private fun isOre(block: Block): Boolean {
        return ores.contains(block.type)
    }

    private fun isTree(block: Block): Boolean {
        return woodsUwu.contains(block.type)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block
        val playerItem = event.player.inventory.itemInMainHand
        if (isOre(block) && picks.contains(playerItem.type)) {
            veinMine(block)
        } else if (isTree(block))
            veinTreeMine(block)
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
            if (curBlock.type == type)
                blocks.add(curBlock)
        }
        for (y: Int in nearby) {
            val curBlock = world.getBlockAt(blockX, blockY + y, blockZ)
            if (curBlock.type == type)
                blocks.add(curBlock)
        }
        for (z: Int in nearby) {
            val curBlock = world.getBlockAt(blockX, blockY, blockZ + z)
            if (curBlock.type == type)
                blocks.add(curBlock)
        }
        return blocks
    }

    private fun getTreeBlocks(block: Block): MutableList<Block> {
        val location = block.location
        val world = block.world
        val nearby = listOf(-2, -1, 1, 2)
        val blocks = mutableListOf<Block>()

        val blockX = location.blockX
        val blockY = location.blockY
        val blockZ = location.blockZ

        // still all 3 in case liek beeeg trees~.. liek accadia or beeg/dark owoak idk
        for (x: Int in nearby) {
            val curBlock = world.getBlockAt(blockX + x, blockY, blockZ)
            if (woodsUwu.contains(curBlock.type))
                blocks.add(curBlock)
        }
        for (y: Int in nearby) {
            val curBlock = world.getBlockAt(blockX, blockY + y, blockZ)
            if (woodsUwu.contains(curBlock.type))
                blocks.add(curBlock)
        }
        for (z: Int in nearby) {
            val curBlock = world.getBlockAt(blockX, blockY, blockZ + z)
            if (woodsUwu.contains(curBlock.type))
                blocks.add(curBlock)
        }
        return blocks
    }

    private fun veinMine(block: Block) {
        var blocks = getNearbyBlocks(block)
        for (x: Int in 0..6) {
            val nextBlocks = mutableListOf<Block>()
            for (curBlock: Block in blocks) {
                nextBlocks += getNearbyBlocks(curBlock)
                curBlock.breakNaturally()
            }
            blocks = nextBlocks
        }
    }

    private fun veinTreeMine(block: Block) {
        var blocks = getTreeBlocks(block)
        while (true) {
            val nextBlocks = mutableListOf<Block>()
            for (curBlock: Block in blocks) {
                nextBlocks += getTreeBlocks(curBlock)
                curBlock.breakNaturally()
            }
            if (nextBlocks.size == 0)
                break
            blocks = nextBlocks
        }
    }

}