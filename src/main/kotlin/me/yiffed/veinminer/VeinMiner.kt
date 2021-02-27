package me.yiffed.veinminer

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import kr.entree.spigradle.annotations.PluginMain
import me.yiffed.veinminer.listeners.BlockBreakListener
import org.bukkit.plugin.java.JavaPlugin

/**
 * Root class for the vein miner plugin.
 */
@PluginMain
class VeinMiner : JavaPlugin() {
    override fun onLoad() {
        // turn on verbose mode because it's nice
        CommandAPI.onLoad(true)
    }

    override fun onEnable() {
        // always call this!
        CommandAPI.onEnable(this)
        // construct a boop command - very useful api, includes tab completion.
        CommandAPICommand("boop")
                .executesPlayer(PlayerCommandExecutor { sender, args ->
                    sender.sendMessage("boop!")
                }).register()
        // please try and use this as often as you can - isn't necessary, but helps other people
        // read your code :3
        this.logger.info("enabled!")

        server.pluginManager.registerEvents(BlockBreakListener(), this)
    }
}
