package astolfo.api.loader.check;

public interface Measure {
   String getName();

   void execute() throws MeasureException;
}
