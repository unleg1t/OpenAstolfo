package astolfo.api.auth.net;

import astolfo.utilities.impl.datatypes.collections.ShapedMap;

public class ChannelGroupBuilder {
   private final ShapedMap<String, Channel> channels = new ShapedMap<>();

   public ChannelGroupBuilder withChannel(String string, Channel channel) {
      this.channels.put(string, channel);
      return this;
   }

   public ImmutableChannelGroup build() {
      return new ImmutableChannelGroup(this);
   }

   public static ShapedMap getChannels(ChannelGroupBuilder channelGroupBuilder) {
      return channelGroupBuilder.channels;
   }
}
