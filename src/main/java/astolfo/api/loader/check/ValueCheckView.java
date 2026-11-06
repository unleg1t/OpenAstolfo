package astolfo.api.loader.check;

public interface ValueCheckView {
   int computeValue();

   ValueType getType();

   CheckResult evaluate();
}
