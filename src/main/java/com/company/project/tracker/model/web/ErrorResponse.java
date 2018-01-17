package com.company.project.tracker.model.web;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponse {
    private int errorStatus;
    private Object errorResponse;
    private String message;
}
