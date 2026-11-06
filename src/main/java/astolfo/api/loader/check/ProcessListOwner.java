package astolfo.api.loader.check;

import astolfo.utilities.impl.datatypes.containers.ListContainerOwner;

public class ProcessListOwner {
   private final ListContainerOwner<String> processContainer;

   public ProcessListOwner() {
      ProcessNames processnames = new ProcessNames();
      this.processContainer = new ListContainerOwner<>(processnames.getProcessNames());
   }

   public ListContainerOwner<String> getProcessContainer() {
      return this.processContainer;
   }
}
