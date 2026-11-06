package astolfo.utilities.impl.datatypes.functions;

import java.util.function.Function;

@FunctionalInterface
public interface CheckedFunction<A, R> extends Function<A, R>, Checked {
   @Override
   R apply(A var1);
}
