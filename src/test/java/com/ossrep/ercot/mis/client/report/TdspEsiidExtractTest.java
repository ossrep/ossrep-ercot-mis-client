package com.ossrep.ercot.mis.client.report;

import com.ossrep.ercot.mis.client.report.tdspesiidextract.TdspEsiid;
import com.ossrep.ercot.mis.client.report.tdspesiidextract.TdspEsiidExtract;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TdspEsiidExtractTest {

    private final TdspEsiidExtract tdspEsiidExtract = new TdspEsiidExtract();

    @Test
    public void fetchForCenterpointYesterday() throws IOException, InterruptedException {
        List<TdspEsiid> tdspEsiids = tdspEsiidExtract.fetchByTdspAndDate(Tdsp.CENTERPOINT, LocalDate.now().minusDays(1));
        assertNotNull(tdspEsiids);
    }

}
