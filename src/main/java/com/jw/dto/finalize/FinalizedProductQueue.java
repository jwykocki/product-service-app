package com.jw.dto.finalize;

public record FinalizedProductQueue(Long productId, int reserved, int finalized) {}
