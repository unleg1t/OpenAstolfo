package astolfo.api.loader.check;

import astolfo.utilities.impl.datatypes.containers.ListContainerOwner;
import java.io.File;

public class FileListHolder {
   private final ListContainerOwner<File> fileContainer;

   public FileListHolder(ListContainerOwner<File> listContainerOwner) {
      this.fileContainer = listContainerOwner;
   }

   public ListContainerOwner<File> getFileContainer() {
      return this.fileContainer;
   }
}
