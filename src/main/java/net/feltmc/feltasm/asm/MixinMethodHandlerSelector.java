package net.feltmc.feltasm.asm;

import org.spongepowered.asm.mixin.injection.selectors.ElementNode;
import org.spongepowered.asm.mixin.injection.selectors.ISelectorContext;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorDynamic;
import org.spongepowered.asm.mixin.injection.selectors.InvalidSelectorException;
import org.spongepowered.asm.mixin.injection.selectors.MatchResult;

@ITargetSelectorDynamic.SelectorId("MixinMethodHandler")
public class MixinMethodHandlerSelector implements ITargetSelectorDynamic, ITargetSelectorByName {
    private final String prefix, name;

    public MixinMethodHandlerSelector(String prefix, String name) {
        this.prefix = prefix;
        this.name = name;
    }

    @Override
    public String getOwner() {
        return null;
    }

    @Override
    public String getName() {
        return prefix + "$" + name;
    }

    @Override
    public String getDesc() {
        return null;
    }

    @Override
    public String toDescriptor() {
        return "";
    }

    @Override
    public MatchResult matches(String owner, String name, String desc) {
        return name.startsWith(prefix + "$") && name.endsWith("$" + this.name) ? MatchResult.EXACT_MATCH : MatchResult.NONE;
    }

    @Override
    public ITargetSelector next() {
        return null;
    }

    @Override
    public ITargetSelector configure(Configure request, String... args) {
        return this;
    }

    @Override
    public ITargetSelector validate() throws InvalidSelectorException {
        return this;
    }

    @Override
    public ITargetSelector attach(ISelectorContext context) throws InvalidSelectorException {
        return this;
    }

    @Override
    public int getMinMatchCount() {
        return 0;
    }

    @Override
    public int getMaxMatchCount() {
        return 1;
    }

    @Override
    public <TNode> MatchResult match(ElementNode<TNode> node) {
        return this.matches(node.getOwner(), node.getName(), node.getDesc());
    }

    public static MixinMethodHandlerSelector parse(String input, ISelectorContext context) {
        String[] strings = decompose(input);
        return new MixinMethodHandlerSelector(strings[0], strings[1]);
    }

    protected static String[] decompose(String location) {
        if (!location.contains(":")) throw new InvalidSelectorException("input must contain a colon!");
        String[] strings = new String[2];
        int i = location.indexOf(':');
        if (i >= 1) {
            strings[1] = location.substring(i + 1);
            strings[0] = location.substring(0, i);
        } else if (i == 0){
            throw new InvalidSelectorException("input cannot start with a colon!");
        }
        return strings;
    }
}
