package org.drombler.jstore.client.agent.startup.download;

import java.util.Objects;

public class DownloadId<K> {
    private final K key;

    public DownloadId(K key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DownloadId)) return false;
        DownloadId<?> that = (DownloadId<?>) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {

        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "DownloadId{" +
                "key=" + key +
                '}';
    }
}
