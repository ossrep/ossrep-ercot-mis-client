package com.ossrep.ercot.mis.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record Document(
        @JsonProperty("ExpiredDate")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime expiredDate,

        @JsonProperty("ILMStatus")
        String ILMStatus,

        @JsonProperty("SecurityStatus")
        String securityStatus,

        @JsonProperty("ContentSize")
        int contentSize,

        @JsonProperty("Extension")
        String extension,

        @JsonProperty("ReportTypeID")
        String reportTypeID,

        @JsonProperty("Prefix")
        String prefix,

        @JsonProperty("FriendlyName")
        String friendlyName,

        @JsonProperty("ConstructedName")
        String constructedName,

        @JsonProperty("DocID")
        int docID,

        @JsonProperty("PublishDate")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime publishDate,

        @JsonProperty("ReportName")
        String reportName,

        @JsonProperty("DUNS")
        String DUNS,

        @JsonProperty("DocCount")
        String docCount
) {
}