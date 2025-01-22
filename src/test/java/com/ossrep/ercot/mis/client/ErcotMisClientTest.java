package com.ossrep.ercot.mis.client;

import com.ossrep.ercot.mis.client.report.tdspesiidextract.TdspEsiidExtract;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ErcotMisClientTest {

    ErcotMisClient emil = new ErcotMisClient();

    @Test
    public void fetchDocumentList() {
        DocumentList documentList = emil.fetchDocumentList(TdspEsiidExtract.getReportTypeId());
        assertNotNull(documentList);
    }

}
