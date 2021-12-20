package io.flexio.svg.generator.generator.tag.property;

import io.flexio.svg.generator.generator.Attribute;

import java.util.Locale;

public interface ClipPathHolder<C extends ClipPathHolder> extends Attribute {
    @SuppressWarnings("unchecked")
    default C clipPath(String clipPath) {
        attributes("clip-path", clipPath);
        return (C) this;
    }

    default C clipPath(String clipPathFormat, Object... args) {
        return this.clipPath(String.format(Locale.US, clipPathFormat, args));
    }

    @SuppressWarnings("unchecked")
    default C clipPath(Identifiable element) {
        if (element.attributesMap().containsKey(Identifiable.ID)) {
            return clipPath("url(#%s)", element.attributesMap().get(Identifiable.ID));
        }
        return (C) this;
    }
}
