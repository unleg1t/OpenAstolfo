package astolfo.api.loader.check;

import astolfo.utilities.impl.datatypes.containers.ListContainerOwner;

public class GameJarHashList {
   private final ListContainerOwner<String> hashContainer;

   public GameJarHashList() {
      GameJarHash gamejarhash = new GameJarHash();
      this.hashContainer = new ListContainerOwner<>(gamejarhash.getHashes());
   }

   public ListContainerOwner<String> getHashContainer() {
      return this.hashContainer;
   }
}
