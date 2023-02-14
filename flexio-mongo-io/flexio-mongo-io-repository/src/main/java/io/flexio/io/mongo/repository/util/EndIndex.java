package io.flexio.io.mongo.repository.util;

public class EndIndex {
    private final long requestedStart;
    private final int foundSize;

    public EndIndex(long requestedStart, int foundSize) {
        this.requestedStart = requestedStart;
        this.foundSize = foundSize;
    }

    public long index() {
        return Math.max(this.requestedStart + this.foundSize - 1, 0);
    }
}
