package io.flexio.docker.api.types.container;

import io.flexio.docker.api.types.container.optional.OptionalNetworkSettings;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class NetworkSettingsImpl implements NetworkSettings {
  private final String iPAddress;

  NetworkSettingsImpl(String iPAddress) {
    this.iPAddress = iPAddress;
  }

  public String iPAddress() {
    return this.iPAddress;
  }

  public NetworkSettings withIPAddress(String value) {
    return NetworkSettings.from(this).iPAddress(value).build();
  }

  public NetworkSettings changed(NetworkSettings.Changer changer) {
    return changer.configure(NetworkSettings.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NetworkSettingsImpl that = (NetworkSettingsImpl) o;
        return Objects.equals(this.iPAddress, that.iPAddress);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.iPAddress});
  }

  @Override
  public String toString() {
    return "NetworkSettings{" +
        "iPAddress=" + Objects.toString(this.iPAddress) +
        '}';
  }

  public OptionalNetworkSettings opt() {
    return OptionalNetworkSettings.of(this);
  }
}
