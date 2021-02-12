package ubiquitaku.amasta;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Amasta extends JavaPlugin implements Listener {
    List<Player> list = new ArrayList<>();



    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this,this);
        list = new ArrayList<>();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("amasta")) {
            if (!sender.isOp()) {
                sender.sendMessage("§c§lあなたはこのコマンドを実行する権限を持っていません§r");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage("§a----------------------------------------");
                sender.sendMessage("/amasta set : アマスタに装備させるアイテムを設定してプレイヤー立ってる場所に置きます");
                sender.sendMessage("§a----------------------------------------");
                return true;
            }
            if (args[0].equals("set")) {
                if (list.contains((Player) sender)) {
                    sender.sendMessage("あんたハッククライアントかなんか使ってる？");
                    return true;
                }
                Player p = (Player) sender;
                list.add(p);
                Inventory inv = Bukkit.createInventory(null,9,"あますた");
                ItemStack s0 = new ItemStack(Material.DIAMOND_HELMET);
                ItemMeta m0 = s0.getItemMeta();
                ItemStack s1 = new ItemStack(Material.DIAMOND_CHESTPLATE);
                ItemMeta m1 = s1.getItemMeta();
                ItemStack s2 = new ItemStack(Material.DIAMOND_LEGGINGS);
                ItemMeta m2 = s2.getItemMeta();
                ItemStack s3 = new ItemStack(Material.DIAMOND_BOOTS);
                ItemMeta m3 = s3.getItemMeta();
                ItemStack s4 = new ItemStack(Material.DIAMOND_SWORD);
                ItemMeta m4 = s4.getItemMeta();
                ItemStack s5 = new ItemStack(Material.SHIELD);
                ItemMeta m5 = s5.getItemMeta();
                m0.setDisplayName("あたま");
                m1.setDisplayName("胴");
                m2.setDisplayName("レギンス");
                m3.setDisplayName("足");
                m4.setDisplayName("右手");
                m5.setDisplayName("左手");
                inv.setItem(0,s0);
                inv.setItem(1,s1);
                inv.setItem(2,s2);
                inv.setItem(3,s3);
                inv.setItem(4,s4);
                inv.setItem(5,s5);
                p.openInventory(inv);
            }
        }
        return true;
    }

    @EventHandler
    public void click(InventoryCloseEvent e) {
        if (list.size() == 0) {
            return;
        }
        if (!list.contains(e.getPlayer())) {
            return;
        }
        ArmorStand as = (ArmorStand) getServer().getWorld(e.getPlayer().getWorld().getName()).spawnEntity(e.getPlayer().getLocation(), EntityType.ARMOR_STAND);
        as.setInvisible(true);
        as.setItem(EquipmentSlot.HEAD,e.getInventory().getItem(0));
        as.setItem(EquipmentSlot.CHEST,e.getInventory().getItem(1));
        as.setItem(EquipmentSlot.LEGS,e.getInventory().getItem(2));
        as.setItem(EquipmentSlot.FEET,e.getInventory().getItem(3));
        as.setItem(EquipmentSlot.HAND,e.getInventory().getItem(4));
        as.setItem(EquipmentSlot.OFF_HAND,e.getInventory().getItem(5));
        list.remove(e.getPlayer());
    }
}
