package com.ecpi.jwt.response;

import java.util.List;

public record DataResponse<T>(List<T> content,int totalPages, long totalElements, int size) { }
