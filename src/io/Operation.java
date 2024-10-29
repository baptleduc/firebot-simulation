package io;
import java.util.zip.DataFormatException;
@FunctionalInterface
public interface Operation {
    void caseExecute(int lig, int col) throws DataFormatException;

}
