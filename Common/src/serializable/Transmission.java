package serializable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 传输的序列化对象基类
 */
public class Transmission implements Serializable {
    private static final long serialVersionUID = 2984401328900115796L;
    private final Map<String, Object> data;
    public TransmissionType type;
    public Transmission(TransmissionType type){
        this.type = type;
        this.data = new HashMap<>();
    }
    public void setAttribute(String name, Object object){
        data.put(name, object);
    }
    public Object getAttribute(String name){
        return data.get(name);
    }
}
