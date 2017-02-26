package ni.edu.ucem.webapi.modelo;

public class Filtro 
{
    public static final int DEFAULT_OFFSET = 0;
    public static final int DEFAULT_LIMIT = 10;
    private int limit;
    private int offset;
    private String sort;
    private SortOrder sortOrder;
    
    public static enum SortOrder
    {
        ASC("asc"),DESC("desc");
        private final String order;
        
        private SortOrder(final String order)
        {
            this.order = order;
        }
        
        public String getValue()
        {
            return this.order;
        }
    }
    
    public static class Builder
    {
        private int offset;
        private int limit;
        private String sort;
        private SortOrder sortOrder;
        
        public Builder paginacion(final int offset, final int limit)
        {
            this.offset = offset;
            this.limit = limit;
            return this;
        }
        
        public Builder ordenamiento(final String sort, final SortOrder sortOrder)
        {
            this.sort = sort;
            this.sortOrder = sortOrder;
            return this;
        }
        
        public Filtro build() 
        {
            Filtro filtro = new Filtro();
            if (this.offset < 0 || this.limit < 1) 
            {
                this.offset = Filtro.DEFAULT_OFFSET;
                this.limit = Filtro.DEFAULT_LIMIT;
            }
            filtro.setOffset(offset);
            filtro.setLimit(limit);
            
            if(this.sort != null && !"".equals(this.sort))
            {
                filtro.setSort(this.sort);
                if(this.sortOrder != null)
                {
                    filtro.setSortOrder(this.sortOrder);
                }
            }
            return filtro;
        }
    }

    public Filtro()
    {
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
}
