package com.ossrep.ercot.mis.client;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public record DocumentList(List<Document> documents) {

    public List<Document> getByPublishDate(LocalDate publishDate) {
        return this.documents.stream().filter(document -> document.publishDate().truncatedTo(ChronoUnit.DAYS).isEqual(OffsetDateTime.of(publishDate, LocalTime.MIDNIGHT, document.publishDate().getOffset())))
                .toList();
    }

}
