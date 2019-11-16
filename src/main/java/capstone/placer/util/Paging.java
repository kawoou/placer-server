package capstone.placer.util;

import lombok.Data;

@Data
public class Paging {
    public static int PAGE_SIZE = 100;

    private int pageNumber;

    public int getNextPageStartOffset() {
        return (this.pageNumber - 1) * PAGE_SIZE;
    }
    public Paging(int pageNumber) {
        this.pageNumber = pageNumber;
    }

}
