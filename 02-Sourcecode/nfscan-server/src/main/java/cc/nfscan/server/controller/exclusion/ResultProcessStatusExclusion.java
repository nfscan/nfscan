package cc.nfscan.server.controller.exclusion;

import cc.nfscan.server.domain.OCRTransaction;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.util.Arrays;
import java.util.List;

/**
 * A strategy (or policy) definition that is used to decide whether or not a field or top-level
 * class should be serialized or deserialized as part of the JSON output/input.
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
public class ResultProcessStatusExclusion implements ExclusionStrategy {

    /**
     * Fields we want to serialized into our JSON response
     */
    private List<String> fieldsOcrTransaction = Arrays.asList("cnpj", "date", "coo", "total", "processed");

    /**
     * @param f the field object that is under test
     * @return true if the field should be ignored; otherwise false
     */
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        if (f.getDeclaringClass() == OCRTransaction.class) {
            return !fieldsOcrTransaction.contains(f.getName());
        } else {
            return false;
        }

    }

    /**
     * @param clazz the class object that is under test
     * @return true if the class should be ignored; otherwise false
     */
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

}
