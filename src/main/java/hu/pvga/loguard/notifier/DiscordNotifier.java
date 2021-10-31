package hu.pvga.loguard.notifier;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import hu.pvga.loguard.loghandler.Pattern;
import org.json.JSONObject;

/**
 * Notifier that sends Discord webhooks when matches are found.
 */
public class DiscordNotifier extends BaseNotifier {
    private final long rateLimit;
    private final WebhookClient client;

    public DiscordNotifier(JSONObject config) {
        super(config);
        this.rateLimit = config.getLong("rate_limit");

        client = WebhookClient.withUrl(config.getString("webhook_url"));
    }

    @Override
    public void handle(String file, String line, Pattern pattern) {
        if (checkLimit(rateLimit)) {

            WebhookEmbed embed = new WebhookEmbedBuilder()
                    .setColor(0xFF0000)
                    .setDescription("loguard caught something")
                    .addField(new WebhookEmbed.EmbedField(true, "Tag", pattern.getTag()))
                    .addField(new WebhookEmbed.EmbedField(true, "Pattern", pattern.getContains()))
                    .addField(new WebhookEmbed.EmbedField(false, "File", file))
                    .addField(new WebhookEmbed.EmbedField(false, "Log", line))
                    .build();

            client.send(embed);
        }
    }
}
