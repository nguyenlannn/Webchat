package website.chatx.core.common;

import lombok.Getter;

@Getter
public class CommonPaginator {

    private Integer pageNo;
    private Integer pageSize;
    private Long totalItems;
    private Integer totalPages;

    private Integer offset;
    private Integer limit;

    private void calculate() {

        this.limit = this.pageSize;

        if (totalItems == 0) {
            return;
        }

        this.totalPages = (int) Math.floor((double) this.totalItems / (double) this.pageSize);
        this.offset = (this.pageNo - 1) * this.pageSize;
    }

    public CommonPaginator(Integer pageNo, Integer pageSize, Long totalItems) {
        this.pageNo = pageNo == null ? 1 : pageNo;
        this.pageSize = pageSize == null ? Integer.MAX_VALUE : pageSize;
        this.totalItems = totalItems;
        this.calculate();
    }
}
