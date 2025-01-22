package com.ossrep.ercot.mis.client.report.tdspesiidextract;

public record TdspEsiid(
    String esiid,
    String address,
    String addressOverflow,
    String city,
    String state,
    String zipCode,
    String duns,
    String meterReadCycle,
    String status,
    String premiseType,
    String powerRegion,
    String stationCode,
    String stationName,
    String metered,
    String openServiceOrders,
    String polrCustomerClass,
    String settlementAmsIndicator,
    String tdspAmsIndicator,
    String switchHoldIndicator
) {}