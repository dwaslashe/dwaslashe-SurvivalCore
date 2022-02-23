package xyz.dwaslashe.survivalcore.craftbukkit;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonParseException;
import io.papermc.paper.adventure.AdventureComponent;
import net.minecraft.EnumChatFormat;
import net.minecraft.network.chat.*;
import org.bukkit.ChatColor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CraftChatMessage {
    private static final Pattern LINK_PATTERN = Pattern.compile("((?:(?:https?):\\/\\/)?(?:[-\\w_\\.]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + String.valueOf('§') + " \\n]|$))))");
    private static final Map<Character, EnumChatFormat> formatMap;

    public static EnumChatFormat getColor(ChatColor color) {
        return (EnumChatFormat)formatMap.get(color.getChar());
    }

    public static ChatColor getColor(EnumChatFormat format) {
        return ChatColor.getByChar(format.A);
    }

    public static IChatBaseComponent fromStringOrNull(String message) {
        return fromStringOrNull(message, false);
    }

    public static IChatBaseComponent fromStringOrNull(String message, boolean keepNewlines) {
        return message != null && !message.isEmpty() ? fromString(message, keepNewlines)[0] : null;
    }

    public static IChatBaseComponent[] fromString(String message) {
        return fromString(message, false);
    }

    public static IChatBaseComponent[] fromString(String message, boolean keepNewlines) {
        return fromString(message, keepNewlines, false);
    }

    public static IChatBaseComponent[] fromString(String message, boolean keepNewlines, boolean plain) {
        return (new CraftChatMessage.StringMessage(message, keepNewlines, plain)).getOutput();
    }

    public static String toJSON(IChatBaseComponent component) {
        return IChatBaseComponent.ChatSerializer.a(component);
    }

    public static String toJSONOrNull(IChatBaseComponent component) {
        return component == null ? null : toJSON(component);
    }

    public static IChatBaseComponent fromJSON(String jsonMessage) throws JsonParseException {
        return IChatBaseComponent.ChatSerializer.a(jsonMessage);
    }

    public static IChatBaseComponent fromJSONOrNull(String jsonMessage) {
        if (jsonMessage == null) {
            return null;
        } else {
            try {
                return fromJSON(jsonMessage);
            } catch (JsonParseException var2) {
                return null;
            }
        }
    }

    public static IChatBaseComponent fromJSONOrString(String message) {
        return fromJSONOrString(message, false);
    }

    public static IChatBaseComponent fromJSONOrString(String message, boolean keepNewlines) {
        return fromJSONOrString(message, false, keepNewlines);
    }

    private static IChatBaseComponent fromJSONOrString(String message, boolean nullable, boolean keepNewlines) {
        if (message == null) {
            message = "";
        }

        if (nullable && message.isEmpty()) {
            return null;
        } else {
            IChatBaseComponent component = fromJSONOrNull(message);
            return component != null ? component : fromString(message, keepNewlines)[0];
        }
    }

    public static String fromJSONOrStringToJSON(String message) {
        return fromJSONOrStringToJSON(message, false);
    }

    public static String fromJSONOrStringToJSON(String message, boolean keepNewlines) {
        return fromJSONOrStringToJSON(message, false, keepNewlines, 2147483647, false);
    }

    public static String fromJSONOrStringOrNullToJSON(String message) {
        return fromJSONOrStringOrNullToJSON(message, false);
    }

    public static String fromJSONOrStringOrNullToJSON(String message, boolean keepNewlines) {
        return fromJSONOrStringToJSON(message, true, keepNewlines, 2147483647, false);
    }

    public static String fromJSONOrStringToJSON(String message, boolean nullable, boolean keepNewlines, int maxLength, boolean checkJsonContentLength) {
        if (message == null) {
            message = "";
        }

        if (nullable && message.isEmpty()) {
            return null;
        } else {
            IChatBaseComponent component = fromJSONOrNull(message);
            if (component != null) {
                if (checkJsonContentLength) {
                    String content = fromComponent(component);
                    String trimmedContent = trimMessage(content, maxLength);
                    if (content != trimmedContent) {
                        return fromStringToJSON(trimmedContent, keepNewlines);
                    }
                }

                return message;
            } else {
                message = trimMessage(message, maxLength);
                return fromStringToJSON(message, keepNewlines);
            }
        }
    }

    public static String trimMessage(String message, int maxLength) {
        return message != null && message.length() > maxLength ? message.substring(0, maxLength) : message;
    }

    public static String fromStringToJSON(String message) {
        return fromStringToJSON(message, false);
    }

    public static String fromStringToJSON(String message, boolean keepNewlines) {
        IChatBaseComponent component = fromString(message, keepNewlines)[0];
        return toJSON(component);
    }

    public static String fromStringOrNullToJSON(String message) {
        IChatBaseComponent component = fromStringOrNull(message);
        return toJSONOrNull(component);
    }

    public static String fromJSONComponent(String jsonMessage) {
        IChatBaseComponent component = fromJSONOrNull(jsonMessage);
        return fromComponent(component);
    }

    public static String fromComponent(IChatBaseComponent component) {
        if (component == null) {
            return "";
        } else {
            if (component instanceof AdventureComponent) {
                component = ((AdventureComponent)component).deepConverted();
            }

            StringBuilder out = new StringBuilder();
            boolean hadFormat = false;

            IChatBaseComponent c;
            for(Iterator<IChatBaseComponent> var3 = component.iterator(); var3.hasNext(); c.b((x) -> {
                out.append(x);
                return Optional.empty();
            })) {
                c = var3.next();
                ChatModifier modi = c.getChatModifier();
                ChatHexColor color = modi.getColor();
                if (!c.getText().isEmpty() || color != null) {
                    if (color == null) {
                        if (hadFormat) {
                            out.append(ChatColor.RESET);
                            hadFormat = false;
                        }
                    } else {
                        if (color.format != null) {
                            out.append(color.format);
                        } else {
                            out.append('§').append("x");
                            char[] var7 = color.b().substring(1).toCharArray();
                            int var8 = var7.length;

                            for (char magic : var7) {
                                out.append('§').append(magic);
                            }
                        }

                        hadFormat = true;
                    }
                }

                if (modi.isBold()) {
                    out.append(EnumChatFormat.r);
                    hadFormat = true;
                }

                if (modi.isItalic()) {
                    out.append(EnumChatFormat.u);
                    hadFormat = true;
                }

                if (modi.isUnderlined()) {
                    out.append(EnumChatFormat.t);
                    hadFormat = true;
                }

                if (modi.isStrikethrough()) {
                    out.append(EnumChatFormat.s);
                    hadFormat = true;
                }

                if (modi.isRandom()) {
                    out.append(EnumChatFormat.q);
                    hadFormat = true;
                }
            }

            return out.toString();
        }
    }

    public static IChatBaseComponent fixComponent(IChatBaseComponent component) {
        Matcher matcher = LINK_PATTERN.matcher("");
        return fixComponent(component, matcher);
    }

    private static IChatBaseComponent fixComponent(IChatBaseComponent component, Matcher matcher) {
        if (component instanceof ChatComponentText text) {
            String msg = text.getText();
            if (matcher.reset(msg).find()) {
                matcher.reset();
                ChatModifier modifier = text.getChatModifier();
                List<IChatBaseComponent> extras = new ArrayList<>();
                List<IChatBaseComponent> extrasOld = new ArrayList<>(text.getSiblings());
                component = text = new ChatComponentText("");

                int pos;
                for(pos = 0; matcher.find(); pos = matcher.end()) {
                    String match = matcher.group();
                    if (!match.startsWith("http://") && !match.startsWith("https://")) {
                        match = "http://" + match;
                    }

                    ChatComponentText prev = new ChatComponentText(msg.substring(pos, matcher.start()));
                    prev.setChatModifier(modifier);
                    extras.add(prev);
                    ChatComponentText link = new ChatComponentText(matcher.group());
                    ChatModifier linkModi = modifier.setChatClickable(new ChatClickable(ChatClickable.EnumClickAction.a, match));
                    link.setChatModifier(linkModi);
                    extras.add(link);
                }

                ChatComponentText prev = new ChatComponentText(msg.substring(pos));
                prev.setChatModifier(modifier);
                extras.add(prev);
                extras.addAll(extrasOld);

                for (IChatBaseComponent c : extras) {
                    text.addSibling(c);
                }
            }
        }

        List<IChatBaseComponent> extras = component.getSiblings();

        for(int i = 0; i < extras.size(); ++i) {
            IChatBaseComponent comp = extras.get(i);
            if (comp.getChatModifier().getClickEvent() == null) {
                extras.set(i, fixComponent(comp, matcher));
            }
        }

        if (component instanceof ChatMessage) {
            Object[] subs = ((ChatMessage)component).getArgs();

            for(int i = 0; i < subs.length; ++i) {
                Object comp = subs[i];
                if (comp instanceof IChatBaseComponent) {
                    IChatBaseComponent c = (IChatBaseComponent)comp;
                    if (c.getChatModifier().getClickEvent() == null) {
                        subs[i] = fixComponent(c, matcher);
                    }
                } else if (comp instanceof String && matcher.reset((String)comp).find()) {
                    subs[i] = fixComponent(new ChatComponentText((String)comp), matcher);
                }
            }
        }

        return component;
    }

    private CraftChatMessage() {
    }

    static {
        ImmutableMap.Builder<Character, EnumChatFormat> builder = ImmutableMap.builder();
        EnumChatFormat[] var1 = EnumChatFormat.values();
        int var2 = var1.length;

        for (EnumChatFormat format : var1) {
            builder.put(Character.toLowerCase(format.toString().charAt(1)), format);
        }

        formatMap = builder.build();
    }

    public static class StringMessage {
        private static final Pattern INCREMENTAL_PATTERN = Pattern.compile("(" + '§' + "[0-9a-fk-orx])|((?:(?:https?):\\/\\/)?(?:[-\\w_\\.]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + String.valueOf('§') + " \\n]|$))))|(\\n)", 2);
        private static final Pattern INCREMENTAL_PATTERN_KEEP_NEWLINES = Pattern.compile("(" + '§' + "[0-9a-fk-orx])|((?:(?:https?):\\/\\/)?(?:[-\\w_\\.]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + String.valueOf('§') + " ]|$))))", 2);
        private static final ChatModifier EMPTY;
        private static final ChatModifier RESET;
        private final List<IChatBaseComponent> list = new ArrayList<>();
        private IChatMutableComponent currentChatComponent = new ChatComponentText("");
        private ChatModifier modifier;
        private final IChatBaseComponent[] output;
        private int currentIndex;
        private StringBuilder hex;
        private final String message;

        private StringMessage(String message, boolean keepNewlines, boolean plain) {
            this.modifier = ChatModifier.a;
            this.message = message;
            if (message == null) {
                this.output = new IChatBaseComponent[]{this.currentChatComponent};
            } else {
                this.list.add(this.currentChatComponent);
                Matcher matcher = (keepNewlines ? INCREMENTAL_PATTERN_KEEP_NEWLINES : INCREMENTAL_PATTERN).matcher(message);
                String match = null;
                boolean needsAdd = false;

                int groupId;
                for(boolean hasReset = false; matcher.find(); this.currentIndex = matcher.end(groupId)) {
                    groupId = 0;

                    do {
                        ++groupId;
                    } while((match = matcher.group(groupId)) == null);

                    int index = matcher.start(groupId);
                    if (index > this.currentIndex) {
                        needsAdd = false;
                        this.appendNewComponent(index);
                    }

                    switch(groupId) {
                    case 1:
                        char c = match.toLowerCase(Locale.ENGLISH).charAt(1);
                        EnumChatFormat format = CraftChatMessage.formatMap.get(c);
                        if (c == 'x') {
                            this.hex = new StringBuilder("#");
                        } else if (this.hex != null) {
                            this.hex.append(c);
                            if (this.hex.length() == 7) {
                                this.modifier = RESET.setColor(ChatHexColor.a(this.hex.toString()));
                                this.hex = null;
                            }
                        } else if (format.isFormat() && format != EnumChatFormat.v) {
                            switch (format) {
                                case r -> this.modifier = this.modifier.setBold(Boolean.TRUE);
                                case u -> this.modifier = this.modifier.setItalic(Boolean.TRUE);
                                case s -> this.modifier = this.modifier.setStrikethrough(Boolean.TRUE);
                                case t -> this.modifier = this.modifier.setUnderline(Boolean.TRUE);
                                case q -> this.modifier = this.modifier.setRandom(Boolean.TRUE);
                                default -> throw new AssertionError("Unexpected message format");
                            }
                        } else {
                            ChatModifier previous = this.modifier;
                            this.modifier = (!hasReset ? RESET : EMPTY).setColor(format);
                            hasReset = true;
                            if (previous.isBold()) {
                                this.modifier = this.modifier.setBold(false);
                            }

                            if (previous.isItalic()) {
                                this.modifier = this.modifier.setItalic(false);
                            }

                            if (previous.isRandom()) {
                                this.modifier = this.modifier.setRandom(false);
                            }

                            if (previous.isStrikethrough()) {
                                this.modifier = this.modifier.setStrikethrough(false);
                            }

                            if (previous.isUnderlined()) {
                                this.modifier = this.modifier.setUnderline(false);
                            }
                        }

                        needsAdd = true;
                        break;
                    case 2:
                        if (plain) {
                            this.appendNewComponent(matcher.end(groupId));
                        } else {
                            if (!match.startsWith("http://") && !match.startsWith("https://")) {
                                match = "http://" + match;
                            }

                            this.modifier = this.modifier.setChatClickable(new ChatClickable(ChatClickable.EnumClickAction.a, match));
                            this.appendNewComponent(matcher.end(groupId));
                            this.modifier = this.modifier.setChatClickable(null);
                        }
                        break;
                    case 3:
                        if (needsAdd) {
                            this.appendNewComponent(index);
                        }

                        this.currentChatComponent = null;
                    }
                }

                if (this.currentIndex < message.length() || needsAdd) {
                    this.appendNewComponent(message.length());
                }

                this.output = this.list.toArray(new IChatBaseComponent[0]);
            }
        }

        private void appendNewComponent(int index) {
            IChatBaseComponent addition = (new ChatComponentText(this.message.substring(this.currentIndex, index))).setChatModifier(this.modifier);
            this.currentIndex = index;
            if (this.currentChatComponent == null) {
                this.currentChatComponent = new ChatComponentText("");
                this.list.add(this.currentChatComponent);
            }

            this.currentChatComponent.addSibling(addition);
        }

        private IChatBaseComponent[] getOutput() {
            return this.output;
        }

        static {
            EMPTY = ChatModifier.a.setItalic(false);
            RESET = ChatModifier.a.setBold(false).setItalic(false).setUnderline(false).setStrikethrough(false).setRandom(false);
        }
    }
}
