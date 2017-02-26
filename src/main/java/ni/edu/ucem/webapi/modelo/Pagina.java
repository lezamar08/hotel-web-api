package ni.edu.ucem.webapi.modelo;

import java.util.List;

public class Pagina<T>
{
    private List<T> data;
    private int count;
    private int offset;
    private int limit;
    
    public Pagina(){
        
    }

    public Pagina(final List<T> data, final int count, final int offset,final int limit)
    {
        this.data = data;
        this.count = count;
        this.offset = offset;
        this.limit = limit;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
