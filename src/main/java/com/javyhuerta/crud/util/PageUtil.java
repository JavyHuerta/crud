package com.javyhuerta.crud.util;

import com.javyhuerta.crud.exception.ApiException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;

public class PageUtil {

    private PageUtil() {}

    public static Sort getSort(String sort) {
        Sort sortFormat = Sort.unsorted();

        String[] sortSplit = sort.split(",");
        int length = sortSplit.length;

        if (length % 2 != 0) {
            throw new ApiException("Error: Sort format is not being followed", HttpStatus.BAD_REQUEST);
        }

        for (int i = 0; i < length; i += 2) {
            String column = sortSplit[i].trim();
            String direction = sortSplit[i + 1].toLowerCase().trim();

            Order order = direction.equals("asc") ? Order.asc(column) : Order.desc(column);
            sortFormat = sortFormat.and(Sort.by(order));
        }

        return sortFormat;
    }
}

