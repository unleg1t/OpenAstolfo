package astolfo.api.loader.check;

import astolfo.utilities.impl.datatypes.containers.ListContainerOwner;

public class JvmArgumentsPropertyListOwner {
   private final ListContainerOwner<String> patternContainer;

   public JvmArgumentsPropertyListOwner() {
      JvmArgumentPropertyPatterns jvmargumentpropertypatterns = new JvmArgumentPropertyPatterns();
      this.patternContainer = new ListContainerOwner<>(jvmargumentpropertypatterns.getPatterns());
   }

   public ListContainerOwner<String> getPatternContainer() {
      return this.patternContainer;
   }
}
