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
        world.addEntity(npc, SpawnReason.NATURAL);
        
        for(Player p : Bukkit.getOnlinePlayers()){
        	
        	PacketPlayOutPlayerInfo packet1 = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc);
        	
//			try{
//
//				Field f = packet1.getClass().getDeclaredField("b");
//				f.setAccessible(true);
//				List<PlayerInfoData>  profileList = (List<PlayerInfoData>) f.get(packet1);
//				
//				for(PlayerInfoData gameProfile : profileList){
//					
//					System.out.println(gameProfile.a().toString());
//					
//				}
//				
//				f.setAccessible(false);
//				
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
    			
    		
        	
//        	((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet1);
       
        	PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn(npc);
        	
        	try {

        		Field f = packet.getClass().getDeclaredField("b");
        		f.setAccessible(true);
        		f.set(packet, uuid.fromString("452e82d8-c423-4e6c-bb90-6c7121e2677e"));
        		f.setAccessible(false);

//            	for(Field f1 : packet.getClass().getDeclaredFields()){
//            		
//            		f1.setAccessible(true);
//            		System.out.println(f1.getName() + " --- " + f1.get(packet));
//            		f1.setAccessible(false);
//            		
//            	}

        		
        		
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        	
        }
        
		
	}
	
}
