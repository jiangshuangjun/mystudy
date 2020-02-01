import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakRenferenceDemo extends WeakReference {

    private String name;
    private Integer age;

    public WeakRenferenceDemo(Object referent) {
        super(referent);
    }

    public WeakRenferenceDemo(Object referent, ReferenceQueue q) {
        super(referent, q);
    }


}
