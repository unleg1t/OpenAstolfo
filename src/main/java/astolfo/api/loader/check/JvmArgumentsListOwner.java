package astolfo.api.loader.check;

import astolfo.utilities.impl.datatypes.containers.ListContainerOwner;

public class JvmArgumentsListOwner {
   private final ListContainerOwner<String> patternContainer;

   public JvmArgumentsListOwner() {
      JvmArgumentPatterns jvmargumentpatterns = new JvmArgumentPatterns();
      this.patternContainer = new ListContainerOwner<>(jvmargumentpatterns.getPatterns());
   }

   public ListContainerOwner<String> getPatternContainer() {
      return this.patternContainer;
   }
}
