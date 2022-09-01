package io.flexio.svg.generator.generator.tag.property;

import io.flexio.svg.generator.generator.Attribute;

import java.util.Locale;

public interface ClipPathHolder<C extends ClipPathHolder> extends Attribute {
    String CLIP_PATH = "clip-path";
    @SuppressWarnings("unchecked")
    default C clipPath(String clipPath) {
        if (clipPath == null) {
            attributesMap().remove(CLIP_PATH);
        } else {
            this.attributes(CLIP_PATH, clipPath);
        }
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
