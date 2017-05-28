package pro.shikkoku.listener;

import com.comphenix.packetwrapper.WrapperPlayServerNamedEntitySpawn;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import pro.shikkoku.Shikkoku;

/**
 * Created by mcnek on 2017/05/29.
 */
public class ProtocolListener extends PacketAdapter {

    private final Shikkoku plugin;

    public ProtocolListener(Shikkoku plugin) {
        super(plugin, PacketType.Play.Server.NAMED_ENTITY_SPAWN);
        this.plugin = plugin;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        WrapperPlayServerNamedEntitySpawn packet = new WrapperPlayServerNamedEntitySpawn(event.getPacket());
        plugin.getNameStore().getName(packet.getPlayerUUID()).ifPresent(name -> {
            packet.getMetadata().setObject(2, plugin.getNameStore().getName(packet.getPlayerUUID()));
        });
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {

    }
}
