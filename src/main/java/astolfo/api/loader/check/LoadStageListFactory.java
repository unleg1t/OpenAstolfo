package astolfo.api.loader.check;

import astolfo.api.loader.LoadStage;
import astolfo.utilities.impl.datatypes.containers.ListContainerOwner;

public class LoadStageListFactory {
   private final ListContainerOwner<LoadStage> stageContainer;

   public LoadStageListFactory() {
      LoadStagesList loadstageslist = new LoadStagesList();
      this.stageContainer = new ListContainerOwner<>(loadstageslist.getStages());
   }

   public ListContainerOwner<LoadStage> getStageContainer() {
      return this.stageContainer;
   }
}
