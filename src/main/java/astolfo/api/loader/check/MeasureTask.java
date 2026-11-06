package astolfo.api.loader.check;

public interface MeasureTask {
   MeasureResult getResult();

   void setResult(MeasureResult var1);

   void validate();
}
