package com.ossrep.ercot.mis.client.report.tdspesiidextract;

import com.ossrep.ercot.mis.client.Document;
import com.ossrep.ercot.mis.client.DocumentList;
import com.ossrep.ercot.mis.client.ErcotMisClient;
import com.ossrep.ercot.mis.client.ErcotMisClientException;
import com.ossrep.ercot.mis.client.report.Tdsp;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TdspEsiidExtract {

    private final ErcotMisClient ercotMisClient = new ErcotMisClient();
    static int reportTypeId = 203;

    public List<TdspEsiid> fetchByTdspAndDate(Tdsp tdsp, LocalDate localDate) throws IOException, InterruptedException {
        DocumentList documentList = ercotMisClient.fetchDocumentList(reportTypeId);
        Document document = filterByTdspAndLocalDate(documentList, tdsp, localDate).orElseThrow(() -> new RuntimeException("No Tdsp and LocalDate found"));
        Path path = ercotMisClient.fetchDocument(document);
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(path.toFile()))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().toLowerCase().endsWith(".csv")) {
                    return parseCsv(zis);
                }
                zis.closeEntry();
            }
        }
        throw new ErcotMisClientException("No entries found");
    }

    private Optional<Document> filterByTdspAndLocalDate(DocumentList documentList, Tdsp tdsp, LocalDate localDate) {
        List<Document> list = documentList.documents().stream().filter(document -> {
            return document.friendlyName().contains(tdsp.name()) && document.publishDate().toLocalDate().isEqual(localDate);
        }).toList();
        if (list.isEmpty()) {
            return Optional.empty();
        }
        if (list.size() > 1) {
            throw new RuntimeException("Found more than one for Tdsp and LocalDate");
        }
        return Optional.of(list.getFirst());
    }

    private static List<TdspEsiid> parseCsv(InputStream csvStream) throws IOException {
        List<TdspEsiid> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",", -1);
                if (fields.length < 19) {
                    continue;
                }
                TdspEsiid record = new TdspEsiid(
                        fields[0],
                        fields[1],
                        fields[2],
                        fields[3],
                        fields[4],
                        fields[5],
                        fields[6],
                        fields[7],
                        fields[8],
                        fields[9],
                        fields[10],
                        fields[11],
                        fields[12],
                        fields[13],
                        fields[14],
                        fields[15],
                        fields[16],
                        fields[17],
                        fields[18]
                );
                records.add(record);
            }
        }
        return records;
    }

    public static int getReportTypeId() {
        return reportTypeId;
    }

}
