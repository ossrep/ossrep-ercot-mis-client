package com.ossrep.ercot.mis.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ErcotMisClient {

    private final ObjectMapper objectMapper;

    public ErcotMisClient() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public DocumentList fetchDocumentList(int reportTypeId) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.ercot.com/misapp/servlets/IceDocListJsonWS?reportTypeId=" + reportTypeId))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonResponse = response.body();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode documentListNode = rootNode.path("ListDocsByRptTypeRes").path("DocumentList");
            return new DocumentList(deserializeList(documentListNode));
        } catch (IOException | InterruptedException e) {
            throw new ErcotMisClientException(e.getMessage(), e);
        }
    }

    public Path fetchDocument(Document document) throws IOException, InterruptedException {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.ercot.com/misdownload/servlets/mirDownload?doclookupId=" + document.docID()))
                    .GET()
                    .build();
            Path tempFilePath = Files.createTempFile(String.valueOf(document.docID()), "." + document.extension());
            HttpResponse<Path> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofFile(tempFilePath));
            return response.body();
        }
    }

    private List<Document> deserializeList(JsonNode documentListNode) throws JsonProcessingException {
        List<Document> documents = new ArrayList<>();
        for (JsonNode documentWrapperNode : documentListNode) {
            JsonNode documentNode = documentWrapperNode.path("Document");
            documents.add(deserialize(documentNode));
        }
        return documents;
    }

    private Document deserialize(JsonNode jsonNode) throws JsonProcessingException {
        return objectMapper.treeToValue(jsonNode, Document.class);
    }

}
