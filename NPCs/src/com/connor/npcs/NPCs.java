package com.connor.npcs;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import javax.tools.DocumentationTool.Location;

import org.apache.commons.codec.language.bm.Rule.RPattern;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.plugin.java.JavaPlugin;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.PlayerInfoData;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldServer;

public class NPCs extends JavaPlugin{

	public void onEnable() {

        MinecraftServer server = MinecraftServer.getServer();
        WorldServer world = server.getWorldServer(0);
        UUID uuid = UUID.randomUUID();
        GameProfile profile = new GameProfile(uuid, "NPC-01");
        PlayerInteractManager manager = new PlayerInteractManager(world);
       
        EntityPlayer npc = new EntityPlayer(server, world, profile, manager);
        npc.playerConnection = new PlayerConnection(server, new NetworkManager(EnumProtocolDirection.CLIENTBOUND), npc);
        npc.setLocation(0,65,0,0,0);
        
        for(Player p : Bukkit.getOnlinePlayers()){
        	
        	PacketPlayOutPlayerInfo packet1 = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc);
        	
        	PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn(npc);
        	
        	try {

        		Field f = packet.getClass().getDeclaredField("b");
        		f.setAccessible(true);
        		f.set(packet, uuid.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf5"));
        		f.setAccessible(false);

        		
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet1);
        	((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        	
        }
        
        world.addEntity(npc, SpawnReason.NATURAL);
		
	}
	
}
