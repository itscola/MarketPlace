package fr.fabienhebuterne.marketplace.commands

import fr.fabienhebuterne.marketplace.MarketPlace
import fr.fabienhebuterne.marketplace.commands.factory.CallCommand
import fr.fabienhebuterne.marketplace.services.inventory.MailsInventoryService
import fr.fabienhebuterne.marketplace.services.pagination.MailsService
import org.bukkit.Server
import org.bukkit.command.Command
import org.bukkit.entity.Player
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class CommandMails(kodein: Kodein) : CallCommand<MarketPlace>("mails") {

    private val mailsService: MailsService by kodein.instance<MailsService>()
    private val mailsInventoryService: MailsInventoryService by kodein.instance<MailsInventoryService>()

    override fun runFromPlayer(server: Server, player: Player, commandLabel: String, cmd: Command, args: Array<String>) {
        val mailsPaginated = mailsService.getInventoryPaginated(player.uniqueId, 1)

        val initListingsInventory = mailsInventoryService.initInventory(instance, mailsPaginated, player)
        player.openInventory(initListingsInventory)
    }

}