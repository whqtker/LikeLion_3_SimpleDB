package com.ll.SimpleDb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Article {
    private final Long id;
    private final String title;
    private final String body;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final boolean isBlind;
}
