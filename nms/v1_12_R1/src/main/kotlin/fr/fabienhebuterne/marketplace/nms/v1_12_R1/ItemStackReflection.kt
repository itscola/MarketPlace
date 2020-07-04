package fr.fabienhebuterne.marketplace.nms.v1_12_R1

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import fr.fabienhebuterne.marketplace.nms.interfaces.IItemStackReflection
import net.minecraft.server.v1_12_R1.MojangsonParser
import net.minecraft.server.v1_12_R1.NBTTagCompound
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.lang.reflect.Field
import java.util.*

object ItemStackReflection : IItemStackReflection {

    override fun serializeItemStack(itemStack: ItemStack): String {
        val nbtTagSerialized = NBTTagCompound()
        val itemStackNMS = CraftItemStack.asNMSCopy(itemStack)
        return itemStackNMS.save(nbtTagSerialized).toString()
    }

    override fun deserializeItemStack(itemStackString: String): ItemStack {
        val nbtTagDeserialized = MojangsonParser.parse(itemStackString)
        val itemStackNMS = net.minecraft.server.v1_12_R1.ItemStack(nbtTagDeserialized)
        return CraftItemStack.asBukkitCopy(itemStackNMS)
    }

    override fun getSkull(textureEncoded: String): ItemStack {
        val head = ItemStack(Material.SKULL_ITEM, 1, 3);
        val headMeta = head.itemMeta as SkullMeta
        val profile = GameProfile(UUID.randomUUID(), null)
        profile.properties.put("textures", Property("textures", textureEncoded))
        val profileField: Field
        try {
            profileField = headMeta.javaClass.getDeclaredField("profile");
            profileField.isAccessible = true
            profileField.set(headMeta, profile)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        head.itemMeta = headMeta;
        return head
    }

}