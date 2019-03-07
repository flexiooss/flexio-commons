package io.flexio.docker.api.types.container;

import io.flexio.docker.api.types.container.optional.OptionalNetworkSettings;
import java.lang.String;

public interface NetworkSettings {
  static Builder builder() {
    return new NetworkSettings.Builder();
  }

  static NetworkSettings.Builder from(NetworkSettings value) {
    if(value != null) {
      return new NetworkSettings.Builder()
          .iPAddress(value.iPAddress())
          ;
    }
    else {
      return null;
    }
  }

  String iPAddress();

  NetworkSettings withIPAddress(String value);

  int hashCode();

  NetworkSettings changed(NetworkSettings.Changer changer);

  OptionalNetworkSettings opt();

  class Builder {
    private String iPAddress;

    public NetworkSettings build() {
      return new NetworkSettingsImpl(this.iPAddress);
    }

    public NetworkSettings.Builder iPAddress(String iPAddress) {
      this.iPAddress = iPAddress;
      return this;
    }
  }

  interface Changer {
    NetworkSettings.Builder configure(NetworkSettings.Builder builder);
  }
}
