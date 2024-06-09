package me.obsilabor.tpshud.server

import me.obsilabor.tpshud.networking.Packets
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import java.nio.ByteBuffer

class TpsHudPaperServer : JavaPlugin(), Listener {
    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this)
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, Packets.TPS)
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, Packets.HANDSHAKE)
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, Runnable {
            for (player in Bukkit.getOnlinePlayers()) {
                player.sendPluginMessage(this, Packets.TPS, ByteBuffer.allocate(8).putDouble(Bukkit.getTPS()[0]).array())
            }
        }, 0, 20L)
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        Bukkit.getScheduler().runTaskLater(this, Runnable {
            logger.info("Sending handshake to ${event.player.name}")
            event.player.sendPluginMessage(this, Packets.HANDSHAKE, byteArrayOf())
        }, 40L) // Paper calls join event earlier than fabric
    }
}