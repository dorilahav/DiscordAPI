package com.dorilahav.api.chat;

import java.awt.Color;
import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.utils.Checks;

@Deprecated
public class Embed extends EmbedBuilder {

    public String title = "";
    private List<String> args = new ArrayList<>();

    public Embed() {
        super();
    }

    /**
     * Creates a new Embed, setting it's title and description.
     *
     * @param title       the title of the Embed.
     * @param description the description of the Embed.
     * @param args        arguments to replace.
     */
    public Embed(String title, String description, String... args) {
        super();
        setTitle(title);
        super.setDescription(description);
        this.args.addAll(Arrays.asList(args));
    }

    public Embed(Embed embed) {
        super(embed);
        this.title = embed.title;
        this.args = embed.args;
    }

    /**
     * Sets the arguments to replace when building the Embed.
     *
     * @param args the arguments.
     * @return this Embed instance.
     */
    public Embed setArguments(String... args) {
        Checks.notEmpty(args, "args");

        this.args = Arrays.asList(args);
        return this;
    }

    /**
     * Sets the arguments to replace when building the Embed.
     *
     * @param args the arguments.
     * @return this Embed instance.
     */
    public Embed setArguments(List<String> args) {
        Checks.notEmpty(args, "args");

        this.args = args;
        return this;
    }

    /**
     * Adds an arguments to replace when building the Embed.
     *
     * @param arg the argument.
     * @return this Embed instance.
     */
    public Embed addArgument(String replacer, String replacement) {
        Checks.notEmpty(replacer, "replacer");

        this.args.add(replacer);
        this.args.add(replacement);
        return this;
    }

    /**
     * Sets the title of the Embed.
     *
     * @param title the title.
     * @return this Embed instance.
     */
    public Embed setTitle(String title) {
        Checks.notEmpty(title, "title");

        this.title = title;
        return this;
    }

    /**
     * Sets the description of the Embed.
     *
     * @param description the description.
     * @param args        arguments to replace.
     * @return this Embed instance.
     */
    public Embed setDescription(String description) {
        Checks.notEmpty(description, "description");

        super.setDescription(description);
        return this;
    }

    /**
     * Adds the current time as the Embed's time stamp.
     *
     * @return this Embed instance.
     */
    public Embed setTimeStampAsNow() {
        super.setTimestamp(Instant.now());
        return this;
    }

    /**
     * Sets the color of the Embed.
     *
     * @param color the color.
     * @return this Embed instance.
     */
    public Embed setColor(Color color) {
        Checks.notNull(color, "color");

        super.setColor(color);
        return this;
    }

    /**
     * Sets the color of the Embed to a RGB color.
     *
     * @param red   red value of the RGB.
     * @param green green value of the RGB.
     * @param blue  blue value of the RGB.
     * @return this Embed instance.
     */
    public Embed setColor(int red, int green, int blue) {
        super.setColor(new Color(red, green, blue));
        return this;
    }

    /**
     * Sets the color of the Embed to a Hexadecimal color.
     *
     * @param hexCode the Hexadecimal color code.
     * @return this Embed instance.
     */
    public Embed setColor(String hexCode) {
        Checks.notEmpty(hexCode, "hexCode");

        if (hexCode.startsWith("#"))
            super.setColor(Color.decode(hexCode));
        else
            super.setColor(Color.decode("#" + hexCode));
        return this;
    }

    /**
     * Sets the thumbnail of the Embed to a User's profile picture.
     *
     * @param user the user.
     * @return this Embed instance.
     */
    public Embed setThumbnail(User user) {
        Checks.notNull(user, "user");

        super.setThumbnail(user.getAvatarUrl() == null ? user.getDefaultAvatarUrl() : user.getAvatarUrl());
        return this;
    }

    /**
     * Sets the thumbnail of the Embed to a picture url.
     *
     * @param url the url of the picture.
     * @return this Embed instance.
     */
    public Embed setThumbnail(String url) {
        Checks.notEmpty(url, "url");

        super.setThumbnail(url);
        return this;
    }

    /**
     * Sets the image of the Embed to a User's profile picture.
     *
     * @param user the user.
     * @return this Embed instance.
     */
    public Embed setImage(User user) {
        Checks.notNull(user, "user");

        super.setImage(user.getAvatarUrl());
        return this;
    }

    /**
     * Sets the image of the Embed to a picture url.
     *
     * @param url the url of the picture.
     * @return this Embed instance.
     */
    public Embed setImage(String url) {
        Checks.notEmpty(url, "url");

        super.setImage(url);
        return this;
    }

    /**
     * Sets the author of the Embed to a user.
     *
     * @param user the user.
     * @return this Embed instance.
     */
    public Embed setAuthor(User user) {
        Checks.notNull(user, "user");

        super.setAuthor(user.getName(), null, user.getAvatarUrl());
        return this;
    }

    /**
     * Sets the author of the Embed to a user and making it clickable using a url.
     *
     * @param user the user.
     * @param url  the clickable url.
     * @return this Embed instance.
     */
    public Embed setAuthor(User user, String url) {
        Checks.notNull(user, "user");

        super.setAuthor(user.getName(), url, user.getAvatarUrl());
        return this;
    }

    /**
     * Sets the author of the Embed to a name.
     *
     * @param name the name.
     * @return this Embed instance.
     */
    public Embed setAuthor(String name) {
        Checks.notEmpty(name, "name");

        super.setAuthor(name);
        return this;
    }

    /**
     * Sets the author of the Embed to a name and making it clickable using a url.
     *
     * @param name the name.
     * @param url  the clickable url.
     * @return this Embed instance.
     */
    public Embed setAuthor(String name, String url) {
        Checks.notEmpty(name, "name");

        super.setAuthor(name, url);
        return this;
    }

