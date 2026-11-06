package astolfo.utilities.impl.misc;

public interface SelfTyped<A> {
   default <B extends A> B self() {
      return (B)this;
   }
}
