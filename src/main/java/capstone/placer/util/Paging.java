package capstone.placer.util;

import lombok.Data;

@Data
public class Paging {
    public static int PAGE_SIZE = 100;

    private int pageNumber;

    public Paging(int pageNumber) {
        this.pageNumber = pageNumber;
    }

}