    /**
     * Sets the author of the Embed to a name and image (using iconUrl) and making it clickable using a url.
     *
     * @param name    the name.
     * @param url     the clickable url.
     * @param iconUrl the image's url.
     * @return this Embed instance.
     */
    public Embed setAuthor(String name, String url, String iconUrl) {
        Checks.notEmpty(name, "name");

        super.setAuthor(name, url, iconUrl);
        return this;
    }

    /**
     * Sets the footer of the Embed to a user's name and profile picture.
     *
     * @param user the user.
     * @return this Embed instance.
     */
    public Embed setFooter(User user) {
        Checks.notNull(user, "user");

        super.setFooter(String.format("%#s", user), user.getAvatarUrl());
        return this;
    }

    /**
     * Sets the footer of the Embed to a text and image (using iconUrl).
     *
     * @param text    the text.
     * @param iconUrl the image's url.
     * @return this Embed instance.
     */
    public Embed setFooter(String text, String iconUrl) {
        Checks.notEmpty(text, "text");

        super.setFooter(text, iconUrl);
        return this;
    }

    /**
     * Adds a field to the Embed.
     *
     * @param field the field.
     * @return this Embed instance.
     */
    public Embed addField(Field field) {
        Checks.notNull(field, "field");

        super.addField(field);
        return this;
    }

    /**
     * Adds a field the the Embed making it not inline.
     *
     * @param name    the name of the field.
     * @param content the content of the field.
     * @return this Embed instance.
     */
    public Embed addField(String name, String content) {
        Checks.notEmpty(name, "name");
        Checks.notEmpty(content, "content");

        super.addField(name, content, false);
        return this;
    }

    /**
     * Adds a field the the Embed making it inline using inline.
     *
     * @param name    the name of the field.
     * @param content the content of the field.
     * @param inline  if the field should be inline.
     * @return this Embed instance.
     */
    public Embed addField(String name, String content, boolean inline) {
        Checks.notEmpty(name, "name");
        Checks.notEmpty(content, "content");

        super.addField(name, content, inline);
        return this;
    }

    /**
     * Bulk adds fields to the Embed.
     *
     * @param fields the fields.
     * @return this Embed instance.
     */
    public Embed addFields(List<Field> fields) {
        Checks.notEmpty(fields, "fields");

        for (Field field : fields)
            super.addField(field);
        return this;
    }

    /**
     * Bulk adds fields and adds fields to the Embed using name, content and inline.
     *
     * @param fields the fields.
     * @param inline if the fields should be inline.
     * @return this Embed instance.
     */
    public Embed addFields(Map<String, String> fields, boolean inline) {
        Checks.notNull(fields, "fields");

        for (Entry<String, String> field : fields.entrySet())
            super.addField(field.getKey(), field.getValue(), inline);
        return this;
    }

    @Override
    public Embed clear() {
        super.clear();
        return this;
    }

    @Override
    public Embed appendDescription(CharSequence description) {
        Checks.notEmpty(description, "description");

        super.appendDescription(description);
        return this;
    }

    @Override
    public Embed clearFields() {
        super.clearFields();
        return this;
    }

    @Override
    public Embed addBlankField(boolean inline) {
        super.addBlankField(inline);
        return this;
    }

    /**
     * Gets the arguments used to replace when building the Embed.
     *
     * @return the arguments.
     */
    public List<String> getArgs() {
        return args;
    }

    /**
     * Gets the title of the Embed.
     *
     * @return the title.
     */
    public String getTitle() {
        return title;
    }

    @Override
    public Embed setTitle(String title, String url) {
        Checks.notEmpty(title, "title");

        super.setTitle(title, url);
        return this;
    }

    @Override
    public Embed setColor(int color) {
        super.setColor(color);
        return this;
    }

    @Override
    public Embed setTimestamp(TemporalAccessor temporal) {
        Checks.notNull(temporal, "temporal");

        super.setTimestamp(temporal);
        return this;
    }

    /**
     * Builds the message.
     *
     * @return a built EmbedMessage.
     */
    public MessageEmbed build() {
        if (args == null || args.size() == 0) {
            super.setTitle(title);
            return super.build();
        }

        Embed embed = new Embed(this);

        String[] args = this.args.toArray(new String[this.args.size()]);
        embed.setDescription(Chat.processString(super.getDescriptionBuilder().toString(), args));
        List<Field> fields = new ArrayList<>(super.getFields());
        embed.clearFields();
        for (Field field : fields)
            embed.addField(new Field(Chat.processString(field.getName(), args), Chat.processString(field.getValue(), args), field.isInline()));

        return embed.superBuild();
    }

    private MessageEmbed superBuild() {
        String[] args = this.args.toArray(new String[this.args.size()]);
        super.setTitle(Chat.processString(title, args));
        return super.build();
    }

    /**
     * Sends the embed message to a channel.
     *
     * @param channel the channel.
     */
    public void send(MessageChannel channel) {
        channel.sendMessage(build()).queue();
    }

    /**
     * Sends the embed message to a channel and submits consumer right after.
     *
     * @param channel  the channel.
     * @param consumer the consumer.
     */
    public void send(MessageChannel channel, Consumer<Message> consumer) {
        channel.sendMessage(build()).queue(consumer);
    }

    /**
     * Sends the embed message to a channel and returning the message.
     *
     * @param channel  the channel.
     * @param complete true if send the message in main thread false if not.
     * @return the mesage sent.
     */
    public Message send(MessageChannel channel, boolean complete) {
        if (complete)
            return channel.sendMessage(build()).complete();
        send(channel);
        return null;
    }

}
