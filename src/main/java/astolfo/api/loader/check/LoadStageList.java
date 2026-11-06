package astolfo.api.loader.check;

import astolfo.api.loader.LoadStage;
import astolfo.utilities.impl.datatypes.containers.ListContainerOwner;

public class LoadStageList {
   private final ListContainerOwner<LoadStage> stageContainer;

   public LoadStageList(ListContainerOwner<LoadStage> listContainerOwner) {
      this.stageContainer = listContainerOwner;
   }

   public ListContainerOwner<LoadStage> getStageContainer() {
      return this.stageContainer;
   }
}
