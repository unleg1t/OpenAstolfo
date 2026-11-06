package astolfo.utilities.impl.datatypes.containers;

public abstract class ContainerOperation {
   public abstract void initialize() throws ContainerException;

   public abstract void destroy() throws ContainerError;
}
